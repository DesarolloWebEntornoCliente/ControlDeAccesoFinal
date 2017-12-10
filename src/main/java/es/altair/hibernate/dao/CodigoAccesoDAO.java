package es.altair.hibernate.dao;

import java.util.List;

import es.altair.hibernate.bean.CodigoAcceso;

public interface CodigoAccesoDAO {

	public void save(CodigoAcceso cod);

	public List<Object[]> listaCodigosAcceso();

	public void listaCodigosDesincriptados();

	public CodigoAcceso obtener(int codCod);

	public void borrar(CodigoAcceso codObtenido);

}
