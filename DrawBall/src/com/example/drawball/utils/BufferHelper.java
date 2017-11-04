package com.example.drawball.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class BufferHelper {
	/**
	 * 获取整型缓冲数据
	 * @param vertexs
	 * @return
	 */
	public static IntBuffer getIntBuffer(int[] vertexs) {
		IntBuffer buffer = ByteBuffer.allocateDirect(vertexs.length * 4).order(ByteOrder.nativeOrder())
				.asIntBuffer();
		buffer.put(vertexs);
		buffer.position(0);
		return buffer;
	}
	
	public static ByteBuffer getByteBuffer(byte[] vertexs) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(vertexs.length).order(ByteOrder.nativeOrder());
		buffer.put(vertexs);
		buffer.position(0);
		return buffer;
	}
	
	public static FloatBuffer getFloatBuffer(float[] vertexs) {
		FloatBuffer buffer = ByteBuffer.allocateDirect(vertexs.length * 4).order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		buffer.put(vertexs);
		buffer.position(0);
		return buffer;
	}
	
	public static FloatBuffer getFloatBuffer(ArrayList<Float> vertexs) {
		FloatBuffer buffer = ByteBuffer.allocateDirect(vertexs.size() * 4).order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		float[] tmpVertex = new float[vertexs.size()];
		for (int i = 0; i < vertexs.size(); i++ ) {
			tmpVertex[i] = vertexs.get(i);
		}
		buffer.put(tmpVertex);
		buffer.position(0);
		return buffer;
	}
}
