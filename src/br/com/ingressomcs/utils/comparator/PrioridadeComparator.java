package br.com.ingressomcs.utils.comparator;

import java.util.Comparator;

import br.com.ingressomcs.sistema.dominio.entidades.Professor;

public class PrioridadeComparator implements Comparator<Professor> {

	@Override
	public int compare(Professor professor1, Professor professor2) {
		Integer prioridade1 = professor1.getPrioridade();
		Integer prioridade2 = professor2.getPrioridade();

		return prioridade1.compareTo(prioridade2);
	}

}
