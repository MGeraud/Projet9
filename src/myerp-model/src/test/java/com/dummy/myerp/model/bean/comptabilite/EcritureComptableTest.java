package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class EcritureComptableTest {

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                                     .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                                                                    vLibelle,
                                                                    vDebit, vCredit);
        return vRetour;
    }
    private EcritureComptable ecritureComptable;

    @Before
    public void setUp(){

        ecritureComptable = new EcritureComptable();
        ecritureComptable.setLibelle("Equilibrée");
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
    }

    @Test
    public void isEquilibree() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        //  Test avec ecriture comptable equilibree
        Assert.assertTrue(ecritureComptable.toString(), ecritureComptable.isEquilibree());

        //Test avec ecriture comptable non equilibrée
        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        Assert.assertFalse(vEcriture.toString(), vEcriture.isEquilibree());
    }

    @Test
    public void getTotalDebit() {
        BigDecimal totalExpected = new BigDecimal("341");
        Assert.assertTrue(totalExpected.compareTo(ecritureComptable.getTotalDebit()) == 0);
    }

    @Test
    public void getTotalCredit() {
        BigDecimal totalExpected = new BigDecimal("341");
        Assert.assertTrue(totalExpected.compareTo(ecritureComptable.getTotalCredit()) == 0);
    }
}
