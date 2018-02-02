package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;


public class VideoPlayStress extends LetvTestCase {


    BySelector storageDeviceS = By.clazz("android.widget.TextView").text("存储设备");
    BySelector intStorageDeviceS = By.clazz("android.widget.TextView").text("本机存储");
    BySelector menuBartext = By.clazz("android.widget.TextView").text("按菜单键更多操作");
    BySelector extStorageDeviceS = By.text("USB");

    BySelector bigToSmallS =By.text(Pattern.compile(".*Z到A"));
    BySelector contentS =By.res("android:id/content");

    int count =0;

    @Test
    @CaseName("视频播放")
    public void testVideoPlay() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        press_down(1);
        press_back(3);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper : " + Loop);
            try {
                fileManageFirst();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("应用");
                    press_down(1);
                    press_back(3);
                    fileManageFirst();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
        press_back(3);
    }
    public void fileManageFirst() throws UiObjectNotFoundException, RemoteException {
        launchApp(AppName.Filemanager,IntentConstants.Filemanager);
        addStep("进入文件管理");
        press_right(3);
        UiObject2 storageDevice = waitForObj(storageDeviceS);
        clickAndWaitForNewWindow(storageDevice);
        sleepInt(1);
        addStep("copy 外接USB存储中文件到本地文件夹");
        UiObject2 extStorageDevice1 = waitForObj(extStorageDeviceS);
        verify("没有找本机外部存储设备，请检查", extStorageDevice1 != null);
        extStorageDevice1.click();
        check("未进入外接存储文件", menuBartext != null);
        sleepInt(1);
        sleepInt(1);
        press_right(2);
        UiObject2 copeAAAfile = phone.findObject(By.text("AAA"));
        check("未进入AAA文件夹", copeAAAfile != null);
        copeAAAfile.click();
        copeAAAfile.click();
        sleepInt(2);
        check("未进入外接存储文件", menuBartext != null);
        UiObject2 video= waitForObj(By.text("video"));
        video.click();
        video.click();
        sleepInt(2);
        press_menu(1);
        press_down(2);
        UiObject2 screen= waitForObj(By.text("筛选"));
        check("未进入筛选栏",screen!=null);
        screen.click();
        screen.click();
        UiObject2 all= waitForObj(By.text("全部"));
        check("未进入全部栏",screen!=null);
        all.click();
        all.click();
        sleepInt(5);
        press_left(2);
        sleepInt(5);
        addStep("进入视频播放中");
        for(int i=1;i<=10;i++){
            UiObject2 videoplay=waitForObj(By.res("com.stv.filemanager:id/text_name").text("video"+i));
            addStep("进入视频播放video"+i+"播放");
            check("未进入视频播放",videoplay!=null);
            videoplay.click();
            videoplay.click();
            sleepInt(5);
            UiObject2 start=waitForObj(By.res("com.stv.videoplayer:id/tip_tv").text("按确定键从头开始播放"));
            if (start!=null){
                press_center(1);
            }
            sleepInt(5);
            press_right(3);
            sleepInt(10);
            press_left(3);
            sleepInt(10);
            press_center(1);
            sleepInt(10);
            press_center(1);
            sleepInt(10);
            sleepInt(40);
            press_back(1);
            sleepInt(2);
            press_right(1);
            sleepInt(2);
        }
    }

}
