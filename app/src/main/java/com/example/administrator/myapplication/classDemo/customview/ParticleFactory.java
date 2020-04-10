package com.example.administrator.myapplication.classDemo.customview;

import android.graphics.Bitmap;
import android.graphics.Rect;

//粒子管理类
public abstract class ParticleFactory {

    public abstract Particle[][] generateParticles(Bitmap bitmap, Rect bound);
}
