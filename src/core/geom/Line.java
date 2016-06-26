package core.geom;

import org.joml.Vector2f;

public class Line {
	public Vector2f start;
	public Vector2f end;
	
	private final int multiplier = 3;
	public Line(Vector2f start, Vector2f end) {
		this.start = start.mul(multiplier);
		this.end = end.mul(multiplier);
	}
}
