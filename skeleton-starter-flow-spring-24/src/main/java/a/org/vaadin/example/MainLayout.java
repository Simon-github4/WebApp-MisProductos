package a.org.vaadin.example;

import java.util.Optional;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

import views.marcas.MarcasView;
import views.productos.ProductosView;

/**
 * The main view is a top-level placeholder for other views.
 */

	@Route("main")
    public class MainLayout extends AppLayout {
        //private final Tabs menu;
        private H1 viewTitle;
        private HorizontalLayout drawerLayout;
        public MainLayout() {
        	try {

            // Put the menu in the drawer
            //menu = createMenu();

            addToDrawer(createDrawerContent());
            addToNavbar(createHeaderContent());
            
            //setPrimarySection(Section.NAVBAR);
	        
        	} catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error initializing MainLayout", e);
	        }

        } 
    
        private Component createHeaderContent() {
            drawerLayout = new HorizontalLayout();

            // Configure styling for the header
            drawerLayout.setId("header");
            drawerLayout.getThemeList().set("dark", true);
            drawerLayout.setWidthFull();
            drawerLayout.setSpacing(false);
            drawerLayout.setAlignItems(FlexComponent.Alignment.CENTER);

            // Have the drawer toggle button on the left
            drawerLayout.add(new DrawerToggle());

            // Placeholder for the title of the current view.  The title will be set after navigation.
            viewTitle = new H1();
            viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
            drawerLayout.add(viewTitle);

            return drawerLayout;
        }
        
        private Component createDrawerContent() {
            VerticalLayout layout = new VerticalLayout();

          // Configure styling for the drawer
            layout.setSizeFull();
            layout.setPadding(false);
            layout.setSpacing(false);
            layout.getThemeList().set("spacing-s", true);
            layout.setAlignItems(FlexComponent.Alignment.STRETCH);

            /*VerticalLayout logoLayout = new VerticalLayout();
            //logoLayout.setId("logo");
            //logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);  
            H2 title = new H2("Mis Productos"); 
            Header h = new Header(title);
            h.setWidthFull();
            h.addClassNames(LumoUtility.FontSize.MEDIUM, LumoUtility.Margin.XSMALL);
            //logoLayout.add(h);*/
            
            SideNav nav = new SideNav();

            SideNavItem dashboardLink = new SideNavItem("Productos",
                    ProductosView.class, VaadinIcon.DASHBOARD.create());
            SideNavItem calendarLink = new SideNavItem("marcas",
                    MarcasView.class, VaadinIcon.CAR.create());
            SideNavItem vaadinLink = new SideNavItem("Vaadin website",
                    "https://vaadin.com", VaadinIcon.VAADIN_H.create());

            nav.addItem(dashboardLink, calendarLink,vaadinLink);
            
            Scroller sc = new Scroller(nav);
            sc.setClassName(LumoUtility.Padding.SMALL);

            layout.add(sc);
            
            return layout;
        }
	      
        @Override
        protected void afterNavigation() {
            super.afterNavigation();
            //getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
            viewTitle.setText(getCurrentPageTitle());
        }

        private String getCurrentPageTitle() {
            PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
            return title == null ? "" : title.value();
        }
      
        /* Metodos de Menu, estan al pedo
        private Tabs createMenu() {
            final Tabs tabs = new Tabs();
            tabs.setOrientation(Tabs.Orientation.VERTICAL);
            tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
            tabs.setId("tabs");
            tabs.add(createMenuItems());
            return tabs;
        }

        private Component[] createMenuItems() {
            return new Tab[] { 
                    createTab("Marcas", MarcasView.class),
                    createTab("Productos", ProductosView.class) 
            		};
        }

        private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
            final Tab tab = new Tab();
            tab.add(new RouterLink(text, navigationTarget));
            ComponentUtil.setData(tab, Class.class, navigationTarget);
            return tab;
        }
        
        private Optional<Tab> getTabForComponent(Component component) {
            return menu.getChildren()
                    .filter(tab -> ComponentUtil.getData(tab, Class.class)
                            .equals(component.getClass()))
                    .findFirst().map(Tab.class::cast);
        }
        */

}
