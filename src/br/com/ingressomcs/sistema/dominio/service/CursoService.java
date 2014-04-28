package br.com.ingressomcs.sistema.dominio.service;

import java.util.List;

import br.com.ingressomcs.sistema.dominio.entidades.Curso;

public interface CursoService {
	
	public List<Curso> buscarTodos();

	public List<Curso> buscarAtivos();

	public Curso buscarPorId(Long id);

	public void salvar(Curso entity) throws Exception;

	public void excluir(Curso entity);

	public void atualizar(Curso entity);

}
