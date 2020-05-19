package test;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import org.junit.Test;

import pojo.Facturation;
import pojo.Reservation;

public class TestFacturation {

	@Test
	public void testFacturationCalculDepartAvantFinCoutProlongation() {
		Reservation reservation = new Reservation(1, 5, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,16,0,0,0), 330, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,15,0,0,0), 5, 30);
		assertTrue(Facturation.calculerCoutProlongationAttente(reservation) == 0);
	}
	
	@Test
	public void testFacturationCalculDepartAvantFinCoutNormal() {
		Reservation reservation = new Reservation(1, 5, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,16,0,0,0), 330, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,15,0,0,0), 5, 30);
		assertTrue(Facturation.calculerCoutNormal(reservation) == 55);
	}
	
	@Test
	public void testFacturationCalculDepartAvantFinCoutDepassement() {
		Reservation reservation = new Reservation(1, 5, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,16,0,0,0), 330, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,15,0,0,0), 5, 30);
		assertTrue(Facturation.calculerCoutDepassement(reservation) == 0);
	}
	
	@Test
	public void testFacturationCalculDepassementAvantFinCoutProlongation() {
		Reservation reservation = new Reservation(1, 5, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,16,0,0,0), 330, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,19,0,0,0), 5, 30);
		assertTrue(Facturation.calculerCoutProlongationAttente(reservation) == 0);
	}
	
	@Test
	public void testFacturationCalculDepartDepassementFinCoutNormal() {
		Reservation reservation = new Reservation(1, 5, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,16,0,0,0), 330, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,19,0,0,0), 5, 30);
		assertTrue(Facturation.calculerCoutNormal(reservation) == 55);
	}
	
	@Test
	public void testFacturationCalculDepartDepassementFinCoutDepassement() {
		Reservation reservation = new Reservation(1, 5, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,16,0,0,0), 330, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,19,0,0,0), 5, 30);
		assertTrue(Facturation.calculerCoutDepassement(reservation) == 36);
	}
	
	@Test
	public void testFacturationCalculProlongationAttenteCoutProlongation() {
		Reservation reservation = new Reservation(1, 5, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,16,0,0,0), 330, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,19,0,0,0), 5, 150);
		assertTrue(Facturation.calculerCoutProlongationAttente(reservation) == 5);
	}
	
	@Test
	public void testFacturationCalculProlongationAttenteCoutNormal() {
		Reservation reservation = new Reservation(1, 5, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,16,0,0,0), 330, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,19,0,0,0), 5, 150);
		assertTrue(Facturation.calculerCoutNormal(reservation) == 55);
	}
	
	@Test
	public void testFacturationCalculProlongationAttenteCoutDepassement() {
		Reservation reservation = new Reservation(1, 5, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,16,0,0,0), 330, new Timestamp(2020,5,20,10,30,0,0), new Timestamp(2020,5,20,19,45,0,0), 5, 150);
		assertTrue(Facturation.calculerCoutDepassement(reservation) == 48);//10+12+14+16*3/4
	}
}
