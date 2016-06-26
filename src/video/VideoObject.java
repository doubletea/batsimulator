package video;


import static org.lwjgl.opengl.GL11.glLoadIdentity;


import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.joml.Vector3f;

public class VideoObject {
	protected Vector3f position;
	protected float rotation;
	protected Vector3f velocity;
	
	protected VideoObject(){
		this.position = new Vector3f();
		rotation = 0.0f;
		this.velocity = new Vector3f();
	}
	
	protected void prerender(){
		glLoadIdentity();
		
		glTranslatef(position.x, position.y, position.z);
		glRotatef(rotation,0.0f,0.0f,1.0f); //  rotate around center
		
		
		
	}
	
}
