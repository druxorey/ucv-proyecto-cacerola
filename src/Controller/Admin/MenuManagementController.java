package Controller.Admin;

import View.Admin.MenuManagementPanel;
import View.Admin.MenuEditDialog;
import Model.Services.MenuService;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;

public class MenuManagementController {
	private MenuManagementPanel panel;
	private MenuService menuService;

	public MenuManagementController(MenuManagementPanel panel) {
		this.panel = panel;
		this.menuService = new MenuService();
		updateButtons();
	}


	public void onMenuButtonClicked(int dayIndex, int shiftIndex) {
		LocalDate sunday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
		LocalDate date = sunday.plusDays(dayIndex);
		String shift = (shiftIndex == 0) ? "AM" : "PM";
		String fileName = menuService.getMenuFileName(date, shift);

		MenuEditDialog dialog = new MenuEditDialog(date, shift, fileName, menuService, this, dayIndex, shiftIndex);
		dialog.setVisible(true);
	}


	public void updateButtons() {
		LocalDate sunday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
		for (int day = 0; day < 5; day++) {
			LocalDate date = sunday.plusDays(day);
			for (int shift = 0; shift < 2; shift++) {
				String shiftStr = (shift == 0) ? "AM" : "PM";
				boolean exists = menuService.menuExists(date, shiftStr);
				panel.setButtonConfigured(day, shift, exists);
			}
		}
	}
}