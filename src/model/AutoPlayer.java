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
        if (count == 9) {
            throw new IndexOutOfBoundsException("Ошибка! Ходы закончены.");
        }
        if (count == 1 && freeFields.contains(new Field(1,1))) {
            return new Field(1, 1);
        } else {
            int index = (int) (Math.random() * (freeFields.size() - 1));
            return freeFields.get(index);
        }
    }
}
