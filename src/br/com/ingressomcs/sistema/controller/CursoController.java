package br.com.ingressomcs.sistema.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import br.com.ingressomcs.sistema.dominio.entidades.Curso;
import br.com.ingressomcs.sistema.dominio.entidades.Disciplina;
import br.com.ingressomcs.sistema.dominio.entidades.Grade;
import br.com.ingressomcs.sistema.dominio.entidades.Periodo;
import br.com.ingressomcs.sistema.dominio.service.CursoService;
import br.com.ingressomcs.sistema.dominio.service.DisciplinaService;
import br.com.ingressomcs.utils.datamodel.CursosDataModel;

@Named
@Scope("session")
public class CursoController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(CursoController.class);

	@Inject
	@Named("cursoService")
	private CursoService cursoService;

	@Inject
	@Named("disciplinaService")
	private DisciplinaService disciplinaService;

	private Curso curso;
	private Grade grade;
	private Set<Grade> grades;

	private List<Curso> cursos;
	private List<Disciplina> disciplinas;
	private List<String> dias;
	private List<String> periodosStr;
	private Map<String, String> periodosMap;
	private Map<String, Map<String, String>> gradeMap;

	private CursosDataModel cursosDataModel;
	private Curso[] cursosSelecionados;

	private static final int QTD_PERIODOS = 10;
	private static final int QTD_DISCIPLINAS_PERIODO = 7;

	@SuppressWarnings("unused")
	private String atualizarPagina;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		try {

			carregarPagina();

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void carregarPagina() {
		try {

			if (curso == null)
				curso = new Curso();

			carregarCursos();
			carregarDisciplinas();
			gerarDias();
			gerarPeriodos();
			gerarCurso();

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void gerarDias() {
		try {

			dias = new ArrayList<String>();
			for (int dia = 1; dia <= QTD_DISCIPLINAS_PERIODO; dia++) {
				dias.add(String.valueOf(dia));
			}

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void gerarPeriodos() {
		try {

			periodosStr = new ArrayList<String>();
			periodosMap = new HashMap<String, String>();

			for (int i = 1; i <= QTD_PERIODOS; i++) {
				String periodoStr = String.valueOf(i);
				periodosStr.add(periodoStr);
				periodosMap.put(periodoStr, periodoStr + "º Período");
			}

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void gerarCurso() {
		try {

			if (curso.getId() != 0) {
				curso = cursoService.buscarPorId(curso.getId());

				if (curso != null) {
					grades = curso.getGrades();

					if (grades.size() > 0) {
						for (Iterator<Grade> iterator = grades.iterator(); iterator
								.hasNext();) {
							Grade grade = iterator.next();

							if (grade.isAtual()) {
								this.grade = grade;
								break;
							}
						}

						montarGrade();
					}
				} else {
					novoRegistro();
				}
			} else {
				novoRegistro();
			}

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void carregarCursos() {
		try {

			logger.debug("Carregando cursos...");

			cursos = cursoService.buscarAtivos();
			cursosSelecionados = new Curso[cursos.size()];
			cursosDataModel = new CursosDataModel(cursos);

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void carregarDisciplinas() {
		try {

			logger.debug("Carregando disciplinas...");

			disciplinas = disciplinaService.buscarAtivos();

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void montarGrade() {
		try {

			gradeMap = new HashMap<String, Map<String, String>>();
			Set<Periodo> periodos = grade.getPeriodos();

			for (String periodoStr1 : periodosStr) {
				boolean periodoAdicionado = false;

				for (Periodo periodo : periodos) {
					String periodoStr2 = periodo.getPeriodoStr();

					if (periodoStr1.equalsIgnoreCase(periodoStr2)) {
						Map<String, String> disciplinasMap = new HashMap<String, String>();
						List<Disciplina> listaDisciplinas = new ArrayList<Disciplina>(
								periodo.getDisciplinas());

						for (int index = 0; index < QTD_DISCIPLINAS_PERIODO; index++) {
							String indexStr = String.valueOf(index + 1);

							try {
								Disciplina disciplina = listaDisciplinas
										.get(index);
								String idDisciplina = String.valueOf(disciplina
										.getId());
								disciplinasMap.put(indexStr, idDisciplina);
							} catch (IndexOutOfBoundsException e) {
								disciplinasMap.put(indexStr, "");
							}
						}

						gradeMap.put(periodoStr1, disciplinasMap);
						periodoAdicionado = true;
					}
				}

				if (!periodoAdicionado) {
					gradeMap.put(periodoStr1, gerarDisciplinasMap());
				}
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void novoRegistro() {
		logger.debug("Criando novo curso...");

		curso = new Curso();
		grade = new Grade();
		grades = new HashSet<Grade>();
		gerarGrade();
	}

	private void gerarGrade() {
		gradeMap = new HashMap<String, Map<String, String>>();

		for (int periodo = 1; periodo <= QTD_PERIODOS; periodo++) {
			gradeMap.put(String.valueOf(periodo), gerarDisciplinasMap());
		}
	}

	private Map<String, String> gerarDisciplinasMap() {
		Map<String, String> disciplinasMap = new HashMap<String, String>();

		for (int index = 1; index <= QTD_DISCIPLINAS_PERIODO; index++) {
			disciplinasMap.put(String.valueOf(index), "");
		}

		return disciplinasMap;
	}

	private Grade criarGrade() {
		try {

			Grade grade = new Grade();
			Set<Periodo> periodos = new HashSet<Periodo>();
			Set<Disciplina> disciplinas;

			for (Entry<String, Map<String, String>> entry : gradeMap.entrySet()) {
				String periodoStr = entry.getKey();
				Map<String, String> disciplinasMap = entry.getValue();
				Periodo periodo = new Periodo();
				disciplinas = new HashSet<Disciplina>();

				for (Entry<String, String> entry2 : disciplinasMap.entrySet()) {
					String disciplinaIdStr = entry2.getValue();

					if (!disciplinaIdStr.equals("")) {
						long disciplinaId = Long.parseLong(entry2.getValue());

						for (Disciplina disciplina : this.disciplinas) {
							if (disciplinaId == disciplina.getId()) {
								disciplinas.add(disciplina);
							}
						}
					}
				}

				if (!disciplinas.isEmpty()) {
					periodo.setPeriodoStr(periodoStr);
					periodo.setDisciplinas(disciplinas);
					periodos.add(periodo);
				}
			}

			grade.setPeriodos(periodos);
			grade.setAtual(true);

			return grade;

		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	public void salvar() {
		try {

			if (!curso.getNome().isEmpty()) {
				logger.debug("Salvando...");

				if (!grades.isEmpty()) {
					for (Grade grade : grades) {
						if (grade.isAtual()) {
							grade.setAtual(false);
						}
					}
				}

				Grade grade = criarGrade();
				grades.add(grade);
				curso.setGrades(grades);
				cursoService.salvar(curso);

				carregarCursos();

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Registro salvo com sucesso!"));

				logger.debug("Registro salvo/atualizado com sucesso...");
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Favor informar o nome do curso."));
				novoRegistro();
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void excluir() {
		try {

			logger.debug("Excluindo...");

			int qtdeCursosSelecionados = cursosSelecionados.length;

			if (qtdeCursosSelecionados > 0) {
				for (int i = 0; i < qtdeCursosSelecionados; i++) {
					Curso curso = cursosSelecionados[i];
					cursoService.excluir(curso);

					if (this.curso.getId() == curso.getId())
						novoRegistro();

					logger.debug("Curso: '" + curso.getNome()
							+ "' excluído com sucesso!");
				}

				if (qtdeCursosSelecionados == 1) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Curso removido com sucesso!"));
				} else if (qtdeCursosSelecionados > 1) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Cursos removidos com sucesso!"));
				}

				carregarCursos();
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(
								"Por favor selecione ao menos um curso!"));
			}

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	public void editar(ActionEvent actionEvent) {
		try {

			logger.debug("Capturando dados do curso...");

			curso = (Curso) actionEvent.getComponent().getAttributes()
					.get("curso");
			gerarCurso();

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public Map<String, Map<String, String>> getGradeMap() {
		return gradeMap;
	}

	public void setGradeMap(Map<String, Map<String, String>> gradeMap) {
		this.gradeMap = gradeMap;
	}

	public List<String> getPeriodosStr() {
		return periodosStr;
	}

	public void setPeriodosStr(List<String> periodosStr) {
		this.periodosStr = periodosStr;
	}

	public Map<String, String> getPeriodosMap() {
		return periodosMap;
	}

	public void setPeriodosMap(Map<String, String> periodosMap) {
		this.periodosMap = periodosMap;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<String> getDias() {
		return dias;
	}

	public void setDias(List<String> dias) {
		this.dias = dias;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public CursosDataModel getCursosDataModel() {
		return cursosDataModel;
	}

	public void setCursosDataModel(CursosDataModel cursosDataModel) {
		this.cursosDataModel = cursosDataModel;
	}

	public Curso[] getCursosSelecionados() {
		return cursosSelecionados;
	}

	public void setCursosSelecionados(Curso[] cursosSelecionados) {
		this.cursosSelecionados = cursosSelecionados;
	}

	public String getAtualizarPagina() {
		carregarPagina();
		return "";
	}

	public void setAtualizarPagina(String atualizarPagina) {
		this.atualizarPagina = atualizarPagina;
	}
}
