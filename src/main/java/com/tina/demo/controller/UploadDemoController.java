package com.tina.demo.controller;

import com.tina.demo.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadDemoController {

    @Autowired
    private UploadService uploadService;

    @GetMapping("/")
    public String welcome(){
        return "Welcome to upload";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam(value = "file") MultipartFile file) throws IOException {
        return this.uploadService.uploadFile(file);
    }

}
