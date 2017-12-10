package es.altair.hibernate.utilitarios;

import java.awt.Color;

public class Menus {
	
	public static void menu() {
		// Menu Principal  //
		System.out.println();
		System.out.println("╔════════════════════════════════╗");
		System.out.println("║         MENÚ PRINCIPAL         ║");
		System.out.println("╠════════════════════════════════╣");
		System.out.println("║ 1) Codigos de Acceso           ║");		
		System.out.println("║                                ║");
		System.out.println("║ 2) Añadir Departamento         ║");		
		System.out.println("║                                ║");
		System.out.println("║ 3) Añadir Empleado             ║");		
		System.out.println("║                                ║");
		System.out.println("║ 4) Actualizar Departamento     ║");		
		System.out.println("║                                ║");
		System.out.println("║ 5) Actualizar Empleado         ║");		
		System.out.println("║                                ║");
		System.out.println("║ 6) Borrar Departamento         ║");
		System.out.println("║                                ║");
		System.out.println("║ 7) Borrar Empleado             ║");
		System.out.println("║                                ║");
		System.out.println("║ 8) Reports                     ║");	
		System.out.println("║________________________________║");
		System.out.println("║             0) Salir           ║");
		System.out.println("╚════════════════════════════════╝");
	}
	
	public static void menuCodigoDeAcceso() {
		// Menu de Codigos de Acceso //
		System.out.println();
		System.out.println("╔══════════════════════════════════╗");
		System.out.println("║    MENÚ DE CÓDIGOS DE ACCESO     ║");
		System.out.println("╠══════════════════════════════════╣");
		System.out.println("║ 1) Añadir Código de Acceso       ║");		
		System.out.println("║                                  ║");
		System.out.println("║ 2) Actualizar Código de Acceso   ║");		
		System.out.println("║                                  ║");
		System.out.println("║ 3) Borrar Código de Acceso       ║");		
		System.out.println("║                                  ║");
		System.out.println("║ 4) Asignar Acceso a Empleado     ║");		
		System.out.println("║__________________________________║");
		System.out.println("║            0) Retorna            ║");
		System.out.println("╚══════════════════════════════════╝");
	}
	
	public static void menuReports() {
		// Menu de Relatorios //
		System.out.println();		
		System.out.println("╔══════════════════════════════════════════╗");
		System.out.println("║            MENÚ DE RELATORIOS            ║");
		System.out.println("╠══════════════════════════════════════════╣");
		System.out.println("║ 1) Lista General de Empleados            ║");		
		System.out.println("║                                          ║");
		System.out.println("║ 2) Empleados por Departamentos           ║");		
		System.out.println("║                                          ║");
		System.out.println("║ 3) Lista General de Departamentos        ║");		
		System.out.println("║                                          ║");
		System.out.println("║ 4) Empleados con Accesos que Posee       ║");		
		System.out.println("║                                          ║");		
		System.out.println("║ 5) Listar Códigos Desincriptados         ║");		
		System.out.println("║                                          ║");		
		System.out.println("║ 6) Lista Empleados Efetivos/Contratados  ║");	
		System.out.println("║__________________________________________║");
		System.out.println("║                0) Retorna                ║");
		System.out.println("╚══════════════════════════════════════════╝");
	}
 
}
