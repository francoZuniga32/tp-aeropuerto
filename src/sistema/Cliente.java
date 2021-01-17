package sistema;

public class Cliente implements Comparable{
	private String tipo;
	private int numero;
	private String nombre;
	private String apellido;
	private String fechaNacimiento;
	private String domicilio;
	private String telefono;
	
	public Cliente(String tipoParam, int numeroParam) {
		this.tipo = tipoParam;
		this.numero = numeroParam;
	}
	
	public Cliente(String tipoParam, int numeroParam, String nombreParam, String apellidoParam, String fechaParam, String domiciolioParam, String telefonoParam) {
		this.tipo = tipoParam;
		this.numero = numeroParam;
		this.nombre = nombreParam;
		this.apellido = apellidoParam;
		this.fechaNacimiento = fechaParam;
		this.domicilio = domiciolioParam;
		this.telefono = telefonoParam;
	}
	
	public void setNombre(String nombreParam) {
		this.nombre = nombreParam;
	}
	
	public void setApellido(String apellidoParam) {
		this.apellido = apellidoParam;
	}
	
	public void setFechaNacimiento(String fechaParam) {
		this.fechaNacimiento = fechaParam;
	}
	
	public void setTelefono(String telefonoParam) {
		this.telefono = telefonoParam;
	}
	
	public void serDomicilio(String domicilioParam) {
		this.domicilio = domicilioParam;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getApellido() {
		return this.apellido;
	}
	
	public String getFechaNaciento() {
		return this.fechaNacimiento;
	}
	
	public String getTelefono() {
		return this.telefono;
	}
	
	public String toString() {
		return "(tipo: "+this.tipo+", "+this.numero+", "+this.nombre+")";
	}
	
	public boolean equals(Comparable elem) {
		Cliente obj = (Cliente) elem;
		boolean retorno = (this.tipo.equals(obj.tipo) && this.numero == obj.numero);
		System.out.println(retorno);
		return retorno;
	}

	@Override
	public int compareTo(Object elem) {
		Cliente clienteCompare = (Cliente) elem;
		int retorno = 0;
		int compareTipo = this.tipo.compareTo(clienteCompare.tipo);
		int compareNumero = this.numero - clienteCompare.numero;
		if(compareTipo != 0) {
			retorno = compareTipo;
		}else {
			retorno = compareNumero;
		}
		return retorno;
	}
}
