package com.wordplat.ikvstockchart.drawing;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.wordplat.ikvstockchart.align.YLabelAlign;
import com.wordplat.ikvstockchart.entry.SizeColor;
import com.wordplat.ikvstockchart.render.AbstractRender;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/8/22.
 */

public class VolumeYLabelDrawing implements IDrawing {
    private Paint yLabelPaint; // Y 轴标签的画笔
    private final Paint.FontMetrics fontMetrics = new Paint.FontMetrics(); // 用于 labelPaint 计算文字位置

    private final RectF indexRect = new RectF();

    private YLabelAlign yLabelAlign; // Y 轴标签对齐方向

    @Override
    public void onInit(RectF contentRect, AbstractRender render) {
        final SizeColor sizeColor = render.getSizeColor();

        if (yLabelPaint == null) {
            yLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            yLabelPaint.setTextSize(sizeColor.getYLabelSize());
        }
        yLabelPaint.setColor(sizeColor.getYLabelColor());
        yLabelPaint.getFontMetrics(fontMetrics);
        yLabelAlign = sizeColor.getYLabelAlign();
        if (yLabelAlign == YLabelAlign.RIGHT) {
            yLabelPaint.setTextAlign(Paint.Align.RIGHT);
        }

        indexRect.set(contentRect);
    }

    @Override
    public void computePoint(int minIndex, int maxIndex, int currentIndex) {

    }

    @Override
    public void onComputeOver(Canvas canvas, int minIndex, int maxIndex, float minY, float maxY) {
        float labelX = yLabelAlign == YLabelAlign.LEFT ? indexRect.left + 5 : indexRect.right - 5;

        canvas.drawText(
                String.valueOf((int) maxY),
                labelX,
                indexRect.top - fontMetrics.top,
                yLabelPaint);

        canvas.drawText(
                String.valueOf(0),
                labelX,
                indexRect.bottom - fontMetrics.bottom,
                yLabelPaint);
    }

    @Override
    public void onDrawOver(Canvas canvas) {

    }
}
