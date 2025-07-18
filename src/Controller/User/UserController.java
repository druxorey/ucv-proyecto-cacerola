package Controller.User;

import View.User.UserView;

public class UserController {
    private UserView view;
    private String userId;

    public UserController(UserView view, String userId) {
        this.view = view;
        this.userId = userId;
        initController();
    }

    private void initController() {
        // Aquí puedes agregar la lógica para manejar eventos de la vista de usuario
        // Por ahora, solo inicializa el controlador
    }
}
