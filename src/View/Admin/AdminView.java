package View.Admin;

import javax.swing.*;
import java.awt.*;
import View.Common.*;

public class AdminView extends JFrame {
    public JButton adminButton;

    public AdminView() {
        setTitle("Panel de Administrador");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        adminButton = new JButton("Botón de Administrador");
        setLayout(new GridBagLayout());
        add(adminButton, new GridBagConstraints());
    }
}
