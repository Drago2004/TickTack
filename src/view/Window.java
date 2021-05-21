package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;

public class Window extends JFrame {
    private final Controller controller;
    private final int SIZE = 3;
    private JButton[][] buttons;
    private JPanel panel;

    String path = "./resources/";
    ImageIcon imgFree = new ImageIcon(path + "free.png");
    ImageIcon imgCross = new ImageIcon(path + "cross.png");
    ImageIcon imgZero = new ImageIcon(path + "zero.png");

    public Window(Controller controller) {
        this.controller = controller;
    }

    public void init() {
        setPreferredSize(new Dimension(250, 250));
        setDefaultCloseOperation(Window.EXIT_ON_CLOSE);
        setTitle("Крестики-Нолики");
        panel=createGamePanel();
        add(panel);
        setResizable(false);
        pack();
        setVisible(true);
    }

    private JPanel createGamePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3, 1, 1));
        buttons = new JButton[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                JButton button = new JButton(imgFree);
                button.setBackground(Color.WHITE);
                int row = i;
                int column = j;
                button.addActionListener(e -> {
                    if (button.getIcon().equals(imgFree)) {
                        controller.setCross(row, column);
                    }
                });
                buttons[i][j] = button;
                panel.add(button);
            }
        }
        return panel;
    }

    public void setCross(int row, int column) {
        JButton button = buttons[row][column];
        button.setIcon(imgCross);
    }

    public void setZero(int row, int column) {
        JButton button = buttons[row][column];
        button.setIcon(imgZero);
    }

    public void showStandOff() {
        int res = showConfirmDialog(this, "Ничья. Начать новую игру?", "Сообщение", JOptionPane.YES_NO_OPTION);
        if (res == YES_OPTION) {
            controller.initNewGame();
        } else {
            System.exit(0);
        }
    }

    public void showWinner(String sign) {
        String message = "Победили " + sign + "! Начать новую игру?";
        int res = showConfirmDialog(this, message, "Сообщение", JOptionPane.YES_NO_OPTION);
        if (res == YES_OPTION) {
            controller.initNewGame();
        } else {
            System.exit(0);
        }
    }

    public void repaintPanel() {
        remove(panel);
        panel = createGamePanel();
        add(panel);
        revalidate();
        repaint();
    }
}
