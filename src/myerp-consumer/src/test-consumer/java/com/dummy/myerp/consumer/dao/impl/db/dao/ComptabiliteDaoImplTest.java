package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ComptabiliteDaoImplTest extends ConsumerTestCase {



    @Test
    public void getListCompteComptable() {
        List<CompteComptable> compteComptableList = getDaoProxy().getComptabiliteDao().getListCompteComptable();
        Assert.assertNotNull(compteComptableList);
    }

    @Test
    public void getListJournalComptable() {
        List<JournalComptable> journalComptableList = getDaoProxy().getComptabiliteDao().getListJournalComptable();
        Assert.assertNotNull(journalComptableList);
    }

    @Test
    public void getListEcritureComptable() {
    }

    @Test
    public void getEcritureComptable() {
    }

    @Test
    public void getEcritureComptableByRef() {
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