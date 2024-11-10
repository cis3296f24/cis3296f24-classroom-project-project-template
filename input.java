import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

class Input extends GLFWKeyCallback {

    public static boolean[] keys = new boolean[65536];

    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action == GLFW.GLFW_RELEASE; // We use key release because we fly when key is pressed.

    }
}
