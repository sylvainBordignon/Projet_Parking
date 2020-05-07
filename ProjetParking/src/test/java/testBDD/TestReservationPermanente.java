package testBDD;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Time;
import java.util.List;

import org.junit.jupiter.api.Test;

import mysql.ReservationPermanenteMysql;
import pojo.ReservationPermanente;

public class TestReservationPermanente {

	@Test
	public void testSelectionnerReservationPermanenteNonExistante() {
		ReservationPermanenteMysql reserv = ReservationPermanenteMysql.getInstance();
		ReservationPermanente res = reserv.selectionnerReservationPermanente(999);
		assertNull(res);
	}
	
	@Test
	public void testSelectionnerReservationsPermanentesClientAucuneReservation() {
		ReservationPermanenteMysql reserv = ReservationPermanenteMysql.getInstance();
		List<ReservationPermanente> res = reserv.selectionnerReservationsPermanentesClient(999);
		assertTrue(res.isEmpty());
	}
	
	@Test
	public void testAjoutReservationPermanente() {
		ReservationPermanenteMysql reserv = ReservationPermanenteMysql.getInstance();
		ReservationPermanente reservPerma = new ReservationPermanente(500, 500, "hebdomadaire", new Time(0), new Time(1,0,0), 2);
		assertTrue(reserv.ajoutReservationPermanente(reservPerma));
	}
	
	@Test
	public void testSelectionnerReservationsPermanentesClient() {
		ReservationPermanenteMysql reserv = ReservationPermanenteMysql.getInstance();
		List<ReservationPermanente> res = reserv.selectionnerReservationsPermanentesClient(3);
		assertTrue(res.get(0).getType().equals("hebdomadaire"));
		assertTrue(res.get(1).getType().equals("journalière"));
	}
	
	@Test
	public void testSelectionnerReservationPermanente() {
		ReservationPermanenteMysql reserv = ReservationPermanenteMysql.getInstance();
		ReservationPermanente res = reserv.selectionnerReservationPermanente(2);
		assertTrue(res.getType().equals("hebdomadaire"));
	}
	
	@Test
	public void testReservationPermanenteContigueFVrai(){
		ReservationPermanenteMysql reserv = ReservationPermanenteMysql.getInstance();
		ReservationPermanente reservPerma2 = new ReservationPermanente(3, "hebdomadaire", new Time(19,0,0), new Time(1,6,0), 2);
		assertNotNull(reserv.reservationContigue(reservPerma2));
	}
	
	@Test
	public void testReservationPermanenteContigueFaux(){
		ReservationPermanenteMysql reserv = ReservationPermanenteMysql.getInstance();
		ReservationPermanente reservPerma2 = new ReservationPermanente(3, "hebdomadaire", new Time(20,0,0), new Time(1,6,0), 2);
		assertNull(reserv.reservationContigue(reservPerma2));
	}
	
	@Test
	public void testSuppressionReservationPermanente() {
		ReservationPermanenteMysql reserv = ReservationPermanenteMysql.getInstance();
		assertTrue(reserv.suppressionReservationPermanente(500)==1);
	}
	
	@Test
	public void testSuppressionReservationPermanenteNonExistante(){
		ReservationPermanenteMysql reserv = ReservationPermanenteMysql.getInstance();
		assertTrue(reserv.suppressionReservationPermanente(999)!=1);
	}
	
	@Test
	public void testFusionReservationPermanenteContigue(){		
		ReservationPermanenteMysql reserv = ReservationPermanenteMysql.getInstance();
		ReservationPermanente reservPerma1 = new ReservationPermanente(555, 900, "hebdomadaire", new Time(10,0,0), new Time(2,0,0), 2);
		ReservationPermanente reservPerma2 = new ReservationPermanente(900, "hebdomadaire", new Time(13,0,0), new Time(2,0,0), 2);
		reserv.ajoutReservationPermanente(reservPerma1);
		assertNotNull(reserv.reservationContigue(reservPerma2));
		assertTrue(reserv.fusionDeuxReservationsContigue(reservPerma1, reservPerma2));
		assertTrue(reserv.selectionnerReservationPermanente(555).getDuree().getHours() == 5);
		reserv.suppressionReservationPermanente(555);
	}
}
