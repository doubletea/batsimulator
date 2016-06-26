package video;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector3f;

import core.Game;
import core.geom.Line;

public class VideoWall extends VideoObject{
	private float length;
	private final float width = 0.15f;
	private final float height = 0.8f;
	
	public VideoWall(){
		super();
	}
	
	public VideoWall(Line line){
		super();
		this.position = new Vector3f((line.start.x+line.end.x)/2,(line.start.y+line.end.y)/2, 0.0f);	
		this.rotation = (float)(Math.toDegrees(Math.atan2(line.end.y - line.start.y, line.end.x - line.start.x)));
		this.length = line.start.distance(line.end);
	}
	
	protected void coreRender(){
    	glColor3f(0, 1, 0);
        glBegin(GL_QUADS);
        glVertex3f(length/2, width/2, position.z);
        glVertex3f(length/2, -width/2, position.z);
        glVertex3f(-length/2, -width/2, position.z);
        glVertex3f(-length/2, width/2, position.z);
        glEnd();
        
        glColor3f(1, 0 , 0);
        glBegin(GL_QUADS);
        glVertex3f(length/2, width/2, position.z + height);
        glVertex3f(length/2, -width/2, position.z + height);
        glVertex3f(-length/2, -width/2, position.z + height);
        glVertex3f(-length/2, width/2, position.z + height);
        glEnd();
        
        glColor3f(0, 0, 1);
        glBegin(GL_QUADS);
        glVertex3f(length/2, width/2, position.z + height);
        glVertex3f(length/2, width/2, position.z);
        glVertex3f(-length/2, width/2, position.z);
        glVertex3f(-length/2, width/2, position.z + height);
        glEnd();
        
        glColor3f(0.5f, 1f, 0.6f);
        glBegin(GL_QUADS);
        glVertex3f(length/2, -width/2, position.z + height);
        glVertex3f(length/2, -width/2, position.z);
        glVertex3f(-length/2, -width/2, position.z);
        glVertex3f(-length/2, -width/2, position.z + height);
        glEnd();   
        
        glColor3f(1f, 1f, 0f);
        glBegin(GL_QUADS);
        glVertex3f(length/2, -width/2, position.z + height);
        glVertex3f(length/2, -width/2, position.z);
        glVertex3f(length/2, width/2, position.z);
        glVertex3f(length/2, width/2, position.z + height);
        glEnd();   
        
        glColor3f(0f, 1f, 1f);
        glBegin(GL_QUADS);
        glVertex3f(-length/2, width/2, position.z + height);
        glVertex3f(-length/2, width/2, position.z);
        glVertex3f(-length/2, -width/2, position.z);
        glVertex3f(-length/2, -width/2, position.z + height);
        glEnd();   
	}

	@Override
	protected void coreUpdate(long window, Game game) {
		
	}

}
