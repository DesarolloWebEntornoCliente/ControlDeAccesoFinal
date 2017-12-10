package es.altair.hibernate.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;

import es.altair.hibernate.bean.AccesoEmpleados;
import es.altair.hibernate.bean.CodigoAcceso;
import es.altair.hibernate.bean.Departamento;
import es.altair.hibernate.bean.Empleado;
import es.altair.hibernate.dao.AccesoEmpleadoDAO;
import es.altair.hibernate.dao.AccesoEmpleadosDAOImplHIbernate;
import es.altair.hibernate.dao.CodigoAccesoDAO;
import es.altair.hibernate.dao.CodigoAccesoDAOImplHIbernate;
import es.altair.hibernate.dao.Conexion;
import es.altair.hibernate.dao.DepartamentoDAO;
import es.altair.hibernate.dao.DepartamentoDAOImplHIbernate;
import es.altair.hibernate.dao.EmpleadoDAO;
import es.altair.hibernate.dao.EmpleadoDAOImplHIbernate;
import es.altair.hibernate.utilitarios.Encriptaciones;
import es.altair.hibernate.utilitarios.Menus;

public class AppTest{
	
	private static Scanner sc = new Scanner(System.in);
	private static Scanner tecladoLine = new Scanner(System.in);
	private static Scanner tecladoInt = new Scanner(System.in);
	
	
	private static CodigoAccesoDAO cDAO = new CodigoAccesoDAOImplHIbernate();
	private static DepartamentoDAO depDAO = new DepartamentoDAOImplHIbernate();
	private static EmpleadoDAO empDAO = new EmpleadoDAOImplHIbernate();
	private static AccesoEmpleadoDAO aeDAO = new AccesoEmpleadosDAOImplHIbernate();

	public static void main( String[] args ) {
		
	       int opcion = 0;
	        do {
	    		Menus.menu();
	    			opcion = sc.nextInt();
	    			
	    			switch (opcion) {
	 				case 1: // añadir codigo de acceso
	 					
	 			       int opcionCodigos = 0;
	 			        do {
	 			        	Menus.menuCodigoDeAcceso();
	 		    			opcionCodigos = sc.nextInt();
	 		    			
	 		    			switch (opcionCodigos) {
	 		 				case 1: 
	 					
	 		 					System.out.println("Introduzca el Nombre para Encriptar: ");
	 		 					String nombreEncripta = sc.next();
	 		 						 		 					
	 		 					System.out.println("Introduzca Descripción del Nivel de Acceso: ");
	 		 					String nivel = tecladoLine.nextLine();
	 		 					
	 		 					String key = "92AE31A79FEEB2A3"; //llave
	 		 					String iv = "0123456789ABCDEF"; // vector de inicialización
	 		 					
		 		 					try {
		 		 						
		 								CodigoAcceso c1 = new CodigoAcceso(Encriptaciones.encrypt(key, iv, nombreEncripta.trim()), nivel);
		 								
		 		 						cDAO.save(c1);
	
		 		 						System.out.println("Codigo Añadido con Exito");
	
		 							} catch (Exception e2) {
		 										 								
		 								System.out.println("El nombre no se ha encriptado, el codigo NO ha sido añadido");
		 							}
	 		
		 		 				break;
	 		 					
	 		 				case 2:
	 		 					break;
	 		 				case 3: // Borrar Codigo de Acceso
	 		 					
	 		 					listaCodigosAcceso();
	 		 					
	 		 					System.out.println("\n\tIntroduzca el Código: ");
	 		 					int codCod = sc.nextInt();
	 		 					
	 		 					try {
	 		 						
	 			 					CodigoAcceso codObtenido = cDAO.obtener(codCod);

	 			 					System.out.println("¿Está Seguro que lo quieres Borrar (S/N)? ");
	 			 					String respuesta = sc.next();
	 			 					
	 			 					if(respuesta.trim().equalsIgnoreCase("S")) 
	 			 					{
	 			 						cDAO.borrar(codObtenido);
	 			 						
	 			 						System.out.println("\n\tDepartamento Borrado con Exito\n");
	 			 					}

	 							} catch (Exception e) {
	 								System.out.println("No se ha podido acceder al Codigo de Acceso Solicitado");
	 							}
	 		 					
	 		 					break;
	 		 				case 4: // Asignar Acceso a un empleado //
	 		 					
	 		 					listarEmpleado();
	 		 					
			 					System.out.println("\n\tIntroduzca el Código del Empleado: ");
			 					int codEmp = sc.nextInt();
			 					
			 					Empleado empObtenido = empDAO.obtener(codEmp);
			 					
			 					listaDepartamentos();
			 					
			 					System.out.println("\n\tIntroduzca Código del Departamento: ");
			 					int codDep = sc.nextInt();
			 					
			 					// obtenet departamento por id
			 					Departamento depObtenido = depDAO.obtener(codDep);
			 					
			 								 					
			 					CodigoAcceso codObtenido = depObtenido.getCodigoacceso();
			 								 								 					
			 					
			 					CodigoAcceso c1 = codObtenido;
			 					
			 					Empleado emp1 = empObtenido;
			 					
			 								 					
			 					AccesoEmpleados ae1 = new AccesoEmpleados(c1, emp1, new Date()); // guardando el acceso del empleado al departamento asignado
			 					
			 					aeDAO.save(ae1);
	 		 					
	 		 					break;
	 		 					
	 		 				case 5:
	 		 					break;
	 		 					
	 		 					
		    			}
		    			
	    	    	} while (opcionCodigos != 0); // frin de menu de Codigos de Acceso
	 			        
	 			        if(opcionCodigos == 0)
	 			        	break;
	
	 	 				
	 				case 2: // Añadir Departamento
		 					
		 					System.out.println("Instroduzca el Nombre del Departamento: ");
		 					String nombreDepto = tecladoLine.nextLine();
		 					
		 					if(nombreDepto.length() == 0)
		 						return;
		 					
		 					listaCodigosAcceso();
		 					
		 					System.out.println("\n\tIntroduzca Código de Acceso para ese Departamento: ");
		 					int codProv = sc.nextInt();
		 					
		 					CodigoAcceso c1 = new CodigoAcceso(codProv,"");
		 					
		 					Departamento d1 = new Departamento(nombreDepto,c1);
		 							 					
		 					depDAO.save(d1);
		 					
		 						System.out.println("Departamento Añadido con Exito");

		 					
		 				break;
		 					
	 				case 3: // Añadir Empleado //
	 					
	 				   System.out.println("Introduzca los Datos del Nuevo Empleado [ENTER para Retornar]");
	 				   System.out.println();
	 				   System.out.println("Nombre: ");
	 				   String intNombre = tecladoLine.nextLine();	
	 				   
	 				   if(intNombre.trim() == "") 
	 					   return;
	 					   
	 					   System.out.println("Apellidos: ");
	 					   String intApellidos = tecladoLine.nextLine();	
	 					   
	 					  // System.out.println("Edad: ");
	 					   //int intEdad = tecladoInt.nextInt();
	 					   
	 				       SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
	 					   Date dtn = null; 

	 					   System.out.println("Fecha de Nacimiento: [dd/mm/yyyy] ");
	 					   String fecha = sc.next();
	 					   
	 					   if(fecha.length() >0) { // si fecha no es null //
	 							try {
	 								dtn = formatoDelTexto.parse(fecha);
	 							} catch (ParseException e) {
	 								System.out.println("El formato de la fecha es invalido");
	 								dtn = null;
	 							}
	 					   }
	 					   
	 					   System.out.println("Nº Documiento: ");
	 					   String doc = sc.next();
	 					   
	 					   System.out.println("Función: ");
	 					   String func = sc.next();
	 					   
	 					   System.out.println("Efetivo[S/N]?: ");
	 					   String efet = sc.next();
	 					   efet = efet.toUpperCase();
	 					   
	 					   Date dtContrato = null;
	 					   System.out.println("Fecha de Contratación: [dd/mm/yyyy] ");
	 					   String fechaContrato = sc.next();
	 					   
	 					   if(fechaContrato.length() >0) { // si fecha no es null //
	 							try {
	 								dtContrato = formatoDelTexto.parse(fechaContrato);
	 							} catch (ParseException e) {
	 								System.out.println("El formato de la fecha es invalido");// se la fecha está mal, se quedará como nul;
	 								dtContrato = null;
	 							}
	 					   }
	 					   
	 					   System.out.println("Salario: ");
	 					   float sal = sc.nextFloat();
	 					   
	 					   
	 					   /////// Lista todos los Departamentos //
	 					  listaDepartamentos();

		 					System.out.println("\n\tIntroduzca Código del Departamento: ");
		 					int depProv = sc.nextInt();
		 					
		 					Departamento d2 = new Departamento(depProv,"");
		 					
		 					Empleado e1 = new Empleado(intNombre, intApellidos, dtn,doc,func, efet, dtContrato,sal, d2);

		 					empDAO.save(e1);

	 					
	 						System.out.println("Empleado Añadido con Exito");

	 					break;
	 					
	 				case 4: // Actualizar Departamento
	 					
	 					listaDepartamentos();
	 					
	 					System.out.println("\n\tIntroduzca el Código del Departamento: ");
	 					int codDep1 = sc.nextInt();
	 					Departamento depObtenido2 = null;
	 					try {
	 						
		 					depObtenido2 = depDAO.obtener(codDep1);
		 					
		 					String nombreDeptoAct = depObtenido2.getDescripcion();
		 					
		 					System.out.println("\tNombre Obtenido: " + nombreDeptoAct + "  " + depObtenido2.getIdDepartamento());
		 					
		 					System.out.println("Instroduzca el Nombre del Departamento: ");
		 					nombreDeptoAct = tecladoLine.nextLine();
		 					
		 					listaCodigosAcceso();
		 					
		 					System.out.println("\n\tIntroduzca Código de Acceso para ese Departamento: ");
		 					int codProvAct = sc.nextInt();
		 					
		 					System.out.println("¿Está Seguro que quieres Actualizar los Datos (S/N)? ");
		 					String respuesta2 = sc.next();
		 					respuesta2 = respuesta2.toUpperCase();
		 					
		 					if(respuesta2.trim().equalsIgnoreCase("S")) 
		 					{
		 						try {
		 							CodigoAcceso c2 = new CodigoAcceso(codProvAct,"");
					 					
				 					Session sesion = Conexion.abrirConexion();
				 							 					
				 					depObtenido2.setDescripcion(nombreDeptoAct);
				 					depObtenido2.setCodigoacceso(c2);
				 					
				 					sesion.update(depObtenido2);
				 					
				 					sesion.getTransaction().commit();
				 							
				 					Conexion.desconectar(sesion);
			 						
			 						System.out.println("\n\tDepartamento Actualizado con Exito\n");
			 						
								} catch (Exception e) {
									System.out.println("No se ha podido Actualizar el Departamento");
								}

		 					}	 					
				 					//	depDAO.actualizarConStrings(codProvAct,nombreDeptoAct);
				 					//	depDAO.actualizar(depObtenido2);

						} catch (Exception e) {
							System.out.println("No se ha podido Obtener el Departamento");

						}
	
		 					break;
	 				case 5: // Actualizar Empleado
		 					break;
	 				case 6: //BOrrar Departamrnto
	 					
	 					listaDepartamentos();
	 					
	 					System.out.println("\n\tIntroduzca el Código del Departamento: ");
	 					int codDep = sc.nextInt();
	 					
	 					try {
	 						
		 					Departamento depObtenido = depDAO.obtener(codDep);

		 					System.out.println("¿Está Seguro que lo quieres Borrar (S/N)? ");
		 					String respuesta = sc.next();
		 					
		 					respuesta = respuesta.toUpperCase();
		 					
		 					if(respuesta.trim().equalsIgnoreCase("S")) 
		 					{
		 						depDAO.borrar(depObtenido);
		 						
		 						System.out.println("\n\tDepartamento Borrado con Exito\n");
		 					}

						} catch (Exception e) {
							System.out.println("No se ha podido acceder al Departamento Solicitado");
						}
	 					
		 					break;
	 				case 7: // Borrar Empleado
	 					
	 					listarEmpleado();
		 					
	 					System.out.println("\n\tIntroduzca el Código del Empleado: ");
	 					int codEmp = sc.nextInt();
	 					
	 					try {
	 						
		 					Empleado empObtenido = empDAO.obtener(codEmp);

		 					System.out.println("¿Está Seguro que lo quieres Borrar (S/N)? ");
		 					String respuesta = sc.next();
		 					
		 					if(respuesta.trim().equalsIgnoreCase("S")) 
		 					{
		 						empDAO.borrar(empObtenido);
		 						
		 						System.out.println("\n\tEmpleado Borrado con Exito\n");
		 					}

						} catch (Exception e) {
							System.out.println("No se ha podido acceder al Registro del Empleado");
						}
	 					
	 		
		 				break;
	 				case 8: // Reports
	 					
	 					int opcionReports = 0;
	 				     do {
	 	 					Menus.menuReports();
		 		    			opcionReports = sc.nextInt();
		 		    			
		 		    			switch (opcionReports) {
	 					
	 					
									case 1: // Lista de General de Empleados
										
										empDAO.listaGeneralDeEmpleados();
										
										break;
			
									case 2: //Listar departamentos e empleados vinculados a ele
										
										depDAO.listarDeptos(2); // 3 es el tamaço de itenes que tendrºa cada página
										
										
										break;
									case 3: // Lista General de Departamentos
										
										depDAO.listaGeneraldeDepartamentos();
										
										break;
									case 4: // Empleados con Accesos que Posee 
										
											empDAO.listaEmpleadosConSusAccesos();
	
										break;
									case 5:  // Listar Códigos Desincriptados
										
											cDAO.listaCodigosDesincriptados();
	
										break;
									}
	 					
	 					
			    			
	    	    	} while (opcionReports != 0); // frin de menu de Codigos de Acceso
	 			        
	 			        if(opcionReports == 0)
	 			        	break;
	 					
		 					break; // Fin del Menu Principal(opcion 8)
		 					
	 	    			
	    			}
	    			
	    	} while (opcion != 0);  // fin del do while del menu Principal 	
	        

			sc.close();

	}

	private static void listarEmpleado() {
		
		List<Object[]> emps = empDAO.listaEmpleados();
		
		System.out.println("Lista de Empleados");
		System.out.println("══════════════════════════════════════════");

		
		for (Object[] objects : emps) {
			System.out.println("\t"+objects[0] + "  " + objects[1]);
		}
	}
		
	

	private static void listaCodigosAcceso() {
		List<Object[]> codigos = cDAO.listaCodigosAcceso();
		
		System.out.println("Lista de Códigos de Acceso");
		System.out.println("══════════════════════════════════════════");
		System.out.println("\tCód.\tNIvel de Acceso");
		System.out.println();
		for (Object[] objects : codigos) {
			System.out.println("\t"+objects[0] + "  " + objects[1]);
		}
	}
		

	private static void listaDepartamentos() {
		List<Object[]> departs = depDAO.listaDepartamentos();
		
		System.out.println("Lista de Departamentos");
		System.out.println("══════════════════════════════════════════");

		
		for (Object[] objects : departs) {
			System.out.println("\t"+objects[0] + "\t" + objects[1]);
		}
	}
}

