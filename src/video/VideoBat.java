package video;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.joml.Vector4f;

public class VideoBat {
	private Vector4f moment;
	
	public VideoBat(){
		this.moment = new Vector4f();
	}
	
	public void draw(){
		glLoadIdentity();
    	glTranslatef(moment.x, 0.0f, 0.0f);
    	glRotatef(moment.w,0.0f,0.0f,1.0f); //  rotate around center
    	
    	glColor3f(1, 0, 0);
        glBegin(GL_TRIANGLES);
        glVertex3f(-0.05f, -0.05f, 0.0f);
        glColor3f(1, 1, 0);
        glVertex3f(0.0f, 0.05f, 0.0f);
        glColor3f(1, 0, 0);
        glVertex3f(0.05f, -0.05f, 0.0f);
        glEnd();
	}
	
	public void command(int action, int key){
		if (action == GLFW_PRESS || action == GLFW_REPEAT) {
			if (key == GLFW_KEY_D){
				moment.x += 0.1f;
			}
				
			if (key == GLFW_KEY_A){
				moment.x -= 0.1f;
			}
				
			if (key == GLFW_KEY_Q){
				moment.w += 1f;
			}
				
			if (key == GLFW_KEY_E){
				moment.w -= 1f;
			}
				
         }
	}
}
