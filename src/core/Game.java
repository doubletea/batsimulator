package core;

import org.joml.Vector2f;

import core.geom.Line;
import video.VideoBat;
import video.VideoWall;

public class Game {
	private VideoBat bat;
	private long window;
	private VideoWall wall1;
	
	public Game(long window) {
		this.window = window;
		bat = new VideoBat();
		
		Line w1 = new Line(new Vector2f(-0.8f,-0.8f), new Vector2f(-0.8f,0.8f));
    	
    	wall1 = new VideoWall(w1);
	}
	
	public VideoBat getVideoBat() {
		return bat;
	}
	
	public void update() {
		bat.update(window, this);
	}
	
	public void render() {
    	wall1.render();
		bat.render();
		
	}
}
