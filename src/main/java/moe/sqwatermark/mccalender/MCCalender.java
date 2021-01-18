package moe.sqwatermark.mccalender;

public class MCCalender {

    // MC中的第几天
    private int date;
    // 小时
    private int hour;
    // 分钟
    private int minute;

    public MCCalender() {
    }

    protected void setDate(int date) {
        this.date = date;
    }

    protected void setHour(int hour) {
        this.hour = hour;
    }

    protected void setMinute(int minute) {
        this.minute = minute;
    }

    public int getDate() {
        return date;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}
