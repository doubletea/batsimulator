package audio;

import static org.lwjgl.openal.AL10.*;

import java.nio.FloatBuffer;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

public class AudioListener {
	private Vector3f velocity;
	private Vector3f position;
	private Vector3f facing;
	private Vector3f up;
	private FloatBuffer orientation;

	public AudioListener() {
		this(new Vector3f(), new Vector3f(), new Vector3f(0, 0, -1), new Vector3f(0, 1, 0 ));
	}

	public AudioListener(Vector3f position, Vector3f velocity, Vector3f facing, Vector3f up) {
		orientation = BufferUtils.createFloatBuffer(6);

		this.position = position;
		this.velocity = velocity;
		this.facing = facing;
		this.up = up;
		updatePosition();
		updateVelocity();
		updateOrientation();
	    alListenerf(AL_GAIN, 0.5f);
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
		updateVelocity();
	}
	
	public void setVelocity(float x, float y, float z) {
		velocity.set(x, y, z);
		updateVelocity();
	}

	private void updateVelocity() {
		alListener3f(AL_VELOCITY, velocity.x, velocity.y, velocity.z);
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
		updatePosition();
	}

	public void setPosition(float x, float y, float z) {
		position.set(x, y, z);
		updatePosition();
	}
	
	private void updatePosition() {
		System.out.println(position.x + " " + position.y + " " + position.z);
		alListener3f(AL_POSITION, position.x, position.y, position.z);
	}
	
	public Vector3f getUp() {
		return up;
	}
	
	public void setUp(Vector3f up) {
		this.up.set(up);
		updateOrientation();
	}
	
	public void setUp(float x, float y, float z) {
		up.set(x, y, z);
		updateOrientation();
	}

	public Vector3f getFacing() {
		return facing;
	}

	public void setFacing(Vector3f facing) {
		this.facing=facing;
		updateOrientation();
	}
	
	public void setFacing(float x, float y, float z) {
		facing.set(x, y, z);
		updateOrientation();
	}
	
	private void updateOrientation() {
		orientation.rewind();
		facing.get(orientation);
		up.get(orientation);
		orientation.rewind();
		alListenerfv(AL_ORIENTATION, orientation);
	}
}
