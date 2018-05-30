package com.ray.corelib.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SupportActivity;
import com.ray.corelib.delegates.IMallDelegate;

/**
 * 使用它的时候主Activity继承自它，所以把他声明为抽象类
 */
public abstract class ProxyActivity extends SupportActivity {

    public abstract IMallDelegate setRootDelegate();//返回根Delegate

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 根容器，一般用来容纳fragment的都是frameLayout
     * @param savedInstanceState
     */
    private void initContainer(@Nullable Bundle savedInstanceState){

    }
}
