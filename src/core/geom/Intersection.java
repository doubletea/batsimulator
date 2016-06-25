package core.geom;

import org.joml.Vector2f;

public class Intersection {
	public Vector2f intersection;
	public float distance;
	
	public Intersection(Vector2f intersection, float distance) {
		this.intersection = intersection;
		this.distance = distance;
	}
	
	public String toString() {
		return intersection.toString() + " " + this.distance;
	}
}
