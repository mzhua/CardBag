package com.wonders.xlab.cardbag;

/**
 * Created by hua on 16/9/21.
 */

public class CBagEventDataBean {
    private String event;
    private String name;
    private long timeInMill;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimeInMill() {
        return timeInMill;
    }

    public void setTimeInMill(long timeInMill) {
        this.timeInMill = timeInMill;
    }
}
