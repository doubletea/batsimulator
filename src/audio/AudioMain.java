/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package audio;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;
import org.lwjgl.stb.STBVorbisInfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.List;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.ALC11.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryUtil.*;

public final class AudioMain {

	private AudioMain() {
	}

	public static void main(String[] args) {
		AudioContext context = AudioContext.createContext();
		try {
			testPlayback();
		} finally {
			context.destroy();
		}
	}

	private static void testPlayback() {
		AudioListener listener = new AudioListener();
		
		// generate buffers and sources
		int buffer = alGenBuffers();
		int source = alGenSources();
		try ( STBVorbisInfo info = STBVorbisInfo.malloc() ) {
			ShortBuffer pcm = IOUtil.readVorbis("assets/sounds/BatSqueaks.ogg", 32 * 1024, info);
			//copy to buffer
			alBufferData(buffer, AL_FORMAT_MONO16, pcm, info.sample_rate());
		}
		//set up source input
		alSourcei(source, AL_BUFFER, buffer);
		//lets loop the sound
		alSourcei(source, AL_LOOPING, AL_TRUE);
		
		

		//play source 0
		alSourcePlay(source);

		//wait 5 secs
		/*try {
			System.out.println("Waiting 5 seconds for sound to complete");
			Thread.sleep(5000);
		} catch (InterruptedException inte) {
		}*/
		
		for (int n = 0; n < 1000; n++) {
			int degrees = 15 * n % 360;
			System.out.println(degrees);
			double ang = degrees / 180.0 * Math.PI;
			float distance = 3;
			float px = (float) (distance * Math.sin(ang));
			float pz = (float) (distance * Math.cos(ang));
			alSource3f(source, AL_POSITION, px, 0, pz);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		
		//stop source 0
		alSourceStop(source);

		//delete buffers and sources
		alDeleteSources(source);
		alDeleteBuffers(buffer);
	}

	

}