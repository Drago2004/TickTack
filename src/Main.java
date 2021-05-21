import controller.Controller;
import model.AutoPlayer;
import model.GameField;
import view.Window;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        GameField gameField = new GameField();
        Controller controller = new Controller(gameField);
        Window window = new Window(controller);
        gameField.setWindow(window);
        gameField.init();
        AutoPlayer autoPlayer = new AutoPlayer(gameField);
        controller.setAutoPlayer(autoPlayer);
    }
}
