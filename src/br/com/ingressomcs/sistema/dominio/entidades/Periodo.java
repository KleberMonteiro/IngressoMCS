package br.com.ingressomcs.sistema.dominio.entidades;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "periodo")
public class Periodo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@Column(name = "periodo_str")
	private String periodoStr;

	@ManyToMany(targetEntity = Disciplina.class, cascade = {
		CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "periodo_disciplinas", joinColumns = { @JoinColumn(name = "id_periodo") }, inverseJoinColumns = { @JoinColumn(name = "id_disciplina") })
	private Set<Disciplina> disciplinas;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPeriodoStr() {
		return periodoStr;
	}

	public void setPeriodoStr(String periodoStr) {
		this.periodoStr = periodoStr;
	}

	public Set<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(Set<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

}
