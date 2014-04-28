package br.com.ingressomcs.utils.datamodel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.ingressomcs.sistema.dominio.entidades.Professor;

public class ProfessoresDataModel extends ListDataModel<Professor> implements
		SelectableDataModel<Professor>, Serializable {
	
	private static final long serialVersionUID = 1L;

	public ProfessoresDataModel() {
	}

	public ProfessoresDataModel(List<Professor> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Professor getRowData(String rowKey) {

		List<Professor> professores = (List<Professor>) getWrappedData();

		for (Professor professor : professores) {
			if (professor.getId() == (Long.parseLong(rowKey)))
				return professor;
		}

		return null;
	}

	@Override
	public Object getRowKey(Professor professor) {
		return professor.getId();
	}

}
