package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import verificationsentreeclavier.MethodesVerificationsAjoutClient;

public class TestFormatClient {
    @Test
    public void testFormatNom() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertTrue(methodes.formatNom("Gérardin"));
    }
    
    @Test
    public void testFormatNomEspace() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertTrue(methodes.formatNom("De maison"));
    }
    
    @Test
    public void testFormatNomIncorrectVide() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatNom(""));
    }
    
    @Test
    public void testFormatNomIncorrectTropLong() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatNom("Nomquiestbeaucouptroplongaecrire"));
    }
    
    @Test
    public void testFormatNomIncorrect1() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatNom("Jean#"));
    }
    
    @Test
    public void testFormatNomIncorrect2() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatNom("Jean33"));
    }
    
    @Test
    public void testFormatPrenom() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertTrue(methodes.formatPrenom("Jean"));
    }
    
    @Test
    public void testFormatPrenomIncorrectVide() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatPrenom(""));
    }
    
    @Test
    public void testFormatPrenomIncorrectTropLong() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatPrenom("Prenomquiestbeaucouptroplongaecrire"));
    }
    
    @Test
    public void testFormatPrenomIncorrect1() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatPrenom("Jean#"));
    }
    
    @Test
    public void testFormatPrenomIncorrect2() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatPrenom("Jean33"));
    }
    
    @Test
    public void testFormatAdresse() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertTrue(methodes.formatAdresse("1 rue des palmiers"));
    }
    
    @Test
    public void testFormatAdresse2() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertTrue(methodes.formatAdresse("1 bis rue de l'abbé"));
    }
    
    @Test
    public void testFormatAdresseIncorrectTropLong() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatAdresse("1 qui fait plus de cinquante caractères beaucoup trop long"));
    }
    
    @Test
    public void testFormatAdresseIncorrect() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatAdresse("1# rue de palmiers"));
    }
    
    @Test
    public void testFormatIban() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertTrue(methodes.formatIban("FR54546542131234123412"));
    }
    
    @Test
    public void testFormatIbanIncorrectVide() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatIban(""));
    }
    
    @Test
    public void testFormatIbanIncorrectTropLong() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatIban("FR5454654213123412341212"));
    }
    
    @Test
    public void testFormatIbanIncorrect() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatIban("45454654213123412341212"));
    }
    
    @Test
    public void testFormatMobile() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertTrue(methodes.formatMobile("0658142355"));
    }
    
    @Test
    public void testFormatMobileIncorrectTropLong() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatMobile("065814235545"));
    }
    
    @Test
    public void testFormatMobileIncorrectVide() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatMobile(""));
    }
    
    @Test
    public void testFormatMobileIncorrect1() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatMobile("4446test"));
    }
    
    @Test
    public void testFormatMobileIncorrect2() {
    	MethodesVerificationsAjoutClient methodes = new MethodesVerificationsAjoutClient();
    	assertFalse(methodes.formatMobile("0125465932"));
    }
}
