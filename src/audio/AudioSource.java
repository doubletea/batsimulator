package audio;

import static org.lwjgl.openal.AL10.*;

import java.nio.ShortBuffer;

import org.joml.Vector3f;
import org.lwjgl.stb.STBVorbisInfo;

public class AudioSource {
	private int buffer;
	private int source;
	private boolean looping;
	private Vector3f position;
	private Vector3f velocity;

	public AudioSource(int buffer, int source, boolean looping, Vector3f position, Vector3f velocity) {
		this.buffer = buffer;
		this.source = source;
		setLooping(looping);
		setPosition(position);
		setVelocity(velocity);
	}

	public boolean getLooping() {
		return looping;
	}

	public void setLooping(boolean looping) {
		this.looping = looping;
		alSourcei(source, AL_LOOPING, looping ? AL_TRUE : AL_FALSE);

	}

	public int getBuffer() {
		return buffer;
	}

	public int getSource() {
		return source;
	}

	public static AudioSource createEmpty() {
		return new AudioSource(alGenBuffers(), alGenSources(), false, new Vector3f(), new Vector3f());
	}
	
	public void updateBuffer() {
		alSourcei(source, AL_BUFFER, buffer);

	}

	public static AudioSource createFromVorbis(String path) {
		AudioSource src = createEmpty();
		try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
			ShortBuffer pcm = IOUtil.readVorbis(path, 32 * 1024, info);
			// copy to buffer
			alBufferData(src.getBuffer(), AL_FORMAT_MONO16, pcm, info.sample_rate());
		}
		src.updateBuffer();
		return src;
	}

	public void play() {
		alSourcePlay(source);
	}

	public void stop() {
		alSourceStop(source);
	}

	public void destroy() {
		stop();
		alDeleteSources(source);
		alDeleteBuffers(buffer);
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
		alSource3f(source, AL_POSITION, position.x, position.y, position.z);
	}

	public Vector3f getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
		alSource3f(source, AL_VELOCITY, velocity.x, velocity.y, velocity.z);
	}
}
