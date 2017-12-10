package es.altair.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import es.altair.hibernate.bean.AccesoEmpleados;

public class AccesoEmpleadosDAOImplHIbernate implements AccesoEmpleadoDAO {

	public void save(AccesoEmpleados ae) {
		
		Session sesion = Conexion.abrirConexion();
		
		sesion.save(ae);
		
		sesion.getTransaction().commit();
				
	Conexion.desconectar(sesion);

	}

}
