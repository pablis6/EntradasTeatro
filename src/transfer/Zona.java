/**
 * 
 */
package transfer;

/**
 * @author Pablo
 * @descripcion enumerador de las dos zonas del teatro.
 */
public enum Zona {	
	PATIO_BUTACAS ("P", "Patio de butacas"),
	ENTRESUELO ("E", "Entresuelo");

	private final String id, descripcion;

	private Zona(String id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	
}
