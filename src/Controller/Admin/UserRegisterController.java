package Controller.Admin;

import View.Admin.UserRegisterView;
import Model.Entities.User;
import Model.Services.UserService;
import Model.Services.RegisterService;

import javax.swing.*;

public class UserRegisterController {
    private UserRegisterView view;
    private UserService userService;

    public UserRegisterController(UserRegisterView view) {
        this.view = view;
        this.userService = new UserService();

        view.addUserButton.addActionListener(_ -> processUserRegistration());
    }

    private void processUserRegistration() {
        String firstName = view.firstNameField.getText().trim();
        String lastName = view.lastNameField.getText().trim();
        String userId = view.userIdField.getText().trim();
        String password = new String(view.passwordField.getPassword());
        String email = view.emailField.getText().trim();
        int type = view.userTypeComboBox.getSelectedIndex();

        if (firstName.isEmpty() || lastName.isEmpty() || userId.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Todos los campos son obligatorios.");
            return;
        }

        boolean valid = UserService.validateRegistrationFields(view, firstName, lastName, email, userId);
        if (!valid) {
            return;
        }

        User user = new User(userId, password, type, email, firstName, lastName);
        boolean success = userService.addUserToDatabase(user);

        if (success) {
            RegisterService.deleteRegistrationRequestByUserId(userId);
            JOptionPane.showMessageDialog(view, "Usuario agregado correctamente.");
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Error al agregar usuario.");
        }
    }
}