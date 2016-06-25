package core.geom;

import java.util.Arrays;

import org.joml.Vector2f;

public class GeomMain {

	public static void main(String[] args) {
		System.out.println(CollisionDetector.pointLine(new Vector2f(1, 1), new Line(new Vector2f(0, 0), new Vector2f(2, 2))));
		System.out.println(CollisionDetector.pointLine(new Vector2f(1, 2), new Line(new Vector2f(0, 0), new Vector2f(2, 2))));
		System.out.println(CollisionDetector.pointLine(new Vector2f(1, 1), new Line(new Vector2f(0, 0), new Vector2f(0, 2))));
		System.out.println(CollisionDetector.pointLine(new Vector2f(1, 1), new Line(new Vector2f(0, 0), new Vector2f(2, 0))));

		System.out.println(CollisionDetector.pointLine(new Vector2f(1, 1), new Line(new Vector2f(2, 2), new Vector2f(0, 0))));
		System.out.println(CollisionDetector.pointLine(new Vector2f(1, 2), new Line(new Vector2f(2, 2), new Vector2f(0, 0))));
		System.out.println(CollisionDetector.pointLine(new Vector2f(1, 1), new Line(new Vector2f(0, 2), new Vector2f(0, 0))));
		System.out.println(CollisionDetector.pointLine(new Vector2f(1, 1), new Line(new Vector2f(2, 0), new Vector2f(0, 0))));

		System.out.println(CollisionDetector.closestPointLine(new Vector2f(1, 2), Arrays.asList(
				new Line(new Vector2f(0, 0), new Vector2f(2, 2)),
				new Line(new Vector2f(0, 0), new Vector2f(0, 2)),
				new Line(new Vector2f(0, 0), new Vector2f(2, 0))
		)));
	}

}
