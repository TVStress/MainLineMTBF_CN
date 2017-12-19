package com.letv.cases.leui;
import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;

import org.junit.After;
import org.junit.Test;
import org.junit.Assert;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;


import java.util.Date;
import java.util.Calendar;

import java.text.SimpleDateFormat;

public class CalendarStress extends LetvTestCase{
    int count = 0;

    @Test
    @CaseName("多次进入日历应用，查看日历界面显示")
    public void testEntryCalendar() throws UiObjectNotFoundException, RemoteException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            try{
                EntryCalendar();
            }catch (Exception e){
                try{
                    count ++;
                    failCount(count,getIntParams("Loop"),e.getMessage());
                    EntryCalendar();
                }catch(RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void EntryCalendar() throws UiObjectNotFoundException,RemoteException{
        addStep("打开日历App");
        launchApp(AppName.Calendar, IntentConstants.Calendar);
        int sysDate[] = {0,0,0};
        int calendarDate[] = {0,0,0};
        boolean isToday = checkCurrentDate(sysDate,calendarDate);
        verify("It is doesn't show today",isToday);
        press_back(3);
    }

    @Test
    @CaseName("应用桌面操作日历应用")
    public void testOpertionCalendar() throws UiObjectNotFoundException, RemoteException {

        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            addStep(".............looper : " + Loop);
            addStep("进入应用界面");
            gotoHomeScreen("应用");

            addStep("进入日历");
            UiObject2 calendar=null;
            for(int i = 0;i < 3;i++)
            {
                calendar = waitForObj(By.res("com.stv.plugin.app:id/cellview_label").text(Pattern.compile("日历|Calendar")));
                if (calendar == null)
                {
                    press_down(4);
                    calendar = waitForObj(By.res("com.stv.plugin.app:id/cellview_label").text(Pattern.compile("日历|Calendar")));
                    if (calendar != null)
                        break;
                }
            }
            if (calendar == null)
            {
                launchApp(AppName.Calendar, IntentConstants.Calendar);
            }else
            {
                verify("can not find calendar in app",calendar != null);
                calendar.click();
                calendar.click();
                sleepInt(1);
            }
            addStep("按菜单键");
            press_menu(1);
            addStep("按返回键退出");
           press_back(2);

        }
    }

    @Test
    @CaseName("切换不同日期后，进入日历应用，查看日历界面显示")
    public void testCalendarSwitchDate() throws UiObjectNotFoundException, RemoteException {

        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            //Calendar();
            try {
                addStep("打开日历App");
                launchApp(AppName.Calendar, IntentConstants.Calendar);
                Calendar(Loop);
                addStep("检查是否是当日的日期");
                int sysDate[] = {0,0,0};
                int calendarDate[] = {0,0,0};
                boolean isToday = checkCurrentDate(sysDate,calendarDate);
                verify("It is doesn't show today",isToday);
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("打开日历App");
                    launchApp(AppName.Calendar, IntentConstants.Calendar);
                    Calendar(Loop);
                    addStep("检查是否是当日的日期");
                    int sysDate[] = {0,0,0};
                    int calendarDate[] = {0,0,0};
                    boolean isToday = checkCurrentDate(sysDate,calendarDate);
                    verify("It is doesn't show today",isToday);
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
        addStep("退出日历");
        press_back(2);
    }

    public void Calendar(int Loop) throws UiObjectNotFoundException, RemoteException {
        press_menu(1);
        sleepInt(1);
        UiObject2 returnToday = waitForObj(By.clazz("android.widget.TextView").text("回到今天"));
        check("not open menu of calendar", returnToday!=null);
        press_up(2);
        press_down(1);
        UiObject2 selectDate1 = waitForObj(By.clazz("android.widget.TextView").text("选择日期"));
        check("select date not focused", selectDate1.isSelected());
        press_center(1);
        sleepInt(1);
        addStep("更改日期");
        press_down(2);
        addStep("已按向下键");
        sleepInt(2);
        UiObject2 check = waitForObj(By.clazz("android.widget.Button").text("农历"));
        check("select date not focused", check!=null);
        if (Loop % 2 == 0)
        {
            press_right(1);
            press_up(GenerateRandom(18));
            sleepInt(5);
            press_right(1);
            press_down(GenerateRandom(12));
            sleepInt(5);
            press_right(1);
            press_up(GenerateRandom(30));
            press_center(1);
            sleepInt(2);
        }
        else {
            press_right(1);
            press_down(GenerateRandom(18));
            sleepInt(5);

            press_right(1);
            press_up(GenerateRandom(12));
            sleepInt(5);
            press_right(1);
            press_down(GenerateRandom(30));
            sleepInt(5);
            press_center(1);
            sleepInt(1);
        }

    }

    private void GetSysCurrentDataTime(int sysDate[]){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        String nowDate = dateFormat.format( now );
        System.out.println(nowDate);
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        sysDate[0] = c.get(Calendar.YEAR);
        sysDate[1] = c.get(Calendar.MONTH) + 1;
        sysDate[2] = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        System.out.println(sysDate[0] + "/" + sysDate[1] + "/" + sysDate[2] + " " +hour + ":" +minute + ":" + second);
        addStep("获取到当前系统日期及时间为__" + sysDate[0] + "/" + sysDate[1] + "/" + sysDate[2] + " " +hour + ":" +minute + ":" + second);

    }
    public void GetCalendarCurrentDate( int calendarDate[]){
        addStep("打开日历");
        launchApp(AppName.Calendar, IntentConstants.Calendar);
        addStep("检查日期是否是当期日期");
        UiObject2 currentMonth = waitForObj(By.res("com.stv.calendar:id/home_date"));
        check("don't show current year and month", currentMonth != null);
        String cur= currentMonth.getText();
        String curDate;
        String curMonth;
        String curYear;    //2016.12
        curYear = cur.substring(0,4);
        curMonth = cur.substring(5,7);
        UiObject2 currentDate = waitForObj(By.res("com.stv.calendar:id/home_day"));
        check("don't show current year and month", currentDate != null);
        curDate = currentDate.getText();
        calendarDate[0] = Integer.parseInt(curYear);
        calendarDate[1] = Integer.parseInt(curMonth);
        calendarDate[2] = Integer.parseInt(curDate);
        addStep("日历显示" + calendarDate[0] + "年"+ calendarDate[1]+"月" + calendarDate[2]+"日");
       }

    public boolean checkCurrentDate(int sysDate[],int calendarDate[]){

         boolean isCur = false;
         GetSysCurrentDataTime(sysDate);
         GetCalendarCurrentDate(calendarDate);
         if((sysDate[0] ==calendarDate[0]) && (sysDate[1] ==calendarDate[1]) && (sysDate[2] ==calendarDate[2]) )
             isCur = true;
         else
             isCur = false;
         return isCur;
     }
}
