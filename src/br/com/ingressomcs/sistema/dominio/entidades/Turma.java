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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.ingressomcs.utils.enumeration.StatusEnum;
import br.com.ingressomcs.utils.enumeration.TurnoEnum;

@Entity
@Table(name = "turma")
public class Turma implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@Column(name = "codigo")
	private String codigo;

	@Column(name = "turma")
	private String turma;
	
	@Column(name = "turno")
	@Enumerated(EnumType.ORDINAL)
	private TurnoEnum turno;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Curso curso;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "turma_cadeira", joinColumns = { @JoinColumn(name = "id_turma") }, inverseJoinColumns = { @JoinColumn(name = "id_cadeira") })
	private Set<Cadeira> cadeiras;

	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private StatusEnum status = StatusEnum.ATIVO;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Set<Cadeira> getCadeiras() {
		return cadeiras;
	}

	public void setCadeiras(Set<Cadeira> cadeiras) {
		this.cadeiras = cadeiras;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public TurnoEnum getTurno() {
		return turno;
	}

	public void setTurno(TurnoEnum turno) {
		this.turno = turno;
	}
}
