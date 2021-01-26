package Objetos;

public class Promocion implements Comparable{
	private Cliente cliente;
	private int cantidadPasaje;
	
	public Promocion(Cliente cliente, int cantidad) {
		this.cliente = cliente;
		this.cantidadPasaje = cantidad;
	}
	
	public Cliente getCliente() {
		return this.cliente;
	}
	
	public int getCantidadPasaje() {
		return this.cantidadPasaje;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void setCantidadPasaje(int cantidad) {
		this.cantidadPasaje = cantidad;
	}
	
	public String toString() {
		return this.cliente.toString();
	}

	@Override
	public int compareTo(Object obj) {
		// TODO Auto-generated method stub
		Promocion elem = (Promocion) obj;
		return this.cantidadPasaje - elem.cantidadPasaje;
	}
}
