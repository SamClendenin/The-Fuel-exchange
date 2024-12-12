package com.example.T_F_E;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ResourceController {
    @GetMapping("/styles/{filename}")
    public ResponseEntity<Resource> serveCss(@PathVariable String filename) {
        Resource resource = new ClassPathResource("static/styles/" + filename);
        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("text/css"))
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        Resource resource = new ClassPathResource("static/images/" + filename);
        if (resource.exists() && resource.isReadable()){
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }
}
