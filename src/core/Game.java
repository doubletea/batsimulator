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
import video.VideoHP;
import video.VideoObject;
import video.VideoWall;

public class Game {
	private Camera camera;
	
	private VideoBat bat;
	private long window;
	private VideoHP hp;
	private ArrayList<VideoWall> walls;
	private List<Line> lines;
	private AudioSource screech;
	private AudioSource sonar;
	private VideoDot nearDot;
	private VideoDot straightDot;
	private OpenAL openAL;

	private List<Line> generateRandom() {
		List<Line> list = new ArrayList<Line>();
		float range = 20f;
		float sizeRange = 5f;
		
		for (int i = 0; i < 400; ++i){
			Vector2f start = new Vector2f((float) Math.random() * range, (float) Math.random() * range);
			
			float size = (float) (sizeRange * Math.random() - sizeRange/2);
			
			Vector2f end;
			if (i % 4 == 0){
				end = new Vector2f((float) (start.x), (float) (start.y + size*Math.random()));
			} else if (i % 5 == 0){
				end = new Vector2f((float) (start.x + size*Math.random()), (float) (start.y));
			}
			else {
				end = new Vector2f((float) (start.x + size*Math.random()), (float) (start.y + size*Math.random()));
			}
			list.add(new Line(start.mul(3), end.mul(3)));
		}
		
		return list;
	}
	
	public Game(long window, OpenAL openAL) throws ALException {
		this.window = window;
		
		camera = new Camera();
		
		bat = new VideoBat();

		hp = new VideoHP();
		
		nearDot = new VideoDot(new Vector3f(1f, 1f, 1f));
		straightDot = new VideoDot(new Vector3f(1f, 1f, 0f));

    	
		lines = generateRandom();
		
				/*Arrays.asList(
				// ENTRANCE
				new Line(new Vector2f(-1.8f,-0.8f), new Vector2f(-0.8f,0.8f)),
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
				
		);*/
		
		
				
		walls = new ArrayList<VideoWall>();
		for (Line line:lines) {
			walls.add(new VideoWall(line));
		}
		
		screech = new AudioSource(openAL, "assets\\sounds\\BatSqueaksMono.wav");
		screech.play();
		
		sonar = new AudioSource(openAL, "assets\\sounds\\submarineMono.wav");
		sonar.play();
	}
	
	public VideoBat getVideoBat() {
		return bat;
	}
	
	public void update() throws ALException {
		bat.update(window, this);
		camera.update(window, this);
		hp.update(window, this);
		
		Vector3f batPos = bat.getPosition();
		Vector2f bat2DPos = new Vector2f(batPos.x, batPos.y);
		Intersection inter = CollisionDetector.closestPointLine(bat2DPos, lines);
		
		Vector3f dotPos = nearDot.getPosition();
		dotPos.x = inter.intersection.x;
		dotPos.y = inter.intersection.y;
		double batAng = Math.toRadians(bat.getRotation());
		if (inter.distance < 0.1f){
			float oldX = dotPos.x - batPos.x;
			float oldY = dotPos.y - batPos.y;
			if (inter.distance < 0.05f){
			}
			Vector3f vec = new Vector3f(bat.getVelocity());
			float oldMag = (float) Math.sqrt(oldX * oldX + oldY * oldY);
			float xMag = oldX / oldMag;
			float yMag = oldY / oldMag;
			float dot = vec.dot(xMag, yMag, 0);
			if (dot > 0) {
				bat.setVelocity(bat.getVelocity().sub(xMag * dot * 1.5f, yMag * dot * 1.5f, 0));
			}
			
			hp.getHit();
		}

		determineEffectiveSoundLocaiton(batPos, dotPos, screech);
		
		float maxDis = 5;
		inter = CollisionDetector.closestLineLine(new Line(bat2DPos, bat2DPos.add((float)(maxDis * Math.cos(batAng)), (float)(maxDis * Math.sin(batAng)), new Vector2f())), lines);
		if (inter.distance > maxDis) {
			dotPos = straightDot.getPosition();
			dotPos.x = Float.MAX_VALUE;
			dotPos.y = Float.MAX_VALUE;
			sonar.pause();
		} else {
			dotPos = straightDot.getPosition();
			dotPos.x = inter.intersection.x;
			dotPos.y = inter.intersection.y;
			sonar.play();
			determineEffectiveSoundLocaiton(batPos, dotPos, sonar);

		}
	}
	
	private void determineEffectiveSoundLocaiton(Vector3f batPos, Vector3f soundPos, AudioSource sound) throws ALException {
		float oldX = soundPos.x - batPos.x;
		float oldY = soundPos.y - batPos.y;
		double batAng = Math.toRadians(-bat.getRotation());
		double batCos = Math.cos(batAng);
		double batSin = Math.sin(batAng);
		float newX = (float) (batCos * oldX - batSin * oldY);
		float newZ = (float) (batSin * oldX + batCos * oldY);
		newX = newX > Float.MAX_VALUE ? Float.MAX_VALUE : newX;
		newX = newX < -Float.MAX_VALUE ? -Float.MAX_VALUE : newX;
		newZ = newZ > Float.MAX_VALUE ? Float.MAX_VALUE : newZ;
		newZ = newZ < -Float.MAX_VALUE ? -Float.MAX_VALUE : newZ;
		sound.setPosition(-newZ, 0, -newX);
	}
	
	public void render() throws ALException {
		camera.render();
    	
    	for (VideoWall wall: walls) {
    		wall.render();
    	}
		bat.render();

		hp.render();

		nearDot.render();
		straightDot.render();
	}
	
	public void destroy() throws ALException {
		screech.close();
	}
}
