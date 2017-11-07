/**
 * 
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Pablo
 *
 */
public class ModeloConfiguracion {

	Properties propiedades;
	/**
	 * Constructor
	 */
	public ModeloConfiguracion() {
		try {
			propiedades = new Properties();
			propiedades.load(new FileInputStream("properties" + File.separator + "app.properties"));
			
	//		System.setProperty("TAM_BUTACA", propiedades.getProperty("TAM_BUTACA", "25"));
	//		System.setProperty("ICONO_TEATRO", propiedades.getProperty("ICONO_TEATRO"));
	//		System.setProperty("ICONO_OBRA", propiedades.getProperty("ICONO_OBRA"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the propiedades
	 */
	public Properties getPropiedades() {
		return propiedades;
	}

	/**
	 * @param propiedades the propiedades to set
	 */
	public void setPropiedades(Properties propiedades) {
		this.propiedades = propiedades;
	}

	public void guardaConf() {
		try {
			propiedades.store(new FileOutputStream("properties" + File.separator + "app.properties"), "Propiedades para el teatro Menesiano");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
