/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pong_game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author CNM
 */
public class Ball extends Rectangle {

    //khởi tạo trái bóng bay ngẫu nhiên có vận tốc trên trục x,y
    Random random;
    int xVelocity;
    int yVelocity;
    int speed = 2;

    public Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
        random = new Random();

        // Khởi tạo vận tốc ban đầu cho x và y để đảm bảo bóng di chuyển.
        xVelocity = 3; // Thay đổi giá trị tùy theo tốc độ bạn muốn.
        yVelocity = 3; // Thay đổi giá trị tùy theo tốc độ bạn muốn.

        // Ngẫu nhiên chọn hướng ban đầu cho x và y.
        if (random.nextBoolean()) {
            xVelocity = -xVelocity; // Đảo ngược hướng x với xác suất 50%.
        }
        if (random.nextBoolean()) {
            yVelocity = -yVelocity; // Đảo ngược hướng y với xác suất 50%.
        }
    }

    public void setXDirection(int randomXDirection) {
        xVelocity = randomXDirection;
    }

    public void setYDirection(int randomYDirection) {
        yVelocity = randomYDirection;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(x, y, height, width);
    }

}
