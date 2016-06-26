package video;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import core.Game;

public class VideoBat extends VideoObject{
	
	public VideoBat(){
		super();
	}
	
	@Override
	protected void coreRender(){
    	glColor3f(1, 0, 0);
        glBegin(GL_TRIANGLES);
        glVertex3f(-0.05f, -0.05f, 0.0f);
        glColor3f(1, 1, 0);
        glVertex3f(0.0f, 0.05f, 0.0f);
        glColor3f(1, 0, 0);
        glVertex3f(0.05f, -0.05f, 0.0f);
        glEnd();
	}

	@Override
	protected void coreUpdate(long window, Game game) {
		if (glfwGetKey(window, GLFW_KEY_D) != GLFW_RELEASE) {
			position.x += 0.1f;
		}
		if (glfwGetKey(window, GLFW_KEY_A) != GLFW_RELEASE) {
			position.x -= 0.1f;
		}
		if (glfwGetKey(window, GLFW_KEY_W) != GLFW_RELEASE) {
			position.y += 0.1f;
		}
		if (glfwGetKey(window, GLFW_KEY_S) != GLFW_RELEASE) {
			position.y -= 0.1f;
		}
		if (glfwGetKey(window, GLFW_KEY_Q) != GLFW_RELEASE) {
			setRotation(getRotation() + 1f);
		}
			
		if (glfwGetKey(window, GLFW_KEY_E) != GLFW_RELEASE) {
			setRotation(getRotation() - 1f);
		}
	}
}
