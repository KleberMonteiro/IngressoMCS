package br.com.ingressomcs.sistema.dominio.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.Transient;

@Entity
@Table(name = "semana")
public class Semana implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "semana_dia", joinColumns = { @JoinColumn(name = "id_semana") }, inverseJoinColumns = { @JoinColumn(name = "id_dia") })
	private Set<Dia> dias;

	@SuppressWarnings("unused")
	@Transient
	private List<Dia> listaDias;

	// Gets e Sets
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<Dia> getDias() {
		return dias;
	}

	public void setDias(Set<Dia> dias) {
		this.dias = dias;
	}

	public List<Dia> getListaDias() {
		return new ArrayList<Dia>(dias);
	}

	public void setListaDias(List<Dia> listaDias) {
		this.listaDias = listaDias;
	}

}
