package View.Admin;

import javax.swing.*;
import java.awt.*;
import View.Common.*;
import Controller.Admin.AdminController;

public class AdminView extends JFrame {
    private JPanel leftPanel;
    private JPanel rightPanel;
    private CardLayout cardLayout;
    private JButton turnosButton;
    private JButton usuariosButton;

    public AdminView() {
        setTitle("Panel de Administrador");
        setSize(UIStyles.WINDOW_WIDTH_INTERFACE, UIStyles.WINDOW_HEIGHT_INTERFACE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        leftPanel = createLeftPanel();
        rightPanel = createRightPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setEnabled(false);
        splitPane.setDividerSize(0);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        add(splitPane);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int w = getWidth();
                double relation = 0.25; // 25% width for left panel
                int divider = (int) (w * relation);
                splitPane.setDividerLocation(divider);
            }
        });
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UIStyles.BG_SECONDARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(UIStyles.PANEL_PADDING, UIStyles.PANEL_PADDING, UIStyles.PANEL_PADDING, UIStyles.PANEL_PADDING));

        turnosButton = UIElements.createStyledButton("Gestión de Turnos", UIStyles.ACCENT_COLOR, Color.WHITE, false);
        turnosButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        turnosButton.addActionListener(e -> showCard("turnos"));
        panel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
        panel.add(turnosButton);
        panel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));

        usuariosButton = UIElements.createStyledButton("Gestión de Usuarios", UIStyles.BG_PRIMARY_COLOR, UIStyles.FG_SECONDARY_COLOR, false);
        usuariosButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        usuariosButton.addActionListener(e -> showCard("usuarios"));
        panel.add(usuariosButton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createRightPanel() {
        cardLayout = new CardLayout();
        JPanel panel = new JPanel(cardLayout);
        panel.setBackground(UIStyles.BG_PRIMARY_COLOR);

        JPanel turnosPanel = new JPanel();
        turnosPanel.setBackground(UIStyles.BG_PRIMARY_COLOR);
        JLabel turnosLabel = new JLabel("Vista temporal para Gestión de Turnos");
        turnosLabel.setFont(UIStyles.TITLE_FONT);
        turnosLabel.setForeground(UIStyles.FG_SECONDARY_COLOR);
        turnosPanel.add(turnosLabel);

        JPanel usuariosPanel = new JPanel();
        usuariosPanel.setBackground(UIStyles.BG_PRIMARY_COLOR);
        usuariosPanel.setLayout(new BoxLayout(usuariosPanel, BoxLayout.Y_AXIS));
        usuariosPanel.setBorder(BorderFactory.createEmptyBorder(UIStyles.PANEL_PADDING, UIStyles.PANEL_PADDING, UIStyles.PANEL_PADDING, UIStyles.PANEL_PADDING));

        JLabel usuariosLabel = new JLabel("Gestión de Usuarios");
        usuariosLabel.setFont(UIStyles.TITLE_FONT);
        usuariosLabel.setForeground(UIStyles.FG_SECONDARY_COLOR);
        usuariosLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usuariosPanel.add(usuariosLabel);
        usuariosPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));

        JTextField nombreField = (JTextField) UIElements.createInputField(false, null);
        addField(usuariosPanel, "Nombre", nombreField, "<html>Nombre del usuario a registrar.</html>");

        JTextField apellidoField = (JTextField) UIElements.createInputField(false, null);
        addField(usuariosPanel, "Apellido", apellidoField, "<html>Apellido del usuario a registrar.</html>");

        JTextField userIdField = (JTextField) UIElements.createInputField(false, null);
        addField(usuariosPanel, "Usuario", userIdField, "<html>Cédula o identificador único.</html>");

        JPasswordField passwordField = (JPasswordField) UIElements.createInputField(true, null);
        addField(usuariosPanel, "Contraseña", passwordField, "<html>Debe tener al menos 8 caracteres, incluir letras y números.</html>");

        JTextField emailField = (JTextField) UIElements.createInputField(false, null);
        addField(usuariosPanel, "Email", emailField, "<html>Correo institucional o personal válido.</html>");

        String[] tiposUsuario = {"Estudiante", "Profesor/Personal"};
        JComboBox<String> tipoCombo = new JComboBox<>(tiposUsuario);
        tipoCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, UIStyles.FIELD_HEIGHT));
        tipoCombo.setBackground(UIStyles.BG_PRIMARY_COLOR);
        tipoCombo.setFont(UIStyles.FIELD_FONT);
        tipoCombo.setBorder(BorderFactory.createLineBorder(UIStyles.FG_SECONDARY_COLOR, 2));
        addField(usuariosPanel, "Tipo de Usuario", tipoCombo, "<html>Selecciona el tipo de usuario.</html>");

        usuariosPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));

        JButton agregarButton = UIElements.createStyledButton("Agregar", UIStyles.ACCENT_COLOR, Color.WHITE, false);
        agregarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        usuariosPanel.add(agregarButton);

        AdminController adminController = new AdminController(this);
        agregarButton.addActionListener(e -> {
            adminController.handleAddUser(nombreField, apellidoField, userIdField, passwordField, emailField, tipoCombo);
        });

        panel.add(turnosPanel, "turnos");
        panel.add(usuariosPanel, "usuarios");

        return panel;
    }

    private void addField(JPanel panel, String labelText, JComponent field, String helpText) {
        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        panel.add(label);

        panel.add(field);

        JLabel helpLabel = new JLabel(helpText);
        helpLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        helpLabel.setForeground(Color.GRAY);
        helpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(helpLabel);
        panel.add(Box.createVerticalStrut(10));
    }

    private void showCard(String name) {
        cardLayout.show(rightPanel, name);
    }

    public static void main(String[] args) {
        AdminView adminView = new AdminView();
        adminView.setVisible(true);
    }
}