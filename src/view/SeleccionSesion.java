/**
 * 
 */
package view;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

import com.toedter.calendar.JCalendar;

import controller.ControladorEntradas;

import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JSpinnerDateEditor;

import java.awt.ComponentOrientation;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.Rectangle;

/**
 * @author Pablo
 *
 */
public class SeleccionSesion extends JFrame {
	private JLabel lblFecha, lblSesion;
	private JSpinner spinnerSesion;
	private JDateChooser dateChooser;
	private JButton btnComenzar;
	private SeleccionSesion seleccionSesion;
	private ControladorEntradas controller;
	
	public SeleccionSesion() {
		seleccionSesion = this;
		controller = new ControladorEntradas();
		
		Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2 - 450/2, Toolkit.getDefaultToolkit().getScreenSize().height/2 - 300/2, 450, 300);
		setResizable(false);
		getContentPane().setLayout(null);

		//operacion de cierre de ventana
		seleccionSesion.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		seleccionSesion.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent evt ) {
        		if(JOptionPane.showConfirmDialog(SeleccionSesion.this, "¿Quieres salir?", "Confirmacion de salida", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					System.exit(0);
        		}
        	}
		});
		
		seleccionSesion.setTitle("Entradas Teatro La Menais - Fecha");
		//icono APP
		URL pathIcon = seleccionSesion.getClass().getClassLoader().getResource("img/icono-Mene-192x192.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(pathIcon);
		seleccionSesion.setIconImage(img);
		
		
		lblFecha = new JLabel("FECHA:");
		lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFecha.setBounds(87, 98, 50, 20);
		getContentPane().add(lblFecha);
		
//		JDateChooser dateChooser = 
		dateChooser = new JDateChooser(null, new Date(), null, new JSpinnerDateEditor());
		dateChooser.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateChooser.setDateFormatString("dd-MM-yyyy");
		dateChooser.setBounds(147, 98, 100, 20);
		getContentPane().add(dateChooser);
		
		lblSesion = new JLabel("SESIÓN:");
		lblSesion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSesion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSesion.setBounds(87, 129, 50, 20);
		getContentPane().add(lblSesion);

		spinnerSesion = new JSpinner();
		spinnerSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		spinnerSesion.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerSesion.setBounds(147, 129, 100, 20);
		getContentPane().add(spinnerSesion);
		
		btnComenzar = new JButton("Comenzar");
		btnComenzar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnComenzar.setBounds(334, 226, 100, 35);
		getContentPane().add(btnComenzar);
		
		btnComenzar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fecha = Integer.toString(dateChooser.getCalendar().get(Calendar.YEAR))+ "_" +
						Integer.toString(dateChooser.getCalendar().get(Calendar.MONTH)+1) + "_" +
						Integer.toString(dateChooser.getCalendar().get(Calendar.DAY_OF_MONTH));
				//se crea un plano si no esta creado previamente.
				controller.crearPlanoSiNoExiste(fecha, spinnerSesion.getValue().toString());
				try {
					controller.leerPlano(fecha, spinnerSesion.getValue().toString());
				} catch (IOException e) {
					//TODO mensaje de error al leer los planos
				}
				//se abre el plano de la fecha y sesion.
				new PlanoTeatro(controller).setVisible(true);
				seleccionSesion.setVisible(false);
			}
		});
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
