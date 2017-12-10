package es.altair.hibernate.dao;

import java.util.List;

import es.altair.hibernate.bean.Departamento;

public interface DepartamentoDAO {
	
	public void save(Departamento dep);

	public List<Object[]> listaDepartamentos();

	public Departamento obtener(int codDep);

	public void listarDeptos(int i);

	public void listaGeneraldeDepartamentos();

	public void borrar(Departamento depObtenido);



}
