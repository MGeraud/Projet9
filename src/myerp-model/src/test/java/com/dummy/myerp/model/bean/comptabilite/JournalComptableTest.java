package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class JournalComptableTest {

    private List<JournalComptable> journalComptableList;
    private final List<String> codes = Arrays.asList("AA" , "AB" , "AC" , "AD" , "AE");
    private final List<String> libellés = Arrays.asList("libellé0" , "libellé1" , "libellé2" , "libellé3" , "libellé4");

    @Before
    public void setUp() throws Exception {
        journalComptableList = new ArrayList<>();
        for (int i = 0 ; i < 5 ; i++){
            journalComptableList.add(new JournalComptable(codes.get(i), libellés.get(i)));
        }
    }

    @Test
    public void getByCode() {
        for (int i=0 ; i<5 ; i++){
            Assert.assertEquals(journalComptableList.get(i) , JournalComptable.getByCode(journalComptableList , codes.get(i)));
        }
    }

    @Test
    public void getByCodeNotExisting() {
            Assert.assertNull( JournalComptable.getByCode(journalComptableList , "NN"));

    }
}