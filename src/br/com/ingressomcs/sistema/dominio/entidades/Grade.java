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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "grade")
public class Grade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@Column(name = "atual")
	private boolean atual;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "grade_periodo", joinColumns = { @JoinColumn(name = "id_grade") }, inverseJoinColumns = { @JoinColumn(name = "id_periodo") })
	private Set<Periodo> periodos;

	// Gets e Sets
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isAtual() {
		return atual;
	}

	public void setAtual(boolean atual) {
		this.atual = atual;
	}

	public Set<Periodo> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(Set<Periodo> periodos) {
		this.periodos = periodos;
	}

}
