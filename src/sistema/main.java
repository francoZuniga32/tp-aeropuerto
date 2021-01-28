package sistema;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import Objetos.Aeropuerto;
import Objetos.Cliente;
import Objetos.Pasaje;
import Objetos.Programado;
import Objetos.Promocion;
import Objetos.Vuelo;
import conjuntitas.dinamico.AVL;
import conjuntitas.estaticas.ArbolHeapMaximo;
import grafos.GrafoEtiquetado;
import lineales.dinamicas.Lista;

public class main {
static TecladoIn teclado = new TecladoIn();
	
	public static void main(String[] args) throws FileNotFoundException{
		//las estructuras donde almacenamos los datos del sistema
		AVL tablaVuelos = new AVL();
		AVL tablaClientes = new AVL();
		GrafoEtiquetado aeropuertos = new GrafoEtiquetado();
		HashMap<Cliente, Lista> pasajes = new HashMap<Cliente, Lista>();
		HashMap<String, String> listaAeropuerto = new HashMap<String, String>();
		int opcion;
		cargarAeropuerto(aeropuertos);
		cargarRutas(aeropuertos);
		cargarClientes(tablaClientes);
		cargarVuelos(tablaVuelos, aeropuertos);
		cargarPasajes(tablaClientes, tablaVuelos, pasajes);
		do {
			opcion = menu();
			switch (opcion) {
				case 1:
					ABMaeropuerto(aeropuertos);
					break;
				case 2:
					ABMCliente(tablaClientes);
					break;
				case 3:
					ABMvuelos(tablaVuelos, aeropuertos);
					break;
				case 4:
					ABMpasajes(tablaClientes, tablaVuelos, pasajes);
					break;
				case 5:
					operacionesCliente(tablaClientes, pasajes);
					break;
				case 6:
					operacionesVuelos(tablaVuelos);
					break;
				case 7:
					consultaTiemposDeViaje(aeropuertos);
					break;
				case 8:
					promociones(tablaClientes, pasajes);
					break;
				case 9:
					mostrarSistema(tablaClientes, tablaVuelos, aeropuertos, pasajes);
					break;
				case 10:
					escribir("Finalizando programa...");
					break;
				default:
					escribir("Ingrese una opcion valida");
					break;
			}
		}while(opcion != 10);
	}
	
	public static void createLog() {
		 try {
	         //Creo el PrintWriter que referencia al archivo LOG creado
	         PrintWriter arc = new PrintWriter(new File("log.txt"));
	     } catch (IOException e) {
	    	 System.out.println("Error al crear el archivo LOG:" + e);
	     }
	}
	
	public static int menu() {
		int opcion = 0;
		escribir("-- Seleccione una de las opciones : --\n"
				+ "1. ABM (Altas-Bajas-Modificaciones) de aeropuertos\n"
				+ "2. ABM (Altas-Bajas-Modificaciones) de clientes\n"
				+ "3. ABM (Altas-Bajas-Modificaciones) de vuelos\n"
				+ "4. ABM (Altas-Bajas-Modificaciones) de pasajes\n"
				+ "5. Operaciones Cliente\n"
				+ "6. Operaciones Vuelos\n"
				+ "7. Tiempos de viaje \n"
				+ "8. Promociones\n"
				+ "9. TEST\n"
				+ "10. salir");
		opcion = leerInt();
		return opcion;
	}
	
	public static void cargarAeropuerto(GrafoEtiquetado aeropuerto) throws FileNotFoundException {
		Scanner sc = cargarArchivo("./aeropuertos.txt");
        String[] datos = new String[10];
        while (sc.hasNextLine()) {
            //Los ";" separan los datos de cada linea y el primer dato determina que es lo que se va a cargar
            datos = sc.nextLine().split(";");
            if(datos.length == 3) {
            	String nombreAeronautico = datos[0];
            	String nombre = datos[1];
            	String telefono = datos[2];
            	aeropuerto.insertarVertice(new Aeropuerto(nombreAeronautico, nombre, telefono));	
            }
        }
	}
	
	public static void cargarRutas(GrafoEtiquetado aeropuerto) throws FileNotFoundException {
		Scanner sc = cargarArchivo("./rutas.txt");
        String[] datos = new String[10];
        while (sc.hasNextLine()) {
            //Los ";" separan los datos de cada linea y el primer dato determina que es lo que se va a cargar
            datos = sc.nextLine().split(";");
            if(datos.length == 3) {
            	Aeropuerto aeropuertoOrigen = new Aeropuerto(datos[0]);
            	Aeropuerto aeropuertoDestino = new Aeropuerto(datos[1]);
            	double tiempoViaje = Double.parseDouble(datos[2]);
            	aeropuerto.insertarArco(aeropuertoOrigen, aeropuertoDestino, tiempoViaje);
            }
        }
	}
	
	public static void cargarClientes(AVL clientes) throws FileNotFoundException {
		Scanner sc = cargarArchivo("./clientes.txt");
        String[] datos = new String[10];
        while (sc.hasNextLine()) {
            //Los ";" separan los datos de cada linea y el primer dato determina que es lo que se va a cargar
            datos = sc.nextLine().split(";");
            if(datos.length == 7) {
            	String tipo = datos[0];
                int numero = Integer.parseInt(datos[1]);
                String nombre = datos[2];
                String apellido = datos[3];
                String fechaNacimiento = datos[4];
                String domicilio = datos[5];
                String telefono = datos[6];
                Cliente cliente = new Cliente(tipo, numero);
                clientes.insertar(cliente);
                cliente.setNombre(nombre);
                cliente.setApellido(apellido);
                cliente.setFechaNacimiento(fechaNacimiento);
                cliente.setDomicilio(domicilio);
                cliente.setTelefono(telefono);
            }
        }
	}
	
	public static void cargarVuelos(AVL vuelos, GrafoEtiquetado aeropuertos) throws FileNotFoundException {
		
		Scanner sc = cargarArchivo("./vuelos.txt");
        String[] datos;
        while (sc.hasNextLine()) {
            //Los ";" separan los datos de cada linea y el primer dato determina que es lo que se va a cargar
            datos = sc.nextLine().split(";");
            String siglas = datos[0];
            if(datos.length == 6) {
            	int numero = Integer.parseInt(datos[1]);
                Aeropuerto origen = (Aeropuerto) obtenerAeropuertosAux(aeropuertos, datos[2]); 
                Aeropuerto destino = (Aeropuerto) obtenerAeropuertosAux(aeropuertos, datos[3]);
                String fechaSalida = datos[4];
                String fechaLlegada = datos[5];
                vuelos.insertar(new Vuelo(siglas, numero, origen, destino, fechaSalida, fechaSalida));
            }
        }
	}
	
	public static void cargarPasajes(AVL clientes, AVL vuelos, HashMap<Cliente, Lista> pasajes) throws FileNotFoundException {
		Scanner sc = cargarArchivo("./pasajes.txt");
		String[] datos;
		while (sc.hasNextLine()) {
            //Los ";" separan los datos de cada linea y el primer dato determina que es lo que se va a cargar
            datos = sc.nextLine().split(";");
            if(datos.length > 6) {
            	String tipoDocumento = datos[0];
            	int numeroDocumento = Integer.parseInt(datos[1]);
            	String siglasAerolineas = datos[2];
            	int numeroVuelo = Integer.parseInt(datos[3]);
            	String fecha = datos[4];
            	int asientos = Integer.parseInt(datos[5]);
            	String estado = datos[6];
            	
            	Object comprador = obtenerClienteAux(clientes, tipoDocumento, numeroDocumento);
            	if(comprador != null) {
                	Cliente clienteRecuperado = (Cliente) comprador;
                	Lista pasajesCliente = (Lista) pasajes.get(comprador);
                    if(pasajesCliente != null) {
                    	//en caso de que ya tenga pasajes
                    	Vuelo vueloRecuperado = (Vuelo) vuelos.recuperar(new Vuelo(siglasAerolineas, numeroVuelo));
                    	Lista programados = vueloRecuperado.getProgramados();
                    	pasajesCliente.insertar(new Pasaje(vueloRecuperado, fecha, asientos, estado), pasajesCliente.longitud() + 1);
                    	
                    }else {
                    	//en caso de no tener pasajes
                    	pasajesCliente = new Lista();
                    	Vuelo vueloRecuperado = (Vuelo) vuelos.recuperar(new Vuelo(siglasAerolineas, numeroVuelo));
                    	pasajesCliente.insertar(new Pasaje(vueloRecuperado, fecha, asientos, estado), pasajesCliente.longitud() + 1);
                    	pasajes.put(clienteRecuperado, pasajesCliente);
                    }
                }
            }
        }
	}
	
	public static Scanner cargarArchivo(String archivo) throws FileNotFoundException {
		File f = new File(archivo);
        Scanner sc = new Scanner(f, "UTF-8");
        return sc;
	}
	
	public static void escribir(Object entrada) {
		System.out.println(entrada.toString());
		FileWriter fichero = null;
        PrintWriter pw = null;
        try{
            fichero = new FileWriter("log.txt", true);
            pw = new PrintWriter(fichero);

            pw.println(entrada);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}
	
	public static String leerString() {
		String retorno = TecladoIn.readLine();
		escribir(retorno);
		return retorno;
	}
	
	public static int leerInt() {
		int retorno = TecladoIn.readLineInt();
		escribir(retorno);
		return retorno;
	}
	
	public static double leerDouble() {
		double retorno = TecladoIn.readLineDouble();
		escribir(retorno);
		return retorno;
	}
	
	//ABM aeropuetos
	
	public static void ABMaeropuerto(GrafoEtiquetado aeropuertos) {
		int opcion = 0;
		do {
			opcion = ABMaeropuertoMenu();
			switch(opcion) {
				case 1:
					altasAeropuerto(aeropuertos);
					break;
				case 2:
					bajasAeropuerto(aeropuertos);
					break;
				case 3:
					modificacionAeropuerto(aeropuertos);
					break;
				case 4:
					ABMrutas(aeropuertos);
					break;
				case 5:
					escribir("finalizando ABM aeropuertos");
					break;
				default:
					escribir("La opcion ingresada no es correcta");
				break;
			}
		}while(opcion != 5);
	}
	
	public static int ABMaeropuertoMenu() {
		int opcion = 0;
		escribir("-- Seleccione una de las opciones : --\n"
				+ "1. Alta aeropuerto\n"
				+ "2. Baja aeropuerto\n"
				+ "3. Modificacion aeropuerto\n"
				+ "4. ABM Rutas\n"
				+ "5. salir");
		return leerInt();
	}
	
	public static void altasAeropuerto(GrafoEtiquetado aeropuertos) {
		Aeropuerto nuevoAeropuerto = obtenerNuevoAeropuerto(aeropuertos);
		if(!aeropuertos.existeVertice(nuevoAeropuerto)) {
			escribir("Ingrese el nombre del aeropuerto:");
			String aeropuertoNombre = leerString();
			escribir("Ingrese el numero de telefono:");
			String aeropuertoTelefono = leerString();
			nuevoAeropuerto.setCiudad(aeropuertoNombre);
			nuevoAeropuerto.setTelefono(aeropuertoTelefono);
			aeropuertos.insertarVertice(nuevoAeropuerto);
		}else {
			escribir("Dicho aeropuerto ya existe");
		}
	}
	
	public static void bajasAeropuerto(GrafoEtiquetado aeropuertos) {
		Aeropuerto aeropuertoBuscado = obtenerAeropuerto(aeropuertos);
		if(aeropuertos.eliminarVertice(aeropuertoBuscado))escribir("Se ha eliminado exitosamente");
		else escribir("El aeropuerto ingresado no existe");
	}
	
	public static void modificacionAeropuerto(GrafoEtiquetado aeropuertos) {
		Aeropuerto aeropuertoBuscado = obtenerAeropuerto(aeropuertos);
		if(aeropuertos.existeVertice(aeropuertoBuscado)) {
			Aeropuerto aeropuertoRecuperado = (Aeropuerto) aeropuertos.recuperarVertice(aeropuertoBuscado);
			escribir("Ingrese el nuevo telefono:");
			String nuevoTelefono = leerString();
			aeropuertoRecuperado.setTelefono(nuevoTelefono);
		}else {
			escribir("El aeropuerto ingresado no existe");
		}
	}
	
	//ABM ruras
	
	public static void ABMrutas(GrafoEtiquetado aeropuertos) {
		int opcion = 0;
		do {
			opcion = ABMrutasMenu();
			switch (opcion) {
			case 1:
				altaRuta(aeropuertos);
				break;
			case 2:
				bajaRuta(aeropuertos);
				break;
			case 3:
				escribir("Finalizado ABM rutas");
				break;
			default:
				escribir("La opcion ingresada no es correcta");
				break;
			}
		}while(opcion != 3);
	}
	
	public static int ABMrutasMenu() {
		int opcion = 0;
		escribir("-- Seleccione una de las opciones : --\n"
				+ "1. Alta Ruta\n"
				+ "2. Baja Ruta\n"
				+ "3. salir");
		return leerInt();
	}
	
	public static void altaRuta(GrafoEtiquetado aeropuertos) {
		escribir("ingrese el aeropuerto de origen");
		Aeropuerto aeropuertoOrigen = obtenerAeropuerto(aeropuertos);
		escribir("ingrese el eropuerto de destino");
		Aeropuerto aeropuertoDestino = obtenerAeropuerto(aeropuertos);
		escribir("Ingrese el tiempo de viaje entre aeropuertos (hs)");
		double tiempo = leerDouble();
		if(aeropuertos.insertarArco(aeropuertoOrigen, aeropuertoDestino, tiempo)) escribir("La ruta fue creada con exito");
		else escribir("ups ha pasado algo creado la ruta");
	}
	
	public static void bajaRuta(GrafoEtiquetado aeropuertos) {
		escribir("ingrese el aeropuerto de origen");
		Aeropuerto aeropuertoOrigen = obtenerAeropuerto(aeropuertos);
		escribir("ingrese el eropuerto de destino");
		Aeropuerto aeropuertoDestino = obtenerAeropuerto(aeropuertos);
		if(aeropuertos.eliminarArco(aeropuertoOrigen, aeropuertoDestino)) escribir("La ruta fue eliminada con exito");
		else escribir("ups, la ruta ingresada no existia o ha pasado otra cosa :( ");
	}
	
	
	//ABM cliete
	
	public static int ABMclienteMenu() {
		int opcion = 0;
		escribir("-- Seleccione una de las opciones : --\n"
				+ "1. Alta cliente\n"
				+ "2. Baja cliente\n"
				+ "3. Modificacion cliente\n"
				+ "4. salir");
		return leerInt();
	}
	
	public static void ABMCliente(AVL clientes) {
		int opcion = 0;
		do {
			opcion = ABMclienteMenu();
			switch (opcion) {
			case 1:
				altaCliente(clientes);
				break;
			case 2:
				bajaCliente(clientes);
				break;
			case 3:
				modificarCliente(clientes);
				break;
			case 4:
				escribir("finalizando ABM clientes");
				break;
			default:
				escribir("La opcion ingresada no es correcta");
			break;
			}
		}while(opcion != 4);
	}
	
	public static void altaCliente(AVL clientes) {
		Cliente nuevoCliente = obtenerNuevoCliente(clientes);
		if(clientes.insertar(nuevoCliente)) {
			escribir("Ingresar nombre: ");
			String nombreParam = leerString();
			escribir(nombreParam);
			escribir("Ingresar apellido: ");
			nuevoCliente.setApellido(leerString());
			escribir("Ingrese direccion: ");
			nuevoCliente.setDomicilio(leerString());
			escribir("Ingrese fecha de nacimiento: ");
			nuevoCliente.setFechaNacimiento(leerString());
			escribir("Ingrese telefono: ");
			nuevoCliente.setNombre(leerString());
			escribir("El usuario fue creado con exito");
		}else {
			escribir("El usuario ya existe");
		}
	}
	
	public static void bajaCliente(AVL clientes) {
		Cliente nuevoCliente = obtenerCliente(clientes);
		if(!clientes.eliminar(nuevoCliente)) {
			escribir("El usuario no Existe!!");
		}else {
			escribir("El usuario fue eliminado con exito");
		}
	}
	
	public static void modificarCliente(AVL clientes) {
		Cliente nuevoCliente = obtenerCliente(clientes);
		if(clientes.recuperar(nuevoCliente) != null) {
			Cliente cliente = (Cliente) nuevoCliente;
			escribir("Ingrese el nuevo telefono:");
			cliente.setTelefono(leerString());
			escribir("Ingrese la nueva direccion:");
			cliente.setDomicilio(leerString());
		}else {
			escribir("El usuario no existe!!");
		}
	}
	
	//ABM vuelos
	
	public static void ABMvuelos(AVL vuelos, GrafoEtiquetado aeropuertos) {
		int opcion = 0;
		do {
			opcion = ABMvuelosMenu();
			switch (opcion) {
			case 1:
				altasVuelos(vuelos, aeropuertos);
				break;
			case 2:
				bajasVuelos(vuelos);
				break;
			case 3:
				modificacionesVuelos(vuelos);
				break;
			case 4:
				escribir("finalizando ABM vuelos");
				break;
			default:
				escribir("La opcion ingresa no es correcta");
				break;
			}			
		}while(opcion != 4);
	}
	
	public static int ABMvuelosMenu() {
		int opcion = 0;
		do {
			escribir("-- Seleccione una de las opciones : --\n"
					+ "1. Alta Vuelo\n"
					+ "2. Baja Vuelo\n"
					+ "3. Modificacion Vuelo\n"
					+ "4. salir");
			opcion = leerInt();
			if(opcion < 1 && opcion > 5) escribir("Ingrese una opcion Correcta!!");
		}while(opcion < 1 && opcion > 5 );
		return opcion;
	}
	
	public static void altasVuelos(AVL vuelos, GrafoEtiquetado aeropuertos) {
		Vuelo nuevoVuelo = obtenerVuelo(vuelos);
		if(vuelos.insertar(nuevoVuelo)) {
			escribir("ingrese el aeropuerto de origen: ");
			nuevoVuelo.setAeropuertoOrigen(obtenerAeropuerto(aeropuertos));
			escribir("ingrese el aeropuerto de destino: ");
			nuevoVuelo.setAeropuertoDestino(obtenerAeropuerto(aeropuertos));
			escribir("ingrese la hora de salida: ");
			nuevoVuelo.setHoraDeSalida(leerString());
			escribir("ingrese la hora de llegada: ");
			nuevoVuelo.setHoraDeSalida(leerString());
		}else {
			escribir("El vuelo ingresado ya existe !! ");
		}
	}

	public static void bajasVuelos(AVL vuelos){
		escribir("Ingrese las siglas de la compania:");
		String siglasCompania = leerString();
		escribir("Ingrese el numero de vuelo:");
		int numeroVuelo = leerInt();
		Vuelo vuelo = new Vuelo(siglasCompania, numeroVuelo);
		if(vuelos.eliminar(vuelo)) {
			escribir("Se elimino el vuelo con exito !!");
		}else {
			escribir("El vuelo ingresado ya existe !! ");
		}
	}
	
	public static void modificacionesVuelos(AVL vuelos) {
		escribir("Ingrese las siglas de la compania:");
		String siglasCompania = leerString();
		escribir("Ingrese el numero de vuelo:");
		int numeroVuelo = leerInt();
		Object vuelo = vuelos.recuperar(new Vuelo(siglasCompania, numeroVuelo));
		if(vuelo != null){
			Vuelo vueloModificar = (Vuelo) vuelo;
			modificarProgramado(vueloModificar.getProgramados());
		}
	}
	
	public static void modificarProgramado(Lista programados) {
		if(!programados.esVacia()) {
			for(int i = 1; i <= programados.longitud(); i++ ) {
				escribir(i+":"+programados.recuperar(i).toString());
			}
		}else {
			escribir("NO HAY VUELOS POGRAMADOS");
		}
		ABMprogramados(programados);
	}
	
	public static void ABMprogramados(Lista programados) {
		int opcion = 0;
		do {
			opcion = ABMmenuProgramados();
			switch (opcion) {
			case 1:
				altaProgramado(programados);
				break;
			case 2:
				bajaProgramado(programados);
				break;
			case 3:
				escribir("Finalizando AB programado");
				break;
			default:
				escribir("ingrese una opcion correcta!!");
				break;
			}
		}while(opcion != 3);
	}
	
	public static int ABMmenuProgramados() {
		escribir("Seleccione una de las opciones : \n"
				+ "1. Alta Vuelo Programado\n"
				+ "2. Baja Vuelo Programado\n"
				+ "3. salir");
		return leerInt();
	}
	
	public static void altaProgramado(Lista programados) {
		escribir("Ingrese la fecha:" );
		String fecha = leerString();
		escribir("Ingrese la cantidad de asientos totales:");
		int asientos = leerInt();
		Programado programado = new Programado(fecha, asientos);
		System.out.println(programados.localizar(programado));
		if(programados.localizar(programado) == -1) programados.insertar(programado, programados.longitud() +1);	
		else escribir("La fecha para programar ya esta ocupada!!");
	}
	
	public static void bajaProgramado(Lista programados) {
		escribir("Ingrese la fecha:" );
		String fecha = leerString();
		escribir("Ingrese la cantidad de asientos totales:");
		int asientos = leerInt();
		Programado programado = new Programado(fecha, asientos);
		int posicion  = programados.localizar(programado);
		if(posicion != 0) programados.eliminar(posicion);	
		else escribir("No esiste ese vuelo programado");
	}
	
	//ABM pasajes
	
	public static void ABMpasajes(AVL clientes, AVL vuelos, HashMap<Cliente, Lista> pasajesLista) {
		int  opcion = 0;
		do {
			opcion = ABMpasajesMenu();
			switch (opcion) {
			case 1:
				altaPasaje(clientes, vuelos, pasajesLista);
				break;
			case 2:
				bajaPasajes(clientes, pasajesLista);
				break;
			case 3:
				modificarPasajes(clientes, pasajesLista);
				break;
			case 4:
				escribir("finalizando ABM pasajes");
				break;
			default:
				escribir("La opcion ingresa no es correcta");
				break;
			}
		}while(opcion != 4);
	}
	
	public static int ABMpasajesMenu() {
		escribir("Seleccione una de las opciones : \n"
				+ "1. Alta Pasaje\n"
				+ "2. Baja Pasaje\n"
				+ "3. Modificacion Pasaje\n"
				+ "4. salir");
		return leerInt();
	}
	
	public static void altaPasaje(AVL clientes, AVL vuelos, HashMap<Cliente, Lista> listaPasajes) {
		escribir("Altas Pasajes");
		Cliente cliente = obtenerCliente(clientes);
		Vuelo vuelo = obtenerVuelo(vuelos);
		
		Lista programados = vuelo.getProgramados();
		Programado programado = obtenerProgramado(programados);
		if(programado != null) {
			int asiento = seleccionarAsiento(programado);
			System.out.println(asiento);
			if(asiento != -1) {
				if(listaPasajes.get(cliente) != null) {
					Lista pasajes = listaPasajes.get(cliente);
					pasajes.insertar(new Pasaje(vuelo, programado.getFecha(), asiento, "pendiente"), pasajes.longitud() +1);
				}else {
					listaPasajes.put(cliente, new Lista());
					Lista pasajes = listaPasajes.get(cliente);
					pasajes.insertar(new Pasaje(vuelo, programado.getFecha(), asiento, "pendiente"), pasajes.longitud() + 1);
				}
			}else {
				escribir("NO HAY ASIENTOS DISPONIBLES");
			}
		}else {
			escribir("NO HAY VUELOS PROGRAMADOS");
		}
	}
	
	public static void bajaPasajes(AVL clientes, HashMap<Cliente, Lista> listaPasajes) {
		escribir("Bajas Pasajes");
		Cliente cliente = obtenerCliente(clientes);
		Lista pasajes = listaPasajes.get(cliente);
		if(pasajes != null) {
			int pasaje = obtenerPasaje(pasajes);
			if(pasajes.eliminar(pasaje)) escribir("Se elimino el pasaje con exito");
			else escribir("No se puede eliminar dicho pasaje");
		}
	}
	
	public static void operacionesCliente(AVL clientes, HashMap<Cliente, Lista> pasajes) {
		escribir("-- Operaciones sobre Clientes --");
		int operacion = 0;
		do {
			operacion = oprecionesClienteMenu();
			switch (operacion) {
			case 1:
				cotactoCliente(clientes);
				break;
			case 2:
				ciudadesVisitadas(clientes, pasajes);
				break;
			case 3:
				escribir("Finalizando operaciones cliente ");
			break;
			default:
				escribir("La opcion ingresa no es correcta");
				break;
			}
		}while(operacion != 3);
	}
	
	public static int oprecionesClienteMenu() {
		int opcion = 0;
		escribir("-- Seleccione una de las opciones : --\n"
				+ "1. Contacto \n"
				+ "2. Ciudades visitados \n"
				+ "3. salir");
		return leerInt();
	}
	
	public static void cotactoCliente(AVL clientes) {
		escribir("-- contacto --");
		Cliente cliente = (Cliente) obtenerCliente(clientes);
		escribir("Nombre: "+cliente.getNombre());
		escribir("Apellido: "+cliente.getApellido());
		escribir("Fecha de Nacimiento: "+cliente.getFechaNaciento());
		escribir("Domicilio: "+cliente.getDomicilio());
		escribir("Telefono: "+cliente.getTelefono());
	}
	
	public static void ciudadesVisitadas(AVL clientes,HashMap<Cliente, Lista> pasajes) {
		Cliente cliente = (Cliente) obtenerCliente(clientes);
		Lista pasajesCliente = pasajes.get(cliente);
		int i = 1;
		System.out.println(pasajesCliente.longitud());
		escribir("viejes");
		while(i <= pasajesCliente.longitud()) {
			Pasaje pasaje = (Pasaje) pasajesCliente.recuperar(i);
			Vuelo vuelo = pasaje.getVuelo();
			escribir("| "+vuelo.getAeropuertoOrigen().toString()+" -> "+vuelo.getAeropuertoDestino().toString());
			i++;
		}
	}
	
	//operaciones vuelos
	
	public static void operacionesVuelos(AVL vuelos) {
		escribir("-- Operaciones sobre Vuelos --");
		int operacion = 0;
		do {
			operacion = operacionesVuelosMenu();
			switch (operacion) {
			case 1:
				informacionVuelo(vuelos);
				break;
			case 2:
				rangoVuelos(vuelos);
				break;
			case 3:
				escribir("Finalizando operaciones vuelos");
			break;
			default:
				escribir("La operacion ingresa no es correcta");
				break;
			}
		}while(operacion != 3);
	}
	
	public static int operacionesVuelosMenu() {
		int opcion = 0;
		escribir("-- Seleccione una de las opciones : --\n"
				+ "1. Informacion vuelo \n"
				+ "2. Rango de numeros de vuelos \n"
				+ "3. salir");
		return leerInt();
	}
	
	public static void informacionVuelo(AVL vuelos) {
		Vuelo vuelo = obtenerVuelo(vuelos);
		escribir("Aeropuerto de Origen: "+ vuelo.getAeropuertoOrigen().toString());
		escribir("Aeripuerto de Salida: "+vuelo.getAeropuertoDestino().toString());
		escribir("Hora de salida: "+vuelo.getHoraDeSalida());
		escribir("Hora de salida: "+vuelo.getHoraDeSalida());
		escribir("Vuelos Programados"+vuelo.getProgramados().toString());
	}
	
	public static void rangoVuelos(AVL vuelos) {
		escribir("primer aeropuerto:");
		Vuelo primerVuelo = obtenerVuelo(vuelos);
		escribir("segundo vuelo:");
		Vuelo segundoVuelo = obtenerVuelo(vuelos);
		Lista rango = vuelos.listarRango(primerVuelo, segundoVuelo);
		escribir("Rango: "+rango.toString());
	}
	
	//tiempos de viaje
	public static void menorRecorrido(GrafoEtiquetado aeropuertos) {
		escribir("Ingrese una cantidad minima de embarques:");
		int embarquesMinimos = leerInt();
		escribir("Ingrese un aeropuerto:");
		Aeropuerto aeropuertoA = obtenerAeropuerto(aeropuertos);
		escribir("Ingrese un aeropueto:");
		Aeropuerto aeropuertoB = obtenerAeropuerto(aeropuertos);
		
		Lista menorCamino = aeropuertos.caminoMasCorto(aeropuertoA, aeropuertoB);
		if(menorCamino.longitud() < embarquesMinimos) {
			escribir("La lista de embarques minimos es:"+menorCamino.toString());
		}else {
			escribir("no hay una cantidad minima de embarques para ese viaje");
		}
	}
	
	public static void consultaTiemposDeViaje(GrafoEtiquetado aeropuertos) {
		int opcion;
		do {
			opcion = consultaTiemposDeViajeMenu();
			switch (opcion) {
			case 1:
				maximoEscalasAeropuertos(aeropuertos);
				break;
			case 2:
				minimoTiempoDeVuelo(aeropuertos);
				break;
			case 3:
				minimoEscalas(aeropuertos);
				break;
			case 4:
				menorTiempoConEscala(aeropuertos);
				break;
			case 5:
				escribir("Finalizando consulta tiempos de vieje");
				break;
			default:
				escribir("La operacion ingresada no es correcta");
				break;
			}
		}while(opcion != 6);
	}
	
	public static int consultaTiemposDeViajeMenu() {
		int opcion = 0;
		escribir("-- Seleccione una de las opciones : --\n"
				+ "1. Maximo vuelos de un viaje \n"
				+ "2. Menor tiempo de vuelos en un viaje\n"
				+ "3. Menor cantidad de escalas\n"
				+ "4. Menor tiempo de vuelos con una escala seleccionada\n"
				+ "5. salir");
		return leerInt();
	}
	
	public static void maximoEscalasAeropuertos(GrafoEtiquetado aeropuertos) {
		Aeropuerto aeropuertoA = obtenerAeropuerto(aeropuertos);
		Aeropuerto aeropuertoB = obtenerAeropuerto(aeropuertos);
		escribir("inrese un numero maximo de escalas");
		int maximoEscalas = leerInt();
		Lista minimoCamino = aeropuertos.caminoMasCorto(aeropuertoA, aeropuertoB);
		if(!minimoCamino.esVacia() && minimoCamino.longitud() <= maximoEscalas) {
			escribir("existe un minimo de escalas para dichos aeropuertos y es:"+ minimoCamino.toString());
		}else {
			escribir("no existe un minimo de esclas para dichos aeropuertos");
		}
	}
	
	public static void minimoTiempoDeVuelo(GrafoEtiquetado aeropuertos) {
		Aeropuerto aeropuertoA = obtenerAeropuerto(aeropuertos);
		Aeropuerto aeropuertoB = obtenerAeropuerto(aeropuertos);
		Lista caminos = aeropuertos.caminos(aeropuertoA, aeropuertoB);
		if(!caminos.esVacia()) {
			HashMap<Lista, Integer> distancia = new HashMap<Lista, Integer>();
			int i = 1;
			int minimo = 0;
			while(i <= caminos.longitud()) {
				Lista camino = (Lista) caminos.recuperar(i);
				if(i==1) {
					minimo = tiempoCamino(camino, aeropuertos);
				}else {
					int minimoAtual = tiempoCamino(camino, aeropuertos);
					if(minimo > minimoAtual) minimo = minimoAtual;
				}
				i++;
			}
		}
	}
	
	public static int tiempoCamino(Lista camino, GrafoEtiquetado aeropuertos) {
		int retorno = 0;
		int i = 0;
		int tiempo = 0;
		if(camino.longitud() > 1) {
			while(i < camino.longitud()) {
				tiempo += (int) aeropuertos.getEtiquetaArco(camino.recuperar(i), camino.recuperar(i));
				i++;
			}
		}
		return retorno;
	}
	
	public static void minimoEscalas(GrafoEtiquetado aeropuertos) {
		Aeropuerto aeropuertoA = obtenerAeropuerto(aeropuertos);
		Aeropuerto aeropuertoB = obtenerAeropuerto(aeropuertos);
		
		Lista camino = aeropuertos.caminoMasCorto(aeropuertoA, aeropuertoB);
		if(!camino.esVacia()) {
			escribir("La lista de escalas: "+camino.toString());
		}else {
			escribir("NO HAY FORMA DE LLEGAR DESDE "+aeropuertoA.toString()+" AEROPUERTO A "+aeropuertoB.toString());
		}
	}
	
	public static void menorTiempoConEscala(GrafoEtiquetado aeropuertos) {
		escribir("Ingrese el aeropuerto de origen");
		Aeropuerto aeropuertoA = obtenerAeropuerto(aeropuertos);
		escribir("Ingrese el aeropuerto de destino");
		Aeropuerto aeropuertoB = obtenerAeropuerto(aeropuertos);
		escribir("Ingrese el aerpuerto de escala:");
		Aeropuerto aeropuertoC = obtenerAeropuerto(aeropuertos);
		
		Lista caminos = aeropuertos.caminos(aeropuertoA, aeropuertoB);
		Lista caminosConC = new Lista();
		int i = 1;
		while(i <= caminos.longitud()) {
			Lista camino = (Lista) caminos.recuperar(i);
			if(camino.localizar(aeropuertoC) != -1) {
				caminosConC.insertar(camino, 1);
			}
			i++;
		}
		i = 1;
		int max = 0;
		while(i <= caminosConC.longitud()) {
			Lista camino = (Lista) caminosConC.recuperar(i);
			if(i == 1) {
				max = tiempoCamino(camino, aeropuertos);
			}else {
				int maximoActual = tiempoCamino(camino, aeropuertos);
				if(maximoActual <= max) max = maximoActual;
			}
			i++;
		}
		System.out.println(caminosConC.toString());
	}
	
	//promociones
	public static void promociones(AVL clientes, HashMap<Cliente, Lista> pasajes) {
		escribir("Los clientes a pormocionar");
		Lista listaClientes = clientes.lista();
		ArbolHeapMaximo listaMaximo = new ArbolHeapMaximo();
		while(!listaClientes.esVacia()) {
			Cliente cliente = (Cliente) listaClientes.recuperar(1);
			int cantidadPasaje = 0;
			if(pasajes.get(cliente) != null) cantidadPasaje = pasajes.get(cliente).longitud();
			listaMaximo.insertar(new Promocion(cliente, cantidadPasaje));
			listaClientes.eliminar(1);
		}
		System.out.println(listaMaximo.toString());
	}
	
	//recuperar datos de l
	
	public static void modificarPasajes(AVL clientes,  HashMap<Cliente, Lista> listaPasajes) {
		escribir("Modificacion Pasajes");
		Cliente cliente = obtenerCliente(clientes);
		Lista pasajes = listaPasajes.get(cliente);
		if(pasajes != null) {
			int indidePasaje = obtenerPasaje(pasajes);
			Pasaje pasaje = (Pasaje) pasajes.recuperar(indidePasaje);
			boolean control = true;
			do {
				escribir("Ingrese una opcion:\n 1. modificar a en vuelo\n 2. modificar a cancelado\n 3. modificar a pendiante");
				int opcion = leerInt();
				switch (opcion) {
				case 1:
					pasaje.setEstado("Vuelo");
					control = false;
					break;
				case 2:
					pasaje.setEstado("Cancelado");
					control = false;
					break;
				case 3:
					pasaje.setEstado("Pendiente");
					control = false;
					break;
				}
			}while(control);
		}
	}
	
	//obtener 
	
	public static Aeropuerto obtenerAeropuerto(GrafoEtiquetado aeropuertos) {
		Aeropuerto retorno = null;
		do {
			escribir("Ingresar nombre aeroporturario:");
			String nombreAeroportuario = leerString();
			retorno = (Aeropuerto) obtenerAeropuertosAux(aeropuertos, nombreAeroportuario);
		}while(retorno == null);
		return retorno;
	}
	
	public static Object obtenerAeropuertosAux(GrafoEtiquetado aeropuertos, String nombreAeroportuario) {
		return aeropuertos.recuperarVertice(new Aeropuerto(nombreAeroportuario));
	}

	public static Aeropuerto obtenerNuevoAeropuerto(GrafoEtiquetado aeropuertos) {
		Aeropuerto retorno = null;
		do {
			escribir("Ingresar nombre aeroporturario:");
			String nombreAeroportuario = leerString();
			retorno = new Aeropuerto(nombreAeroportuario);
		}while(aeropuertos.existeVertice(retorno));
		
		return retorno;
	}
	
	public static Cliente obtenerCliente(AVL clientes) {
		Cliente retorno = null;
		do {
			escribir("Ingresar el tipo de DNI:");
			String tipo = leerString();
			escribir("Ingresar el numero de DNI:");
			int numero = leerInt();
			retorno = (Cliente) obtenerClienteAux(clientes, tipo, numero);
		}while(retorno == null);
		return retorno;
	}
	
	public static Object obtenerClienteAux(AVL clientes, String tipo, int numero) {
		return clientes.recuperar(new Cliente(tipo, numero));
	}
	
	public static Cliente obtenerNuevoCliente(AVL clientes) {
		Cliente retorno = null;
		do {
			escribir("Ingrese el tipo de DNI:");
			String tipo = leerString();
			escribir("Ingrese el numero de DNI:");
			int numero = leerInt();
			retorno = new Cliente(tipo, numero);
		}while(clientes.recuperar(retorno) != null);
		
		return retorno;
	}
	
	public static Vuelo obtenerVuelo(AVL vuelos) {
		Vuelo retorno = null;
		do {
			escribir("Ingresar las siglas de la Compania Aeronautica:");
			String siglas = leerString();
			escribir("Ingresar el numero de vuelo:");
			int numero = leerInt();
			retorno = (Vuelo) obtenerVueloAux(vuelos, siglas, numero);
		}while(retorno == null);
		return retorno;
	}
	
	public static Object obtenerVueloAux(AVL vuelos, String siglas, int numero) {
		return vuelos.recuperar(new Vuelo(siglas, numero));
	}
	
	public static Vuelo obtenerNuevoVuelo(AVL vuelos) {
		Vuelo retorno = null;
		do {
			escribir("Ingresar las siglas de la Compania Aeronautica:");
			String siglas = leerString();
			escribir("Ingresar el numero de vuelo:");
			int numero = leerInt();
			retorno = (Vuelo) obtenerVueloAux(vuelos, siglas, numero);
		}while(retorno != null);
		return retorno;
	}
	
	public static Programado obtenerProgramado(Lista programados) {
		Programado retorno = null;
		boolean control = true;
		if(!programados.esVacia()) {
			for (int i = 1; i <= programados.longitud(); i++) {
				escribir(i+". "+programados.recuperar(i));
			}
			int posicion = 0;
			do {
				escribir("ingrese alguno de los vuelos programados: ");
				posicion = leerInt();
			}while(posicion < 0 && posicion > programados.longitud());
			retorno = (Programado) obtenerProgramadoAux(programados, posicion);
		}
		return retorno;
	}
	
	public static Object obtenerProgramadoAux(Lista programados, int i) {
		return programados.recuperar(i);
	}
	
	public static int obtenerPasaje(Lista pasajes) {
		int retorno = -1;
		if(pasajes.longitud() > -1) {
			for (int i = 1; i < pasajes.longitud()+1; i++) {
				escribir(i+". "+pasajes.recuperar(i).toString());
			}
			do {
				escribir("ingrese alguno de los vuelos programados: ");
				retorno = leerInt();
			}while(retorno < 0 && retorno > pasajes.longitud() + 1);
		}
		return retorno;
	}
	
	public static int seleccionarAsiento(Programado programado) {
		int retorno = -1;
		System.out.println(programado.asientosLibres());
		if(programado.asientosLibres()){
			System.out.println("ocasad");
			boolean[] asientos = programado.getAsientos();
			boolean control = true;
			for (int i = 0; i < asientos.length; i++) {
				escribir(i+". "+(asientos[i]? "[ ]": "[*]"));
			}
			do {
				escribir("Ingrese el asiento a reservar:");
				int asiento = leerInt();
				if(asiento < asientos.length && asiento > -1 ) {
					if(asientos[asiento]) {
						retorno = asiento;
						asientos[asiento] = false;
						control = false;
					}else {
						escribir("El asiento esta ocupado");
					}
				}else {
					escribir("El asiento no existe");
				}
			}while(control);
		}
		return retorno;
	}
	
	public static void mostrarSistema(AVL clientes, AVL vuelos, GrafoEtiquetado aeropuertos, HashMap<Cliente, Lista> pasajes) {
		escribir("-- -- Sistema de debug -- --");
		escribir("-- Aeropuertos --");
		escribir(aeropuertos.toString());
		escribir("-- Clientes --");
		escribir(clientes.toString());
		escribir("-- Vuelos --");
		escribir(vuelos.toString());
		escribir("-- Pasajes --");
		escribir(pasajes.toString());
	}
}
