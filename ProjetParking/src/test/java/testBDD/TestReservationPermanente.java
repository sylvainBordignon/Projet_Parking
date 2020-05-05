package testBDD;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
		ReservationPermanente reservPerma = new ReservationPermanente(500, 500, "hebdomadaire", new Time(0), 60, 2, null);
		assertTrue(reserv.ajoutReservationPermanente(reservPerma));

	}
	
	@Test
	public void testSelectionnerReservationsPermanentesClient() {
		ReservationPermanenteMysql reserv = ReservationPermanenteMysql.getInstance();
		List<ReservationPermanente> res = reserv.selectionnerReservationsPermanentesClient(3);
		assertTrue(res.get(0).getType().equals("hebdomadaire"));
	}
	
	@Test
	public void testSelectionnerReservationPermanente() {
		ReservationPermanenteMysql reserv = ReservationPermanenteMysql.getInstance();
		ReservationPermanente res = reserv.selectionnerReservationPermanente(2);
		assertTrue(res.getType().equals("hebdomadaire"));
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
}
