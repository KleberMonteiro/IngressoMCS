package br.com.ingressomcs.sistema.dominio.service;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.ingressomcs.sistema.dao.DisciplinaDao;
import br.com.ingressomcs.sistema.dominio.entidades.Disciplina;
import br.com.ingressomcs.utils.comparator.OrdemAlfabeticaComparator;

@Named("disciplinaService")
public class DisciplinaServiceImpl implements DisciplinaService, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@Named("disciplinaDao")
	private DisciplinaDao disciplinaDao;

	@Override
	public List<Disciplina> buscarTodos() {
		List<Disciplina> disciplinas = disciplinaDao.buscarTodos();
		Collections.sort(disciplinas, new OrdemAlfabeticaComparator());
		return disciplinas;
	}

	@Override
	public List<Disciplina> buscarAtivos() {
		return disciplinaDao.buscarAtivos();
	}

	@Override
	public Disciplina buscarPorId(Long id) {
		return disciplinaDao.buscarPorId(id);
	}

	@Override
	public void salvar(Disciplina disciplina) throws Exception {
		disciplinaDao.salvar(disciplina);
	}

	@Override
	public void excluir(Disciplina disciplina) {
		disciplinaDao.desativar(disciplina);
	}

	@Override
	public void atualizar(Disciplina disciplina) {
		disciplinaDao.atualizar(disciplina);
	}

}
