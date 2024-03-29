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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import controller.ControladorConfiguracion;
import controller.ControladorEntradas;
import net.sf.jasperreports.engine.JRException;
import transfer.Butaca;
import transfer.Estado;
import transfer.Zona;

/**
 * @author Pablo
 *
 */
@SuppressWarnings("serial")
public class PlanoTeatro2 extends JFrame {
	private static PlanoTeatro2 INSTANCE_PLANOTEATRO;
	private ControladorEntradas controladorEntradas;
	private JScrollPane scrollPlano;
	private JPanel panelPlanoTeatro, panelPatioButacas, panelEntresuelo, panelEscenario;
	private JLabel lblEscenario;
	private JPopupMenu menuOpciones;
	private JMenuItem menuItemDesocupar, menuItemEstrop_Arreg;
	private JButton btnImprimir, btnOcuparSinImprimir;
	private List<Butaca> seleccionadas = new ArrayList<Butaca>();
	
	private int MAX_COL_P = 0;
	private int MAX_FIL_P = 0;
	private int MAX_COL_E = 0;
	private int MAX_FIL_E = 0;
	private int tamBoton;
	private int tamBotonEspacio = 0;
	
	public static PlanoTeatro2 getInstance() {
		if(INSTANCE_PLANOTEATRO == null) {
			INSTANCE_PLANOTEATRO = new PlanoTeatro2();
		}
		return INSTANCE_PLANOTEATRO;
	}
	
	/**
	 * Constructor
	 */
	private PlanoTeatro2() {
		controladorEntradas = ControladorEntradas.getInstance();
		ControladorConfiguracion controladorConfiguracion = ControladorConfiguracion.getInstance();
		
		tamBoton = Integer.parseInt(controladorConfiguracion.getPropiedades().getProperty("TAM_BUTACA"));
		tamBotonEspacio = tamBoton+2;
		
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
		
		this.setTitle("Entradas Teatro La Mennais - Seleccion butaca " + controladorEntradas.getFecha() + "-" + controladorEntradas.getSesion());
		//icono APP
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage("img/icono-Mene-192x192.png");
		this.setIconImage(img);
	
		GridBagLayout gbl_panelPrincipal = new GridBagLayout();
		gbl_panelPrincipal.columnWidths = new int[] {0};
		gbl_panelPrincipal.rowHeights = new int[] {0, 62};
		gbl_panelPrincipal.columnWeights = new double[]{1.0};
		gbl_panelPrincipal.rowWeights = new double[]{1.0, 0.0};
		getContentPane().setLayout(gbl_panelPrincipal);
		
		scrollPlano = new JScrollPane();
		scrollPlano.getVerticalScrollBar().setUnitIncrement(10);
		scrollPlano.getHorizontalScrollBar().setUnitIncrement(10);
		GridBagConstraints gbc_scrollPlano = new GridBagConstraints();
		gbc_scrollPlano.insets = new Insets(10, 10, 10, 10);
		gbc_scrollPlano.fill = GridBagConstraints.BOTH;
		gbc_scrollPlano.gridx = 0;
		gbc_scrollPlano.gridy = 0;
		getContentPane().add(scrollPlano, gbc_scrollPlano);
		
		panelPlanoTeatro = new JPanel();
//		panelPlanoTeatro.setBackground(new Color(192, 192, 192));
		//panelPlanoTeatro.setBackground(new Color(255, 153, 0));
		scrollPlano.setViewportView(panelPlanoTeatro);
		panelPlanoTeatro.setLayout(null);
		
		panelPatioButacas = new JPanel();
		panelPatioButacas.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 3, true), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		//panelPatioButacas.setBackground(new Color(255, 153, 204));
//		panelPatioButacas.setBackground(new Color(192, 192, 192));
		panelPatioButacas.setLayout(null);
		panelPlanoTeatro.add(panelPatioButacas);
		
		panelEntresuelo = new JPanel();
		//panelEntresuelo.setBackground(new Color(102, 255, 0));
//		panelEntresuelo.setBackground(new Color(192, 192, 192));
		panelEntresuelo.setLayout(null);
		panelPlanoTeatro.add(panelEntresuelo);

		//calculo de filas y columnas
		MAX_FIL_P = controladorEntradas.countFilas(Zona.PATIO_BUTACAS);
		MAX_FIL_E = controladorEntradas.countFilas(Zona.ENTRESUELO);
		MAX_COL_P = controladorEntradas.countColumnas(Zona.PATIO_BUTACAS);
		MAX_COL_E = controladorEntradas.countColumnas(Zona.ENTRESUELO);

		//pintar de plano
		pintaPlano(controladorEntradas.obtenerPlano());
		
		//tama�os de los paneles
		panelPatioButacas.setBounds(0, 0, (2+MAX_COL_P)*tamBotonEspacio, (2+MAX_FIL_P)*tamBotonEspacio);
		panelEntresuelo.setBounds(panelPatioButacas.getWidth(), 0, (2+MAX_COL_E)*tamBotonEspacio, (2+MAX_FIL_E)*tamBotonEspacio);
		
		panelPlanoTeatro.setPreferredSize(new Dimension(panelPatioButacas.getWidth() + panelEntresuelo.getWidth(), panelPatioButacas.getHeight()));
		
		panelEscenario = new JPanel();
		GridBagConstraints gbc_panelEscenario = new GridBagConstraints();
		gbc_panelEscenario.fill = GridBagConstraints.BOTH;
		gbc_panelEscenario.insets = new Insets(0, 10, 5, 10);
		gbc_panelEscenario.gridx = 0;
		gbc_panelEscenario.gridy = 1;
		getContentPane().add(panelEscenario, gbc_panelEscenario);
		
		GridBagLayout gbl_PanelSecundario = new GridBagLayout();
		gbl_PanelSecundario.columnWidths = new int[] {70, 1170, 70};
		gbl_PanelSecundario.rowHeights = new int[]{62, 0};
		gbl_PanelSecundario.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl_PanelSecundario.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelEscenario.setLayout(gbl_PanelSecundario);
		
		btnOcuparSinImprimir = new JButton("Marcar");
		btnOcuparSinImprimir.setToolTipText("Marca las butacas seleccionadas como ocupadas SIN imprimir");
		btnOcuparSinImprimir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnOcuparSinImprimir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnOcuparSinImprimir.setBorder(UIManager.getBorder("Button.border"));
		GridBagConstraints gbc_btnOcuparSinImprimir = new GridBagConstraints();
		gbc_btnOcuparSinImprimir.insets = new Insets(0, 5, 0, 0);
		gbc_btnOcuparSinImprimir.fill = GridBagConstraints.BOTH;
		gbc_btnOcuparSinImprimir.gridx = 0;
		gbc_btnOcuparSinImprimir.gridy = 0;
		panelEscenario.add(btnOcuparSinImprimir, gbc_btnOcuparSinImprimir);
		
		btnOcuparSinImprimir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(seleccionadas);
				if(seleccionadas.size() > 0) {
					controladorEntradas.marcarOcupado(seleccionadas);
									
					pintaPlano(controladorEntradas.obtenerPlano());
				}
				seleccionadas = new ArrayList<Butaca>();
			}
		});
		lblEscenario = new JLabel("ESCENARIO");
		GridBagConstraints gbc_lblEscenario = new GridBagConstraints();
		gbc_lblEscenario.insets = new Insets(0, 5, 0, 5);
		gbc_lblEscenario.gridx = 1;
		gbc_lblEscenario.gridy = 0;
		panelEscenario.add(lblEscenario, gbc_lblEscenario);
		lblEscenario.setFont(new Font("Tahoma", Font.BOLD, 50));
		
		btnImprimir = new JButton("Imprimir");
		btnImprimir.setToolTipText("Imprime las butacas seleccionadas");
		btnImprimir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImprimir.setBorder(UIManager.getBorder("Button.border"));
		btnImprimir.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_btnImprimir = new GridBagConstraints();
		gbc_btnImprimir.insets = new Insets(0, 0, 0, 5);
		gbc_btnImprimir.fill = GridBagConstraints.BOTH;
		gbc_btnImprimir.gridx = 2;
		gbc_btnImprimir.gridy = 0;
		panelEscenario.add(btnImprimir, gbc_btnImprimir);
		
		btnImprimir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(seleccionadas.size() > 0) {
					if(controladorEntradas.isConNombre()){
						String nombre = JOptionPane.showInputDialog(PlanoTeatro2.this, "A nombre de...", "A nombre de...", JOptionPane.QUESTION_MESSAGE);
						if(nombre != null) {
							controladorEntradas.setNombre(nombre);
							try {
								controladorEntradas.imprimir(seleccionadas);
							} catch (JRException e1) {
								JOptionPane.showMessageDialog(PlanoTeatro2.this, "Error al imprimir, la plantilla no es correcta", "Error al imprimir", JOptionPane.ERROR_MESSAGE);
							} 			
							pintaPlano(controladorEntradas.obtenerPlano());
						}
					}
					else {
						try {
							controladorEntradas.imprimir(seleccionadas);
						} catch (JRException e1 ) {
							JOptionPane.showMessageDialog(PlanoTeatro2.this, "Error al imprimir, la plantilla no es correcta", "Error al imprimir", JOptionPane.ERROR_MESSAGE);
						}
										
						pintaPlano(controladorEntradas.obtenerPlano());
					}
				}
				seleccionadas = new ArrayList<Butaca>();
			}
		});
	}
	
	private void pintaPlano(List<List<Butaca>> plano) {
		//vaciar paneles de todos los botones
		panelPatioButacas.removeAll();
		panelEntresuelo.removeAll();
		
		//totales en los bordes de los paneles
//		panelPatioButacas.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "PATIO DE BUTACAS (" + controladorEntradas.getLibresPatio() + "/" + controladorEntradas.getTotalPatio() + ")", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelPatioButacas.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 3, true), "PATIO DE BUTACAS (" + controladorEntradas.getLibresPatio() + "/" + controladorEntradas.getTotalPatio() + ")", TitledBorder.CENTER, TitledBorder.TOP, null, null));
//		panelEntresuelo.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "ENTRESUELO (" + controladorEntradas.getLibresEntresuelo() + "/" + controladorEntradas.getTotalEntresuelo() + ")", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelEntresuelo.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 3, true), "ENTRESUELO (" + controladorEntradas.getLibresEntresuelo() + "/" + controladorEntradas.getTotalEntresuelo() + ")", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		
		//rellenar con todos los botones
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
				boton.setBorder(null);
				boton.setFocusPainted(false);
				boton.setBounds(tamBotonEspacio+(idxcol*tamBotonEspacio), tamBotonEspacio+(idxfil*tamBotonEspacio), tamBoton, tamBoton);
				if(butaca.getZona() == Zona.PATIO_BUTACAS) {
					if(idxcol == 0) {
						boton.setToolTipText("Seleccionar pares");
					}
					else if(idxcol == 13){
						boton.setToolTipText("Seleccionar fila");
					}
					else if(idxcol == 26) {
						boton.setToolTipText("Seleccionar impares");
					}
				}
				
				if(butaca.getEstado() == Estado.PASILLO) {//cuando es pasillo ponemos el numero de fila
					boton.setText(Integer.toString(butaca.getFila()));
					
					boton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if(butaca.getZona() == Zona.PATIO_BUTACAS) {
								
								//seleccionamos las libres pares o viceversa
								if(boton.getToolTipText().equals("Seleccionar pares")){
									for(int i = 1; i < fila.size(); i++) {
										if(fila.get(i).getEstado().equals(Estado.PASILLO)) {
											break;
										}
										if(fila.get(i).getEstado().equals(Estado.LIBRE)) {
											fila.get(i).setEstado(Estado.SELECCIONADA);
											seleccionadas.add(fila.get(i));
										}
										else if(fila.get(i).getEstado().equals(Estado.SELECCIONADA)) {
											fila.get(i).setEstado(Estado.LIBRE);
											Butaca butacaBorrar = fila.get(i);
											seleccionadas.removeIf((Butaca b ) -> b.getButaca() == butacaBorrar.getButaca() && b.getFila() == butacaBorrar.getFila() && b.getZona() == butacaBorrar.getZona());
										}
									}
								}
								
								//seleccionamos las libres de toda la fila o viceversa
								else if(boton.getToolTipText().equals("Seleccionar fila")){
									for(int i = 0; i < fila.size(); i++) {
										if(fila.get(i).getEstado().equals(Estado.LIBRE)) {
											fila.get(i).setEstado(Estado.SELECCIONADA);
											seleccionadas.add(fila.get(i));
										}
										else if(fila.get(i).getEstado().equals(Estado.SELECCIONADA)) {
											fila.get(i).setEstado(Estado.LIBRE);
											Butaca butacaBorrar = fila.get(i);
											seleccionadas.removeIf((Butaca b ) -> b.getButaca() == butacaBorrar.getButaca() && b.getFila() == butacaBorrar.getFila() && b.getZona() == butacaBorrar.getZona());
										}
									}
								}
								
								//seleccionamos las libres impares o viceversa
								else if(boton.getToolTipText().equals("Seleccionar impares")){
									boolean impares = false;
									for(int i = 1; i < fila.size(); i++) {
										if(fila.get(i).getEstado().equals(Estado.PASILLO)){
											impares = true;
										}
										if(impares && fila.get(i).getEstado().equals(Estado.LIBRE)) {
											fila.get(i).setEstado(Estado.SELECCIONADA);
											seleccionadas.add(fila.get(i));
										}
										else if(impares && fila.get(i).getEstado().equals(Estado.SELECCIONADA)) {
											fila.get(i).setEstado(Estado.LIBRE);
											Butaca butacaBorrar = fila.get(i);
											seleccionadas.removeIf((Butaca b ) -> b.getButaca() == butacaBorrar.getButaca() && b.getFila() == butacaBorrar.getFila() && b.getZona() == butacaBorrar.getZona());
										}
									}
								}
								pintaPlano(plano);
							}
						}
					});
				}
				else if(butaca.getEstado() == Estado.VACIO){//cuando es vacio no ponemos ni texto ni butaca.
				}
				else{
					Toolkit kit = Toolkit.getDefaultToolkit();
					Image icono = kit.createImage(butaca.getEstado().getImg()).getScaledInstance(tamBoton, tamBoton, Image.SCALE_SMOOTH);

					boton.setIcon(new ImageIcon(icono));
					boton.setToolTipText(Integer.toString(butaca.getButaca()));
					
					menuOpciones = new JPopupMenu();
					
					menuItemDesocupar = new JMenuItem("Desocupar");
					menuItemDesocupar.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							if(JOptionPane.showConfirmDialog(PlanoTeatro2.this, "Esta butaca esta ocupada por otra persona. �Quieres desocuparla?", "�Desocupar butaca?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
								butaca.setEstado(Estado.LIBRE);

								controladorEntradas.setButacaPlano(butaca);
								pintaPlano(controladorEntradas.obtenerPlano());
							}
							System.out.println("soy la opcion de menu del boton" + "zona: " + butaca.getZona() + " fila: " + butaca.getFila() + " butaca: " + butaca.getButaca());
							
							controladorEntradas.setButacaPlano(butaca);
						}
					});
					
					menuItemEstrop_Arreg = new JMenuItem("Estropeada/Arreglada");
					menuItemEstrop_Arreg.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							if(butaca.getEstado() == Estado.ESTROPEADA) {
								butaca.setEstado(Estado.LIBRE);
								//System.out.println("zona: " + butaca.getZona() + " fila: " + butaca.getFila() + " butaca: " + butaca.getButaca());
							}
							else {
								butaca.setEstado(Estado.ESTROPEADA);
								//System.out.println("zona: " + butaca.getZona() + " fila: " + butaca.getFila() + " butaca: " + butaca.getButaca());
							}
							controladorEntradas.setButacaPlano(butaca);
							pintaPlano(controladorEntradas.obtenerPlano());
						}
					});

					menuOpciones.add(menuItemDesocupar);
					menuOpciones.add(menuItemEstrop_Arreg);
					addPopup(boton, menuOpciones, butaca);
				}
				
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
						Toolkit kit = Toolkit.getDefaultToolkit();
						Image icono = kit.createImage(butaca.getEstado().getImg()).getScaledInstance(tamBoton, tamBoton, Image.SCALE_SMOOTH);
						boton.setIcon(new ImageIcon(icono));
						//System.out.println("zona: " + butaca.getZona() + " fila: " + butaca.getFila() + " butaca: " + butaca.getButaca());
												
					}
				});
				
				idxcol++;
				butacaAnterior = butaca;
			}
			idxfil++;
		}
		
		//forzamos el repintado
		panelPatioButacas.repaint();
		panelEntresuelo.repaint();
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
