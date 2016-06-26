package video;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glLoadMatrixf;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import core.Game;

public class Camera extends VideoObject{
	
	
	public Camera(){
		super();
		this.position = new Vector3f(0f, 0f, -1.20f);
		this.rotation = 0f;
	}
	
	private Matrix4f glhPerspectivef2(float fovyInDegrees, float aspectRatio,
            float znear, float zfar)
	{
		float ymax, xmax;
		float temp, temp2, temp3, temp4;
		ymax = (float) (znear * Math.tan(fovyInDegrees * Math.PI / 360.0));
		//ymin = -ymax;
		//xmin = -ymax * aspectRatio;
		xmax = ymax * aspectRatio;
		return glhFrustumf2(-xmax, xmax, -ymax, ymax, znear, zfar);
	}
	
	private Matrix4f glhFrustumf2(float left, float right, float bottom, float top,
            float znear, float zfar)
	{
		Matrix4f matrix= new Matrix4f();
		
		float temp, temp2, temp3, temp4;
		temp = (float) (2.0 * znear);
		temp2 = right - left;
		temp3 = top - bottom;
		temp4 = zfar - znear;
		matrix.m00 = temp / temp2;
		matrix.m10 = 0.0f;
		matrix.m20 = 0.0f;
		matrix.m30 = 0.0f;
		matrix.m01 = 0.0f;
		matrix.m11 = temp / temp3;
		matrix.m21 = 0.0f;
		matrix.m31 = 0.0f;
		matrix.m02 = (right + left) / temp2;
		matrix.m12 = (top + bottom) / temp3;
		matrix.m22 = (-zfar - znear) / temp4;
		matrix.m32 = -1.0f;
		matrix.m03 = 0.0f;
		matrix.m13 = 0.0f;
		matrix.m23 = (-temp * zfar) / temp4;
		matrix.m33 = 0.0f;
		
		return matrix;
	}
	

	@Override
	protected void coreUpdate(long window, Game game) {
		if (glfwGetKey(window, GLFW_KEY_UP) != GLFW_RELEASE) {
			position.x -= 0.1f*Math.sin(Math.toRadians(getRotation()));
			position.y -= 0.1f*Math.cos(Math.toRadians(getRotation()));
		}
		if (glfwGetKey(window, GLFW_KEY_DOWN) != GLFW_RELEASE) {
			position.x += 0.1f*Math.sin(Math.toRadians(getRotation()));
			position.y += 0.1f*Math.cos(Math.toRadians(getRotation()));
		}
		if (glfwGetKey(window, GLFW_KEY_LEFT) != GLFW_RELEASE) {
			setRotation(getRotation() - 1f);
		}
			
		if (glfwGetKey(window, GLFW_KEY_RIGHT) != GLFW_RELEASE) {
			setRotation(getRotation() + 1f);
		}
	}

	@Override
	protected void coreRender() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		Matrix4f projectionMatrix = glhPerspectivef2(80, 1.777f, 1.0f, 20f);
		
		FloatBuffer buff = BufferUtils.createFloatBuffer(16);
		projectionMatrix.get(buff);
		buff.rewind();
		glLoadMatrixf(buff);
		
		glRotatef(getRotation(), 0, 0, 1);
	
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glTranslatef(getPosition().x, getPosition().y, getPosition().z);
        
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
}