package com.example.triangleexample;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

import com.example.triangleexample.utils.Logit;
import com.example.triangleexample.utils.MatrixState;

public class TriangleRenderer implements Renderer{
	private static final String TAG = "Triangle.Renderer";
	private Triangle triangle;
	private Context  context;
	
//	private float[] mProjectMatrix;
//	
//	private float[] mViewMatrix;
//	
//	private float[] mMVPMatrix;
	
	public TriangleRenderer(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		Logit.d(TAG, "onSurfaceCreated.");
		triangle = new Triangle(context);
		GLES30.glClearColor(1, 1, 1, 1);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		Logit.d(TAG, "onSurfaceChanded...");
		float ratio = (float)width / height;
		Logit.d(TAG, "ratio: " + ratio);
		// 设置透视投影
//		Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f);
//		Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0);
//		 // 计算变换矩阵
//		Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mProjectMatrix, 0);
		MatrixState.setCamera(0, 0, 7, 0, 0, 0, 0, 1, 0);
		MatrixState.setProjectOrtho(-ratio, ratio, -1, 1, 3, 7);
		
//		GLES30.glViewport(0, 0, width, height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		Logit.d(TAG, "onDrawFrame");
		GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
		triangle.draw();
	}

}
