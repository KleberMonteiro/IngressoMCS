package br.com.ingressomcs.sistema.dominio.service;

import java.util.List;

import br.com.ingressomcs.sistema.dominio.entidades.Professor;

public interface ProfessorService {

	public List<Professor> buscarTodos();
	
	public List<Professor> buscarAtivos();

	public Professor buscarPorId(Long id);

	public void salvar(Professor entity) throws Exception;

	public void excluir(Professor entity);

	public void atualizar(Professor entity);

}
