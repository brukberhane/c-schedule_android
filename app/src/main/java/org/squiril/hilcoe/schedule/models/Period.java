package org.squiril.hilcoe.schedule.models;

import java.util.Calendar;

public class Period {

    public Period(String mCourseTitle, String mClassRoom, Calendar time) {
        this.mCourseTitle = mCourseTitle;
        this.mClassRoom = mClassRoom;
        this.mTime = time;
    }

    private String mCourseTitle;
    private String mClassRoom;
    private Calendar mTime;

    public String getCourseTitle() {
        return mCourseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        mCourseTitle = courseTitle;
    }

    public String getClassRoom() {
        return mClassRoom;
    }

    public void setClassRoom(String classRoom) {
        mClassRoom = classRoom;
    }

    public Calendar getTime() {
        return mTime;
    }

    public void setTime(Calendar time) {
        mTime = time;
    }
}
