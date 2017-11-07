/**
 * 
 */
package main;

import java.io.IOException;

import controller.ControladorConfiguracion;
import view.SeleccionSesion;

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
		ControladorConfiguracion controladorConfiguracion = new ControladorConfiguracion();
		
		new SeleccionSesion(controladorConfiguracion).setVisible(true);
	}

	
}
