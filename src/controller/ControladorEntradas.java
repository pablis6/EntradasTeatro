/**
 * 
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.ModelEntradas;
import transfer.Butaca;

/**
 * @author Pablo
 *
 */
public class ControladorEntradas {

	private ModelEntradas modelEntradas;
	
	/**
	 * Contructor del controlador
	 */
	public ControladorEntradas() {
		modelEntradas = new ModelEntradas();
	}
	
	public ArrayList<List<Butaca>> obtenerPlano(){
		return modelEntradas.getPlano();
	}

	public void crearPlanoSiNoExiste(String fecha, String string) {
		modelEntradas.crearPlanoSiNoExiste(fecha, string);
	}

	public void leerPlano(String fecha, String sesion) throws IOException {
		modelEntradas.leerPlano(fecha, sesion);
	}
	
	
}
