package info.smemo.nbase.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author suzhenpeng 标题下方蓝色条
 */
public class TitleIndicator extends View {

	/** 绑定的viewpager */
	private ViewPager viewPager;
	/** 画笔 */
	private Paint paint;
	/** 布局宽度 */
	private float screenWidth = 0f;
	/** 布局高度 */
	private float screenHeight = 0f;
	/** 起始位置 */
	private float x = 0f;
	/** 蓝条宽度 */
	private float width = 0f;

	public TitleIndicator(Context context) {
		super(context);
		init();
	}

	public TitleIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (null == viewPager)
			return;
		// 绘制长条
		canvas.drawRect(x, 0, x + width, screenHeight, paint);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		screenWidth = getWidth();
		screenHeight = getHeight();
		if (screenHeight != 0 || screenWidth != 0) {
			if (null != viewPager)
				width = screenWidth / viewPager.getAdapter().getCount();
			invalidate();
		}
	}

	/**
	 * 初始化
	 */
	public void init() {
		if (null == paint) {
			paint = new Paint();
			paint.setColor(getContext().getResources().getColor(
					android.R.color.holo_blue_light));
		}
	}

	public void setViewPager(ViewPager viewPager) {
		this.viewPager = viewPager;
		if (screenHeight != 0 || screenWidth != 0) {
			width = screenWidth / viewPager.getAdapter().getCount();
		}
		this.viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				x = width * arg0 + width * arg1;
				invalidate();
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		this.invalidate();
	}

}
