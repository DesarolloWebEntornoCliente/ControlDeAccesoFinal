package es.altair.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import es.altair.hibernate.bean.CodigoAcceso;
import es.altair.hibernate.bean.Departamento;
import es.altair.hibernate.utilitarios.Encriptaciones;

public class CodigoAccesoDAOImplHIbernate implements CodigoAccesoDAO {

	public void save(CodigoAcceso cod) {
		
	Session sesion = Conexion.abrirConexion();
		
		sesion.save(cod);
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
		
		 String key = "92AE31A79FEEB2A3"; //llave
		 String iv = "0123456789ABCDEF"; // vector de inicialización
			 
		 List<Object[]> codigos = new ArrayList<Object[]>();
		
		 Session sesion = Conexion.abrirConexion();
			
			codigos = sesion.createQuery("select e.idCodigoAcceso, e.nivel, e.codigoAcceso from CodigoAcceso e").list();
			
			System.out.println("Lista de Códigos de Acceso");
			System.out.println("═════════════════════════════════════════════════════════════");
			//System.out.println("\tCód.\tNIvel de Acceso");
			System.out.println();
			for (Object[] objects : codigos) {
				 try {
				 		//Codigo de desincriptacion //	 
						String nombreOriginal =  Encriptaciones.decrypt(key, iv,objects[2].toString());
						String.format("%-10s", objects[1].toString());
						System.out.println("\t"+objects[0] + "  " + String.format("%-20s", objects[1].toString()) + "\t" + String.format("%-15s", nombreOriginal) + "\t" + objects[2].toString()); 

					} catch (Exception e) {
						System.out.println("No ha sido posible desincriptar ese codigo");;
				}
	
			}
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
					 		
	}

	public CodigoAcceso obtener(int codCod) {
		
		CodigoAcceso codigo = null;
		
		Session sesion = Conexion.abrirConexion();
		
		codigo = (CodigoAcceso)sesion.createQuery("select e from codigosacceso e where idCodigoAcceso=:id").setParameter("id", codCod).uniqueResult();
		
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

}
