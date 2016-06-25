package audio;

import static org.lwjgl.openal.AL10.AL_ORIENTATION;
import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_VELOCITY;
import static org.lwjgl.openal.AL10.alListener3f;
import static org.lwjgl.openal.AL10.alListenerfv;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class AudioListener {
	private float[] velocity;
	private float[] position;
	private float[] facing;
	private float[] up;
	private FloatBuffer orientation;

	public AudioListener() {
		this(new float[] { 0, 0, 0 }, new float[] { 0, 0, 0 }, new float[] { 0, 0, -1}, new float[]{0, 1, 0 });
	}

	public AudioListener(float[] position, float[] velocity, float[] facing, float[] up) {
		orientation = BufferUtils.createFloatBuffer(6);

		this.setPosition(position);
		this.setVelocity(velocity);
		this.facing = facing;
		this.up = up;
		updateOrientation();
	}

	public float[] getVelocity() {
		return velocity;
	}

	public void setVelocity(float[] velocity) {
		this.velocity = velocity;
		alListener3f(AL_VELOCITY, velocity[0], velocity[1], velocity[2]);
	}

	public float[] getPosition() {
		return position;
	}

	public void setPosition(float[] position) {
		this.position = position;
		alListener3f(AL_POSITION, position[0], position[1], position[2]);
	}
	
	public float[] getUp() {
		return up;
	}
	
	public void setUp(float[] up) {
		this.up=up;
		updateOrientation();
	}
	
	public float[] getFacing() {
		return facing;
	}

	public void setFacing(float[] facing) {
		this.facing=facing;
		updateOrientation();
	}
	
	private void updateOrientation() {
		orientation.rewind();
		orientation.put(facing).put(up).rewind();
		alListenerfv(AL_ORIENTATION, orientation);
	}
}
