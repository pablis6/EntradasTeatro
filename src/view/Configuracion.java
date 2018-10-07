/**
 * 
 */
package view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import controller.ControladorConfiguracion;

/**
 * @author Pablo
 *
 */
@SuppressWarnings("serial")
public class Configuracion extends JDialog {
	private JLabel lblTamanoButaca, lblIconoteatro, lblIconoObra, lblQr;
	private JTextField tfIconoTeatro, tfIconoObra, tfQrFaceBook;
	private JSpinner spinTamButaca;
	private JButton btnGuardar, btnIconoTeatro, btnIconoObra, btnQrFaceBook;
	private static Configuracion INSTANCE_CONFIGURACION;
	
	public static Configuracion getInstance() {
		if(INSTANCE_CONFIGURACION == null) {
			INSTANCE_CONFIGURACION = new Configuracion();
		}
		return INSTANCE_CONFIGURACION;
	}
	
	/**
	 * Constructor
	 */
	private Configuracion() {
		ControladorConfiguracion controladorConfiguracion = ControladorConfiguracion.getInstance();
		Configuracion yo = this;
		
		setModal(true);
		setSize(new Dimension(450, 300));
		setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2 - (int)this.getSize().getWidth()/2, Toolkit.getDefaultToolkit().getScreenSize().height/2 - (int)this.getSize().getHeight()/2, (int)this.getSize().getWidth(), (int)this.getSize().getHeight());
		getContentPane().setLayout(null);
		
		this.setTitle("Entradas Teatro La Mennais - Configuración");
		//icono APP
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage("img/icono-Mene-192x192.png");
		this.setIconImage(img);
		
		Properties properties = controladorConfiguracion.getPropiedades();
		
		lblTamanoButaca = new JLabel("Tamaño butaca");
		lblTamanoButaca.setBounds(23, 22, 86, 14);
		getContentPane().add(lblTamanoButaca);
		
		spinTamButaca = new JSpinner();
		spinTamButaca.setModel(new SpinnerNumberModel(new Integer(properties.getProperty("TAM_BUTACA")), 1, null, new Integer(1)));
		spinTamButaca.setBounds(119, 19, 43, 20);
		getContentPane().add(spinTamButaca);
		
		lblIconoteatro = new JLabel("Icono Teatro");
		lblIconoteatro.setBounds(23, 47, 86, 14);
		getContentPane().add(lblIconoteatro);
		
		tfIconoTeatro = new JTextField(properties.getProperty("ICONO_TEATRO"));
		tfIconoTeatro.setEditable(false);
		tfIconoTeatro.setBounds(119, 44, 215, 20);
		getContentPane().add(tfIconoTeatro);
		tfIconoTeatro.setColumns(10);
		
		btnIconoTeatro = new JButton(new ImageIcon("img/folder-search-23x23.png"));
		btnIconoTeatro.setFocusPainted(false);
		btnIconoTeatro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnIconoTeatro.setContentAreaFilled(false);
		btnIconoTeatro.setBorder(null);
		btnIconoTeatro.setMargin(new Insets(2, 2, 2, 2));
		btnIconoTeatro.setBounds(344, 43, 23, 23);
		getContentPane().add(btnIconoTeatro);
		btnIconoTeatro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fch = new JFileChooser("img");
				if(fch.showOpenDialog(yo) == JFileChooser.APPROVE_OPTION) {
					tfIconoTeatro.setText("img/" + fch.getSelectedFile().getName());
					properties.setProperty("ICONO_TEATRO", "img/" + fch.getSelectedFile().getName());
				}
			}
		});
		
		lblIconoObra = new JLabel("Icono Obra");
		lblIconoObra.setBounds(23, 73, 86, 14);
		getContentPane().add(lblIconoObra);
		
		tfIconoObra = new JTextField(properties.getProperty("ICONO_OBRA"));
		tfIconoObra.setEditable(false);
		tfIconoObra.setBounds(119, 70, 215, 20);
		getContentPane().add(tfIconoObra);
		tfIconoObra.setColumns(10);

		btnIconoObra = new JButton(new ImageIcon("img/folder-search-23x23.png"));
		btnIconoObra.setFocusPainted(false);
		btnIconoObra.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnIconoObra.setContentAreaFilled(false);
		btnIconoObra.setBorder(null);
		btnIconoObra.setMargin(new Insets(2, 2, 2, 2));
		btnIconoObra.setBounds(344, 69, 23, 23);
		getContentPane().add(btnIconoObra);
		btnIconoObra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fch = new JFileChooser("img");
				if(fch.showOpenDialog(yo) == JFileChooser.APPROVE_OPTION) {
					tfIconoObra.setText("img/" + fch.getSelectedFile().getName());
					properties.setProperty("ICONO_OBRA", "img/" + fch.getSelectedFile().getName());
				}
			}
		});

		lblQr = new JLabel("QR FaceBook");
		lblQr.setBounds(23, 101, 86, 14);
		getContentPane().add(lblQr);
		
		tfQrFaceBook = new JTextField(properties.getProperty("QR_FACEBOOK"));
		tfQrFaceBook.setEditable(false);
		tfQrFaceBook.setColumns(10);
		tfQrFaceBook.setBounds(119, 98, 215, 20);
		getContentPane().add(tfQrFaceBook);
		
		btnQrFaceBook = new JButton(new ImageIcon("img/folder-search-23x23.png"));
		btnQrFaceBook.setFocusPainted(false);
		btnQrFaceBook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnQrFaceBook.setContentAreaFilled(false);
		btnQrFaceBook.setBorder(null);
		btnQrFaceBook.setMargin(new Insets(2, 2, 2, 2));
		btnQrFaceBook.setBounds(344, 97, 23, 23);
		getContentPane().add(btnQrFaceBook);
		btnQrFaceBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fch = new JFileChooser("img");
				if(fch.showOpenDialog(yo) == JFileChooser.APPROVE_OPTION) {
					tfQrFaceBook.setText("img/" + fch.getSelectedFile().getName());
					properties.setProperty("QR_FACEBOOK", "img/" + fch.getSelectedFile().getName());
				}
			}
		});
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				properties.setProperty("TAM_BUTACA", spinTamButaca.getValue().toString());
				controladorConfiguracion.setPropiedades(properties);
				controladorConfiguracion.guardaConf();
				Configuracion.this.dispose();
			}
		});
		btnGuardar.setBounds(335, 216, 89, 35);
		getContentPane().add(btnGuardar);
		
		
	}
}
