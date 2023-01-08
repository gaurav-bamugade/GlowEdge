package com.example.glowedgecard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class GlowEdgeCardView extends CardView {
    static CardView baseCard;
    static CardView frontCard;
    LinearLayout linear1;
    View rel1, rel2;
    static int cornerRadius;
    int rotateDuration ;
    static int animationColorType;
    static int animationRotateType;
    static Animation rotateTop, rotateBot;
    static int glowWidth;


    public GlowEdgeCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getRootView().setBackgroundResource(R.drawable.round_corner_card);
        inititalizeAttrs(context, attrs);
        baseCard = new CardView(context);
        frontCard = new CardView(context);
        CardView.LayoutParams baseCardParams = new CardView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        CardView.LayoutParams frontCardParams = new CardView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(baseCard);
        baseCard.setRadius(getCornerRadius());
        baseCard.setLayoutParams(baseCardParams);
        baseCard.setPreventCornerOverlap(true);

        linear1 = new LinearLayout(context);
        rel1 = LayoutInflater.from(context).inflate(
                R.layout.top_relative_layout, null);
        rel2 = LayoutInflater.from(context).inflate(
                R.layout.bottom_relative_layout, null);
        linearLayoutAttrs(linear1, rel1, rel2);
        baseCard.addView(linear1);

        frontCardParams.setMargins(
                getGlowWidth(),
                getGlowWidth(),
                getGlowWidth(),
                getGlowWidth());
        frontCard.setRadius( getCornerRadius());
        frontCard.setLayoutParams(frontCardParams);
        baseCard.addView(frontCard);
        getAnimationRotateType(context,rel1,rel2);
    }
    private void inititalizeAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GlowEdgeCardView, 0, 0);
        cornerRadius = a.getInt(R.styleable.GlowEdgeCardView_ge_Card_Corner_Radius, 10);
        rotateDuration = a.getInt(R.styleable.GlowEdgeCardView_ge_Rotation_Speed, 6000);
        animationColorType = a.getInt(R.styleable.GlowEdgeCardView_ge_Animation_Color_type, 0);
        animationRotateType=a.getInt(R.styleable.GlowEdgeCardView_ge_Animation_mode,1);
        glowWidth=a.getInt(R.styleable.GlowEdgeCardView_ge_Width,10);
        a.recycle();
    }

    private void linearLayoutAttrs(LinearLayout linear1, View rel1, View rel2) {
        linear1.setOrientation(LinearLayout.VERTICAL);
        linear1.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight,
                                       int oldBottom) {
                if (left == 0 && top == 0 && right == 0 && bottom == 0) {
                    return;
                } else {//right=width bottom = height
                    LinearLayout.LayoutParams linearLayoutParamsRel1 = new LinearLayout.LayoutParams(right + right / 2, bottom);
                    linearLayoutParamsRel1.setMargins(-right, -bottom / 2, 0, 0);
                    linearLayoutParamsRel1.weight = 1;
                    rel1.setBackgroundColor(  getAnimationColorType());
                    rel1.setLayoutParams(linearLayoutParamsRel1);

                    LinearLayout.LayoutParams linearLayoutParamsRel2 = new LinearLayout.LayoutParams(right + right / 2, bottom);
                    linearLayoutParamsRel2.setMargins(right / 2, 0, 0, -bottom / 2);
                    linearLayoutParamsRel2.weight = 1;
                    rel2.setBackgroundColor( getAnimationColorType());
                    rel2.setLayoutParams(linearLayoutParamsRel2);
                }
            }
        });
        linear1.addView(rel1);
        linear1.addView(rel2);
    }

    //ANIMATION COLOR TYPE
    public static int getAnimationColorType() {
        int selectedColor=0;
        switch (animationColorType) {
            case 0: selectedColor = Color.RED;
                return selectedColor;

            case 1:
                selectedColor = Color.GREEN;
                return selectedColor;

            case 2:
                selectedColor = Color.BLUE;
                return selectedColor;
        }
        return selectedColor;
    }
    public static void setAnimationColorType(int animationColorType) {
        GlowEdgeCardView.animationColorType = animationColorType;
    }

    //EDGE WIDTH
    public static int getGlowWidth() {
        return glowWidth;
    }
    public static void setGlowWidth(int glowWidth) {
        GlowEdgeCardView.glowWidth = glowWidth;
    }

    //CARD CORNER RADIUS
    public static int getCornerRadius() {
        return cornerRadius;
    }
    public static void setCornerRadius(int cornerRadius) {
        GlowEdgeCardView.cornerRadius = cornerRadius;
    }

    //ROTATE SPEED/DURATION
    public int getRotateDuration() {
        return rotateDuration;
    }
    public void setRotateDuration(int rotateDuration) {
        this.rotateDuration = rotateDuration;
    }

    //EDGE ROTATION TYPE
    public int getAnimationRotateType(Context context, View rel1, View rel2) {
        if (animationRotateType == 0) {
            rotateTop = AnimationUtils.loadAnimation(context,
                    R.anim.rotate_from_top_clockwise);
            rotateBot = AnimationUtils.loadAnimation(context,
                    R.anim.rotate_from_bottom_clockwise);
            rotateTop.setDuration(getRotateDuration());
            rotateBot.setDuration(getRotateDuration());
            rel1.startAnimation(rotateTop);
            rel2.startAnimation(rotateBot);


        } else if (animationRotateType == 1) {
            rotateTop = AnimationUtils.loadAnimation(context,
                    R.anim.rotate_from_top_anticlockwise);
            rotateBot = AnimationUtils.loadAnimation(context,
                    R.anim.rotate_from_bottom_anticlockwise);
            rotateTop.setDuration(getRotateDuration());
            rotateBot.setDuration(getRotateDuration());
            rel1.startAnimation(rotateTop);
            rel2.startAnimation(rotateBot);
        }
        return animationRotateType;

    }
    public static void setAnimationRotateType(int animationRotateType) {
        GlowEdgeCardView.animationRotateType = animationRotateType;
    }
}
