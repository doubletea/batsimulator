package video;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import audio.AudioListener;
import core.Game;
import core.MainMain;

public class VideoBat extends VideoObject{
	private AudioListener audioListener;
	
	public VideoBat(){
		super();
		audioListener = new AudioListener();
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
		
		DoubleBuffer xpos = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer ypos = BufferUtils.createDoubleBuffer(1);
		
		
		glfwGetCursorPos(window, xpos, ypos);
	
		
		double xpos1 = xpos.get();
		double ypos1 = ypos.get();
		
		double angle = Math.atan2(xpos1 - MainMain.WIDTH/2,ypos1 - MainMain.HEIGHT/2);
		setRotation((float)(Math.toDegrees(angle)-180));
		
		
		
		xpos.rewind();
		ypos.rewind();
		
		if (glfwGetKey(window, GLFW_KEY_W) != GLFW_RELEASE) {
			setVelocity(getVelocity().add(new Vector3f(0.02f*(float)Math.sin(angle),-0.02f*(float)Math.cos(angle),0f)));
		}
			
		if (glfwGetKey(window, GLFW_KEY_S) != GLFW_RELEASE) {
			position.x += 0.1f*Math.sin(Math.toRadians(getRotation()-90));
			position.y += 0.1f*Math.cos(Math.toRadians(getRotation()-90));
		}
		
		audioListener.setPosition(position);
		audioListener.setVelocity(velocity);
		double batAng = Math.toRadians(rotation);
		audioListener.setFacing((float)Math.sin(batAng), (float)Math.cos(batAng), 0);
		audioListener.setUp(0, 0, 1);
	}
}
