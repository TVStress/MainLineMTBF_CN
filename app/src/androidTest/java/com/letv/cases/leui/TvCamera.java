package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

@RunWith(AndroidJUnit4.class)
public class TvCamera extends LetvTestCase {
    int count =0;

    private void launchCamera(String appName){
        gotoHomeScreen("应用");
        UiObject2 o = phone.findObject(By.depth(12).clazz("android.widget.RelativeLayout"));
        verify("没有找到工具",o!=null);
        if (o != null){
            o.click();
            o = waitForObj(By.depth(12).clazz("android.widget.RelativeLayout"),2000);
            if (o != null){
                clickAndWaitForNewWindow(o);
            }
        }
        UiObject2 title = waitForObj(By.res("com.stv.launcher:id/app_folder_workspace_title"));
        verify("没有进入目录",title!=null);
        UiObject2 camera = phone.findObject(By.text(Pattern.compile(appName)));
        verify("没有找到相机",camera!=null);
        camera.click();
        sleepInt(2);
        camera = phone.findObject(By.text(Pattern.compile(appName)));
        if (camera != null){
            clickAndWaitForNewWindow(camera);
        }
    }

    @Test
    @CaseName("进入乐拍，拍摄照片")
    public void testTvCamera() throws UiObjectNotFoundException, RemoteException {
        addStep("打开电视乐拍");
        launchCamera(AppName.Camera);
        sleepInt(5);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                TvCamera();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("打开电视乐拍");
                    launchCamera(AppName.Camera);
                    sleepInt(5);
                    TvCamera();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }

        addStep("退出电视乐拍");
        press_back(2);

    }

    public void TvCamera() throws UiObjectNotFoundException, RemoteException {
        UiObject2 cameraHWCheck = phone.findObject(By.clazz("android.widget.TextView").textContains("未识别到摄像头，请接入"));
        if (cameraHWCheck != null) {
            screenShot();
            Assert.fail("未识别到摄像头");
        }
        UiObject2 access = phone.findObject(By.clazz("android.widget.TextView").textContains("允许应用访问摄像头吗"));
        if (access != null) {
            UiObject2 permit = phone.findObject(By.text("允许"));
            permit.click();
            sleepInt(5);
        }
        addStep("拍摄10张照片");
        UiObject2 camera = phone.findObject(By.res("com.stv.camera:id/primary_function_iv"));
        UiObject2 filter = phone.findObject(By.res("com.stv.camera:id/filter_btn"));
        UiObject2 photoframe = phone.findObject(By.res("com.stv.camera:id/photo_frame_btn"));
        check("camera isnot open",camera!=null&&filter != null&&photoframe != null);
        for (int i = 1; i <= 10; i++) {
            //camera.click();
            press_center(1);
            sleepInt(5);
            System.out.println("take picture times is :      " + i);
        }

        addStep("预览缩略图");
        press_down(2);

        UiObject2 thumbnail = phone.findObject(By.res(
                "com.stv.camera:id/thumbnail_content"));
        check("take pic failed",thumbnail != null);

        press_right(9);

        addStep("幻灯片浏览");
        press_center(1);
        press_left(9);
        press_back(1);

        addStep("使用滤镜拍摄");
        press_up(1);
        sleepInt(1);
        press_right(1);
        sleepInt(1);
        filter.click();
        sleepInt(2);

        UiObject2 yuanhua = phone.findObject(By.res(
                "com.stv.camera:id/text_filter").text("原画"));
        UiObject2 lomo = phone.findObject(By.res(
                "com.stv.camera:id/text_filter").text("LOMO"));
        UiObject2 yuhua = phone.findObject(By.res(
                "com.stv.camera:id/text_filter").text("羽化"));
        UiObject2 huaijiu = phone.findObject(By.res(
                "com.stv.camera:id/text_filter").text("怀旧"));
        UiObject2 banhua = phone.findObject(By.res(
                "com.stv.camera:id/text_filter").text("版画"));
        UiObject2 rouhua = phone.findObject(By.res(
                "com.stv.camera:id/text_filter").text("柔化"));
        UiObject2 lianhuanhua = phone.findObject(By.res(
                "com.stv.camera:id/text_filter").text("连环画"));
        UiObject2 dipian = phone.findObject(By.res(
                "com.stv.camera:id/text_filter").text("底片"));
        check("filter display failed",lianhuanhua != null);
        sleepInt(2);

        UiObject2 camera1 = phone.findObject(By.res(
                "com.stv.camera:id/primary_function_iv"));
        for (int i = 1; i <= 7; i++) {
            press_right(i);
            System.out.println("........................................." + i);
            sleepInt(5);
            press_center(1);
            sleepInt(5);
            for (int j = 1; j <= 5; j++) {
                //camera1.click();
                press_center(1);
                sleepInt(8);
                System.out.println("take picture times is :      " + j);
            }
            sleepInt(5);
            press_right(1);
            sleepInt(5);
            press_center(1);
            sleepInt(5);
        }

        addStep("使用各个相框拍摄");
        press_up(1);
        press_right(2);
        press_center(1);

        for (int i = 1; i <= 6; i++) {
            press_right(i);
            System.out.println("........................................." + i);
            sleepInt(5);
            press_center(1);
            sleepInt(5);
            for (int j = 1; j <= 5; j++) {
                //camera1.click();
                press_center(1);
                sleepInt(8);
                System.out.println("take picture times is :      " + j);
            }
            sleepInt(5);
            press_right(1);
            sleepInt(5);
            press_center(1);
            sleepInt(5);
        }
    }

    @Before
    public void initDl() throws UiObjectNotFoundException {
        callShell("am start -S com.android.browser/.download.DownloadActivity");
        sleep(2000);
        UiObject2 clearAll = phone.findObject(By.res("com.android.browser:id/clear_txt"));
        verify("未打开浏览器下载页面",clearAll != null);
        if(clearAll.isEnabled()){
            clearAll.click();
            sleep(2000);
            UiObject2 confirm = phone.findObject(By.res("com.android.browser:id/positive"));
            confirm.click();
            sleep(5000);
            phone.pressBack();
            sleep(2000);
            verify("未返回到launch桌面",!phone.getCurrentPackageName().equals("com.stv.launcher"));
        } else {
            press_back(1);
            sleep(2000);
            verify("未返回到launch桌面",phone.getCurrentPackageName() != "com.stv.launcher");
        }



    }
}
