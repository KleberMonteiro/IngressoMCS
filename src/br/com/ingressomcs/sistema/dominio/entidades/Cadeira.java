package br.com.ingressomcs.sistema.dominio.entidades;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.ingressomcs.utils.enumeration.DiaEnum;

@Entity
@Table(name = "cadeira")
public class Cadeira implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@Column(name = "dia")
	@Enumerated(EnumType.ORDINAL)
	private DiaEnum dia;

	@OneToOne(cascade = CascadeType.ALL)
	private Professor professor;

	@OneToOne(cascade = CascadeType.ALL)
	private Disciplina disciplina;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DiaEnum getDia() {
		return dia;
	}

	public void setDia(DiaEnum dia) {
		this.dia = dia;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

}
