package br.com.ingressomcs.sistema.dominio.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.ingressomcs.sistema.dao.ProfessorDao;
import br.com.ingressomcs.sistema.dominio.entidades.Professor;

@Named("professorService")
public class ProfessorServiceImpl implements ProfessorService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	@Named("professorDao")
	private ProfessorDao professorDao;

	@Override
	public List<Professor> buscarTodos() {
		return professorDao.buscarTodos();
	}

	@Override
	public List<Professor> buscarAtivos() {
		return professorDao.buscarAtivos();
	}

	@Override
	public Professor buscarPorId(Long id) {
		return professorDao.buscarPorId(id);
	}

	@Override
	public void salvar(Professor professor) throws Exception {
		professorDao.salvar(professor);		
	}

	@Override
	public void excluir(Professor professor) {
		professorDao.desativar(professor);		
	}

	@Override
	public void atualizar(Professor professor) {
		professorDao.atualizar(professor);
	}	

}
