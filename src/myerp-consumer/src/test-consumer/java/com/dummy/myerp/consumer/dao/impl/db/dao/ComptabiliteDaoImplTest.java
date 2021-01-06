package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ComptabiliteDaoImplTest extends ConsumerTestCase {



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
    public void loadListLigneEcriture() {
    }

    @Test
    public void insertEcritureComptable() {
    }

    @Test
    public void updateEcritureComptable() {
    }

    @Test
    public void deleteEcritureComptable() {
    }
}