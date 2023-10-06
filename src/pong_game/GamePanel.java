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
public class GamePanel extends JPanel implements Runnable {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    //chiều cao 2 ván
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    //luồng triển khai giao diện
    Thread gameThread;
    Image image;
    Graphics gaGraphics;
    Random random;
    //hai ng chơi
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    public GamePanel() {
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall() {
        random = new Random();
        ball = new Ball((GAME_WIDTH / 2 - BALL_DIAMETER / 2),
                random.nextInt(GAME_HEIGHT - BALL_DIAMETER),
                BALL_DIAMETER, BALL_DIAMETER);

    }

    public void newPaddles() {
        paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2),
                PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2),
                PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        gaGraphics = image.getGraphics();
        draw(gaGraphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
        
    }

    public void move() {
        //vì bên move() kia chỉ 1 cái chạy, với này giúp chạy nhanh mượt hơn
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision() {
        //ngăn quả bóng ko bay ra khỏi windform
        //top and bottom
        if (ball.y <= 0) {
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.y >= (GAME_HEIGHT - BALL_DIAMETER)) {
            ball.setYDirection(-ball.yVelocity);
        }
        //nếu banh nó chạm paddle thì bật nó
        //lệnh dưới đây là câu lệnh kiểm tra va chạm để bật ngược lại
        if (ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;//điều này khiến khi chạm vào paddle thì vận tốc tăng lên
            if (ball.yVelocity > 0) {
                ball.yVelocity++;
            } else {
                ball.yVelocity--;

            }
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if (ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;//điều này khiến khi chạm vào paddle thì vận tốc tăng lên
            if (ball.yVelocity > 0) {
                ball.yVelocity++;
            } else {
                ball.yVelocity--;

            }
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);

        }

        //ngăn paddle ra khỏi window form
        if (paddle1.y <= 0) {
            paddle1.y = 0;
        }
        if (paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }
        if (paddle2.y <= 0) {
            paddle2.y = 0;
        }
        if (paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) {
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
        }

        //cộng điểm cho player
        if (ball.x <= 0) {
            score.player2++;
            newPaddles();
            newBall();

        }

        if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            score.player1++;
            newPaddles();
            newBall();

        }
    }

    public void run() {
    //lastTime và now là biến dùng để đo thời gian để tính toán thời gian đã trôi qua giữa các vòng lặp.
    //amountOfTicks là số lần cập nhật mỗi giây mà bạn muốn cho trò chơi. Trong trường hợp này, bạn đang cố gắng cập nhật trò chơi 60 lần mỗi giây.
    //ns là số nano giây mà mỗi vòng lặp phải đợi để đảm bảo rằng trò chơi cập nhật với tốc độ cố định (60 lần/giây).
    //delta là biến để theo dõi thời gian đã trôi qua giữa các vòng lặp.
    //Bên trong vòng lặp while (true), các bước sau được thực hiện:
    //
    //now lấy thời gian hiện tại dưới dạng nano giây.
    //delta tính toán thời gian đã trôi qua kể từ lần lặp trước đó.
    //Nếu delta lớn hơn hoặc bằng 1, tức là đã đủ thời gian để cập nhật trò chơi ở tốc độ mong muốn, các bước sau được thực hiện:
    //move() gọi phương thức move() để cập nhật trạng thái của các thành phần trong trò chơi (như bóng và các ván).
    //checkCollision() gọi phương thức checkCollision() để kiểm tra va chạm giữa các thành phần và thực hiện các xử lý liên quan đến va chạm.
    //repaint() gọi phương thức repaint() để vẽ lại giao diện trò chơi dựa trên các thay đổi đã xảy ra trong move() và checkCollision().
    //delta giảm đi 1 để đánh dấu việc đã thực hiện một lần cập nhật.
        //game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
                //System.out.println("pong_game.GamePanel.run()");

            }
        }

    }

    public class AL extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
