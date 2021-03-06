package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ComptabiliteDaoImplTest extends ConsumerTestCase {


    private EcritureComptable ecritureComptable;
    private SequenceEcritureComptable sequenceEcritureComptable;

    @Before
    public void setUp(){

        ecritureComptable = new EcritureComptable();
        ecritureComptable.setLibelle("Equilibrée");
        ecritureComptable.setReference("AC-2019/00001");
        ecritureComptable.setJournal(new JournalComptable("AC" , "Achat"));
        ecritureComptable.setDate(new Date());

        sequenceEcritureComptable = new SequenceEcritureComptable();
        sequenceEcritureComptable.setJournalCode("AC");
        sequenceEcritureComptable.setAnnee(2019);
        sequenceEcritureComptable.setDerniereValeur(25);
    }

    @Test
    public void getListCompteComptable() {
        List<CompteComptable> compteComptableList = getDaoProxy().getComptabiliteDao().getListCompteComptable();
        assertFalse(compteComptableList.isEmpty());
    }

    @Test
    public void getListJournalComptable() {
        List<JournalComptable> journalComptableList = getDaoProxy().getComptabiliteDao().getListJournalComptable();
        assertFalse(journalComptableList.isEmpty());
    }

    @Test
    public void getListEcritureComptable() {
        List<EcritureComptable> ecritureComptableList = getDaoProxy().getComptabiliteDao().getListEcritureComptable();
        assertFalse(ecritureComptableList.isEmpty());
    }

    @Test
    public void getEcritureComptable() throws NotFoundException {
        EcritureComptable ecritureComptable = getDaoProxy().getComptabiliteDao().getEcritureComptable(-1);
        assertEquals("AC-2016/00001" , ecritureComptable.getReference());
    }

    @Test(expected = NotFoundException.class)
    public void getEcritureComptableByIdNotFound() throws NotFoundException {
        EcritureComptable ecritureComptable = getDaoProxy().getComptabiliteDao().getEcritureComptable(8);
    }

    @Test
    public void getEcritureComptableByRef() throws NotFoundException {
        EcritureComptable ecritureComptable = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef("BQ-2016/00003");
        assertEquals("Paiement Facture F110001" , ecritureComptable.getLibelle());
    }

    @Test(expected = NotFoundException.class)
    public void getEcritureComptableByRefNotFound() throws NotFoundException {
        EcritureComptable ecritureComptable = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef("AA-2015/00002");
    }


    @Test
    public void insertEcritureComptable() throws NotFoundException {
        getDaoProxy().getComptabiliteDao().insertEcritureComptable(ecritureComptable);
        assertNotNull(getDaoProxy().getComptabiliteDao().getEcritureComptableByRef("AC-2019/00001"));
    }

    @Test
    public void updateEcritureComptable() throws NotFoundException {
        EcritureComptable toUpdate = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef("AC-2019/00001");
        toUpdate.setLibelle("updated");
        getDaoProxy().getComptabiliteDao().updateEcritureComptable(toUpdate);
        assertEquals( "updated", getDaoProxy().getComptabiliteDao().getEcritureComptableByRef("AC-2019/00001").getLibelle() );
    }

    @Test(expected = NotFoundException.class)
    public void deleteEcritureComptable() throws NotFoundException {
        getDaoProxy().getComptabiliteDao().deleteEcritureComptable(1);
        getDaoProxy().getComptabiliteDao().getEcritureComptable(1);
    }

    @Test
    public void getSequenceEcriturecomptableByAnneeAndJournal() throws NotFoundException {
        SequenceEcritureComptable sequenceEcritureComptable = getDaoProxy().getComptabiliteDao().getSequenceEcriturecomptableByAnneeAndJournal(2016,"AC");
        assertNotNull(sequenceEcritureComptable);
        assertEquals((Integer) 40, sequenceEcritureComptable.getDerniereValeur());
    }

    @Test(expected = NotFoundException.class)
    public void getSequenceEcriturecomptableByAnneeAndJournalNotFound() throws NotFoundException {
        getDaoProxy().getComptabiliteDao().getSequenceEcriturecomptableByAnneeAndJournal(2018,"VV");
    }

    @Test
    public void insertSequenceEcritureComptable() throws NotFoundException {

        getDaoProxy().getComptabiliteDao().insertSequenceEcritureComptable(sequenceEcritureComptable);

        assertEquals(sequenceEcritureComptable.toString()
                , getDaoProxy().getComptabiliteDao().getSequenceEcriturecomptableByAnneeAndJournal(2019 , "AC").toString());
    }

    @Test
    public void updateSequenceEcritureComptable() throws NotFoundException {
        SequenceEcritureComptable toUpdate = sequenceEcritureComptable;
        toUpdate.setDerniereValeur(1522);
        getDaoProxy().getComptabiliteDao().updateSequenceEcritureComptable(toUpdate);

        assertEquals((Integer)1522 ,getDaoProxy().getComptabiliteDao()
                .getSequenceEcriturecomptableByAnneeAndJournal(sequenceEcritureComptable.getAnnee() , sequenceEcritureComptable.getJournalCode())
                .getDerniereValeur());
    }
}