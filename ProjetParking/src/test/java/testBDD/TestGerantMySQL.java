package testBDD;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mysql.GerantMysql;
import pojo.Client;

public class TestGerantMySQL {
	
    @Test
    public void testNbPlacesSurreservation() {
    	GerantMysql gerant = GerantMysql.getInstance();
    	int nbPlacesOriginal = gerant.selectionnerNbPlaceSurreservation();
    	gerant.modifierNbPlaceSurreservation(10);
        int nbPlaces = gerant.selectionnerNbPlaceSurreservation();
        assertTrue(nbPlaces==10);
        gerant.modifierNbPlaceSurreservation(nbPlacesOriginal);
    }
    
    @Test
    public void testTarifNormal() {
    	GerantMysql gerant = GerantMysql.getInstance();
    	float tarifBase = gerant.selectionnerTarifNormal();
    	gerant.modifierTarifNormal(10);
        float tarif = gerant.selectionnerTarifNormal();
        assertTrue(tarif==10);
        gerant.modifierTarifDepassement(tarifBase);
    }
    
    @Test
    public void testTarifDepassement() {
    	GerantMysql gerant = GerantMysql.getInstance();
    	float tarifBase = gerant.selectionnerTarifDepassement();
    	gerant.modifierTarifDepassement(10);
        float tarif = gerant.selectionnerTarifDepassement();
        assertTrue(tarif==10);
        gerant.modifierTarifDepassement(tarifBase);
    }
    
    @Test
    public void testTarifProlongation() {
    	GerantMysql gerant = GerantMysql.getInstance();
    	float tarifBase = gerant.selectionnerTarifProlongationAttente();
    	gerant.modifierTarifProlongationAttente(10);
        float tarif = gerant.selectionnerTarifProlongationAttente();
        assertTrue(tarif==10);
        gerant.modifierTarifProlongationAttente(tarifBase);
    }
    
    @Test
    public void testVisualiserClient() {
    	GerantMysql gerant = GerantMysql.getInstance();
    	//Client c = new Client(5,"0633333333", "Dupont","Francois","3 rue de francois dupont","francoisdupont@email.fr","FR3333333333333333333333333");
    	Client test = gerant.visualierInfoClient(5);
        assertTrue(test.getNom().equals("Dupont"));
    }
    
    @Test
    public void testVisualiserClientNonExistant() {
    	GerantMysql gerant = GerantMysql.getInstance();
    	Client test = gerant.visualierInfoClient(999);
        assertNull(test);
    }
}
