/**
 * 
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRException;
import transfer.Butaca;
import transfer.Estado;
import transfer.Zona;

/**
 * @author Pablo
 *
 */
public class ModelEntradas {
	private ArrayList<List<Butaca>> plano;
	private String fecha;
	private String sesion;
	private int libresPatio = 0;
	private int totalPatio = 0;
	private int libresEntresuelo = 0;
	private int totalEntresuelo = 0;
	private boolean conNombre;
	private String nombre;

	static final String PLANO_PATIO = "plantillas/Patio de butacas.txt";
	static final String ENTRESUELO = "plantillas/Entresuelo.txt";

	private static final Logger logger = Logger.getLogger(ModelEntradas.class);

	/**
	 * Constructor
	 */
	public ModelEntradas() {
		BasicConfigurator.configure();
		plano = new ArrayList<List<Butaca>>();
	}

	/**
	 * @return the plano
	 */
	public ArrayList<List<Butaca>> getPlano() {
		return this.plano;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @return the sesion
	 */
	public String getSesion() {
		return sesion;
	}

	/**
	 * @return the libresPatio
	 */
	public int getLibresPatio() {
		return libresPatio;
	}

	/**
	 * @return the totalPatio
	 */
	public int getTotalPatio() {
		return totalPatio;
	}

	/**
	 * @return the libresEntresuelo
	 */
	public int getLibresEntresuelo() {
		return libresEntresuelo;
	}

	/**
	 * @return the totalEntresuelo
	 */
	public int getTotalEntresuelo() {
		return totalEntresuelo;
	}

	/**
	 * @return the conNombre
	 */
	public boolean isConNombre() {
		return conNombre;
	}

	/**
	 * Settea si las entradas van con nombre o no.
	 * 
	 * @param selected <code>true</code> si van con nombre.
	 */
	public void setConNombre(boolean selected) {
		this.conNombre = selected;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void leerPlano(String fecha, String sesion) throws IOException {
		this.fecha = fecha;
		this.sesion = sesion;
		FileInputStream in = null;
		List<Butaca> filaPlano = new ArrayList<Butaca>();
		String ruta;
		try {

			String tipoButaca;
			int tipoButacaInt;
			for (Zona zona : Zona.values()) {// para cada zona del teatro, se leen los planos
				ruta = "planos/" + this.fecha + "-" + this.sesion + zona.getId() + ".txt";
				in = new FileInputStream(ruta);
				int fila = 0;

				while ((tipoButacaInt = in.read()) != -1) {
					tipoButaca = (String.valueOf((char) tipoButacaInt));
					if (!tipoButaca.equalsIgnoreCase("\n")) {// si es distinto de \n es que estamos en la misma fila
						int butaca = 0; // si es pasillo o vacio o F, este valor ira a 0;
						if (!tipoButaca.equalsIgnoreCase(Estado.PASILLO.getId())
								&& !tipoButaca.equalsIgnoreCase(Estado.VACIO.getId())
								&& !tipoButaca.equalsIgnoreCase("F")) {
							butaca = Integer.parseInt(
									new StringBuilder().append((char) in.read()).append((char) in.read()).toString());
						}
						if (tipoButaca.equalsIgnoreCase("F")) {
							fila = Integer.parseInt(
									new StringBuilder().append((char) in.read()).append((char) in.read()).toString());
						} else {
							Estado estado = Estado.fromId(tipoButaca);
							filaPlano.add(new Butaca(fila, butaca, zona, estado));

						}
					} else {// se guarda la fila en el plano.
						plano.add(filaPlano);
						filaPlano = new ArrayList<Butaca>();
					}
				}
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		cuentaButacas();
		// System.out.println("Patio: " + libresPatio+"/"+totalPatio);
		// System.out.println("Entresuelo: " + libresEntresuelo+"/"+totalEntresuelo);
	}

	/**
	 * cuenta las butacas libres y totales
	 */
	private void cuentaButacas() {
		libresPatio = 0;
		totalPatio = 0;
		libresEntresuelo = 0;
		totalEntresuelo = 0;

		for (List<Butaca> fila : plano) {
			for (Butaca butaca : fila) {
				if (butaca.getZona() == Zona.PATIO_BUTACAS) {
					if (butaca.getEstado() == Estado.LIBRE) {
						libresPatio++;
					}
					if (butaca.getEstado() != Estado.PASILLO && butaca.getEstado() != Estado.VACIO
							&& butaca.getEstado() != Estado.ESTROPEADA) {
						totalPatio++;
					}
				} else {// Zona.ENTRESUELO
					if (butaca.getEstado() == Estado.LIBRE) {
						libresEntresuelo++;
					}
					if (butaca.getEstado() != Estado.PASILLO && butaca.getEstado() != Estado.VACIO
							&& butaca.getEstado() != Estado.ESTROPEADA) {
						totalEntresuelo++;
					}
				}
			}
		}
	}

	/**
	 * Comprueba si existen los planos para la fecha y sesion. si no existe los
	 * crea.
	 * 
	 * @param fecha  la fecha a comprobar
	 * @param sesion la sesion a comprobar
	 */
	public void crearPlanoSiNoExiste(String fecha, String sesion) {

		// comprobar plano patio de butacas
		String destino = "planos/" + fecha + "-" + sesion + Zona.PATIO_BUTACAS.getId() + ".txt";
		if (new File(destino).exists() == false) {
			generarPlano(PLANO_PATIO, destino);
		}
		destino = "planos/" + fecha + "-" + sesion + Zona.ENTRESUELO.getId() + ".txt";
		// comprobar plano entresuelo
		if (new File(destino).exists() == false) {
			generarPlano(ENTRESUELO, destino);
		}
	}

	private void generarPlano(String nombreOrigen, String nombreDestino) {
		FileReader ficheroIN = null;
		FileWriter ficheroOUT = null;
		BufferedReader brIN = null;
		PrintWriter pwOUT = null;
		String fila;
		try {
			// inicializacion
			ficheroIN = new FileReader(nombreOrigen);
			brIN = new BufferedReader(ficheroIN);

			ficheroOUT = new FileWriter(nombreDestino);
			pwOUT = new PrintWriter(ficheroOUT);

			// leer fila
			while ((fila = brIN.readLine()) != null) {
				// escribir fila
				pwOUT.write(fila + "\n");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != ficheroOUT) {
					ficheroOUT.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public void imprimir(List<Butaca> seleccionadas, boolean boolImprimir) throws JRException {
		long t1 = System.currentTimeMillis();
		String imprimir = "";
		Collections.sort(seleccionadas, Butaca.butacaComparator);
		// imprimir entradas
		if (conNombre) {
			imprimir += "A nombre de: " + (this.nombre != null ? this.nombre : "") + " (" + seleccionadas.size() + ")" + '\n';
		} else {
			imprimir += "Entradas sin nombre"+'\n';
		}
		int numFila = 0;
		Zona zona = null, zonaAnterior = null;
		List<String> fila = null;
		for (Butaca butaca : seleccionadas) {

			if (numFila != butaca.getFila() || zona != butaca.getZona()) {
				if (fila != null) {
					imprimir += String.join(", ", fila) + '\n';
				}

				numFila = butaca.getFila();
				zona = butaca.getZona();
				if (zonaAnterior != zona) {
					imprimir += "***" + zona.getDescripcion() + "***" + '\n';
				}
				zonaAnterior = zona;
				imprimir += "Fila: " + numFila + '\n';
				imprimir += "Butacas: ";
				fila = new ArrayList<String>();
			}
			fila.add(Integer.toString(butaca.getButaca()));

		}
		if (fila != null) {
			imprimir += String.join(", ", fila) + '\n';
		}
		logger.info(imprimir);

		if (boolImprimir) {
			new Imprimir(imprimir, this.nombre);
		}

		// cambiar estado en el plano
		for (List<Butaca> filaPlano : plano) {
			for (Butaca butaca : filaPlano) {
				if (butaca.getEstado() == Estado.SELECCIONADA) {
					butaca.setEstado(Estado.OCUPADA);
				}
			}
		}

		// guardar en txt
		guardarPlano();

		logger.info("");

		long t2 = System.currentTimeMillis();

		System.out.println("(" + (t2 - t1) + ") = " + (t2 - t1) / 1000 + "s " + (t2 - t1) % 1000);
	}

	public void guardarPlano() {
		String rutaP, rutaE;
		String planoGuardar;
		FileWriter ficheroP = null, ficheroE = null;
		PrintWriter pwP = null, pwE = null;

		try {
			rutaP = "planos/" + this.fecha + "-" + this.sesion + Zona.PATIO_BUTACAS.getId() + ".txt";
			rutaE = "planos/" + this.fecha + "-" + this.sesion + Zona.ENTRESUELO.getId() + ".txt";
			ficheroP = new FileWriter(rutaP);
			ficheroE = new FileWriter(rutaE);
			pwP = new PrintWriter(ficheroP);
			pwE = new PrintWriter(ficheroE);

			for (List<Butaca> lista : plano) {
				int numFila = lista.get(0).getFila();
				NumberFormat formatter = new DecimalFormat("00");

				planoGuardar = (/* lista.get(0).getEstado() != Estado.VACIO || */lista.size() > 1)
						? "F" + formatter.format(numFila)
						: "";
				for (Butaca butaca : lista) {
					String numeroButaca = (butaca.getEstado() != Estado.PASILLO && butaca.getEstado() != Estado.VACIO)
							? formatter.format(butaca.getButaca())
							: "";
					planoGuardar += butaca.getEstado().getId() + numeroButaca;
				}
				planoGuardar += "\n";
				if (lista.get(0).getZona() == Zona.PATIO_BUTACAS) {
					pwP.print(planoGuardar);
				} else { // ENTRESUELO
					pwE.print(planoGuardar);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != ficheroP) {
					ficheroP.close();
				}
				if (null != ficheroE) {
					ficheroE.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		cuentaButacas();
	}

	public void setButacaPlano(Butaca butacaMod) {
//		for (List<Butaca> fila : plano) {
//			for (Butaca butaca : fila) {
//				if(butaca.getZona() == butacaMod.getZona() && 
//						butaca.getFila() == butacaMod.getFila() && 
//						butaca.getButaca() == butacaMod.getButaca()) {
//					butaca.setEstado(butacaMod.getEstado());
//				}
//			}
//		}
		// guardar en txt
		guardarPlano();
	}

	public int countFilas(Zona zona) {
		int contador = 0;
		for (int i = 0; i < plano.size(); i++) {
			if (plano.get(i).get(0).getZona() == zona) {
				contador++;
			}
		}
		return contador;
	}

	public int countColumnas(Zona zona) {
		boolean parar = false;
		int i = 0, contador = 0;
		while (!parar) {
			if (plano.get(i).get(0).getZona() == zona) {
				contador = plano.get(i).size();
				parar = true;
			}
			i++;
		}
		return contador;
	}

}
