package ru.javarush.rushtest.users;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import ru.javarush.rushtest.users.backend.model.User;
import ru.javarush.rushtest.users.backend.ControlService;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

@Title("Rust Test Users")
@Theme("valo")
public class ViewControl extends UI {

    TextField filter = new TextField();
    Grid userList = new Grid();
    Button newUser = new Button("Add user");
    Button delUser = new Button("Del user");
    Button generateBase = new Button("Generate");
    Button updUser = new Button("Upd user");

    Helper helper = new Helper();
    ControlService service = new ControlService();


    @Override
    protected void init(VaadinRequest request) {
        configureComponents();
        buildLayout();
    }


    private void configureComponents() {

        newUser.addClickListener(e -> helper.edit(new User()));
        updUser.addClickListener(e -> helper.edit((User) userList.getSelectedRow()));
        delUser.addClickListener(e -> helper.delete((User) userList.getSelectedRow()));
        generateBase.addClickListener(e -> helper.generate()) ;

        filter.setInputPrompt("Filter contacts...");
        filter.addTextChangeListener(e -> refreshUsers(e.getText()));

        userList.setContainerDataSource(new BeanItemContainer<>(User.class));
        userList.setColumnOrder("id", "name", "age", "admin","createDate");
        userList.setSelectionMode(Grid.SelectionMode.SINGLE);
        refreshUsers();
    }

    private void buildLayout() {
        HorizontalLayout actions = new HorizontalLayout(filter, delUser, updUser, newUser, generateBase);
        actions.setWidth("100%");
        filter.setWidth("300px");
        actions.setExpandRatio(filter, 4);

        VerticalLayout left = new VerticalLayout(actions, userList);
        left.setSizeFull();
        userList.setWidth("100%");
        userList.setHeight("100%");
        left.setExpandRatio(userList, 1);

        HorizontalLayout mainLayout = new HorizontalLayout(left, helper);
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(left, 1);

        setContent(mainLayout);
    }

    void refreshUsers() {
        refreshUsers(filter.getValue());
    }

    private void refreshUsers(String stringFilter) {
        userList.setContainerDataSource(new BeanItemContainer<>(
                User.class, service.findAll(stringFilter)));
        helper.setVisible(false);
    }

    //  бросаем на сервер как сервлет

    @WebServlet(urlPatterns = "/*")
    @VaadinServletConfiguration(ui = ViewControl.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
