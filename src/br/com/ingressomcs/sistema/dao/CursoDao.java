package br.com.ingressomcs.sistema.dao;

import br.com.ingressomcs.infra.hibernate.dao.GenericDao;
import br.com.ingressomcs.sistema.dominio.entidades.Curso;

public interface CursoDao extends GenericDao<Curso, Long>{

	public void desativar(Curso curso);
	
}
