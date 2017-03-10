/**
 * @author © Golubev Alexey 2017
 */
package com.golubev.testtask;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface Light {
	
	public int calculation();

	public void drawRay(Canvas canvas);
}
