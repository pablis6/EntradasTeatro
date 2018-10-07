package model;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import controller.ControladorConfiguracion;
import controller.ControladorEntradas;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrintManager;

public class Imprimir{

	/**
	 * Imprime las entradas del teatro
	 * @param textoImprimir el texto de las entradas
	 * @param nombre a quien van dirigidas
	 * @throws JRException
	 */
	public Imprimir(String textoImprimir, String nombre) throws JRException {
		ControladorConfiguracion controladorConfiguracion = ControladorConfiguracion.getInstance();
		ControladorEntradas controladorEntradas = ControladorEntradas.getInstance();
		
		String fecha = controladorEntradas.getFecha();
		LocalDate diaValido = LocalDate.of(Integer.parseInt(fecha.substring(0, 4)), Integer.parseInt(fecha.substring(5, 7)), Integer.parseInt(fecha.substring(8, 10))) ;
		String diaValidoString = diaValido.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
		
//		String defaultPrinter = PrintServiceLookup.lookupDefaultPrintService().getName();
//	    System.out.println("Default printer: " + defaultPrinter);
		String sourceFileName = new File("plantillas/ticket.jasper").getAbsolutePath();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("Entradas", textoImprimir);
		parameters.put("Fecha", "Validez: " + diaValidoString);
		parameters.put("IconoObra", new File(controladorConfiguracion.getPropiedades().getProperty("ICONO_OBRA")).getAbsolutePath());
		parameters.put("IconoTeatro", new File(controladorConfiguracion.getPropiedades().getProperty("ICONO_TEATRO")).getAbsolutePath());
		parameters.put("CodigoQR", new File(controladorConfiguracion.getPropiedades().getProperty("QR_FACEBOOK")).getAbsolutePath());
		String printFileName = JasperFillManager.fillReportToFile(sourceFileName, parameters, new JREmptyDataSource());
		JasperPrintManager.printReport(printFileName, false);
		
		//Guardar en PDF
//		if(nombre != null && !nombre.equals("")) {
//			File directorio=new File("entradas/"+fecha); 
//			directorio.mkdir();
//			int index = 0;
//			File archivo;
//			do {
//				index++;
//				archivo = new File("entradas/"+fecha+"/"+nombre+"_"+index+".pdf");
//			}
//			while(archivo.exists());
//			JasperExportManager.exportReportToPdfFile(printFileName, "entradas/"+fecha+"/"+nombre+"_"+index+".pdf");
//		}
	}

}
