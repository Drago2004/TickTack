package model;

import java.util.List;

public class AutoPlayer {
    private final GameField gameField;

    public AutoPlayer(GameField gameField) {
        this.gameField = gameField;
    }

    public Field step() {
        List<Field> freeFields = gameField.getFreeFields();
        int count = gameField.getCount();
        if (count == 1) { // сделан первый ход
            if (freeFields.contains(new Field(1, 1))) { // ходим в центр
                return new Field(1, 1);
            } else { // или в угол
                Field[] conerFields = {new Field(0, 0), new Field(0, 2), new Field(2, 0), new Field(2, 2)};
                int index = (int) (Math.random() * conerFields.length - 1);
                return conerFields[index];
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
