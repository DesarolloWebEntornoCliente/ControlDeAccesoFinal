package es.altair.hibernate.dao;

import java.util.List;

import es.altair.hibernate.bean.Empleado;

public interface EmpleadoDAO {

	public void save(Empleado emp);

	public List<Object[]> listaEmpleados();

	public Empleado obtener(int codEmp);

	public void listaEmpleadosConSusAccesos();

	public void listaGeneralDeEmpleados();

	public void borrar(Empleado empObtenido);

}
