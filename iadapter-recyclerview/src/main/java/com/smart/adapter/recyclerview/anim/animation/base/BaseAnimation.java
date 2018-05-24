package com.smart.adapter.recyclerview.anim.animation.base;

import android.animation.Animator;
import android.view.View;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public interface BaseAnimation {
    Animator[] getAnimators(View view);
}
