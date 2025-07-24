package Controller.Common;

import javax.swing.*;

import Model.Services.UserService;
import View.Start.LoginView;

public class LoginController {
	private LoginView loginView;
	private UserService service;


	public LoginController(LoginView loginView, UserService service) {
		this.loginView = loginView;
		this.service = service;

		if (loginView != null) {
			loginView.loginActionButton.addActionListener(_ -> validateUserCredentials());
			loginView.registerActionLabel.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					openRegistrationView();
				}
			});
		}
	}


	private void validateUserCredentials() {
		String userId = loginView.userIdField.getText();
		String password = new String(loginView.passwordField.getPassword());

				if (userId.isEmpty() && password.isEmpty()) {
					System.err.println("[LoginController] Login attempt failed: One or more fields are empty.");
			JOptionPane.showMessageDialog(loginView, "Por favor, complete todos los campos");
					return;
				}

				if (password.isEmpty()) {
					System.err.println("[LoginController] Login attempt failed: Password field is empty.");
			JOptionPane.showMessageDialog(loginView, "Por favor, coloque su contraseña");
					return;
				}

				if (userId.isEmpty()) {
					System.err.println("[LoginController] Login attempt failed: UserID field is empty.");
			JOptionPane.showMessageDialog(loginView, "Por favor, coloque su Cédula de Identidad");
					return;
				}

		short authenticated = service.authenticate(userId, password);

				if (authenticated == 1) {
					System.out.println("[LoginController] User login successful. UserID: '" + userId + "' (Role: User)");
			generateUserCache(userId);
			loginView.dispose();
					View.User.UserView userView = new View.User.UserView(userId);
					new Controller.User.UserController(userView, userId);
					userView.setVisible(true);

				} else if (authenticated == 2) {
					System.out.println("[LoginController] Admin login successful. UserID: '" + userId + "' (Role: Admin)");
			loginView.dispose();
					View.Admin.AdminView adminView = new View.Admin.AdminView();
					new Controller.Admin.AdminController(adminView);
					adminView.setVisible(true);
				
				} else {
					System.err.println("[LoginController] Login failed: Incorrect ID or password.");
			JOptionPane.showMessageDialog(loginView, "Cédula o contraseña incorrectos");
				}
			}


	private void openRegistrationView() {
				System.out.println("[LoginController] Opening registration view for new user.");
		loginView.dispose();
				View.Start.RegisterView registerView = new View.Start.RegisterView();
				new RegisterController(registerView);
				registerView.setVisible(true);
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