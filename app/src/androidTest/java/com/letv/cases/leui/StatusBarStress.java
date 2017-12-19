package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;

import junit.framework.Assert;

import org.junit.Test;

import java.util.regex.Pattern;

public class StatusBarStress extends LetvTestCase {

    int count =0;
    @Test
    @CaseName("状态栏里各项操作")
    public void testStatusBar() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("进入状态栏");
        press_up(4);
        sleepInt(2);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper : "+(Loop+1));
            try {
                StatusBar();
            }catch (Exception e){
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("应用");
                    StatusBar();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
        addStep("退出状态栏");
        press_back(1);
    }

    public void StatusBar() throws UiObjectNotFoundException, RemoteException {

        UiObject2 weather = phone.findObject(By.res("com.stv.launcher:id/nweather_layout")).getParent().findObject(By.text(Pattern.compile(".*℃")));
        UiObject2 connected = phone.findObject(By.res("com.stv.launcher:id/new_notification_network_layout")).getParent().findObject(By.text("已连接"));
        check("没进入状态栏",weather!=null || connected !=null);

        UiObject2 magazine_icon = phone.findObject(By.res("com.stv.launcher:id/magazine_icon"));
        if(magazine_icon!=null){
            addStep("客厅画面");
            magazine_icon.click();
            magazine_icon.click();
            sleepInt(2);
            press_back(1);
            sleepInt(1);
        }

        addStep("进入日历");
        UiObject2 new_week = waitForObj(By.res("com.stv.launcher:id/new_week_day").text(Pattern.compile(".*星期.*")));
        check("failed to fond the clock",new_week!=null);
        new_week.click();
        new_week.click();
        sleepInt(5);
        UiObject2 clock1 = phone.findObject(By.res("com.stv.launcher:id/new_week_day").text(Pattern.compile(".*星期.*")));
        if(clock1!=null){
            press_center(1);
        }
        UiObject2 check1 = phone.findObject(By.pkg("com.stv.calendar"));
        check("未进入日历", check1 != null);
        press_back(1);
        sleepInt(2);


        addStep("进入天气");
        UiObject2 nweather = phone.findObject(By.res("com.stv.launcher:id/nweather_layout")).getParent().findObject(By.text(Pattern.compile(".*℃")));
        nweather.click();
        nweather.click();
        sleepInt(5);
        UiObject2 check4 = phone.findObject(By.pkg(Pattern.compile("com.stv.weather|sina.mobile.tianqitongstv")));
        check("未进入天气", check4 != null);
        press_back(1);
        sleepInt(2);

        addStep("进入系统更新");
        UiObject2 system_update = phone.findObject(By.res("com.stv.launcher:id/update_text").text("系统更新"));
        system_update.click();
        system_update.click();
        sleepInt(2);
        UiObject2 systemupgrade = phone.findObject(By.res("com.stv.systemupgrade:id/tv_title").text(Pattern.compile("系统更新")));
        check("未进入系统更新",systemupgrade!=null);
        press_back(1);
        sleepInt(2);

        addStep("进入文件管理");
        UiObject2 usb = phone.findObject(By.text(Pattern.compile("文件管理|.*移动设备|.*外接设备")));
        usb.click();
        usb.click();
        sleepInt(3);
        UiObject2 fileManager = phone.findObject(By.clazz("android.widget.TextView").text("文件管理"));
        check("未进入文件管理", fileManager != null);
        press_back(1);
        sleepInt(2);

        addStep("进入网络连接");
        UiObject2 connected1 = phone.findObject(By.res("com.stv.launcher:id/new_notification_network_layout")).getParent().findObject(By.text("已连接"));
        connected1.click();
        connected1.click();
        sleepInt(2);
        UiObject2 connectNet = phone.findObject(By.res("eui.tv:id/title_txt").text(Pattern.compile("网络")));
        check("未进入网络",connectNet!=null);
        press_back(1);
        sleepInt(2);

        addStep("进入消息");
        UiObject2 message = phone.findObject(By.res("com.stv.launcher:id/message_center_text").text("消息"));
        message.click();
        message.click();
        sleepInt(2);
        UiObject2 message_title = phone.findObject(By.res("eui.tv:id/title_txt").text(Pattern.compile("消息")));
        check("未进入网络",message_title!=null);
        press_back(1);
        sleepInt(2);
        press_up(2);
    }
}