package com.mbit.pdf2image.service;

import org.ghost4j.Ghostscript;
import org.ghost4j.GhostscriptException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class Pdf2ImageService {


    public byte[] getImage(MultipartFile pdfFile, int pageNumber) throws IOException {
        File tempFile = File.createTempFile("input-", ".pdf");
        pdfFile.transferTo(tempFile);
        return getImage(tempFile, pageNumber);
    }

    private byte[] getImage(File pdfFile, int pageNumber) throws IOException {
        byte[] result = null;

        BufferedImage rasterizedEPS = null;

        File outputFile = File.createTempFile("output-", ".tif");

        Ghostscript gs = Ghostscript.getInstance();
        try {
            gs.initialize(new String[]{
                    "-dBATCH",
                    "-dQUIET",
                    "-dNOPAUSE",
                    "-r600",
                    "-sDEVICE=tifflzw",
                    "-dFirstPage=" + pageNumber,
                    "-dLastPage=" + pageNumber,
                    "-sOutputFile=" + outputFile.getAbsolutePath(),
                    pdfFile.getAbsolutePath()
            });
        } catch (GhostscriptException e) {
            e.printStackTrace();
        }
        try {
            gs.exit();
        } catch (GhostscriptException e) {
            e.printStackTrace();
        }

        try {
            rasterizedEPS = ImageIO.read(outputFile);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(rasterizedEPS, "jpg", baos);

            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();

            result = imageInByte;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
