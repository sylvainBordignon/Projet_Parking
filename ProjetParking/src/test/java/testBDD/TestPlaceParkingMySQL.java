package testBDD;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mysql.PlaceParkingMysql;

public class TestPlaceParkingMySQL {
    @Test
    public void testPlaceDispo() {
    	PlaceParkingMysql place = PlaceParkingMysql.getInstance();
    	int nbPlaces = place.verifierNombrePlaceDispo();
        assertTrue(nbPlaces>0);
    }
}
