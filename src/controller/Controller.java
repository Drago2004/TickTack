package controller;

import model.AutoPlayer;
import model.Field;
import model.GameField;

public class Controller {
    private final GameField gameField;
    private AutoPlayer autoPlayer;
    private boolean cross;

    public Controller(GameField gameField) {
        this.gameField = gameField;
        cross = true;
    }

    public void setAutoPlayer(AutoPlayer autoPlayer) {
        this.autoPlayer = autoPlayer;
    }

    public void setStep(int row, int column) {
        if (cross) { // игрок играет крестиками
            new Thread(() -> {
                gameField.setCross(row, column);
                if (gameField.isNotStop() && gameField.getCount() < 9) {
                    Field freeField = autoPlayer.step();
                    gameField.setZero(freeField.getRow(), freeField.getColumn());
                }
            }).start();
        } else { // игрок играет ноликами
            gameField.setZero(row, column);
            if (gameField.isNotStop() && gameField.getCount() < 9) {
                Field freeField = autoPlayer.step();
                gameField.setCross(freeField.getRow(), freeField.getColumn());
            }
        }
    }

    public void initNewGame() {
        cross = !cross;
        if (cross) { // игрок играет крестиками
            autoPlayer.setCrossPlay(false);
            new Thread(gameField::init).start();
        } else { // игрок играет ноликами
            autoPlayer.setCrossPlay(true);
            new Thread(() -> {
                gameField.init();
                Field freeField = autoPlayer.step();
                gameField.setCross(freeField.getRow(), freeField.getColumn());
            }).start();
        }
    }
}
