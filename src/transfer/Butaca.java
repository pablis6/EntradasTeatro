/**
 * 
 */
package transfer;

import java.util.Comparator;

/**
 * @author Pablo
 *
 */
public class Butaca {
	private int fila, butaca;
	private Zona zona;
	private Estado estado;
	
	/**
	 * Constructor 
	 */
	public Butaca(int fila, int butaca, Zona zona, Estado estado) {
		this.fila = fila;
		this.butaca = butaca;
		this.zona = zona;
		this.estado = estado;
	}

	/**
	 * @return the fila
	 */
	public int getFila() {
		return fila;
	}

	/**
	 * @param fila the fila to set
	 */
	public void setFila(int fila) {
		this.fila = fila;
	}

	/**
	 * @return the butaca
	 */
	public int getButaca() {
		return butaca;
	}

	/**
	 * @param butaca the butaca to set
	 */
	public void setButaca(int butaca) {
		this.butaca = butaca;
	}

	/**
	 * @return the zona
	 */
	public Zona getZona() {
		return zona;
	}

	/**
	 * @param zona the zona to set
	 */
	public void setZona(Zona zona) {
		this.zona = zona;
	}

	/**
	 * @return the estado
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Butaca [fila=");
		builder.append(fila);
		builder.append(", butaca=");
		builder.append(butaca);
		builder.append(", zona=");
		builder.append(zona);
		builder.append(", estado=");
		builder.append(estado);
		builder.append("]");
		return builder.toString();
	}

	public static Comparator<Butaca> butacaComparator = new Comparator<Butaca>() {

		/**
		 * Ordena por "Patio de butacas - Entresuelo", orden numerico de fila, orden numerico de butaca
		 * @param butaca1
		 * @param butaca2
		 * @return entero resultante de la comparacion
		 */
		public int compare(Butaca butaca1, Butaca butaca2) {
			
			String butacaZona1 = butaca1.getZona().getDescripcion();
			String butacaZona2 = butaca2.getZona().getDescripcion();
			
			Integer butacaFila1 = butaca1.getFila();
			Integer butacaFila2 = butaca2.getFila();

			Integer butacaNumero1 = butaca1.getButaca();
			Integer butacaNumero2 = butaca2.getButaca();
			
			//ascending order
			int zona = butacaZona2.compareTo(butacaZona1);
			if(zona != 0) { return zona;}
			
			int fila = butacaFila1.compareTo(butacaFila2);
			if(fila != 0) {return fila;}
			
			int num = butacaNumero1.compareTo(butacaNumero2);
			return num;
			
		}
		
	};

}
