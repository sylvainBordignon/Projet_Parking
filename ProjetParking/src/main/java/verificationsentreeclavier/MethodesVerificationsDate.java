package verificationsentreeclavier;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MethodesVerificationsDate {

	
	  public boolean estValideDate(String dateUtilisateur)  {

	  
	        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        sdf.setLenient(false);
	        String date = sdf.format(new Date());
	        
	        try {
	            sdf.parse(dateUtilisateur);
	        } catch (ParseException e) {
	            return false;
	        }
	     // vérifier si dateUtilisateur > date du jour	
	        Date dateParseUtilisateur = null;
	        Date dateParseDateCourante=null;
			try {
				dateParseUtilisateur = sdf.parse(dateUtilisateur);
				dateParseDateCourante= sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     
	        if (dateParseUtilisateur.compareTo(dateParseDateCourante) < 0) {
	            System.out.println("Notre système ne permet seulement de visualiser les places disponibles dans le futur. ");
	           return false; 
	        }
	        
	        System.out.println("OKL");
	        return true;
	    }
	  
	  public boolean estValideHeureMinute(String dateUtilisateur) {

	        DateFormat sdf = new SimpleDateFormat("HH:mm");
	        sdf.setLenient(false);
	        try {
	            sdf.parse(dateUtilisateur);
	        } catch (ParseException e) {
	            return false;
	        }
	        System.out.println("OK");
	        return true;
	    }
	  
	  
	  public boolean estValideHeureMinuteMemeJour(String dateUtilisateur) {

	        DateFormat sdf = new SimpleDateFormat("HH:mm");
	        sdf.setLenient(false);
	        String date = sdf.format(new Date());
	            
	        
	        
	        Date dateParseUtilisateur = null;
	        Date dateParseDateCourante=null;
			try {
				dateParseUtilisateur = sdf.parse(dateUtilisateur);
				dateParseDateCourante= sdf.parse(date);
			} catch (ParseException e) {
	         return false;
			}
	        
			if(dateParseUtilisateur.before(dateParseDateCourante)) {
			
			System.out.println("Notre système ne permet seulement de visualiser les places disponibles dans le futur. ");
			return false;	
			}
	        
	        System.out.println("OK");
	        return true;
	    }
	  
	  
	}	
	
	

