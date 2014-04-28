package br.com.ingressomcs.sistema.dao;

import java.io.Serializable;

import javax.inject.Named;

import br.com.ingressomcs.infra.hibernate.dao.GenericHibernateDao;
import br.com.ingressomcs.sistema.dominio.entidades.Turma;
import br.com.ingressomcs.utils.enumeration.StatusEnum;

@Named("turmaDao")
public class TurmaDaoImpl extends GenericHibernateDao<Turma, Long> implements
		TurmaDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public void desativar(Turma turma) {
		turma.setStatus(StatusEnum.DELETADO);
		atualizar(turma);
	}

}
