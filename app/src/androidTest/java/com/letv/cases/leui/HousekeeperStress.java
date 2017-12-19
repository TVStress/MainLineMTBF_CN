package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;

import junit.framework.Assert;

import org.junit.Test;

public class HousekeeperStress extends LetvTestCase{
    int count=0;

    @Test
    @CaseName("电视管家进行多次一键体检和清理内存以及清除数据")
    public void testHousekeeper() throws UiObjectNotFoundException,RemoteException{
        addStep("进入电视管家应用");
        launchApp(AppName.Housekeeper, IntentConstants.Housekeeper);
        for (int Loop = 0; Loop < getIntParams("Loop");Loop++) {
            addStep(".............looper : " + Loop);
            try{
                Housekeeper();
            }
            catch (Exception e){
                try{
                    count++;
                    failCount(count,getIntParams("Loop"),e.getMessage());
                    addStep("进入电视管家应用");
                    launchApp(AppName.Housekeeper, IntentConstants.Housekeeper);
                    Housekeeper();
                }
                catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());

                }
            }
        }
    }

    public void Housekeeper()throws UiObjectNotFoundException,RemoteException{
        UiObject2 oneKey = waitForObj(By.text("一键体检"));
        if (oneKey.isFocused()){
            press_up(1);
        }
        UiObject2 applicationManager = waitForObj(By.text("垃圾清理"));
        verify("未进入到电视管家界面", applicationManager != null);
        applicationManager.click();
        applicationManager.click();
        sleepInt(5);
        press_back(1);
        UiObject2 systemacceleration = waitForObj(By.text("系统加速"));
        verify("未进入到电视管家界面", systemacceleration != null);
        systemacceleration.click();
        systemacceleration.click();
        sleepInt(5);
        press_back(1);
        addStep("选择一键清理按钮");
        UiObject2 oneKey1 = waitForObj(By.text("一键体检"));
        verify("未返回到电视管家界面", oneKey1 != null);
        press_down(2);
        clickAndWaitForNewWindow(oneKey1);
        for (int i = 0; i < 20; i++) {
            UiObject2 compelete = phone.findObject(By.text("扫描完成"));
            if (compelete == null) {
                sleepInt(3);
            } else break;
        }
        press_back(1);
//            verify("未能退出到桌面", phone.getCurrentPackageName().equals(PACKAGE_HOME));
    }
}
