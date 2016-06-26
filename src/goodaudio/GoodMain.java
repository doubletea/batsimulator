package goodaudio;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.urish.openal.ALException;
import org.urish.openal.OpenAL;
import org.urish.openal.Source;
import org.urish.openal.jna.AL;


public class GoodMain {
	
	public static void main(String[] args)
			throws ALException, IOException, UnsupportedAudioFileException, InterruptedException {
		OpenAL openal = new OpenAL();
		Source source = openal.createSource(new File("assets\\sounds\\BatSqueaksMono.wav"));
		source.play();
		
		/* Play with various sound parameters */
		source.setGain(0.75f);        // 75% volume
		source.setPitch(0.85f);       // 85% of the original pitch
		source.setPosition(-1, 0, 0); // -1 means 1 unit to the left
		source.setLooping(true);      // Loop the sound effect
		source.setFloatParam(AL.AL_MIN_GAIN, 0);
		source.setFloatParam(AL.AL_MAX_GAIN, 1);
		source.setFloatParam(AL.AL_REFERENCE_DISTANCE, 1);
		source.setFloatParam(AL.AL_ROLLOFF_FACTOR, 1);

		//Thread.sleep(10000);          // Wait for 10 seconds
		
		/*for (int n = 0; n < 100; n++) {
			source.setPosition(50 - n, 0, 0); // -1 means 1 unit to the left
			Thread.sleep(100);
		}
		for (int n = 0; n < 100; n++) {
			source.setPosition(50 + n, 0, 0); // -1 means 1 unit to the left
			Thread.sleep(100);
		}*/
		
		for (int n = 0; n < 1000; n++) {
			double ang = n * Math.PI / 10 ;
			source.setPosition((float)Math.cos(n), 0, (float)Math.sin(n)); // -1 means 1 unit to the left
			Thread.sleep(100);
		}

		/* Cleanup */
		source.close();
		openal.close();
	}
}
