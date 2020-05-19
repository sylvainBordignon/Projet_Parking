package test;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import org.junit.Test;

import pojo.Reservation;

public class TestReservation {
								//A FINIR
	@Test
	public void testTempsDepassementInexistant() {
		Reservation reservation = new Reservation(1, 5, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,16,0,0,0), 330, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,15,0,0,0), 5, 30);
		assertTrue(reservation.calculerTempsDepassement() == 0);
	}
	
	@Test
	public void testTempsDepassement() {
		Reservation reservation = new Reservation(1, 5, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,16,0,0,0), 330, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,18,0,0,0), 5, 30);
		assertTrue(reservation.calculerTempsDepassement() == 120);
	}
}
