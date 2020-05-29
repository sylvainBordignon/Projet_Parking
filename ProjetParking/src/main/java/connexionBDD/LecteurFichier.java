package connexionBDD;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class LecteurFichier {
	private static final Logger logger = Logger.getLogger(LecteurFichier.class.getName());
	public String recupererInfoConnexionBdd(String url) throws IOException {
	
		// TODO Auto-generated method stub

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(url));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String res = sb.toString();
			return res.replaceAll("\\s+","");
			
		} finally {
			br.close();
		}

	}
}