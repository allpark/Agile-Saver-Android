package com.example.agilesavev2.utils;

import android.animation.ObjectAnimator;
import android.os.Looper;
import android.widget.LinearLayout;

import com.example.agilesavev2.R;

public class PlusButtonAnimation {

    public PlusButtonAnimation(LinearLayout hint) {
        final ObjectAnimator animation = ObjectAnimator.ofFloat(hint, "translationX", 10);
        animation.setDuration(1000);
        animation.setRepeatMode(animation.REVERSE);
        animation.setRepeatCount(5);
        animation.start();
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        hint.animate().alpha(0).setDuration(1000);
                    }
                },
                5000);

    }
}
