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

	public static void main(String args[])
	{
        int i = 0;
        if(args[i].equals("-mvp"))
        {
            MVPTree mvp = new MVPTree("mvp");
            MHImageHash[] hashes = getMHImageHashes(args[1]);
            boolean result = mvp.create(hashes);
            if(result)
            {
                System.out.println("Successfully created MVP tree");
                Hash[] results = mvp.query(hashes[0], 100, 20, 30);
                if(results != null && results.length > 0)
                {
                    System.out.println("Query found " + results.length + " results");
                    for(int j = 0; j < results.length; ++j)
                        System.out.println("File: " + results[j].getFilename());
                }

                MHImageHash[] newHashes = getMHImageHashes(args[2]);

                boolean added = mvp.add(newHashes);
                if(added)
                {
                    System.out.println("Hashes added successfully.");
                    Hash[] foundHashes = mvp.query(newHashes[0], 100, 20, 30);
                    if(foundHashes != null && foundHashes.length > 0)
                    {
                        System.out.println("Found newly added hash.");
                    }
                }
            } else
                System.out.println("Creating tree failed");
        }
        else if(args[i].equals("-dct"))
        {
            DCTImageHash imHash = dctImageHash(args[1]);
            DCTImageHash imHash2 = dctImageHash(args[2]);
            System.out.println("File 1: " + imHash.getFilename());
            System.out.println("File 2: " + imHash2.getFilename());

            System.out.println(imageDistance(imHash,imHash2));
        }
        else if(args[i].equals("-mh"))
        {
            MHImageHash imHash = mhImageHash(args[1]);
            MHImageHash imHash2 = mhImageHash(args[2]);
            System.out.println("File 1: " + imHash.getFilename());
            System.out.println("File 2: " + imHash2.getFilename());

            System.out.println(imageDistance(imHash,imHash2));

        }
        else if(args[i].equals("-t"))
        {
            TextHash txtHash = textHash(args[1]);
            TextHash txtHash2 = textHash(args[2]);
            System.out.println(textDistance(txtHash,txtHash2));
        }

        pHash.cleanup();
	}
}
