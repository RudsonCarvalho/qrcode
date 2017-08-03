package br.com.rksc.qrcode.app;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * QrCode
 *
 */
public class App {
	
	public static void main(String[] args) throws WriterException, IOException {
		String qrCodeText = "Simone Rabelo, cpf: 22623329881";
		String filePath = "simoneQR.jpg";
		int size = 250;
		String fileType = "jpg";
		File qrFile = new File(filePath);
		createQRImage(qrFile, qrCodeText, size, fileType);
		System.out.println("QR Code generated!");
	}

	/**
	 * Generate
	 * @param qrFile
	 * @param qrCodeText
	 * @param size
	 * @param fileType
	 * @throws WriterException
	 * @throws IOException
	 */
	private static void createQRImage(File qrFile, String qrCodeText, int size, String fileType)
			throws WriterException, IOException {
		
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hastable = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
		hastable.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hastable);
				
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();

		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		
		graphics.setColor(Color.BLACK);

		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		
		ImageIO.write(image, fileType, qrFile);
	}
}
