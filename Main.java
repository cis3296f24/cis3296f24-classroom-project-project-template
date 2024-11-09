import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import com.thecherno.flappy.graphics.Shader;
import com.thecherno.flappy.input.Input;
import com.thecherno.flappy.level.Level;
import com.thecherno.flappy.maths.Matrix4f;


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
        if (glfwInit() != GL_TRUE) {
            System.err.println("Failed to initialize GLFW");
            return;
        }

        glfwDefaultWindowHints(GLFW_RESIZABLE, GLFW_TRUE);
        window = glfwCreateWindow(width, height, "03-Flappy-Bird", NULL, NULL);
        if (window == NULL) {
            // To do: Handle
            return;
        }

        ByteBuffer  vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (GLFWVidMode.width(vidmode) - width / 2), (GLFWVidMode.height(vidmode) - height / 2));
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
    }

    public void run() {
        init();
        while (running) {
            update();
            render();

            if (glfwWindowShouldClose(window) == GLFW_TRUE)
                running = false;

        }
    }

    public void update() {
        glfwPollEvents();
    }
    public void render() {
        glfwSwapBuffers(window);
    }


    public static void main(String[] args) {
        new Main().start();
    }
}
