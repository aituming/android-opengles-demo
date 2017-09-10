package com.example.triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;  

import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glViewport;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;

public class MyRender implements Renderer{
	public static final String TAG = "Triangle.MyRender";
	
	private Context mContext;
	
	Triangle mTriangle;  

	public MyRender(Context context) {
		mContext = context;
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Logit.d(TAG, "onSurfaceCreated.");
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		mTriangle = new Triangle(mContext);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		Logit.d(TAG, "onSurfaceChanded...");
		glViewport(0, 0, width, height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		Logit.d(TAG, "onDrawFrame");
		glClear(GL_COLOR_BUFFER_BIT); 
		mTriangle.draw();
	}

}
