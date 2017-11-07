/**
 * 
 */
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.ModelEntradas;
import transfer.Butaca;
import transfer.Zona;

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

	public void imprimir(List<Butaca> seleccionadas) {
		modelEntradas.imprimir(seleccionadas);
	}

	public int countFilas(Zona zona) {
		return modelEntradas.countFilas(zona);
	}

	public int countColumnas(Zona zona) {
		return modelEntradas.countColumnas(zona);
	}

	public void setButacaPlano(Butaca butaca) {
		modelEntradas.setButacaPlano(butaca);
	}
	
	public String getFecha() {
		return modelEntradas.getFecha();
	}
	
	public String getSesion() {
		return modelEntradas.getSesion();
	}

	public boolean isConNombre() {
		return modelEntradas.isConNombre();
	}
	
	public void setConNombre(boolean selected) {
		modelEntradas.setConNombre(selected);
	}

	public void setNombre(String nombre) {
		modelEntradas.setNombre(nombre);
	}

	public int getLibresPatio() {
		return modelEntradas.getLibresPatio();
	}
	
	public int getTotalPatio() {
		return modelEntradas.getTotalPatio();
	}
	public int getLibresEntresuelo() {
		return modelEntradas.getLibresEntresuelo();
	}
	
	public int getTotalEntresuelo() {
		return modelEntradas.getTotalEntresuelo();
	}

}
