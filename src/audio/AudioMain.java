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
		
		AudioSource ping = AudioSource.createFromOrbis("assets/sounds/submarine.ogg");
		ping.setLooping(true);
		ping.play();
		
		float[] pos = new float[3];
		for (int n = 0; n < 1000; n++) {
			int degrees = 15 * n % 360;
			System.out.println(degrees);
			double ang = degrees / 180.0 * Math.PI;
			float distance = 3;
			float px = (float) (distance * Math.sin(ang));
			float pz = (float) (distance * Math.cos(ang));
			pos[0] = px;
			pos[2] = pz;
			ping.setPosition(pos);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		
		ping.destroy();
	}

	

}