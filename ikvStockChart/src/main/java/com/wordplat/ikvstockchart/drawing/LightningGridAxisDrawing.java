package com.wordplat.ikvstockchart.drawing;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.wordplat.ikvstockchart.compat.ViewUtils;
import com.wordplat.ikvstockchart.entry.EntrySet;
import com.wordplat.ikvstockchart.entry.SizeColor;
import com.wordplat.ikvstockchart.render.AbstractRender;
import com.wordplat.ikvstockchart.render.LightningRender;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/8/30.
 */

public class LightningGridAxisDrawing implements IDrawing {
    private Paint yLabelPaint; // Y 轴标签的画笔
    private Paint axisPaint; // X 轴和 Y 轴的画笔
    private Paint gridPaint; // k线图网格线画笔
    private final RectF lightningRect = new RectF(); // K 线图显示区域
    private LightningRender render;
    private float lineHeight;
    private int entrySetSize;
    private final Paint.FontMetrics fontMetrics = new Paint.FontMetrics(); // 用于 labelPaint 计算文字位置
    private final float[] pointCache = new float[2];
    private DecimalFormat decimalFormatter;
    private int lastDecimalPlaces = -1;

    @Override
    public void onInit(RectF contentRect, AbstractRender render) {
        this.render = (LightningRender) render;
        final SizeColor sizeColor = render.getSizeColor();
        // TODO: 2018/8/29 需要优化滴
        int decimalPlaces = render.getDecimalPlaces();
        if(lastDecimalPlaces!=decimalPlaces){
            lastDecimalPlaces = decimalPlaces;
            decimalFormatter = ViewUtils.getDecimalFormat(decimalFormatter,decimalPlaces);
        }
        if (axisPaint == null) {
            axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }
        axisPaint.setStyle(Paint.Style.STROKE);
        axisPaint.setStrokeWidth(sizeColor.getAxisSize());
        axisPaint.setColor(sizeColor.getAxisColor());

        if (gridPaint == null) {
            gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(sizeColor.getAxisSize());
        gridPaint.setColor(sizeColor.getAxisColor());
        gridPaint.setPathEffect(new DashPathEffect(
                new float[]{8, 8, 8, 8}, 1));

        if (yLabelPaint == null) {
            yLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }
        yLabelPaint.setTextAlign(Paint.Align.RIGHT);
        yLabelPaint.setTextSize(sizeColor.getYLabelSize());
        yLabelPaint.setColor(sizeColor.getYLabelColor());
        yLabelPaint.getFontMetrics(fontMetrics);

        lightningRect.set(contentRect);
        lineHeight = lightningRect.height() / 4;
    }

    @Override
    public void computePoint(int minIndex, int maxIndex, int currentIndex) {

    }

    @Override
    public void onComputeOver(Canvas canvas, int minIndex, int maxIndex, float minY, float maxY) {
        final EntrySet entrySet = render.getEntrySet();
        entrySetSize = entrySet.getEntryList().size();
        // 绘制 最外层大框框
        canvas.drawRect(lightningRect, axisPaint);
        // 绘制 三条横向网格线
        for (int i = 0; i < 3; i++) {
            float lineTop = lightningRect.top + (i + 1) * lineHeight;
            canvas.drawLine(lightningRect.left, lineTop, lightningRect.right, lineTop, gridPaint);
        }

    }

    @Override
    public void onDrawOver(Canvas canvas) {
        if (entrySetSize < 1) return;
        for (int i = 0; i < 5; i++) {
            float lineTop = lightningRect.top + i * lineHeight;
            pointCache[1] = lineTop;
            render.invertMapPoints(pointCache);
            Log.d("pointCache",pointCache[1]+"");
//            String value = decimalFormatter.format(pointCache[1]);
            String value = String.valueOf(pointCache[1]);
            if (i == 0) {
                pointCache[0] = lineTop - fontMetrics.top;
            } else if (i == 4) {
                pointCache[0] = lineTop - fontMetrics.bottom;
            } else {
                pointCache[0] = lineTop + fontMetrics.bottom;
            }

            canvas.drawText(value, lightningRect.right, pointCache[0], yLabelPaint);
        }
    }
}
