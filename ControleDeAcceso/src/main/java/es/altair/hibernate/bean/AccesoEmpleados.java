package es.altair.hibernate.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="accesosempleados")
public class AccesoEmpleados implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idAccesosEmpleados;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idCodigoAcceso")
	private CodigoAcceso codigoacceso;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idEmpleado")
	private Empleado empleado;
	
	@Temporal(TemporalType.DATE)
	private Date fechaEntrada;


	public AccesoEmpleados() {
		super();
	}

	

	public AccesoEmpleados(CodigoAcceso codigoacceso, Empleado empleado, Date fechaEntrada) {
		super();
		this.codigoacceso = codigoacceso;
		this.empleado = empleado;
		this.fechaEntrada = fechaEntrada;
	}


	public int getIdAccesosEmpleados() {
		return idAccesosEmpleados;
	}


	public void setIdAccesosEmpleados(int idAccesosEmpleados) {
		this.idAccesosEmpleados = idAccesosEmpleados;
	}


	public CodigoAcceso getCodigoacceso() {
		return codigoacceso;
	}


	public void setCodigoacceso(CodigoAcceso codigoacceso) {
		this.codigoacceso = codigoacceso;
	}


	public Empleado getEmpleado() {
		return empleado;
	}


	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}


	public Date getFechaEntrada() {
		return fechaEntrada;
	}


	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}


	


}
