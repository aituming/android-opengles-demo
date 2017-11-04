package com.example.drawball.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

public class TextResourceReader {
	public static String readTextFileFromResource(Context context, int resourceId) {
		StringBuilder body = new StringBuilder();
		InputStream input = null;
		try {
			input = context.getApplicationContext().getResources().openRawResource(resourceId);
			InputStreamReader reader = new InputStreamReader(input);
			BufferedReader bufferReader = new BufferedReader(reader);
			
			String nextLine;
			while((nextLine = bufferReader.readLine()) != null) {
				body.append(nextLine);
				body.append("\n");
			}
		} catch (IOException e) {
			throw new RuntimeException("can't read reourseId.");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}
		
		return body.toString();
	}
	
}
