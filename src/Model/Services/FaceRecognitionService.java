package Model.Services;

import java.io.File;

public class FaceRecognitionService {

	private static final String FACES_DIR = "Model/Data/Faces";

	public boolean compareFaceImage(String userId, File uploadedImage) {
		File referenceImage = new File(FACES_DIR, userId + ".jpg");
		if (!referenceImage.exists() || !uploadedImage.exists()) {
			return false;
		}
		return referenceImage.length() == uploadedImage.length();
	}
}