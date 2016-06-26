package core;

import java.nio.FloatBuffer;
import video.VideoBat;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.AL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import org.urish.openal.ALException;
import org.urish.openal.OpenAL;

public class MainMain {
	//audio 
	private OpenAL openAL;
	
	// timing
	private final int FRAME_RATE = 60;
	private final double FRAME_DELAY = (long) Math.round(1000.0 / FRAME_RATE);
	private double timeDeficit;
	public static boolean running;


	// video
	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;
	private long window;
	
	//game
	private Game game;
	
	public static void main(String[] args) throws ALException {
		new MainMain();
	}

	public MainMain() throws ALException {
		init();
		execute();
		end();
	}

	private void init() throws ALException {
		// audio init
		try {
			openAL = new OpenAL();
		} catch (ALException e) {
			e.printStackTrace();
		}

		// video init
		GLFWErrorCallback.createPrint(System.err).set();
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		window = glfwCreateWindow(WIDTH, HEIGHT, "Bat Simulator!", NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - WIDTH) / 2, (vidmode.height() - HEIGHT) / 2);
		glfwSetKeyCallback(window, new KeyListener());
		glfwSetWindowCloseCallback(window, new WindowCloseListener());
        
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
        // Make the window visible
        glfwShowWindow(window);
		GL.createCapabilities();
		
		glEnable(GL_DEPTH_TEST);
        
		// game logic init
		running = true;
		timeDeficit = 0;
		game = new Game(window, openAL);
	}

	private void end() {
		// audio
		openAL.close();
		// video
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private class KeyListener implements  GLFWKeyCallbackI {
		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			if (key == GLFW_KEY_UNKNOWN){
        		return;
        	}
        	if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ){
        		running = false;
        	}
		}
	}
	
	private class WindowCloseListener implements  GLFWWindowCloseCallbackI {
		@Override
		public void invoke(long window) {
        	running = false;
		}
	}
	
	private void loop() throws ALException {
		glfwPollEvents();
		update();
		render();
	}
	
	private void update() throws ALException {
		//game logic
		game.update();
	}
	
	private void render() throws ALException {
		glClearColor(0, 0, 0, 0);
        
        game.render();
        glFlush();
		glfwSwapBuffers(window);
	}

	private void execute() throws ALException {
		long before = System.currentTimeMillis();
		while (running) {
			loop();
			long after = System.currentTimeMillis();
			try {
				double delay = FRAME_DELAY - after + before + timeDeficit;
				long roundedDelay = (long) Math.round(delay);
				roundedDelay = roundedDelay < 0 ? 0 : roundedDelay;
				timeDeficit = delay - roundedDelay;
				before = System.currentTimeMillis();
				Thread.sleep(roundedDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
