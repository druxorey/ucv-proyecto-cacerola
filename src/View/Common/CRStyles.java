package View.Common;

import java.awt.*;

public class CRStyles {
	// Colors
	public static final String BG_LIGHT_HEX = "#fffcf2";
	public static final String BG_DARK_HEX = "#403d39";
	public static final String FG_LIGHT_HEX = "#252422";
	public static final String FG_DARK_HEX = "#ccc5b9";
	public static final String ACCENT_HEX = "#eb5e28";
	public static final String DEBUG_HEX = "#00ff00";

	public static final Color BG_LIGHT_COLOR = Color.decode(BG_LIGHT_HEX);
	public static final Color BG_DARK_COLOR = Color.decode(BG_DARK_HEX);
	public static final Color FG_LIGHT_COLOR = Color.decode(FG_LIGHT_HEX);
	public static final Color FG_DARK_COLOR = Color.decode(FG_DARK_HEX);
	public static final Color ACCENT_COLOR = Color.decode(ACCENT_HEX);
	public static final Color DEBUG_COLOR = Color.decode(DEBUG_HEX);

	// Sizes
	public static final short WINDOW_WIDTH_LOGIN = 820;
	public static final short WINDOW_HEIGHT_LOGIN = 570;
	public static final short WINDOW_WIDTH_INTERFACE = 1366;
	public static final short WINDOW_HEIGHT_INTERFACE = 768;
	public static final short FIELD_HEIGHT = 30;
	public static final short FIELD_COLUMNS = 20;
	public static final short PANEL_PADDING_LARGE = 30;
	public static final short PANEL_PADDING_SMALL = 10;
	public static final short VERTICAL_GAP_SMALL = 10;
	public static final short VERTICAL_GAP_MEDIUM = 20;
	public static final short VERTICAL_GAP_LARGE = 30;
	public static final short BUTTON_WIDTH_SMALL = 120;
	public static final short BUTTON_WIDTH_MEDIUM = 260;

	// Fonts
	public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 26);
	public static final Font MAIN_FONT = new Font("Segoe UI", Font.BOLD, 14);
	public static final Font FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 14);
	public static final Font ITALIC_FONT = new Font("Segoe UI", Font.ITALIC, 14);
}
