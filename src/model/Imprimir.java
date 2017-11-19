/**
 * 
 */
package model;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.Attribute;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.PrinterState;
import javax.print.event.PrintServiceAttributeEvent;
import javax.print.event.PrintServiceAttributeListener;
import javax.swing.ImageIcon;

import controller.ControladorConfiguracion;

/**
 * @author Pablo
 *
 */
public class Imprimir implements Printable {
	private String textoImprimir;
	private Image logoTeatro, logoObra, qrFbTeatro;
	/**
	 * @throws PrinterException 
	 * 
	 */
	public Imprimir(String textoImprimir) throws PrinterException {
		this.textoImprimir = textoImprimir;
		ControladorConfiguracion controladorConfiguracion = ControladorConfiguracion.getInstance();
		logoTeatro = new ImageIcon(controladorConfiguracion.getPropiedades().getProperty("ICONO_TEATRO")).getImage();
		logoObra = new ImageIcon(controladorConfiguracion.getPropiedades().getProperty("ICONO_OBRA")).getImage();
		qrFbTeatro = new ImageIcon(controladorConfiguracion.getPropiedades().getProperty("QR_FACEBOOK")).getImage();
		
		PrinterJob pj = PrinterJob.getPrinterJob();
		
//		PrintService printService = pj.getPrintService();
		PageFormat pageFormat = new PageFormat();
		Paper papel = new Paper(); 
		papel.setImageableArea(14.0, 2.8, 136.0, 583.0);
		pageFormat.setPaper(papel);
		
		pj.setPrintable(this, pageFormat);
		pj.print();
	}

	/* (non-Javadoc)
	 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	 */
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex == 0) {
			FontMetrics fontMetrics;
			Graphics2D g2d = (Graphics2D) graphics;
		    g2d.translate (pageFormat.getImageableX (), pageFormat.getImageableY ());
		    g2d.setFont(new Font(g2d.getFont().getFontName(), Font.PLAIN, 10));
	    	fontMetrics = g2d.getFontMetrics(); 
		    
		    //imprimimos los iconos en 40x40
			graphics.drawImage(logoTeatro, ((int)pageFormat.getImageableWidth()/2 + 10 - 40 -2), 10, 40, 40, null);
			graphics.drawImage(logoObra, ((int)pageFormat.getImageableWidth()/2) + 10 +2, 10, 40, 40, null);
		    int y = 45;
		    
		    for (String line : textoImprimir.split("\n")) {
		    	graphics.drawString(line, (int) ((pageFormat.getImageableWidth() - fontMetrics.stringWidth(line))/2), y += fontMetrics.getHeight()	);
		    }
		    
		    //imprimir fecha y entrada gratuita
		    LocalDate hoy = LocalDate.now();
		    String dia = hoy.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
		    graphics.drawString("Valido para", (int) ((pageFormat.getImageableWidth() - fontMetrics.stringWidth("Valido para"))/2), y += fontMetrics.getHeight()	);
		    graphics.drawString(dia, (int) ((pageFormat.getImageableWidth() - fontMetrics.stringWidth(dia))/2), y += fontMetrics.getHeight()	);
		    
		    g2d.setFont(new Font(g2d.getFont().getFontName(), Font.PLAIN, 8));
		    fontMetrics = g2d.getFontMetrics();
		    graphics.drawString("Entrada gratuita, prohibida su venta", (int) ((pageFormat.getImageableWidth() - fontMetrics.stringWidth("Entrada gratuita, prohibida su venta"))/2), y += fontMetrics.getHeight()	);		    
		    
		    //imprimir codigo qr
			graphics.drawImage(qrFbTeatro, ((int) (pageFormat.getImageableWidth() - 90) /2), y += fontMetrics.getHeight(), 90, 90, null);
			
			return PAGE_EXISTS;
		}
		else {
			return NO_SUCH_PAGE;
		}
	}

}
class MyPrintServiceAttributeListener implements PrintServiceAttributeListener {
	  public void attributeUpdate(PrintServiceAttributeEvent psae) {
	    PrintService service = psae.getPrintService();
	    Attribute[] attrs = psae.getAttributes().toArray();
	    for (int i = 0; i < attrs.length; i++) {
	      String attrName = attrs[i].getName();
	      String attrValue = attrs[i].toString();
	    }
	  }
	}
