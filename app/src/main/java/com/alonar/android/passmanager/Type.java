package com.alonar.android.passmanager;

public enum Type {
    WEBSITE(0), APP(1), EMAIL(2), BANK(3), UTILITY(4), OTHER(5);

    private final int value;

    private Type(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
