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
    }
}
