package views.productos;

import org.springframework.beans.factory.annotation.Autowired;

import com.demoprogb.objects.entities.Producto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import a.org.vaadin.example.MainLayout;
import data.service.ClienteRestProductos;
import jakarta.annotation.PostConstruct;

@PageTitle("Productos")
@Route(value = "", layout = MainLayout.class)
public class ProductosView extends VerticalLayout {
    Grid<Producto> grid = new Grid<>(Producto.class);
    TextField filterText = new TextField();
    private ProductoForm form;
    @Autowired
    private ClienteRestProductos service;

    public ProductosView() {
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        closeEditor(); 
    }
    
    @PostConstruct
    private void init() {
        updateList();
        form.setMarcas(service.getMarcas(""));
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
        form = new ProductoForm();
        form.setWidth("25em");

        form.addListener(ProductoForm.SaveEvent.class, this::saveProducto); 
        form.addListener(ProductoForm.DeleteEvent.class, this::deleteProducto); 
        form.addListener(ProductoForm.CloseEvent.class, e -> closeEditor()); 
    }
    
    private void saveProducto(ProductoForm.SaveEvent event) {
        service.saveProducto(event.getProducto());
        updateList();
        closeEditor();
    }

    private void deleteProducto(ProductoForm.DeleteEvent event) {
    	try {
    		service.deleteProducto(event.getProducto());
    		updateList();
    		closeEditor();
    	}
    	catch (Exception e) {
    		Notification.show("No se puede eliminar este item", 2500, Position.MIDDLE);
    	}    		
    }

    private void configureGrid() {
        grid.addClassNames("Producto-grid");
        grid.setSizeFull();
        grid.setColumns("descripcion");
        grid.addColumn(producto -> producto.getMarca().getNombre()).setHeader("Marca").setId("marca");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
            editProducto(event.getValue(), true)); 
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filtrar por descripcion...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addButton = new Button("Nuevo Producto");
        addButton.addClickListener(click -> addProducto()); 

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editProducto(Producto producto, boolean showButtonDelete) { 
        if (producto == null) {
            closeEditor();
        } else {
            form.setProducto(producto);
            form.setVisible(true);
            form.showButtonDelete(showButtonDelete);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setProducto(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addProducto() { 
        grid.asSingleSelect().clear();
        editProducto(new Producto(), false);
    }


    private void updateList() {
        grid.setItems(service.getProductos(filterText.getValue()));
    }
}

