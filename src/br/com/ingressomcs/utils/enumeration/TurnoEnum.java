package br.com.ingressomcs.utils.enumeration;

import java.io.Serializable;

public enum TurnoEnum implements Serializable {

	MANHA("Manhã"), TARDE("Tarde"), NOITE("Noite");

	private final String label;

	private TurnoEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

}
