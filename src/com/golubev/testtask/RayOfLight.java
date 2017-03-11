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
import android.util.Log;

public class RayOfLight implements Light {

	private double dy = 0, dx = 0;
	private double step = 0;
	private double X = 0, Y = 0;
	private Point center = null;
	private int deadKick = 0;
	private Paint mainPaint = null;
	private HashSet<DropLight> dls = null;
	private int eachFifth = 0;
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

		double k = (y - center.y) / (x - center.x);
		
		dx = Math.sqrt((step * step) / (1 + k * k));
		if (x < center.x)
			dx = -dx;
		dy = k * dx;
		
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

		if (deadKick >= 3) {

			// ¬се падающие искры упали. ћожно удал€ть и луч
			if (dls.size() == 0) {
				path.clear();
				return 1;
			}

			return 0;

		}
		
		if ((X + dx) > (center.x * 2) || (X + dx) < 0) {
			deadKick++;
			dx = -dx;			
		}
		if ((Y+dy) > (center.y * 2) || (Y+dy) < 0) {
			deadKick++;
			dy = -dy;
		}

		X = X + dx;		
		Y = Y + dy;
		

		if (deadKick < 3)
			path.add(new Point((int) X, (int) Y));

		if (eachFifth == 5) {

			dls.add(new DropLight(X, Y, center, Math.abs(step), mainPaint
					.getColor()));
			eachFifth = 0;

		} else
			eachFifth++;

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
			mainPaint.setAlpha(alphToZero(mainPaint.getAlpha(), 10));
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
