package br.com.ingressomcs.utils.datamodel;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import br.com.ingressomcs.sistema.dominio.entidades.Turma;

public class TurmasDataModel extends ListDataModel<Turma> implements
		SelectableDataModel<Turma>, Serializable {

	private static final long serialVersionUID = 1L;

	public TurmasDataModel() {
	}

	public TurmasDataModel(List<Turma> data) {
		super(data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Turma getRowData(String rowKey) {

		List<Turma> turmas = (List<Turma>) getWrappedData();

		for (Turma turma : turmas) {
			if (turma.getId() == (Long.parseLong(rowKey)))
				return turma;
		}

		return null;
	}

	@Override
	public Object getRowKey(Turma turma) {
		return turma.getId();
	}

}
