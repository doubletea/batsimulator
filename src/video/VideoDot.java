package video;

import static org.lwjgl.opengl.GL11.*;

import core.Game;

public class VideoDot extends VideoObject {

	@Override
	protected void coreUpdate(long window, Game game) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void coreRender() {
		glColor3f(1, 0, 0);
		glBegin(GL_POLYGON);
		int sides = 10;
		double len = 0.05;
		for (int n = 0; n < sides; n++) {
			double ang = Math.PI * 2 / sides * n;
			glVertex3f((float) (len * Math.cos(ang)), (float) (len * Math.sin(ang)), 0f);
		}
		glEnd();
	}

}
