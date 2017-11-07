/**
 * 
 */
package controller;

import java.util.Properties;

import model.ModeloConfiguracion;

/**
 * @author Pablo
 *
 */
public class ControladorConfiguracion {

	private ModeloConfiguracion modeloConf;
	
	/**
	 * Constructor
	 */
	public ControladorConfiguracion() {
		modeloConf = new ModeloConfiguracion();
	}
	
	/**
	 * @return the propiedades
	 */
	public Properties getPropiedades() {
		return modeloConf.getPropiedades();
	}
	
	/**
	 * @param propiedades the propiedades to set
	 */
	public void setPropiedades(Properties propiedades) {
		modeloConf.setPropiedades(propiedades);;
	}
	public void guardaConf() {
		modeloConf.guardaConf();
	}
}
