package video;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.joml.Vector3f;

import core.Game;
import core.MainMain;

public class VideoHP extends VideoObject{
	private final float HPBase = 100f;
	private float HP;
	

	public VideoHP(){
		super();
		this.position = new Vector3f(0f,-3f,0f);
		HP = HPBase;
	}
	@Override
	protected void coreUpdate(long window, Game game) {
		Vector3f batPos = game.getVideoBat().getPosition();
		position.x = batPos.x;
		position.y = batPos.y;
	}

	@Override
	protected void coreRender() {
    	glColor3f(0.3f, 0.5f, 0.8f);
        glBegin(GL_QUADS);
        glVertex3f(2f * HP/HPBase, 0.1f, 0f);
        glVertex3f(2f * HP/HPBase, -0.1f, 0f);
        glVertex3f(-2f * HP/HPBase, -0.1f, 0f);
        glVertex3f(-2f * HP/HPBase, 0.1f, 0f);
        glEnd();
		
	}
	public void getHit() {
		this.HP -= 5;
		
		if (this.HP <= 0){
			MainMain.running = false;
		}
	}

}
