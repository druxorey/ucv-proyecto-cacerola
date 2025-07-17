
package Controller.Admin;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import View.Admin.AdminView;
import Model.Entities.User;
import Model.Services.UserService;

public class AdminController {
    private AdminView view;
    private UserService userService;

    public AdminController(AdminView view) {
        this.view = view;
        this.userService = new UserService();
    }

    public boolean addUser(String userId, String password, int type, String email, String name, String lastName) {
        User user = new User(userId, password, type, email, name, lastName);
        return userService.addUserToDatabase(user);
    }

    public void handleAddUser(JTextField nombreField, JTextField apellidoField, JTextField userIdField, JPasswordField passwordField, JTextField emailField, JComboBox<String> userType) {
        String nombre = nombreField.getText().trim();
        String apellido = apellidoField.getText().trim();
        String userId = userIdField.getText().trim();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText().trim();
        int type = userType.getSelectedIndex() + 1;

        if (nombre.isEmpty() || apellido.isEmpty() || userId.isEmpty() || password.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Todos los campos son obligatorios.");
            return;
        }

        boolean success = addUser(userId, password, type, email, nombre, apellido);
        if (success) {
            JOptionPane.showMessageDialog(view, "Usuario agregado correctamente.");
            nombreField.setText("");
            apellidoField.setText("");
            userIdField.setText("");
            passwordField.setText("");
            emailField.setText("");
            userType.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(view, "Error al agregar usuario.");
        }
    }
}
