package br.com.ingressomcs.sistema.dominio.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.com.ingressomcs.sistema.dao.PermissaoDao;
import br.com.ingressomcs.sistema.dominio.entidades.Permissao;

@Named("permissaoService")
@Transactional
public class PermissaoServiceImpl implements PermissaoService {

	@Inject
	@Named("permissaoDao")
	private PermissaoDao permissaoDao;

	@Override
	public List<Permissao> buscarTodos() {
		return permissaoDao.buscarTodos();
	}

	@Override
	public List<Permissao> buscarAtivos() {
		return permissaoDao.buscarAtivos();
	}

	@Override
	public Permissao buscarPorId(Long id) {
		return permissaoDao.buscarPorId(id);
	}

	@Override
	public void salvar(Permissao permissao) {
		this.permissaoDao.salvar(permissao);
	}

	@Override
	public void excluir(Permissao permissao) {
		this.permissaoDao.excluir(permissao);
	}

	@Override
	public void atualizar(Permissao permissao) {
		this.permissaoDao.atualizar(permissao);

	}

}
