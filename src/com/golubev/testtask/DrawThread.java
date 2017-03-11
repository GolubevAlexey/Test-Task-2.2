/**
 * @author © Golubev Alexey 2017
 */
package com.golubev.testtask;

import java.util.HashSet;
import java.util.Iterator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {

	private SurfaceHolder holder = null;
	private boolean gRun = false;
	private HashSet<RayOfLight> rol = null;
	private Point center = null;
	private double gStep = 3;

	DrawThread(SurfaceHolder holder) {
		this.holder = holder;
		center = new Point(
				(holder.getSurfaceFrame().right - holder.getSurfaceFrame().left) / 2,
				(holder.getSurfaceFrame().bottom - holder.getSurfaceFrame().top) / 2);
	}

	@Override
	public void start() {
		super.start();
		gRun = true;
	}

	public void allAbort() {
		gRun = false;
	}

	public void eventTouch(int x, int y) {
		if (rol == null)
			rol = new HashSet<RayOfLight>();

		synchronized (rol) {
			rol.add(new RayOfLight(x, y, center, gStep));
		}

	}

	public void run() {

		Canvas canvas = null;

		while (gRun) {

			canvas = holder.lockCanvas(null);

			if (canvas != null) {

				canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);

				if (rol != null) {

					synchronized (rol) {

						Iterator<RayOfLight> iterator = rol.iterator();
						while (iterator.hasNext()) {
							RayOfLight element = iterator.next();

							if (element.calculation() == 1)
								iterator.remove();
							else
								element.drawRay(canvas);
						}
					}
				}

				holder.unlockCanvasAndPost(canvas);
			}

			SystemClock.sleep(10);
		}

	}
}
