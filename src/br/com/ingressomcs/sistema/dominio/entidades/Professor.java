package br.com.ingressomcs.sistema.dominio.entidades;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.ingressomcs.utils.enumeration.StatusEnum;

@Entity
@Table(name = "professor")
public class Professor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@Column(name = "nome", nullable = false, columnDefinition = "VARCHAR(60) default ''")
	private String nome = "";

	@Column(name = "matricula", nullable = false, columnDefinition = "VARCHAR(30) default ''")
	private String matricula = "";

	@OneToOne(cascade = CascadeType.ALL)
	private QuadroDeHorarios quadroDeHorarios;

	@ManyToMany(targetEntity = Disciplina.class, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "professor_disciplina", joinColumns = @JoinColumn(name = "id_professor"), inverseJoinColumns = @JoinColumn(name = "id_disciplina"))
	private Set<Disciplina> disciplinas;

	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private StatusEnum status = StatusEnum.ATIVO;

	@Transient
	private int prioridade;

	// Gets e Sets
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Set<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public QuadroDeHorarios getQuadroDeHorarios() {
		return quadroDeHorarios;
	}

	public void setQuadroDeHorarios(QuadroDeHorarios quadroDeHorarios) {
		this.quadroDeHorarios = quadroDeHorarios;
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

}
