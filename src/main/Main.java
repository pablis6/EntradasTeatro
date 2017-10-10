/**
 * 
 */
package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import view.PlanoTeatro;

/**
 * @author Pablo
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args)  {
		new PlanoTeatro().setVisible(true);
		try {
			leerFichero("src/plano.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}

	private static void leerFichero(String ruta) throws IOException{
		FileReader f=null;
		BufferedReader b=null;
		try {

			int cadena;
	        f = new FileReader(ruta);
			b = new BufferedReader(f);
	        while((cadena = b.read())!=-1) {
	        	if((char)cadena == 'P') {
	        		cadena = ' ';
	        	}
        		System.out.print((char)cadena);
	        }
	        f.close();
	        b.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            if (f != null) {
                f.close();
            }
            if (b != null) {
                b.close();
            }
        }
	}
}
