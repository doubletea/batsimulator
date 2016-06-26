package video;


import static org.lwjgl.opengl.GL11.glLoadIdentity;


import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;

import core.Game;

public abstract class VideoObject {
	protected Vector3f position;
	protected float rotation;
	protected Vector3f velocity;
	
	protected VideoObject(){
		this.position = new Vector3f();
		setRotation(0.0f);
		this.velocity = new Vector3f();
	}
	
	protected void updatePhysics() {
		getPosition().add(getVelocity());
		getVelocity().mul(0.9f);
	}
	
	public void update(long window, Game game) {
		updatePhysics();
		coreUpdate(window, game);
	}
	
	protected abstract void coreUpdate(long window, Game game);
	
	protected void preRender(){
		if (this instanceof Camera) return;
		
		glPushMatrix();
		glTranslatef(getPosition().x, getPosition().y, getPosition().z);
		glRotatef(getRotation(),0.0f,0.0f,1.0f); //  rotate around center
	}
	
	protected abstract void coreRender();
	
	protected void postRender(){
		glPopMatrix();
		
	}
	
	public void render() {
		preRender();
		coreRender();
		postRender();
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public Vector3f getVelocity() {
		return velocity;
	}
	
}
