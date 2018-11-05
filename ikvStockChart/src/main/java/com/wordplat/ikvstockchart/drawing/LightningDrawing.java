package com.wordplat.ikvstockchart.drawing;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.wordplat.ikvstockchart.compat.ViewUtils;
import com.wordplat.ikvstockchart.entry.Entry;
import com.wordplat.ikvstockchart.entry.EntrySet;
import com.wordplat.ikvstockchart.entry.SizeColor;
import com.wordplat.ikvstockchart.render.AbstractRender;

import java.text.DecimalFormat;

/**
 * 闪电图绘制
 * Created by Administrator on 2018/8/30.
 */

public class LightningDrawing implements IDrawing {
    private Paint linePaint;
    private Paint circlePaint;
    private Paint textPaint;
    private final RectF chartRect = new RectF();//闪电图显示区域
    private AbstractRender render;
    private float[] lineBuffer = new float[4];
    private float [] point = new float[2];
    //滑块高度
    private float rectHeight;
    //滑块宽度
    private float rectWidth;
    private float textSize;
    private float radiusSize;
    private DecimalFormat decimalFormatter;
    private int lastDecimalPlaces = -1;

    @Override
    public void onInit(RectF contentRect, AbstractRender render) {
        this.render = render;
        final SizeColor sizeColor = render.getSizeColor();
        int decimalPlaces = render.getDecimalPlaces();
        if(lastDecimalPlaces!=decimalPlaces){
            lastDecimalPlaces = decimalPlaces;
            decimalFormatter = ViewUtils.getDecimalFormat(decimalFormatter,decimalPlaces);
        }
        rectHeight = sizeColor.getRectHeight();
        textSize = sizeColor.getTextSize();
        radiusSize = sizeColor.getRadiusSize();
        if (linePaint == null) {
            linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            linePaint.setStyle(Paint.Style.FILL);
            linePaint.setStrokeWidth(sizeColor.getTimeLineSize());
            linePaint.setColor(sizeColor.getTimeLineColor());
        }
        if(circlePaint == null){
            circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            circlePaint.setStyle(Paint.Style.FILL);
            circlePaint.setColor(Color.RED);
        }
        if(textPaint ==null){
            textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(textSize);
            textPaint.setTextAlign(Paint.Align.CENTER);
        }
        chartRect.set(contentRect);
    }

    @Override
    public void computePoint(int minIndex, int maxIndex, int currentIndex) {
        final int count = (maxIndex - minIndex) * 4;
        if (lineBuffer.length < count) {
            lineBuffer = new float[count];
        }
        final EntrySet entrySet = render.getEntrySet();
        final Entry entry = entrySet.getEntryList().get(currentIndex);
        final int i = currentIndex - minIndex;
        if (currentIndex < maxIndex) {
            lineBuffer[i * 4 + 0] = currentIndex;
            lineBuffer[i * 4 + 1] = entry.getClose();
            lineBuffer[i * 4 + 2] = currentIndex + 1;
            lineBuffer[i * 4 + 3] = entrySet.getEntryList().get(currentIndex + 1).getClose();
            Log.d("lineBuffer",entry.getClose()+"");
            if(currentIndex+1==maxIndex){
                point [0] = currentIndex + 1;
                point [1] = entrySet.getEntryList().get(currentIndex + 1).getClose();
            }
        }else {
            point [0] = currentIndex;
            point [1] = entrySet.getEntryList().get(currentIndex).getClose();
        }
    }

    @Override
    public void onComputeOver(Canvas canvas, int minIndex, int maxIndex, float minY, float maxY) {
        final int count = (maxIndex - minIndex) * 4;
        if(count<0) return;
        render.mapPoints(lineBuffer);
        String y = decimalFormatter.format(point[1]);
        rectWidth = textPaint.measureText(y)+40;
        render.mapPoints(point);
        // 使用 drawLines 方法比依次调用 drawLine 方法要快
        canvas.drawLines(lineBuffer, 0, count, linePaint);
        canvas.drawLine(point[0],point[1],chartRect.right,point[1],linePaint);
        canvas.drawCircle(point[0],point[1],radiusSize,circlePaint);
        Rect rect =new Rect((int)(chartRect.right-rectWidth),(int) (point[1]+rectHeight),(int)chartRect.right,(int)(point[1]-rectHeight));
        canvas.drawRect(rect,linePaint);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        int baseLineY = (int) (rect.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式
        render.invertMapPoints(point);
        canvas.drawText(y,rect.centerX(),baseLineY,textPaint);
    }

    @Override
    public void onDrawOver(Canvas canvas) {

    }
}
