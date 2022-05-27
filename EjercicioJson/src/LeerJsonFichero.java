

import java.io.BufferedReader;
import java.io.FileReader;
import com.google.gson.Gson;


public class LeerJsonFichero {

	public static void main(String[] args) {
		
		String cadenaJson = leerFichero("C:\\xampp\\htdocs\\persona.json");
     
		Persona persona = new Gson().fromJson(cadenaJson, Persona.class);
		
		String nombre = persona.getNombre();
		System.out.println(nombre);
	}

	public static String leerFichero(String fichero) {
		String output = "";
		
		try {
			FileReader f = new FileReader(fichero);
	
	        BufferedReader b = new BufferedReader(f);

	        String cadenaLeida;
	        while ((cadenaLeida = b.readLine()) != null) {
	        	output += cadenaLeida;
	        }
	        b.close();
	        
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return output;		
	}
}
