package com.viaplay.ime.bean;

/**
 * 瑙︽懜鏄犲皠鐨勯厤缃枃浠? * 
 * @author Steven
 *
 */

public class JnsIMEProfile {
	public static final int LEFT_JOYSTICK = 0;
	public static final int RIGHT_JOYSTICK = 1;
	public int keyCode;
	public int key;
	public float posX; //涓績鐐�?	
	public float posY; //涓績鐐�?	
	public float posR; //鍖哄煙鐨勫崐寰�	
	public float posType; //鍖哄煙绫诲�?锛氬乏鎽囨潌锛屽彸鎽囨潌
	
	public JnsIMEProfile()
	{
		posType = 2;
	}
}
