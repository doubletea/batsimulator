package core.geom;

import java.util.List;

import org.joml.Vector2f;

public class CollisionDetector {
	public static Intersection closestPointLine(Vector2f point, List<Line> lines) {
		float closestDistance = Float.MAX_VALUE;
		Vector2f closest = new Vector2f();
		Intersection inter = new Intersection(new Vector2f(), 0);
		for (Line line : lines) {
			pointLine(point, line, inter);
			if (inter.distance < closestDistance) {
				closestDistance = inter.distance;
				closest.set(inter.intersection);
			}
		}
		inter.distance = closestDistance;
		
		inter.intersection.set(closest);
		return inter;
	}
	
	public static Intersection pointLine(Vector2f point, Line line) {
		return pointLine(point, line, new Intersection(new Vector2f(), 0));
	}

	public static Intersection pointLine(Vector2f point, Line line, Intersection inter) {
		Vector2f intersect = inter.intersection;
		float slope = (line.end.y - line.start.y) / (line.end.x - line.start.x);
		if (slope == 0) {
			// horizontal line
			intersect.x = point.x;
			intersect.y = line.end.y;
			findClosestToIntersectAlongLine(line, intersect);
		} else if (Float.isInfinite(slope)) {
			// vertical line
			intersect.x = line.end.x;
			intersect.y = point.y;
			if (line.end.y > line.start.y) {
				// going up
				if (intersect.y > line.end.y) {
					// too far up
					intersect.y = line.end.y;
				} else if (point.y < line.start.y) {
					// too far down
					intersect.y = line.start.y;
				} else {
					// in range
				}
			} else {
				// going down
				if (intersect.y < line.end.y) {
					// too far down
					intersect.y = line.end.y;
				} else if (intersect.y > line.start.y) {
					// too far up
					intersect.y = line.start.y;
				} else {
					// in range
				}
			}
		} else {
			// normal line
			float yInterLine = line.start.y - slope * line.start.x;
			float perp = -1 / slope;
			float yInterPoint = point.y - perp * point.x;
			intersect.x = (yInterPoint - yInterLine) / (slope - perp);
			findClosestToIntersectAlongLine(line, intersect);
			intersect.y = yInterLine + slope * intersect.x;
		}
		float dx = point.x - intersect.x;
		float dy = point.y - intersect.y;
		float dis = (float) Math.sqrt(dx * dx + dy * dy);
		inter.distance = dis;
		return inter;
	}

	private static void findClosestToIntersectAlongLine(Line line, Vector2f intersect) {
		if (line.end.x > line.start.x) {
			// going right
			if (intersect.x > line.end.x) {
				// too far right
				intersect.x = line.end.x;
			} else if (intersect.x < line.start.x) {
				// too far left
				intersect.x = line.start.x;
			} else {
				// in range
			}
		} else {
			// going left
			if (intersect.x < line.end.x) {
				// too far left
				intersect.x = line.end.x;
			} else if (intersect.x > line.start.x) {
				// too far right
				intersect.x = line.start.x;
			} else {
				// in range
			}
		}
	}
}
