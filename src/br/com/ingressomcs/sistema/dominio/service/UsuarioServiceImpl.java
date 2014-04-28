package br.com.ingressomcs.sistema.dominio.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.util.DigestUtils;

import br.com.ingressomcs.sistema.dao.UsuarioDao;
import br.com.ingressomcs.sistema.dominio.entidades.Usuario;

@Named("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@Inject
	@Named("usuarioDao")
	private UsuarioDao usuarioDao;

	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	@Override
	public List<Usuario> buscarTodos() {
		return usuarioDao.buscarTodos();
	}

	@Override
	public List<Usuario> buscarAtivos() {
		return usuarioDao.buscarAtivos();
	}

	@Override
	public Usuario buscarPorId(Long id) {
		return this.usuarioDao.buscarPorId(id);
	}

	@Override
	public void salvar(Usuario usuario) throws Exception {
		try {
			// Criptografando senha
			usuario.setSenha(DigestUtils.md5DigestAsHex(usuario.getPassword()
					.getBytes()));
			usuarioDao.salvar(usuario);
		} catch (Exception e) {
			usuarioDao.salvar(usuario);
		}

	}

	@Override
	public void excluir(Usuario usuario) {
		usuarioDao.desativar(usuario);
	}

	@Override
	public void atualizar(Usuario usuario) {
		usuarioDao.atualizar(usuario);
	}

	@Override
	public void alterarSenha(Usuario usuario) {
		// Criptografando senha
		usuario.setSenha(DigestUtils.md5DigestAsHex(usuario.getPassword()
				.getBytes()));

		atualizar(usuario);
	}

}
