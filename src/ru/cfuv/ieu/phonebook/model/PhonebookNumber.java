package ru.cfuv.ieu.phonebook.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhonebookNumber {
    public enum Format {
        USCANADA("1", "+%1 (%2%3%4) %5%6%7-%8%9%A%B"),
        RUSSIAKZ("7", "+%1 (%2%3%4) %5%6%7-%8%9-%A%B"),
        UKRAINE("380", "+%1%2%3 (%4%5) %6%7%8-%9%A-%B%C"),
        BRITAIN("44", "+%1%2 %3%4%5%6 %7%8%9%A%B%C"),
        GERMANY("49", "+%1%2 %3%4%5%6 %7%8%9%A%B%C"),
        SINGAPORE("65", "+%1%2 %3%4%5%6 %7%8%9%A"),
        CHINA("86", "+%1%2 %3%4%5 %6%7%8%9 %A%B%C%D");

        Format(String country, String format) {
            this.country = country;
            this.format = format;
        }

        String country;
        String format;
    }

    private String number;

    public PhonebookNumber(String sNumber) {
        this.number = sNumber;
    }

    @Override
    public String toString() {
        for (Format format : Format.values()) {
            for (int i = 2; i > 0; i--) {
                if (number.substring(0, i).equals(format.country)) {
                    String fstring = format.format;
                    String sNumber;
                    sNumber = fstring.replaceAll("%0", number);
                    Pattern pat1 = Pattern.compile("%([1-9A-Fa-f])");
                    Matcher m = pat1.matcher(fstring);
                    while (m.find()) {
                        char c = m.group(1).charAt(0);
                        String digit = String.valueOf(number.charAt(
                                Character.digit(c, 16)-1));
                        sNumber = sNumber.replaceFirst("%" + c, digit);
                    }
                    return sNumber;
                }
            }
        }
        return "+" + number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
