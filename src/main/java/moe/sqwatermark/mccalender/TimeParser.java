package moe.sqwatermark.mccalender;

public class TimeParser {

    int TICK_OF_A_DAY = 24000;
    int TICK_OF_AN_HOUR = 1000;
    int TICK_OF_24_00 = 18000;
    double TICK_OF_A_MINUTE = 16.6666666667;
    boolean dayStartAtMidnight = false;

    public MCCalender parse(long time) {
        MCCalender mcCalender = new MCCalender();
        if (dayStartAtMidnight) {
            mcCalender.setDate((int)((time + TICK_OF_A_DAY - TICK_OF_24_00) / TICK_OF_A_DAY));
        } else {
            mcCalender.setDate((int)(time / TICK_OF_A_DAY));
        }
        int timeOfDay = (int)((time + TICK_OF_A_DAY - TICK_OF_24_00) % TICK_OF_A_DAY);
        mcCalender.setHour(timeOfDay / TICK_OF_AN_HOUR);
        mcCalender.setMinute((int)((timeOfDay % TICK_OF_AN_HOUR) / TICK_OF_A_MINUTE));
        return mcCalender;
    }

    public void setDayStartAtMidnight() {
        this.dayStartAtMidnight = true;
    }
}