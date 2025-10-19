package com.spendzy.ui.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

public class FormatUtils {
    private static final NumberFormat INR = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
    private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyy-MM-dd");

    public static String inr(double v) {
        return INR.format(Math.round(v * 100.0) / 100.0);
    }

    public static String date(Date d) {
        if (d == null) return "";
        return DATE_FMT.format(d);
    }
}
