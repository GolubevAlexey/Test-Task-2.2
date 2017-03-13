﻿/**
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.SystemClock;
import android.util.Log;

public class RayOfLight implements Light {

	private double dy = 0, dx = 0;
	private double step = 0;
	private double X = 0, Y = 0;
	private Point center = null;
	private int deadKick = 0;
	private Paint mainPaint = null;

	// коллекция искр
	private HashSet<DropLight> dls = null;
	private int eachFifth = 0;

	// путь луча должен быть связанным,так как по нему рисуется линия.
	private LinkedHashSet<Point> path = new LinkedHashSet<Point>();

	/**
	 * Конструктор луча.
	 * 
	 * @param x
	 *            координаты направления луча X
	 * @param y
	 *            координаты направления луча Y
	 * @param center
	 *            координаты центра
	 * @param step
	 *            шаг луча(скорость)
	 */
	public RayOfLight(double x, double y, Point center, double step) {

		x = (x == center.x) ? (center.x - 0.0001) : x;
		double k = (y - center.y) / (x - center.x);

		dx = Math.sqrt((step * step) / (1 + k * k));
		if (x < center.x)
			dx = -dx;
		dy = k * dx;

		// ситуация с вертикальной прямой
		if (x == center.x) {
			dx = 0;
			dy = (k / Math.abs(k)) * step;
		}

		this.step = step;
		this.center = center;
		X = center.x;
		Y = center.y;

		path.add(new Point((int) X, (int) Y));

		mainPaint = new Paint();
		Random rnd = new Random();
		mainPaint.setColor(Color.argb(255, rnd.nextInt(256), rnd.nextInt(256),
				rnd.nextInt(256)));

		dls = new HashSet<DropLight>();

	}

	/**
	 * Расчет двиежния линии и расчет падающих искр.
	 * 
	 * @return 1-если можно удалять
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

			// Все падающие искры упали. Можно удалять и луч
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

		if ((Y + dy) > (center.y * 2) || (Y + dy) < 0) {

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
	 * Отрисовка луча и падающих искр.
	 * 
	 * @param canvas
	 *            передаем канву для отрисовки.
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
