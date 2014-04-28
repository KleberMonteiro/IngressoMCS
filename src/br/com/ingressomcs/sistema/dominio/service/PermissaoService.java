package br.com.ingressomcs.sistema.dominio.service;

import java.util.List;

import br.com.ingressomcs.sistema.dominio.entidades.Permissao;

public interface PermissaoService {

	public List<Permissao> buscarTodos();

	public List<Permissao> buscarAtivos();

	public Permissao buscarPorId(Long id);

	public void salvar(Permissao permissao);

	public void excluir(Permissao permissao);

	public void atualizar(Permissao permissao);

}
