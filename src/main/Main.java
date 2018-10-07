/**
 * 
 */
package main;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
		if(!new File("properties").exists()) {
			JOptionPane.showMessageDialog(null, "La carpeta properties es necesaria para poder iniciar la aplicación", "Error, falta carpeta properties", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		if (!new File("plantillas").exists()) {
			JOptionPane.showMessageDialog(null, "La carpeta plantillas es necesaria para poder iniciar la aplicación", "Error falta carpeta plantillas", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		if (!new File("img").exists()) {
			JOptionPane.showMessageDialog(null, "La carpeta img es necesaria para poder iniciar la aplicación", "Error falta carpeta img", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		new File("planos").mkdir();
		new File("entradas").mkdir();
		
		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			
		}
		
		new SeleccionSesion().setVisible(true);
	}

	
}
