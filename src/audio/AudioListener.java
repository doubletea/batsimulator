package audio;

import static org.lwjgl.openal.AL10.AL_ORIENTATION;
import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_VELOCITY;
import static org.lwjgl.openal.AL10.alListener3f;
import static org.lwjgl.openal.AL10.alListenerfv;

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

		this.setPosition(position);
		this.setVelocity(velocity);
		this.facing = facing;
		this.up = up;
		updateOrientation();
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
		alListener3f(AL_VELOCITY, velocity.x, velocity.y, velocity.z);
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
		alListener3f(AL_POSITION, position.x, position.y, position.z);
	}
	
	public Vector3f getUp() {
		return up;
	}
	
	public void setUp(Vector3f up) {
		this.up=up;
		updateOrientation();
	}
	
	public Vector3f getFacing() {
		return facing;
	}

	public void setFacing(Vector3f facing) {
		this.facing=facing;
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
