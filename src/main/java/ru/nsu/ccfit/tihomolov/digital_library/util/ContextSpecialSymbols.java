package ru.nsu.ccfit.tihomolov.digital_library.util;

public class ContextSpecialSymbols {
    public static char REPLACE_SPACE_SYMBOL = '_';
    public static char SPACE = ' ';

    public static String parseTitle(String title) {
        return title.replace(ContextSpecialSymbols.REPLACE_SPACE_SYMBOL, ContextSpecialSymbols.SPACE);
    }

}
