package com.HelpTapProj.backEnd.model;

public enum RiskRating {
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    CRITICAL(4);

    private final int rating;

    RiskRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }
}
