package br.com.ingressomcs.sistema.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import br.com.ingressomcs.infra.hibernate.dao.GenericHibernateDao;
import br.com.ingressomcs.sistema.dominio.entidades.Usuario;
import br.com.ingressomcs.utils.enumeration.StatusEnum;

@Named("usuarioDao")
public class UsuarioDaoImpl extends GenericHibernateDao<Usuario, Long>
		implements UsuarioDao, Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public Usuario buscarPorEmail(String email) {
		List<Usuario> usuarios = (List<Usuario>) getHibernateTemplate()
				.findByCriteria(
						DetachedCriteria
								.forClass(Usuario.class)
								.add(Restrictions.eq("email", email))
								.add(Restrictions
										.eq("status", StatusEnum.ATIVO)));

		return usuarios.get(0);
	}

	@Override
	public void desativar(Usuario usuario) {
		usuario.setStatus(StatusEnum.DELETADO);
		atualizar(usuario);
	}

}
