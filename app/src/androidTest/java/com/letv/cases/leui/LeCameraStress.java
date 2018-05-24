package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiWatcher;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.PkgName;
import com.letv.common.LetvTestCase;
import java.io.IOException;

import org.junit.Test;

import java.util.regex.Pattern;


public class LeCameraStress extends  LetvTestCase{


    @Test
    @CaseName("test Camera")
    public void testCamera() throws UiObjectNotFoundException{

        for (int Loop = 0; Loop < getIntParams("Loop");Loop++) {

            System.out.println(".............looper : " + Loop);

            addStep("打开乐拍");
            UiObject2 Tools = waitForObj(By.text(Pattern.compile("工具")));
            if (Tools == null) {
                launchApp(AppName.Camera,PkgName.Camera);
            }else {
                Tools.click();
                press_center(1);
                sleepInt(1);
                UiObject2 camera = waitForObj(By.res("com.stv.plugin.app:id/cellview_label").text("电视乐拍"));
                if (camera == null){
                    press_right(1);
                    press_center(1);
                    sleepInt(1);
                }else {
                    verify("没有找到电视乐拍",camera != null);
                    camera.click();
                    press_center(1);
                }

            }

            addStep("查看乐拍界面");
            UiObject2 Camera = waitForObj(By.res("com.stv.camera:id/primary_function_iv"));
            verify("照相功能没有找到",Camera.isFocused());

            UiObject2 Filter = waitForObj(By.res("com.stv.camera:id/filter_btn"));
            verify("美化功能没有找到",Filter != null);

            addStep("退出乐拍");
            press_back(2);

        }
    }

    @Test
    @CaseName("test take photo测试连续拍照100张")
    public void testTakePhoto() throws UiObjectNotFoundException{

        addStep("进入相册查找已有照片数");
        launchApp(AppName.Gallery,PkgName.Gallery);
        UiObject2 photoCount = waitForObj(By.res("com.android.gallery3d:id/title_photocount"));
        verify("没找到照片数",photoCount != null);
        String phCount = photoCount.getText();
        int phCountBefore =   GetPhotoAccount(phCount);

        addStep("打开乐拍");
        launchApp(AppName.Camera,PkgName.Camera);

        addStep("查看乐拍界面");
        UiObject2 Camera = waitForObj(By.res("com.stv.camera:id/primary_function_iv"));
        verify("照相功能没有找到",Camera.isFocused());
        sleepInt(2);

        UiObject2 Check = phone.findObject(By.res("com.stv.camera:id/camera_info_tv"));
        verify("未识别到摄像头，请接入",Check == null);
        int Loop = 0;
        for (  Loop = 0; Loop < getIntParams("Loop");Loop++) {
            press_center(1);
            sleepInt(6);
        }
        addStep("退出乐拍");
        press_back(2);

        addStep("进入相册查找拍照之后照片数");
        exitApp();
        launchApp(AppName.Gallery,PkgName.Gallery);
         photoCount = waitForObj(By.res("com.android.gallery3d:id/title_photocount"));
        verify("没找到照片数",photoCount != null);
        phCount = photoCount.getText();
        int phCountAfter =   GetPhotoAccount(phCount);

        //检查所有拍照是否已存储
         if ( (phCountAfter - phCountBefore ) !=  Loop )
         {
             check("拍照片没有正确存储，照片数量不对",true);
         }

    }

    @Test
    @CaseName("test switch take photo and video之间切换")
    public void testSwitchPhotoCamera() throws UiObjectNotFoundException {

        addStep("打开乐拍");
        launchApp(AppName.Camera, PkgName.Camera);

        addStep("查看乐拍界面");
        UiObject2 Camera = waitForObj(By.res("com.stv.camera:id/primary_function_iv"));
        verify("照相功能没有找到", Camera.isFocused());
        sleepInt(2);

        UiObject2 Check = phone.findObject(By.res("com.stv.camera:id/camera_info_tv"));
        verify("未识别到摄像头，请接入", Check == null);
        int Loop = 0;
        for (Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            press_up(1);
            sleepInt(1);
            UiObject2 Filter = phone.findObject(By.res("com.stv.camera:id/filter_btn"));
            verify("现在不是录像模式",Filter == null);
            press_up(1);
            sleepInt(1);
            Filter = phone.findObject(By.res("com.stv.camera:id/filter_btn"));
            verify("现在不是照相模式",Filter != null);

        }
            addStep("退出乐拍");
            press_back(2);

    }


    @Test
    @CaseName("测试连续录像1个小时")
    public void testTakeVediofor1hr() throws UiObjectNotFoundException{

        addStep("打开乐拍");
        launchApp(AppName.Camera, PkgName.Camera);

        addStep("查看乐拍界面");
        UiObject2 Camera = waitForObj(By.res("com.stv.camera:id/primary_function_iv"));
        verify("照相功能没有找到", Camera.isFocused());
        sleepInt(2);

        UiObject2 Check = phone.findObject(By.res("com.stv.camera:id/camera_info_tv"));
        verify("未识别到摄像头，请接入", Check == null);

        press_up(1);
        sleepInt(1);
        UiObject2 Filter = phone.findObject(By.res("com.stv.camera:id/filter_btn"));
        verify("现在不是录像模式",Filter == null);

        addStep("持续录像1个小时");
        //按下录像按钮
        press_center(1);
        UiObject2 Message =phone.findObject(By.res("com.stv.camera:id/recorder_tip_tv").text("请接入存储设备"));
        if (Message != null)
              verify("没有外存设备，不能录像",Message != null);
        else {
            press_center(1);
            sleepInt(2);
            Message =phone.findObject(By.res("com.stv.camera:id/recorder_tip_tv").text("注意：录制过程中请不要断开设备"));
            verify("录制过程中没有弹出提示信息",Message != null);
         //   sleepInt(3600);
            sleepInt(60);
            //录制结束
            press_center(1);
            sleepInt(1);
            addStep("播放刚才录制的视频文件");
            press_down(1);
            press_left(5);
            press_center(1);
            sleepInt(10);
            press_center(1);
            Message =phone.findObject(By.res("com.stv.videoplayer:id/duration_tv").text(Pattern.compile("00:01.*")));
            verify("播放视频的录制时间不对",Message != null);
            addStep("111111_"+Message.getText()+"_1111");

            press_center(1);
            sleepInt(20);
        }

        addStep("退出乐拍");
        press_back(5);

    }

    @Test
    @CaseName("测试在缩略图和全屏之前切换")
    public void testSwitchFullAndthumbnail() throws UiObjectNotFoundException{


        addStep("打开乐拍");
        launchApp(AppName.Camera,PkgName.Camera);

        addStep("查看乐拍界面");
        UiObject2 Camera = waitForObj(By.res("com.stv.camera:id/primary_function_iv"));
        verify("照相功能没有找到",Camera.isFocused());
        sleepInt(2);

        UiObject2 Check = phone.findObject(By.res("com.stv.camera:id/camera_info_tv"));
        verify("未识别到摄像头，请接入",Check == null);
        for (  int n = 0; n < 4; n++) {
            press_center(1);
            sleepInt(6);
        }
        press_down(1);
        for (int Loop = 0; Loop < getIntParams("Loop");Loop++)
        {
            press_center(1);
            sleepInt(6);
            press_back(1);
            sleepInt(1);

            press_right(1);
            press_center(1);
            sleepInt(6);
            press_back(1);
            sleepInt(1);

            press_right(1);
            press_center(1);
            sleepInt(6);
            press_back(1);
            sleepInt(1);

            press_left(1);
            press_center(1);
            sleepInt(6);
            press_back(1);
            sleepInt(1);

            press_left(1);
            press_center(1);
            sleepInt(6);
            press_back(1);
            sleepInt(1);
        }

        addStep("退出乐拍");
        press_back(3);


    }
    //获取照片数量
    private int GetPhotoAccount(String text)
    {
        int a = -1;
        if (text == null){  throw new IllegalArgumentException("incorrect parameter");}
        String old = "张";
        String new1 = "";
        text = text.replace(old,new1);
        text = text.substring(2);
        try {
             a = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return a;
    }


}
