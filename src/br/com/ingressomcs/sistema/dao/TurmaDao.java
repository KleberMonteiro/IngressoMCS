package br.com.ingressomcs.sistema.dao;

import br.com.ingressomcs.infra.hibernate.dao.GenericDao;
import br.com.ingressomcs.sistema.dominio.entidades.Turma;

public interface TurmaDao extends GenericDao<Turma, Long> {

	public void desativar(Turma turma);

}
