package com.mbit.pdf2image.service;

import com.mbit.pdf2image.domain.Timer;
import org.ghost4j.Ghostscript;
import org.ghost4j.GhostscriptException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class Pdf2ImageService {


    public byte[] getImage(MultipartFile pdfFile, int pageNumber) throws IOException {
        File tempFile = File.createTempFile("input-", ".pdf");
        pdfFile.transferTo(tempFile);

        return getImageFromPdf(tempFile, pageNumber);
    }

    private byte[] getImageFromPdf(File pdfFile, int pageNumber) throws IOException {
        Timer timer = new Timer("getImageFromPdf");
        byte[] result = null;

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
            gs.exit();

        } catch (GhostscriptException e) {
            e.printStackTrace();
        }

        try {

            result = Files.readAllBytes(outputFile.toPath());

        } catch (IOException e) {
            e.printStackTrace();
        }

        timer.stop();

        return result;
    }
}
