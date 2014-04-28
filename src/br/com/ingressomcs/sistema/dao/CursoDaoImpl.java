package br.com.ingressomcs.sistema.dao;

import java.io.Serializable;

import javax.inject.Named;

import br.com.ingressomcs.infra.hibernate.dao.GenericHibernateDao;
import br.com.ingressomcs.sistema.dominio.entidades.Curso;
import br.com.ingressomcs.utils.enumeration.StatusEnum;

@Named("cursoDao")
public class CursoDaoImpl extends GenericHibernateDao<Curso, Long> implements
		CursoDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public void desativar(Curso curso) {
		curso.setStatus(StatusEnum.DELETADO);
		atualizar(curso);
	}

}
