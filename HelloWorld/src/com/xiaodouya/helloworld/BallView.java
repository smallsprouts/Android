package com.xiaodouya.helloworld; //���������

import java.util.ArrayList; //���������
import java.util.Random; //���������

import android.content.Context; //���������
import android.content.res.Resources; //���������
import android.graphics.Bitmap; //���������
import android.graphics.BitmapFactory; //���������
import android.graphics.Canvas; //���������
import android.graphics.Color; //���������
import android.graphics.Paint; //���������
import android.graphics.RectF;
import android.view.SurfaceHolder; //���������
import android.view.SurfaceView; //���������
//�̳���SurfaceView������	

public class BallView extends SurfaceView implements SurfaceHolder.Callback {
	public static final int V_MAX = 35*2; // С��ˮƽ�ٶȵ����ֵ
	public static final int V_MIN = 15; // С����ֱ�ٶȵ����ֵ
	public static final int WOOD_EDGE = 394; // ľ����ұ��ص�x����
	public static final int GROUND_LING = (int) (1280*0.9424778);// ��Ϸ�д������y���꣬С�����䵽�˻ᵯ��
	public static final int UP_ZERO = 30; // С�������������У�����ٶȴ�СС�ڸ�ֵ����Ϊ0
	public static final int DOWN_ZERO = 60; // С����ײ�����������ٶȴ�СС�ڸ�ֵ����Ϊ0
	Bitmap[] bitmapArray = new Bitmap[6]; // ������ɫ��״��С��ͼƬ����
	Bitmap bmpBack; // ����ͼƬ����
	Bitmap bmpWood; // ľ��ͼƬ����
	String fps = "FPS:N/A"; // ������ʾ֡���ʵ��ַ���������ʹ��
	int ballNumber = 8; // С����Ŀ
	ArrayList<Movable> alMovable = new ArrayList<Movable>(); // С���������
	DrawThread dt; // ��̨��Ļ�����߳�

	public BallView(Context activity) {
		super(activity); // ���ø��๹����
		getHolder().addCallback(this);
		initBitmaps(getResources()); // ��ʼ��ͼƬ
		initMovables(); // ��ʼ��С��
		dt = new DrawThread(this, getHolder()); // ��ʼ���ػ��߳�
	}

	// ��������ʼ��ͼƬ
	public void initBitmaps(Resources r) {
		bitmapArray[0] = BitmapFactory.decodeResource(r, R.drawable.radio1); // ��ɫ��С��
		bitmapArray[1] = BitmapFactory.decodeResource(r, R.drawable.radio2); // ��ɫ��С��
		bitmapArray[2] = BitmapFactory.decodeResource(r, R.drawable.radio3); // ��ɫ��С��
		bitmapArray[3] = BitmapFactory.decodeResource(r, R.drawable.radio4); // ��ɫ�ϴ���
		bitmapArray[4] = BitmapFactory.decodeResource(r, R.drawable.radio5); // ��ɫ�ϴ���
		bitmapArray[5] = BitmapFactory.decodeResource(r, R.drawable.radio2); // ��ɫ�ϴ���
		bmpBack = BitmapFactory.decodeResource(r, R.drawable.back); // ����שǽ
		bmpWood = BitmapFactory.decodeResource(r, R.drawable.wood); // ľ��
	}

	// ��������ʼ��С��
	public void initMovables() {
		Random r = new Random(); // ����һ��Random����
		for (int i = 0; i < ballNumber; i++) {
			int index = r.nextInt(32); // ���������
			Bitmap tempBitmap = null; // ����һ��BitmapͼƬ����
			if (i < ballNumber / 2) {
				tempBitmap = bitmapArray[3 + index % 3];// ����ǳ�ʼ��ǰһ���򣬾ʹӴ����������һ��
			} else {
				tempBitmap = bitmapArray[index % 3];// ����ǳ�ʼ����һ���򣬾ʹ�С���������һ��
			}
			Movable m = new Movable(0, 212 - tempBitmap.getHeight(), tempBitmap.getWidth() / 2, tempBitmap); // ����Movable����
			alMovable.add(m); // ���½���Movable������ӵ�ArrayList�б���
		}
	}

	// ���������Ƴ���������Ҫ��ͼƬ����Ϣ
	public void doDraw(Canvas canvas) { // 720 1080
		canvas.drawBitmap(bmpBack, 0, 0, null); // ���Ʊ���ͼƬ 622 948
		//canvas.drawBitmap(bmpWood, 0, 60, null);// ����ľ��ͼƬ
		canvas.drawBitmap(bmpWood, null, new RectF(0, 212, 394, 96+212), null);
		for (Movable m : alMovable) { // ����Movable�б�����ÿ��Movable����
			m.drawSelf(canvas);
		}
		Paint paint = new Paint();
		Paint p = paint; // �������ʶ���
		p.setColor(Color.BLUE); // Ϊ����������ɫ
		p.setTextSize(18); // Ϊ�������������С
		p.setAntiAlias(true); // ���ÿ����
		canvas.drawText(fps, 30, 30, p); // ����֡�����ַ���
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {// ��дsurfaceChanged����
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {// ��дsurfaceCreated����
		if (!dt.isAlive()) { // ���DrawThreadû������������������߳�
			dt.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {// ��дsurfaceDestroyed����
		dt.flag = false; // ֹͣ�̵߳�ִ��
		dt = null; // ��dtָ��Ķ�������Ϊ����
	}
}