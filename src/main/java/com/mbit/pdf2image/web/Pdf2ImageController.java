package com.mbit.pdf2image.web;

import com.mbit.pdf2image.service.Pdf2ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class Pdf2ImageController {

    private final Pdf2ImageService pdf2ImageService;

    @PostMapping
    public ResponseEntity<byte[]> getImageFromPdf(@RequestParam MultipartFile file,
                                                  @RequestParam Integer pageNumber) throws IOException {

        byte[] result = pdf2ImageService.getImage(file, pageNumber);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
