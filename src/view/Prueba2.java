package view;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.Rectangle;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLayeredPane;
import javax.swing.JSeparator;
import java.awt.Component;
import javax.swing.Box;

public class Prueba2 extends JFrame {
	public Prueba2() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {414};
		gridBagLayout.rowHeights = new int[] {182};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setMaximumSize(new Dimension(50, 32767));
		scrollPane.setViewportBorder(new MatteBorder(1, 1, 1, 1, (Color) UIManager.getColor("ProgressBar.foreground")));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setAutoscrolls(true);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(10, 10, 10, 10);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		getContentPane().add(scrollPane, gbc_scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 153, 0));
		panel.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)));
		panel.setBounds(0,0,600,600);
		//panel.setPreferredSize(new Dimension(1000, 1000));
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		int maxFilasE = 10;
		int maxColsE = 40;
		int maxFilasP = 20;
		int maxColsP = 40;
		int tamBoton = 40;
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 153, 204));
		panel_1.setBounds(0, 0, maxColsP*tamBoton, maxFilasP*tamBoton);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(102, 255, 0));
		panel_2.setBounds((maxColsP+1)*tamBoton, 0, maxColsE*tamBoton, maxFilasE*tamBoton);
		panel.add(panel_2);
		panel_2.setLayout(null);

//		panel.setPreferredSize(new Dimension((maxColsP+1+maxColsE)*tamBoton, maxFilasP*tamBoton));
		panel.setPreferredSize(new Dimension(panel_1.getWidth() + panel_2.getWidth(), panel_1.getHeight()));

		for (int fila = 0; fila < maxFilasP; fila++) {
			for (int col = 0; col < maxColsP; col++) {
				JButton btn = new JButton(fila+"-"+col);
				btn.setBounds(col*tamBoton, fila*tamBoton, tamBoton, tamBoton);
				btn.setMargin(new Insets(2, 0, 2, 0));
				btn.setContentAreaFilled(false);
				panel_1.add(btn);
			}
		}

		for (int fila = 0; fila < maxFilasE; fila++) {
			for (int col = 0; col < maxColsE; col++) {
				JButton btn = new JButton(fila+"-"+col);
				btn.setBounds(col*tamBoton, fila*tamBoton, tamBoton, tamBoton);
				btn.setMargin(new Insets(2, 0, 2, 0));
				btn.setContentAreaFilled(false);
				panel_2.add(btn);
			}
		}
		
//		JButton btnNewButton = new JButton("New button");
//		btnNewButton.setBounds(118, 5, 89, 23);
//		panel.add(btnNewButton);
//		
//		JButton btnNewButton_1 = new JButton("New button");
//		btnNewButton_1.setBounds(212, 5, 89, 23);
//		panel.add(btnNewButton_1);
	}
}
