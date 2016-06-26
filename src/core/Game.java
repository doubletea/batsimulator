package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.urish.openal.ALException;
import org.urish.openal.OpenAL;

import static org.lwjgl.openal.AL10.*;
import core.geom.CollisionDetector;
import core.geom.Intersection;
import core.geom.Line;
import goodaudio.AudioSource;
import video.Camera;
import video.VideoBat;
import video.VideoDot;
import video.VideoObject;
import video.VideoWall;

public class Game {
	private Camera camera;
	
	private VideoBat bat;
	private long window;
	private VideoWall wall1;
	private VideoWall wall2;
	private ArrayList<VideoWall> walls;
	private List<Line> lines;
	private AudioSource ping;
	private VideoDot dot;
	private OpenAL openAL;

	
	public Game(long window, OpenAL openAL) throws ALException {
		this.window = window;
		
		camera = new Camera();
		
		bat = new VideoBat();
		dot = new VideoDot();
    	
		lines = Arrays.asList(
				// ENTRANCE
				new Line(new Vector2f(-0.8f,-0.8f), new Vector2f(-0.8f,0.8f)),
				new Line(new Vector2f(0.4f,0.8f), new Vector2f(0.8f,-0.8f)),
				new Line(new Vector2f(-0.6f,-0.8f), new Vector2f(0.8f,-0.8f)),
				new Line(new Vector2f(-0.8f,0.8f), new Vector2f(-1.2f,1.2f)),
				new Line(new Vector2f(0.8f,1.2f), new Vector2f(0.4f,0.8f)),
				
				// FIRST BLOCK
				new Line(new Vector2f(0.4f,2.2f), new Vector2f(-0.4f,2.2f)),
				new Line(new Vector2f(0.4f,2.2f), new Vector2f(0.4f,1.8f)),
				new Line(new Vector2f(-0.4f,2.2f), new Vector2f(-0.4f,1.8f)),
				new Line(new Vector2f(0.4f,1.8f), new Vector2f(-0.4f,1.8f)),
				
				// LEFT HALL
				new Line(new Vector2f(-1.2f,1.2f), new Vector2f(-1.2f,2.8f)),
				
				// RIGHT HALL
				new Line(new Vector2f(0.8f,1.2f), new Vector2f(2.4f,1.2f)),
				new Line(new Vector2f(1.6f,1.2f), new Vector2f(1.6f,2.0f))
				
				
				
				//new Line(new Vector2f(-0.4f,2.2f), new Vector2f(0.4f,2.2f))
				
		);
		walls = new ArrayList<VideoWall>();
		for (Line line:lines) {
			walls.add(new VideoWall(line));
		}
		
		ping = new AudioSource(openAL, "assets\\sounds\\BatSqueaksMono.wav");
		ping.play();
	}
	
	public VideoBat getVideoBat() {
		return bat;
	}
	
	public void update() throws ALException {
		bat.update(window, this);
		camera.update(window, this);
		
		Vector3f batPos = bat.getPosition();
		Vector2f bat2DPos = new Vector2f(batPos.x, batPos.y);
		Intersection inter = CollisionDetector.closestPointLine(bat2DPos, lines);
		
		Vector3f dotPos = dot.getPosition();
		dotPos.x = inter.intersection.x;
		dotPos.y = inter.intersection.y;
		float oldX = batPos.x - dotPos.x;
		float oldY = batPos.y - dotPos.y;
		
		if (inter.distance < 0.1f){
			Vector3f vec = new Vector3f(bat.getVelocity());
			float oldMag = (float) Math.sqrt(oldX * oldX + oldY * oldY);
			float xMag = oldX / oldMag;
			float yMag = oldY / oldMag;
			float dot = vec.dot(xMag, yMag, 0);
			if (dot < 0) {
				bat.setVelocity(bat.getVelocity().sub(xMag * dot, yMag * dot, 0));
			}
		}
		
		
		double batAng = Math.toRadians(bat.getRotation());
		double batCos = Math.cos(batAng);
		double batSin = Math.sin(batAng);
		float newX = (float) (batCos * oldX - batSin * oldY);
		float newZ = (float) (batSin * oldX + batCos * oldY);
		ping.setPosition(newX, 0, newZ);
	}
	
	public void render() throws ALException {
		camera.render();
    	
    	for (VideoWall wall: walls) {
    		wall.render();
    	}
		bat.render();
		dot.render();
	}
	
	public void destroy() throws ALException {
		ping.close();
	}
}
