/**
 * @author © Golubev Alexey 2017
 * @version 1.0
 */
package com.golubev.testtask;

import android.app.Activity;
import android.os.Bundle;

public class FullscreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(new TouchingSurfaceView(this));

	}
	

}
