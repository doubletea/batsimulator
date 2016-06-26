package goodaudio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.joml.Vector3f;
import org.urish.openal.ALException;
import org.urish.openal.OpenAL;
import org.urish.openal.Source;
import org.urish.openal.jna.AL;

public class AudioSource {
	private Source source;
	private Vector3f positon;
	private boolean playing;
	public AudioSource(OpenAL openAL, String path) throws ALException {
		source = null;
		try {
			source = openAL.createSource(new File(path));
		} catch (IOException | UnsupportedAudioFileException e) {
			throw new ALException(e);
		}
	
		/* Play with various sound parameters */
		source.setGain(0.75f);        // 75% volume
		source.setPitch(0.85f);       // 85% of the original pitch
		positon = new Vector3f();
		source.setPosition(0, 0, 0); // -1 means 1 unit to the left
		source.setLooping(true);      // Loop the sound effect
		source.setFloatParam(AL.AL_MIN_GAIN, 0);
		source.setFloatParam(AL.AL_MAX_GAIN, 1);
		source.setFloatParam(AL.AL_REFERENCE_DISTANCE, 0.1f);
		source.setFloatParam(AL.AL_ROLLOFF_FACTOR, 1);
		playing = false;
	}
	
	public void setPosition(Vector3f position) throws ALException {
		this.positon.set(position);
		source.setPosition(position.x, position.y, position.z); // -1 means 1 unit to the left
	}
	
	public void setPosition(float x, float y, float z) throws ALException {
		this.positon.set(x, y, z);
		source.setPosition(x, y, z); // -1 means 1 unit to the left
	}
	
	public void play() throws ALException {
		if (!playing) {
			source.play();
		}
	}
	
	public void close() throws ALException {
		source.stop();
		source.close();
	}

	public void pause() throws ALException {
		if (playing) {
			playing = false;
			source.pause();
		}
	}
}
