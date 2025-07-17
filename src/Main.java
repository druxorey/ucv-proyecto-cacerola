public class Main {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			View.Start.LoginView view = new View.Start.LoginView();
			Model.Services.UserService service = new Model.Services.UserService();
			Controller.Common.LoginController controller = new Controller.Common.LoginController(view, service);
			view.setVisible(true);
		});
	}
}
