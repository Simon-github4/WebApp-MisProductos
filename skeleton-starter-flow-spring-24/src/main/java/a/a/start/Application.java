package a.a.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
//import org.springframework.beans.factory.annotation;
/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@NpmPackage(value = "lumo-css-framework", version = "4.0.10")
@NpmPackage(value = "line-awesome", version = "1.3.0")
@PWA(name = "Project Base for Vaadin with Spring", shortName = "Project Base")
@Theme("my-theme")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan({"a.a.start", "data.service"})
public class Application implements AppShellConfigurator {

    private static final long serialVersionUID = -6011341677665662749L;

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
