package org.squiril.hilcoe.schedule.models;

@SuppressWarnings("WeakerAccess")
public class Schedule {

    private ClassDay Monday;
    private ClassDay Tuesday;
    private ClassDay Wednesday;
    private ClassDay Thursday;
    private ClassDay Friday;
    private ClassDay Saturday;
    private String mScheduleType;

    public ClassDay getMonday() {
        return Monday;
    }

    public void setMonday(ClassDay monday) {
        Monday = monday;
    }

    public ClassDay getTuesday() {
        return Tuesday;
    }

    public void setTuesday(ClassDay tuesday) {
        Tuesday = tuesday;
    }

    public ClassDay getWednesday() {
        return Wednesday;
    }

    public void setWednesday(ClassDay wednesday) {
        Wednesday = wednesday;
    }

    public ClassDay getThursday() {
        return Thursday;
    }

    public void setThursday(ClassDay thursday) {
        Thursday = thursday;
    }

    public ClassDay getFriday() {
        return Friday;
    }

    public void setFriday(ClassDay friday) {
        Friday = friday;
    }

    public ClassDay getSaturday() {
        return Saturday;
    }

    public void setSaturday(ClassDay saturday) {
        Saturday = saturday;
    }

    public String getScheduleType() {
        return mScheduleType;
    }

    public void setScheduleType(String scheduleType) {
        mScheduleType = scheduleType;
    }
}
