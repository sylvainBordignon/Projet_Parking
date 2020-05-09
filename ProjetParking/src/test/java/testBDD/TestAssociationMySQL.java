package testBDD;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import mysql.AssociationMysql;

public class TestAssociationMySQL {
    @Test
    public void testPlaqueExistante() {
    	AssociationMysql association = AssociationMysql.getInstance();
        assertTrue(association.verificationPlaqueExiste("AA-000-AA"));
    }
    
    @Test
    public void testPlaqueNonExistante() {
    	AssociationMysql association = AssociationMysql.getInstance();
        assertFalse(association.verificationPlaqueExiste("PA-513-EN"));
    }
    
    @Test
    public void testClientsMemeVehicule() {
    	AssociationMysql association = AssociationMysql.getInstance();
    	ArrayList<Integer> liste = association.clientsUtilisantCeVehicule("AA-000-AA");
        assertTrue(liste.size()==2);
    }
}
