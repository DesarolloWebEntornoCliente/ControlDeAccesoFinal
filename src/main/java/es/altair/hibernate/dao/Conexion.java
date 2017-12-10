package es.altair.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Conexion {
	
	public static Session abrirConexion() {
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();

		Session sesion = sf.openSession();
		
		try {
			sesion.beginTransaction();

			
		} catch (Exception e2) {
			//e2.printStackTrace();
			System.out.println("No se ha podido conectar con la Base de Datos");
			sesion.close();
		}
		
	
    return sesion;
		    
	}
	
	public static void desconectar(Session sesion) {
		
		sesion.close();
		
        
	}

}





