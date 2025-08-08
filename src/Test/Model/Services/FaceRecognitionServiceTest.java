package Test.Model.Services;

import org.junit.Test;
import Model.Services.FaceRecognitionService;

import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.*;

public class FaceRecognitionServiceTest {

    @Test
    public void testCompareFaceImageReturnsFalseIfReferenceMissing() throws Exception {
        FaceRecognitionService service = new FaceRecognitionService();
        File temp = File.createTempFile("face", ".jpg");
        assertFalse(service.compareFaceImage("noexiste", temp));
        temp.delete();
    }

    @Test
    public void testCompareFaceImageReturnsFalseIfUploadedMissing() throws Exception {
        FaceRecognitionService service = new FaceRecognitionService();
        File ref = new File("Model/Data/Faces/testuser.jpg");
        if (ref.exists()) ref.delete();
        assertFalse(service.compareFaceImage("testuser", new File("noexiste.jpg")));
    }

    @Test
    public void testCompareFaceImageReturnsTrueIfSameLength() throws Exception {
        FaceRecognitionService service = new FaceRecognitionService();
        File dir = new File("Model/Data/Faces");
        if (!dir.exists()) dir.mkdirs();
        File ref = new File(dir, "sameuser.jpg");
        File up = File.createTempFile("sameuser", ".jpg");
        try (FileWriter fw = new FileWriter(ref)) { fw.write("abc"); }
        try (FileWriter fw = new FileWriter(up)) { fw.write("abc"); }
        assertTrue(service.compareFaceImage("sameuser", up));
        ref.delete();
        up.delete();
    }
}