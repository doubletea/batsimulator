/*
 * Copyright LWJGL. All rights reserved.
 * License terms: http://lwjgl.org/license.php
 */
package audio;

import org.joml.Vector3f;
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
		
		AudioSource ping = AudioSource.createFromVorbis("assets/sounds/submarineMono.ogg");
		ping.setLooping(true);
		ping.play();
		
		Vector3f pos = new Vector3f();
		float distance = 3000;
		for (int n = 0; n < 1000; n++) {
			int degrees = 15 * n % 360;
			System.out.println(degrees);
			double ang = degrees / 180.0 * Math.PI;
			distance += n;
			pos.x = (float) (distance * Math.sin(ang));
			pos.z = (float) (distance * Math.cos(ang));
			ping.setPosition(pos);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		
		ping.destroy();
	}

	

}