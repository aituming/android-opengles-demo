package com.example.texture;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.example.texture.utils.BufferHelper;
import com.example.texture.utils.Logit;
import com.example.texture.utils.MatrixState;
import com.example.texture.utils.ShaderHelper;
import com.example.texture.utils.TextResourceReader;
import com.example.texture.utils.TextureHelper;
import com.example.texture.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;

public class Triangle {
	private static final String TAG = "Triangle";
	
	private Context context;
	
	private FloatBuffer vertexBuffer;
	// 纹理坐标数组
	private FloatBuffer textureCoorBuffer;
	
	private static final int BYTES_PER_FLOAT = 4; //浮点字节个数
	private static final int COORS_PER_VERTEX = 2; // 顶点坐标分量个数
	private static final int NUM_VERTEXS = 3;
	
	private static final float[] vertex = {
		0, 0,
		0.5f, 0,
		0, 0.5f
	};
	
	private static final float[] textCoor = {
		0, 0,
		1.0f, 0,
		0, 1.0f
	};
	
	private int program; //应用程序句柄
	
	private static final String A_POSITION = "aPosition";
	private static final String U_COLOR = "uColor";
	private static final String V_MATRIX = "vMatrix";
	private static final String A_TEXTURE_COORD = "aTextCoor";
	private static final String S_TEXTURE = "sTexture";
	
	int vMatrixLocation;
	
	public Triangle(Context context) {
		this.context = context;
		
		vertexBuffer = ByteBuffer.allocateDirect(vertex.length * BYTES_PER_FLOAT). // 注意不要写成 allocate
				order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexBuffer.put(vertex);
		vertexBuffer.position(0);
		
		// 纹理坐标
		textureCoorBuffer = BufferHelper.getFloatBuffer(textCoor);
		
		getProgram();
		
		int aPositionLocation = GLES30.glGetAttribLocation(program, A_POSITION); // 对应于 vertex_shader.glsl 中 attribute属性
		Logit.d(TAG, "aPosition location: " + aPositionLocation);
		GLES30.glVertexAttribPointer(aPositionLocation, 2, GLES30.GL_FLOAT, false, 0, vertexBuffer); // 区别glVertexAttribIPointer
		GLES30.glEnableVertexAttribArray(aPositionLocation);  //启用顶点属性数组
		
//		int uColorLocation = GLES30.glGetUniformLocation(program, U_COLOR);  // 对应与 fragment_shader 中uniform属性
//		Logit.d(TAG, "uColor Location: " + uColorLocation);
		
		vMatrixLocation = GLES30.glGetUniformLocation(program, V_MATRIX);
		
		Logit.d(TAG, "vMatrixLocation: " + vMatrixLocation);
		
//		GLES30.glUniform4f(uColorLocation, 1, 0, 0, 1); // 设置着色器中变量的值
		
		// 纹理相关
		int aTextCoorLocation = GLES30.glGetAttribLocation(program, A_TEXTURE_COORD);
		GLES30.glVertexAttribPointer(aTextCoorLocation, 2, GLES30.GL_FLOAT, false, 0, textureCoorBuffer);
		GLES30.glEnableVertexAttribArray(aTextCoorLocation);  //启用顶点属性数组
		
		int sTextureLocation = GLES30.glGetUniformLocation(program, S_TEXTURE);

//		int textures[] = new int[1];
//		GLES30.glGenTextures(1, textures, 0);
//		
//		int textureId = textures[0];
//		GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
//		
//		// 设置MIN采样方式
//		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
//		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
//		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
//		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
//		GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_SWIZZLE_R, GLES30.GL_GREEN);
//		
//		InputStream inputS = null;
//		Bitmap textureBitmap = null;
//		try {
//			inputS = context.getResources().openRawResource(R.drawable.wall);
//			textureBitmap = BitmapFactory.decodeStream(inputS);
//			GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, textureBitmap, 0);
//			textureBitmap.recycle();
//		} catch (Exception ex) {
//			Logit.e(TAG, "get wall paper exception.", ex);
//		} finally {
//			try {
//				inputS.close();
//				textureBitmap.recycle();
//			} catch (Exception ex) {
//				// ignore it 
//			}
//		} 
		
		int texture = TextureHelper.loadTexture(context, R.drawable.wall);
		Logit.d(TAG, "texture: " + texture);
		// Set the active texture unit to texture unit 0.  
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);  
        // Bind the texture to this unit.  
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texture);  
        // Tell the texture uniform sampler to use this texture in the shader by  
        // telling it to read from texture unit 0.  
        GLES30.glUniform1i(sTextureLocation, 0); 
		
	}
	
	private void getProgram() {
		String vertexSource = TextResourceReader.readTextFileFromResource(context, R.raw.vertex_shader);
		String fragmentSource = TextResourceReader.readTextFileFromResource(context, R.raw.fragment_shader);
		
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
