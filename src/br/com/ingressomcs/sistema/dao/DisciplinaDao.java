package br.com.ingressomcs.sistema.dao;

import br.com.ingressomcs.infra.hibernate.dao.GenericDao;
import br.com.ingressomcs.sistema.dominio.entidades.Disciplina;

public interface DisciplinaDao extends GenericDao<Disciplina, Long>{

	public void desativar(Disciplina disciplina);
	
}
