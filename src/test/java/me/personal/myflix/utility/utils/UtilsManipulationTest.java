package me.personal.myflix.utility.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UtilsManipulationTest {

    @Test
    void match_WithMatchingPatternAndNoGroup_ReturnsEmptyString() {
        String result = UtilsManipulation.match("test123", "[a-z]+\\d+");
        assertEquals("", result);
    }

    @Test
    void match_WithMatchingPatternAndGroup_ReturnsMatchedGroup() {
        String result = UtilsManipulation.match("test123", "([a-z]+)(\\d+)", 2);
        assertEquals("123", result);
    }

    @Test
    void match_WithNonMatchingPattern_ReturnsNull() {
        String result = UtilsManipulation.match("test", "\\d+");
        assertNull(result);
    }

    @Test
    void format_WithMultipleParameters_ReturnsFormattedString() {
        String result = UtilsManipulation.format("Hello {0}, your balance is {1,number,currency}", "John", 1000.50);
        assertEquals("Hello John, your balance is $1,000.50", result);
    }

    @ParameterizedTest
    @CsvSource({
        "/237373/257701/257702/257801/, /, 2, false, 257701",
        "one:two:three:four, :, 2, false, three",
        "a.b.c.d, . , 3, false, d"
    })
    void parseElement_WithValidInput_ReturnsExpectedElement(
            String target, String separator, int occurrence, boolean toEnd, String expected) {
        String result = UtilsManipulation.parseElement(target, separator, occurrence, toEnd);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
        "one:two:three, :, 2, false, onetwo",
        "a.b.c.d, \\. , 3, false, abc",
        "1-2-3-4, -, 2, true, 1-2"
    })
    void splitUntil_WithValidInput_ReturnsExpectedString(
            String target, String separator, int occurrence, boolean includeSeparator, String expected) {
        String result = UtilsManipulation.splitUntil(target, separator, occurrence, includeSeparator);
        assertEquals(expected, result);
    }

    @Test
    void splitString_WithValidInput_ReturnsCommaSeparatedString() {
        String result = UtilsManipulation.splitString(new String[]{"one", "two", "three"});
        assertEquals("one, two, three, ", result);
    }

    @Test
    void countArrayElements_WithMixedArray_ReturnsNonNullCount() {
        String[] input = {"one", null, "three", null, "five"};
        int result = UtilsManipulation.countArrayElements(input);
        assertEquals(3, result);
    }

    @Test
    void insertBetweenString_WithValidInput_InsertsStringAtOffsets() {
        String result = UtilsManipulation.insertBetweenString("1234567890", 3, "-");
        assertEquals("123-456-789-0", result);
    }

    @Test
    void stringPosition_WithExistingElement_ReturnsIndex() {
        String[] array = {"apple", "banana", "cherry"};
        Integer result = UtilsManipulation.stringPosition(array, "banana");
        assertEquals(1, result);
    }

    @Test
    void stringPosition_WithNonExistingElement_ReturnsNull() {
        String[] array = {"apple", "banana", "cherry"};
        Integer result = UtilsManipulation.stringPosition(array, "orange");
        assertNull(result);
    }

    @Test
    void findMax_WithStringArray_ReturnsIndexOfLongestString() {
        String[] array = {"short", "medium length", "very long string", "medium"};
        int result = UtilsManipulation.findMax(array);
        assertEquals(2, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "hello world",
        "hElLo WoRlD",
        "HELLO WORLD"
    })
    void capitalizeFirstLetterInAllWordsInAString_WithDifferentCases_ReturnsProperlyCapitalized(String input) {
        String result = UtilsManipulation.capitalizeFirstLetterInAllWordsInAString(input);
        assertEquals("Hello World", result);
    }

    @Test
    void filenameFromPath_WithWindowsPath_ReturnsFilename() {
        String result = UtilsManipulation.filenameFromPath("C:\\path\\to\\file.txt");
        assertEquals("file.txt", result);
    }

    @Test
    void replaceAllLR_WithSpecialCharacters_ReplacesWithUnderscore() {
        String input = "file*name?with<special>characters";
        String result = UtilsManipulation.replaceAllLR(input);
        assertEquals("file_name_with_special_characters", result);
    }

    @Test
    void replaceBlankSpace_WithSpaces_RemovesAllSpaces() {
        String result = UtilsManipulation.replaceBlankSpace("  test  string  with  spaces  ");
        assertEquals("teststringwithspaces", result);
    }

    @Test
    void capitalize_WithValidInput_ReturnsCapitalizedString() {
        String result = UtilsManipulation.capitalize("test");
        assertEquals("Test", result);
    }
}
