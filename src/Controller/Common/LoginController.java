package Controller.Common;

import Model.Services.UserService;
import View.Start.LoginView;

import javax.swing.*;
import java.awt.event.*;

public class LoginController {
	private LoginView view;
	private UserService service;

	public LoginController(LoginView view, UserService service) {
		this.view = view;
		this.service = service;
		initController();
	}

	private void initController() {
		view.loginActionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userId = view.userIdField.getText();
				String password = new String(view.passwordField.getPassword());

				if (userId.isEmpty() && password.isEmpty()) {
					System.err.println("[LoginController] Login attempt failed: One or more fields are empty.");
					JOptionPane.showMessageDialog(view, "Por favor, complete todos los campos");
					return;
				}

				if (password.isEmpty()) {
					System.err.println("[LoginController] Login attempt failed: Password field is empty.");
					JOptionPane.showMessageDialog(view, "Por favor, coloque su contraseña");
					return;
				}

				if (userId.isEmpty()) {
					System.err.println("[LoginController] Login attempt failed: UserID field is empty.");
					JOptionPane.showMessageDialog(view, "Por favor, coloque su Cédula de Identidad");
					return;
				}

				int authenticated = service.authenticate(userId, password);

				if (authenticated == 1) {
					System.out.println("[LoginController] User login successful. UserID: '" + userId + "' (Role: User)");
					ensureUserWalletFile(userId);
					view.dispose();
					View.User.UserView userView = new View.User.UserView(userId);
					new Controller.User.UserController(userView, userId);
					userView.setVisible(true);

				} else if (authenticated == 2) {
					System.out.println("[LoginController] Admin login successful. UserID: '" + userId + "' (Role: Admin)");
					view.dispose();
					View.Admin.AdminView adminView = new View.Admin.AdminView();
					new Controller.Admin.AdminController(adminView);
					adminView.setVisible(true);
				
				} else {
					System.err.println("[LoginController] Login failed: Incorrect ID or password.");
					JOptionPane.showMessageDialog(view, "Cédula o contraseña incorrectos");
				}
			}
		});

		view.registerActionLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				System.out.println("[LoginController] Opening registration view for new user.");
				View.Start.RegisterView registerView = new View.Start.RegisterView();
				new RegisterController(registerView);
				registerView.setVisible(true);
			}
		});
	}
	
	private void ensureUserWalletFile(String userId) {
		String walletDirPath = "Model/Data/Wallets";
		String walletFilePath = walletDirPath + "/" + userId + ".json";
		java.io.File walletFile = new java.io.File(walletFilePath);
		try {
			java.io.File walletDir = new java.io.File(walletDirPath);
			if (!walletDir.exists()) {
				walletDir.mkdirs();
			}
			if (!walletFile.exists()) {
				String json = "{\n\"saldo\": 0\n}";
				java.nio.file.Files.write(walletFile.toPath(), json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
				System.out.println("[LoginController] Wallet file created for user: " + userId);
			} else {
				System.out.println("[LoginController] Wallet file already exists for user: " + userId);
			}
		} catch (Exception ex) {
			System.err.println("[LoginController] Error creating wallet file for user: " + userId);
			ex.printStackTrace();
		}
	}
}
