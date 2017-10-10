/**
 * 
 */
package transfer;

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
	
	

}
