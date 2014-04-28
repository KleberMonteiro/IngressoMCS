package br.com.ingressomcs.sistema.dominio.service;

import java.util.List;

import br.com.ingressomcs.sistema.dominio.entidades.Disciplina;

public interface DisciplinaService {

	public List<Disciplina> buscarTodos();

	public List<Disciplina> buscarAtivos();

	public Disciplina buscarPorId(Long id);

	public void salvar(Disciplina entity) throws Exception;

	public void excluir(Disciplina entity);

	public void atualizar(Disciplina entity);
	
}
