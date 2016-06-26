package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import static org.lwjgl.openal.AL10.*;
import audio.AudioListener;
import audio.AudioSource;
import core.geom.CollisionDetector;
import core.geom.Intersection;
import core.geom.Line;
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

	
	public Game(long window) {
		this.window = window;
		
		camera = new Camera();
		
		bat = new VideoBat();
		dot = new VideoDot();
		
		Line w1 = new Line(new Vector2f(-0.8f,-0.8f), new Vector2f(-0.8f,0.8f));
    	wall1 = new VideoWall(w1);
    	
    	Line w2 = new Line(new Vector2f(0.8f,-0.8f), new Vector2f(0.8f,0.8f));
    	wall2 = new VideoWall(w2);
		lines = Arrays.asList(
				new Line(new Vector2f(-0.8f,-0.8f), new Vector2f(-0.8f,0.8f)),
				new Line(new Vector2f(0.4f,0.8f), new Vector2f(0.8f,-0.8f)),
				new Line(new Vector2f(-0.6f,-0.8f), new Vector2f(0.8f,-0.8f))
		);
		walls = new ArrayList<VideoWall>();
		for (Line line:lines) {
			walls.add(new VideoWall(line));
		}
		
		ping = AudioSource.createFromVorbis("assets/sounds/submarine.ogg");
		ping.setLooping(true);
		ping.play();
	}
	
	public VideoBat getVideoBat() {
		return bat;
	}
	
	public void update() {
		bat.update(window, this);
		camera.update(window, this);
	}
	
	public void render() {
		camera.render();
		
    	wall1.render();
    	wall2.render();
    	
    	for (VideoWall wall: walls) {
    		wall.render();
    	}
		bat.render();
		dot.render();
		
		Vector3f batPos = bat.getPosition();
		Vector2f bat2DPos = new Vector2f(batPos.x, batPos.y);
		Intersection inter = CollisionDetector.closestPointLine(bat2DPos, lines);
		Vector3f dotPos = dot.getPosition();
		dotPos.x = inter.intersection.x;
		dotPos.y = inter.intersection.y;
		dotPos.z = batPos.z;
		ping.setPosition(dotPos);
	}
	
	public void destroy() {
		ping.destroy();
	}
}
