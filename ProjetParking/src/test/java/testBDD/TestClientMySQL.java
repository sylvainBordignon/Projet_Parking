package testBDD;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import mysql.ClientMysql;
import pojo.Client;

public class TestClientMySQL {
    @Test
    public void testVisualiserClient() {
    	ClientMysql client = ClientMysql.getInstance();
    	//Client c = new Client(5,"0633333333", "Dupont","Francois","3 rue de francois dupont","francoisdupont@email.fr","FR3333333333333333333333333");
    	Client test = client.visualierInfoClient(5);
        assertTrue(test.getNom().equals("Dupont"));
    }
    
    @Test
    public void testVisualiserClientNonExistant() {
    	ClientMysql client = ClientMysql.getInstance();
    	Client test = client.visualierInfoClient(999);
        assertNull(test);
    }
   
    @Test
    public void TestAjouterPlaque() {
    	String plaque = "ZZ-999-ZZ";
    	ClientMysql client = ClientMysql.getInstance();
    	client.ajouterVehicule(plaque, 2);
    	List<String> listes = client.selectionnerListeVehicules(2);
        assertTrue(listes.contains(plaque));
    }
    
    @Test
    public void TestSupprimerPlaque() {
    	String plaque = "ZZ-999-ZZ";
    	ClientMysql client = ClientMysql.getInstance();
    	client.supprimerVehicule(plaque, 2);
    	List<String> listes = client.selectionnerListeVehicules(2);
        assertFalse(listes.contains(plaque));
    }
}
