package com.letv.cases.leui;

import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObject2Condition;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;

import org.junit.After;
import org.junit.Test;
import org.junit.Assert;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;


public class BTStress extends LetvTestCase{

    int count =0;

    @Test
    @CaseName("在任意桌面按设置进入蓝牙")
    public void testBTSetting() throws UiObjectNotFoundException, RemoteException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper : " + Loop);
            try {
                blueToothSetting();
                }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    blueToothSetting();
                }catch (RuntimeException re){
                   screenShot();
                   Assert.fail(re.getMessage());
               }
            }
        }
    }
    public void blueToothSetting() throws UiObjectNotFoundException, RemoteException {
        String[] desktops={"应用","首页","乐见","信号源"};
        press_left(4);
        for(int j=0;j<desktops.length;j++)
        {
            addStep("切换到" + desktops[j] + "桌面");
            gotoHomeScreen(desktops[j]);
            addStep("切换到应用桌面通过按键打开设置");
            phone.pressKeyCode(KEY_SETTING);
            sleepInt(1);
            press_down(2);
            UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_advance"));
            verify("没有找到系统设置", sytemSetting != null);
            sytemSetting.click();
            press_center(1);
//            UiObject2 accessory = waitForObj(By.res("com.stv.globalsetting:id/advance_accessory"));
//            check("没有找到配件",accessory != null);
//            accessory.click();
            press_center(1);
            UiObject2 bt = waitForObj(By.res("eui.tv:id/textView").text("蓝牙"));
            check("未找到蓝牙",bt !=null);
            press_center(1);
            press_right(2);
            sleepInt(12);
            press_down(2);
            press_back(3);
        }
    }

//    这条case现在不用调试
    @Test
    @CaseName("多次关闭开启蓝牙")
    public void testSwitchBlueTooth() throws UiObjectNotFoundException, RemoteException {

        gotoHomeScreen("应用");

        String[] desktops={"信号源","搜索","应用","LIVE|Live|首页","乐见","游戏","体育","购物"};
        press_left(7);
        for(int j=0;j<desktops.length;j++) {

            // addStep("切换到" + desktops[j] + "桌面");
            //gotoHomeScreen(desktops[j]);
            press_right(2);

            int m = GenerateRandom(3);
            int n = GenerateRandom(3);
            press_down(m +1);
            //  press_center(1);
            press_right(n +1);

          //  press_center(1);
            sleepInt(1);
            addStep("遥控设置键->设置->配件->蓝牙");
            phone.pressKeyCode(KEY_SETTING);
            sleepInt(1);
            press_up(6);
            sleepInt(1);
            press_down(6);
            sleepInt(1);
            press_center(1);
            /*UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_advance"));
            verify("没有找到系统设置", sytemSetting != null);
            sytemSetting.click();
            press_center(1);*/
            sleepInt(1);
            press_down(1);
            press_center(1);
            /*UiObject2 accessory = waitForObj(By.res("com.stv.globalsetting:id/advance_accessory"));
            verify("没有找到配件", accessory != null);
            accessory.click();
            accessory.click();*/
            sleepInt(1);
//            UiObject2 blueTooth = waitForObj(By.res("eui.tv:id/textView").text("蓝牙"));
//            check("没有找到蓝牙", blueTooth != null);


            /*开启关闭蓝牙Loop次*/
            int Loop = getIntParams("Loop");
            //
            //
            //
            press_back(6);
          }

    }

}



