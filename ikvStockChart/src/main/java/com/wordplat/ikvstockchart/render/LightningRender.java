package com.wordplat.ikvstockchart.render;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;

import com.wordplat.ikvstockchart.drawing.IDrawing;
import com.wordplat.ikvstockchart.drawing.LightningDrawing;
import com.wordplat.ikvstockchart.drawing.LightningGridAxisDrawing;
import com.wordplat.ikvstockchart.entry.Entry;
import com.wordplat.ikvstockchart.entry.EntrySet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/30.
 */

public class LightningRender extends AbstractRender{
    private final RectF chartRect = new RectF(); // 闪电图显示区域
    private final List<IDrawing> drawingList = new ArrayList<>();
    private final float[] contentPts = new float[2];
    private final float[] extremumY = new float[2];
    public LightningRender(){
        drawingList.add(new LightningGridAxisDrawing());
        drawingList.add(new LightningDrawing());
    }
    @Override
    public void onViewRect(RectF viewRect) {
        chartRect.set(viewRect);
        for (IDrawing drawing : drawingList) {
            drawing.onInit(chartRect, this);
        }
    }

    @Override
    public void setEntrySet(EntrySet entrySet) {
        super.setEntrySet(entrySet);
        postMatrixTouch(chartRect.width()-200, 100);
        getMinY(entrySet);
        String smallestFluctuation = entrySet.getSmallestFluctuation();
        if(TextUtils.isEmpty(smallestFluctuation)){
            return;
        }
        Log.d("getMinY",extremumY[0]+"******"+extremumY[1]);
        if(extremumY[1]==0){
//            computeExtremumValue(extremumY, extremumY[0]+1, extremumY[1]+1);
            postMatrixValue(chartRect.width()-200, chartRect.height(), extremumY[0]-2*Float.parseFloat(smallestFluctuation),Float.parseFloat(smallestFluctuation)*4);
        }else {
//            computeExtremumValue(extremumY, extremumY[0], extremumY[1]);
            postMatrixValue(chartRect.width()-200, chartRect.height(), extremumY[0]-extremumY[1]/2, extremumY[1]*2);
        }
        postMatrixOffset(chartRect.left, chartRect.top);
    }

    private void getMinY(EntrySet entrySet){
        List<Entry> entries=entrySet.getEntryList();
        Log.d("entries",entries.size()+"");
        if(entries==null||entries.size()==0) return;
        float minY = entries.get(0).getClose();
        float maxY = entries.get(0).getClose();
        for (int i=1;i<entries.size();i++){
            if(entries.get(i).getClose()<minY){
                minY = entries.get(i).getClose();
            }
            if(entries.get(i).getClose()>maxY){
                maxY = entries.get(i).getClose();
            }
        }
        BigDecimal b1 = new BigDecimal(Float.toString(maxY));
        BigDecimal b2 = new BigDecimal(Float.toString(minY));
        extremumY [0] = minY;
        extremumY [1] = b1.subtract(b2).floatValue();
    }

    @Override
    public void zoomIn(float x, float y) {

    }

    @Override
    public void zoomOut(float x, float y) {

    }

    @Override
    public void zoomDefault(float x, float y) {

    }

    @Override
    public void render(Canvas canvas) {
        contentPts[0] = chartRect.left;
        contentPts[1] = 0;
        invertMapPoints(contentPts);
        final int count = entrySet.getEntryList().size();
        final int lastIndex = count - 1;
        for (int i = 0 ; i < count ; i++) {
            for (IDrawing drawing : drawingList) {
                drawing.computePoint(0, lastIndex, i);
            }
        }

        for (IDrawing drawing : drawingList) {
            drawing.onComputeOver(canvas, 0, lastIndex, entrySet.getMinY(), entrySet.getMaxY());
        }

        for (IDrawing drawing : drawingList) {
            drawing.onDrawOver(canvas);
        }

    }
}
