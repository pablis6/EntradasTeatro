/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import controller.ControladorEntradas;
import transfer.Butaca;
import transfer.Estado;
import transfer.Zona;
import javax.swing.border.MatteBorder;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import java.awt.Cursor;

/**
 * @author Pablo
 *
 */
public class PlanoTeatro extends JFrame {
	private JSplitPane splitPane;
	private JLabel lblEscenario;
	private JPopupMenu menuOpciones;
	private JMenuItem menuItemDesocupar, menuItemEstrop_Arreg;
	private JButton btnImprimir;
	private JPanel panelEscenario, panelPatioButacas, panelEntresuelo, panelBoton;
	private List<Butaca> seleccionadas = new ArrayList<Butaca>();
	private List<JButton> botones = new ArrayList<JButton>();
	
	public PlanoTeatro(ControladorEntradas controladorEntradas) {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setSize(new Dimension(1382, 885));
		//operacion de cierre de ventana
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent evt ) {
        		if(JOptionPane.showConfirmDialog(PlanoTeatro.this, "¿Quieres salir?", "Confirmacion de salida", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					System.exit(0);
        		}
        	}
		});
		
		this.setTitle("Entradas Teatro La Mennais - Seleccion butaca");
		//icono APP
		URL pathIcon = this.getClass().getClassLoader().getResource("img/icono-Mene-192x192.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(pathIcon);
		this.setIconImage(img);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {917};
		gridBagLayout.rowHeights = new int[] {800, 85};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		splitPane = new JSplitPane();
		splitPane.setBounds(new Rectangle(0, 0, 0, 800));
		splitPane.setAutoscrolls(true);
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.insets = new Insets(0, 10, 5, 10);
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		splitPane.setDividerLocation(getWidth()/2);	
		getContentPane().add(splitPane, gbc_splitPane);
		
		panelPatioButacas = new JPanel();
		panelPatioButacas.setPreferredSize(new Dimension(10, 620));
		panelPatioButacas.setMinimumSize(new Dimension(10, 620));
		panelPatioButacas.setMaximumSize(new Dimension(32767, 620));
		panelPatioButacas.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panelPatioButacas.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "PATIO DE BUTACAS", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelPatioButacas.setAutoscrolls(true);
		panelPatioButacas.setBounds(new Rectangle(0, 0, 200, 620));
		splitPane.setLeftComponent(panelPatioButacas);
		panelPatioButacas.setLayout(null);
		
		panelEntresuelo = new JPanel();
		panelEntresuelo.setBounds(new Rectangle(0, 0, 0, 620));
		panelEntresuelo.setBorder(new TitledBorder(new EmptyBorder(0, 0, 0, 0), "ENTRESUELO", TitledBorder.CENTER, TitledBorder.BELOW_TOP, null, null));
		panelEntresuelo.setAutoscrolls(true);
		splitPane.setRightComponent(panelEntresuelo);
		panelEntresuelo.setLayout(null);	
		
		ControladorEntradas controller = controladorEntradas;
		
		//carga de plano
		List<List<Butaca>> plano = controller.obtenerPlano();
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
							if(JOptionPane.showConfirmDialog(PlanoTeatro.this, "Esta butaca esta ocupada por otra persona. ¿Quieres desocuparla?", "¿Desocupar butaca?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
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
				boton.setBounds(10+(idxcol*27), 20+(idxfil*27), 25, 25);
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
							JOptionPane.showMessageDialog(PlanoTeatro.this, "Esta butaca esta ocupada por otra persona. Tienes que vaciarla primero", "Butaca ocupada", JOptionPane.ERROR_MESSAGE);
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
		
		panelEscenario = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		getContentPane().add(panelEscenario, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {1252, 100, 30};
		gbl_panel.rowHeights = new int[]{82, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelEscenario.setLayout(gbl_panel);
		
		lblEscenario = new JLabel("ESCENARIO");
		GridBagConstraints gbc_lblEscenario = new GridBagConstraints();
		gbc_lblEscenario.insets = new Insets(0, 0, 0, 5);
		gbc_lblEscenario.gridx = 0;
		gbc_lblEscenario.gridy = 0;
		panelEscenario.add(lblEscenario, gbc_lblEscenario);
		lblEscenario.setFont(new Font("Tahoma", Font.BOLD, 68));
		
		btnImprimir = new JButton("Imprimir");
		btnImprimir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImprimir.setBorder(UIManager.getBorder("Button.border"));
		btnImprimir.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_btnImprimir = new GridBagConstraints();
		gbc_btnImprimir.insets = new Insets(0, 0, 0, 5);
		gbc_btnImprimir.fill = GridBagConstraints.BOTH;
		gbc_btnImprimir.gridx = 1;
		gbc_btnImprimir.gridy = 0;
		panelEscenario.add(btnImprimir, gbc_btnImprimir);
		
		btnImprimir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controladorEntradas.imprimir(seleccionadas);
				
				for (JButton boton : botones) {
					if(boton.getName() == Estado.SELECCIONADA.getDescripcion()) {
						Image icono = new ImageIcon(getClass().getResource(Estado.OCUPADA.getImg())).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
						boton.setIcon(new ImageIcon(icono));
					}
				}
				for (List<Butaca> list : plano) {
					for (Butaca butaca : list) {
						if(butaca.getEstado().equals(Estado.SELECCIONADA)) {
							butaca.setEstado(Estado.OCUPADA);
						}
						
					}
				}
			}
		});

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
