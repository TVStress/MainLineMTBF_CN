package com.letv.cases.leui;


import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.PkgName;
import com.letv.common.LetvTestCase;

import org.junit.Assert;
import org.junit.Test;
public class Calendar extends LetvTestCase{
    int count =0;

    @Test
    @CaseName("进入日历切换日期")
    public void testCalendar() throws UiObjectNotFoundException, RemoteException {
        addStep("打开日历App");
        launchApp(AppName.Calendar, PkgName.Calendar);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            //Calendar();
            try {
                Calendar();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("打开日历App");
                    launchApp(AppName.Calendar, PkgName.Calendar);
                    Calendar();
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
        addStep("4.退出日历");
        press_back(2);
    }

    public void Calendar() throws UiObjectNotFoundException, RemoteException {
        press_menu(1);
        sleepInt(1);
        UiObject2 returnToday = waitForObj(By.clazz("android.widget.TextView").text("回到今天"));
        check("not open menu of calendar", returnToday!=null);
        press_up(2);
        press_down(1);
        UiObject2 selectDate1 = waitForObj(By.clazz("android.widget.TextView").text("选择日期"));
        check("select date not focused", selectDate1.isSelected());
        press_center(1);
        sleepInt(2);

        addStep("2.更改日期");
        sleepInt(2);
        press_down(2);
        addStep("已按向下键");
        sleepInt(2);
        UiObject2 check = waitForObj(By.clazz("android.widget.Button").text("农历"));
        check("select date not focused", check!=null);
        press_right(1);
        press_down(2);
        press_center(1);
        sleepInt(2);

        addStep("3.回到今天");
        press_menu(1);
        sleepInt(2);
        press_up(1);
        UiObject2 returntoday1 = waitForObj(By.clazz("android.widget.TextView").text("回到今天"));
        check("return today not focused", returntoday1.isSelected());
        press_center(1);
        sleepInt(2);
    }
}