package core;

import org.joml.Vector2f;

import core.geom.Line;
import video.Camera;
import video.VideoBat;
import video.VideoWall;

public class Game {
	private Camera camera;
	
	private VideoBat bat;
	private long window;
	private VideoWall wall1;
	private VideoWall wall2;
	
	public Game(long window) {
		this.window = window;
		
		camera = new Camera();
		
		bat = new VideoBat();
		
		Line w1 = new Line(new Vector2f(-0.8f,-0.8f), new Vector2f(-0.8f,0.8f));
    	wall1 = new VideoWall(w1);
    	
    	Line w2 = new Line(new Vector2f(0.8f,-0.8f), new Vector2f(0.8f,0.8f));
    	wall2 = new VideoWall(w2);
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
		bat.render();
		
	}
}