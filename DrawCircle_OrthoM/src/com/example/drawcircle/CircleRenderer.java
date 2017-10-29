package com.example.drawcircle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView.Renderer;

import com.example.drawcircle.utils.Logit;
import com.example.drawcircle.utils.MatrixState;

public class CircleRenderer implements Renderer{
	private static final String TAG = "CircleRenderer";
	private Circle triangle;
	private Context  context;
	public CircleRenderer(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		Logit.d(TAG, "onSurfaceCreated.");
		triangle = new Circle(context);
		GLES30.glClearColor(1, 1, 1, 1);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		Logit.d(TAG, "onSurfaceChanded...");
//		GLES30.glViewport(0, 0, width, height);
		MatrixState.setCamera(0, 0, 7, 0, 0, 0, 0, 1, 0);
		
		float ratio = width * 1.0f / height;
		MatrixState.setProjectOrtho(-ratio, ratio, -1, 1, 2, 7);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		Logit.d(TAG, "onDrawFrame");
		GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
		triangle.draw();
	}

}
