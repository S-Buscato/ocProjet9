package com.mediscreen.constant;

public class RiskControl {
    public static final String NONE = "None";
    public static final String BORDERLINE = "Borderline";
    public static final String EARLY_ONSET = "Early onset";
    public static final String IN_DANGER = "In danger";
    public static final int AGE_LIMIT = 30;

    //RISK FOR PATIENT AGE > 30
    public static final int EARLY_ONSET_SUP_AGE_LIMIT = 8;
    public static final int IN_DANGER_SUP_AGE_LIMIT = 6;
    public static final int BORDERLINE_SUP_AGE_LIMIT = 2;
    public static final int NONE_SUP_AGE_LIMIT = 1;

    //RISK FOR PATIENT AGE < 30 AND SEX IS M
    public static final int M_EARLY_ONSET_INF_AGE_LIMIT = 5;
    public static final int M_IN_DANGER_INF_AGE_LIMIT = 3;
    public static final int M_BORDERLINE_INF_AGE_LIMIT = 0;
    public static final int M_NONE_INF_AGE_LIMIT = 2;

    //RISK FOR PATIENT AGE < 30 AND SEX IS F
    public static final int F_EARLY_ONSET_INF_AGE_LIMIT = 7;
    public static final int F_IN_DANGER_INF_AGE_LIMIT = 4;
    public static final int F_BORDERLINE_INF_AGE_LIMIT = 0;
    public static final int F_NONE_INF_AGE_LIMIT = 3;
}
