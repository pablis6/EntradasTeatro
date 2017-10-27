/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import controller.ControladorEntradas;
import transfer.Butaca;
import transfer.Estado;
import transfer.Zona;

/**
 * @author Pablo
 *
 */
public class PlanoTeatro2 extends JFrame {
	private JScrollPane scrollPlano;
	private JPanel panelPlanoTeatro, panelPatioButacas, panelEntresuelo, panelEscenario;
	private JLabel lblEscenario;
	private JPopupMenu menuOpciones;
	private JMenuItem menuItemDesocupar, menuItemEstrop_Arreg;
	private JButton btnImprimir;
	private List<Butaca> seleccionadas = new ArrayList<Butaca>();
	private List<JButton> botones = new ArrayList<JButton>();
	
	public PlanoTeatro2(ControladorEntradas controladorEntradas) {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setSize(new Dimension(1382, 885));
		//operacion de cierre de ventana
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent evt ) {
        		if(JOptionPane.showConfirmDialog(PlanoTeatro2.this, "�Quieres salir?", "Confirmacion de salida", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					System.exit(0);
        		}
        	}
		});
		
		this.setTitle("Entradas Teatro La Menais - Seleccion butaca");
		//icono APP
		URL pathIcon = this.getClass().getClassLoader().getResource("img/icono-Mene-192x192.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(pathIcon);
		this.setIconImage(img);
	
		GridBagLayout gbl_panelPrincipal = new GridBagLayout();
		gbl_panelPrincipal.columnWidths = new int[] {0};
		gbl_panelPrincipal.rowHeights = new int[] {0, 82};
		gbl_panelPrincipal.columnWeights = new double[]{1.0};
		gbl_panelPrincipal.rowWeights = new double[]{1.0, 0.0};
		getContentPane().setLayout(gbl_panelPrincipal);
		
		scrollPlano = new JScrollPane();
		scrollPlano.setAutoscrolls(true);
		GridBagConstraints gbc_scrollPlano = new GridBagConstraints();
		gbc_scrollPlano.insets = new Insets(10, 10, 10, 10);
		gbc_scrollPlano.fill = GridBagConstraints.BOTH;
		gbc_scrollPlano.gridx = 0;
		gbc_scrollPlano.gridy = 0;
		getContentPane().add(scrollPlano, gbc_scrollPlano);
		
		panelPlanoTeatro = new JPanel();
		panelPlanoTeatro.setAutoscrolls(true);
		panelPlanoTeatro.setBackground(new Color(255, 153, 0));
		scrollPlano.setViewportView(panelPlanoTeatro);
		panelPlanoTeatro.setLayout(null);
		
//		int maxFilasE = 10;
//		int maxColsE = 40;
//		int maxFilasP = 20;
//		int maxColsP = 40;
//		int tamBoton = 40;

		int MAX_COL_P = 0;
		int MAX_FIL_P = 0;
		int MAX_COL_E = 0;
		int MAX_FIL_E = 0;
		int tamBoton = 25;
		int tamBotonEspacio = 27;
		
		panelPatioButacas = new JPanel();
		panelPatioButacas.setBackground(new Color(255, 153, 204));
//		panelPatioButacas.setBounds(0, 0, maxColsP*tamBoton, maxFilasP*tamBoton);
		panelPatioButacas.setLayout(null);
		panelPlanoTeatro.add(panelPatioButacas);
		
		panelEntresuelo = new JPanel();
		panelEntresuelo.setBackground(new Color(102, 255, 0));
//		panelEntresuelo.setBounds((maxColsP+1)*tamBoton, 0, maxColsE*tamBoton, maxFilasE*tamBoton);
		panelEntresuelo.setLayout(null);
		panelPlanoTeatro.add(panelEntresuelo);
		
		//carga de plano
		List<List<Butaca>> plano = controladorEntradas.obtenerPlano();
		int idxfil = 0;
		Butaca butacaAnterior = null;
		for (List<Butaca> fila : plano) {
			int idxcol = 0;
			for (Butaca butaca : fila) {
				// ponemos el contador de filas a 0 si cambia de patio a entresuelo
				if(butacaAnterior != null && butacaAnterior.getZona() == Zona.PATIO_BUTACAS && butaca.getZona() == Zona.ENTRESUELO) {
					idxfil = 0;
				}
				JButton boton = new JButton();
				boton.setContentAreaFilled(false);
				boton.setName(butaca.getEstado().getDescripcion());
				
				if(butaca.getEstado() == Estado.PASILLO) {//cuando es pasillo ponemos el numero de fila
					boton.setText(Integer.toString(butaca.getFila()));
				}
				else if(butaca.getEstado() == Estado.VACIO){//cuando es vacio no ponemos ni texto ni butaca.
				}
				else{
					Image icono = new ImageIcon(getClass().getResource(butaca.getEstado().getImg())).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					boton.setIcon(new ImageIcon(icono));
					
					menuOpciones = new JPopupMenu();
					
					menuItemDesocupar = new JMenuItem("Desocupar");
					menuItemDesocupar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							if(JOptionPane.showConfirmDialog(PlanoTeatro2.this, "Esta butaca esta ocupada por otra persona. �Quieres desocuparla?", "�Desocupar butaca?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
								butaca.setEstado(Estado.LIBRE);
								Image icono = new ImageIcon(getClass().getResource(butaca.getEstado().getImg())).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
								boton.setIcon(new ImageIcon(icono));
							}
							System.out.println("soy la opcion de menu del boton" + "zona: " + butaca.getZona() + " fila: " + butaca.getFila() + " butaca: " + butaca.getButaca());
							
						}
					});
					
					menuItemEstrop_Arreg = new JMenuItem("Estropeada/Arreglada");
					menuItemEstrop_Arreg.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							if(butaca.getEstado() == Estado.ESTROPEADA) {
								butaca.setEstado(Estado.LIBRE);
								System.out.println("zona: " + butaca.getZona() + " fila: " + butaca.getFila() + " butaca: " + butaca.getButaca());
							}
							else {
								butaca.setEstado(Estado.ESTROPEADA);
								System.out.println("zona: " + butaca.getZona() + " fila: " + butaca.getFila() + " butaca: " + butaca.getButaca());
							}
							boton.setName(butaca.getEstado().getDescripcion());
							Image icono = new ImageIcon(getClass().getResource(butaca.getEstado().getImg())).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
							boton.setIcon(new ImageIcon(icono));
							
						}
					});

					menuOpciones.add(menuItemDesocupar);
					menuOpciones.add(menuItemEstrop_Arreg);
					addPopup(boton, menuOpciones, butaca);
				}
				
				boton.setBorder(null);
				boton.setBounds(tamBotonEspacio+(idxcol*tamBotonEspacio), tamBotonEspacio+(idxfil*tamBotonEspacio), tamBoton, tamBoton);
				if(butaca.getZona() == Zona.PATIO_BUTACAS) {panelPatioButacas.add(boton);}
				if(butaca.getZona() == Zona.ENTRESUELO) {panelEntresuelo.add(boton);}
				
				//evento click izquierdo
				boton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {						
						if(butaca.getEstado() == Estado.LIBRE) {
							butaca.setEstado(Estado.SELECCIONADA);
							seleccionadas.add(butaca);
						}
						else if(butaca.getEstado() == Estado.SELECCIONADA) {
							butaca.setEstado(Estado.LIBRE);
							seleccionadas.removeIf((Butaca b ) -> b.getButaca() == butaca.getButaca() && b.getFila() == butaca.getFila() && b.getZona() == butaca.getZona());
						}
						if(butaca.getEstado() == Estado.OCUPADA) {
							JOptionPane.showMessageDialog(PlanoTeatro2.this, "Esta butaca esta ocupada por otra persona. Tienes que vaciarla primero", "Butaca ocupada", JOptionPane.ERROR_MESSAGE);
						}
						boton.setName(butaca.getEstado().getDescripcion());
						Image icono = new ImageIcon(getClass().getResource(butaca.getEstado().getImg())).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
						boton.setIcon(new ImageIcon(icono));
						System.out.println("zona: " + butaca.getZona() + " fila: " + butaca.getFila() + " butaca: " + butaca.getButaca());
						
						
//						Collections.sort(seleccinadas, Butaca.butacaComparator);
//						System.out.println("Butacas seleccionadas: " + seleccinadas);
						
					}
				});
				
				botones.add(boton);
				
				idxcol++;
				butacaAnterior = butaca;
			}
			idxfil++;
		}
		
		//calculo de filas y columnas
		MAX_FIL_P = controladorEntradas.countFilas(Zona.PATIO_BUTACAS);
		MAX_FIL_E = controladorEntradas.countFilas(Zona.ENTRESUELO);
		MAX_COL_P = controladorEntradas.countColumnas(Zona.PATIO_BUTACAS);
		MAX_COL_E = controladorEntradas.countColumnas(Zona.ENTRESUELO);
		
		//tama�os de los paneles
		panelPatioButacas.setBounds(0, 0, (2+MAX_COL_P)*tamBotonEspacio, (2+MAX_FIL_P)*tamBotonEspacio);
		panelEntresuelo.setBounds(panelPatioButacas.getWidth(), 0, (2+MAX_COL_E)*tamBotonEspacio, (2+MAX_FIL_E)*tamBotonEspacio);
		
		panelPlanoTeatro.setPreferredSize(new Dimension(panelPatioButacas.getWidth() + panelEntresuelo.getWidth(), panelPatioButacas.getHeight()));
		
		panelEscenario = new JPanel();
		GridBagConstraints gbc_panelEscenario = new GridBagConstraints();
		gbc_panelEscenario.fill = GridBagConstraints.BOTH;
		gbc_panelEscenario.insets = new Insets(0, 0, 5, 10);
		gbc_panelEscenario.gridx = 0;
		gbc_panelEscenario.gridy = 1;
		getContentPane().add(panelEscenario, gbc_panelEscenario);
		
		GridBagLayout gbl_PanelSecundario = new GridBagLayout();
		gbl_PanelSecundario.columnWidths = new int[] {1282, 100};
		gbl_PanelSecundario.rowHeights = new int[]{82, 0};
		gbl_PanelSecundario.columnWeights = new double[]{1.0, 0.0};
		gbl_PanelSecundario.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelEscenario.setLayout(gbl_PanelSecundario);
		
		lblEscenario = new JLabel("ESCENARIO");
		GridBagConstraints gbc_lblEscenario = new GridBagConstraints();
		gbc_lblEscenario.gridx = 0;
		gbc_lblEscenario.gridy = 0;
		panelEscenario.add(lblEscenario, gbc_lblEscenario);
		lblEscenario.setFont(new Font("Tahoma", Font.BOLD, 68));
		
		btnImprimir = new JButton("Imprimir");
		btnImprimir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImprimir.setBorder(UIManager.getBorder("Button.border"));
		btnImprimir.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_btnImprimir = new GridBagConstraints();
		gbc_btnImprimir.fill = GridBagConstraints.BOTH;
		gbc_btnImprimir.gridx = 1;
		gbc_btnImprimir.gridy = 0;
		panelEscenario.add(btnImprimir, gbc_btnImprimir);
	}
	
private static void addPopup(Component component, final JPopupMenu popup, Butaca butaca) {
		
		
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
				//opcion para desocupar butaca
				if(butaca.getEstado() == Estado.OCUPADA) { //si esta ocupada, mostramos opcion de menu
					popup.getComponent(0).setEnabled(true);
				}
				else {//si no, se muestra deshabilitada
					popup.getComponent(0).setEnabled(false);
				}
				
				//opcion para marcar como estropeada o arreglada una butaca
				if(butaca.getEstado() == Estado.ESTROPEADA || butaca.getEstado() == Estado.LIBRE) { //si esta estropeada o esta libre, mostramos opcion de menu
					popup.getComponent(1).setEnabled(true);
				}
				else {//si no, se muestra deshabilitada
					popup.getComponent(1).setEnabled(false);
				}
				
				
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}