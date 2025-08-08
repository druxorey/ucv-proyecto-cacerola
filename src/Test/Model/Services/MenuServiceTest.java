package Test.Model.Services;

import org.junit.Test;
import Model.Services.MenuService;
import org.json.simple.JSONObject;

import java.io.File;
import java.time.LocalDate;

import static org.junit.Assert.*;
import java.io.FileWriter;

public class MenuServiceTest {

    @Test
    public void testMenuExistsReturnsFalseIfNoFile() {
        MenuService service = new MenuService();
        assertFalse(service.menuExists(LocalDate.of(2099,1,1), "M"));
    }

    @Test
    public void testSaveMenuAndLoadMenu() {
        MenuService service = new MenuService();
        LocalDate date = LocalDate.of(2024, 6, 15);
        String shift = "M";
        service.saveMenu(date, shift, "Arroz", "Jugo", "Torta");
        assertTrue(service.menuExists(date, shift));
        JSONObject obj = service.loadMenu(date, shift);
        assertNotNull(obj);
        assertEquals("Arroz", obj.get("plato"));
        // Limpieza
        new File(service.getMenuFileName(date, shift)).delete();
    }

    @Test
    public void testLoadMenuReturnsNullIfMalformed() throws Exception {
        MenuService service = new MenuService();
        LocalDate date = LocalDate.of(2024, 6, 16);
        String shift = "M";
        File f = new File(service.getMenuFileName(date, shift));
        f.getParentFile().mkdirs();
        try (FileWriter fw = new FileWriter(f)) { fw.write("not a json"); }
        assertNull(service.loadMenu(date, shift));
        f.delete();
    }
}