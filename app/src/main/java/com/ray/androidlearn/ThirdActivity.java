package com.ray.androidlearn;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class ThirdActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        replaceFragment(new RightFragment());
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager=getFragmentManager();
        FragmentTransaction tansaction=manager.beginTransaction();
        //tansaction.replace(R.id.right_layout,fragment);
        tansaction.add(R.id.right_layout,fragment);
        tansaction.commit();
    }
}
