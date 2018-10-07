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

	private static ControladorConfiguracion INSTANCE_CONTROLADORCONFIGURACION;
	private ModeloConfiguracion modeloConf;
	
	/**
	 * Constructor
	 */
	private ControladorConfiguracion() {
		modeloConf = ModeloConfiguracion.getInstance();
	}
	
	public static ControladorConfiguracion getInstance() {
		if(INSTANCE_CONTROLADORCONFIGURACION == null) {
			INSTANCE_CONTROLADORCONFIGURACION = new ControladorConfiguracion();
		}
		return INSTANCE_CONTROLADORCONFIGURACION;
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
