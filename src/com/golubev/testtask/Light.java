/**
 * ���������� ������� ����������, � ������� ��� ������� ������� ������ ������������ ���, ������������ �� ������ ������ � ����� �������.
 * ��� ���������� �� ������ ������ 3 ����.
 * ������������� ������� ���������� ������� ������� �����.
 * ������������ SDK, ��� ������������������ ���������.
 * 
 * API Level: 18+
 * ���� ����������������: Java
 * 
 * @author � Golubev Alexey 2017
 * @version 1.0
 */
package com.golubev.testtask;

import android.graphics.Canvas;

public interface Light {
	
	public int calculation();

	public void drawRay(Canvas canvas);
}
