package testBDD;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mysql.GerantMysql;

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
    public void testTarifDepassementAugmentation() {
    	GerantMysql gerant = GerantMysql.getInstance();
    	float tarifBase = gerant.selectionnerTarifDepassementAugmentation();
    	gerant.modifierTarifDepassementAugmentation(10);
        float tarif = gerant.selectionnerTarifDepassementAugmentation();
        assertTrue(tarif==10);
        gerant.modifierTarifDepassementAugmentation(tarifBase);
    }
}
