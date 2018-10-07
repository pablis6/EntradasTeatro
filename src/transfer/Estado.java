/**
 * 
 */
package transfer;

/**
 * @author Pablo
 * @descripcion enumerador de los estados de las butacas.
 */
public enum Estado {
	LIBRE("L", "Libre", "img/libre.png"),
	SELECCIONADA("S", "Seleccionada", "img/seleccionado.png"),
	OCUPADA("O", "Ocupada", "img/ocupado.png"),
	ESTROPEADA("E", "Estropeada", "img/estropeado.png"),
	PASILLO("P", "Pasillo", ""),
	VACIO("X", "Vacio", "");
	
	private final String id, descripcion, img;

	private Estado(String id, String descripcion, String img) {
		this.id = id;
		this.descripcion = descripcion;
		this.img = img;
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

	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	public static Estado fromId(String id) {
		for (Estado estado : Estado.values()) {
	      if (estado.id.equalsIgnoreCase(id)) {
	        return estado;
	      }
	    }
	    return null;
	}
	
}
