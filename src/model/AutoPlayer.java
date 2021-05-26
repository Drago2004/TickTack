package model;

import java.util.List;

public class AutoPlayer {
    private final GameField gameField;
    private boolean crossPlay;

    public AutoPlayer(GameField gameField) {
        this.gameField = gameField;
        crossPlay = false;
    }

    public void setCrossPlay(boolean crossPlay) {
        this.crossPlay = crossPlay;
    }

    public Field step() {
        List<Field> freeFields = gameField.getFreeFields();
        int count = gameField.getCount();
        if (crossPlay) { // робот играет крестиками
            if (count == 0) { // делаем первый ход
                return new Field(1, 1);
            } else if (count < 9) {
                Field zeroWinField = gameField.getWinField(FieldStatus.ZERO);
                Field crossWinField = gameField.getWinField(FieldStatus.CROSS);
                if (crossWinField != null) { // делаем свой выигрышный ход
                    return crossWinField;
                } else if (zeroWinField != null) { // блокируем ход ноликов
                    return zeroWinField;
                } else { // ходим в любое свободное поле
                    int index = (int) (Math.random() * (freeFields.size() - 1));
                    return freeFields.get(index);
                }
            } else {
                throw new IndexOutOfBoundsException("Ошибка! Ходы закончены.");
            }
        } else { // робот играет ноликами
            if (count == 1) { // сделан первый ход
                if (freeFields.contains(new Field(1, 1))) { // ходим в центр
                    return new Field(1, 1);
                } else { // или в угол
                    Field[] cornerFields = {new Field(0, 0), new Field(0, 2), new Field(2, 0), new Field(2, 2)};
                    int index = (int) (Math.random() * cornerFields.length - 1);
                    return cornerFields[index];
                }
            } else if (count < 9) {
                Field zeroWinField = gameField.getWinField(FieldStatus.ZERO);
                Field crossWinField = gameField.getWinField(FieldStatus.CROSS);
                if (zeroWinField != null) { // делаем свой выигрышный ход
                    return zeroWinField;
                } else if (crossWinField != null) { // блокируем выигрышний ход крестиков
                    return crossWinField;
                } else {// ходим в любое свободное поле
                    int index = (int) (Math.random() * (freeFields.size() - 1));
                    return freeFields.get(index);
                }
            } else {
                throw new IndexOutOfBoundsException("Ошибка! Ходы закончены.");
            }
        }

    }
}
