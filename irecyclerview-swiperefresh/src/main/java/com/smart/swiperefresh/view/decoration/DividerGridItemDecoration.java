package com.smart.swiperefresh.view.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * @author Administrator
 * @version ${Rev}
 * @time 2016/6/7 0007 δΈε 6:07
 * @des
 * @updateAuthor
 * @updateDate 2016/6/7 0007
 * @updateDes
 */
public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;

    public DividerGridItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {

        drawHorizontal(c, parent);
        drawVertical(c, parent);

    }

    private int getSpanCount(RecyclerView parent) {
        // εζ°
        int spanCount = -1;
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin + mDivider.getIntrinsicWidth();
            final int right = child.getRight() + params.rightMargin + mDivider.getIntrinsicWidth();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * check is first column
     *
     * @param parent     parent
     * @param pos        pos
     * @param spanCount  spanCount
     * @param childCount childCount
     * @return true or false
     */
    private boolean isFirstColumn(RecyclerView parent, int pos, int spanCount,
                                  int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            // GridLayoutManager
            //
            if ((pos + 1) % spanCount != 0) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            // StaggeredGridLayoutManager
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // ε¦ζζ―ζεδΈεοΌεδΈιθ¦η»εΆε³θΎΉ
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                // ε¦ζζ―ζεδΈεοΌεδΈιθ¦η»εΆε³θΎΉ
                if (pos >= childCount) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * check is last column
     *
     * @param parent     parent
     * @param pos        pos
     * @param spanCount  spanCount
     * @param childCount childCount
     * @return true or false
     */
    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount,
                                 int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            // ε¦ζζ―ζεδΈεοΌεδΈιθ¦η»εΆε³θΎΉ
            if ((pos + 1) % spanCount == 0) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // ε¦ζζ―ζεδΈεοΌεδΈιθ¦η»εΆε³θΎΉ
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                // ε¦ζζ―ζεδΈεοΌεδΈιθ¦η»εΆε³θΎΉ
                if (pos >= childCount) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * check is first row
     *
     * @param parent     parent
     * @param pos        pos
     * @param spanCount  spanCount
     * @param childCount childCount
     * @return true or false
     */
    private boolean isFirstRow(RecyclerView parent, int pos, int spanCount,
                               int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            //childCount = childCount - childCount % spanCount;
            // GridLayoutManager
            //if ((pos + 1) % childCount != 0) {
            if (pos <= spanCount) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager δΈηΊ΅εζ»ε¨
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // ε¦ζζ―ζεδΈθ‘οΌεδΈιθ¦η»εΆεΊι¨
                if (pos >= childCount)
                    return true;
            } else {
                // StaggeredGridLayoutManager δΈζ¨ͺεζ»ε¨
                // ε¦ζζ―ζεδΈθ‘οΌεδΈιθ¦η»εΆεΊι¨
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * check is last row
     *
     * @param parent     parent
     * @param pos        pos
     * @param spanCount  spanCount
     * @param childCount childCount
     * @return true or false
     */
    private boolean isLastRow(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            // ε¦ζζ―ζεδΈθ‘οΌεδΈιθ¦η»εΆεΊι¨
            if (pos >= childCount) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager δΈηΊ΅εζ»ε¨
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // ε¦ζζ―ζεδΈθ‘οΌεδΈιθ¦η»εΆεΊι¨
                if (pos >= childCount)
                    return true;
            } else {
                // StaggeredGridLayoutManager δΈζ¨ͺεζ»ε¨
                // ε¦ζζ―ζεδΈθ‘οΌεδΈιθ¦η»εΆεΊι¨
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition,
                               RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();

        int left = 0;
        int top = mDivider.getIntrinsicHeight();
        int right = 0;
        int bottom = 0;

        if (isFirstColumn(parent, itemPosition, spanCount, childCount)) {
            left = mDivider.getIntrinsicWidth() / 2;
            right = mDivider.getIntrinsicWidth();
        } else if (isLastColumn(parent, itemPosition, spanCount, childCount)) {
            left = mDivider.getIntrinsicWidth();
            right = mDivider.getIntrinsicWidth() / 2;
        }

        if (isFirstRow(parent, itemPosition, spanCount, childCount)) {
            top = mDivider.getIntrinsicHeight() / 2;
        }

        outRect.set(left, top, right, bottom);
    }
}

