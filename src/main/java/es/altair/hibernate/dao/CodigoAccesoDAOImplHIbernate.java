package es.altair.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.Session;

import es.altair.hibernate.bean.CodigoAcceso;
import es.altair.hibernate.utilitarios.Encriptaciones;

public class CodigoAccesoDAOImplHIbernate implements CodigoAccesoDAO {

	public void save(CodigoAcceso cod) {
		Session sesion = Conexion.abrirConexion();

		try {
			
			sesion.save(cod);
			
			sesion.getTransaction().commit();
			
				System.out.println("Codigo Añadido con Exito");
			
		} catch (ConstraintViolationException e) {
			
			sesion.getTransaction().rollback();
			System.out.println("-- ERRORES ENCUENTRADOS --");
			for (ConstraintViolation cv : e.getConstraintViolations()) {
				System.out.println("Campo: " + cv.getPropertyPath());
				System.out.println("Mensage: " + cv.getMessage());
				
			}
		} finally {
			Conexion.desconectar(sesion);

		}

	}
	
	public CodigoAcceso obtener(int codCod) {
		
		CodigoAcceso codigo = null;
		
		Session sesion = Conexion.abrirConexion();
		
		codigo = (CodigoAcceso)sesion.createQuery("select c from CodigoAcceso c where idCodigoAcceso=:id").setParameter("id", codCod).uniqueResult();

			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
		return codigo;
	}

	public void borrar(CodigoAcceso codBorrar) {
	Session sesion = Conexion.abrirConexion();
		
		sesion.delete(codBorrar);
		
		sesion.getTransaction().commit();
				
	Conexion.desconectar(sesion);
		
	}


	public List<Object[]> listaCodigosAcceso() {
		
		List<Object[]> codigos = new ArrayList<Object[]>();
		
			Session sesion = Conexion.abrirConexion();
				
				codigos = sesion.createQuery("select e.idCodigoAcceso, e.nivel from CodigoAcceso e").list(); // Consulta HQL
			
				sesion.getTransaction().commit();
						
			Conexion.desconectar(sesion);
		
		return codigos;
	}

	public void listaCodigosDesincriptados() {
		
		 String key = "47AE31A79FEEB2A3"; // LLAVE DE INCRIPTACIÓN
		 String iv = "0123456789ABCDEF"; // VETOR DE INICIALIZACIÓN
		 
		 int numPagina = 1, contaCodigos = 0, contador = 0;	
			 
		 List<Object[]> codigos = new ArrayList<Object[]>();
		
		 Session sesion = Conexion.abrirConexion();
			
			codigos = sesion.createQuery("select e.idCodigoAcceso, e.nivel, e.codigoAcceso from CodigoAcceso e").list();
			
			for (Object[] objects : codigos) {
				if(contador == 0) {
					System.out.println();
					System.out.println("\tLista de Códigos de Acceso\tPagina: " + numPagina);
					System.out.println("══════════════════════════════════════════════════════════════════════");
					System.out.println("  Código  NIvel de Acceso\tCodigo\t\tEncriptación");
					System.out.println("══════════════════════════════════════════════════════════════════════");
				}
				contador++;
				if(contador == 3) {   // HE CREADO UNA PAGINACION POR NUMERO DE REGISTROS LEÍDOS //	
					contador = 0;
					numPagina++;
				}
				contaCodigos++;
				 try {
				 		//  CODIGO PARA DESINCRIPTAR //	 
						String nombreOriginal =  Encriptaciones.decrypt(key, iv,objects[2].toString());
						String.format("%-10s", objects[1].toString());
						System.out.println("\t"+objects[0] + "  " + String.format("%-20s", objects[1].toString()) + "\t" + String.format("%-15s", nombreOriginal) + "\t" + objects[2].toString()); 

					} catch (Exception e) {
						System.out.println("No ha sido posible desincriptar ese codigo");;
				}
	
			}
			
			System.out.println();
			if(contaCodigos > 0)
				System.out.println("\n\t\tHay " + contaCodigos + " empleados en la empresa");
			else
				System.out.println("\n\t\tNo hay Empleados Registrados en la Empresa");
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
					 		
	}

}
