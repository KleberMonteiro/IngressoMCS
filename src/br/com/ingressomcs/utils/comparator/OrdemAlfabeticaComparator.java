package br.com.ingressomcs.utils.comparator;

import java.util.Comparator;

import br.com.ingressomcs.sistema.dominio.entidades.Disciplina;
import br.com.ingressomcs.utils.RemoveAcentos;

public class OrdemAlfabeticaComparator implements Comparator<Disciplina>{

	@Override
	public int compare(Disciplina disciplina1, Disciplina disciplina2) {
		String nome1 = RemoveAcentos.remover(disciplina1.getNome());
		String nome2 = RemoveAcentos.remover(disciplina2.getNome());		
		
		return nome1.compareTo(nome2);
	}

}
