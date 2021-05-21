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

    public void init() {
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

    public List<Field> getFreeFields() {
        return freeFields;
    }

    public int getCount() {
        return count;
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
