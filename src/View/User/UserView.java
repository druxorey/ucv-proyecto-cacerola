package View.User;

import javax.swing.*;
import java.awt.*;
import View.Common.*;
import Model.Services.WalletService;
import Model.Entities.WalletMovement;
import java.util.List;
import java.text.SimpleDateFormat;

public class UserView extends JFrame {
    private JPanel leftPanel;
    private JPanel rightPanel;
    private CardLayout cardLayout;
    private JButton walletButton;
    private JButton menuButton;
    private WalletService walletService = new WalletService();


    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(
            UIStyles.PANEL_PADDING,
            UIStyles.PANEL_PADDING,
            UIStyles.PANEL_PADDING,
            UIStyles.PANEL_PADDING));
        leftPanel.setBackground(UIStyles.BG_SECONDARY_COLOR);

        walletButton = UIElements.createButton(
            "Monedero Virtual",
            UIStyles.ACCENT_COLOR,
            Color.WHITE,
            false,
            UIStyles.BUTTON_WIDTH_MEDIUM);
        walletButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        walletButton.addActionListener(e -> showCard("monedero"));

        menuButton = UIElements.createButton(
            "Menú",
            UIStyles.BG_PRIMARY_COLOR,
            UIStyles.FG_PRIMARY_COLOR,
            false,
            UIStyles.BUTTON_WIDTH_MEDIUM);
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.addActionListener(e -> showCard("menu"));

        leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
        leftPanel.add(walletButton);
        leftPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
        leftPanel.add(menuButton);
        leftPanel.add(Box.createVerticalGlue());

        return leftPanel;
    }


    private JPanel createRightPanel(String userId) {
        cardLayout = new CardLayout();
        JPanel panel = new JPanel(cardLayout);

        // Monedero Panel
        JPanel walletPanel = new JPanel();
        walletPanel.setLayout(new BoxLayout(walletPanel, BoxLayout.Y_AXIS));
        walletPanel.setBackground(UIStyles.BG_PRIMARY_COLOR);
        walletPanel.setBorder(BorderFactory.createEmptyBorder(
            UIStyles.PANEL_PADDING,
            UIStyles.PANEL_PADDING,
            UIStyles.PANEL_PADDING,
            UIStyles.PANEL_PADDING));
        walletPanel.add(UIElements.createTitleLabel("Monedero Virtual"));

        // Saldo
        double saldo = walletService.getBalance(userId);
        JLabel saldoLabel = new JLabel("Saldo actual: $" + String.format("%.2f", saldo));
        saldoLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        saldoLabel.setForeground(UIStyles.ACCENT_COLOR);
        saldoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        walletPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
        walletPanel.add(saldoLabel);

        // Movimientos
        walletPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_MEDIUM));
        JLabel movTitle = new JLabel("Últimos movimientos");
        movTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        movTitle.setForeground(UIStyles.FG_PRIMARY_COLOR);
        movTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        walletPanel.add(movTitle);

        List<WalletMovement> movimientos = walletService.getMovements();
        if (movimientos.isEmpty()) {
            JLabel noMov = new JLabel("No hay movimientos recientes.");
            noMov.setFont(new Font("Segoe UI", Font.ITALIC, 15));
            noMov.setForeground(UIStyles.FG_SECONDARY_COLOR);
            noMov.setAlignmentX(Component.CENTER_ALIGNMENT);
            walletPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_SMALL));
            walletPanel.add(noMov);
        } else {
            JPanel movListPanel = new JPanel();
            movListPanel.setLayout(new BoxLayout(movListPanel, BoxLayout.Y_AXIS));
            movListPanel.setBackground(UIStyles.BG_PRIMARY_COLOR);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            for (WalletMovement mov : movimientos) {
                String text = String.format("%s | %s | %s$%.2f",
                        sdf.format(mov.getDate()),
                        mov.getDescription(),
                        mov.getAmount() >= 0 ? "+" : "",
                        mov.getAmount());
                JLabel movLabel = new JLabel(text);
                movLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                movLabel.setForeground(UIStyles.FG_PRIMARY_COLOR);
                movListPanel.add(movLabel);
            }
            JScrollPane scroll = new JScrollPane(movListPanel);
            scroll.setPreferredSize(new Dimension(400, 180));
            scroll.setBorder(BorderFactory.createEmptyBorder());
            walletPanel.add(Box.createVerticalStrut(UIStyles.VERTICAL_STRUT_SMALL));
            walletPanel.add(scroll);
        }

        // Menú Panel
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(UIStyles.BG_PRIMARY_COLOR);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(
            UIStyles.PANEL_PADDING,
            UIStyles.PANEL_PADDING,
            UIStyles.PANEL_PADDING,
            UIStyles.PANEL_PADDING));
        menuPanel.add(UIElements.createTitleLabel("Menú"));

        panel.add(walletPanel, "monedero");
        panel.add(menuPanel, "menu");

        return panel;
    }


    private void showCard(String name) {
        cardLayout.show(rightPanel, name);
        setTitle(name);

        if (name.equals("monedero")) {
            walletButton.setBackground(UIStyles.ACCENT_COLOR);
            walletButton.setForeground(Color.WHITE);
            menuButton.setBackground(UIStyles.BG_PRIMARY_COLOR);
            menuButton.setForeground(UIStyles.FG_PRIMARY_COLOR);
        } else if (name.equals("menu")) {
            menuButton.setBackground(UIStyles.ACCENT_COLOR);
            menuButton.setForeground(Color.WHITE);
            walletButton.setBackground(UIStyles.BG_PRIMARY_COLOR);
            walletButton.setForeground(UIStyles.FG_PRIMARY_COLOR);
        }
    }


    public UserView(String userId) {
        setTitle("Panel de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(UIStyles.WINDOW_WIDTH_INTERFACE, UIStyles.WINDOW_HEIGHT_INTERFACE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        leftPanel = createLeftPanel();
        rightPanel = createRightPanel(userId);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UserView("").setVisible(true);
        });
    }
}
