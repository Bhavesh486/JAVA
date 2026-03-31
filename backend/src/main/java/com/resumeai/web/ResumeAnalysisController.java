package com.resumeai.web;

import com.resumeai.dto.ResumeAnalysisResponse;
import com.resumeai.service.ResumeAnalysisService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/resume")
public class ResumeAnalysisController {

    private final ResumeAnalysisService service;

    public ResumeAnalysisController(ResumeAnalysisService service) {
        this.service = service;
    }

    @GetMapping("/analyze")
    public ResponseEntity<Map<String, Object>> analyzeInfo() {
        return ResponseEntity.ok(Map.of(
                "message", "Use POST /api/resume/analyze with multipart/form-data",
                "field", "file",
                "allowedExtensions", "pdf, doc, docx, txt"
        ));
    }

    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResumeAnalysisResponse analyze(
            @RequestPart("file") @NotNull MultipartFile file
    ) throws IOException {
        return service.analyze(file);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, Object>> handleIo(IOException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "Failed to read resume file",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", "Unexpected error",
                        "message", ex.getMessage()
                ));
    }
}

