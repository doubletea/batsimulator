package core;

import audio.AudioContext;
import audio.AudioListener;
import audio.AudioSource;
import video.VideoBat;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFlush;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.opengl.GL;

public class MainMain {
	// timing
	private final int FRAME_RATE = 60;
	private final double FRAME_DELAY = (long) Math.round(1000.0 / FRAME_RATE);
	private double timeDeficit;
	private boolean running;

	// audio
	private AudioContext context;
	private AudioListener audioListener;
	private AudioSource ping;

	// video
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	private long window;
	
	//game
	private Game game;
	
	public static void main(String[] args) {
		new MainMain();
	}

	public MainMain() {
		init();
		execute();
		end();
	}

	private void init() {
		// audio init
		context = AudioContext.createContext();
		audioListener = new AudioListener();
		ping = AudioSource.createFromVorbis("assets/sounds/submarine.ogg");
		ping.setLooping(true);

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
        
		// game logic init
		running = true;
		timeDeficit = 0;
		game = new Game(window);
	}

	private void end() {
		// audio
		ping.destroy();
		context.destroy();
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
	
	private void loop() {
		glfwPollEvents();
		update();
		render();
	}
	
	private void update() {
		//game logic
		game.update();
		VideoBat bat = game.getVideoBat();
		audioListener.setPosition(bat.getPosition());
		audioListener.setVelocity(bat.getVelocity());
		//audioListener.setUp(up);
	}
	
	private void render() {
		glClearColor(0, 0, 0, 0);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        
        game.render();
        glFlush();
		glfwSwapBuffers(window);
	}

	private void execute() {
		ping.play();
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
