/**
 * 	Win8ProgressBar.java
 * 	com.alaric.norris.widget.progressbar_win8
 * 	Function： 	TODO 
 *   ver     date      		author
 * 	──────────────────────────────────
 *   		 2013-12-24 		Norris
 *	Copyright (c) 2013, Norris All Rights Reserved.
 *	──────────────────────────────────────────────────────────────────────────────────────────────────────
 *	2013-12-24上午10:09:55	Modified By Norris 
 *	──────────────────────────────────────────────────────────────────────────────────────────────────────
 */
package com.alaric.norris.widget.progressbar ;

import java.util.ArrayList ;
import android.content.Context ;
import android.graphics.Canvas ;
import android.graphics.Color ;
import android.graphics.Paint ;
import android.os.Handler ;
import android.os.Message ;
import android.util.AttributeSet ;
import android.util.Log ;
import android.view.View ;

/**
 *	ClassName:	Win8ProgressBar
 *	Function: 	仿Win8圆形动画进度条
 *	@author   	Norris		Norris.sly@gmail.com
 *	@version  	
 *	@since   	Ver 1.0		I used to be a programmer like you, then I took an arrow in the knee 
 *	@Date	 	2013		2013-12-24		上午10:09:55
 *	@see 	 	
 *	──────────────────────────────────────────────────────────────────────────────────────────────────────
 *	@Fields 
 *	──────────────────────────────────────────────────────────────────────────────────────────────────────
 *	@Methods 
 *	──────────────────────────────────────────────────────────────────────────────────────────────────────
 *	2013-12-24上午10:09:55	Modified By Norris 
 *	──────────────────────────────────────────────────────────────────────────────────────────────────────
 */
public class Win8ProgressBar extends View {

	/**
	 * 	帧率=1000/delayMillis
	 * 	帧率越大,旋转速度也就越快
	 * 	int			:		delayMillis	
	 * 	@since 2013-12-24上午11:01:13
	 */
	private int delayMillis = 30 ;

	/**
	 * 	TODO
	 * 	Handler			:		mHandler	
	 * 	@since 2013-12-24上午11:00:46
	 */
	private Handler mHandler = new Handler(new Handler.Callback() {

		@ Override
		public boolean handleMessage(Message msg) {
			for(Entity tempEntity : entities) {
				tempEntity.update() ;
			}
			invalidate() ;
			mHandler.sendEmptyMessageDelayed(0 , delayMillis) ;
			time += delayMillis ;
			return false ;
		}
	}) ;

	private ArrayList<Entity> entities ;

	/**
	 * 	layout中的View宽
	 * 	int			:		width_View	
	 * 	@since 2013-12-24上午11:25:32
	 */
	private int width_View = 0 ;

	/**
	 * 	layout中的View高
	 * 	int			:		height_View	
	 * 	@since 2013-12-24上午11:25:34
	 */
	private int height_View = 0 ;

	/**
	 * 	根据宽高自适应后的宽
	 * 	int			:		width_MaxSquare	
	 * 	@since 2013-12-24上午11:25:36
	 */
	private int width_MaxSquare = 0 ;

	/**
	 * 	进度圈的半径
	 * 	int			:		radius_Circle	
	 * 	@since 2013-12-24上午11:52:36
	 */
	private int radius_Circle = 15 ;

	private int shift = 20 ;

	/**
	 * 	小球的半径
	 * 	int			:		radius	
	 * 	@since 2013-12-24上午9:36:58
	 */
	private int radius_Ball = 3 ;

	/**
	 * 	小球的颜色
	 * 	int			:		color_ball	
	 * 	@since 2013-12-24上午11:53:03
	 */
	private int color_ball = Color.WHITE ;

	private long time = 0 ;

	private boolean isStarted = false ;

	private Context mContext ;

	public Win8ProgressBar(Context context) {
		this(context , null) ;
	}

	public Win8ProgressBar(Context context , AttributeSet attrs) {
		this(context , attrs , 0) ;
	}

	public Win8ProgressBar(Context context , AttributeSet attrs , int defStyleAttr) {
		super(context , attrs , defStyleAttr) ;
		this.mContext = context ;
		init(attrs) ;
	}

	@ Override
	protected void onLayout(boolean changed , int left , int top , int right , int bottom) {
		super.onLayout(changed , left , top , right , bottom) ;
		width_View = getLayoutParams().width ;
		height_View = getLayoutParams().height ;
		// 根据宽高自动取小的
		width_MaxSquare = width_View > height_View ? height_View : width_View ;
		getLayoutParams().width = width_MaxSquare ;
		getLayoutParams().height = width_MaxSquare ;
		if(width_MaxSquare <= 0) {
			width_MaxSquare = 30 ;
		}
		else {
			if(width_MaxSquare > 0) {
				//根据view宽度定义小球的半径
				if(width_MaxSquare < 50) {
					radius_Ball = 2 ;
				}
				else if(width_MaxSquare < 80) {
					radius_Ball = 3 ;
				}
				else {
					radius_Ball = 4 ;
				}
				//radius = width<80?2:4;
				radius_Circle = width_MaxSquare / 2 - radius_Ball * 2 ;
				if(radius_Circle <= 0)
					radius_Circle = 15 ;
				shift = width_MaxSquare / 2 ;
			}
		}
	}

	private void init(AttributeSet attrs) {
		//获取设置的background作为小球颜色,然后将view自身背景设置成透明色
		if(attrs != null) {
			String color_attrs = attrs.getAttributeValue(
					"http://schemas.android.com/apk/res/android" , "background") ;
			if(color_attrs != null) {
				if(color_attrs.startsWith("#")) {
					color_ball = Color.parseColor(color_attrs) ;
				}
				else {
					color_ball = getResources().getColor(
							Integer.parseInt(color_attrs.replaceAll("@" , ""))) ;
				}
			}
			setBackgroundResource(android.R.color.transparent) ;
			Log.i("tag" , color_attrs + "" + color_ball) ;
		}
//		String color_attrs = attrs.getAttributeValue("http://schemas.android.com/apk/res/android" ,
//				"background") ;
//		//TypedArray是一个数组容器   
//		TypedArray array = mContext.obtainStyledAttributes(attrs , R.styleable.ProgressBar , 0 , 0) ;
//		//这里的属性是:名字_属性名   
//		String text = array.getString(R.styleable.ProgressBar_gravity) ;
//		Log.i("tag" , text + "") ;
//		array.recycle() ;
	}

	public void setColor(int color) {
		this.color_ball = color ;
	}

	/**
	 * 	stopAnimation:{结束动画}
	 *  ──────────────────────────────────		void
	 * 	@throws 
	 * 	@since  	I used to be a programmer like you, then I took an arrow in the knee　Ver 1.0
	 *	──────────────────────────────────────────────────────────────────────────────────────────────────────
	 *	2013-12-23下午2:03:39	Modified By Norris 
	 *	──────────────────────────────────────────────────────────────────────────────────────────────────────
	 */
	public void stopAnimation() {
		mHandler.removeMessages(0) ;
		isStarted = false ;
		invalidate() ;
	}

	public boolean isStart() {
		return isStarted ;
	}

	/**
	 * 	startAnimation:{重新开始动画}
	 *  ──────────────────────────────────		void
	 * 	@throws 
	 * 	@since  	I used to be a programmer like you, then I took an arrow in the knee　Ver 1.0
	 *	──────────────────────────────────────────────────────────────────────────────────────────────────────
	 *	2013-12-23下午2:05:14	Modified By Norris 
	 *	──────────────────────────────────────────────────────────────────────────────────────────────────────
	 */
	public void startAnimation() {
		if(isStarted)
			return ;
		isStarted = true ;
		time = 0 ;
		entities = new ArrayList<Win8ProgressBar.Entity>() ;
		float s = 0.25f ;
		entities.add(new Entity(0 * s , color_ball , delayMillis * 0 * 4)) ;
		entities.add(new Entity(1 * s , color_ball , delayMillis * 1 * 4)) ;
		entities.add(new Entity(2 * s , color_ball , delayMillis * 2 * 4)) ;
		entities.add(new Entity(3 * s , color_ball , delayMillis * 3 * 4)) ;
		entities.add(new Entity(4 * s , color_ball , delayMillis * 4 * 4)) ;
		mHandler.sendEmptyMessage(0) ;
	}

	@ Override
	protected void onDraw(Canvas canvas) {
		for(Entity tempEntity : entities) {
			tempEntity.draw(canvas) ;
		}
		super.onDraw(canvas) ;
	}

	class Entity {

		private float x ;

		private float y ;

		private Paint mPaint = new Paint() ;

		private double sp = 0 ;

		private long delay ;

		//动画一共三个阶段0~2
		private int section = 0 ;

		//每个阶段pec从0~1
		private float pec = 0 ;

		boolean visiable = true ;

		public Entity(float sp , int color , int delay) {
			mPaint.setAntiAlias(true) ;
			mPaint.setStyle(Paint.Style.FILL) ;
			this.sp = sp ;
			this.delay = delay ;
			mPaint.setColor(color) ;
		}

		public float getInterpolation(float input) {
			return (float) (Math.cos( (input + 1) * Math.PI) / 2.0f) + 0.5f ;
		}

		public void update() {
			if(time < delay)
				return ;
			visiable = true ;
			//pec这个变量是步进值,值越大,旋转速度也越快,可以跟delayMillis配合使用
			pec += 0.03 ;
			if(pec > 1) {
				pec = 0 ;
				section = ++ section == 3 ? 0 : section ;
				delay = section == 0 ? time + delayMillis * 22 : time + delayMillis * 3 ;
				visiable = section == 0 ? false : true ;
			}
			//sec=0从0.5pi开始,sec=1从1.5pi开始,sec=2从1pi开始
			double θ = Math.PI
					* 0.5
					+ (section == 0 ? 0 : section * Math.PI / section)
					- (section == 0 ? 0 : sp)
					//sec=0,sec=2移动1pi, sec=1移动2pi
					+ (Math.PI * (section == 1 ? 2 : 1) - (section == 0 ? sp : 0) + (section == 2 ? sp
							: 0)) * getInterpolation(pec) ;
			x = (float) (radius_Circle / 2 * Math.cos(θ)) + shift / 2 ;
			y = (float) (radius_Circle / 2 * Math.sin(θ)) + shift / 2 ;
		}

		public void draw(Canvas canvas) {
			if( ! visiable || x == 0 || y == 0)
				return ;
			canvas.save() ;
			canvas.translate(x , y) ;
			canvas.drawCircle(x , y , radius_Ball , mPaint) ;
			canvas.restore() ;
		}
	}

	@ Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow() ;
		if(getVisibility() == View.VISIBLE) {
			startAnimation() ;
		}
	}

	@ Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow() ;
		stopAnimation() ;
	}
}
