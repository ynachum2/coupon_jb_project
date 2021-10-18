package com.aviad_eshel_johnBryce_JDBC.Models;

public class Administrator {
    private static Administrator ourInstance = new Administrator();

    public static Administrator getInstance() {
        return ourInstance;
    }

    private Administrator() {
    }
}
