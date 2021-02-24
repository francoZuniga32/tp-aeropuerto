package grafos;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import java.util.HashMap;

public class GrafoEtiquetado {
	private VerticeEtiquetado inicio;

	public GrafoEtiquetado() {
		this.inicio = null;
	}

	public boolean insertarVertice(Object element) {
		boolean retorno = true;
		VerticeEtiquetado nodo = this.inicio;
		if (nodo == null) {
			this.inicio = new VerticeEtiquetado(element, null, null);
			retorno = true;
		} else {
			while (retorno && nodo.getSigVertice() != null) {
				if (nodo.getElemento().equals(element))
					retorno = false;
				nodo = nodo.getSigVertice();
			}
			if (retorno) {
				nodo.setSigVertice(new VerticeEtiquetado(element, null, null));
				retorno = true;
			}
		}

		return retorno;
	}

	/**
	 * eliminamos un vertice del grafo
	 * 
	 * @param elemento
	 * @return true si se elimino, false caso contrario
	 */
	public boolean eliminarVertice(Object elemento) {
		boolean retorno = false;
		VerticeEtiquetado nodo = this.inicio;
		if (nodo.getElemento().equals(elemento)) {
			this.inicio = nodo.getSigVertice();
		} else {
			VerticeEtiquetado sigNodo = nodo.getSigVertice();
			boolean control = true;
			while (sigNodo != null && control) {
				if (sigNodo.getElemento().equals(elemento)) {
					control = false;
				} else {
					sigNodo = sigNodo.getSigVertice();
					nodo = nodo.getSigVertice();
				}
			}
			if (!control) {
				nodo.setSigVertice(sigNodo.getSigVertice());
				retorno = true;
			}
		}

		return retorno;
	}

	public boolean insertarArco(Object elemA, Object elemB, double etiqueta) {
		boolean retorno = false;
		VerticeEtiquetado nodo = this.inicio;
		HashMap<Object, VerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
		VerticeEtiquetado verticeA = vertices.get(elemA);
		VerticeEtiquetado verticeB = vertices.get(elemB);
		if (verticeA != null && verticeB != null) {
			this.insertarAdyacente(verticeA, verticeB, etiqueta);
			this.insertarAdyacente(verticeB, verticeA, etiqueta);
			retorno = true;
		}

		return retorno;
	}

	public boolean eliminarArco(Object elemA, Object elemB) {
		boolean retorno = false;
		HashMap<Object, VerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
		VerticeEtiquetado verticeA = vertices.get(elemA);
		VerticeEtiquetado verticeB = vertices.get(elemB);
		if (verticeA != null && verticeB != null) {
			this.eliminarAdyacente(verticeA, verticeB);
			this.eliminarAdyacente(verticeB, verticeA);
			retorno = true;
		}

		return retorno;
	}

	public Object getEtiquetaArco(Object elemA, Object elemB) {
		Object retorno = null;
		HashMap<Object, VerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
		VerticeEtiquetado verticeA = vertices.get(elemA);
		VerticeEtiquetado verticeB = vertices.get(elemB);
		if (verticeA != null && verticeB != null) {
			AdyacenteEtiquetado aux = verticeA.getAdyacente();
			while (aux != null && retorno == null) {
				if (aux.getVertice().equals(verticeB))
					retorno = aux.getEtiqueta();
				aux = aux.getSiguienteAdy();
			}
			return retorno;
		}

		return retorno;
	}

	public boolean setEtiquetaArco(Object elemA, Object elemB, double etiquetaNueva) {
		boolean retorno = false;
		HashMap<Object, VerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
		VerticeEtiquetado verticeA = vertices.get(elemA);
		VerticeEtiquetado verticeB = vertices.get(elemB);
		if (verticeA != null && verticeB != null) {
			retorno = this.setEtiquetaAdyacenteAux(verticeA, verticeB, etiquetaNueva);
		}

		return retorno;
	}

	public boolean existeVertice(Object elem) {
		boolean retorno = false;
		VerticeEtiquetado aux = this.inicio;
		while (aux != null && !retorno) {
			if (aux.getElemento().equals(elem))
				retorno = true;
			aux = aux.getSigVertice();
		}
		return retorno;
	}

	public Object recuperarVertice(Object elem) {
		Object retorno = null;
		VerticeEtiquetado aux = this.inicio;
		while (aux != null && retorno == null) {
			if (aux.getElemento().equals(elem))
				retorno = aux.getElemento();
			aux = aux.getSigVertice();
		}
		return retorno;
	}

	public boolean existeArco(Object elemA, Object elemB) {
		boolean retorno = false;
		HashMap<Object, VerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
		VerticeEtiquetado verticeA = vertices.get(elemA);
		VerticeEtiquetado verticeB = vertices.get(elemB);
		if (verticeA != null && verticeB != null) {
			AdyacenteEtiquetado aux = verticeA.getAdyacente();
			while (aux != null && !retorno) {
				if (aux.getVertice().equals(verticeB))
					retorno = true;
				aux = aux.getSiguienteAdy();
			}
		}

		return retorno;
	}

	public boolean existeCamino(Object elemA, Object elemB) {
		Lista visitados = new Lista();
		boolean retorno = false;
		HashMap<Object, VerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
		VerticeEtiquetado verticeA = vertices.get(elemA);
		VerticeEtiquetado verticeB = vertices.get(elemB);
		if (verticeA != null && verticeB != null) {
			retorno = existeCaminoAux(verticeA, verticeB, visitados);
		}

		return retorno;
	}

	public Lista caminoMasCorto(Object elemA, Object elemB) {
		Lista camino = new Lista();
		Lista comparar = new Lista();
		HashMap<String, Object> visitados = new HashMap<String, Object>();
		HashMap<Object, VerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
		VerticeEtiquetado verticeA = vertices.get(elemA);
		VerticeEtiquetado verticeB = vertices.get(elemB);
		if (verticeA != null && verticeB != null) {
			camino = this.caminoMasCortoAux(verticeA, verticeB, visitados, camino, comparar);
		}
		return camino;
	}

	public Lista caminoMasLargo(Object elemA, Object elemB) {
		Lista camino = new Lista();
		Lista comparar = new Lista();
		HashMap<String, Object> visitados = new HashMap<String, Object>();
		HashMap<Object, VerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
		VerticeEtiquetado verticeA = vertices.get(elemA);
		VerticeEtiquetado verticeB = vertices.get(elemB);
		if (verticeA != null && verticeB != null) {
			camino = this.caminoMasLargoAux(verticeA, verticeB, visitados, camino, comparar);
		}
		return camino;
	}

	public Lista caminoConMasPeso(Object elemA, Object elemB) {
		Lista retorno = new Lista();
		HashMap<String, VerticeEtiquetado> visitados = new HashMap<String, VerticeEtiquetado>();
		HashMap<Object, VerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
		VerticeEtiquetado verticeA = vertices.get(elemA);
		VerticeEtiquetado verticeB = vertices.get(elemB);
		if (verticeA != null && verticeB != null) {
			retorno = this.caminoConMasPesoAux(verticeA, verticeB, visitados, retorno, 0, 0);
		}
		if (!retorno.esVacia())
			retorno = (Lista) retorno.recuperar(1);
		return (Lista) retorno;
	}

	public Lista caminoConMenosPeso(Object elemA, Object elemB) {
		Lista retorno = new Lista();
		Lista camino = new Lista();
		HashMap<String, VerticeEtiquetado> visitados = new HashMap<String, VerticeEtiquetado>();
		HashMap<Object, VerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
		VerticeEtiquetado verticeA = vertices.get(elemA);
		VerticeEtiquetado verticeB = vertices.get(elemB);
		if (verticeA != null && verticeB != null) {
			retorno = this.caminoConMenosPesoAux(verticeA, verticeB, visitados, camino, retorno, 0, 0);
		}
		if(!retorno.esVacia()) retorno = (Lista) retorno.recuperar(1);
		return retorno;
	}

	public Lista caminos(Object elemA, Object elemB) {
		Lista camino = new Lista();
		HashMap<String, Object> visitados = new HashMap<String, Object>();
		HashMap<Object, VerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
		VerticeEtiquetado verticeA = vertices.get(elemA);
		VerticeEtiquetado verticeB = vertices.get(elemB);
		if (verticeA != null && verticeB != null) {
			camino = this.caminosAux(verticeA, verticeB, visitados);
		}
		return camino;
	}

	private Lista concatenar(Lista l1, Lista l2) {
		Lista retorno = new Lista();
		int i = 1;

		while (i <= l1.longitud() + l2.longitud()) {
			if (i <= l1.longitud()) {
				retorno.insertar(l1.recuperar(i), i);
			} else {
				retorno.insertar(l2.recuperar(i - l1.longitud()), i - l1.longitud());
			}
			i++;
		}

		return retorno;
	}

	public Lista menorCaminoQueTiene(Object elemA, Object elemB, Object elemC) {
		Lista camino = new Lista();
		Lista comparar = new Lista();
		HashMap<String, VerticeEtiquetado> visitados = new HashMap<String, VerticeEtiquetado>();
		VerticeEtiquetado aux = this.inicio;
		VerticeEtiquetado verticeA = null;
		VerticeEtiquetado verticeB = null;
		VerticeEtiquetado verticeC = null;
		boolean corte = false;
		while (aux != null && !corte) {
			if (aux.getElemento().equals(elemA))
				verticeA = aux;
			else if (aux.getElemento().equals(elemB))
				verticeB = aux;
			else if (aux.getElemento().equals(elemC))
				verticeC = aux;
			corte = verticeA != null && verticeB != null && verticeC != null;
			aux = aux.getSigVertice();
		}
		if (verticeA != null && verticeB != null && verticeC != null) {
			comparar = this.menorCaminoQueTieneAux(verticeA, verticeB, verticeC, visitados, camino, comparar);
		}
		return comparar;
	}

	public Lista caminoMasRapidoQuePasa(Object elemA, Object elemB, Object elemC) {
		Lista camino = new Lista();
		Lista comparar = new Lista();
		HashMap<String, VerticeEtiquetado> visitados = new HashMap<String, VerticeEtiquetado>();

		VerticeEtiquetado aux = this.inicio;
		VerticeEtiquetado verticeA = null;
		VerticeEtiquetado verticeB = null;
		VerticeEtiquetado verticeC = null;
		boolean corte = false;
		while (aux != null && !corte) {
			if (aux.getElemento().equals(elemA))
				verticeA = aux;
			else if (aux.getElemento().equals(elemB))
				verticeB = aux;
			else if (aux.getElemento().equals(elemC))
				verticeC = aux;
			corte = verticeA != null && verticeB != null && verticeC != null;
			aux = aux.getSigVertice();
		}
		if (verticeA != null && verticeB != null && verticeC != null) {
			camino = this.caminoMasRapidoQuePasaAux(verticeA, verticeB, verticeC, visitados, camino, comparar, 0, 0);
		}
		if(!camino.esVacia()) camino = (Lista) camino.recuperar(1);
		return camino;
	}

	public Lista caminoConTopeDeVertices(Object elemA, Object elemB, int cantidadVertices) {
		Lista retorno = new Lista();
		Lista comparar = new Lista();
		HashMap<String, VerticeEtiquetado> visitados = new HashMap<String, VerticeEtiquetado>();
		HashMap<Object, VerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
		VerticeEtiquetado verticeA = vertices.get(elemA);
		VerticeEtiquetado verticeB = vertices.get(elemB);
		if (verticeA != null && verticeB != null) {
			comparar = this.caminoConTopeDeVerticesAux(verticeA, verticeB, cantidadVertices, visitados, retorno,
					comparar);
		}
		return comparar;
	}

	public Lista listarProfundidad() {
		Lista listados = new Lista();
		HashMap<String, Object> visitados = new HashMap<String, Object>();
		VerticeEtiquetado nodo = this.inicio;
		while (nodo != null) {
			if (visitados.get(nodo.getElemento().toString()) == null)
				this.listarProdundiadAux(nodo, listados, visitados);
			nodo = nodo.getSigVertice();
		}
		return listados;
	}

	public Lista listarAnchura() {
		Lista listados = new Lista();
		HashMap<String, Object> visitados = new HashMap<String, Object>();
		VerticeEtiquetado nodo = this.inicio;
		while (nodo != null) {
			if (visitados.get(nodo.getElemento().toString()) == null)
				this.listarAnchuraAux(nodo, listados, visitados);
			nodo = nodo.getSigVertice();
		}
		return listados;
	}

	public GrafoEtiquetado clone() {
		GrafoEtiquetado clone = new GrafoEtiquetado();
		VerticeEtiquetado nodo = this.inicio;
		HashMap<String, VerticeEtiquetado> nodos = new HashMap<String, VerticeEtiquetado>();
		if (nodo != null) {
			clone.inicio = new VerticeEtiquetado(nodo.getElemento(), null, null);
			VerticeEtiquetado nodoClone = clone.inicio;
			nodos.put(nodo.getElemento().toString(), nodoClone);
			nodo = nodo.getSigVertice();
			while (nodo != null) {
				nodoClone.setSigVertice(new VerticeEtiquetado(nodo.getElemento(), null, null));
				nodoClone = nodoClone.getSigVertice();
				nodos.put(nodoClone.getElemento().toString(), nodoClone);
				nodo = nodo.getSigVertice();
			}
			nodoClone = clone.inicio;
			nodo = this.inicio;
			while (nodoClone != null) {
				AdyacenteEtiquetado adyacenteNodo = nodo.getAdyacente();
				if (adyacenteNodo != null) {
					nodoClone.setAdyacente(
							new AdyacenteEtiquetado(nodos.get(adyacenteNodo.getVertice().getElemento().toString()),
									null, adyacenteNodo.getEtiqueta()));
					AdyacenteEtiquetado adyacenteClonado = nodoClone.getAdyacente();
					adyacenteNodo = adyacenteNodo.getSiguienteAdy();
					while (adyacenteNodo != null) {
						adyacenteClonado.setSiguienteAdy(
								new AdyacenteEtiquetado(nodos.get(adyacenteNodo.getVertice().getElemento().toString()),
										null, adyacenteNodo.getEtiqueta()));
						adyacenteNodo = adyacenteNodo.getSiguienteAdy();
						adyacenteClonado = adyacenteClonado.getSiguienteAdy();
					}
				}
				nodoClone = nodoClone.getSigVertice();
				nodo = nodo.getSigVertice();
			}
		}
		return clone;
	}

	public String toString() {
		String retorno = "Grafo Vacio";
		VerticeEtiquetado nodo = this.inicio;
		if (nodo != null) {
			retorno = "";
			while (nodo != null) {
				AdyacenteEtiquetado adyacente = nodo.getAdyacente();
				retorno += nodo.getElemento().toString() + ":";
				while (adyacente != null) {
					retorno += "->(" + adyacente.getVertice().getElemento().toString() + "," + adyacente.getEtiqueta()
							+ ")";
					adyacente = adyacente.getSiguienteAdy();
				}
				retorno += "\n";
				nodo = nodo.getSigVertice();
			}
		}
		return retorno;
	}

	private void listarAnchuraAux(VerticeEtiquetado nodo, Lista listados, HashMap<String, Object> visitados) {
		Cola q = new Cola();
		visitados.put(nodo.getElemento().toString(), inicio);
		q.poner(nodo);
		while (!q.esVacia()) {
			VerticeEtiquetado aux = (VerticeEtiquetado) q.obtenerFrente();
			listados.insertar(aux.getElemento(), listados.longitud() + 1);
			q.sacar();
			AdyacenteEtiquetado adyacente = aux.getAdyacente();
			while (adyacente != null) {
				if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
					q.poner(adyacente.getVertice());
					visitados.put(adyacente.getVertice().getElemento().toString(), adyacente.getVertice());
				}
				adyacente = adyacente.getSiguienteAdy();
			}
		}
	}

	private void listarProdundiadAux(VerticeEtiquetado nodo, Lista listados, HashMap<String, Object> visitados) {
		visitados.put(nodo.getElemento().toString(), inicio);
		listados.insertar(nodo.getElemento(), listados.longitud() + 1);
		AdyacenteEtiquetado aux = nodo.getAdyacente();
		while (aux != null) {
			if (visitados.get(aux.getVertice().getElemento().toString()) == null) {
				this.listarProdundiadAux(aux.getVertice(), listados, visitados);
			}
			aux = aux.getSiguienteAdy();
		}
	}

	private Lista caminoMasCortoAux(VerticeEtiquetado inicio, VerticeEtiquetado destino,
			HashMap<String, Object> visitados, Lista camino, Lista comparar) {
		if (inicio != null) {
			visitados.put(inicio.getElemento().toString(), inicio);
			camino.insertar(inicio.getElemento(), camino.longitud() + 1);
			if (comparar.esVacia() || camino.longitud() < comparar.longitud()) {
				if (inicio.equals(destino)) {
					comparar = camino.clone();
				} else {
					AdyacenteEtiquetado adyacente = inicio.getAdyacente();
					while (adyacente != null) {
						if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
							comparar = this.caminoMasCortoAux(adyacente.getVertice(), destino, visitados, camino,
									comparar);
						}
						adyacente = adyacente.getSiguienteAdy();
					}
				}
			}
			visitados.remove(inicio.getElemento().toString());
			camino.eliminar(camino.localizar(inicio.getElemento()));
		}
		return comparar;
	}

	private Lista caminoMasLargoAux(VerticeEtiquetado inicio, VerticeEtiquetado destino,
			HashMap<String, Object> visitados, Lista camino, Lista comparar) {
		Lista retorno = comparar;
		if (inicio != null) {
			visitados.put(inicio.getElemento().toString(), inicio);
			camino.insertar(inicio.getElemento(), camino.longitud() + 1);

			if (comparar.esVacia() || camino.longitud() >= comparar.longitud()) {
				if (inicio.equals(destino)) {
					retorno = camino.clone();
				} else {
					AdyacenteEtiquetado adyacente = inicio.getAdyacente();
					while (adyacente != null) {
						if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
							if (comparar.esVacia()) {
								comparar = this.caminoMasLargoAux(adyacente.getVertice(), destino, visitados, camino,
										comparar);
							} else {
								comparar = this.caminoMasLargoAux(adyacente.getVertice(), destino, visitados, camino,
										comparar);
							}
						}
						adyacente = adyacente.getSiguienteAdy();
					}
					retorno = comparar;
				}
			}
			visitados.remove(inicio.getElemento().toString());
			camino.eliminar(camino.localizar(inicio.getElemento()));
		}
		return retorno;
	}

	private Lista caminoConMasPesoAux(VerticeEtiquetado inicio, VerticeEtiquetado destino,
			HashMap<String, VerticeEtiquetado> visitados, Lista camino, double peso, double comparar) {
		Lista retorno = new Lista();
		if (inicio != null) {
			visitados.put(inicio.getElemento().toString(), inicio);
			camino.insertar(inicio.getElemento(), camino.longitud() + 1);
			if (inicio.equals(destino)) {
				retorno.insertar(camino.clone(), 1);
				retorno.insertar(peso, 2);
			} else {
				AdyacenteEtiquetado adyacente = inicio.getAdyacente();
				Lista aux = new Lista();
				while (adyacente != null) {
					if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
						if (retorno.esVacia()) {
							retorno = this.caminoConMasPesoAux(adyacente.getVertice(), destino, visitados, camino,
									peso + adyacente.getEtiqueta(), comparar);
						} else {
							aux = this.caminoConMasPesoAux(adyacente.getVertice(), destino, visitados, camino,
									peso + adyacente.getEtiqueta(), (Double) retorno.recuperar(2));
							if (!aux.esVacia() && (Double) aux.recuperar(2) >= comparar)
								retorno = aux;
						}
					}
					adyacente = adyacente.getSiguienteAdy();
				}
			}
			visitados.remove(inicio.getElemento().toString());
			camino.eliminar(camino.localizar(inicio.getElemento()));
		}
		return retorno;
	}
	
	private Lista caminosAux(VerticeEtiquetado nodo, VerticeEtiquetado destino, HashMap<String, Object> visitados) {
		Lista retorno = new Lista();
		if (nodo != null) {
			visitados.put(nodo.getElemento().toString(), nodo);
			if (nodo.equals(destino)) {
				Lista lista = new Lista();
				lista.insertar(nodo.getElemento(), 1);
				retorno.insertar(lista, 1);
			} else {
				AdyacenteEtiquetado adyacente = nodo.getAdyacente();
				while (adyacente != null) {
					if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
						Lista listaRetorno = caminosAux(adyacente.getVertice(), destino, visitados);
						retorno = this.concatenar(listaRetorno, retorno);
					}
					adyacente = adyacente.getSiguienteAdy();
				}
				int i = 1;
				while (i <= retorno.longitud()) {
					Lista lista = (Lista) retorno.recuperar(i);
					retorno.eliminar(i);
					lista.insertar(nodo.getElemento(), 1);
					retorno.insertar(lista, i);
					i++;
				}
			}
		}
		visitados.remove(nodo.getElemento().toString());
		return retorno;
	}
	
	private Lista menorCaminoQueTieneAux(VerticeEtiquetado inicio, VerticeEtiquetado destino,
			VerticeEtiquetado incluido, HashMap<String, VerticeEtiquetado> visitados, Lista camino, Lista comparar) {
		if (inicio != null) {
			visitados.put(inicio.getElemento().toString(), inicio);
			camino.insertar(inicio.getElemento(), camino.longitud() + 1);
			System.out.println("Inicio: "+inicio.getElemento().toString()+" Destino: "+destino.getElemento().toString()+" camino: "+camino.toString()+"comparar: "+comparar.toString());
			if (comparar.esVacia() || camino.longitud() < comparar.longitud()) {

				if (inicio.equals(destino)) {
					if (camino.localizar(incluido.getElemento()) != -1)
						comparar = camino.clone();
				} else {
					AdyacenteEtiquetado adyacente = inicio.getAdyacente();
					while (adyacente != null) {
						if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
							comparar = this.menorCaminoQueTieneAux(adyacente.getVertice(), destino, incluido, visitados,
									camino, comparar);
						}
						adyacente = adyacente.getSiguienteAdy();
					}
				}
			}
			visitados.remove(inicio.getElemento().toString());
			camino.eliminar(camino.localizar(inicio.getElemento()));
		}
		return comparar;
	}
	
	private Lista caminoMasRapidoQuePasaAux(VerticeEtiquetado inicio, VerticeEtiquetado destino, VerticeEtiquetado incluido,
			HashMap<String, VerticeEtiquetado> visitados, Lista camino, Lista retorno, double peso, double pesoComparar) {
		if (inicio != null) {
			visitados.put(inicio.getElemento().toString(), inicio);
			camino.insertar(inicio.getElemento(), camino.longitud() + 1);
			if (inicio.equals(destino)) {
				if (pesoComparar == 0 || peso < pesoComparar && camino.localizar(incluido.getElemento()) != -1 ) {
					retorno.insertar(camino.clone(), 1);
					retorno.insertar(peso, 2);
				}
			} else {
				AdyacenteEtiquetado adyacente = inicio.getAdyacente();
				Lista aux = new Lista();
				while (adyacente != null) {
					if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
						double pesoAdyacente = peso + adyacente.getEtiqueta();
						if (pesoComparar == 0 || pesoAdyacente < pesoComparar)
							
							if(retorno.esVacia()) retorno = this.caminoMasRapidoQuePasaAux(adyacente.getVertice(), incluido, destino, visitados, camino, retorno, pesoAdyacente, pesoComparar);
							else retorno = this.caminoMasRapidoQuePasaAux(adyacente.getVertice(), destino, incluido, visitados, camino, retorno, pesoAdyacente, (double) retorno.recuperar(2));
					}
					adyacente = adyacente.getSiguienteAdy();
				}
			}
			visitados.remove(inicio.getElemento().toString());
			camino.eliminar(camino.localizar(inicio.getElemento()));
		}
		return retorno;
	}
	
	private Lista caminoConTopeDeVerticesAux(VerticeEtiquetado inicio, VerticeEtiquetado destino, int vertices,
			HashMap<String, VerticeEtiquetado> visitados, Lista camino, Lista comparar) {
		if (inicio != null) {
			visitados.put(inicio.getElemento().toString(), inicio);
			camino.insertar(inicio.getElemento(), camino.longitud() + 1);
			if (camino.longitud() <= vertices && comparar.esVacia() || camino.longitud() < comparar.longitud()) {
				System.out.println("camino: " + camino.toString() + " comparar: " + comparar.toString());
				if (inicio.equals(destino)) {
					comparar = camino.clone();
				} else {
					AdyacenteEtiquetado adyacente = inicio.getAdyacente();
					while (adyacente != null) {
						if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
							comparar = this.caminoConTopeDeVerticesAux(adyacente.getVertice(), destino, vertices,
									visitados, camino, comparar);
						}
						adyacente = adyacente.getSiguienteAdy();
					}
				}
			}
			visitados.remove(inicio.getElemento().toString());
			camino.eliminar(camino.localizar(inicio.getElemento()));
		}
		System.out.println(comparar.toString());
		return comparar;
	}

	private Lista caminoConMenosPesoAux(VerticeEtiquetado inicio, VerticeEtiquetado destino,
			HashMap<String, VerticeEtiquetado> visitados, Lista camino, Lista retorno, double peso, double pesoComparar) {
		if (inicio != null) {
			visitados.put(inicio.getElemento().toString(), inicio);
			camino.insertar(inicio.getElemento(), camino.longitud() + 1);
			if (inicio.equals(destino)) {
				if (pesoComparar == 0 || peso < pesoComparar) {
					retorno.insertar(camino.clone(), 1);
					retorno.insertar(peso, 2);
				}
			} else {
				AdyacenteEtiquetado adyacente = inicio.getAdyacente();
				Lista aux = new Lista();
				while (adyacente != null) {
					if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
						double pesoAdyacente = peso + adyacente.getEtiqueta();
						if (pesoComparar == 0 || pesoAdyacente < pesoComparar)
							
							if(retorno.esVacia()) retorno = this.caminoConMenosPesoAux(adyacente.getVertice(), destino, visitados, camino, retorno, pesoAdyacente, pesoComparar);
							else retorno = this.caminoConMenosPesoAux(adyacente.getVertice(), destino, visitados, camino, retorno, pesoAdyacente, (double) retorno.recuperar(2));
					}
					adyacente = adyacente.getSiguienteAdy();
				}
			}
			visitados.remove(inicio.getElemento().toString());
			camino.eliminar(camino.localizar(inicio.getElemento()));
		}
		return retorno;
	}

	private boolean existeCaminoAux(VerticeEtiquetado inicio, VerticeEtiquetado destino, Lista visitados) {
		boolean retorno = false;

		if (inicio != null) {
			if (inicio.getElemento().equals(destino.getElemento())) {
				retorno = true;
			} else {
				visitados.insertar(inicio.getElemento(), visitados.longitud() + 1);
				AdyacenteEtiquetado aux = inicio.getAdyacente();
				while (!retorno && aux != null) {
					if (visitados.localizar(aux.getVertice().getElemento()) < 0) {
						retorno = existeCaminoAux(aux.getVertice(), destino, visitados);
					}
					aux = aux.getSiguienteAdy();
				}
			}
		}
		return retorno;
	}

	private boolean setEtiquetaAdyacenteAux(VerticeEtiquetado nodo, VerticeEtiquetado adyacente, double etiquetaNueva) {
		boolean retorno = false;
		AdyacenteEtiquetado aux = nodo.getAdyacente();
		boolean control = true;
		while (aux != null && control) {
			if (aux.getVertice().equals(adyacente)) {
				aux.setEtiqueta(etiquetaNueva);
				control = false;
				retorno = true;
			}
			aux = aux.getSiguienteAdy();
		}
		return retorno;
	}

	private boolean insertarAdyacente(VerticeEtiquetado nodo, VerticeEtiquetado adyacente, double etiqueta) {
		boolean retorno = false;
		if (nodo != null) {
			AdyacenteEtiquetado aux = nodo.getAdyacente();
			if (aux != null) {
				boolean control = true;
				while (aux.getSiguienteAdy() != null && control) {
					aux = aux.getSiguienteAdy();
				}
				aux.setSiguienteAdy(new AdyacenteEtiquetado(adyacente, null, etiqueta));
				retorno = true;
			} else {
				nodo.setAdyacente(new AdyacenteEtiquetado(adyacente, null, etiqueta));
				retorno = true;
			}
		}
		return retorno;
	}

	private boolean eliminarAdyacente(VerticeEtiquetado nodo, Object elemento) {
		boolean retorno = false;
		if (nodo != null) {
			AdyacenteEtiquetado adyacente = nodo.getAdyacente();
			if (adyacente.getVertice().equals(elemento)) {
				nodo.setAdyacente(adyacente.getSiguienteAdy());
				retorno = true;
			} else {
				AdyacenteEtiquetado siguienteAdyacente = adyacente.getSiguienteAdy();
				while (!siguienteAdyacente.getVertice().equals(elemento)) {
					siguienteAdyacente = siguienteAdyacente.getSiguienteAdy();
					adyacente = adyacente.getSiguienteAdy();
				}
				if (siguienteAdyacente != null) {
					adyacente.setSiguienteAdy(siguienteAdyacente.getSiguienteAdy());
					retorno = true;
				}
			}
		}
		return retorno;
	}

	private HashMap<Object, VerticeEtiquetado> ubicarVertices(Object elemA, Object elemB) {
		boolean corte = false;
		HashMap<Object, VerticeEtiquetado> retorno = new HashMap<Object, VerticeEtiquetado>();
		VerticeEtiquetado aux = this.inicio;
		while (aux != null && !corte) {
			if (aux.getElemento().equals(elemA)) {
				VerticeEtiquetado verticeA = aux;
				retorno.put(elemA, verticeA);
			} else if (aux.getElemento().equals(elemB)) {
				VerticeEtiquetado verticeB = aux;
				retorno.put(elemB, verticeB);
			}

			if (retorno.get(elemA) != null && retorno.get(elemB) != null)
				corte = true;
			aux = aux.getSigVertice();
		}
		return retorno;
	}
}
