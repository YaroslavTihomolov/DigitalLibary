package ru.nsu.ccfit.tihomolov.digital_library.util;

public record ContextValidation() {
    public static final int MIN_TITLE_LENGTH = 1;

    public static final int MAX_TITLE_LENGTH = 50;

    public static final int MIN_DESCRIPTION_LENGTH = 1;

    public static final int MAX_DESCRIPTION_LENGTH = 1000;

    public static final int MIN_PUBLISHER_NAME_LENGTH = 1;

    public static final int MAX_PUBLISHER_NAME_LENGTH = 50;

    public static final int MIN_WORD_LENGTH = 1;

    public static final int MAX_WORD_LENGTH = 50;

    public static final int MIN_YEAR = 1;

    public static final int CURRENT_YEAR =2023;

    public static final int MIN_PAGE = 1;

    public static final int PAGE_SIZE = 10;
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 50;
    public static final int PASSWORD_MIN_LENGTH = 7;
    public static final int PASSWORD_MAX_LENGTH = 50;
}
