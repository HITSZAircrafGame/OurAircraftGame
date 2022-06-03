package com.example.aircraftgame;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

public interface ButtonAnimation {
    default void initButton(Button bt){
        Animation animation = new AlphaAnimation(1.0f,0.0f);
        animation.setDuration(300);
        bt.startAnimation(animation);
    }
}
