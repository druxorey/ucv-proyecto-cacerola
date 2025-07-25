package View.Common;

import javax.swing.*;
import java.awt.*;
import Model.Services.UserService;
import View.Admin.UserRegisterView;
import Controller.Admin.UserRegisterController;

public class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private boolean isPushed;
    private String firstName, lastName, userId, email;

    public ButtonEditor(JCheckBox checkBox, JFrame parent) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(_ -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        button.setText((value == null) ? "" : value.toString());
        isPushed = true;
        userId = (String) table.getValueAt(row, 0);
        email = (String) table.getValueAt(row, 1);
        UserService userService = new UserService();
        for (UserService.IncomingUserRequest req : userService.getIncomingUserRequests()) {
            if (req.userId.equals(userId) && req.email.equals(email)) {
                firstName = req.firstName;
                lastName = req.lastName;
                break;
            }
        }
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            SwingUtilities.invokeLater(() -> {
                UserRegisterView registerView = new UserRegisterView(firstName, lastName, userId, email);
                new UserRegisterController(registerView);
                registerView.setVisible(true);
            });
        }
        isPushed = false;
        return button.getText();
    }
}