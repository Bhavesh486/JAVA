package com.resumeai.dto;

import java.util.List;

public class ResumeAnalysisResponse {

    private String candidateName;
    private int atsScore;
    private int keywordMatchPercent;
    private long analysisTimeMs;
    private List<String> matchedKeywords;
    private List<String> missingKeywords;
    private List<String> suggestedImprovements;

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public int getAtsScore() {
        return atsScore;
    }

    public void setAtsScore(int atsScore) {
        this.atsScore = atsScore;
    }

    public int getKeywordMatchPercent() {
        return keywordMatchPercent;
    }

    public void setKeywordMatchPercent(int keywordMatchPercent) {
        this.keywordMatchPercent = keywordMatchPercent;
    }

    public long getAnalysisTimeMs() {
        return analysisTimeMs;
    }

    public void setAnalysisTimeMs(long analysisTimeMs) {
        this.analysisTimeMs = analysisTimeMs;
    }

    public List<String> getMatchedKeywords() {
        return matchedKeywords;
    }

    public void setMatchedKeywords(List<String> matchedKeywords) {
        this.matchedKeywords = matchedKeywords;
    }

    public List<String> getMissingKeywords() {
        return missingKeywords;
    }

    public void setMissingKeywords(List<String> missingKeywords) {
        this.missingKeywords = missingKeywords;
    }

    public List<String> getSuggestedImprovements() {
        return suggestedImprovements;
    }

    public void setSuggestedImprovements(List<String> suggestedImprovements) {
        this.suggestedImprovements = suggestedImprovements;
    }
}

