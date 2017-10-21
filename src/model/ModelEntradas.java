/**
 * 
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
//		try {
//			leerFichero("src/plano.txt");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
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
	    		int fila = 1;
	    		
		        while((tipoButacaInt = in.read())!=-1) {
		        	tipoButaca = (String.valueOf( (char)tipoButacaInt) );
		        	if(!tipoButaca.equalsIgnoreCase("\n")){//si es distinto de \n es que estamos en la misma fila 
		        		int butaca = 0; //si es pasillo o vacio, este valor ira a 0;
		        		if(!tipoButaca.equalsIgnoreCase(Estado.PASILLO.getId()) && !tipoButaca.equalsIgnoreCase(Estado.VACIO.getId())) {
		        			butaca = Integer.parseInt( new StringBuilder().append((char)in.read()).append((char)in.read()).toString());
		        		}
		        		filaPlano.add(new Butaca(fila, butaca, zona, Estado.fromId(tipoButaca)));
	        		}
		        	else {//cuando cambiamos de linea hay cambio de fila, asi que se guarda la fila en el plano. 
		        		if(!(filaPlano.size() == 1 && filaPlano.get(0).getEstado() == Estado.VACIO)) {//si la fila es solo una X es pasillo horizontal y no corre el numero de fila 
		        			fila++;
		        		}
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
}
