package com.example.demo.common.util;

import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

    /**
     * 取指定日期零点
     *
     * @return
     */
    public static Date getDateStartTime(Date date) {
        Calendar calendar = new Calendar.Builder().setInstant(date).build();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 取指定日期零点
     *
     * @return
     */
    public static Date getDateZeroTime(Date date) {
        Calendar calendar = new Calendar.Builder().setInstant(date).build();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 取指定日期本月零点
     *
     * @return
     */
    public static Date getThisMounthTime(Date date) {
        Calendar calendar = new Calendar.Builder().setInstant(date).build();

        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 取指定日期上个月零点
     *
     * @return
     */
    public static Date getLastOneMounthTime(Date date) {
        Calendar calendar = new Calendar.Builder().setInstant(date).build();
        calendar.add(Calendar.DATE, -30);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 取指定日期下个月零点
     *
     * @return
     */
    public static Date getNextMounthTime(Date date) {
        Calendar calendar = new Calendar.Builder().setInstant(date).build();
        calendar.add(Calendar.DATE, 30);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }

    /**
     * 获取指定日期相加指定天数后的日期
     *
     * @return
     */
    public static Date getDateByAddDayOfMonth(Date date, Integer dayOfMonth) {
        if (null == date || null == dayOfMonth) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, dayOfMonth.intValue());
        return c.getTime();
    }

    public static void main(String[] args) {
        DateTimeUtil dateTimeUtil = new DateTimeUtil();
        System.out.println(dateTimeUtil.getLastOneMounthTime(new Date()));
        System.out.println(dateTimeUtil.getNextMounthTime(new Date()));
    }
}
