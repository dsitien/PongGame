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
public class Paddle extends Rectangle {

    int id;//id 2 player
    int yVelocity;//tốc độ paddle để di chuyển lên xuống
    int speed = 20;

    public Paddle(int x, int y, int paddle_width, int paddle_height,
            int id) {
        super(x, y, paddle_width, paddle_height);
        this.id = id;
    }

    public void keyPressed(KeyEvent e) {
        switch (id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    setYDirection(-speed);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(speed);
                    move();
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    setYDirection(-speed);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(speed);
                    move();
                }
                break;

        }
    }

    public void keyReleased(KeyEvent e) {
        switch (id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    setYDirection(0);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(0);
                    move();
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    setYDirection(0);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(0);
                    move();
                }
                break;
        }
    }

    //set trục y 
    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
        
    }

    public void move() {
        y= y + yVelocity;
    }
    // khi ta làm 2 hàm setY và move thì chỉ giải quyết vấn đề di chuyển, 
    //khi chạy sẽ phát sinh nó trượt khỏi màn hình. Để giải quyết ta qua GamePanel
    //trong phần checkCollison()
    
    public void draw(Graphics g) {
        if (id == 1) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.red);
        }
        g.fillRect(x, y, width, height);
    }

}
