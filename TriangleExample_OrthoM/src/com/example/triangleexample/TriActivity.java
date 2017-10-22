/**
 * 画一个直角三角形
 */

package com.example.triangleexample;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TriActivity extends Activity {
	private GLSurfaceView mTriGLView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tri);
        mTriGLView = new TriGLSurfaceView(this);
        setContentView(mTriGLView);
    }
    
    static class TriGLSurfaceView extends GLSurfaceView {

		public TriGLSurfaceView(Context context) {
			super(context);
			setEGLContextClientVersion(3);
			setRenderer(new TriangleRenderer(context));
			setRenderMode(RENDERMODE_WHEN_DIRTY);
		}
    	
    }
}
