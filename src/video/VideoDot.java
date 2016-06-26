package video;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;

import core.Game;

public class VideoDot extends VideoObject {
	private final float height = 0.81f;
	private Vector3f color;

	public VideoDot(Vector3f color) {
		this.color = color;
	}
	
	@Override
	protected void coreUpdate(long window, Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void coreRender() {
		glColor3f(color.x, color.y, color.z);
		glBegin(GL_POLYGON);
		int sides = 10;
		double len = 0.05;
		for (int n = 0; n < sides; n++) {
			double ang = Math.PI * 2 / sides * n;
			glVertex3f((float) (len * Math.cos(ang)), (float) (len * Math.sin(ang)), height);
		}
		glEnd();
	}

}
