package com.example.administrator.myapplication.classDemo.customview;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class FailingParticleFactory extends ParticleFactory {
    //小球默认宽高
    public static final int PART_WH = 14;

    @Override
    public Particle[][] generateParticles(Bitmap bitmap, Rect bound) {
        int w = bound.width();
        int h = bound.height();

        int part_w_count = w / PART_WH;   //横向个数（列数）
        int part_h_count = h / PART_WH;   //纵向个数 (行数）
        part_w_count = part_w_count > 0 ? part_w_count : 1;
        part_h_count = part_h_count > 0 ? part_h_count : 1;

        int bitmap_part_w = bitmap.getWidth() / part_w_count;   //列
        int bitmap_part_h = bitmap.getHeight() / part_h_count; //行

        Particle[][] particles = new Particle[part_h_count][part_w_count];
        for(int row = 0;row < part_h_count;row++){
            for(int column = 0;column < part_w_count;column++){
                //取得当前粒子所在位置的颜色
                int color = bitmap.getPixel(column*bitmap_part_w,row*bitmap_part_h);
                float x = bound.left + PART_WH  * column;
                float y = bound.right + PART_WH  * row;
                //关联粒子对象
                particles[row][column] = new FallingParticle(x,y,color,bound);
            }
        }
        return particles;
    }
}
