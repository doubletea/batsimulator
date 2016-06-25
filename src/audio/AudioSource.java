package audio;

import static org.lwjgl.openal.AL10.*;

import java.nio.ShortBuffer;

import org.lwjgl.stb.STBVorbisInfo;

public class AudioSource {
	private int buffer;
	private int source;
	private boolean looping;
	private float[] position;
	private float[] velocity;

	public AudioSource(int buffer, int source, boolean looping, float[] position, float[] velocity) {
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
		return new AudioSource(alGenBuffers(), alGenSources(), false, new float[] { 0, 0, 0 }, new float[] { 0, 0, 0 });
	}
	
	public void updateBuffer() {
		alSourcei(source, AL_BUFFER, buffer);

	}

	public static AudioSource createFromOrbis(String path) {
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

	public float[] getPosition() {
		return position;
	}

	public void setPosition(float[] position) {
		this.position = position;
		alSource3f(source, AL_POSITION, position[0], position[1], position[2]);
	}

	public float[] getVelocity() {
		return velocity;
	}

	public void setVelocity(float[] velocity) {
		this.velocity = velocity;
		alSource3f(source, AL_VELOCITY, velocity[0], velocity[1], velocity[2]);
	}
}
