package com.resumeai.service;

import com.resumeai.dto.ResumeAnalysisResponse;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class ResumeAnalysisService {

    private final Tika tika = new Tika();

    public ResumeAnalysisResponse analyze(MultipartFile file) throws IOException {
        long start = System.currentTimeMillis();

        String text = extractText(file);
        String lower = text.toLowerCase(Locale.ROOT);

        List<String> targetKeywords = Arrays.asList(
                "java", "spring", "spring boot", "rest", "api",
                "mysql", "postgresql", "docker", "kubernetes",
                "microservices", "aws", "azure"
        );

        List<String> matched = new ArrayList<>();
        List<String> missing = new ArrayList<>();
        for (String kw : targetKeywords) {
            if (lower.contains(kw.toLowerCase(Locale.ROOT))) {
                matched.add(kw);
            } else {
                missing.add(kw);
            }
        }

        int keywordMatchPercent = (int) Math.round(100.0 * matched.size() / targetKeywords.size());
        int atsScore = 70 + (keywordMatchPercent / 5); // simple mock scoring
        if (atsScore > 99) {
            atsScore = 99;
        }

        ResumeAnalysisResponse response = new ResumeAnalysisResponse();
        response.setCandidateName(extractNameGuess(text));
        response.setAtsScore(atsScore);
        response.setKeywordMatchPercent(keywordMatchPercent);
        response.setMatchedKeywords(matched);
        response.setMissingKeywords(missing);
        response.setSuggestedImprovements(buildSuggestions(missing));
        response.setAnalysisTimeMs(System.currentTimeMillis() - start);

        return response;
    }

    private String extractText(MultipartFile file) throws IOException {
        // Try Apache Tika first; if anything fails, fall back to plain bytes as text
        try {
            return tika.parseToString(file.getInputStream());
        } catch (Exception ex) {
            byte[] bytes = file.getBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }

    private String extractNameGuess(String text) {
        String[] lines = text.split("\\R");
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.length() >= 3 && trimmed.length() <= 40 && Character.isUpperCase(trimmed.charAt(0))) {
                return trimmed;
            }
        }
        return "Candidate";
    }

    private List<String> buildSuggestions(List<String> missingKeywords) {
        List<String> suggestions = new ArrayList<>();
        if (!missingKeywords.isEmpty()) {
            suggestions.add("Add more role-specific keywords like: " + String.join(", ", missingKeywords));
        }
        suggestions.add("Use strong action verbs at the start of each bullet point.");
        suggestions.add("Quantify your impact with metrics (%, $, time saved, etc.) where possible.");
        return suggestions;
    }
}

