package com.viaplay.ime.jni;

/**
 * ��ͼ�࣬��ǰӦ���н���android2.2����ʹ�ã�4.2����ʱ�����������ʱ������ͼ�쳣��
 * 
 * @author Steven.xu
 *
 */

public class ScreenShot {


	public static native boolean getScreenShot();

	static {
		System.loadLibrary("screenshot");
	}
	
}
