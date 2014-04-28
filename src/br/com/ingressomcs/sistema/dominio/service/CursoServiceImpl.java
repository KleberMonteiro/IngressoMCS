package br.com.ingressomcs.sistema.dominio.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.ingressomcs.sistema.dao.CursoDao;
import br.com.ingressomcs.sistema.dominio.entidades.Curso;

@Named("cursoService")
public class CursoServiceImpl implements CursoService, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@Named("cursoDao")
	private CursoDao cursoDao;

	@Override
	public List<Curso> buscarTodos() {
		return cursoDao.buscarTodos();
	}

	@Override
	public List<Curso> buscarAtivos() {
		return cursoDao.buscarAtivos();
	}

	@Override
	public Curso buscarPorId(Long id) {
		return cursoDao.buscarPorId(id);
	}

	@Override
	public void salvar(Curso curso) throws Exception {
		cursoDao.salvar(curso);
	}

	@Override
	public void excluir(Curso curso) {
		cursoDao.desativar(curso);
	}

	@Override
	public void atualizar(Curso curso) {
		cursoDao.atualizar(curso);
	}

}
