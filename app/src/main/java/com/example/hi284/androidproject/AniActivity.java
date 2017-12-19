package com.example.hi284.androidproject;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

public class AniActivity extends AppCompatActivity {

    final String TAG = "AnimationTest";
    ImageView Ani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ani);

        Ani=findViewById(R.id.animation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startFireValuePropertyAnimation();

    }

    private void startFireValuePropertyAnimation() {
        ValueAnimator AlphaAnimator = ValueAnimator.ofFloat(0,1);
        AlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator){
                float value = (float) valueAnimator.getAnimatedValue();
                Ani.setAlpha(value);
            }
        });

        ValueAnimator scaleAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                Ani.setScaleX(value);
                Ani.setScaleY(value);
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.play(AlphaAnimator).after(scaleAnimator);
        //animatorSet.setStartDelay(2000);
        animatorSet.setDuration(2000);
        animatorSet.start();




    }
}
