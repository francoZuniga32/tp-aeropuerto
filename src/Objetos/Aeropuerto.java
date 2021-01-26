package Objetos;

public class Aeropuerto implements Comparable{
	private String nombreAeronautico;
	private String ciudad;
	private String telefono;
	
	public Aeropuerto(String nombreAeronauticoParam) {
		this.nombreAeronautico = nombreAeronauticoParam;
	}
	
	public Aeropuerto(String nombreAeronauticoParam, String ciudadParam, String telefonoParam) {
		this.nombreAeronautico = nombreAeronauticoParam;
		this.ciudad = ciudadParam;
		this.telefono = telefonoParam;
	}
	
	public String getNombreAeronautico() {
		return this.nombreAeronautico;
	}
	
	public void setCiudad(String ciudadParam) {
		this.ciudad = ciudadParam;
	}
	
	public String getCiudad() {
		return this.ciudad;
	}
	
	public void setTelefono(String telefonoParam) {
		this.telefono = telefonoParam;
	}
	
	public String getTelefono() {
		return this.telefono;
	}
	
	public String toString() {
		return "|"+this.nombreAeronautico+"|";
	}
	
	public boolean equals(Object elem) {
		Aeropuerto obj = (Aeropuerto) elem;
		return this.nombreAeronautico.equals(obj.nombreAeronautico);
	}

	@Override
	public int compareTo(Object o) {
		Aeropuerto areopuertoParam = (Aeropuerto) o;
		return this.nombreAeronautico.compareTo(areopuertoParam.nombreAeronautico);
	}
}
