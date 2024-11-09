import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.windows.WinBase.TRUE;

import java.nio.ByteBuffer;
import org.lwjgl.glfw.GLFW;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;


public class Main implements Runnable {

    private int width= 1280;
    private int height= 720;

    private Thread thread;
    private boolean running = false;

    private long window;

    public void start() {
        running = true;
        thread = new Thread(this, "Game");
        thread.start();
    }

    private void init() {
        if (!glfwInit()) {
            System.err.println("Failed to initialize GLFW");
            return;
        }

        //glfwDefaultWindowHints(GLFW_RESIZABLE, GLFW_TRUE);
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        window = glfwCreateWindow(width, height, "03-Flappy-Bird", NULL, NULL);
        if (window == NULL) {
            // To do: Handle
            return;
        }

        GLFWVidMode  vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        //glfwSetWindowPos(window, (GLFWVidMode.width(vidmode) - width / 2), (GLFWVidMode.height(vidmode) - height / 2));
        glfwSetWindowPos(window,(vidmode.width() - width) / 2,(vidmode.height() - height) / 2);

        glfwSetKeyCallback(window, new Input()); // Uses the class Input

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
    }

    public void run() {
        init();
        while (running) {
            update();
            render();

            if (glfwWindowShouldClose(window))
                running = false;

        }
    }

    public void update() {
        glfwPollEvents();
        if (Input.keys[GLFW_KEY_SPACE]) {
            System.out.println("flap");
        }
    }
    public void render() {
        glfwSwapBuffers(window);
    }


    public static void main(String[] args) {
        new Main().start();
    }
}
