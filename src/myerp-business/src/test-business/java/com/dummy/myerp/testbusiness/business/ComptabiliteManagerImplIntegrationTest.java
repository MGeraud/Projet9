package com.dummy.myerp.testbusiness.business;

import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.dummy.myerp.consumer.ConsumerHelper.getDaoProxy;
import static org.junit.Assert.*;

public class ComptabiliteManagerImplIntegrationTest extends BusinessTestCase{

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    private EcritureComptable ecritureComptable;
    private EcritureComptable vecritureComptable = new EcritureComptable();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp(){

        Date date  = new GregorianCalendar(2016,5,12,5,30,10).getTime();

        ecritureComptable = new EcritureComptable();
        ecritureComptable.setLibelle("libellé");
        ecritureComptable.setReference("AC-2016/00001");
        ecritureComptable.setJournal(new JournalComptable("AC" , "Achat"));
        ecritureComptable.setDate(date);
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        Date date2019  = new GregorianCalendar(2020,5,12,5,30,10).getTime();
        vecritureComptable = new EcritureComptable();
        vecritureComptable.setId(1);
        vecritureComptable.setLibelle("libellé");
        vecritureComptable.setReference("AC-2020/00001");
        vecritureComptable.setJournal(new JournalComptable("AC" , "Achat"));
        vecritureComptable.setDate(date2019);
        vecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, new BigDecimal(123),
                null));
        vecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                null, null,
                new BigDecimal(123)));
    }

    @Test
    public void checkEcritureComptableContextwithIdNull() throws FunctionalException{
        expectedException.expect(FunctionalException.class);
        expectedException.expectMessage("Une autre écriture comptable existe déjà avec la même référence.");

        manager.checkEcritureComptable(ecritureComptable);
    }

    @Test
    public void checkEcritureComptableContextwithDifferentIdOnSameReference() throws FunctionalException{
        ecritureComptable.setId(12);
        expectedException.expect(FunctionalException.class);
        expectedException.expectMessage("Une autre écriture comptable existe déjà avec la même référence.");

        manager.checkEcritureComptable(ecritureComptable);
    }

    @Test
    public void checkEcritureComptableContext() throws FunctionalException{
        Date date2019  = new GregorianCalendar(2019,5,12,5,30,10).getTime();
        EcritureComptable toCheck = new EcritureComptable();
        toCheck.setLibelle("libellé");
        toCheck.setReference("AC-2019/00001");
        toCheck.setJournal(new JournalComptable("AC" , "Achat"));
        toCheck.setDate(date2019);
        toCheck.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                null, new BigDecimal(123),
                null));
        toCheck.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                null, null,
                new BigDecimal(123)));
        manager.checkEcritureComptable(toCheck);
    }

    @Test
    public void getListCompteComptable(){
        assertFalse(manager.getListCompteComptable().isEmpty());
    }

    @Test
    public void getListJournalComptable(){
        assertFalse(manager.getListJournalComptable().isEmpty());
    }

    @Test
    public void getListEcritureComptable(){
        assertFalse(manager.getListEcritureComptable().isEmpty());
    }

    @Test
    public void insertEcriturecomptable() throws FunctionalException, NotFoundException {
        manager.insertEcritureComptable(vecritureComptable);
        assertNotNull(getDaoProxy().getComptabiliteDao().getEcritureComptableByRef("AC-2020/00001"));
    }

    @Test
    public void updateEcriturecomptable() throws FunctionalException, NotFoundException {
        EcritureComptable toUpdate =getDaoProxy().getComptabiliteDao().getEcritureComptableByRef("AC-2020/00001");
        toUpdate.setLibelle("updated");
        manager.updateEcritureComptable(toUpdate);
        assertEquals( "updated", getDaoProxy().getComptabiliteDao().getEcritureComptableByRef("AC-2020/00001").getLibelle() );
    }

    @Test(expected = NotFoundException.class)
    public void deleteEcritureComptable() throws NotFoundException {

        manager.deleteEcritureComptable(1);
        getDaoProxy().getComptabiliteDao().getEcritureComptable(1);
    }

    @Test
    public void addReference() throws NotFoundException {
        String actualEcritureComptableReference = ecritureComptable.getReference();
        SequenceEcritureComptable vSequenceEcritureComptable = getDaoProxy().getComptabiliteDao()
                .getSequenceEcriturecomptableByAnneeAndJournal(2016, ecritureComptable.getJournal().getCode());
        manager.addReference(ecritureComptable);
        assertNotEquals( actualEcritureComptableReference, ecritureComptable.getReference());
        assertEquals(java.util.Optional.of(vSequenceEcritureComptable.getDerniereValeur() + 1).get(), getDaoProxy().getComptabiliteDao().getSequenceEcriturecomptableByAnneeAndJournal(2016, ecritureComptable.getJournal().getCode()).getDerniereValeur());
    }

    @Test
    public void addReferenceWithNotKnownAnnee() throws NotFoundException {
        Date date  = new GregorianCalendar(1952,5,12,5,30,10).getTime();
        ecritureComptable.setDate(date);
        manager.addReference(ecritureComptable);
        SequenceEcritureComptable sequenceExpected = getDaoProxy().getComptabiliteDao()
                .getSequenceEcriturecomptableByAnneeAndJournal(1952, ecritureComptable.getJournal().getCode());
        assertNotNull(sequenceExpected);
        assertEquals(java.util.Optional.of(1).get(),sequenceExpected.getDerniereValeur());
        assertEquals(ecritureComptable.getJournal().getCode() + "-" + 1952 + "/00001" , ecritureComptable.getReference());
    }
}
