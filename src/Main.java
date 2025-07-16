public class Main {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			View.Common.LoginView view = new View.Common.LoginView();
			Model.Services.UserService service = new Model.Services.UserService();
			Controller.Common.LoginController controller = new Controller.Common.LoginController(view, service);
			view.setVisible(true);
		});
	}
}
