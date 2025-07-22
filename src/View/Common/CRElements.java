package View.Common;

import javax.swing.*;
import java.awt.*;

public class CRElements {
	public static JComponent createInputField(java.awt.event.ActionListener actionListener) {
		JComponent field = new JTextField(CRStyles.FIELD_COLUMNS);
		field.setMaximumSize(new Dimension(Integer.MAX_VALUE, CRStyles.FIELD_HEIGHT));
		field.setBackground(CRStyles.BG_LIGHT_COLOR);
		field.setBorder(BorderFactory.createLineBorder(CRStyles.FG_DARK_COLOR, 2));
		field.setFont(CRStyles.FIELD_FONT);
		if (field instanceof JTextField) {
			((JTextField) field).addActionListener(actionListener);
		}
		return field;
	}


	public static JComponent createInputField(java.awt.event.ActionListener actionListener, boolean isPassword) {
		JComponent field = isPassword ? new JPasswordField(CRStyles.FIELD_COLUMNS) : new JTextField(CRStyles.FIELD_COLUMNS);
		field.setMaximumSize(new Dimension(Integer.MAX_VALUE, CRStyles.FIELD_HEIGHT));
		field.setBackground(CRStyles.BG_LIGHT_COLOR);
		field.setBorder(BorderFactory.createLineBorder(CRStyles.FG_DARK_COLOR, 2));
		field.setFont(CRStyles.FIELD_FONT);
		if (isPassword) {
			((JPasswordField) field).setEchoChar('*');
		}
		if (field instanceof JTextField) {
			((JTextField) field).addActionListener(actionListener);
		}
		return field;
	}


	public static void createRegistrationField(JPanel panel, String labelText, JComponent field, String helpText) {
		JLabel label = new JLabel(labelText);
		label.setFont(CRStyles.MAIN_FONT);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel helpLabel = new JLabel(helpText);
		helpLabel.setFont(CRStyles.ITALIC_FONT);
		helpLabel.setForeground(Color.GRAY);
		helpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		panel.add(label);
		panel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_SMALL));
		panel.add(field);
		panel.add(helpLabel);
		panel.add(Box.createVerticalStrut(CRStyles.VERTICAL_GAP_MEDIUM));
	}


	public static JButton createButton(String text, Color bgColor, Color fgColor, boolean focusPainted, int width) {
		JButton button = new JButton(text);
		button.setFont(CRStyles.MAIN_FONT);
		button.setBackground(bgColor);
		button.setForeground(fgColor);
		button.setFocusPainted(focusPainted);
		button.setBorder(BorderFactory.createEmptyBorder()); // Transparent border
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(width, 30));
		button.setPreferredSize(new Dimension(width, 30));
		return button;
	}


	public static JLabel createTitleLabel(String text, Color textColor) {
		JLabel label = new JLabel(text);
		label.setFont(CRStyles.TITLE_FONT);
		label.setForeground(textColor);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		return label;
	}

	
	public static JPanel createPanel(Color bgColor, int axis) {
		return createMainPanel(bgColor, CRStyles.PANEL_PADDING_LARGE, axis);
	}


	public static JPanel createMainPanel(Color bgColor, int padding, int axis) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, axis));
		panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
		panel.setBackground(bgColor);
		return panel;
	}
}
