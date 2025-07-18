package View.Common;

import javax.swing.*;
import java.awt.*;

public class UIElements {
	public static JComponent createInputField(boolean isPassword, java.awt.event.ActionListener actionListener) {
		JComponent field = isPassword ? new JPasswordField(UIStyles.FIELD_COLUMNS) : new JTextField(UIStyles.FIELD_COLUMNS);
		field.setMaximumSize(new Dimension(Integer.MAX_VALUE, UIStyles.FIELD_HEIGHT));
		field.setBackground(UIStyles.BG_PRIMARY_COLOR);
		field.setBorder(BorderFactory.createLineBorder(UIStyles.FG_SECONDARY_COLOR, 2));
		field.setFont(UIStyles.FIELD_FONT);
		if (isPassword) {
			((JPasswordField) field).setEchoChar('*');
		}
		if (field instanceof JTextField) {
			((JTextField) field).addActionListener(actionListener);
		}
		return field;
	}

	public static JButton createButton(String text, Color bgColor, Color fgColor, boolean focusPainted, int width) {
		JButton button = new JButton(text);
		button.setFont(UIStyles.MAIN_FONT);
		button.setBackground(bgColor);
		button.setForeground(fgColor);
		button.setFocusPainted(focusPainted);
		button.setBorder(BorderFactory.createEmptyBorder()); // Transparent border
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(width, 30));
		button.setPreferredSize(new Dimension(width, 30));
		return button;
	}

	public static JLabel createTitleLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 28));
		label.setForeground(UIStyles.FG_PRIMARY_COLOR);
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}
}
