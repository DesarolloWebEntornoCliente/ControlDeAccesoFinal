package es.altair.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import es.altair.hibernate.bean.Departamento;
import es.altair.hibernate.bean.Empleado;

public class EmpleadoDAOImplHIbernate implements EmpleadoDAO {

	public void save(Empleado emp) {
		
	Session sesion = Conexion.abrirConexion();
		
		sesion.save(emp);
		sesion.getTransaction().commit();
				
	Conexion.desconectar(sesion);

		
	}

	public List<Object[]> listaEmpleados() {
		
		List<Object[]> emps = new ArrayList<Object[]>();
		
		Session sesion = Conexion.abrirConexion();
			
		emps = sesion.createQuery("select e.idEmpleado, e.nombre from Empleado e").list();
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
	
	return emps;
	}

	public Empleado obtener(int codEmp) {
		
		Empleado emp = null;
		Session sesion = Conexion.abrirConexion();
		
		emp = (Empleado)sesion.createQuery("select e from Empleado e where idEmpleado=:id").setParameter("id", codEmp).uniqueResult();
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
		return emp;
	}

	public void listaEmpleadosConSusAccesos() {
		List<Object[]> emps = new ArrayList<Object[]>();
		
		Session sesion = Conexion.abrirConexion();
		
		
		// consulta SQLQuery 
		emps = sesion.createSQLQuery("select e.idEmpleado, nombre, a.idCodigoAcceso, descripcion from empleados e join accesosempleados a on (e.idEmpleado = a.idEmpleado)\r\n" + 
				"join departamentos d on (a.idCodigoAcceso  = d.idCodigoAcceso)\r\n" + 
				"group by e.idEmpleado,a.idCodigoAcceso order by e.idEmpleado, a.idCodigoAcceso;").list();
		
		System.out.println();
		System.out.println("Lista de Empleados y los Departamentos a que posee Acceso");
		System.out.println("═════════════════════════════════════════════════════════════");	
		
		Object auxiliar = emps.get(0)[0];
		System.out.println(auxiliar.toString() + "   " + emps.get(0)[1]);

		for (Object[] objects : emps) {
			
			if(auxiliar != objects[0]) {
				System.out.println();
				System.out.println(auxiliar.toString() + "   " + objects[1]);
				auxiliar = objects[0];

			}
			System.out.println("\t" + objects[2] + "\t" + objects[3]);
		//	System.out.println("\t"+objects[0] + "\t" + String.format("%-20s", objects[1].toString()) + "\t" + objects[2] + "\t" + objects[3]);
		}
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
	}

	public void listaGeneralDeEmpleados() {
		List<Empleado> emps = new ArrayList<Empleado>();
		
		Session sesion = Conexion.abrirConexion();
		
		
		// consulta SQLQuery 
		emps = sesion.createSQLQuery("select * from empleados order by nombre").addEntity(Empleado.class).list();
		
		System.out.println();
		System.out.println("Lista General de Empleados");
		System.out.println("═════════════════════════════════════════════════════════════");	
		
		int contador = 0;
		for (Empleado empleados : emps) {
				contador++;
				System.out.println();
				System.out.println("\t" + empleados.getIdEmpleado() + "   "  + String.format("%-30s", (empleados.getNombre() + " "+ empleados.getApellidos())) + "\t" + empleados.getFuncion());

			}

		System.out.println("\n\\ttHay " + contador + " empleados en la empresa");
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
	}

	public void borrar(Empleado empBorrar) {
		
	Session sesion = Conexion.abrirConexion();
		
			sesion.delete(empBorrar);
			
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
	}

}
