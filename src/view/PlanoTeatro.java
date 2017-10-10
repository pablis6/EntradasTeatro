/**
 * 
 */
package view;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * @author Pablo
 *
 */
public class PlanoTeatro extends JFrame {
	
	public PlanoTeatro() {
		setSize(new Dimension(1000, 700));
		getContentPane().setLayout(null);
		
		for(int i = 0; i < 5; i++) {
			JButton btnA = new JButton("");
			btnA.setContentAreaFilled(false);
			
			String resource="";
			
			switch (i) {
			case 0:
				resource = "/img/libre.png";
			break;
			case 1:
				resource = "/img/ocupado.png";
			break;
			case 2:
				resource = "";
			break;
			case 3:
				resource = "/img/seleccionado.png";
			break;
			case 4:
				resource = "/img/estropeado.png";
			break;
			default:
				break;
			}
			Image icono = new ImageIcon(getClass().getResource(resource)).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
			
			btnA.setIcon(new ImageIcon(icono));
			btnA.setBorder(null);
			btnA.setBounds(20+(i*25), 20, 25, 25);
			getContentPane().add(btnA);
		}
	}
}
