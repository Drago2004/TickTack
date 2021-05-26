package model;

import view.Window;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameField {
    static final int SIZE = 3;
    private FieldStatus[][] fields;
    private int count;
    private Window window;
    private List<Field> freeFields;
    private boolean stop;

    public Field getWinField(FieldStatus status) {
        FieldStatus[][] copyFields = new FieldStatus[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(fields[i], 0, copyFields[i], 0, SIZE);
        }
        for (Field field : freeFields) {
            int row = field.getRow();
            int column = field.getColumn();
            copyFields[row][column] = status;
            if (isWin(status, copyFields)) {
                return field;
            } else {
                copyFields[row][column] = FieldStatus.FREE;
            }
        }
        return null;
    }

    public void init() {
        stop = false;
        fields = new FieldStatus[SIZE][SIZE];
        freeFields = new ArrayList<>(9);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                fields[i][j] = FieldStatus.FREE;
                freeFields.add(new Field(i, j));
            }
        }
        if (count > 0) {
            count = 0;
            SwingUtilities.invokeLater(() -> window.repaintPanel());
        } else {
            SwingUtilities.invokeLater(window::init);
        }
    }

    public void setCross(int row, int column) {
        if ((row >= 0 && row < SIZE) && (column >= 0 && column < SIZE) && (fields[row][column] == FieldStatus.FREE)) {
            fields[row][column] = FieldStatus.CROSS;
            count++;
            freeFields.remove(new Field(row, column));
            if (isWin(FieldStatus.CROSS)) {
                String sign = "Крестики";
                stop = true;
                SwingUtilities.invokeLater(() -> {
                    window.setCross(row, column);
                    window.showWinner(sign);
                });
            } else if (count == 9) {
                SwingUtilities.invokeLater(() -> {
                    window.setCross(row, column);
                    window.showStandOff();
                });
            } else {
                SwingUtilities.invokeLater(() -> window.setCross(row, column));
            }
        }
    }

    public void setZero(int row, int column) {
        if ((row >= 0 && row < SIZE) && (column >= 0 && column < SIZE) && (fields[row][column] == FieldStatus.FREE)) {
            fields[row][column] = FieldStatus.ZERO;
            count++;
            freeFields.remove(new Field(row, column));
            if (isWin(FieldStatus.ZERO)) {
                String sign = "Нолики";
                stop = true;
                SwingUtilities.invokeLater(() -> {
                    window.setZero(row, column);
                    window.showWinner(sign);
                });
            } else if (count == 9) {
                SwingUtilities.invokeLater(() -> {
                    window.setZero(row, column);
                    window.showStandOff();
                });
            } else {
                SwingUtilities.invokeLater(() -> window.setZero(row, column));
            }
        }
    }

    boolean isWin(FieldStatus status) {
        if (status != FieldStatus.FREE) {
            for (int i = 0; i < SIZE; i++) {
                if ((fields[i][0] == status) && (fields[i][0] == fields[i][1]) && (fields[i][0] == fields[i][2])) {
                    return true;
                }
                if ((fields[0][i] == status) && (fields[0][i] == fields[1][i]) && (fields[0][i] == fields[2][i])) {
                    return true;
                }
            }
            if (fields[1][1] == status) {
                if ((fields[1][1] == fields[0][0]) && (fields[1][1] == fields[2][2])) {
                    return true;
                }
                return (fields[1][1] == fields[2][0]) && (fields[1][1] == fields[0][2]);

            }
        }
        return false;
    }

    boolean isWin(FieldStatus status, FieldStatus[][] fld) {
        if (status != FieldStatus.FREE) {
            for (int i = 0; i < SIZE; i++) {
                if ((fld[i][0] == status) && (fld[i][0] == fld[i][1]) && (fld[i][0] == fld[i][2])) {
                    return true;
                }
                if ((fld[0][i] == status) && (fld[0][i] == fld[1][i]) && (fld[0][i] == fld[2][i])) {
                    return true;
                }
            }
            if (fld[1][1] == status) {
                if ((fld[1][1] == fld[0][0]) && (fld[1][1] == fld[2][2])) {
                    return true;
                }
                return (fld[1][1] == fld[2][0]) && (fld[1][1] == fld[0][2]);

            }
        }
        return false;
    }

    public List<Field> getFreeFields() {
        return freeFields;
    }

    public int getCount() {
        return count;
    }

    public boolean isNotStop() {
        return !stop;
    }

    public void setWindow(Window window) {
        this.window = window;
    }
}

enum FieldStatus {
    FREE,
    CROSS,
    ZERO
}
