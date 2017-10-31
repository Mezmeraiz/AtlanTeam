package com.mezmeraiz.atlanteam;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by max on 31.10.17.
 */

public class SpaceDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    public SpaceDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = verticalSpaceHeight;
        }
        outRect.bottom = verticalSpaceHeight;
        outRect.right = verticalSpaceHeight;
        outRect.left = verticalSpaceHeight;
    }
}