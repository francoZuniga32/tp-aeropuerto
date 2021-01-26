package Objetos;

public class Programado {
	private String fecha;
	private boolean[] asientos;
	
	public Programado(String fechaParam, int asientosParam) {
		this.fecha = fechaParam;
		this.asientos = new boolean[asientosParam];
		for (int i = 0; i < this.asientos.length; i++) {
			this.asientos[i] = true;
		}
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public void setAsientos(int asientos) {
		this.asientos = new boolean[asientos];
	}
	
	public void setReservados(int asiento) {
		if(!this.asientos[asiento]) this.asientos[asiento] = false;
	}
	
	public String getFecha() {
		return this.fecha;
	}
	
	public boolean[] getAsientos() {
		return this.asientos;
	}
	
	public boolean asientosLibres() {
		boolean retorno = false;
		int i = 0;
		while(!retorno && i < this.asientos.length) {
			retorno = this.asientos[i];
			i++;
		}
		return retorno;
	}
	
	public boolean estaLibre(int asiento) {
		return this.asientos[asiento];
	}
	
	public String toString() {
		return this.fecha;
	}
}
