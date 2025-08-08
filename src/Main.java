public class Main {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			View.Start.LoginView view = new View.Start.LoginView();
			new Controller.Start.LoginController(view);
			view.setVisible(true);
		});
	}
}