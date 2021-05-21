package controller;

import model.AutoPlayer;
import model.Field;
import model.GameField;

public class Controller {
    private final GameField gameField;
    private AutoPlayer autoPlayer;

    public Controller(GameField gameField) {
        this.gameField = gameField;
    }

    public void setAutoPlayer(AutoPlayer autoPlayer) {
        this.autoPlayer = autoPlayer;
    }

    public void setCross(int row, int column) {
        new Thread(() -> {
            gameField.setCross(row, column);
            if (gameField.getCount() < 9) {
                Field freeField = autoPlayer.step();
                gameField.setZero(freeField.getRow(), freeField.getColumn());
            }
        }).start();
    }

    public void initNewGame() {
        new Thread(gameField::init).start();
    }
}
