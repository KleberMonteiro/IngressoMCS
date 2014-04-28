package br.com.ingressomcs.sistema.dominio.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.ingressomcs.sistema.dao.TurmaDao;
import br.com.ingressomcs.sistema.dominio.entidades.Cadeira;
import br.com.ingressomcs.sistema.dominio.entidades.Dia;
import br.com.ingressomcs.sistema.dominio.entidades.Professor;
import br.com.ingressomcs.sistema.dominio.entidades.Turma;
import br.com.ingressomcs.sistema.dominio.entidades.Turno;
import br.com.ingressomcs.utils.enumeration.DiaEnum;
import br.com.ingressomcs.utils.enumeration.DisponibilidadeEnum;
import br.com.ingressomcs.utils.enumeration.TurnoEnum;

@Named("turmaService")
public class TurmaServiceImpl implements TurmaService, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	@Named("turmaDao")
	private TurmaDao turmaDao;

	@Override
	public List<Turma> buscarTodos() {
		return turmaDao.buscarTodos();
	}

	@Override
	public List<Turma> buscarAtivos() {
		return turmaDao.buscarAtivos();
	}

	@Override
	public Turma buscarPorId(Long id) {
		return turmaDao.buscarPorId(id);
	}

	@Override
	public void salvar(Turma turma) throws Exception {
		turmaDao.salvar(turma);
	}

	@Override
	public void excluir(Turma turma) {
		TurnoEnum turnoEnum = turma.getTurno();
		
		for(Cadeira cadeira : turma.getCadeiras()){
			Professor professor = cadeira.getProfessor();
			DiaEnum diaEnum = cadeira.getDia();			
			Turno turno = capturarTurno(professor, diaEnum, turnoEnum);
			turno.setDisponibilidade(DisponibilidadeEnum.DISPONIVEL);
		}
		
		turmaDao.desativar(turma);		
	}

	@Override
	public void atualizar(Turma turma) {
		turmaDao.atualizar(turma);
	}
	
	private Turno capturarTurno(Professor professor, DiaEnum diaEnum,
			TurnoEnum turnoEnum) {
		Turno turno = new Turno();

		List<Dia> dias = professor.getQuadroDeHorarios().getSemana()
				.getListaDias();
		for (Dia dia : dias) {
			if (diaEnum == dia.getDia()) {
				for (Turno turno2 : dia.getListaTurnos()) {
					if (turno2.getTurno() == turnoEnum) {
						turno = turno2;
						break;
					}
				}
				break;
			}
		}

		return turno;
	}

}
