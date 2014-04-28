package br.com.ingressomcs.sistema.dao;

import java.io.Serializable;

import javax.inject.Named;

import br.com.ingressomcs.infra.hibernate.dao.GenericHibernateDao;
import br.com.ingressomcs.sistema.dominio.entidades.Professor;
import br.com.ingressomcs.utils.enumeration.StatusEnum;

@Named("professorDao")
public class ProfessorDaoImpl extends GenericHibernateDao<Professor, Long>
		implements ProfessorDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public void desativar(Professor professor) {
		professor.setStatus(StatusEnum.DELETADO);
		atualizar(professor);		
	}

}
