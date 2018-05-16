package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiWatcher;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;

import junit.framework.Assert;

import java.io.IOException;

import org.junit.Test;

import java.util.regex.Pattern;

public class HomeTimeStress extends LetvTestCase {
int count=0;

    @Test
    @CaseName("test HomeTime")
    public void testHomeTime() throws UiObjectNotFoundException,RemoteException {
        addStep("打开Hometime");
//      launchApp(AppName.HomeTime,IntentConstants.HomeTime);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            LaunchHomeTime();
            try {
                HomeTime();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("打开Hometime");
                    LaunchHomeTime();
                    HomeTime();
                } catch (RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
            press_back(1);
            sleepInt(2);
            UiObject2 exitbotton = waitForObj(By.text("退出"));
            exitbotton.click();
            press_back(3);
        }
    }
    public void HomeTime() throws UiObjectNotFoundException,RemoteException{
        UiObject2 hometime = phone.findObject(By.res("com.stv.plugin.app:id/cellview_label").text(Pattern.compile("HomeTime.*")));
        check("未进入HomeTime",hometime!= null);
        hometime.click();
//        hometime.click();
        loginAccount();
        sleepInt(2);
        press_right(6);
        press_left(5);
        sleepInt(2);
        for(int j = 0;j< 2;j++ ) {
            addStep("查看hometime界面");
            String feedback[] = {"联系人.*", "通话记录.*", "拨号.*", "我的.*"};
            for (int i = 0; i < feedback.length; i++) {
                press_center(1);
                press_right(2);
                press_left(2);
                press_down(1);
                sleepInt(1);
            }
            press_up(4);
            sleepInt(2);
        }
    }

    public void loginAccount(){
        UiObject2 loginNow = phone.findObject(By.text("立即登录"));
        if(loginNow!=null) {
            addStep("登录会员");
            loginNow.click();
            sleepInt(2);
            UiObject2 login = phone.findObject(By.text(Pattern.compile(".*添加帐号.*|.*帐号密码登录.*|.*其他帐号登录.*")));
            if (login!=null) {
                login.click();
                sleepInt(2);
            }
            sleepInt(2);
            UiObject2 userName = phone.findObject(By.text(Pattern.compile("超级电视帐号|乐视帐号|会员帐号"))).getParent().findObject(By.clazz("android.widget.EditText"));
            verify("can't find userName.", userName != null);
            userName.setText(getStrParams("USERNAME"));
            sleepInt(2);
            press_down(1);
            UiObject2 passwd = phone.findObject(By.text(Pattern.compile("密码"))).getParent().findObject(By.clazz("android.widget.EditText"));
            verify("can't find passwd.", passwd != null);
            passwd.setText(getStrParams("PASSWORD"));
            sleepInt(1);
            UiObject2 loginNow1 = phone.findObject(By.clazz("android.widget.Button").text("立即登录"));
            verify("can't find loginNow.", loginNow1!=null);
            loginNow1.click();
            loginNow1.click();
            sleepInt(3);
        }
    }
    public void LaunchHomeTime() throws UiObjectNotFoundException,RemoteException{
        addStep("进入应用桌面");
        gotoHomeScreen("应用");
        press_down(1);
        press_back(2);
        press_down(6);
        sleepInt(1);


    }

}
