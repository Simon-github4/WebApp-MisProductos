package a.a.start;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.NavigationEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import views.marcas.MarcasView;

@PageTitle("Login")
@Route("")
public class Login extends Div{
	
LoginForm form;
	
	public Login() {
		getElement().getThemeList().add("dark");

		LoginOverlay loginOverlay = new LoginOverlay();
		loginOverlay.setTitle("Mis Productos");
		loginOverlay.setDescription("Bienvenido a la WebApp Mis Productos");
		loginOverlay.setAction("productos");
		
		Label l = new Label("Aun no tienes cuenta?");
		l.getStyle().setPaddingTop("15px");
		l.getStyle().setFontSize("14px").setFontWeight(400);
		loginOverlay.getCustomFormArea().add(l);
		
		Button sign = new Button("Sign Up");
        sign.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        sign.addClickListener(e -> sign.getUI().ifPresent(ui -> ui.navigate("productos")));
        loginOverlay.getCustomFormArea().add(sign);
        //RouterLink rt = new RouterLink("marcas", MarcasView.class);
        
        loginOverlay.setForgotPasswordButtonVisible(false);
		add(loginOverlay);
		loginOverlay.setOpened(true);
		//Button login = new Button("Log in");
		//login.addClickListener(event -> loginOverlay.setOpened(true));
		//login.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
	    //add(login);
	       
		/*
		form.getStyle().set("background-color", "var(--lumo-contrast-0pct)")
        .set("display", "flex").set("justify-content", "center")
        .set("align-items", "center")
        .set("padding", "var(--lumo-space-l)");		

		form.getElement().setAttribute("no-autofocus", "");
		 */

		
	}

}
