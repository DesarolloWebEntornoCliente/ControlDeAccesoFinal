package es.altair.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.hibernate.Query;
import org.hibernate.Session;

import es.altair.hibernate.bean.CodigoAcceso;
import es.altair.hibernate.bean.Departamento;
import es.altair.hibernate.bean.Empleado;

public class DepartamentoDAOImplHIbernate implements DepartamentoDAO {

	public void save(Departamento dep) {
		
		Session sesion = Conexion.abrirConexion();
	
		try {
			
			sesion.save(dep);
			
			sesion.getTransaction().commit();
			
				System.out.println("Departamento Añadido con Exito");
				
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
	
	public Departamento obtener(int codDep) {
		Departamento dept = null;
		Session sesion = Conexion.abrirConexion();
		
		dept = (Departamento)sesion.createQuery("select e from Departamento e where idDepartamento=:id").setParameter("id", codDep).uniqueResult();
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
		return dept;
	}
	
	public void borrar(Departamento depBorrar) {
		
		Session sesion = Conexion.abrirConexion();
		
		sesion.delete(depBorrar);
		
		sesion.getTransaction().commit();
				
	Conexion.desconectar(sesion);
		
	}

	public List<Object[]> listaDepartamentos() {
		
		List<Object[]> departs = new ArrayList<Object[]>();
		
		Session sesion = Conexion.abrirConexion();
			
		departs = sesion.createQuery("select e.idDepartamento, e.descripcion from Departamento e").list();
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
	
	return departs;
	}


	public void listarDeptos(int tamanyoPagina) {
		
		Session sesion = Conexion.abrirConexion();
		
		long numDepartamentos = (Long) sesion.createQuery("select count(*) from Departamento d").uniqueResult(); 		
		
		int numPaginas = (int) Math.ceil(numDepartamentos/tamanyoPagina);

		int contaEmpleados = 0, contaDeptos = 0;
		
		Query query = (Query) sesion.createQuery("select d from Departamento d order by d.descripcion").setMaxResults(tamanyoPagina);
		
		for (int i = 0; i < numPaginas; i++) {
						
			query.setFirstResult(i * tamanyoPagina);
						
			List<Departamento> departamentos = query.list();
						
			System.out.println();
			System.out.println("═════════════════════════════════════════════════════════════════════");
			System.out.println("Lista de Empleados por Departamentos" + "\t\tPagina: " + (i + 1));
			System.out.println("═════════════════════════════════════════════════════════════════════");
			
			for (Departamento departamento : departamentos) {
				
				contaDeptos++;

				System.out.println("  "+departamento.getDescripcion());
				
				//Obtener los empleados de ese departamento				
				Query query1 = (Query) sesion.createQuery("select e from Empleado e where idDepartamento=:id").setParameter("id", departamento.getIdDepartamento());
				
				
					List<Empleado> listaDeEmpleados = query1.list(); // crea una lista de los empleados del deàrtamento
				
				for (Empleado empleado : listaDeEmpleados) { // LIsta todos los empleados del departamento
					System.out.println("\t" + String.format("%-30s", (empleado.getNombre() + " "+ empleado.getApellidos())) + "\t" + String.format("%-15s", (empleado.getFuncion())) + "\t");
					contaEmpleados++;
				}	
				
				System.out.println();

			}
			
		}
		
		if(contaEmpleados > 0)
			System.out.println("\n\t\tHay " + contaEmpleados + " empleados en " + contaDeptos + " departamentos de la empresa");
		else
			System.out.println("\n\t\tNo hay Empleados Registrados en la Empresa");
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
	}

	public void listaGeneraldeDepartamentos() {
		List<Departamento> deps = new ArrayList<Departamento>();
		
		Session sesion = Conexion.abrirConexion();
		
		int numPagina = 1, contaDeptos = 0;
		// consulta SQLQuery 
		deps = sesion.createSQLQuery("select * from departamentos order by descripcion").addEntity(Departamento.class).list();
				
		int contador = 0;
		for (Departamento departamento : deps) {
			if(contador == 0) {
				System.out.println();
				System.out.println("Lista General de Departamentos\t\tPagina: " + numPagina);
				System.out.println("═════════════════════════════════════════════════════════════");	
			}
				contador++;
				if(contador == 3) {
					numPagina++;
					contador = 0;					
				}
				contaDeptos++;
				System.out.println();
				System.out.println("\t" + departamento.getIdDepartamento() + "\t"  + String.format("%-30s", departamento.getDescripcion()));

			}

		System.out.println("\n\t\tHay " + contaDeptos + " Departamentos en la empresa");
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
	}



}
