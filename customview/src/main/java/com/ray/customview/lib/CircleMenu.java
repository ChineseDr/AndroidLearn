package com.ray.customview.lib;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @ Created by Raymond on 2018/6/25 20:32
 */
public class CircleMenu extends View {
    private static final int MEUN_STATUS_CLOSED=1<<2;
    private int status;

    private Paint oPaint;
    private Paint cPaint;
    private Paint sPaint;
    public CircleMenu(Context context) {
        super(context);
    }

    public CircleMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        status=MEUN_STATUS_CLOSED;
    }

    //初始化画图工具
    private void initTools(){
        oPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        cPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        cPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
}
