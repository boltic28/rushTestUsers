package ru.javarush.rushtest.users;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import ru.javarush.rushtest.users.backend.model.User;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

public class Helper extends FormLayout {

    TextField name = new TextField("Name");
    TextField age = new TextField("Age");
    DateField createDate = new DateField("Create date");
    TextField admin = new TextField("IsAdmin \"yes\" or \"no\"");
    Button save = new Button("Save", this::save);
    Button cancel = new Button("Cancel", this::cancel);

    User user;

    BeanFieldGroup<User> formFieldBindings;

    public Helper() {
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setVisible(false);
    }

    private void buildLayout() {
        setSizeUndefined();
        setMargin(true);

        HorizontalLayout actions = new HorizontalLayout(save, cancel);
        actions.setSpacing(true);

		addComponents(name, age, createDate, admin, actions );
    }

    public void save(Button.ClickEvent event) {
        try {
            formFieldBindings.commit();

            getUI().service.save(user);

            String msg = String.format("Saved '%s'.",
                    user.getName());
            Notification.show(msg,Type.TRAY_NOTIFICATION);
            getUI().refreshUsers();
        } catch (FieldGroup.CommitException e) {
            Notification.show("error of updating",Type.TRAY_NOTIFICATION);
        }
    }

    public void cancel(Button.ClickEvent event) {
        Notification.show("Cancelled", Type.TRAY_NOTIFICATION);
        getUI().userList.select(null);
        setVisible(false);
    }

    void edit(User user) {
        this.user = user;
        if(user != null) {
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(user, this);
            name.focus();
        }else {
            Notification.show("Choose the user, please", Notification.Type.TRAY_NOTIFICATION);
            getUI().userList.select(null);
        }
        setVisible(user != null);
    }

    void delete(User user) {
        if(user != null ) {
            getUI().service.delete(user);
            getUI().refreshUsers();
            Notification.show("User was deleted.", Notification.Type.TRAY_NOTIFICATION);
            getUI().userList.select(null);
        } else {
            Notification.show("Choose the user, please", Notification.Type.TRAY_NOTIFICATION);
            getUI().userList.select(null);
        }
        setVisible(false);
    }

    public void generate() {
        getUI().service.generate();
        getUI().refreshUsers();
    }

    @Override
    public ViewControl getUI() {
        return (ViewControl) super.getUI();
    }

}
