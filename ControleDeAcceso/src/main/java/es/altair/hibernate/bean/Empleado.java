package es.altair.hibernate.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.altair.hibernate.dao.Conexion;

@Entity
@Table(name="empleados")
public class Empleado implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEmpleado;
	private String nombre;
	private String apellidos;
	private Date fechaNacto;
	private String documiento;
	private String funcion;
	private String efetivo;

	@Temporal(TemporalType.DATE)
	private Date fechaEntrada;
	
	private float salario;
		
	@OneToMany(mappedBy = "empleado") // conexion con codigos de acceso
	private Set<AccesoEmpleados> accesoempleados = new HashSet<AccesoEmpleados>();
	
	@ManyToOne
	@JoinColumn(name="idDepartamento") // conexion con departamrentos 
	private Departamento departamento;

	public Empleado() {
		super();
	}

	
	public Empleado(int idEmpleado, String nombre, String apellidos) {
		super();
		this.idEmpleado = idEmpleado;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}



	public Empleado(String nombre, String apellidos, Date fechaNacto, String documiento, String funcion, String efetivo,
			Date fechaEntrada, float salario) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacto = fechaNacto;
		this.documiento = documiento;
		this.funcion = funcion;
		this.efetivo = efetivo;
		this.fechaEntrada = fechaEntrada;
		this.salario = salario;
	}

	
	
	public Empleado(String nombre, String apellidos, Date fechaNacto, String documiento, String funcion, String efetivo,
			Date fechaEntrada, float salario, Departamento departamento) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacto = fechaNacto;
		this.documiento = documiento;
		this.funcion = funcion;
		this.efetivo = efetivo;
		this.fechaEntrada = fechaEntrada;
		this.salario = salario;
		this.departamento = departamento;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFechaNacto() {
		return fechaNacto;
	}

	public void setFechaNacto(Date fechaNacto) {
		this.fechaNacto = fechaNacto;
	}

	public String getDocumiento() {
		return documiento;
	}

	public void setDocumiento(String documiento) {
		this.documiento = documiento;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public String getEfetivo() {
		return efetivo;
	}

	public void setEfetivo(String efetivo) {
		this.efetivo = efetivo;
	}

	
	
	public float getSalario() {
		return salario;
	}


	public void setSalario(float salario) {
		this.salario = salario;
	}


	public Date getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}
	

	public Set<AccesoEmpleados> getAccesoempleados() {
		return accesoempleados;
	}

	public void setAccesoempleados(Set<AccesoEmpleados> accesoempleados) {
		this.accesoempleados = accesoempleados;
	}



	@Override
	public String toString() {
		return "Empleado [idEmpleado=" + idEmpleado + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", fechaNacto=" + fechaNacto + ", documiento=" + documiento + ", funcion=" + funcion + ", efetivo="
				+ efetivo + ", fechaEntrada=" + fechaEntrada + ", accesoempleados=" + accesoempleados + "]";
	}

	
	
}
