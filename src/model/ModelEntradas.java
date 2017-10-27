/**
 * 
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import transfer.Butaca;
import transfer.Estado;
import transfer.Zona;

/**
 * @author Pablo
 *
 */
public class ModelEntradas {
	private ArrayList<List<Butaca>> plano;
	static final String PLANO_PATIO = "plantillas/Patio de butacas.txt";
	static final String ENTRESUELO = "plantillas/Entresuelo.txt";
	
	/**
	 * Constructor
	 */
	public ModelEntradas() {
		plano = new ArrayList<List<Butaca>>();
	}
	
	public void leerPlano(String fecha, String sesion) throws IOException{
		FileInputStream in = null;
		List<Butaca> filaPlano = new ArrayList<Butaca>();
		String ruta;
		try {

			String tipoButaca;
			int tipoButacaInt;
			for (Zona zona : Zona.values()) {//para cada zona del teatro, se leen los planos
				ruta = "planos/" + fecha + "-" + sesion + zona.getId() + ".txt";
				in = new FileInputStream(ruta);
	    		int fila = 0;
	    		
		        while((tipoButacaInt = in.read())!=-1) {
		        	tipoButaca = (String.valueOf( (char)tipoButacaInt) );
		        	if(!tipoButaca.equalsIgnoreCase("\n")){//si es distinto de \n es que estamos en la misma fila
		        		int butaca = 0; //si es pasillo o vacio o F, este valor ira a 0;
		        		if(!tipoButaca.equalsIgnoreCase(Estado.PASILLO.getId()) 
		        				&& !tipoButaca.equalsIgnoreCase(Estado.VACIO.getId()) 
		        				&& !tipoButaca.equalsIgnoreCase("F")) {
		        			butaca = Integer.parseInt( new StringBuilder().append((char)in.read()).append((char)in.read()).toString());
		        		}
		        		if(tipoButaca.equalsIgnoreCase("F")) {
		        			fila = Integer.parseInt( new StringBuilder().append((char)in.read()).append((char)in.read()).toString());
		        		} else {
		        			filaPlano.add(new Butaca(fila, butaca, zona, Estado.fromId(tipoButaca)));
		        		}
	        		}
		        	else {//se guarda la fila en el plano. 
			        	plano.add(filaPlano);
			    		filaPlano = new ArrayList<Butaca>();
		        	}
		        }
		        in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		} finally {
            if (in != null) {
                in.close();
            }
        }
	}
	
	/**
	 * @return the plano
	 */
	public ArrayList<List<Butaca>> getPlano() {
		return this.plano;
	}
	
	/**
	 * Comprueba si existen los planos para la fecha y sesion. si no existe los crea.
	 * @param fecha la fecha a comprobar
	 * @param sesion la sesion a comprobar
	 */
	public void crearPlanoSiNoExiste(String fecha, String sesion) {
		
		//comprobar plano patio de butacas
		String destino = "planos/" + fecha + "-" + sesion + Zona.PATIO_BUTACAS.getId() + ".txt";
		if(new File(destino).exists() == false) {
			generarPlano(PLANO_PATIO, destino);
		}
		destino = "planos/" + fecha + "-" + sesion + Zona.ENTRESUELO.getId() + ".txt";
		//comprobar plano entresuelo
		if(new File(destino).exists() == false) {
			generarPlano(ENTRESUELO, destino);
		}
	}

	//TODO cambiar a leer por lineas??
	private void generarPlano(String nombreOrigen, String nombreDestino) {
		FileInputStream in;
		FileOutputStream out;
		
		try {
	        in = new FileInputStream(nombreOrigen);
	        out = new FileOutputStream(nombreDestino);
	
	        
	        int c;
	        while( (c = in.read() ) != -1) {
	            out.write(c);
	        }
		} catch (IOException e) {
			
		}
	}

	public void imprimir(List<Butaca> seleccionadas) {
		Collections.sort(seleccionadas, Butaca.butacaComparator);
		//guardar en txt 
		
		System.out.println("Butacas seleccionadas: \n" + seleccionadas);
		
	}
	
	public int countFilas(Zona zona) {
		int contador = 0;
		for(int i = 0; i < plano.size(); i++) {
			if(plano.get(i).get(0).getZona() == zona) {
				contador++;
			}
		}
		return contador;
	}
	
	public int countColumnas(Zona zona) {
		boolean parar = false;
		int i = 0, contador = 0;
		while(!parar) {
			if(plano.get(i).get(0).getZona() == zona) {
				contador = plano.get(i).size();
				parar = true;
			}
			i++;
		}
		return contador;
	}
}
