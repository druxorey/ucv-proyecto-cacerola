package Controller.Admin;

import View.Admin.UserRegisterView;
import Model.Entities.User;
import Model.Services.UserService;
import Model.Services.RegisterService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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

		boolean valid = UserService.validateRegistrationFields(view, firstName, lastName, email, userId, password);
		if (!valid) {
			return;
		}
		if (view.selectedPhotoFile == null) {
			JOptionPane.showMessageDialog(view, "Debe seleccionar una foto.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			File facesDir = new File("Model/Data/Faces");
			if (!facesDir.exists()) facesDir.mkdirs();
			File dest = new File(facesDir, userId + ".jpg");
			Files.copy(view.selectedPhotoFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(view, "Error al guardar la foto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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