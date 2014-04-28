package br.com.ingressomcs.sistema.dominio.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.ingressomcs.utils.enumeration.DisponibilidadeEnum;
import br.com.ingressomcs.utils.enumeration.TurnoEnum;

@Entity
@Table(name = "turno")
public class Turno implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private long id;
	
	@Column(name = "turno")
	@Enumerated(EnumType.ORDINAL)
	private TurnoEnum turno;
	
	@Column(name = "disponivel")
	private DisponibilidadeEnum disponibilidade;

	// Gets e Sets
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TurnoEnum getTurno() {
		return turno;
	}

	public void setTurno(TurnoEnum turno) {
		this.turno = turno;
	}

	public DisponibilidadeEnum getDisponibilidade() {
		return disponibilidade;
	}

	public void setDisponibilidade(DisponibilidadeEnum disponibilidade) {
		this.disponibilidade = disponibilidade;
	}

}
