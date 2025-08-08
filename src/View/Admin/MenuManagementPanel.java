package View.Admin;

import javax.swing.*;
import java.awt.*;
import Controller.Admin.MenuManagementController;
import View.Common.CRStyles;
import View.Common.CRElements;

public class MenuManagementPanel extends JPanel {
	private MenuManagementController controller;
	public static final String[] DAYS = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
	public static final String[] SHIFTS = {"Mañana", "Tarde"};
	public JButton[][] menuButtons = new JButton[2][5];

	public MenuManagementPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(CRStyles.PANEL_PADDING_LARGE, CRStyles.PANEL_PADDING_LARGE, CRStyles.PANEL_PADDING_LARGE, CRStyles.PANEL_PADDING_LARGE));
		setBackground(View.Common.CRStyles.BG_LIGHT_COLOR);

		JLabel title = CRElements.createTitleLabel("Gestión de Menú Semanal", CRStyles.FG_LIGHT_COLOR);;
		add(title, BorderLayout.NORTH);

		JPanel tablePanel = new JPanel(new GridLayout(3, 6, 10, 10));
		tablePanel.setBackground(View.Common.CRStyles.BG_LIGHT_COLOR);

		tablePanel.add(new JLabel(""));
		for (String day : DAYS) {
			JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
			dayLabel.setFont(View.Common.CRStyles.MAIN_FONT);
			tablePanel.add(dayLabel);
		}

		for (int shift = 0; shift < 2; shift++) {
			JLabel shiftLabel = new JLabel(SHIFTS[shift], SwingConstants.CENTER);
			shiftLabel.setFont(View.Common.CRStyles.MAIN_FONT);
			tablePanel.add(shiftLabel);
			for (int day = 0; day < 5; day++) {
				JButton btn = new JButton();
				btn.setOpaque(true);
				btn.setBackground(Color.LIGHT_GRAY);
				btn.setText("Configurar");
				int finalShift = shift;
				int finalDay = day;
				btn.addActionListener(_ -> controller.onMenuButtonClicked(finalDay, finalShift));
				menuButtons[shift][day] = btn;
				tablePanel.add(btn);
			}
		}

		add(tablePanel, BorderLayout.CENTER);
	}

	
	public void setButtonConfigured(int day, int shift, boolean configured) {
		menuButtons[shift][day].setBackground(configured ? CRStyles.ACCENT_COLOR : Color.LIGHT_GRAY);
	}


	public void setController(MenuManagementController controller) {
		this.controller = controller;
		for (int shift = 0; shift < 2; shift++) {
			for (int day = 0; day < 5; day++) {
				JButton btn = menuButtons[shift][day];
				for (java.awt.event.ActionListener al : btn.getActionListeners()) {
					btn.removeActionListener(al);
				}
				int finalShift = shift;
				int finalDay = day;
				btn.addActionListener(_ -> controller.onMenuButtonClicked(finalDay, finalShift));
			}
		}
	}
}