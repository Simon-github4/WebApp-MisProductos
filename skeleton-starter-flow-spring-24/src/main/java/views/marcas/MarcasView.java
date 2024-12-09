package views.marcas;

import org.springframework.beans.factory.annotation.Autowired;

import com.demoprogb.objects.entities.Marca;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import a.org.vaadin.example.MainLayout;
import data.service.ClienteRestProductos;
import jakarta.annotation.PostConstruct;

//@CssImport("./styles/marcas-view.css") // Import your CSS file
//@JsModule("./scripts/marcas-view.js") // Import your JavaScript file
//@JsModule("./styles/shared-styles.js")
//@CssImport("./styles/views/helloworld/hello-world-view.css")
@PageTitle("Marcas")
@Route(value = "Marcas", layout = MainLayout.class)
public class MarcasView extends VerticalLayout {
    Grid<Marca> grid = new Grid<>(Marca.class);
    TextField filterText = new TextField();
    MarcaForm form;
    @Autowired
    private ClienteRestProductos service;

    public MarcasView() {

    	addClassName("list-view");  
        setSizeFull();
        configureGrid();
        configureForm();
        add(getToolbar(), getContent());
        closeEditor();
        //MainLayout.from(getElement(), .get)
    }
    
    @PostConstruct
    private void init() {
        updateList();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new MarcaForm();
        form.setWidth("25em");
        form.addListener(MarcaForm.SaveEvent.class, this::saveMarca); 
        form.addListener(MarcaForm.DeleteEvent.class, this::deleteMarca); 
        form.addListener(MarcaForm.CloseEvent.class, e -> closeEditor()); 
    }
    
    private void saveMarca(MarcaForm.SaveEvent event) {
        service.saveMarca(event.getMarca());
        updateList();
        closeEditor();
    }

    private void deleteMarca(MarcaForm.DeleteEvent event) {
    	try {
    		service.deleteMarca(event.getMarca());
    		updateList();
    		closeEditor();
    	}
    	catch (Exception e) {
    		Notification.show("No se puede eliminar este item: Hay productos que son de esta Marca.", 4000, Position.MIDDLE);
    	}
    }

    private void configureGrid() {
        grid.addClassNames("Marca-grid");
        grid.setSizeFull();
        grid.setColumns("nombre", "mail");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
            editMarca(event.getValue(), true)); 
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filtrar por nombre...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        Button addMarcaButton = new Button("Nueva Marca");
        addMarcaButton.addClickListener(click -> addMarca()); 
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addMarcaButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editMarca(Marca Marca, boolean showButtonDelete) { 
        if (Marca == null) {
            closeEditor();
        } else {
            form.setMarca(Marca);
            form.setVisible(true);
            form.showButtonDelete(showButtonDelete);
            addClassName("editing");
        }
    }
    private void addMarca() { 
        grid.asSingleSelect().clear();
        editMarca(new Marca(), false);
    }
    private void closeEditor() {
        form.setMarca(null);
        form.setVisible(false);
        removeClassName("editing");
    }    
    private void updateList() {
     grid.setItems(service.getMarcas(filterText.getValue()));
    }
}

