package es.altair.hibernate.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Convert;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

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
	
	private static	String key = "47AE31A79FEEB2A3"; //llave
	private static  String iv = "0123456789ABCDEF"; // vector de inicialización

	public static void main( String[] args ) {
		
	     String opc = "";
	     int opcion = 0;
	  
	        do {	        	
	        		 Menus.menu(); // MOSTRA EL MENU PRINCIPAL
		
		    		opc = sc.next();
	    			
	    			try {
	    				opcion = Integer.parseInt(opc); // VERIFICA SE HA SIDO INTRODUZIDA CARACTERES QUE NO SEA NUMERO // 
	    			} catch (NumberFormatException nfe){
	    				System.out.println("Formato Invalido, Introduzca Otra Opción");
	    				opcion = 20;
	    			}
			
	    			
	    			switch (opcion) {
	 				case 1: // EJECUTA EL MENU DE CODIGO DE ACCESOS QUE TIENE 4 OPCIONES, COMO SIGUE  //
	 				
	 				String opc1 = "";	
	 			    int opcionCodigos = 0;
	 			    
	 			    do {
	 			        	
	 			        Menus.menuCodigoDeAcceso();	 	// CASO SEA 1, MOSTRARÁ EL MENU DE CODIGOS DE ACCESO	//	        	
	 		    		
	 		    		opc1 = sc.next();
		    			
		    			try {
		    				opcionCodigos = Integer.parseInt(opc1);
		    			} catch (NumberFormatException nfe){
		    				System.out.println("Formato Invalido, Introduzca Otra Opción");
		    				opcionCodigos = 20;
		    			}
	 		    			
	 		    		switch (opcionCodigos) {
	 		 			case 1: // AÑADIR UN CODIGO DE ACCESO
	 					
	 		 					System.out.println("Introduzca el Nombre para Encriptar: ");
	 		 					String nombreEncripta = sc.next();
	 		 						 		 					
	 		 					System.out.println("Introduzca Descripción del Nivel de Acceso: ");
	 		 					String nivel = tecladoLine.nextLine();
	 		 					
		 		 					try {
		 		 						
		 								CodigoAcceso c1 = new CodigoAcceso(Encriptaciones.encrypt(key, iv, nombreEncripta.trim()), nivel.trim());
		 								
		 		 						cDAO.save(c1); // EN EL CODIGO IMPLEMENT HAGO EM ConstraintViolation // HA SIDO UTILIZADO .save //
	
	
		 							} catch (Exception e2) {
		 										 								
		 								System.out.println("El nombre no se ha encriptado, el codigo NO ha sido añadido");
		 							}
	 		
		 		 			break;
	 		 					
	 		 			case 2: // ACTUALIZAR UN CODIGO DE ACCESO
	 		 					
	 		 					listaCodigosAcceso();
	 		 					
		 		 					System.out.println("\n\tIntroduzca el Código a Actualizar: ");
		 		 					int cod1 = sc.nextInt();
		 		 					
		 		 					ActualizaCodigoAcceso(cod1);  // HA SIDO UTILIZADO .update //
	
	 		 				break;	
	 		 					
	 		 			case 3: // BORRAR UN CODIGO DE ACCESO
	 		 					
	 		 					listaCodigosAcceso();
	 		 					
	 		 					System.out.println("\n\tIntroduzca el Código: ");
	 		 					int codCod = sc.nextInt();
	 		 					
	 		 					try {
	 		 						
 			 					CodigoAcceso codObtenido = cDAO.obtener(codCod); // HA SIDO UTILIZADO CreateQuery  //

 			 					System.out.println("¿Está Seguro que lo quieres Borrar (S/N)? ");
 			 					String respuesta = sc.next();
	 			 				
	 			 				if(respuesta.trim().equalsIgnoreCase("S")) 
 			 					  {
 			 						try {
 			 							cDAO.borrar(codObtenido);  // HA SIDO UTILIZADO .delete //

 			 							System.out.println("\n\tCódigo de Acceso Borrado con Exito\n");

									} catch (Exception e) {
										System.out.println("ERROR, Hay Departamento Asociado a ese Código de Acceso");
									}
 			 						
 			 					  }

	 							} catch (Exception e) {
	 								System.out.println("No se ha podido acceder al Codigo de Acceso Solicitado");
	 							}
	 		 					
	 		 				break;
	 		 			case 4: // ASIGNAR ACCESO A LOS DEPARTAMENTOS PARA UN EMPLEADO //
	 		 					
	 		 					listarEmpleado();
	 		 					
			 					System.out.println("\n\tIntroduzca el Código del Empleado: ");
			 					int codEmp = sc.nextInt();
			 					
			 					Empleado empObtenido = empDAO.obtener(codEmp); // OBTIENE EL EMPLEADO QUE RECIBIRÁ EL ACCESO  //
			 					
			 					listaCodigosAcceso();
			 					
			 					System.out.println("\n\tIntroduzca el Código de Acceso para el Empleado: ");
			 					int codAcceso = sc.nextInt();
			 					
			 					CodigoAcceso codObtenido = cDAO.obtener(codAcceso); // OBTIENE EL CODIGO DE ACCESO PARA GURDAR EN LA BD JUNTO AL EMPLEADO 
			 					
			 					CodigoAcceso c1 = codObtenido;
			 					
			 					Empleado emp1 = empObtenido;
			 								 								 					
			 					AccesoEmpleados ae1 = new AccesoEmpleados(c1, emp1, new Date()); // GUARDA EL ACCESO DEL EMPLEADO A LOS DEPARTAMENTOS CON ESE TIPO DE ACCESO  //
			 								 					
			 					// EN ESE METODO HE UTILIZADO createSQLQuery, CON DOS PARAMETROS, DE UNA FORMA DOFERENTE DE LA QUE VIMOS EN CLASE, 
			 					// ESPERO QUE LO CONSIDERE, HA SIDO LA UNICA FORMA QUE HA IDO BIEN // ( ** Está en AccesoEmpleadosDAOImplHIbernate ** )
			 					
	 							if(aeDAO.verificaAccesoExistente(codEmp, codAcceso)) // VERIFICA SE EL EMPLEADO YA TIENE ASIGNADO ESE ACCESO  //
	 								System.out.println("Ese Empleado Ya Tiene ese Nivel de Acceso, Inserción Cancelada");
	 							else			 					
	 								aeDAO.save(ae1); // SI NO LO TIENE , LO GUARDA  //
	 		 					
	 		 					break;
	 		    			}
		    			
	    	    	} while (opcionCodigos != 0); // AQUI FINALIZA LAS OPCIONES DEL MENU DE CODIGO DE ACCESO ////////////
	 			        
	 			    if(opcionCodigos == 0)  // ESA VERIFICACION ES PARA QUE SALTE DIRECTAMENTE EN EL MENU PRINCIPAL //
	 			       break; 
	 			       
	 	 				
	 				case 2: // AÑADIR DEPARTAMENTO
		 					
			 					System.out.println("Instroduzca el Nombre del Departamento: ");
			 					String nombreDepto = tecladoLine.nextLine();
			 					
			 					listaCodigosAcceso();
			 					
			 					System.out.println("\n\tIntroduzca Código de Acceso para ese Departamento: "); // DETERMINA QUE CODIGO DE ACCESO TENDRÁ ESE DEPARTAMENTO  //
			 					int codProv = sc.nextInt();
			 					
			 					CodigoAcceso c1 = new CodigoAcceso(codProv,"");
			 					
			 					Departamento d1 = new Departamento(nombreDepto,c1);
			 							 					
			 					depDAO.save(d1); // LAS VERIFICACIONES DE VIOLATION, ESTÁN EL EL IMPLEMENT	// 					
		 					
		 				break;
		 					
	 				case 3: // AÑADIR EMPLEADO
	 					
	 							Empleado e1 = AnadirEmpleado(); // ABAJO, ESTÁ EL CODIGO DE ESE MÉTODO  //
	 							
	 							if(empDAO.verificaDNI(e1.getDocumiento()))
	 								System.out.println("Ya existe Registro de otro Empleado con el DNI: " + e1.getDocumiento() + "  Inserción Cancelada");
	 							else
	 								empDAO.save(e1);
		 					
	 					break;
	 					
	 				case 4: // ACTUALIZAR DEPARTAMENTO
	 					
			 					listaDepartamentos();
			 					
			 					System.out.println("\n\tIntroduzca el Código del Departamento: ");
			 					int codDep1 = sc.nextInt();
			 					
			 					Departamento depObtenido2 = null;
			 					try {
			 						
				 					actualizarDepartamento(codDep1);	// ABAJO, ESTÁ EL CODIGO DE ESE MÉTODO  //					
		
								} catch (Exception e) {
									System.out.println("No se ha podido Obtener el Departamento");
		
								}
	
		 				break;
	 				case 5: // ACTUALIZAR EMPLEADO
	 					
	 							Empleado empObtenido12 = null;

				 				listarEmpleado();
				 					
			 					System.out.println("\n\tIntroduzca el Código del Empleado: ");
			 					int codEmp12 = sc.nextInt();
			 					
			 					
			 					try {
			 							ActualizarEmpleado(codEmp12);  // ABAJO, ESTÁ EL CODIGO DE ESE MÉTODO  //
		 												 				
				 				} catch (Exception e) {
										System.out.println("No se ha podido Obtener el Empleado");
								}
	 					
		 					break; 
	 				case 6: // BORRAR DEPARTAMENTO
	 					
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
				 							try {
				 								
						 						depDAO.borrar(depObtenido);
						 						
						 						System.out.println("\n\tDepartamento Borrado con Exito\n");
	
											} catch (Exception e) {
												System.out.println("El Departamenton no Existe o Aún Tiene Empleado(s) Asociados, no se puede Borrarlo");
											}
					 						
						 				  }
		
								} catch (Exception e) {
									System.out.println("No se ha podido acceder al Departamento Solicitado");
								}
	 					
		 					break;
	 				case 7: // BORRAR EMPLEADO
	 					
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
		 				
	 				case 8: // MOSTRA EL MENU DE RELATORIOS
	 					
			 					int opcionReports = 0;
			 				     do {
			 	 					Menus.menuReports();
				 		    		opc1 = sc.next();
					    			
					    			try {
					    				opcionReports = Integer.parseInt(opc1);
					    			} catch (NumberFormatException nfe){
					    				System.out.println("Formato Invalido, Introduzca Otra Opción");
					    				opcionReports = 20;
					    			}
					    			
				 		    			switch (opcionReports) {
			 					
			 					
										case 1: // Lista General de Empleados
											
													empDAO.listaGeneralDeEmpleados();
											
											break;				
										case 2: //Listar departamentos e empleados pertenecentes al Departamento  //
											
													// HE UTILIZADO VARIOS createQuery PARA SACAR TODOS LOS DATOS Y TAMBIÉN HE UTILIZADO EL Pagination, QUE HEMOS DADO EN CLASE //
											
													depDAO.listarDeptos(2); // 3 ES LA CANTIDAD DE ITENES QUE TENDRÁ CADA PÁGINA // 
																						
											break;
										case 3: // Lista General de Departamentos // TODOS LOS LISTADOS ESTÁN PAGINADOS PERO, UTILIZO UN PAGINADO MÁS SIMPLE, AÚN QUE, NO MENOS EFICAZ
											
													depDAO.listaGeneraldeDepartamentos(); // HE UTILIZADO createSQLQuery  //
											
											break;
										case 4: // Empleados con Accesos que Posee // LISTA LOS MEPLEADOS CON TODOS LOS DEPARTAMENTOS A QUE LE HAN DADO ACCESO
											
													empDAO.listaEmpleadosConSusAccesos(); // HE UTILIZADO createSQLQuery  Y TODOS LOS joins NECESARIOS PARA SACAR INFORMACIONES DE 3 TABLAS//
		
											break;
										case 5:  // Listar Códigos Desincriptados // LISTA TODOS LOS CODIGOS DE ACCESO CON LAS INFORMACIONES COMPLETAS
											
													cDAO.listaCodigosDesincriptados(); // HE UTILIZADO createQuery  //
		
											break;
										case 6:  // Listar Empleados Efetivos y/o Contratados /////  LISTA TODOS LOS EMPLEADOS, CON OPCIÓN DE LISTAR, TODOS, SOLO LOS EFETIVOS O SOLO LOS CONTRATADOS
											
						 					
						 					System.out.println("\n\tIntroduzca < T > para Listar [Todos], < E > [Efetivos] o < C > [Contratados]: ");
						 					String tipoLista = tecladoLine.nextLine();
						 					
						 					tipoLista = tipoLista.toUpperCase().trim();
						 					
						 					
						 					if(tipoLista.trim().equals("E") || tipoLista.equals("C")) {
						 						tipoLista = tipoLista;
						 					}else
						 						tipoLista = "T";
											
											empDAO.listaEfetivosContratados(tipoLista);  // HE UTILIZADO createCriteria PARA SACAR LOS EFETIVOS y/o CONTRATADOS, Y createQuery PARA LISTAR TODOS //

											break;
										}
											    			
			 				     	} while (opcionReports != 0); // FIN DEL MENU DE RELATORIOS (REPORTS)  //
			 			        
				 			          if(opcionReports == 0)  // ESA VERIFICACION ES PARA QUE SALTE DIRECTAMENTE EN EL MENU PRINCIPAL //
				 			        	 break;
	 					
				 			 break; // FIN DEL MENU PRINCIPAL, LA OPCION 8 //	 						 	    			
	    				}
	    			

			       
	        	 
			    } while (opcion != 0);  // // FIN DEL MENU PRINCIPAL  //	
	     
		sc.close();

	}

	private static void actualizarDepartamento(int codDep1) { // METODO PARA ACTUALIZAR DEPARTAMENTOS  //
		Departamento depObtenido2;
		depObtenido2 = depDAO.obtener(codDep1); // HE UTILIZADO CreateQuery //
		
		String nombreDeptoAct = depObtenido2.getDescripcion();
		
		// EL METODO RECIBE EL CODIGO DEL DEPARTAMENTO A SER MODIFICADO Y SACA TODAS SUS INFORMACIONES, LAS INFORMACIONES QUE EL USUARIO,
		// NO DESEE MODIFICAR, BASTARÁ CON ENTER PARA QUE SE MANTENGA LA INFORMACIÓN ACTUAL  //
		
		System.out.println("\tDatos Actuales: " + depObtenido2.getIdDepartamento() + "  " + nombreDeptoAct);
		
		System.out.println("Instroduzca el Nurevo Nombre del Departamento [ ENTER para Mantener el Actual ]: ");
		nombreDeptoAct = tecladoLine.nextLine();
		
		if(nombreDeptoAct.length() == 0)
			nombreDeptoAct = depObtenido2.getDescripcion();
		
		listaCodigosAcceso();
		
		System.out.println("\n\tIntroduzca el Nuevo Código de Acceso [ 0 para Mantener el Actual ]: ");
		int codProvAct = tecladoInt.nextInt();
		
		if(codProvAct == 0)
			codProvAct = depObtenido2.getCodigoacceso().getIdCodigoAcceso();
		
		System.out.println("¿Está Seguro que quieres Actualizar los Datos (S/N)? ");
		String respuesta2 = sc.next();
		respuesta2 = respuesta2.toUpperCase();
		
		if(respuesta2.trim().equalsIgnoreCase("S")) 
		{
			try { // VERIFICA INCONSISTENCIA DE DATOS //
				CodigoAcceso c2 = new CodigoAcceso(codProvAct,"");
					
				Session sesion = Conexion.abrirConexion();
						 					
				depObtenido2.setDescripcion(nombreDeptoAct);
				depObtenido2.setCodigoacceso(c2);
				
				
					try { //  VERIFICA SE TODOS LOS DATOS ENTÁN CORRECTOS //
						
						sesion.update(depObtenido2);
						
						sesion.getTransaction().commit();
						
						System.out.println("\n\tDepartamento Actualizado con Exito\n");

						
					} catch (ConstraintViolationException e) {
					
					sesion.getTransaction().rollback();
					System.out.println("-- ERRORES ENCUENTRADOS --");
					for (ConstraintViolation cv : e.getConstraintViolations()) {
						System.out.println("Campo: " + cv.getPropertyPath());
						System.out.println("Mensage: " + cv.getMessage());
						
					}
					
				}finally {
					Conexion.desconectar(sesion);
				}

			} catch (Exception e) { // SE HAY INCONSISTENCIA, NO GUARDA LOS DATOS // 
				System.out.println("No se ha podido Actualizar el Departamento");
			}

		}
	}

	private static Empleado AnadirEmpleado() { // METODO PARA AÑADIR EMPLEADO //
		System.out.println("Introduzca los Datos del Nuevo Empleado [ENTER para Retornar]");
		   System.out.println();
		   System.out.println("Nombre: ");
		   String intNombre = tecladoLine.nextLine();	

			   System.out.println("Apellidos: ");
			   String intApellidos = tecladoLine.nextLine();	
			   
		       SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy"); // HE CREADO UN FORMATO PARA LA FECHA Y INFORMO AL USUARIO EN LA SOLICITUD //
			   Date dtn = null; 

			   System.out.println("Fecha de Nacimiento: [dd/mm/yyyy] ");
			   String fecha = tecladoLine.nextLine();
			   
			   if(fecha.length() > 0) { // SOLICITO LA FECHA COMO String, DESPUÉS LA CONVIRTO EN Date //
					try {
						dtn = formatoDelTexto.parse(fecha);
					} catch (ParseException e) { // SI EL FORMATO NO ES ADECUADO, LA PONGO COMO Null, PARA QUE EL PROGRAMA NO COLAPSE  //
						System.out.println("El formato de la fecha es invalido"); 
						dtn = null;
					}
			   } else { // EN EL CASO DE QUE LA FECHA NO SEA INFORMADA, TBM LA CONVIRTO EN Null  //
				   dtn = null;
			   }
			   
			   System.out.println("Nº DNI: ");
			   String doc = tecladoLine.nextLine(); // VERIFICA FORMATO EN Constraint Validation // 
			   
			   System.out.println("Función: ");
			   String func = tecladoLine.nextLine();
			   
			   System.out.println("Introduzca e (Efetivo) o c (Contratado): "); // VERIFICA FORMATO EN Constraint Validation // 
			   String efet = tecladoLine.nextLine();
			   efet = efet.toUpperCase();
			   
			   Date dtContrato = null; // LO MISMO QUE EN FECHA DE NACIMIENTO //
			   System.out.println("Fecha de Contratación: [dd/mm/yyyy] ");
			   String fechaContrato = tecladoLine.nextLine();
			   
			   if(fechaContrato.length() >0) { 
					try {
						dtContrato = formatoDelTexto.parse(fechaContrato);
					} catch (ParseException e) {
						System.out.println("El formato de la fecha es invalido");// se la fecha está mal, se quedará como nul;
						dtContrato = null;
					}
			   }
			   
			    System.out.println("Salario: ");
			    String salar = tecladoLine.nextLine();
			   	 					   
			    Float sal;
			    	 					    
			    if(validaSalario(salar)) { // HAY UN METODO PARA CONVERTIR EL SALARIO EN VALOR , EN EL CASO DE SER INTRODUZIDO DATOS ALFANUMERICOS //
			    	Float f = new Float("0f");  // VERIFICA VALOR MINIMO EN Constraint Validation // 
			    	sal = f.parseFloat(salar);
			    }else {
			    	sal = 0f;
			    }
			     					    
			    listaDepartamentos();

				System.out.println("\n\tIntroduzca Código del Departamento: ");
				int depProv = tecladoInt.nextInt();
				
				Departamento d2 = null;
				try {
					d2 = depDAO.obtener(depProv);

				} catch (Exception e) {
					System.out.println("No se ha podido Obtener el Departamento");
				}			
				
				Empleado e1 = new Empleado(intNombre, intApellidos, dtn,doc,func, efet, dtContrato,sal, d2);
				
		return e1; // RETORNO A LA LLAMADA DEL METODO, DONDE LO VERIFICO Y GLO GUARDO SI ESTÁ TODO BIEN //
	}

	private static boolean validaSalario(String sal) { // METODO PARA VERIFICAR VALOR INTRODUZIDO EN EL CAMPO SALARIO //
		try {
			Float f = new Float("0f");
			float valido = f.parseFloat(sal);  // SI EL VALOR INTRODUZIDO NO ES VALIDO, GUARDA EL SALARIO COMO 0, Y DA ERROR PORQUE EL SALARIO NO PUEDE SER MENOR QUE 100 (MINIMO) //
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private static void ActualizarEmpleado(int codEmp12) { // METODO PARA ACTUALIZAR EMPLEADO //
		Empleado empObtenido12;
		empObtenido12 = empDAO.obtener(codEmp12); // HE UTILIZADO CreateQuery //
		
		// EL METODO RECIBE EL CODIGO DEL EMPLEADO A SER MODIFICADO Y SACA TODAS SUS INFORMACIONES, LAS INFORMACIONES QUE EL USUARIO,
		// NO DESEE MODIFICAR, BASTARÁ CON ENTER PARA QUE SE MANTENGA LA INFORMACIÓN ACTUAL  //
		
		   System.out.println("Introduzca los Datos del Nuevo Empleado [ENTER para Retornar]");
		   System.out.println();
		   System.out.println("Nombre: ");
		   String intNombre12 = tecladoLine.nextLine();	
		   
		   if(intNombre12.length() == 0)
			   intNombre12 = empObtenido12.getNombre();
		   
		   System.out.println("Apellidos: ");
		   String intApellidos12 = tecladoLine.nextLine();	
		   
		   if(intApellidos12.length() == 0)
			   intApellidos12 = empObtenido12.getApellidos();
		   
		   SimpleDateFormat formatoDelTexto12 = new SimpleDateFormat("dd/MM/yyyy");
		   Date dtn12 = null;  

		   System.out.println("Fecha de Nacimiento: [dd/mm/yyyy] ");
		   String fecha12 = tecladoLine.nextLine();
		   
		   if(fecha12.length() >0) { // si fecha no es null //
				try {
					dtn12 = formatoDelTexto12.parse(fecha12);
				} catch (ParseException e) {
					System.out.println("El formato de la fecha es invalido");
					dtn12 = null;
				}
		   } else {
			   
			   dtn12 = empObtenido12.getFechaNacto();
		   }
		   
		   // TODOS LOS DATOS SON VERIFICADOS DE LA MISMA FORMA QUE EN AÑADIR //
		   System.out.println("Nº DNI: ");
		   String doc12 = tecladoLine.nextLine();
		   
		   if(doc12.length() == 0)
			   doc12 = empObtenido12.getDocumiento();
		   
		   System.out.println("Función: ");
		   String func12 = tecladoLine.nextLine();
		   
		   if(func12.length() == 0)
			   func12 = empObtenido12.getFuncion();
		   
		   System.out.println("Introduzca e (Efetivo) o c (Contratado): ");
		   String efet12 = tecladoLine.nextLine();
		   efet12 = efet12.toUpperCase();
		   
		   if(efet12.length() == 0)
			   efet12 = empObtenido12.getEfetivo();
		   
		   Date dtContrato12 = null;
		   System.out.println("Fecha de Contratación: [dd/mm/yyyy] ");
		   String fechaContrato12 = tecladoLine.nextLine();
		   
		   if(fechaContrato12.length() >0) { // si fecha no es null //
				try {
					dtContrato12 = formatoDelTexto12.parse(fechaContrato12);
				} catch (ParseException e) {
					System.out.println("El formato de la fecha es invalido");// se la fecha está mal, se quedará como nul;
					dtContrato12 = null;
				}
		   } else {
			   
			   dtContrato12 = empObtenido12.getFechaEntrada();
		   }
		   
		    System.out.println("Salario: ");
		    String salar12 = tecladoLine.nextLine();
		   	
		    Float sal12;

		    if(salar12.length() > 0) {
		    	
				    if(validaSalario(salar12)) {
				    	Float f = new Float("0f");
				    	sal12 = f.parseFloat(salar12);
				    }else {
				    	sal12 = 0f;
				    }
		    }else {
		    	
		    	sal12 = empObtenido12.getSalario();

		    }  
		    
		    listaDepartamentos();

			System.out.println("\n\tIntroduzca el Nuevo Código de Departamento [ 0 para Mantener el Actual ]: ");
			int depProv12 = sc.nextInt();
			
			if(depProv12 == 0)
				depProv12 = empObtenido12.getDepartamento().getIdDepartamento();
			
			Departamento d12 = null;
			try { // DA ERROR SI INTRODUZ UN DEPARTAMENTO QUE NO EXISTE  //
				
				d12 = depDAO.obtener(depProv12);
				
				System.out.println("¿Está Seguro que quieres Actualizar los Datos (S/N)? ");
				String respuesta2 = sc.next();
				
				respuesta2 = respuesta2.toUpperCase();
				
				if(respuesta2.trim().equalsIgnoreCase("S")) 
				{
					try {
						
						Session sesion = Conexion.abrirConexion();
								 									 					 					
						empObtenido12.setNombre(intNombre12);
						empObtenido12.setApellidos(intApellidos12);
						empObtenido12.setFechaNacto(dtn12);
						empObtenido12.setDocumiento(doc12);
						empObtenido12.setFuncion(func12);
						empObtenido12.setFechaEntrada(dtContrato12);
						empObtenido12.setEfetivo(efet12);
						empObtenido12.setSalario(sal12);
						empObtenido12.setDepartamento(d12);							
						
						try { // VERIFICA SE EL DNI YA HA SIDO INTRODUZIDO EN OTRO EMPLEADO  //
								if(empDAO.verificaDNI(empObtenido12.getDocumiento()))  // HA SIDO UTILIZADO CreateQuery  //
	 								System.out.println("Ya existe Registro de otro Empleado con el DNI: " + empObtenido12.getDocumiento() + "  Actualización Cancelada");
	 							else {
									sesion.update(empObtenido12);

									System.out.println("\n\tEmpleado Actualizado con Exito\n");

	 							}
								
								sesion.getTransaction().commit();
																
							} catch (ConstraintViolationException e) {
							
							sesion.getTransaction().rollback();
							System.out.println("-- ERRORES ENCUENTRADOS --");
							for (ConstraintViolation cv : e.getConstraintViolations()) {
								System.out.println("Campo: " + cv.getPropertyPath());
								System.out.println("Mensage: " + cv.getMessage());
								
							}
							
						}finally {
							Conexion.desconectar(sesion);
						}
						
						
					} catch (Exception e) {
						System.out.println("No se ha podido Actualizar el Empleado");
					}
				}

			} catch (Exception e) {
				System.out.println("No se ha podido Obtener el Departamento");
			}
	}

	private static void ActualizaCodigoAcceso(int cod1) { // METODO PARA ACTUALIZAR CODIGO DE ACCESO //
		
		// FUNCIONA DE LA MISMA FORMA QUE LOS DEMÁS METODOS DE ACTUALIZACIÓN  //
		
		CodigoAcceso codObtenido3 = null;
		try {
			
			codObtenido3 = cDAO.obtener(cod1);
			
			String nivelCod = codObtenido3.getNivel();
						
			System.out.println("Introduzca el Nuevo Nombre para Encriptar: ");
			String nombreEncriptaProv = tecladoLine.nextLine();
			
			if(nombreEncriptaProv.length() == 0)
				nombreEncriptaProv = Encriptaciones.decrypt(key, iv, codObtenido3.getCodigoAcceso());
				 		 					
			System.out.println("Introduzca La Nueva Descripción del Nivel de Acceso: ");
			nivelCod = tecladoLine.nextLine();
			
			if(nivelCod.length() == 0)
				nivelCod = codObtenido3.getNivel();
	
			System.out.println("¿Está Seguro que quieres Actualizar los Datos (S/N)? ");
			String respuesta2 = sc.next();
			respuesta2 = respuesta2.toUpperCase();
			
			if(respuesta2.trim().equalsIgnoreCase("S")) 
			{
				try {
					 						 					
					Session sesion = Conexion.abrirConexion();
							 					
					codObtenido3.setNivel(nivelCod);
					codObtenido3.setCodigoAcceso(Encriptaciones.encrypt(key, iv, nombreEncriptaProv.trim()));;
					
					try {	
						sesion.update(codObtenido3);
						
						sesion.getTransaction().commit();

						System.out.println("\n\tCódigo de Acceso Actualizado con Exito\n");

						
					} catch (ConstraintViolationException e) {
						
						sesion.getTransaction().rollback();
						System.out.println("-- ERRORES ENCUENTRADOS --");
						for (ConstraintViolation cv : e.getConstraintViolations()) {
							System.out.println("Campo: " + cv.getPropertyPath());
							System.out.println("Mensage: " + cv.getMessage());
							
						}
						
					}finally {
						Conexion.desconectar(sesion);
					}
				
				} catch (Exception e2) {
						
					System.out.println("El nombre no se ha encriptado, el codigo NO ha sido Actualizado");
				}
			}	 					

		} catch (Exception e) {
			System.out.println("No se ha podido Obtener el Código para ser Actualizado");
		}			
		
	}

	private static void listarEmpleado() {
		
		List<Object[]> emps = empDAO.listaEmpleados();  // HA SIDO UTILIZADO CreateQuery PARA SACAR LA LISTA  //
		
		System.out.println("Lista de Empleados");
		System.out.println("══════════════════════════════════════════");
		
		for (Object[] objects : emps) {
			System.out.println("\t"+objects[0] + "  " + objects[1]);
		}
	}
			
	private static void listaCodigosAcceso() {
		List<Object[]> codigos = cDAO.listaCodigosAcceso();  // HA SIDO UTILIZADO CreateQuery PARA SACAR LA LISTA  //
		
		System.out.println("Lista de Códigos de Acceso");
		System.out.println("══════════════════════════════════════════");
		System.out.println("\tCód.\tNIvel de Acceso");
		System.out.println();
		for (Object[] objects : codigos) {
			System.out.println("\t"+objects[0] + "  " + objects[1]);
		}
	}
		
	private static void listaDepartamentos() {
		List<Object[]> departs = depDAO.listaDepartamentos();  // HA SIDO UTILIZADO CreateQuery PARA SACAR LA LISTA  //
		
		System.out.println("Lista de Departamentos");
		System.out.println("══════════════════════════════════════════");
		
		for (Object[] objects : departs) {
			System.out.println("\t"+objects[0] + "\t" + objects[1]);
		}
	}
}

