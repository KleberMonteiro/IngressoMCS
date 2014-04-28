package br.com.ingressomcs.sistema.dao;

import br.com.ingressomcs.infra.hibernate.dao.GenericDao;
import br.com.ingressomcs.sistema.dominio.entidades.Professor;

public interface ProfessorDao extends GenericDao<Professor, Long> {

	public void desativar(Professor professor);

}
