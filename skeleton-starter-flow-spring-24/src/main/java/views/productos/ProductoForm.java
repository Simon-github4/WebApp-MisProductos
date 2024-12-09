package views.productos;

import java.util.List;
import com.demoprogb.objects.entities.Marca;
import com.demoprogb.objects.entities.Producto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToBigDecimalConverter;
import com.vaadin.flow.shared.Registration;

	public class ProductoForm extends FormLayout { 

	private static final long serialVersionUID = 5983305505981719637L;
	private Producto producto;
	  Binder<Producto> binder = new BeanValidationBinder<>(Producto.class); 

	  ComboBox<Marca> marca = new ComboBox<Marca>("Marca");
 	  TextField descripcion = new TextField("Descripcion"); 
	  Button save = new Button("Ok");
	  Button delete = new Button("Borrar");
	  Button close = new Button("Cancelar");

	  public ProductoForm() {
		    addClassName("producto-form"); 
		    
	        
		    
		    binder.bindInstanceFields(this);
		    add(descripcion, 
		    	marca,
		        createButtonsLayout());
	  }
	  
	  public void setMarcas(List<Marca> marcas) {
		    marca.setItems(marcas);
		    marca.setItemLabelGenerator(Marca::getNombre);
	  }
	  
	  public Producto getProducto() {
		return producto;
	  }

	  public void setProducto(Producto producto) {
		this.producto = producto;
        binder.readBean(producto); 
	  }
	  
	private Component createButtonsLayout() {
		  save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		  delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
		  close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

		  save.addClickShortcut(Key.ENTER);
		  close.addClickShortcut(Key.ESCAPE);

		  save.addClickListener(event -> validateAndSave()); 
		  delete.addClickListener(event -> fireEvent(new DeleteEvent(this, producto))); 
		  close.addClickListener(event -> fireEvent(new CloseEvent(this))); 

		  binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); 
		  return new HorizontalLayout(save, delete, close);
	}

	private void validateAndSave() {
		  try {
			if (producto != null) {
				binder.writeBean(producto); 
				fireEvent(new SaveEvent(this, producto)); 
			}
		  } catch (ValidationException e) {
		    e.printStackTrace();
		  }
	}
	
	public static abstract class ProductoFormEvent extends ComponentEvent<ProductoForm> {
		  private Producto producto;

		  protected ProductoFormEvent(ProductoForm source, Producto producto) { 
		    super(source, false);
		    this.producto = producto;
		  }

		  public Producto getProducto() {
		    return producto;
		  }
		}

		public static class SaveEvent extends ProductoFormEvent {
		  SaveEvent(ProductoForm source, Producto producto) {
		    super(source, producto);
		  }
		}

		public static class DeleteEvent extends ProductoFormEvent {
		  DeleteEvent(ProductoForm source, Producto producto) {
		    super(source, producto);
		  }

		}

		public static class CloseEvent extends ProductoFormEvent {
		  CloseEvent(ProductoForm source) {
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

