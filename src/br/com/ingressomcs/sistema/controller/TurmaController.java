package br.com.ingressomcs.sistema.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import br.com.ingressomcs.sistema.dominio.entidades.Cadeira;
import br.com.ingressomcs.sistema.dominio.entidades.Curso;
import br.com.ingressomcs.sistema.dominio.entidades.Dia;
import br.com.ingressomcs.sistema.dominio.entidades.Disciplina;
import br.com.ingressomcs.sistema.dominio.entidades.Grade;
import br.com.ingressomcs.sistema.dominio.entidades.Periodo;
import br.com.ingressomcs.sistema.dominio.entidades.Professor;
import br.com.ingressomcs.sistema.dominio.entidades.Turma;
import br.com.ingressomcs.sistema.dominio.entidades.Turno;
import br.com.ingressomcs.sistema.dominio.service.CursoService;
import br.com.ingressomcs.sistema.dominio.service.ProfessorService;
import br.com.ingressomcs.sistema.dominio.service.TurmaService;
import br.com.ingressomcs.utils.comparator.PrioridadeComparator;
import br.com.ingressomcs.utils.datamodel.TurmasDataModel;
import br.com.ingressomcs.utils.enumeration.DiaEnum;
import br.com.ingressomcs.utils.enumeration.DisponibilidadeEnum;
import br.com.ingressomcs.utils.enumeration.TurnoEnum;

@Named
@Scope("session")
public class TurmaController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(TurmaController.class);

	@Inject
	@Named("turmaService")
	private TurmaService turmaService;

	@Inject
	@Named("cursoService")
	private CursoService cursoService;

	@Inject
	@Named("professorService")
	private ProfessorService professorService;

	private Turma turma;
	private List<Turma> turmas;
	private Turma[] turmasSelecionadas;
	private TurmasDataModel turmasDataModel;

	private Curso curso;
	private Curso cursoSelecionado;
	private List<Curso> cursos;

	private Periodo periodo;
	private String periodoSelecionado;
	private List<Periodo> periodos;
	private List<String> periodosStr;

	private List<String> turnos;
	private String turnoSelecionado;
	private TurnoEnum turno;

	private List<DiaEnum> dias;

	private List<Disciplina> disciplinas;
	private List<Cadeira> cadeiras;
	private List<Professor> professores;
	private List<Professor> professoresRelacionados;

	private Map<DiaEnum, Disciplina> diaDisciplinaMap;
	private Map<DiaEnum, Professor> diaProfessorMap;
	private Map<DiaEnum, List<Professor>> diaProfessoresMap;

	@SuppressWarnings("unused")
	private String atualizarPagina;

	Map<Disciplina, List<Professor>> professoresPorDisciplinaMap;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		try {

			carregarPagina();
			periodosStr = new ArrayList<String>();

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	public void novoRegistro() {
		try {
			
			Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap();
			sessionMap.remove("turmaController");

//			curso = null;
//			periodo = null;
//			turnoSelecionado = "";
//			carregarPagina();
//			carregarPeriodos();
//			carregarDisciplinas();

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	public void carregarPagina() {
		try {

			turmas = turmaService.buscarAtivos();
			turmasSelecionadas = new Turma[turmas.size()];
			turmasDataModel = new TurmasDataModel(turmas);

			carregarCursos();
			carregarTurnos();
			carregarDias();
			carregarDisciplinas();

			professores = professorService.buscarAtivos();
			carregarProfessores();

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void carregarCursos() {
		try {

			if (cursoSelecionado == null)
				cursoSelecionado = new Curso();

			cursos = cursoService.buscarAtivos();

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void carregarTurnos() {
		try {

			turnos = new ArrayList<String>();
			turnos.add(TurnoEnum.MANHA.getLabel());
			turnos.add(TurnoEnum.TARDE.getLabel());
			turnos.add(TurnoEnum.NOITE.getLabel());

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void carregarDias() {
		try {

			if (diaDisciplinaMap == null)
				diaDisciplinaMap = new HashMap<DiaEnum, Disciplina>();

			dias = new ArrayList<DiaEnum>();
			for (DiaEnum dia : DiaEnum.values()) {
				dias.add(dia);

				if (diaDisciplinaMap.size() < 7)
					diaDisciplinaMap.put(dia, new Disciplina());
			}

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	public void carregarPeriodos() {
		try {

			carregarCursoSelecionado();

			if (curso != null) {
				Grade grade = null;
				Set<Grade> grades = curso.getGrades();

				for (Grade gradeAtual : grades) {
					if (gradeAtual.isAtual())
						grade = gradeAtual;
				}

				periodos = new ArrayList<Periodo>(grade.getPeriodos());
				carregarPeriodosStr(periodos);
			} else {
				periodos = new ArrayList<Periodo>();
				carregarPeriodosStr(periodos);
			}

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void carregarCursoSelecionado() {
		try {

			boolean selecionado = false;

			for (Curso curso : cursos) {
				if (curso.getId() == cursoSelecionado.getId()) {
					selecionado = true;
					this.curso = curso;
				}
			}
			if (!selecionado)
				this.curso = null;

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	public void carregarPeriodosStr(List<Periodo> periodos) {
		try {

			periodosStr.clear();

			if (periodos.size() > 0) {
				for (int i = 1; i <= periodos.size(); i++) {
					String periodoStr = String.valueOf(i);
					periodosStr.add(periodoStr);
				}
			}

			if (!periodoSelecionado.isEmpty()) {
				carregarDisciplinas();
			}

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	public void carregarTurnoSelecionado() {
		try {

			boolean vazio = true;

			for (TurnoEnum turno : TurnoEnum.values()) {
				if (turno.getLabel().equalsIgnoreCase(turnoSelecionado)) {
					this.turno = turno;
					vazio = false;
				}
			}

			if (vazio)
				this.turno = null;

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	public void carregarDisciplinas() {
		try {

			if (periodos != null)
				carregarPeriodoSelecionado();

			if (periodo != null)
				disciplinas = new ArrayList<Disciplina>(
						periodo.getDisciplinas());
			else
				disciplinas = new ArrayList<Disciplina>();

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void carregarPeriodoSelecionado() {
		try {

			boolean vazio = true;

			for (Periodo periodoSelecionado : periodos) {
				if (periodoSelecionado.getPeriodoStr().equals(
						this.periodoSelecionado)) {
					periodo = periodoSelecionado;
					vazio = false;
				}
			}
			if (vazio)
				periodo = null;

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	public void carregarProfessores() {
		try {

			if (professoresRelacionados == null)
				professoresRelacionados = new ArrayList<Professor>();

			for (Professor professor : professores) {
				for (Disciplina disciplina : disciplinas) {
					for (Disciplina disciplinaProfessor : professor
							.getDisciplinas()) {
						if (disciplina.getId() == disciplinaProfessor.getId()) {
							boolean contem = false;

							for (Professor professorRelacionado : professoresRelacionados) {
								if (professorRelacionado.getId() == professor
										.getId())
									contem = true;
							}

							if (!contem)
								professoresRelacionados.add(professor);
						}
					}
				}
			}

			carregarDiaProfessoresMap();

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void carregarDiaProfessoresMap() {
		try {

			if (diaProfessorMap == null)
				diaProfessorMap = new HashMap<DiaEnum, Professor>();

			if (diaProfessoresMap == null)
				diaProfessoresMap = new HashMap<DiaEnum, List<Professor>>();

			for (DiaEnum dia : DiaEnum.values()) {
				List<Professor> professoresDisponiveis = new ArrayList<Professor>();

				if (diaProfessorMap.size() < 7)
					diaProfessorMap.put(dia, new Professor());

				if (this.turno != null) {

					for (Professor professor : professoresRelacionados) {
						Turno turno = capturarTurno(professor, dia, this.turno);

						if (turno.getDisponibilidade() == DisponibilidadeEnum.DISPONIVEL
								&& !professoresDisponiveis.contains(professor)) {
							professoresDisponiveis.add(professor);
						}
					}

				}

				diaProfessoresMap.put(dia, professoresDisponiveis);
			}

			filtrarProfessoresDisponiveis();

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
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

	private void filtrarProfessoresDisponiveis() {

		for (Entry<DiaEnum, Disciplina> entry : diaDisciplinaMap.entrySet()) {
			DiaEnum dia = entry.getKey();
			Disciplina disciplinaSelecionada = entry.getValue();

			List<Professor> professores = diaProfessoresMap.get(dia);
			List<Professor> professoresTemp = new ArrayList<Professor>();

			for (Professor professor : professores) {
				for (Disciplina disciplinaProfessor : professor
						.getDisciplinas()) {
					if (disciplinaProfessor.getId() != disciplinaSelecionada
							.getId()) {
						professoresTemp.add(professor);
						break;
					}
				}
			}

			professores.removeAll(professoresTemp);
		}
	}

	public void excluir() {
		try {

			logger.debug("Excluindo...");

			int qtdeTurmasSelecionadas = turmasSelecionadas.length;

			if (qtdeTurmasSelecionadas > 0) {
				for (int i = 0; i < qtdeTurmasSelecionadas; i++) {
					Turma turma = turmasSelecionadas[i];
					turmaService.excluir(turma);

					logger.debug("Turma excluída com sucesso!");
				}

				if (qtdeTurmasSelecionadas == 1) {
					FacesContext
							.getCurrentInstance()
							.addMessage(
									null,
									new FacesMessage(
											"Turma removida com sucesso!"));
				} else if (qtdeTurmasSelecionadas > 1) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(
									"Turmas removidas com sucesso!"));
				}

				carregarPagina();
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Por favor selecione ao menos uma turma!",
								""));
			}

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	public void salvar() {
		try {

			turma = new Turma();
			turma.setCurso(curso);
			turma.setCadeiras(criarCadeiras());
			turma.setTurno(this.turno);
			
			turmaService.salvar(turma);
			
			FacesContext
			.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(
							"Turma salva com sucesso!"));
			
			novoRegistro();

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private Set<Cadeira> criarCadeiras() {

		validarProfessores();
		validarDisciplinas();

		Set<Cadeira> cadeiras = new HashSet<Cadeira>();

		for (DiaEnum dia : DiaEnum.values()) {
			Professor professor = diaProfessorMap.get(dia);
			Disciplina disciplina = diaDisciplinaMap.get(dia);

			if (professor.getId() > 0) {
				Turno turno = capturarTurno(professor, dia, this.turno);
				turno.setDisponibilidade(DisponibilidadeEnum.OCUPADO);

				Cadeira cadeira = new Cadeira();
				cadeira.setDia(dia);
				cadeira.setProfessor(professor);
				cadeira.setDisciplina(disciplina);

				cadeiras.add(cadeira);

			}
		}

		return cadeiras;
	}

	private void validarProfessores() {
		try {

			for (Professor professor : professores) {
				for (Professor professorSelecionado : diaProfessorMap.values()) {
					if (professorSelecionado.getId() == professor.getId()) {
						professorSelecionado.setNome(professor.getNome());
						professorSelecionado.setQuadroDeHorarios(professor
								.getQuadroDeHorarios());
						professorSelecionado.setDisciplinas(professor
								.getDisciplinas());
						professorSelecionado.setMatricula(professor
								.getMatricula());
					}
				}
			}

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void validarDisciplinas() {
		try {

			for (Disciplina disciplina : disciplinas) {
				for (Disciplina disciplinaSelecionada : diaDisciplinaMap
						.values()) {
					if (disciplinaSelecionada.getId() == disciplina.getId())
						disciplinaSelecionada.setNome(disciplina.getNome());
				}
			}

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	public void alocarProfessores() {
		try {

			if (curso != null) {
				if (!periodoSelecionado.isEmpty()) {
					if (!turnoSelecionado.isEmpty()) {

						carregarDisciplinas();
						carregarProfessores();
						agruparProfessoresPorDisciplinas();
						carregarCadeiras();
						atribuirDias();

						turma = new Turma();
						turma.setTurma("MA");
						turma.setCurso(curso);
						turma.setCadeiras(new HashSet<Cadeira>(cadeiras));

						turmaService.salvar(turma);

						for (Cadeira cadeira : cadeiras) {
							professorService.atualizar(cadeira.getProfessor());
						}

					} else {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_WARN,
										"Favor selecionar o turno!", ""));
					}
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Favor selecionar o periodo!", ""));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Favor selecionar o curso!", ""));
			}

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void agruparProfessoresPorDisciplinas() {
		try {

			professoresPorDisciplinaMap = new HashMap<Disciplina, List<Professor>>();

			for (Disciplina disciplina : disciplinas) {
				List<Professor> professores = new ArrayList<Professor>();

				for (Professor professor : this.professores) {
					for (Disciplina disciplinaProfessor : professor
							.getDisciplinas()) {
						if (disciplina.getId() == disciplinaProfessor.getId())
							professores.add(professor);
					}
				}

				professoresPorDisciplinaMap.put(disciplina, professores);
			}

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void carregarCadeiras() {
		try {

			Cadeira cadeira = null;
			cadeiras = new ArrayList<Cadeira>();
			List<Professor> professoresAlocados = new ArrayList<Professor>();

			for (Entry<Disciplina, List<Professor>> entry : professoresPorDisciplinaMap
					.entrySet()) {
				Disciplina disciplina = entry.getKey();
				List<Professor> professores = entry.getValue();

				if (professores.size() == 0) {
					cadeira = new Cadeira();
					cadeira.setDisciplina(disciplina);
					cadeiras.add(cadeira);
				} else if (professores.size() == 1) {
					cadeira = new Cadeira();
					cadeira.setDisciplina(disciplina);
					cadeira.setProfessor(professores.get(0));
					cadeiras.add(cadeira);

					if (!professoresAlocados.contains(professores.get(0)))
						professoresAlocados.add(professores.get(0));

				} else {
					for (Professor professor : professores) {
						List<Dia> dias = professor.getQuadroDeHorarios()
								.getSemana().getListaDias();
						int prioridade = 7;

						for (Dia dia : dias) {
							for (Turno turno : dia.getTurnos()) {
								if (turno.getTurno() == this.turno) {
									if (turno.getDisponibilidade() == DisponibilidadeEnum.DISPONIVEL)
										prioridade--;
								}
							}
						}

						professor.setPrioridade(prioridade);
					}

					Collections.sort(professores, new PrioridadeComparator());

					cadeira = new Cadeira();
					cadeira.setDisciplina(disciplina);

					for (Professor professor : professores) {
						if (!professoresAlocados.contains(professor)) {
							cadeira.setProfessor(professor);
							professoresAlocados.add(professor);
							break;
						}
					}

					cadeiras.add(cadeira);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void atribuirDias() {
		try {

			List<DiaEnum> diasSelecionados = new ArrayList<DiaEnum>();

			for (Cadeira cadeira : cadeiras) {
				Professor professor = cadeira.getProfessor();

				if (professor != null) {
					for (DiaEnum dia : DiaEnum.values()) {
						if (dia.ordinal() == cadeiras.size()) {
							break;
						}

						Turno turnoProfessor = capturarTurno(professor, dia,
								turno);

						if (!diasSelecionados.contains(dia)
								&& turnoProfessor.getDisponibilidade() == DisponibilidadeEnum.DISPONIVEL) {
							cadeira.setDia(dia);
							diasSelecionados.add(dia);
							turnoProfessor
									.setDisponibilidade(DisponibilidadeEnum.OCUPADO);
							break;
						}
					}
				}
			}

			for (Cadeira cadeira : cadeiras) {
				Professor professor = cadeira.getProfessor();

				if (professor == null && cadeira.getDia() == null) {
					FacesContext
							.getCurrentInstance()
							.addMessage(
									null,
									new FacesMessage(
											FacesMessage.SEVERITY_WARN,
											"Não foi possivel alocar todos os professores!",
											""));

					break;
				}
			}

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Curso getCursoSelecionado() {
		return cursoSelecionado;
	}

	public void setCursoSelecionado(Curso cursoSelecionado) {
		this.cursoSelecionado = cursoSelecionado;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public List<String> getPeriodosStr() {
		return periodosStr;
	}

	public void setPeriodosStr(List<String> periodosStr) {
		this.periodosStr = periodosStr;
	}

	public String getPeriodoSelecionado() {
		return periodoSelecionado;
	}

	public void setPeriodoSelecionado(String periodoSelecionado) {
		this.periodoSelecionado = periodoSelecionado;
	}

	public List<String> getTurnos() {
		return turnos;
	}

	public void setTurnos(List<String> turnos) {
		this.turnos = turnos;
	}

	public String getTurnoSelecionado() {
		return turnoSelecionado;
	}

	public void setTurnoSelecionado(String turnoSelecionado) {
		this.turnoSelecionado = turnoSelecionado;
	}

	public List<Cadeira> getCadeiras() {
		return cadeiras;
	}

	public void setCadeiras(List<Cadeira> cadeiras) {
		this.cadeiras = cadeiras;
	}

	public List<Professor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	public Map<Disciplina, List<Professor>> getProfessoresPorDisciplinaMap() {
		return professoresPorDisciplinaMap;
	}

	public void setProfessoresPorDisciplinaMap(
			Map<Disciplina, List<Professor>> professoresPorDisciplinaMap) {
		this.professoresPorDisciplinaMap = professoresPorDisciplinaMap;
	}

	public String getAtualizarPagina() {
		carregarPagina();
		return "";
	}

	public void setAtualizarPagina(String atualizarPagina) {
		this.atualizarPagina = atualizarPagina;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<DiaEnum> getDias() {
		return dias;
	}

	public void setDias(List<DiaEnum> dias) {
		this.dias = dias;
	}

	public Map<DiaEnum, Disciplina> getDiaDisciplinaMap() {
		return diaDisciplinaMap;
	}

	public void setDiaDisciplinaMap(Map<DiaEnum, Disciplina> diaDisciplinaMap) {
		this.diaDisciplinaMap = diaDisciplinaMap;
	}

	public Map<DiaEnum, Professor> getDiaProfessorMap() {
		return diaProfessorMap;
	}

	public void setDiaProfessorMap(Map<DiaEnum, Professor> diaProfessorMap) {
		this.diaProfessorMap = diaProfessorMap;
	}

	public Map<DiaEnum, List<Professor>> getDiaProfessoresMap() {
		return diaProfessoresMap;
	}

	public void setDiaProfessoresMap(
			Map<DiaEnum, List<Professor>> diaProfessoresMap) {
		this.diaProfessoresMap = diaProfessoresMap;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public Turma[] getTurmasSelecionadas() {
		return turmasSelecionadas;
	}

	public void setTurmasSelecionadas(Turma[] turmasSelecionadas) {
		this.turmasSelecionadas = turmasSelecionadas;
	}

	public TurmasDataModel getTurmasDataModel() {
		return turmasDataModel;
	}

	public void setTurmasDataModel(TurmasDataModel turmasDataModel) {
		this.turmasDataModel = turmasDataModel;
	}

}
