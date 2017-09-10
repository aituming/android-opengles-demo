package com.example.square;

import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.GLES20.glClear;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

import com.example.square.utils.Logit;

public class SquareRenderer  implements Renderer{
	public static final String TAG = "Renderer";
	
	private Square mSquare;
	
	private Context mContext;
	public SquareRenderer(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		Logit.d(TAG, "onSurfaceCreated.");
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		mSquare = new Square(mContext);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		Logit.d(TAG, "onSurfaceChanged.");
		
		//设置视口大小
		glViewport(0, 0, width, height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		Logit.d(TAG, "onDrawFrame");
		glClear(GLES20.GL_COLOR_BUFFER_BIT);
		mSquare.draw();
	}

}
