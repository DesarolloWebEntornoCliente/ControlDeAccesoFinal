package es.altair.hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import es.altair.hibernate.bean.CodigoAcceso;
import es.altair.hibernate.bean.Departamento;
import es.altair.hibernate.bean.Empleado;

public class DepartamentoDAOImplHIbernate implements DepartamentoDAO {

	public void save(Departamento dep) {
		
		Session sesion = Conexion.abrirConexion();
		
		sesion.save(dep);
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

	public Departamento obtener(int codDep) {
		Departamento dept = null;
		Session sesion = Conexion.abrirConexion();
		
		dept = (Departamento)sesion.createQuery("select e from Departamento e where idDepartamento=:id").setParameter("id", codDep).uniqueResult();
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
		return dept;
	}

	public void listarDeptos(int tamanyoPagina) {
		
		Session sesion = Conexion.abrirConexion();
		
		long numDepartamentos = (Long) sesion.createQuery("select count(*) from Departamento d").uniqueResult(); 		
		
		int numPaginas = (int) Math.ceil(numDepartamentos/tamanyoPagina);
		
		Query query = (Query) sesion.createQuery("select d from Departamento d order by d.descripcion").setMaxResults(tamanyoPagina);
		
		for (int i = 0; i < numPaginas; i++) {
						
			query.setFirstResult(i * tamanyoPagina);
			
			List<Departamento> departamentos = query.list();
						
			System.out.println("Lista de Empleados por Departamentos" + "\t\tPagina: " + (i + 1));
			System.out.println("═════════════════════════════════════════════════════════════════════");
			
			for (Departamento departamento : departamentos) {
				

				System.out.println("\t"+departamento.getDescripcion() + "\n");
				
				//Obtener los empleados de ese departamento				
				Query query1 = (Query) sesion.createQuery("select e from Empleado e where idDepartamento=:id").setParameter("id", departamento.getIdDepartamento());
				
				
					List<Empleado> vamosla = query1.list(); // crea una lista de los empleados del deàrtamento
				
				for (Empleado empleado : vamosla) { // LIsta todos los empleados del departamento
					System.out.println("\t\t"+empleado.getNombre() + "  -  " + empleado.getFuncion());
				
				}	
				
			}
			
		}
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
	}

	public void listaGeneraldeDepartamentos() {
		List<Departamento> deps = new ArrayList<Departamento>();
		
		Session sesion = Conexion.abrirConexion();
		
		
		// consulta SQLQuery 
		deps = sesion.createSQLQuery("select * from departamentos order by descripcion").addEntity(Departamento.class).list();
		
		System.out.println();
		System.out.println("Lista General de Departamentos");
		System.out.println("═════════════════════════════════════════════════════════════");	
		
		int contador = 0;
		for (Departamento departamento : deps) {
				contador++;
				System.out.println();
				System.out.println("\t" + departamento.getIdDepartamento() + "   "  + String.format("%-30s", departamento.getDescripcion()));

			}

		System.out.println("\n\t\tHay " + contador + " Departamentos en la empresa");
		
			sesion.getTransaction().commit();
					
		Conexion.desconectar(sesion);
		
	}

	public void borrar(Departamento depBorrar) {
		Session sesion = Conexion.abrirConexion();
		
		sesion.delete(depBorrar);
		
		sesion.getTransaction().commit();
				
	Conexion.desconectar(sesion);
		
	}

}
