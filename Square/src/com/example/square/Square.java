package com.example.square;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.example.square.utils.ShaderHelper;
import com.example.square.utils.TextResourceReader;

import android.content.Context;
import android.opengl.GLES20;

public class Square {
	
	// 浮点数每个数字节数
	public static final int BYTES_PER_FLOAT = 4;
	//数组中每个顶点的坐标维数
	public static final int COORDS_PER_VERTEX = 2;
	
	// 顶点坐标
	public static final float[] squareCoords = {
		- 0.5f,  0.5f,
		  0.5f,  0.5f,
		  0.5f, -0.5f,
		- 0.5f, -0.5f
	};
	
	private FloatBuffer vertexBuffer;
	
	//------------第一个是顶点着色器的变量名，第二个是片段着色器的变量名  
    private static final String A_POSITION = "a_Position";  
    private static final String U_COLOR = "u_Color";  
    
    //------------获得program的ID的含义类似的  
    private int uColorLocation;  
    private int aPositionLocation;
    
    private int program;//保存program的id  
    
    /*------------------第二步: 修改顶点个数-------------------------*/  
    private static final int POSITION_COMPONENT_COUNT = 4;  
	
	private Context mContext;
	private int mProgram;
	public Square(Context context) {
		mContext = context;
		
		//创建字节缓冲，
		vertexBuffer = ByteBuffer.allocateDirect(squareCoords.length * BYTES_PER_FLOAT)
				// 并设置字节顺序未当前平台顺序
				.order(ByteOrder.nativeOrder())
				//返回浮点数缓冲
				.asFloatBuffer();
		
		// 将坐标数组写入浮点缓冲
		vertexBuffer.put(squareCoords);
		//设置缓冲从0开始
		vertexBuffer.position(0);
		
		getProgram();
		
		// 返回 由 U_COLOR指定的统一变量的位置
		uColorLocation = GLES20.glGetUniformLocation(mProgram, U_COLOR);
		// 获取顶点着色器相应数据index
		aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION);
		
		GLES20.glVertexAttribPointer(aPositionLocation, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, vertexBuffer);
		GLES20.glEnableVertexAttribArray(aPositionLocation);
	}
	
	
	
	public void getProgram() {
		String vertexShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_vertex_shader);
		String fragmentShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_fragment_shader);
		mProgram = ShaderHelper.buildProgram(vertexShaderSource, fragmentShaderSource);
		GLES20.glUseProgram(mProgram);
	}
	
	public void draw() {
		GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 0f);
		GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, POSITION_COMPONENT_COUNT);
	}
}
