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
	
	public static Intersection closestLineLine(Line ray, List<Line> lines) {
		float closestDistance = Float.MAX_VALUE;
		Vector2f closest = new Vector2f();
		Intersection inter = new Intersection(new Vector2f(), 0);
		for (Line line : lines) {
			lineLine(ray, line, inter);
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

	private static boolean horizontalNormalLine(Line horizontal, Line other, float otherSlope, Vector2f inter) {
		inter.x = (horizontal.start.y - other.start.y) / otherSlope + other.start.x;
		inter.y = horizontal.start.y;
		return closestToInterAlongLine(horizontal, inter) && closestToInterAlongLine(other, inter);
	}
	
	private static boolean verticalNormalLine(Line vertical, Line other, float otherSlope, Vector2f inter) {
		inter.y = (vertical.start.x - other.start.x) * otherSlope + other.start.y;
		inter.x = vertical.start.x;
		return closestToInterAlongLine(other, inter) && closestInterAlongVertical(vertical, inter);
	}

	private static boolean horizontalVerticalLine(Line horizontal, Line vertical, Vector2f inter) {
		inter.x = vertical.start.x;
		inter.y = horizontal.start.y;
		return closestToInterAlongLine(horizontal, inter) && closestInterAlongVertical(vertical, inter);
	}

	public static Intersection lineLine(Line ray, Line line, Intersection inter) {
		Vector2f intersect = inter.intersection;
		float lineSlope = (line.end.y - line.start.y) / (line.end.x - line.start.x);
		float raySlope = (ray.end.y - ray.start.y) / (ray.end.x - ray.start.x);
		boolean ok = true;
		if (lineSlope == 0) {
			// horizontal
			if (raySlope == 0) {
				// horizontal
				ok = false;
			} else if (Float.isInfinite(raySlope)) {
				// vertical
				ok = horizontalVerticalLine(line, ray, intersect);
			} else {
				// normal
				ok = horizontalNormalLine(line, ray, raySlope, intersect);
			}
		} else if (Float.isInfinite(lineSlope)) {
			// vertical
			if (raySlope == 0) {
				// horizontal
				ok = horizontalVerticalLine(ray, line, intersect);
			} else if (Float.isInfinite(raySlope)) {
				// vertical
				ok = false;
			} else {
				// normal
				ok = verticalNormalLine(line, ray, raySlope, intersect);
			}
		} else {
			// normal
			if (raySlope == 0) {
				// horizontal
				ok = horizontalNormalLine(line, ray, lineSlope, intersect);
			} else if (Float.isInfinite(raySlope)) {
				// vertical
				ok = verticalNormalLine(ray, line, lineSlope, intersect);
			} else {
				// normal
				float lineInter = line.start.y - lineSlope * line.start.x;
				float rayInter = ray.start.y - raySlope * ray.start.x;
				intersect.x = (rayInter - lineInter) / (lineSlope - raySlope);
				ok = closestToInterAlongLine(ray, intersect) && closestToInterAlongLine(line, intersect);
				intersect.y = rayInter + raySlope * intersect.x;
			}
		}
		if (ok) {
			float dx = ray.start.x - intersect.x;
			float dy = ray.start.y - intersect.y;
			float dis = (float) Math.sqrt(dx * dx + dy * dy);
			inter.distance = dis;
			return inter;
		} else {
			inter.distance = Float.MAX_VALUE;
			return inter;
		}

	}

	public static Intersection pointLine(Vector2f point, Line line, Intersection inter) {
		Vector2f intersect = inter.intersection;
		float slope = (line.end.y - line.start.y) / (line.end.x - line.start.x);
		if (slope == 0) {
			// horizontal line
			intersect.x = point.x;
			intersect.y = line.end.y;
			closestToInterAlongLine(line, intersect);
		} else if (Float.isInfinite(slope)) {
			// vertical line
			intersect.x = line.end.x;
			intersect.y = point.y;
			closestInterAlongVertical(line, intersect);
		} else {
			// normal line
			float perp = -1 / slope;
			float yInterLine = line.start.y - slope * line.start.x;
			float yInterPoint = point.y - perp * point.x;
			intersect.x = (yInterPoint - yInterLine) / (slope - perp);
			closestToInterAlongLine(line, intersect);
			intersect.y = yInterLine + slope * intersect.x;
		}
		float dx = point.x - intersect.x;
		float dy = point.y - intersect.y;
		float dis = (float) Math.sqrt(dx * dx + dy * dy);
		inter.distance = dis;
		return inter;
	}

	private static boolean closestInterAlongVertical(Line line, Vector2f intersect) {
		if (line.end.y > line.start.y) {
			// going up
			if (intersect.y > line.end.y) {
				// too far up
				intersect.y = line.end.y;
			} else if (intersect.y < line.start.y) {
				// too far down
				intersect.y = line.start.y;
			} else {
				// in range
				return true;
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
				return true;
			}
		}
		return false;
	}

	private static boolean closestToInterAlongLine(Line line, Vector2f intersect) {
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
				return true;
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
				return true;
			}
		}
		return false;
	}
}
