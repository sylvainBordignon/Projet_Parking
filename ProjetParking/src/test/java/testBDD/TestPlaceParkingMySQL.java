package testBDD;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mysql.PlaceParkingMysql;

public class TestPlaceParkingMySQL {

	@Test
	public void testVoirEtatPlace1() {
		PlaceParkingMysql place = PlaceParkingMysql.getInstance();
		String etat = place.visualiserEtatPlace(1);
		assertTrue(etat.equals("disponible"));
	}

	@Test
	public void testVoirEtatPlace2() {
		PlaceParkingMysql place = PlaceParkingMysql.getInstance();
		String etat = place.visualiserEtatPlace(2);
		assertTrue(etat.equals("occupée"));
	}
	
	@Test
	public void testTrouverPlaceLibre() {
		PlaceParkingMysql place = PlaceParkingMysql.getInstance();
		int idPlace = place.trouverPlaceDisponible();
		assertTrue(idPlace == 1);
	}

	@Test
	public void testVerifierPlacesDispo() {
		PlaceParkingMysql place = PlaceParkingMysql.getInstance();
		int nbPlaces = place.verifierNombrePlaceDispo();
		assertTrue(nbPlaces > 0);
	}

	@Test
	public void testVerifierPlaceNonPriseId() {
		PlaceParkingMysql place = PlaceParkingMysql.getInstance();
		boolean dispo = place.verifierPlaceNonPrise(1);
		assertTrue(dispo);
	}

	@Test
	public void testChangerEtatOccupee() {
		PlaceParkingMysql place = PlaceParkingMysql.getInstance();
		int res = place.changerEtatPlaceOccupee(10);
		assertTrue(res == 1);
		String etat = place.visualiserEtatPlace(10);
		assertTrue(etat.equals("occupée"));
	}

	@Test
	public void testChangerEtatReservee() {
		PlaceParkingMysql place = PlaceParkingMysql.getInstance();
		int res = place.changerEtatPlaceReservee(10);
		assertTrue(res == 1);
		String etat = place.visualiserEtatPlace(10);
		assertTrue(etat.equals("réservée"));
	}

	@Test
	public void testChangerEtatLibre() {
		PlaceParkingMysql place = PlaceParkingMysql.getInstance();
		int res = place.changerEtatPlaceLibre(10);
		assertTrue(res == 1);
		String etat = place.visualiserEtatPlace(10);
		assertTrue(etat.equals("disponible"));
	}
}
