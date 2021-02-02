package com.mycompany.form.config;

/**
 * This enum provides constants, that needed to handle user form
 */
public enum UserData {
    FIRST_NAME(0),
    LAST_NAME(1),
    MIDDLE_NAME(2),
    AGE(3),
    SALARY(4),
    EMAIL(5),
    COMPANY(6),
    ABOUT_ME(7),
    NUM_FIELDS(8);

    public static final String FILE_DATA = "data.csv";
    private int value;

    UserData(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
