package br.com.ingressomcs.utils;

import java.text.Normalizer;

public class RemoveAcentos {
	
	public static String remover(String nome) {
		nome = nome.replaceAll(" ","_");  
	    nome = Normalizer.normalize(nome, Normalizer.Form.NFD);  
	    nome = nome.replaceAll("[^\\p{ASCII}]", "");  
	    return nome;
	}
	
}
