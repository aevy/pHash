package org.pHash;

import java.lang.*;
import java.io.*;

public class pHash
{

	public native static DCTImageHash dctImageHash(String file);
	public native static MHImageHash mhImageHash(String file);
	public native static TextHash textHash(String file);
	public native static double imageDistance(ImageHash hash1, ImageHash hash2);
	public native static int textDistance(TextHash txtHash1, TextHash txtHash2);
	private native static void pHashInit();
	private native static void cleanup();

	public static MHImageHash[] getMHImageHashes(String d)
	{
		File dir = new File(d);
		MHImageHash[] hashes = null;
		if(dir.isDirectory())
		{
			File[] files = dir.listFiles();
			hashes = new MHImageHash[files.length];
			for(int i = 0; i < files.length; ++i)
			{
				MHImageHash mh = mhImageHash(files[i].toString());
				if(mh != null)
					hashes[i] = mh;
			}

		}
		return hashes;

	}

	public static void loadLibrary()
	{

		try {
			System.loadLibrary("pHash-jni");
			pHashInit();
		} catch (Throwable t) {
			System.out.format("pHash-jni failed to load: %s \n", t.getMessage());
			throw t;
		}

	}

}
