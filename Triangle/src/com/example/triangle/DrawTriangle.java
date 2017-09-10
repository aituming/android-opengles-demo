package com.example.triangle;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class DrawTriangle extends Activity {
	private GLSurfaceView mGLView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_draw_triangle);
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
                setRenderer(new MyRender(context));

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
}
