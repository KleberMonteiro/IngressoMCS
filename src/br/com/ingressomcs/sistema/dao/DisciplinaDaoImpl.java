package br.com.ingressomcs.sistema.dao;

import java.io.Serializable;

import javax.inject.Named;

import br.com.ingressomcs.infra.hibernate.dao.GenericHibernateDao;
import br.com.ingressomcs.sistema.dominio.entidades.Disciplina;
import br.com.ingressomcs.utils.enumeration.StatusEnum;

@Named("disciplinaDao")
public class DisciplinaDaoImpl extends GenericHibernateDao<Disciplina, Long>
		implements DisciplinaDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public void desativar(Disciplina disciplina) {
		disciplina.setStatus(StatusEnum.DELETADO);
		atualizar(disciplina);
	}

}
