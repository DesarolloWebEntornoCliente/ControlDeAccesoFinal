package es.altair.hibernate.bean;

import java.io.Serializable;
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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;


@Entity
@Table(name="codigosacceso")
public class CodigoAcceso implements Serializable{

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int idCodigoAcceso;
		
		@NotNull
		@Size(min=3, max=35)
		private String codigoAcceso;
		
		@Size(min=3, max=30)
		@Pattern(regexp="[A-Z]{1}[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]{2,29}", message="La Asignación de Nivel tiene que empiezar por letra maiuscula")
		private String nivel;
		
		@OneToMany(mappedBy = "codigoacceso", cascade=CascadeType.DETACH, fetch = FetchType.LAZY) // conexion con tabla empleados
		@Valid
		private Set<AccesoEmpleados> accesoempleados = new HashSet<AccesoEmpleados>();
		
		@OneToMany(mappedBy = "codigoacceso", cascade=CascadeType.DETACH, fetch = FetchType.LAZY) // conexion con departamentos
		@Valid
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
