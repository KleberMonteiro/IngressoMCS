package br.com.ingressomcs.sistema.dominio.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.ingressomcs.utils.enumeration.DiaEnum;

@Entity
@Table(name = "dia")
public class Dia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@Column(name = "dia")
	@Enumerated(EnumType.ORDINAL)
	private DiaEnum dia;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "dia_turnos", joinColumns = { @JoinColumn(name = "id_dia") }, inverseJoinColumns = { @JoinColumn(name = "id_turno") })
	private Set<Turno> turnos;

	@SuppressWarnings("unused")
	@Transient
	private List<Turno> listaTurnos;

	// Gets e Sets
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

	public Set<Turno> getTurnos() {
		return turnos;
	}

	public void setTurnos(Set<Turno> turnos) {
		this.turnos = turnos;
	}

	public List<Turno> getListaTurnos() {
		return new ArrayList<Turno>(turnos);
	}

	public void setListaTurnos(List<Turno> listaTurnos) {
		this.listaTurnos = listaTurnos;
	}

}
