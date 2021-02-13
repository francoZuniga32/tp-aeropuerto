package grafos;

public class AdyacenteEtiquetado {
	private VerticeEtiquetado NodoVert;
	private AdyacenteEtiquetado NodoAdy;
	private double Etiqueta;
	
	public AdyacenteEtiquetado(VerticeEtiquetado nodoVert, AdyacenteEtiquetado nodoAdy, double etiqueta) {
		this.NodoVert = nodoVert;
		this.NodoAdy = nodoAdy;
		this.Etiqueta = etiqueta;
	}
	
	public VerticeEtiquetado getVertice() {
		return this.NodoVert;
	}
	
	public void setVertice(VerticeEtiquetado nodo) {
		this.NodoVert = nodo;
	}
	
	public AdyacenteEtiquetado getSiguienteAdy() {
		return this.NodoAdy;
	}
	
	public void setSiguienteAdy(AdyacenteEtiquetado nodo) {
		this.NodoAdy = nodo;
	}
	
	public double getEtiqueta() {
		return this.Etiqueta;
	}
	
	public void setEtiqueta(double elem) {
		this.Etiqueta = elem;
	}
}
