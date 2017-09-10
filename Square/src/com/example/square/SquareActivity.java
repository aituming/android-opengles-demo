package com.example.square;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SquareActivity extends Activity {

	private GLSurfaceView mSquareSurface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_square);
        mSquareSurface = new SquareSurface(this);
        setContentView(mSquareSurface);
    }
    
    
}
