package com.example.agilesavev2.models.ai;
import java.util.List;

public class LineGraphData {

    private List<Point> linePoints;

    private Point lineOfBestFitStart;
    private Point lineOfBestFitEnd;

    private float domainXMax;
    private float domainYMax;

    private float gradient;
    private float y_intercept;

    public float getDomainXMax(){
        return domainXMax;
    }
    public float getDomainYMax(){
        return domainYMax;
    }
    public float getComputedGradient(){
        return gradient;
    }
    public float getComputedYIntercept(){
        return y_intercept;
    }
    public Point getLineOfBestFitStart(){
        return lineOfBestFitStart;
    }
    public Point getLineOfBestFitEnd(){
        return lineOfBestFitEnd;
    }
    public List<Point> getLinePoints(){
        return linePoints;
    }


    @Override
    public String toString() {
        return "Points size: "  + (linePoints != null ? linePoints.size() : 0)+  " ... y=m"+gradient+"x+"+y_intercept;
    }
}
