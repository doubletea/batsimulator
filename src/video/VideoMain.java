package video;

import org.lwjgl.*;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.opengl.GL;

public class VideoMain {
	 // The window handle
    private long window;
    private final int WIDTH = 320;
    private final int HEIGHT = 320;
    
    private VideoBat bat;
    
    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
 
        try {
        	bat = new VideoBat();
        	
            init();
            loop();
 
            // Free the window callbacks and destroy the window
            glfwFreeCallbacks(window);
            glfwDestroyWindow(window);
        } finally {
            // Terminate GLFW and free the error callback
            glfwTerminate();
            glfwSetErrorCallback(null).free();
        }
    }
 
    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
 
        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Bat Simulator!", NULL, NULL);
        if ( window == NULL ){
        	throw new RuntimeException("Failed to create the GLFW window");
        }
        
        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
            window,
            (vidmode.width() - WIDTH) / 2,
            (vidmode.height() - HEIGHT) / 2
        );
 
     // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
        	if (key == GLFW_KEY_UNKNOWN){
        		return;
        	}
        	if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ){
        		glfwSetWindowShouldClose(window, true); // We will detect this in our rendering loop
        		return;
        	}
                
        	bat.command(action, key);
        	
        });
        
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
        // Make the window visible
        glfwShowWindow(window);
    }
 
    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        
        glClearColor(0, 0, 0, 0);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        
        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        
        
 
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
           
        	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        	bat.draw();
        	
        	glLoadIdentity();
        	glColor3f(0, 1, 0);
        	
            glBegin(GL_QUADS);
            glVertex3f(-.9f, -.9f, 0.0f);
            glVertex3f(-.9f, 0.9f, 0.0f);
            glVertex3f(-.8f, 0.9f, 0.0f);
            glVertex3f(-.8f, -.9f, 0.0f);
            
            glEnd();
            
            glColor3f(0, 0, 1);
            glLoadIdentity();
            
            glBegin(GL_QUADS);
            glVertex3f(.9f, -.9f, 0.0f);
            glVertex3f(.9f, 0.9f, 0.0f);
            glVertex3f(.8f, 0.9f, 0.0f);
            glVertex3f(.8f, -.9f, 0.0f);
            glEnd();
            
            glFlush();
            
            
            
            glfwSwapBuffers(window); // swap the color buffers
 
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
 
    public static void main(String[] args) {
        new VideoMain().run();
    }
 
}
