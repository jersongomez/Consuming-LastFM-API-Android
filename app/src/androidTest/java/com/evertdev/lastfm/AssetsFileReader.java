package com.evertdev.lastfm;

import android.support.test.InstrumentationRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Evert Dev on 1317/2018.
 * Helper class that reads a file in the test assets directory into a string
 */

public class AssetsFileReader {
    public static String readFileAsString(String fileName) throws IOException {
        InputStream inputStream = InstrumentationRegistry.getContext().getAssets().open(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }
}