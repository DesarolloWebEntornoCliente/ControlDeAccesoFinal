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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="codigosacceso")
public class CodigoAcceso implements Serializable{

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int idCodigoAcceso;
		private String codigoAcceso;
		private String nivel;
		
		@OneToMany(mappedBy = "codigoacceso", cascade=CascadeType.ALL, fetch = FetchType.LAZY) // conexion con tabla empleados
		private Set<AccesoEmpleados> accesoempleados = new HashSet<AccesoEmpleados>();
		
		@OneToMany(mappedBy = "codigoacceso", cascade=CascadeType.ALL, fetch = FetchType.LAZY) // conexion con departamentos
		private Set<Departamento> departamento2;

		public CodigoAcceso() {
			super();
		}

		// Constructor para Añadir
		public CodigoAcceso(String codigoAcceso, String nivel) {
			super();
			this.codigoAcceso = codigoAcceso;
			this.nivel = nivel;
		}
		
		
		// Constructor para Añadir Codigo de acceso
		public CodigoAcceso(int idCodigoAcceso, String codigoAcceso) {
			super();
			this.idCodigoAcceso = idCodigoAcceso;
			this.codigoAcceso = codigoAcceso;
		}

		public int getIdCodigoAcceso() {
			return idCodigoAcceso;
		}

		public void setIdCodigoAcceso(int idCodigoAcceso) {
			this.idCodigoAcceso = idCodigoAcceso;
		}

		public String getCodigoAcceso() {
			return codigoAcceso;
		}

		public void setCodigoAcceso(String codigoAcceso) {
			this.codigoAcceso = codigoAcceso;
		}
		
		

		public String getNivel() {
			return nivel;
		}

		public void setNivel(String nivel) {
			this.nivel = nivel;
		}

		public Set<AccesoEmpleados> getAccesoempleados() {
			return accesoempleados;
		}

		public void setAccesoempleados(Set<AccesoEmpleados> accesoempleados) {
			this.accesoempleados = accesoempleados;
		}

		@Override
		public String toString() {
			return "CodigoAcceso [idCodigoAcceso=" + idCodigoAcceso + ", codigoAcceso=" + codigoAcceso
					+ ", accesoempleados=" + accesoempleados + ", departamento2=" + departamento2 + "]";
		}
		
		
}
