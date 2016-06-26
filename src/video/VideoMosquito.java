package video;

import static org.lwjgl.opengl.GL11.*;

import core.Game;

public class VideoMosquito extends VideoObject {

	@Override
	protected void coreUpdate(long window, Game game) {
	}

	@Override
	protected void coreRender() {
		glColor3f(0.5f, 0.5f, 0.5f);
		glBegin(GL_QUADS);
		glVertex3f(0.1f, 0.1f, 0);
		glVertex3f(0.1f, -0.1f, 0);
		glVertex3f(-0.1f, -0.1f, 0);
		glVertex3f(-0.1f, 0.1f, 0);
		glEnd();
	}

}
