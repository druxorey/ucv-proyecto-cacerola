package View.Admin;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import Model.Services.MenuService;
import Controller.Admin.MenuManagementController;
import org.json.simple.JSONObject;

public class MenuEditDialog extends JDialog {
	@SuppressWarnings("unchecked")
	public MenuEditDialog(LocalDate date, String shift, String fileName, MenuService menuService, MenuManagementController controller, int dayIndex, int shiftIndex) {
		setTitle("Configurar Menú - " + date + " " + (shift.equals("AM") ? "Mañana" : "Tarde"));
		setModal(true);
		setSize(400, 260);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(View.Common.CRStyles.BG_LIGHT_COLOR);

		JLabel label = new JLabel("Configurar menú para " + date + " (" + (shift.equals("AM") ? "Mañana" : "Tarde") + ")");
		label.setFont(View.Common.CRStyles.MAIN_FONT);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel fieldsPanel = new JPanel(new GridBagLayout());
		fieldsPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;

		JLabel dishLabel = new JLabel("Plato:");
		dishLabel.setFont(View.Common.CRStyles.MAIN_FONT);
		JTextField dishField = new JTextField(15);
		dishField.setBackground(View.Common.CRStyles.BG_LIGHT_COLOR);
		JLabel dishDesc = new JLabel("Ej: Milanesa con papas");

		JLabel drinkLabel = new JLabel("Bebida:");
		drinkLabel.setFont(View.Common.CRStyles.MAIN_FONT);
		JTextField drinkField = new JTextField(15);
		drinkField.setBackground(View.Common.CRStyles.BG_LIGHT_COLOR);
		JLabel drinkDesc = new JLabel("Ej: Jugo de naranja");

		JLabel dessertLabel = new JLabel("Postre:");
		dessertLabel.setFont(View.Common.CRStyles.MAIN_FONT);
		JTextField dessertField = new JTextField(15);
		dessertField.setBackground(View.Common.CRStyles.BG_LIGHT_COLOR);
		JLabel dessertDesc = new JLabel("Ej: Flan casero");

		JSONObject menuData = menuService.loadMenu(date, shift);
		if (menuData != null) {
			dishField.setText((String) menuData.getOrDefault("plato", ""));
			drinkField.setText((String) menuData.getOrDefault("bebida", ""));
			dessertField.setText((String) menuData.getOrDefault("postre", ""));
		}

		// Plato
		gbc.gridx = 0; gbc.gridy = 0; fieldsPanel.add(dishLabel, gbc);
		gbc.gridx = 1; fieldsPanel.add(dishField, gbc);
		gbc.gridx = 2; fieldsPanel.add(dishDesc, gbc);
		// Bebida
		gbc.gridx = 0; gbc.gridy = 1; fieldsPanel.add(drinkLabel, gbc);
		gbc.gridx = 1; fieldsPanel.add(drinkField, gbc);
		gbc.gridx = 2; fieldsPanel.add(drinkDesc, gbc);
		// Postre
		gbc.gridx = 0; gbc.gridy = 2; fieldsPanel.add(dessertLabel, gbc);
		gbc.gridx = 1; fieldsPanel.add(dessertField, gbc);
		gbc.gridx = 2; fieldsPanel.add(dessertDesc, gbc);

		JButton saveButton = new JButton("Guardar");
		saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		saveButton.setBackground(View.Common.CRStyles.ACCENT_COLOR);
		saveButton.setForeground(Color.WHITE);

		saveButton.addActionListener(_ -> {
			String dish = dishField.getText().trim();
			String drink = drinkField.getText().trim();
			String dessert = dessertField.getText().trim();
			menuService.saveMenu(date, shift, dish, drink, dessert);
			controller.updateButtons();
			JOptionPane.showMessageDialog(this, "Menú guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		});

		panel.add(Box.createVerticalStrut(10));
		panel.add(label);
		panel.add(Box.createVerticalStrut(10));
		panel.add(fieldsPanel);
		panel.add(Box.createVerticalStrut(10));
		panel.add(saveButton);
		panel.add(Box.createVerticalStrut(10));
		add(panel);
	}
}