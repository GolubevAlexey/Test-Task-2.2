/**
 * @author © Golubev Alexey 2017
 */
package com.golubev.testtask;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class RayOfLight implements Light {

	// Ћинейное уравнение
	private double k = 0;
	private double b = 0;
	private double step = 0;
	private double X = 0, Y = 0;
	private Point center = null;
	private int deadKick = 0;
	private Paint mainPaint = null;
	private HashSet<DropLight> dls = null;
	private int eachTen = 0;
	private LinkedHashSet<Point> path = new LinkedHashSet<Point>();

	/**
	 *  онструктор луча.
	 * 
	 * @param x
	 *            координаты направлени€ луча X
	 * @param y
	 *            координаты направлени€ луча Y
	 * @param center
	 *            координаты центра
	 * @param step
	 *            шаг луча @
	 * 
	 * @return 1-если можно удал€ть
	 */
	public RayOfLight(double x, double y, Point center, double step) {

		k = (y - center.y) / (x - center.x);
		b = y - k * x;
		if (x < center.x)
			step = -step;
		this.step = step;
		this.center = center;
		X = center.x;
		Y = center.y;

		path.add(new Point((int) X, (int) Y));

		mainPaint = new Paint();
		Random rnd = new Random();
		mainPaint.setColor(Color.argb(255, rnd.nextInt(255), rnd.nextInt(255),
				rnd.nextInt(255)));

		dls = new HashSet<DropLight>();

	}

	/**
	 * –асчет двиежни€ линии и расчет падающих искр.
	 * 
	 * @return 1-если можно удал€ть
	 */
	@Override
	public int calculation() {

		Iterator<DropLight> iterator = dls.iterator();
		while (iterator.hasNext()) {
			DropLight dl = iterator.next();

			if (dl.calculation() == 1)
				iterator.remove();
		}

		if (deadKick == 3) {

			// ¬се падающие искры упали. ћожно удал€ть.
			if (dls.size() == 0) {
				path.clear();
				return 1;
			}

			return 0;

		}

		if ((X + step) > (center.x * 2) || (X + step) < 0) {
			step = -step;
			k = -k;
			b = Y - k * X;
			deadKick++;
		}

		X = X + step;

		double yTmp = k * X + b;
		if (yTmp > (center.y * 2) || yTmp < 0) {
			k = -k;
			b = yTmp - k * X;
			deadKick++;
		}

		Y = k * X + b;

		if (deadKick < 3)
			path.add(new Point((int) X, (int) Y));

		if (eachTen == 5) {
			dls.add(new DropLight(X, Y, center, Math.abs(step), mainPaint
					.getColor()));
			eachTen = 0;
		} else
			eachTen++;

		return 0;

	}

	/**
	 * ќтрисовка луча и падающих искр.
	 * 
	 * @param canvas
	 *            передаем канву дл€ отрисовки.
	 */
	@Override
	public void drawRay(Canvas canvas) {

		for (DropLight dl : dls) {
			dl.drawRay(canvas);
		}

		Point last = null;
		if (deadKick == 3) {
			mainPaint.setAlpha(alphToZero(mainPaint.getAlpha(),10));
		}
		for (Point p : path) {
			if (last != null)
				canvas.drawLine(last.x, last.y, p.x, p.y, mainPaint);
			last = p;
		}

	}

	public static int alphToZero(int alpha, int step) {

		alpha -= step;
		if (alpha < 0)
			alpha = 0;
		
		return alpha;

	}
}
