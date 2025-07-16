package Controller.Common;

import View.Common.RegisterView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController {
    private RegisterView registerView;

    public RegisterController(RegisterView registerView) {
        this.registerView = registerView;
        this.registerView.submitRegistrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(registerView, "¡Solicitud enviada correctamente!", "Registro", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(registerView, "Error al enviar la solicitud: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                }
            }
        });
    }
}
