package Objetos;
import lineales.dinamicas.*;

public class Vuelo implements Comparable{
	private String identificacion;
	private int codigo;
	private Aeropuerto aeropuertoOrigen;
	private Aeropuerto aeropuertoDestino;
	private String horaDeSalida;
	private String horaDeLlegada;
	private Lista programados;
	
	public Vuelo(String identificacionParam, int codigoParam) {
		this.identificacion = identificacionParam;
		this.codigo = codigoParam;
		this.programados = new Lista();
	}
	
	public Vuelo(String identificacionParam, int codigo, Aeropuerto aeropuertoOrigen, Aeropuerto aeropuertoDestino, String horaSalidaParam, String horaLlegadaParam) {
		this.identificacion = identificacionParam;
		this.codigo = codigo;
		this.aeropuertoOrigen = aeropuertoOrigen;
		this.aeropuertoDestino = aeropuertoDestino;
		this.horaDeLlegada = horaLlegadaParam;
		this.horaDeSalida = horaSalidaParam;
		this.programados = new Lista();
	}
	
	public String getIdentificacionCodigo() {
		return this.identificacion+this.codigo;
	}
	
	public Aeropuerto getAeropuertoOrigen() {
		return this.aeropuertoOrigen;
	}
	
	public Aeropuerto getAeropuertoDestino() {
		return this.aeropuertoDestino;
	}
	
	public String getHoraDeSalida() {
		return this.horaDeSalida;
	}
	
	public String getHoraDeLlegada() {
		return this.horaDeLlegada;
	}
	
	public Lista getProgramados() {
		return this.programados;
	}
	
	public void setAeropuertoOrigen(Aeropuerto aeropuerto) {
		this.aeropuertoOrigen = aeropuerto;
	}
	
	public void setAeropuertoDestino(Aeropuerto aeropuerto) {
		this.aeropuertoDestino = aeropuerto;
	}
	
	public void setHoraDeSalida(String horaDeSalida) {
		this.horaDeSalida = horaDeSalida;
	}
	
	public void setHoraDeLlegada(String horaDeLlegada) {
		this.horaDeLlegada = horaDeLlegada;
	}
	
	public void setProgramado(Programado programado) {
		this.programados.insertar(programado, this.programados.longitud() +1);
	}
	
	public String toString() {
		return "("+this.identificacion+this.codigo+")";
	}

	@Override
	public int compareTo(Object elem) {
		Vuelo objeto = (Vuelo) elem;
		int retorno = 0;
		int compareTipo = this.identificacion.compareTo(objeto.identificacion);
		int compareNumero = this.codigo - objeto.codigo;
		if(compareTipo != 0) {
			retorno = compareTipo;
		}else {
			retorno = compareNumero;
		}
		return retorno;
	}
}