package br.com.ingressomcs.sistema.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import br.com.ingressomcs.sistema.dominio.entidades.Disciplina;
import br.com.ingressomcs.sistema.dominio.service.DisciplinaService;
import br.com.ingressomcs.utils.datamodel.DisciplinasDataModel;

@Named
@Scope("session")
public class DisciplinaController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(DisciplinaController.class);

	@Inject
	@Named("disciplinaService")
	private DisciplinaService disciplinaService;

	private Disciplina disciplina;
	private List<Disciplina> disciplinas;
	private Disciplina[] disciplinasSelecionadas;
	private DisciplinasDataModel disciplinasDataModel;
	
	@SuppressWarnings("unused")
	private String atualizarPagina;

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		carregarPagina();
	}

	public void carregarPagina() {
		try {
			
			if(disciplina == null)
				disciplina = new Disciplina();
			
			carregarDisciplinas();

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	private void carregarDisciplinas() {
		disciplinas = disciplinaService.buscarAtivos();
		disciplinasDataModel = new DisciplinasDataModel(disciplinas);
		disciplinasSelecionadas = new Disciplina[disciplinas.size()];
	}

	public void novoRegistro() {
		disciplina = new Disciplina();
	}

	public void salvar() {
		try {

			if (!disciplina.getNome().isEmpty()) {
				logger.debug("Salvando...");

				disciplinaService.salvar(disciplina);

				carregarDisciplinas();

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Disciplina salva com sucesso!"));

				logger.debug("Registro salvo/atualizado com sucesso...");
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Favor informar o nome da disciplina!", ""));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	public void excluir() {
		try {

			logger.debug("Excluindo...");

			int qtdeDisciplinasSelecionadas = disciplinasSelecionadas.length;

			if (qtdeDisciplinasSelecionadas > 0) {
				for (int i = 0; i < qtdeDisciplinasSelecionadas; i++) {
					Disciplina disciplina = disciplinasSelecionadas[i];
					disciplinaService.excluir(disciplina);

					if (this.disciplina.getId() == disciplina.getId())
						novoRegistro();

					logger.debug("Disciplina: '" + disciplina.getNome()
							+ "' excluída com sucesso!");
				}

				if (qtdeDisciplinasSelecionadas == 1) {
					FacesContext
							.getCurrentInstance()
							.addMessage(
									null,
									new FacesMessage(
											"Disciplina removida com sucesso!"));
				} else if (qtdeDisciplinasSelecionadas > 1) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(
									"Disciplinas removidas com sucesso!"));
				}

				carregarDisciplinas();
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"Por favor selecione ao menos uma disciplina!",
								""));
			}

		} catch (Throwable e) {
			logger.error(e.getClass() + ":" + e.getMessage());
			e.printStackTrace();
		}
	}

	public void editar(ActionEvent actionEvent) {
		try {

			logger.debug("Capturando informações da disciplina...");

			disciplina = (Disciplina) actionEvent.getComponent()
					.getAttributes().get("disciplina");

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(e.getClass() + ":" + e.getMessage());
		}
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public Disciplina[] getDisciplinasSelecionadas() {
		return disciplinasSelecionadas;
	}

	public void setDisciplinasSelecionadas(Disciplina[] disciplinasSelecionadas) {
		this.disciplinasSelecionadas = disciplinasSelecionadas;
	}

	public DisciplinasDataModel getDisciplinasDataModel() {
		return disciplinasDataModel;
	}

	public String getAtualizarPagina() {
		carregarPagina();
		return "";
	}

	public void setAtualizarPagina(String atualizarPagina) {
		this.atualizarPagina = atualizarPagina;
	}

}
