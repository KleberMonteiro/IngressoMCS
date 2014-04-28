package br.com.ingressomcs.infra.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;

public interface GenericDao<T, PK extends Serializable> {

	List<T> buscarTodos() throws DataAccessException;
	
	List<T> buscarAtivos() throws DataAccessException;

	T buscarPorId(PK id) throws DataAccessException;

	void salvar(T entity) throws DataAccessException;

	void excluir(T entity) throws DataAccessException;

	void atualizar(T entity) throws DataAccessException;

}
