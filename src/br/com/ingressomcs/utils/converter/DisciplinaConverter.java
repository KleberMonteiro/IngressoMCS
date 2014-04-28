package br.com.ingressomcs.utils.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import br.com.ingressomcs.sistema.dominio.entidades.Disciplina;

@FacesConverter(value="disciplinaConverter", forClass=Disciplina.class)
public class DisciplinaConverter implements Converter{

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		 Object ret = null;
		    if (component instanceof PickList) {
		        Object dualList = ((PickList) component).getValue();
		        DualListModel dl = (DualListModel) dualList;
		        for (Object o : dl.getSource()) {
		            String id = "" + ((Disciplina) o).getId();
		            if (value.equals(id)) {
		                ret = o;
		                break;
		            }
		        }
		        if (ret == null)
		            for (Object o : dl.getTarget()) {
		                String id = "" + ((Disciplina) o).getId();
		                if (value.equals(id)) {
		                    ret = o;
		                    break;
		                }
		            }
		    }
		    return ret;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		String str = "";
	    if (object instanceof Disciplina) {
	        str = "" + ((Disciplina) object).getId();
	    }
	    return str;
	}

}
