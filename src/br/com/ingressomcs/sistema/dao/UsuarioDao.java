package br.com.ingressomcs.sistema.dao;

import br.com.ingressomcs.infra.hibernate.dao.GenericDao;
import br.com.ingressomcs.sistema.dominio.entidades.Usuario;

public interface UsuarioDao extends GenericDao<Usuario, Long> {
	
	public Usuario buscarPorEmail(String email);
	public void desativar(Usuario usuario);
	
}
