package views.marcas;

import com.demoprogb.objects.entities.Marca;
//import com.example.application.objects.Marca;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

	  public class MarcaForm extends FormLayout { 
		
	  private static final long serialVersionUID = -5517587856685756258L;
	  
	  private Marca marca;
	  Binder<Marca> binder = new BeanValidationBinder<>(Marca.class); 

	  TextField nombre = new TextField("Nombre"); 
	  TextField mail = new TextField("Mail");
	  Button save = new Button("Ok");
	  Button delete = new Button("Borrar");
	  Button close = new Button("Cancelar");

	  public MarcaForm() {
		    addClassName("marca-form"); 
		    binder.bindInstanceFields(this);
		    add(nombre, 
		    	 mail,
		        createButtonsLayout());
	  }
	  
	  public Marca getMarca() {
		return marca;
	  }

	  public void setMarca(Marca marca) {
		this.marca = marca;
     		binder.readBean(marca); 
	  }
	  
	private Component createButtonsLayout() {
		  save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		  delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		  close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		  save.addClickShortcut(Key.ENTER);
		  close.addClickShortcut(Key.ESCAPE);

		  save.addClickListener(event -> validateAndSave()); 
		  delete.addClickListener(event -> fireEvent(new DeleteEvent(this, marca))); 
		  close.addClickListener(event -> fireEvent(new CloseEvent(this))); 

		  binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); 
		  return new HorizontalLayout(save, delete, close);
	}

	private void validateAndSave() {
		  try {
			if (marca != null) {
				binder.writeBean(marca); 
				fireEvent(new SaveEvent(this, marca));
			}
		  } catch (ValidationException e) {
		    e.printStackTrace();
		  }
	}
	
	public static abstract class MarcaFormEvent extends ComponentEvent<MarcaForm> {
		  private Marca marca;

		  protected MarcaFormEvent(MarcaForm source, Marca marca) { 
		    super(source, false);
		    this.marca = marca;
		  }

		  public Marca getMarca() {
		    return marca;
		  }
	}

		public static class SaveEvent extends MarcaFormEvent {
		  SaveEvent(MarcaForm source, Marca marca) {
		    super(source, marca);
		  }
		}
		public static class DeleteEvent extends MarcaFormEvent {
		  DeleteEvent(MarcaForm source, Marca marca) {
		    super(source, marca);
		  }
		}
		public static class CloseEvent extends MarcaFormEvent {
		  CloseEvent(MarcaForm source) {
		    super(source, null);
		  }
		}
		public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
		    ComponentEventListener<T> listener) { 
		  return getEventBus().addListener(eventType, listener);
		}
		public void showButtonDelete(boolean showButtonDelete) {
			delete.setVisible(showButtonDelete);	
		}

}
