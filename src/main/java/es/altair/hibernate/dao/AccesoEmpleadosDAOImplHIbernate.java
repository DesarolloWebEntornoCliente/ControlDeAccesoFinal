package es.altair.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import es.altair.hibernate.bean.AccesoEmpleados;
import es.altair.hibernate.bean.CodigoAcceso;
import es.altair.hibernate.bean.Departamento;
import es.altair.hibernate.bean.Empleado;

public class AccesoEmpleadosDAOImplHIbernate implements AccesoEmpleadoDAO {

	public void save(AccesoEmpleados ae) {
		
		Session sesion = Conexion.abrirConexion();
		
		sesion.save(ae);
		
		sesion.getTransaction().commit();
				
	Conexion.desconectar(sesion);

	}

	public boolean verificaAccesoExistente(int codEmp, int codAcceso) {
		
	  Session sesion = Conexion.abrirConexion();
				
	  Query	aEmp = sesion.createSQLQuery("select a.*,e.*,c.* from accesosempleados a, empleados e, codigosacceso c "
	  									+ "where a.idEmpleado=:codEmp AND a.idCodigoAcceso=:codAcceso")
										.addEntity("accesosempleados", Empleado.class)
										.addEntity("accesosempleados", CodigoAcceso.class);
				
		List list = aEmp.setInteger("codEmp", codEmp)
				.setInteger("codAcceso", codAcceso)
				.list();
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
		if(list.size() == 0)
		  return false;
		
		return true;    
	}
	
}
