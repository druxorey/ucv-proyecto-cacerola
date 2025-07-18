package View.User;

import javax.swing.*;
import java.awt.*;
import View.Common.*;

public class UserView extends JFrame {
    private JPanel leftPanel;
    private JPanel rightPanel;
    private CardLayout cardLayout;
    private JButton walletButton;
    private JButton menuButton;


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


    private JPanel createRightPanel() {
        cardLayout = new CardLayout();
        JPanel panel = new JPanel(cardLayout);

        JPanel walletPanel = new JPanel();
        walletPanel.setBackground(UIStyles.BG_PRIMARY_COLOR);
		walletPanel.setBorder(BorderFactory.createEmptyBorder(
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING,
			UIStyles.PANEL_PADDING));
        walletPanel.add(UIElements.createTitleLabel("Monedero Virtual"));

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


    public UserView() {
        setTitle("Panel de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(UIStyles.WINDOW_WIDTH_INTERFACE, UIStyles.WINDOW_HEIGHT_INTERFACE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        leftPanel = createLeftPanel();
        rightPanel = createRightPanel();

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UserView().setVisible(true);
        });
    }
}
