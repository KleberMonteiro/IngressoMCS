package br.com.ingressomcs.utils.datamodel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.ingressomcs.sistema.dominio.entidades.Curso;

public class CursosDataModel extends ListDataModel<Curso> implements
		SelectableDataModel<Curso>, Serializable {
	
	private static final long serialVersionUID = 1L;

	public CursosDataModel() {
	}

	public CursosDataModel(List<Curso> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Curso getRowData(String rowKey) {

		List<Curso> cursos = (List<Curso>) getWrappedData();

		for (Curso curso : cursos) {
			if (curso.getId() == (Long.parseLong(rowKey)))
				return curso;
		}

		return null;
	}

	@Override
	public Object getRowKey(Curso curso) {
		return curso.getId();
	}

}
