package net.andrelopes.hopfieldPatternRecognizer.utils;

public class Settings {

    private static int inputRows;
    private static int inputColumns;

    static {
        inputRows = 2;
        inputColumns = 2;
    }

    public static int getInputRows() {
        return inputRows;
    }

    public static void setInputRows(int inputRows) {
        if (inputRows > 0) {
            Settings.inputRows = inputRows;
        }
    }

    public static int getInputColumns() {
        return inputColumns;
    }

    public static void setInputColumns(int inputColumns) {
        if (inputColumns > 0) {

            Settings.inputColumns = inputColumns;
        }
    }

}
