package es.altair.hibernate.dao;

import es.altair.hibernate.bean.AccesoEmpleados;

public interface AccesoEmpleadoDAO {

	public void save(AccesoEmpleados ae);

	public boolean verificaAccesoExistente(int codEmp, int codAcceso);
}
