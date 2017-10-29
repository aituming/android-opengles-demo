package com.example.drawcircle;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class CircleActivity extends Activity {
	private GLSurfaceView mTriGLView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tri);
        mTriGLView = new CircleGLSurfaceView(this);
        setContentView(mTriGLView);
    }
    
    static class CircleGLSurfaceView extends GLSurfaceView {

		public CircleGLSurfaceView(Context context) {
			super(context);
			setEGLContextClientVersion(3);
			setRenderer(new CircleRenderer(context));
			setRenderMode(RENDERMODE_WHEN_DIRTY);
		}
    	
    }
}
