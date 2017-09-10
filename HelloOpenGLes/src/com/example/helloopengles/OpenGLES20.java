package com.example.helloopengles;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class OpenGLES20 extends Activity {
    private GLSurfaceView mGLView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_opengles20);
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
    }

    class MyGLSurfaceView extends GLSurfaceView
    {

        public MyGLSurfaceView(Context context)
        {
            super(context);

            try
            {
                // Create an OpenGL ES 2.0 context
                setEGLContextClientVersion(2);

                // Set the Renderer for drawing on the GLSurfaceView
                setRenderer(new MyRenderer());

                // Render the view only when there is a change in the drawing
                // data
                setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

                // 注意上面语句的顺序，反了可能会出错

            }
            catch (Exception e)
            {
                e.printStackTrace();

            }

        }
    }
    
    public class MyRenderer implements GLSurfaceView.Renderer
    {

        public void onSurfaceCreated(GL10 unused, EGLConfig config)
        {
            // Set the background frame color
            GLES20.glClearColor(1.0f, 0f, 0f, 1.0f);
        }

        public void onDrawFrame(GL10 unused)
        {
            // Redraw background color
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        }

        public void onSurfaceChanged(GL10 unused, int width, int height)
        {
            GLES20.glViewport(0, 0, width, height);
        }
    }
}
