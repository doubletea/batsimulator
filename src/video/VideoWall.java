package video;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.joml.Vector3f;

import core.geom.Line;

public class VideoWall extends VideoObject{
	private float length;
	private final float width = 0.05f;
	
	public VideoWall(){
		super();
	}
	
	public VideoWall(Line line){
		super();
		this.position = new Vector3f((line.start.x+line.end.x)/2,(line.start.y+line.end.y)/2, 0.0f);
		this.rotation = (float) Math.atan2(line.start.x-line.end.x,line.start.y-line.end.y);
	}
	
	public void render(){
		super.prerender();
		
		Vector3f pointA = new Vector3f(position.x + (float)(length/2*Math.cos(rotation)), position.y + (float)(length/2*Math.sin(rotation)), position.z);
		
		Vector3f pointC = new Vector3f(pointA.x + (float)(width/2*Math.cos(Math.PI/2-rotation)), pointA.y - (float)(width/2*Math.sin(Math.PI/2-rotation)), position.z);
		
    	glColor3f(0, 1, 0);
    	
        glBegin(GL_QUADS);
        glVertex3f(pointC.x, pointC.y, position.z);
        glVertex3f(pointC.y , pointC.x, position.z);
        glVertex3f(-pointC.x , -pointC.y, position.z);
        glVertex3f(-pointC.y, -pointC.x, position.z);
        glEnd();
	}

}
