package video;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import core.Game;
import core.MainMain;

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
        glVertex3f(0.05f, 0.00f, 0.0f);
        glColor3f(1, 0, 0);
        glVertex3f(-0.05f, 0.05f, 0.0f);
        glEnd();
	}

	@Override
	protected void coreUpdate(long window, Game game) {
		
		DoubleBuffer xpos = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer ypos = BufferUtils.createDoubleBuffer(1);
		
		
		glfwGetCursorPos(window, xpos, ypos);
	
		
		double xpos1 = xpos.get();
		double ypos1 = ypos.get();
		
		double angle = Math.atan2(MainMain.HEIGHT/2 - ypos1, xpos1 - MainMain.WIDTH/2);
		setRotation((float)(Math.toDegrees(angle)));
		
		
		
		xpos.rewind();
		ypos.rewind();
		
		if (glfwGetKey(window, GLFW_KEY_W) != GLFW_RELEASE) {
			setVelocity(getVelocity().add(new Vector3f(0.02f*(float)Math.sin(angle),-0.02f*(float)Math.cos(angle),0f)));
		}
		
		double batAng = Math.toRadians(rotation);
	}
}
