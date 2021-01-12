package com.dummy.myerp.testbusiness.business;

import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

public class ComptabiliteManagerImplIT extends BusinessTestCase{

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    private EcritureComptable ecritureComptable;

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
        ecritureComptable.setDate(date2019);
        ecritureComptable.setReference("AC-2019/00001");

        manager.checkEcritureComptable(ecritureComptable);
    }
}
