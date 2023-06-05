package com.uic.assiduite.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.uic.assiduite.model.Utilisateurs;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author engome
 */
public class QRCodeGenerator {

    public static void QRCodeGenerator(Utilisateurs user) throws WriterException, IOException {
        String qrCodePath = "E:\\Projet_tutoré\\assiduites\\src\\main\\resources\\images\\";
        String qrCodeName = qrCodePath + user.getNom() + user.getId() + "-QRCODE.png";
        String urlprefix = "http://localhost:8080/api/attendance";
        byte [] encodeMatricule = Base64.encodeBase64(user.getMatricule().getBytes());
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitmatrix = qrCodeWriter.encode(
                urlprefix+"?matricule="+new String(encodeMatricule), BarcodeFormat.QR_CODE, 250, 250
        );
        Path path = FileSystems.getDefault().getPath(qrCodeName);
        MatrixToImageWriter.writeToPath(bitmatrix, "PNG", path);
    }
}
