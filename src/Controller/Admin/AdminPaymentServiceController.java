package Controller.Admin;

import View.Admin.PrepayView;
import Model.Services.UserService;
import Model.Services.FaceRecognitionService;
import Model.Services.WalletService;
import Model.Services.MenuPriceService;

import javax.swing.*;
import java.io.File;
import java.time.LocalDate;

public class AdminPaymentServiceController {
	private final FaceRecognitionService faceService = new FaceRecognitionService();
	private final UserService userService = new UserService();
	private final WalletService walletService = new WalletService();
	private final MenuPriceService priceService = new MenuPriceService();


	public void showPrepayDialog() {
		PrepayView prepayView = new PrepayView();
		prepayView.setVisible(true);

		prepayView.getConfirmButton().addActionListener(_ -> {
			String userId = prepayView.getUserIdInput();
			File selectedFile = prepayView.getSelectedFile();

			if (userId.isEmpty()) {
				JOptionPane.showMessageDialog(prepayView, "Por favor ingresa la cédula.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (selectedFile == null) {
				JOptionPane.showMessageDialog(prepayView, "Por favor selecciona una imagen.", "Advertencia", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (!userService.existsUser(userId)) {
				JOptionPane.showMessageDialog(prepayView, "El usuario no existe.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			boolean match = faceService.compareFaceImage(userId, selectedFile);
			if (!match) {
				JOptionPane.showMessageDialog(prepayView, "La imagen no coincide con la registrada. Intenta de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			LocalDate date = LocalDate.now();
			String shift = "AM";

			double saldo = walletService.getBalance(userId);
			double monto = priceService.calculateMenuPrice(userId, date, shift);

			if (saldo < monto) {
				JOptionPane.showMessageDialog(prepayView, "Saldo insuficiente para realizar el pago.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			boolean success = walletService.deductBalance(userId, monto);
			if (success) {
				walletService.updateTransactionHistory(userId, -monto, "Pre-pago menú", date, shift);
				System.out.println("[AdminPaymentServiceController] Monto descontado: " + monto + " del usuario: " + userId);
				JOptionPane.showMessageDialog(prepayView, "Pago realizado exitosamente. ¡Buen provecho!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				prepayView.dispose();
			} else {
				JOptionPane.showMessageDialog(prepayView, "Error al descontar el saldo.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}