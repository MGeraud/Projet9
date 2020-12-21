package com.dummy.myerp.model.bean.comptabilite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompteComptableTest {

    private List<CompteComptable> compteComptableList ;
    private final List<String> libellés = Arrays.asList("libellé0" , "libellé1" , "libellé2" , "libellé3" , "libellé4");

    @Before
    public void setUp(){
        compteComptableList = new ArrayList<>();
        for (int i = 0 ; i < 5 ; i++){
            CompteComptable compteComptable = new CompteComptable(i  , libellés.get(i));
            compteComptableList.add(compteComptable);
        }
    }

    @Test
    public void getByExistingNumero() {
        for (int i = 0 ; i < 5 ; i++) {
            Assert.assertEquals(compteComptableList.get(i), CompteComptable.getByNumero(compteComptableList,i));
        }
    }

    @Test
    public void getBynonExistingNumero() {
        Assert.assertNull(CompteComptable.getByNumero(compteComptableList, 20));
    }
}