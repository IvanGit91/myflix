package me.personal.myflix.utility.utils;

import lombok.extern.java.Log;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringEscapeUtils.unescapeJava;

@Log
public class UtilsManipulation {
    private UtilsManipulation() {
        throw new IllegalStateException("UtilsManipulation utility class");
    }

    public static String match(String text, String patternString) {
        return match(text, patternString, null);
    }

    public static String match(String text, String patternString, Integer retGroup) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        boolean res = matcher.matches();
        return res && matcher.groupCount() > 0 && retGroup != null ? matcher.group(retGroup) : res ? "" : null;
    }

    // ------------ FORMAT ------------
    // Es: format("hi I'm {0} {1}", "john", "doe")
    public static String format(String str, Object... positionalString) {
        return MessageFormat.format(str, positionalString);
    }

    // ------------ STRING ------------
    // Es /237373/257701/257702/257801/
    // Es: To take 257701, do a similar call: parseElement(target, "/", 2, false)
    public static String parseElement(String target, String separator, int nOccurrence, boolean toEndOfString) {
        log.info("Target: " + target);
        String tempValue;
        int begin = 0, index = 0, counter = 0;
        while ((index = target.indexOf(separator, index)) != -1 && counter < nOccurrence) {
            index++;
            counter++;
            begin = index;
        }
        // Per il valore finale
        int endStringIndex = target.indexOf(separator, begin + 1);
        toEndOfString = endStringIndex == -1;
        tempValue = toEndOfString ? target.substring(begin) : target.substring(begin, endStringIndex);
        log.info("VALUE: " + tempValue);
        return tempValue;
    }

    public static String splitUntil(String target, String separator, int nOccurrence, boolean includeSeparator) {
        String result = "";
        String[] split = target.split(separator);
        int until = Math.min(nOccurrence, split.length);
        for (int i = 0; i < until; i++) {
            result += split[i];
            result = includeSeparator && (i + 1) < until ? result + unescapeJava(separator) : result;
        }
        return result;
    }

    public static String splitString(String[] target) {
        StringBuilder result = new StringBuilder();
        for (String string : target) {
            result.append(string);
            result.append(", ");
        }
        return result.toString();
    }

    public static int countArrayElements(String[] target) {
        int counter = 0;
        for (String s : target)
            if (s != null)
                counter++;
        return counter;
    }

    public static String insertBetweenString(String target, int offset, String param) {
        StringBuilder res = new StringBuilder();
        int counter = 0;
        while (target.substring(counter).length() > offset) {
            res.append(target, counter, counter + offset).append(param);
            counter += offset;
        }
        res.append(target.substring(counter));
        return res.toString();
    }

    public static Integer stringPosition(String[] array, String search) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(search))
                return i;
        }
        return null;
    }

    public static int findMax(String[] s) {
        int index = 0, max = 0;
        for (int i = 0; i < s.length; i++) {
            if (max < s[i].length()) {
                max = s[i].length();
                index = i;
            }
        }
        return index;
    }

    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    /**
     * @param s String to be modified
     * @return the String with all words in it having only the first letter capitalized
     */
    public static String capitalizeFirstLetterInAllWordsInAString(String s) {
        String[] splitted = s.split(" ");
        for (int k = 0; k < splitted.length; k++) {
            splitted[k] = splitted[k].substring(0, 1).toUpperCase() + splitted[k].substring(1).toLowerCase();
        }

        return String.join(" ", splitted).trim();
    }

    public static String filenameFromPath(String path) {
        int pos = path.lastIndexOf("\\");
        return path.substring(pos + 1);
    }

    public static String replaceAllLR(String target) {
        return target.replaceAll("[$&+,:;=?@#|'<>.^*()%!//\\\"-]", "_").trim();
    }

    public static String replaceBlankSpace(String target) {
        return target.replaceAll("\\s+", "");
    }

}
