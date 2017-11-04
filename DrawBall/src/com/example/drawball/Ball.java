package com.example.drawball;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.example.drawball.utils.BufferHelper;
import com.example.drawball.utils.Logit;
import com.example.drawball.utils.MatrixState;
import com.example.drawball.utils.ShaderHelper;
import com.example.drawball.utils.TextResourceReader;
import com.example.drawcircle.R;

import android.content.Context;
import android.opengl.GLES30;

public class Ball {
	private static final String TAG = "Circle";
	
	private Context context;
	
	private FloatBuffer vertexBuffer;
	private FloatBuffer colorBuffer;
	
	private static final int BYTES_PER_FLOAT = 4; //浮点字节个数
	private static final int COORS_PER_VERTEX = 3; // 顶点坐标分量个数
	private static final int ANGLE_SPAN = 10; // 圆周划分角度10度
	private static final float RADIANS = 0.5f;
	private int numVertex;
	private ArrayList<Float> vertex;
	
	private int program; //应用程序句柄
	
	private static final String A_POSITION = "aPosition";
	private static final String U_COLOR = "uColor";
	private static final String V_MATRIX = "vMatrix";
	
	private int vMatrixLocation;
	
	public Ball(Context context) {
		this.context = context;
		
		initVertex();
		
		vertexBuffer = BufferHelper.getFloatBuffer(vertex);
		
		getProgram();
		
		int aPositionLocation = GLES30.glGetAttribLocation(program, A_POSITION); // 对应于 vertex_shader.glsl 中 attribute属性
		Logit.d(TAG, "aPosition location: " + aPositionLocation);
		GLES30.glVertexAttribPointer(aPositionLocation, COORS_PER_VERTEX, GLES30.GL_FLOAT, false, 0, vertexBuffer); // 区别glVertexAttribIPointer
		GLES30.glEnableVertexAttribArray(aPositionLocation);  //启用顶点属性数组
		
		int uColorLocation = GLES30.glGetUniformLocation(program, U_COLOR);  // 对应与 fragment_shader 中uniform属性
		Logit.d(TAG, "uColor Location: " + uColorLocation);
		GLES30.glUniform4f(uColorLocation, 1, 0, 0, 1); // 设置着色器中变量的值
		
		vMatrixLocation = GLES30.glGetUniformLocation(program, V_MATRIX);
	}
	
	/**
	 * 初始化顶点数组
	 * angleSpan角度进行划分
	 *         point3   point2 (beta + angleSpan)
	 *         point0   point1 (alpha + angleSpan)
	 */
	private void initVertex() {
		vertex = new ArrayList<Float>();
		for (int alpha = 0; alpha <= 360; alpha += ANGLE_SPAN) {
			for (int beta = -90; beta <= 90; beta += ANGLE_SPAN) {
				// 第一个点的坐标
				float x0 = (float) (RADIANS * Math.cos(Math.toRadians(alpha)) * Math.cos(Math.toRadians(beta)));
				float y0 = (float) (RADIANS * Math.sin(Math.toRadians(alpha)) * Math.cos(Math.toRadians(beta)));
				float z0 = (float) (RADIANS * Math.sin(Math.toRadians(beta)));
				
				// 第二个点的坐标
				float x1 = (float) (RADIANS * Math.cos(Math.toRadians(alpha + ANGLE_SPAN)) * Math.cos(Math.toRadians(beta)));
				float y1 = (float) (RADIANS * Math.sin(Math.toRadians(alpha + ANGLE_SPAN)) * Math.cos(Math.toRadians(beta)));
				float z1 = (float) (RADIANS * Math.sin(Math.toRadians(beta)));
				
				// 第三个点的坐标
				float x2 = (float) (RADIANS * Math.cos(Math.toRadians(alpha + ANGLE_SPAN)) * Math.cos(Math.toRadians(beta + ANGLE_SPAN)));
				float y2 = (float) (RADIANS * Math.sin(Math.toRadians(alpha + ANGLE_SPAN)) * Math.cos(Math.toRadians(beta + ANGLE_SPAN)));
				float z2 = (float) (RADIANS * Math.sin(Math.toRadians(beta + ANGLE_SPAN)));
				
				// 第三个点的坐标
				float x3 = (float) (RADIANS * Math.cos(Math.toRadians(alpha)) * Math.cos(Math.toRadians(beta + ANGLE_SPAN)));
				float y3 = (float) (RADIANS * Math.sin(Math.toRadians(alpha)) * Math.cos(Math.toRadians(beta + ANGLE_SPAN)));
				float z3 = (float) (RADIANS * Math.sin(Math.toRadians(beta + ANGLE_SPAN)));
				
				vertex.add(x0);
				vertex.add(y0);
				vertex.add(z0);
				
				vertex.add(x1);
				vertex.add(y1);
				vertex.add(z1);
				
				vertex.add(x2);
				vertex.add(y2);
				vertex.add(z2);
				
				vertex.add(x0);
				vertex.add(y0);
				vertex.add(z0);
				
				vertex.add(x2);
				vertex.add(y2);
				vertex.add(z2);
				
				vertex.add(x3);
				vertex.add(y3);
				vertex.add(z3);
			}
		}
		
		numVertex = vertex.size();
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
		GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, numVertex);
	}
}
