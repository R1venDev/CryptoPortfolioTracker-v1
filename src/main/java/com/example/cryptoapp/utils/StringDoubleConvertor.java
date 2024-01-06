package com.example.cryptoapp.utils;

public class StringDoubleConvertor {

        public static Double fromString(String s) {
            if (s.isEmpty() ||  "-".equals(s) ||  ".".equals(s) || "-.".equals(s)) {
                return 0.0 ;
            } else {
                return Double.valueOf(s);
            }
        }


        public static String toString(Double d) {
            return d.toString();
        }
    }
