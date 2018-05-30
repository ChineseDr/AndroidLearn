package com.ray.corelib.proxies;
/**
 * 使用Fragmentation库
 * https://github.com/YoKeyword/Fragmentation/wiki
 * ButterKnife
 * https://github.com/JakeWharton/butterknife
 * http://jakewharton.github.io/butterknife/
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation.ISupportFragment;

public abstract class BaseProxy extends Fragment implements ISupportFragment {
    /*
     * 为了解决getActivity()空指针问题
     * 在Fragment基类里设置一个Activity mActivity的全局变量，
     * 在onAttach(Activity activity)里赋值，使用mActivity代替getActivity()，
     * 保证Fragment即使在onDetach后，仍持有Activity的引用（有引起内存泄露的风险，
     * 但是异步任务没停止的情况下，本身就可能已内存泄漏，相比Crash，这种做法“安全”些），
     *
     */
    protected FragmentActivity _mActivity=null;

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle saveInstanceState, @NonNull View rootView);

    //创建视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }
        onBindView(savedInstanceState,rootView);
        return rootView;
    }
}
