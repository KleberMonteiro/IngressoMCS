package br.com.ingressomcs.sistema.dao;

import javax.inject.Named;

import br.com.ingressomcs.infra.hibernate.dao.GenericHibernateDao;
import br.com.ingressomcs.sistema.dominio.entidades.Permissao;

@Named("permissaoDao")
public class PermissaoDaoImpl extends GenericHibernateDao<Permissao, Long>
		implements PermissaoDao {

}
