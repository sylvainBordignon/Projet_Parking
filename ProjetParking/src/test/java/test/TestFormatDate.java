package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import verificationsentreeclavier.MethodesVerificationsDate;

public class TestFormatDate {
    @Test
    public void testFormatDate() {
    	MethodesVerificationsDate methodes = new MethodesVerificationsDate();
    	assertTrue(methodes.estValideDate("01/01/2100"));
    }
    
    @Test
    public void testFormatDateInferieurDateJour() {
    	MethodesVerificationsDate methodes = new MethodesVerificationsDate();
    	assertFalse(methodes.estValideDate("01/01/1999"));
    }
    
    @Test
    public void testFormatDateNonValideFevrier() {
    	MethodesVerificationsDate methodes = new MethodesVerificationsDate();
    	assertFalse(methodes.estValideDate("29/02/3001"));
    }
    
    @Test
    public void testFormatDateNonValide1() {
    	MethodesVerificationsDate methodes = new MethodesVerificationsDate();
    	assertFalse(methodes.estValideDate("32/03/3000"));
    }
    
    @Test
    public void testFormatDateNonValide2() {
    	MethodesVerificationsDate methodes = new MethodesVerificationsDate();
    	assertFalse(methodes.estValideDate("30-03-3000"));
    }
    
    @Test
    public void testFormatDateNonValide3() {
    	MethodesVerificationsDate methodes = new MethodesVerificationsDate();
    	assertFalse(methodes.estValideDate("0/1/2400"));
    }
    
    @Test
    public void testFormatHeure() {
    	MethodesVerificationsDate methodes = new MethodesVerificationsDate();
    	assertTrue(methodes.estValideHeureMinute("15:45"));
    }
    
    @Test
    public void testFormatHeureNonValide1() {
    	MethodesVerificationsDate methodes = new MethodesVerificationsDate();
    	assertFalse(methodes.estValideHeureMinute("25:45"));
    }
    
    @Test
    public void testFormatHeureNonValide2() {
    	MethodesVerificationsDate methodes = new MethodesVerificationsDate();
    	assertFalse(methodes.estValideHeureMinute("12:61"));
    }
    
    @Test
    public void testFormatHeureMemeJourApres() {
    	MethodesVerificationsDate methodes = new MethodesVerificationsDate();
    	assertTrue(methodes.estValideHeureMinuteMemeJour("23:59"));
    }
    
    @Test
    public void testFormatHeureMemeJourAvant() {
    	MethodesVerificationsDate methodes = new MethodesVerificationsDate();
    	assertFalse(methodes.estValideHeureMinuteMemeJour("00:01"));
    }
}
