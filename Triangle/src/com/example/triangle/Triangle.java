package com.example.triangle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


import android.content.Context;
import android.opengl.GLES20;

public class Triangle {
	private Context mContext;
	
	public static final int BYTES_PER_FLOAT = 4;
	static final int COORDS_PER_VERTEX = 2;
	
	//---------第四步:定义坐标元素的个数，这里有三个顶点  
    private static final int POSITION_COMPONENT_COUNT = 3;
    
	private FloatBuffer vertexBuffer;
	/**
	 * 该类中定义的float类型的数据并不能直接被opengl使用，float[ ]是属于虚拟机环境的，而Opengl作为本地系统库直接运行在硬件上，所以我们需要将float[ ] 转化为
	 * FloatBuffer以使数据可以被opengl使用，
	 */
	static float triangleCoords[] = {
		0.0f, 0.5f,
		-0.5f, -0.5f,
		0.5f, -0.5f
	};
	
	
	private int mProgram;
	
	
	//------------第一步 : 定义两个标签，分别于着色器代码中的变量名相同,   
    //------------第一个是顶点着色器的变量名，第二个是片段着色器的变量名  
    private static final String A_POSITION = "a_Position";     // 这个与simple_vertex_shader.glsl中定义的顶点变量是一致的， 下面定义的同样如此
    private static final String U_COLOR = "u_Color";  
	
	
    //------------第二步: 定义两个ID,我们就是通ID来实现数据的传递的,这个与前面  
    //------------获得program的ID的含义类似的  
	private int uColorLocation;
	private int aPositionLocation;
	
	public Triangle(Context context) {
		mContext = context;
		vertexBuffer = ByteBuffer.allocateDirect(triangleCoords.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		vertexBuffer.put(triangleCoords);
		
		vertexBuffer.position(0);
		
		getProgram();
		
		//----------第三步: 获取这两个ID ，是通过前面定义的标签获得的  
		uColorLocation = GLES20.glGetUniformLocation(mProgram, U_COLOR);
		aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION);
		
		GLES20.glVertexAttribPointer(aPositionLocation, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, vertexBuffer);
		GLES20.glEnableVertexAttribArray(aPositionLocation);
	}
	
	//获取program  
    private void getProgram(){  
        //获取顶点着色器文本  
        String vertexShaderSource = TextResourceReader  
                .readTextFileFromResource(mContext, R.raw.simple_vertex_shader);  
        //获取片段着色器文本  
        String fragmentShaderSource = TextResourceReader  
                .readTextFileFromResource(mContext, R.raw.simple_fragment_shader);  
        //获取program的id  
        mProgram  = ShaderHelper.buildProgram(vertexShaderSource, fragmentShaderSource);  
        GLES20.glUseProgram(mProgram);  
    }  
	
	public void draw() {
		GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 1.0f, 1.0f);
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, POSITION_COMPONENT_COUNT);
	}
	
}
