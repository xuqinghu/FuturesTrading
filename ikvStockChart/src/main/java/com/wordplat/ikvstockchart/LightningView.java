package com.wordplat.ikvstockchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wordplat.ikvstockchart.compat.ViewUtils;
import com.wordplat.ikvstockchart.entry.EntrySet;
import com.wordplat.ikvstockchart.render.AbstractRender;
import com.wordplat.ikvstockchart.render.LightningRender;

/**
 * Created by Administrator on 2018/8/30.
 */

public class LightningView extends View {
    private static final String TAG = "LightningView";
    private static final boolean DEBUG = true;
    // 与视图大小相关的属性
    private RectF viewRect;
    // 与数据加载、渲染相关的属性
    private AbstractRender render;
    private EntrySet entrySet;
    private float viewPadding;

    public LightningView(Context context) {
        super(context, null);
    }

    public LightningView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LightningView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewRect = new RectF();
        viewPadding = ViewUtils.dpTopx(context, 5);
        render = new LightningRender();
        if (android.os.Build.VERSION.SDK_INT >= 11) {
//            setLayerType(LAYER_TYPE_SOFTWARE, null);
        }
    }

    public void setEntrySet(EntrySet set) {
        entrySet = set;
    }

    public void setRender(AbstractRender render) {
        render.setSizeColor(this.render.getSizeColor());
        this.render = render;
    }

    public AbstractRender getRender() {
        return render;
    }

    public void notifyDataSetChanged() {
        notifyDataSetChanged(true);
    }

    public void notifyDataSetChanged(boolean invalidate) {
        render.setViewRect(viewRect);
        render.onViewRect(viewRect);
        render.setEntrySet(entrySet);
        if (invalidate) {
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        viewRect.set(viewPadding, viewPadding, w - viewPadding, h);
        // 在 Android Studio 预览模式下，添加一些测试数据，可以把 K 线图预览出来
        if (isInEditMode()) {
            EntrySet entrySet = new EntrySet();
            if (entrySet != null) {
                entrySet.computeStockIndex();
            }

            setEntrySet(entrySet);
        }
        if (entrySet == null) {
            entrySet = new EntrySet();
        }

        notifyDataSetChanged();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        render.render(canvas);
    }
}
