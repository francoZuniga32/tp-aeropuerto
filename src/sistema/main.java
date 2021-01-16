package sistema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import conjuntitas.dinamico.AVL;
import grafos.Grafo;
import grafos.GrafoEtiquetado;
import lineales.dinamicas.Lista;

public class main {
static TecladoIn teclado = new TecladoIn();
	
	public static void main(String[] args) throws Exception {
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
		do {
			opcion = menu();
			switch (opcion) {
				case 1:
					
					break;
				case 2:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					break;
				case 8:
					operacion8(tablaClientes, tablaVuelos, aeropuertos, pasajes);
					break;
				case 9:
					System.out.println("Finalizando programa...");
					break;
				default:
					System.out.println("Ingrese una opcion valida");
					break;
			}
		}while(opcion < 9);
	}
	
	public static void createLog() {
		 try {
	         //Creo el PrintWriter que referencia al archivo LOG creado
	         PrintWriter arc = new PrintWriter(new File("log.txt"));
	     } catch (IOException e) {
	    	 System.out.println("Error al crear el archivo LOG:" + e);
	     }
	}
	
	public static void escribir(String entrada) {
		System.out.println(entrada);
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
	
	public static void cargarAeropuerto(GrafoEtiquetado aeropuerto) throws FileNotFoundException {
		Scanner sc = cargarArchivo("./aeropuertos.txt");
        String[] datos = new String[10];
        while (sc.hasNextLine()) {
            //Los ";" separan los datos de cada linea y el primer dato determina que es lo que se va a cargar
            datos = sc.nextLine().split(";");
            System.out.println("Cargamos un Aeropuerto Espera OK"+(aeropuerto.insertarVertice(new Aeropuerto(datos[0], datos[1], datos[2]))? "Ok": "False"));
        }
	}
	
	public static void cargarRutas(GrafoEtiquetado aeropuerto) throws FileNotFoundException {
		Scanner sc = cargarArchivo("./rutas.txt");
        String[] datos = new String[10];
        while (sc.hasNextLine()) {
            //Los ";" separan los datos de cada linea y el primer dato determina que es lo que se va a cargar
            datos = sc.nextLine().split(";");
            System.out.println("Insertamos un arco espera Ok"+(aeropuerto.insertarArco(new Aeropuerto(datos[0]), new Aeropuerto(datos[1]), datos[2])? "OK": "False"));
        }
	}
	
	public static void cargarClientes(AVL clientes) throws FileNotFoundException {
		Scanner sc = cargarArchivo("./clientes.txt");
        String[] datos = new String[10];
        while (sc.hasNextLine()) {
            //Los ";" separan los datos de cada linea y el primer dato determina que es lo que se va a cargar
            datos = sc.nextLine().split(";");
            System.out.println("Insertamos un cliente espera Ok"+(clientes.insertar(new Cliente(datos[0], Integer.parseInt(datos[1])))? "OK": "False"));
        }
	}
	
	public static void cargarVuelos(AVL vuelos, GrafoEtiquetado aeropuertos) throws FileNotFoundException {
		
		Scanner sc = cargarArchivo("./vuelos.txt");
        String[] datos;
        while (sc.hasNextLine()) {
            //Los ";" separan los datos de cada linea y el primer dato determina que es lo que se va a cargar
            datos = sc.nextLine().split(";");
            System.out.println("Insertamos un cliente espera Ok"+(vuelos.insertar(new Vuelos(datos[0], Integer.parseInt(datos[1]), (Aeropuerto) aeropuertos.recuperarVertice(datos[2]), (Aeropuerto) aeropuertos.recuperarVertice(datos[3]), datos[4], datos[5])) ? "OK": "False"));
        }
	}
	
	public static Scanner cargarArchivo(String archivo) throws FileNotFoundException {
		File f = new File(archivo);
        Scanner sc = new Scanner(f, "UTF-8");
        return sc;
	}
	
	public static int menu() {
		int opcion = 0;
		System.out.println("-- Seleccione una de las opciones : --\n"
				+ "1. ABM (Altas-Bajas-Modificaciones) de aeropuertos\n"
				+ "2. ABM (Altas-Bajas-Modificaciones) de clientes\n"
				+ "3. ABM (Altas-Bajas-Modificaciones) de vuelos\n"
				+ "4. ABM (Altas-Bajas-Modificaciones) de pasajes\n"
				+ "9. salir");
		opcion = teclado.readLineInt();
		return opcion;
	}
	
	public static int ABMaeropuertoMenu() {
		int opcion = 0;
		System.out.println("-- Seleccione una de las opciones : --\n"
				+ "1. Alta aeropuerto\n"
				+ "2. Baja aeropuerto\n"
				+ "3. Modificacion aeropuerto\n"
				+ "4. salir");
		opcion = teclado.readLineInt();
		return opcion;
	}
	
	public static int ABMclienteMenu() {
		int opcion = 0;
		System.out.println("-- Seleccione una de las opciones : --\n"
				+ "1. Alta cliente\n"
				+ "2. Baja cliente\n"
				+ "3. Modificacion cliente\n"
				+ "4. salir");
		opcion = teclado.readLineInt();
		return opcion;
	}
	
	public static boolean ABMCliente(int opcion, AVL clientes) {
		boolean retorno = false;
		switch (opcion) {
			case 1:
				retorno = altaCliente(clientes);
			break;
			case 2:
				break;
			case 3:
				break;
			default:
				break;
		}
		return retorno;
	}
	
	public static boolean altaCliente(AVL clientes) {
		boolean retorno = false;
		
		System.out.println("-- Ingresar datos del Usuarios --");
		System.out.println("Ingresar tipo dni:");
		String tipoDni = teclado.readLine();
		System.out.println("Ingresar numero de dni:");
		int dni = teclado.readInt();
		Cliente nuevoCliente = new Cliente(tipoDni, dni);
		retorno = clientes.insertar(nuevoCliente);
		if(retorno) {
			System.out.println("Ingresar nombre: ");
			String nombreParam = TecladoIn.readLine();
			nuevoCliente.setNombre(nombreParam);
			System.out.println("Ingresar apellido: ");
			nuevoCliente.setApellido(teclado.readLine());
			System.out.println("Ingrese direccion: ");
			nuevoCliente.serDomicilio(TecladoIn.readLine());
			System.out.println("Ingrese fecha de nacimiento: ");
			nuevoCliente.setFechaNacimiento(TecladoIn.readLine());
			System.out.println("Ingrese telefono: ");
			nuevoCliente.setNombre(TecladoIn.readLine());
			System.out.println("El usuario fue creado con exito");
		}else {
			System.out.println("El usuario ya existe");
		}
		return retorno;
	}
	
	public static boolean bajaCliente(AVL clientes) {
		boolean retorno = false;
		
		System.out.println("Ingrese el tipo de dni:");
		String tipoDni = teclado.readLine();
		System.out.println("Ingrese el dni: ");
		int dni = teclado.readInt();
		retorno = clientes.eliminar(new Cliente(tipoDni, dni));
		if(!retorno) {
			System.out.println("El usuario no Existe!!");
		}else {
			System.out.println("El usuario fue eliminado con exito");
		}
		return retorno;
	}
	
	public static boolean modificarCliente(AVL clientes) {
		boolean retorno = false;
		
		System.out.println("Ingrese el tipo de dni:");
		String tipoDni = teclado.readLine();
		System.out.println("Ingrese el dni: ");
		int dni = teclado.readInt();
		Object usuario = clientes.recuperar(new Cliente(tipoDni, dni));
		if(usuario != null) {
			Cliente cliente = (Cliente) usuario;
			System.out.println("Ingrese el nuevo telefono:");
			cliente.setTelefono(TecladoIn.readLine());
			System.out.println("Ingrese la nueva direccion:");
			cliente.serDomicilio(TecladoIn.readLine());
		}else {
			System.out.println("El usuario no existe!!");
		}
		return retorno;
	}
	
	public static void operacion8(AVL clientes, AVL vuelos, GrafoEtiquetado aeropuertos, HashMap<Cliente, Lista> pasajes) {
		System.out.println("-- -- Sistema de debug -- --");
		System.out.println("-- Aeropuertos --");
		System.out.println(aeropuertos.toString());
		System.out.println("-- Clientes --");
		System.out.println(clientes.toString());
		System.out.println("-- Vuelos --");
		System.out.println(vuelos.toString());
		System.out.println("-- Pasajes --");
		System.out.println(pasajes.toString());
	}
}
