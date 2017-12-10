package es.altair.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.altair.hibernate.bean.Departamento;
import es.altair.hibernate.bean.Empleado;

public class EmpleadoDAOImplHIbernate implements EmpleadoDAO {

	public void save(Empleado emp) {
		
	Session sesion = Conexion.abrirConexion();
		
		try {
			
			sesion.save(emp);
			
			sesion.getTransaction().commit();
			
				System.out.println("Empleado Añadido con Exito");
	
			
		} catch (ConstraintViolationException e) {
			
			sesion.getTransaction().rollback();
			System.out.println("-- ERRORES --");
			for (ConstraintViolation cv : e.getConstraintViolations()) {
				System.out.println("Campo: " + cv.getPropertyPath());
				System.out.println("Mensage: " + cv.getMessage());
				
			}
		} finally {
			Conexion.desconectar(sesion);
	
		}
		
	}
	
	public Empleado obtener(int codEmp) {
		
		Empleado emp = null;
		Session sesion = Conexion.abrirConexion();
		
		emp = (Empleado)sesion.createQuery("select e from Empleado e where idEmpleado=:id").setParameter("id", codEmp).uniqueResult();
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
		return emp;
	}

	public void borrar(Empleado empBorrar) {
		
	Session sesion = Conexion.abrirConexion();
		
			sesion.delete(empBorrar);
			
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



	public void listaEmpleadosConSusAccesos() {
		List<Object[]> emps = new ArrayList<Object[]>();
		
		Session sesion = Conexion.abrirConexion();
		
		
		// consulta SQLQuery 
		
		emps = sesion.createSQLQuery("select a.idEmpleado, e.nombre, e.apellidos, d.idDepartamento, d.descripcion from accesosempleados a \r\n" + 
				"join departamentos d on (a.idCodigoAcceso = d.idCodigoAcceso)\r\n" + 
				"join empleados e on (a.idEmpleado = e.idEmpleado)\r\n" + 
				"group by a.idEmpleado, a.idCodigoAcceso, d.idDepartamento\r\n" + 
				"order by a.idEmpleado, a.idCodigoAcceso, d.descripcion").list();		
		
		int numPagina = 1, contaEmpleados = 1, contaAccesos = 0;
		int contador = 1;

		System.out.println();
		System.out.println("═══════════════════════════════════════════════════════════════════════════════════════");
		System.out.println("Lista de Empleados y los Departamentos a que posee Acceso\tPagina: " + numPagina);
		System.out.println("═══════════════════════════════════════════════════════════════════════════════════════");
		
		Object auxiliar = emps.get(0)[0];
		System.out.println("\t" + emps.get(0)[1] + " " + emps.get(0)[2]);
		
		for (Object[] objects : emps) {
			

			contaAccesos++;
			
			if(auxiliar != objects[0]) {
				contador++;
				contaEmpleados++;
				if(contador == 3) {   // HE CREADO UNA PAGINACION POR NUMERO DE REGISTROS LEÍDOS //	
					contador = 1;
					numPagina++;
					System.out.println();
					System.out.println("═══════════════════════════════════════════════════════════════════════════════════════");
					System.out.println("Lista de Empleados y los Departamentos a que posee Acceso\tPagina: " + numPagina);
					System.out.println("═══════════════════════════════════════════════════════════════════════════════════════");
				}
				System.out.println();
				System.out.println("\t" + objects[1] + " " + objects[2]);
				auxiliar = objects[0];

			}
			//System.out.println("\t" + objects[2] + "\t" + objects[3]);
			System.out.println("\t\t" + objects[4]);
		}
		
			System.out.println();
			if(contador > 0)
				System.out.println("\n\t\tHay " + contaAccesos + " Accesos liberados para los " + contaEmpleados + " empleados que existen en la empresa");
			else
				System.out.println("\n\t\tNo hay Empleados Registrados en la Empresa");
			
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
	}

	public void listaGeneralDeEmpleados() {
		List<Empleado> emps = new ArrayList<Empleado>();
		
		Session sesion = Conexion.abrirConexion();		
		
		// consulta SQLQuery 
		emps = sesion.createSQLQuery("select * from empleados order by nombre").addEntity(Empleado.class).list();
		
		int numPagina = 1, contaEmpleados = 0;
		int contador = 0;
		for (Empleado empleados : emps) {
			
				if(contador == 0) {
					System.out.println();
					System.out.println("\n\t\tLista General de Empleados\tPagina: " + numPagina);
					System.out.println("════════════════════════════════════════════════════════════════════════════");	
					System.out.println("  Cód\tNombre\t\t\t\tDepartamento\tSituación");
					System.out.println("════════════════════════════════════════════════════════════════════════════");
				}
				contador++;
				if(contador == 3) {   // HE CREADO UNA PAGINACION POR NUMERO DE REGISTROS LEÍDOS //	
					contador = 0;
					numPagina++;
				}
				contaEmpleados++;
				System.out.print("  " + empleados.getIdEmpleado() + "\t"  + String.format("%-30s", (empleados.getNombre() + " "+ empleados.getApellidos())) + "\t" + String.format("%-15s", (empleados.getFuncion())) + "\t");

				
				if(empleados.getEfetivo().equals("E"))
					System.out.print("Efetivo");
				else
					System.out.print("Contratado");
				
				System.out.println();

			}
				System.out.println();
				if(contador > 0)
					System.out.println("\n\t\tHay " + contaEmpleados + " empleados en la empresa");
				else
					System.out.println("\n\t\tNo hay Empleados Registrados en la Empresa");
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
	}

	public void listaEfetivosContratados(String tipoLista) {
		
	List<Empleado> emps = new ArrayList<Empleado>();
		
		Session sesion = Conexion.abrirConexion();
		
		int opcionDeTexto = 0;
		
		if(tipoLista.equals("T")) {
			emps = sesion.createSQLQuery("select * from empleados order by efetivo").addEntity(Empleado.class).list();
			opcionDeTexto = 1;
		}

		else {
			emps = sesion.createCriteria(Empleado.class).add(Restrictions.eqOrIsNull("efetivo", tipoLista))
			.addOrder(Order.asc("efetivo"))
			.addOrder(Order.asc("nombre")).list();
			opcionDeTexto = 0;
		}
			int numPagina = 1, contaEmpleados = 0;	
			int contador = 0;
			for (Empleado empleados : emps) {
				if(contador == 0) {
					System.out.println();
					System.out.print("\n\t\tLista General de Empleados");
					
					if(tipoLista.equals("E"))
						System.out.print(" Efetivos \tPagina: " + numPagina + "\n");
					else if(tipoLista.equals("C"))
						System.out.print(" Contratados \tPagina: " + numPagina + "\n");
					else {
						System.out.print("\tPagina: " + numPagina + "\n");
					}
						if(opcionDeTexto == 1) {
							System.out.println("════════════════════════════════════════════════════════════════════════════");	
							System.out.println("  Cód\tNombre\t\t\t\tDepartamento\tSituación");
							System.out.println("════════════════════════════════════════════════════════════════════════════");
						}else {
							System.out.println("═══════════════════════════════════════════════════════════════════");	
							System.out.println("  Cód\tNombre\t\t\t\tDepartamento");
							System.out.println("═══════════════════════════════════════════════════════════════════");
							}
				}
				contador++;
				if(contador == 3) {  // HE CREADO UNA PAGINACION POR NUMERO DE REGISTROS LEÍDOS //	
					contador = 0;
					numPagina++;
				}
				contaEmpleados++;
				System.out.println();
				System.out.print("  " + empleados.getIdEmpleado() + "\t"  + String.format("%-30s", (empleados.getNombre() + " "+ empleados.getApellidos())) + "\t" + String.format("%-15s", (empleados.getFuncion())) + "\t");
				
					if(tipoLista.equals("T")) {
						if(empleados.getEfetivo().toString().equals("E"))
							System.out.print("Efetivo");
						else
							System.out.print("Contratado");
					}
					
				}
				System.out.println();

				if(contaEmpleados > 0)
					System.out.println("\n\t\tHay " + contaEmpleados + " empleados en la empresa");
				else
					System.out.println("\n\t\tNo hay Empleados Registrados en la Empresa");
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
	}

	public boolean verificaDNI(String documiento) {
		Empleado emp = null;
		Session sesion = Conexion.abrirConexion();
		
		emp = (Empleado)sesion.createQuery("select e from Empleado e where documiento=:documiento").setParameter("documiento", documiento).uniqueResult();
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
		if(emp == null)
		  return false;
		
		return true;
	}

}
