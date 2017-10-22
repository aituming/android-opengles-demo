package com.example.triangleexample;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.example.triangleexample.utils.Logit;
import com.example.triangleexample.utils.MatrixState;
import com.example.triangleexample.utils.ShaderHelper;
import com.example.triangleexample.utils.TextResourceReader;

import android.content.Context;
import android.opengl.GLES30;

public class Triangle {
	private static final String TAG = "Triangle";
	
	private Context context;
	
	private FloatBuffer vertexBuffer;
	
	private static final int BYTES_PER_FLOAT = 4; //浮点字节个数
	private static final int COORS_PER_VERTEX = 2; // 顶点坐标分量个数
	private static final int NUM_VERTEXS = 3;
	
	private static final float[] vertex = {
		0, 0,
		0.5f, 0,
		0, 0.5f
	};
	
	private int program; //应用程序句柄
	
	private static final String A_POSITION = "aPosition";
	private static final String U_COLOR = "uColor";
	private static final String V_MATRIX = "vMatrix";
	
	int vMatrixLocation;
	
	public Triangle(Context context) {
		this.context = context;
		
		vertexBuffer = ByteBuffer.allocateDirect(vertex.length * BYTES_PER_FLOAT). // 注意不要写成 allocate
				order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexBuffer.put(vertex);
		vertexBuffer.position(0);
		
		getProgram();
		
		int aPositionLocation = GLES30.glGetAttribLocation(program, A_POSITION); // 对应于 vertex_shader.glsl 中 attribute属性
		Logit.d(TAG, "aPosition location: " + aPositionLocation);
		GLES30.glVertexAttribPointer(aPositionLocation, 2, GLES30.GL_FLOAT, false, 0, vertexBuffer); // 区别glVertexAttribIPointer
		GLES30.glEnableVertexAttribArray(aPositionLocation);  //启用顶点属性数组
		
		int uColorLocation = GLES30.glGetUniformLocation(program, U_COLOR);  // 对应与 fragment_shader 中uniform属性
		Logit.d(TAG, "uColor Location: " + uColorLocation);
		
		vMatrixLocation = GLES30.glGetUniformLocation(program, V_MATRIX);
		
		Logit.d(TAG, "vMatrixLocation: " + vMatrixLocation);
		
		GLES30.glUniform4f(uColorLocation, 1, 0, 0, 1); // 设置着色器中变量的值
	}
	
	private void getProgram() {
		String vertexSource = TextResourceReader.readTextFileFromResource(context, R.raw.vertex_shader);
		String fragmentSource = TextResourceReader.readTextFileFromResource(context, R.raw.fragment_shader);
		
//		int vertexShader = ShaderHelper.compileVertexShader(vertexSource);
//		int fragmentShader = ShaderHelper.compileFragmentShader(fragmentSource);
		
		//获取program的id  
        program  = ShaderHelper.buildProgram(vertexSource, fragmentSource);  
        Logit.d(TAG, "program: " + program);
		GLES30.glUseProgram(program); // 不要忘记设置，否则不会起作用
	}
	
	public void draw() {
//		GLES30.glUniform4f(2, 1, 0, 1, 0);
//		GLES30.glDrawElements(GLES30.GL_TRIANGLES, 3, GLES30.GL_FLOAT, vertexBuffer);
		GLES30.glUniformMatrix4fv(vMatrixLocation, 1, false, MatrixState.getFinalMatrix(), 0);
		GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, NUM_VERTEXS);
	}
}
