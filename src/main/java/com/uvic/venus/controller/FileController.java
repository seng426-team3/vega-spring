package com.uvic.venus.controller;


import com.uvic.venus.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    StorageService storageService;

    @GetMapping("/listfiles")
    public ResponseEntity<List<String>> listUploadedFiles() {
        List<String> x = this.loadAllUploadedFiles();
        System.out.println("Entered into files");
        return ResponseEntity.ok(x);
    }

    @GetMapping("/fetch/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    public List<String> loadAllUploadedFiles() {
        return storageService.loadAll()
        .map(path -> path.getFileName().toString())
        .collect(Collectors.toList());
    }
}
