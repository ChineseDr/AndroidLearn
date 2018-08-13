package com.ray.customview.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Created by Raymond on 2018/6/25 20:32
 */
public class CircleMenu extends View {
    private static final String TAG = "CircleMenu";

    private static final int MEUN_STATUS_OPEN = 1;
    private static final int MEUN_STATUS_OPENED = 1 << 1;
    private static final int MEUN_STATUS_CLOSE = 1 << 2;
    private static final int MEUN_STATUS_CLOSE_CLEAR = 1 << 3;
    private static final int MEUN_STATUS_CLOSED = 1 << 4;
    private static final int MEUN_STATUS_CANCEL = 1 << 5;
    private static final int MENU_SUBMENU_NUM = 8;

    private int status;

    private boolean pressed;

    private int clickIndex;

    private int itemNum;

    private int partSize;

    private int iconSize;

    private int rotateAngle;
    private int centerX;
    private int centerY;
    //分数，不相连的一块
    private float fraction;
    private float rFraction;

    private int circleMenuRadius;

    private int itemIconSize;

    private float pathLength;


    private Paint oPaint;
    private Paint cPaint;
    private Paint sPaint;

    private Path path, dstPath;
    //PathMeasure用来测量关联的path，获取该path相关状态
    private PathMeasure pathMeasure;

    private int mainMenuColor;

    private Drawable openMenuIcon;

    private Drawable closeMenuIcon;

    private List<Integer> subMenuColorLsit;

    private List<Drawable> subMenuDrawableList;

    private List<RectF> menuRectFList;

    public CircleMenu(Context context) {
        super(context);
    }

    public CircleMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        status = MEUN_STATUS_CLOSED;
        init();
    }

    //初始化画图工具
    private void initTools() {
        oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        cPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        cPaint.setStrokeCap(Paint.Cap.ROUND);

        sPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sPaint.setStyle(Paint.Style.FILL);

        path = new Path();
        dstPath = new Path();
        //无参构造
        pathMeasure = new PathMeasure();
    }

    private void init() {
        initTools();

        openMenuIcon = new GradientDrawable();

        closeMenuIcon = new GradientDrawable();

        subMenuColorLsit = new ArrayList<>();
        subMenuDrawableList = new ArrayList<>();
        menuRectFList = new ArrayList<>();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 绘制周围子菜单环绕的圆环路径
     *
     * @param canvas
     */
    private void drawCircleMenu(Canvas canvas) {
        Log.d(TAG, "drawCircleMenu:Start ");
        if (status == MEUN_STATUS_CLOSE) {
            drawCirclePath(canvas);
            drawCircleIcon(canvas);
        } else {
            cPaint.setStrokeWidth(partSize * 2 + partSize * .5f * fraction);
            cPaint.setColor(calcAlphaColor(getClickMenuColor(), true));
            canvas.drawCircle(centerX, centerY, circleMenuRadius + partSize * .5f * fraction, cPaint);
        }
    }

    private int getClickMenuColor() {
        return clickIndex == 0 ? mainMenuColor : subMenuColorLsit.get(clickIndex - 1);
    }

    /**
     * 绘制子菜单转动时的图标
     *
     * @param canvas
     */
    private void drawCircleIcon(Canvas canvas) {
        Log.d(TAG, "drawCircleIcon:start ");
        canvas.save();
        Drawable selDrawable = subMenuDrawableList.get(clickIndex - 1);
        if (selDrawable == null) {
            return;
        }

        int startAngle = (clickIndex - 1) * (360 / itemNum);
        int endAngle = 360 + startAngle;
        int itemX = (int) (centerX + Math.sin(Math.toRadians(endAngle - startAngle) * fraction + startAngle) * circleMenuRadius);
        int itemY = (int) (centerY + Math.cos(Math.toRadians((endAngle - startAngle) * fraction + startAngle)) * circleMenuRadius);
        canvas.rotate(360 * fraction, itemX, itemY);
        selDrawable.setBounds(itemX - iconSize / 2, itemY - iconSize / 2, itemX - iconSize / 2, itemY + iconSize / 2);
        selDrawable.draw(canvas);
        canvas.restore();
    }

    /**
     * 绘制子菜单转动时的轨迹路径
     *
     * @param canvas
     */
    private void drawCirclePath(Canvas canvas) {
        Log.d(TAG, "drawCirclePath:Start ");
        canvas.save();
        canvas.rotate(rotateAngle, centerX, centerY);
        dstPath.reset();
        dstPath.lineTo(0, 0);
        pathMeasure.getSegment(0, pathLength * fraction, dstPath, true);
        cPaint.setStrokeWidth(partSize * 2);
        cPaint.setColor(getClickMenuColor());
        canvas.drawPath(dstPath, cPaint);
        canvas.restore();
    }

    /**
     * 绘制子菜单按钮
     *
     * @param canvas
     */
    private void drawSubMenu(Canvas canvas) {
        Log.d(TAG, "drawSubMenu:start ");
    }

    /**
     * 绘制子菜单图标
     *
     * @param canvas
     */
    private void drawSubmenuIcon(Canvas canvas) {

    }

    /**
     * 绘制中间开关按钮
     *
     * @param canvas
     */
    private void drawMainMenu(Canvas canvas) {

    }

    /**
     * 绘制中间按钮图标
     *
     * @param canvas
     */
    private void drawMainMenuIcon(Canvas canvas) {

    }

    /**
     * 绘制按钮阴影
     *
     * @param canvas
     * @param centerX
     * @param centerY
     * @param radius
     */
    private void drawMenuShadow(Canvas canvas, int centerX, int centerY, float radius) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * 更新按钮状态
     *
     * @param menuIndex
     * @param isPress
     */
    private void updatePressEffect(int menuIndex, boolean isPress) {

    }

    /**
     * 按钮按下时颜色
     *
     * @param menuIndex
     * @param depth
     * @return
     */
    private int calcPressEffectColor(int menuIndex, float depth) {
        return 0;
    }

    /**
     * 动画透明度
     *
     * @param color
     * @param reverse
     * @return
     */
    private int calcAlphaColor(int color, boolean reverse) {
        return 0;
    }

    /**
     * 开启打开菜单的动画
     */
    private void startOpenMenuAnima() {

    }

    /**
     * 启动取消的动画
     */
    private void startCancelMenuAnima() {

    }

    /**
     * 启动关闭菜单的动画
     */
    private void startCloseMenuAnima() {

    }

    /**
     * 点击的哪个菜单按钮
     *
     * @param x
     * @param y
     */
    private void clickWhichRectF(float x, float y) {

    }

    /**
     * 设置主菜单的背景以及打开/关闭图标
     *
     * @param mainMenuColor
     * @param openMenuRes   Res格式
     * @param closeMenuRes  Res格式
     * @return
     */
    private CircleMenu setMainMenu(int mainMenuColor, int openMenuRes, int closeMenuRes) {
        return this;
    }

    /**
     * @param mainMenuColor
     * @param openMenuBitmap  Bitmap格式
     * @param closeMenuBitmap Bitmap格式
     * @return
     */
    private CircleMenu setMainMenu(int mainMenuColor, Bitmap openMenuBitmap, Bitmap closeMenuBitmap) {
        return this;
    }

    /**
     * @param mainMenuColor
     * @param openMenuDrawable  Drawable格式
     * @param closeMenuDrawable Drawable格式
     * @return
     */
    private CircleMenu setMainMenu(int mainMenuColor, Drawable openMenuDrawable, Drawable closeMenuDrawable) {
        return this;
    }

    /**
     * 添加一个子菜单，
     *
     * @param menuColor
     * @param menuRes   子菜单图标，Resource格式
     * @return
     */
    public CircleMenu addSubMenu(int menuColor, int menuRes) {
        return this;
    }

    /**
     * @param menuColor
     * @param menuBitmap 子菜单图标，Bitmap 格式
     * @return
     */
    public CircleMenu addSubMenu(int menuColor, Bitmap menuBitmap) {
        return this;
    }

    /**
     * @param menuColor
     * @param menuDrawable 子菜单图标，Drawable 格式
     * @return
     */
    public CircleMenu addSubMenu(int menuColor, Drawable menuDrawable) {
        return this;
    }

    /**
     * 打开菜单
     */
    public void openMenu() {

    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {

    }

    /**
     * 菜单是否打来
     *
     * @return
     */
    public boolean isOpen() {
        return status == MEUN_STATUS_OPENED;
    }
}
