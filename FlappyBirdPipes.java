import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBirdPipes extends JPanel implements ActionListener {
    private final int WIDTH = 800;  // Screen width
    private final int HEIGHT = 600; // Screen height
    private final int PIPE_WIDTH = 80; // Width of each pipe
    private final int GAP_HEIGHT = 200; // Gap for the bird to pass through
    private final int PIPE_SPEED = 10; // Speed of pipes
    private final Timer timer;
    private final ArrayList<Pipe> pipes = new ArrayList<>();
    private final Random random = new Random();

    public FlappyBirdPipes() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.CYAN);
        this.timer = new Timer(30, this);

        // Initialize pipes
        for (int i = 0; i < 5; i++) {
            int x = WIDTH + i * (PIPE_WIDTH + 300); // Pipes are spaced apart
            addPipe(x);
        }

        timer.start();
    }

    private void addPipe(int x) {
        int topPipeHeight = random.nextInt(HEIGHT - GAP_HEIGHT - 50); // Randomize top pipe height
        pipes.add(new Pipe(x, topPipeHeight, PIPE_WIDTH));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw pipes
        for (Pipe pipe : pipes) {
            // Top pipe
            g.setColor(Color.GREEN);
            g.fillRect(pipe.x, 0, pipe.width, pipe.topHeight);

            // Bottom pipe
            g.fillRect(pipe.x, pipe.topHeight + GAP_HEIGHT, pipe.width, HEIGHT - (pipe.topHeight + GAP_HEIGHT));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Move pipes to the left
        for (Pipe pipe : pipes) {
            pipe.x -= PIPE_SPEED;
        }

        // Check if pipes go off the screen
        if (pipes.get(0).x + PIPE_WIDTH < 0) {
            pipes.remove(0);
            int newX = pipes.get(pipes.size() - 1).x + PIPE_WIDTH + 200;
            addPipe(newX);
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird Pipes");
        FlappyBirdPipes gamePanel = new FlappyBirdPipes();

        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Pipe class to represent each pipe
    static class Pipe {
        int x, topHeight, width;

        Pipe(int x, int topHeight, int width) {
            this.x = x;
            this.topHeight = topHeight;
            this.width = width;
        }
    }
}
