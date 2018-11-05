package com.wordplat.ikvstockchart.marker;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;

import com.wordplat.ikvstockchart.align.YMarkerAlign;
import com.wordplat.ikvstockchart.entry.SizeColor;
import com.wordplat.ikvstockchart.render.AbstractRender;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/8/22.
 */

public class VolYAxisTextMarketView implements IMarkerView{
    private static final String TAG = "VolYAxisTextMarketView";

    private Paint markerTextPaint;
    private Paint markerBorderPaint;

    private final RectF contentRect = new RectF();
    private AbstractRender render;

    private final Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    private final float[] pointCache = new float[2];
    private final int height;
    private final RectF markerInsets = new RectF(0, 0, 0, 0);
    private float inset = 0;
    private YMarkerAlign yMarkerAlign;

    public VolYAxisTextMarketView(int height) {
        this.height = height;
    }

    @Override
    public void onInitMarkerView(RectF contentRect, AbstractRender render) {
        this.contentRect.set(contentRect);
        this.render = render;
        final SizeColor sizeColor = render.getSizeColor();

        if (markerTextPaint == null) {
            markerTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            markerTextPaint.setTextAlign(Paint.Align.CENTER);
        }

        markerTextPaint.setColor(sizeColor.getMarkerTextColor());
        markerTextPaint.setTextSize(sizeColor.getMarkerTextSize());
        markerTextPaint.getFontMetrics(fontMetrics);

        if (markerBorderPaint == null) {
            markerBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            markerBorderPaint.setStyle(Paint.Style.STROKE);
        }

        markerBorderPaint.setStrokeWidth(sizeColor.getMarkerBorderSize());
        markerBorderPaint.setColor(sizeColor.getMarkerBorderColor());
        markerBorderPaint.setStyle(Paint.Style.FILL);
        inset = markerBorderPaint.getStrokeWidth() / 2;
        yMarkerAlign = sizeColor.getYMarkerAlign();
    }

    @Override
    public void onDrawMarkerView(Canvas canvas, float highlightPointX, float highlightPointY) {
        if (contentRect.top < highlightPointY && highlightPointY < contentRect.bottom) {
            pointCache[0] = 0;
            pointCache[1] = highlightPointY;

            render.invertMapPoints(null, pointCache);
            String value = String.valueOf(pointCache[1]);

            float width = markerTextPaint.measureText(value) + 50;

            highlightPointY = highlightPointY - height / 2;
            if (highlightPointY < contentRect.top) {
                highlightPointY = contentRect.top;
            }
            if (highlightPointY > contentRect.bottom - height) {
                highlightPointY = contentRect.bottom - height;
            }

            if (yMarkerAlign == YMarkerAlign.LEFT) {
                markerInsets.left = contentRect.left + inset;

            } else if (yMarkerAlign == YMarkerAlign.RIGHT) {
                markerInsets.left = contentRect.right - width + inset;

            } else if (highlightPointX < contentRect.left + contentRect.width() / 3) {
                markerInsets.left = contentRect.right - width + inset;

            } else {
                markerInsets.left = contentRect.left + inset;
            }

            markerInsets.top = highlightPointY + inset;
            markerInsets.right = markerInsets.left + width - inset * 2;
            markerInsets.bottom = markerInsets.top + height - inset * 2;
            canvas.drawRect(markerInsets, markerBorderPaint);
            canvas.drawText(value,
                    markerInsets.left + markerInsets.width() / 2,
                    (markerInsets.top + markerInsets.bottom - fontMetrics.top - fontMetrics.bottom) / 2,
                    markerTextPaint);
            canvas.clipRect(markerInsets, Region.Op.XOR);
        }
    }
}
