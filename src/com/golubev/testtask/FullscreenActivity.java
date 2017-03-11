/**
 * Необходимо создать приложение, в котором при касании пальцем экрана изображается луч, направленный из центра экрана к точке касания.
 * Луч отражается от границ экрана 3 раза.
 * Одновременные касания изобразить разными цветами лучей.
 * Использовать SDK, без специализированных библиотек.
 * 
 * API Level: 18+
 * Язык программирования: Java
 * 
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
