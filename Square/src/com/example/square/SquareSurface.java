package com.example.square;

import com.example.square.utils.Logit;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class SquareSurface extends GLSurfaceView{
	public static final String TAG  = "SquareSurface";
	public SquareSurface(Context context) {
		super(context);
		
		try {
			setEGLContextClientVersion(2);
			setRenderer(new SquareRenderer(context));
			
			setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		} catch (Exception e) {
			Logit.d(TAG, "error" + e);
		}
	}
}
