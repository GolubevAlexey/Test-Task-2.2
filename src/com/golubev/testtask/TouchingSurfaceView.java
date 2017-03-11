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

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TouchingSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {

	DrawThread drawThread = null;

	public TouchingSurfaceView(Context context) {
		super(context);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int actionMask = event.getActionMasked();
		int pointerCount = event.getPointerCount();

		if (actionMask == MotionEvent.ACTION_DOWN
				|| actionMask == MotionEvent.ACTION_POINTER_DOWN) {

			for (int i = 0; i < pointerCount; i++)

				if (drawThread != null)
					drawThread.eventTouch((int) event.getX(i),
							(int) event.getY(i));

		}
		return true;

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		drawThread = new DrawThread(holder);
		drawThread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		//

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//
		drawThread.allAbort();

	}

}
