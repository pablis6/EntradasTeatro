/**
 * 
 */
package view;


import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import org.apache.log4j.PropertyConfigurator;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JSpinnerDateEditor;

import controller.ControladorEntradas;

/**
 * @author Pablo
 *
 */
@SuppressWarnings("serial")
public class SeleccionSesion extends JFrame {
	private JLabel lblFecha, lblSesion;
	private JSpinner spinnerSesion;
	private JDateChooser dateChooser;
	private JButton btnComenzar;
	private SeleccionSesion seleccionSesion;
	private ControladorEntradas controllerEntradas;
	private JButton btnConf;
	private JButton btnGenerarInforme;
	private JCheckBox chckbxConNombre;
	
	public SeleccionSesion() {
		seleccionSesion = this;
		controllerEntradas = ControladorEntradas.getInstance();
		
		Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(new Dimension(450, 300));
		setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2 - (int)this.getSize().getWidth()/2, Toolkit.getDefaultToolkit().getScreenSize().height/2 - (int)this.getSize().getHeight()/2, (int)this.getSize().getWidth(), (int)this.getSize().getHeight());
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
		
		seleccionSesion.setTitle("Entradas Teatro La Mennais - Fecha");
		//icono APP
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage("img/icono-Mene-192x192.png");
		seleccionSesion.setIconImage(img);
		
		btnConf = new JButton();
		btnConf.setFocusPainted(false);
		btnConf.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConf.setBorderPainted(false);
		btnConf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Configuracion.getInstance().setVisible(true);
			}
		});
		btnConf.setContentAreaFilled(false);
		btnConf.setBorder(null);
		btnConf.setIcon(new ImageIcon("img/gear-48x48.png"));
		btnConf.setBounds(390, 11, 35, 35);
		getContentPane().add(btnConf);
		
		
		lblFecha = new JLabel("FECHA:");
		lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFecha.setBounds(87, 98, 50, 20);
		getContentPane().add(lblFecha);
		
		dateChooser = new JDateChooser(null, new Date(), null, new JSpinnerDateEditor());
		dateChooser.getCalendarButton().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateChooser.setDateFormatString("dd-MM-yyyy");
		dateChooser.setBounds(147, 98, 129, 20);
		getContentPane().add(dateChooser);
		
		lblSesion = new JLabel("SESIÓN:");
		lblSesion.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSesion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSesion.setBounds(87, 129, 50, 20);
		getContentPane().add(lblSesion);

		spinnerSesion = new JSpinner();
		spinnerSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		spinnerSesion.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinnerSesion.setBounds(147, 129, 129, 20);
		getContentPane().add(spinnerSesion);
		
		btnGenerarInforme = new JButton("Generar informe");
		btnGenerarInforme.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGenerarInforme.setBounds(8, 220, 120, 35);
//		getContentPane().add(btnGenerarInforme);
		
		chckbxConNombre = new JCheckBox("Con nombre");
		chckbxConNombre.setSelected(true);
		chckbxConNombre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chckbxConNombre.setBounds(328, 195, 137, 23);
		getContentPane().add(chckbxConNombre);
		
		btnComenzar = new JButton("Comenzar");
		btnComenzar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnComenzar.setBounds(328, 220, 100, 35);
		getContentPane().add(btnComenzar);
		
		btnComenzar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controllerEntradas.setConNombre(chckbxConNombre.isSelected());
				String fecha = Integer.toString(dateChooser.getCalendar().get(Calendar.YEAR))+ "_" +
						String.format("%02d", dateChooser.getCalendar().get(Calendar.MONTH)+1) + "_" +
						String.format("%02d", dateChooser.getCalendar().get(Calendar.DAY_OF_MONTH));

				Properties p = System.getProperties();
				p.put("log.name", fecha+"-"+spinnerSesion.getValue().toString());
				PropertyConfigurator.configure("properties/log4j.properties");
				//se crea un plano si no esta creado previamente.
				controllerEntradas.crearPlanoSiNoExiste(fecha, spinnerSesion.getValue().toString());
				try {
					controllerEntradas.leerPlano(fecha, spinnerSesion.getValue().toString());
					//se abre el plano de la fecha y sesion.
					PlanoTeatro2.getInstance().setVisible(true);
					seleccionSesion.setVisible(false);
				} catch (IOException e) {
					//TODO mensaje de error al leer los planos
				}
				
			}
		});
		
	}
}
