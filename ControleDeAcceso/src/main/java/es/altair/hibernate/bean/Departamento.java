package es.altair.hibernate.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="departamentos")
public class Departamento implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDepartamento;
	private String descripcion;
	
	@OneToMany(mappedBy = "departamento", cascade=CascadeType.ALL, fetch = FetchType.LAZY) // conexion con tabla empleados
	private Set<Empleado> empleados;

	@ManyToOne
	@JoinColumn(name="idCodigoAcceso") // conexion con codigos de acceso
	private CodigoAcceso codigoacceso;

		
	public Departamento() {
		super();
	}


	public Departamento(int idDepartamento, String descripcion) {
		super();
		this.idDepartamento = idDepartamento;
		this.descripcion = descripcion;
	}
	

	public Departamento(int idDepartamento, String descripcion, CodigoAcceso codigoacceso) {
		super();
		this.idDepartamento = idDepartamento;
		this.descripcion = descripcion;
		this.codigoacceso = codigoacceso;
	}



	public Departamento(String descripcion) {
		super();
		this.descripcion = descripcion;
	}

	

	public Departamento(String descripcion, CodigoAcceso codigoacceso) {
		super();
		this.descripcion = descripcion;
		this.codigoacceso = codigoacceso;
	}



	public int getIdDepartamento() {
		return idDepartamento;
	}


	public void setIdDepartamento(int idDepartamento) {
		this.idDepartamento = idDepartamento;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public Set<Empleado> getEmpleados() {
		return empleados;
	}


	public void setEmpleados(Set<Empleado> empleados) {
		this.empleados = empleados;
	}



	public CodigoAcceso getCodigoacceso() {
		return codigoacceso;
	}



	public void setCodigoacceso(CodigoAcceso codigoacceso) {
		this.codigoacceso = codigoacceso;
	}


	@Override
	public String toString() {
		return "Departamento [idDepartamento=" + idDepartamento + ", descripcion=" + descripcion + ", codigoacceso=" + codigoacceso + "]";
	}



	
	
}




