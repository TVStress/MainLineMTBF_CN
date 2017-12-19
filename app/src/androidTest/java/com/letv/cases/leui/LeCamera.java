package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LeCamera extends LetvTestCase {
    int count=0;

    @Test
    @CaseName("进入乐拍，拍摄照片")
    public void testTakePicture() throws UiObjectNotFoundException, RemoteException {
        addStep("打开电视乐拍");
        launchApp(AppName.Camera,IntentConstants.Camera);
        sleepInt(5);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                TakePicture();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("打开电视乐拍");
                    launchApp(AppName.Camera,IntentConstants.Camera);
                    sleepInt(5);
                    TakePicture();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
/*        addStep("删除所有照片");
        //目前没有删除功能，以cmd来暂时实现
        if(Build.VERSION.SDK_INT>20){
            callShell("rm -rf /sdcard/LepiPhoto*//*");
        }*/
        addStep("退出电视乐拍");
        press_back(2);
    }

    public void TakePicture() throws UiObjectNotFoundException, RemoteException {
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
        check("camera isnot open", camera != null && filter != null && photoframe != null);
        for (int i = 1; i <= 2; i++) {
            //camera.click();
            press_center(1);
            sleepInt(5);
            System.out.println("take picture times is :      " + i);
        }

        addStep("预览缩略图");
        press_down(2);

        UiObject2 thumbnail = phone.findObject(By.res(
                "com.stv.camera:id/thumbnail_content"));
        check("take pic failed", thumbnail != null);

        press_right(9);

        addStep("幻灯片浏览");
        press_center(1);
        press_left(9);
        press_back(1);

        press_up(1);
        sleepInt(1);
        press_right(1);
        sleepInt(1);
        addStep("使用各个滤镜拍摄照片");
        filter.click();
        sleepInt(2);
        press_left(8);

        UiObject2 huaijiu = phone.findObject(By.res("com.stv.camera:id/text_filter").text("原画"));
        check("filter display failed", huaijiu != null);
        for (int i = 1; i <= 7; i++) {
            UiObject2 gallery=waitForObj(By.res("com.stv.camera:id/thumbnail_gallery"));
            check("没有找到相框", gallery != null);
            press_right(1);
            press_center(1);
            sleepInt(3);
            press_center(1);
            sleepInt(8);
            press_right(1);
            press_center(1);
            sleepInt(3);
        }

        addStep("使用各个相框拍摄");
        press_up(1);
        press_right(2);
        press_center(1);

        for (int i = 1; i <= 6; i++) {
            UiObject2 gallery = waitForObj(By.res("com.stv.camera:id/thumbnail_gallery"));
            check("没有找到相框", gallery != null);
            press_right(1);
            press_center(1);
            sleepInt(3);
            for (int j = 1; j <= 5; j++) {
                press_center(1);
                sleepInt(8);
            }
            press_right(2);
            press_center(1);
            sleepInt(3);
        }
    }

    @Test
    @CaseName("乐拍录制视频")
    public void testVideoRecorder() throws UiObjectNotFoundException, RemoteException {
        addStep("打开乐拍");
        launchApp(AppName.Camera,IntentConstants.Camera);
        UiObject2 camera = phone.findObject(By.res("com.stv.camera:id/primary_function_iv"));
        UiObject2 filter = phone.findObject(By.res("com.stv.camera:id/filter_btn"));
        UiObject2 photoframe = phone.findObject(By.res("com.stv.camera:id/photo_frame_btn"));
        verify("camera isnot open", camera != null && filter != null && photoframe != null);
        press_up(1);
        UiObject2 filter1 = phone.findObject(By.res("com.stv.camera:id/filter_btn"));
        verify("video recorder mode not on", filter1 == null);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
           // TakeVideo();
            try {
                TakeVideo();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("Open Lecamera");
                    launchApp(AppName.Camera,IntentConstants.Camera);
                    UiObject2 camera1 = phone.findObject(By.res("com.stv.camera:id/primary_function_iv"));
                    UiObject2 filter2 = phone.findObject(By.res("com.stv.camera:id/filter_btn"));
                    UiObject2 photoframe1 = phone.findObject(By.res("com.stv.camera:id/photo_frame_btn"));
                    verify("camera isnot open", camera1 != null && filter2 != null && photoframe1 != null);
                    addStep("Play video recorded");
                    press_up(1);
                    UiObject2 filter3 = phone.findObject(By.res("com.stv.camera:id/filter_btn"));
                    verify("video recorder mode not on", filter3 == null);
                    TakeVideo();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
        addStep("退出乐拍");
        press_back(2);
    }

    public void TakeVideo() throws UiObjectNotFoundException, RemoteException {
        addStep("录制视频15s");
        press_center(1);
        sleepInt(15);
        press_center(1);
        press_down(1);
        addStep("播放刚录制的视频");
        UiObject2 thumbnail =waitForObj(By.res("com.stv.camera:id/thumbnail_content"));
        check("take video failed", thumbnail != null);
        press_center(1);
        sleepInt(5);
        UiObject2 thumbnail1 =phone.findObject(By.res("com.stv.camera:id/thumbnail_content"));
        check("video is not playing", thumbnail1 == null);
        press_back(2);
        UiObject2 camera1 = phone.findObject(By.res("com.stv.camera:id/primary_function_iv"));
        check("Not in recorder mode",camera1!=null);
    }
}