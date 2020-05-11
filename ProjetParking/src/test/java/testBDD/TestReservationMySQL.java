package testBDD;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mysql.ClientMysql;
import pojo.Reservation;

public class TestReservationMySQL {
//    @Test
//    public void testVisualiserReservation() {
//    	ClientMysql client = ClientMysql.getInstance();
//    	Reservation test = client.visualiserReservationMemeJour(9);
//        assertTrue(test.getDuree() == 600);
//    }
    
    @Test
    public void testVisualiserReservationtNonExistante() {
    	ClientMysql client = ClientMysql.getInstance();
    	Reservation test = client.visualiserReservationMemeJour(999);
        assertNull(test);
    }
}
