package Objetos;

public class Pasaje {
	private Vuelo vuelo;
	private String fecha;
	private int asiento;
	private String estado;
	
	public Pasaje(Vuelo vueloParam, String fechaParam, int asientoParam, String estadoParam) {
		this.vuelo = vueloParam;
		this.fecha = fechaParam;
		this.asiento = asientoParam;
		this.estado = estadoParam;
	}
	
	public void setAsiento(int asientoParam) {
		this.asiento = asientoParam;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public Vuelo getVuelo() {
		return this.vuelo;
	}
	
	public String getFecha() {
		return this.fecha;
	}
	
	public int getAsiento() {
		return this.asiento;
	}
	
	public String getEstado() {
		return this.estado;
	}
	
	public String toString() {
		return "(["+this.vuelo.toString()+"],"+this.fecha+", "+this.estado+")";
	}
}
