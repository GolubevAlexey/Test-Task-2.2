/**
 * @author © Golubev Alexey 2017
 */
package com.golubev.testtask;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class DropLight implements Light {

	private double X = 0, Y = 0;
	private double c = 0, b = 0;
	boolean status = true;
	private Paint mainPaint = null;
	private Point center = null;
	private double step = 0;

	public DropLight(double x, double y, Point center, double step, int color) {

		this.center = center;
		Random rnd = new Random();

		int speed = rnd.nextInt(4) + 3;
		int z = rnd.nextInt(2);
		if (z == 1)
			z = 1;
		else
			z = -1;
		X = x;
		Y = y;
		b = -(x + z * speed);
		c = y - (Math.pow(speed, 2));

		this.step = z * step;

		int difColor = rnd.nextInt(20);
		mainPaint = new Paint();
		mainPaint.setColor(Color.argb(205 + rnd.nextInt(50), Color.red(color)
				+ difColor, Color.green(color) + difColor, Color.blue(color)
				+ difColor));
		mainPaint.setStrokeWidth(2f);
	}

	@Override
	public int calculation() {
		if (!status)
			return 1;

		X = X + step / 8;
		Y = Math.pow(X + b, 2) + c;

		if (X < 0 || X > (center.x * 2))
			status = false;
		if (Y < 0 || Y > (center.y * 2))
			status = false;

		return 0;
	}

	@Override
	public void drawRay(Canvas canvas) {

		if (status) {
			mainPaint.setAlpha(RayOfLight.alphToZero(mainPaint.getAlpha(),2));
			canvas.drawPoint(Math.round(X), Math.round(Y), mainPaint);

		}
	}

}
