package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.util.Log;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;

import junit.framework.Assert;

import org.junit.Test;

import java.util.regex.Pattern;


public class Gallery extends LetvTestCase{
    int count =0;
    BySelector bigToSmallS =By.text(Pattern.compile(".*Z到A"));
    BySelector contentS =By.res("android:id/content");

    @Test
    @CaseName("相册排序切换")
    public void testgalleryRank()throws UiObjectNotFoundException, RemoteException{
        addStep("进入相册");
        launchApp(AppName.Gallery,IntentConstants.Gallery);
        try{
            galleryRank();
        }
        catch (Exception e){
            try {
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入相册");
                launchApp(AppName.Gallery,IntentConstants.Gallery);
                galleryRank();
            }
            catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());
            }

        }
        press_back(3);
    }
    public void galleryRank()throws UiObjectNotFoundException,RemoteException{
        for(int Loop = 0; Loop < getIntParams("Loop"); Loop++){
            UiObject2 album = waitForObj(By.text("按菜单键有更多功能"));
            verify("未进入相册", album != null);
            addStep("相册按大到小、时间近远、名称字母排序");
            sleepInt(1);
            press_menu(1);
            UiObject2 content = waitForObj(contentS);
            verify("menu菜单没有弹出", content != null);
            for (int a = 0; a < 5; a++) {
                UiObject2 ran1 = phone.findObject(By.text("排序"));
                if (ran1.isSelected()) {
                    break;
                } else {
                    press_down(1);
                }
            }
            press_center(1);
            sleepInt(1);
            String[] galleryt = {"时间近到远", "时间远到近", "小到大", "大到小", "名称A到Z", "名称Z到A"};
            for (int i = 0; i < galleryt.length; i++) {
                press_down(i);
                UiObject2 table = waitForObj(By.text(Pattern.compile(galleryt[i])));
                check("未进入相册排序循环", table != null);
                table.click();
                table.click();
                sleepInt(1);
                for (int a = 0; a < 5; a++) {
                    UiObject2 ran1 = phone.findObject(By.text("排序"));
                    if (ran1.isSelected()) {
                        break;
                    } else {
                        press_down(1);
                    }
                }
                press_center(1);
                sleepInt(1);
            }
            press_back(2);
        }
    }


    @Test
    @CaseName("相册浏览切换、旋转、幻灯片")
    public void testSlideShow() throws UiObjectNotFoundException, RemoteException {
        enterAlbum();
        addStep("点击左右键切换图片");
        UiObject2 zpicture = phone.findObject(By.text("zpicture"));
        if(zpicture!=null){
            press_center(1);
        }
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                SlideShow();
            }catch (Exception e){
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    enterAlbum();
                    SlideShow();
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
        press_back(3);
    }
    public void SlideShow() throws UiObjectNotFoundException, RemoteException {
        press_menu(1);
        addStep("向左旋转90°四次");
        UiObject2 rotateLeft = phone.findObject(By.text(Pattern.compile("向左旋转.*")));
//        UiObject2 rotate2 = phone.findObject(By.text("旋转"));
//        check("旋转按钮不存在", rotate1 != null||rotate2!=null);
        if(rotateLeft!=null){
            rotateLeft.click();
            rotateLeft.click();
            sleepInt(1);
            press_center(3);
            sleepInt(1);
        }else {
            press_down(1);
            sleepInt(1);
            press_center(3);
            sleepInt(1);
        }
        press_down(1);
        addStep("向右旋转90°四次");
        UiObject2 rotateRight = phone.findObject(By.text(Pattern.compile("向右旋转.*")));
        if(rotateRight!=null) {
            rotateRight.click();
            rotateRight.click();
            sleepInt(1);
            press_center(3);
            sleepInt(1);
        }
        press_up(2);
        sleepInt(1);
        addStep("播放幻灯片");
        UiObject2 slide = phone.findObject(By.text("幻灯片"));
        check("幻灯片按钮不存在", slide != null);
        slide.click();
        slide.click();
        sleepInt(5);
        press_back(1);
        press_center(1);
        addStep("图片左右来回切换'");
        press_right(6);
        press_left(8);
    }

    public void enterAlbum() throws UiObjectNotFoundException, RemoteException {
        addStep("进入相册");
        launchApp(AppName.Gallery,IntentConstants.Gallery);
        UiObject2 album = waitForObj(By.text("按菜单键有更多功能"));
        verify("未进入相册", album != null);
        addStep("相册从大到小排序");
        press_left(1);
        press_right(1);
        press_menu(1);
        UiObject2 content=waitForObj(contentS);
        verify("menu菜单没有弹出",content!=null);
        for(int a=0;a<5;a++) {
            UiObject2 rank = phone.findObject(By.text("排序"));
            if (rank.isSelected()) {
                break;
            } else {
                press_down(1);
            }
        }
        press_center(1);
        for(int b=0;b<5;b++) {
            UiObject2 bigToSmall = phone.findObject(bigToSmallS);
            if (bigToSmall!=null) {
                break;
            } else {
                press_down(1);
            }
        }
        UiObject2 bigToSmall1 = phone.findObject(bigToSmallS);
        verify("没有找到由近到远排序", bigToSmall1 != null);
        bigToSmall1.click();
        UiObject2 bigToSmall2 = phone.findObject(bigToSmallS);
        if(bigToSmall2!=null){
            bigToSmall2.click();
        }
        sleepInt(1);
        UiObject2 rank = phone.findObject(By.text("排序"));
        verify("没有找到排序字样", rank != null);
        press_back(1);
        addStep("进入一相册文件夹,选择一图片打开");
        press_left(2);
        for(int i=0;i<3;i++){
            UiObject2 album1 = phone.findObject(By.text("相册"));
            verify("没有在相册界面", album1 != null);
            press_center(1);
            UiObject2 gallery1 = phone.findObject(By.text("zpicture"));
            if(gallery1!=null){
                break;
            }else {
                press_back(1);
                UiObject2 album6 = phone.findObject(By.pkg("com.android.gallery3d"));
                verify("没有在相册界面", album6 != null);
                sleepInt(1);
                UiObject2 album2 = phone.findObject(By.text("相册"));
                verify("没有在相册界面",album2!=null);
                press_right(1);
            }
        }
        UiObject2 gallery1 = phone.findObject(By.text("zpicture"));
        verify("没有进入相册", gallery1 != null);
        sleepInt(5);
        press_left(1);
        press_center(1);
        UiObject2 menu = phone.findObject(By.text("按菜单键有更多功能"));
        verify("没有进入图片", menu == null);
    }

    @Test
    @CaseName("进入相册查看、复制、删除图片")
    public void testViewDelPic() throws UiObjectNotFoundException, RemoteException {
        initAlbum();
            try {
                ViewDelPic();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    initAlbum();
                    ViewDelPic();
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        addStep("退出相册");
        press_back(2);
    }
    public void ViewDelPic() throws UiObjectNotFoundException, RemoteException {
        UiObject2 zpicture=waitForObj(By.text("zpicture"));
        for (int i=0;i<5;i++) {
            if (zpicture != null) {
                press_center(1);
                break;
            }
            if (zpicture == null) {
                press_center(1);
                press_back(1);
                press_left(1);
                press_center(1);
            }
        }
        press_back(1);
        for(int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper : " + Loop);
            press_right(1);
            sleepInt(1);
            press_menu(1);
//            Log.i(TAG, "ViewDelPic: 11=" + i);
            UiObject2 del = waitForObj(By.text("删除"));
            check("del option not exists", del != null);
            del.click();
            sleepInt(1);
            UiObject2 confirmdel = waitForObj(By.clazz("android.widget.Button").text("取消"));
            check("confirmdel option not exists", confirmdel != null);
            confirmdel.click();
            sleepInt(1);
            press_menu(1);
            UiObject2 cop = waitForObj(By.text("复制"));
            check("del option not exists", del != null);
            cop.click();
            sleepInt(1);
            UiObject2 usbcopy = waitForObj(By.text("选择设备"));
            check("未进入usb设备",usbcopy!=null);
            press_center(1);
            sleepInt(1);
        }

        for (int j = 0; j < 5; j++) {
            UiObject2 gallery2 = phone.findObject(By.text("相册"));
            if (gallery2!=null) {
                break;
            } else {
                press_back(1);
            }
        }
    }

    public void initAlbum() throws UiObjectNotFoundException,RemoteException{
        addStep("打开相册");
        launchApp(AppName.Gallery, IntentConstants.Gallery);
        sleepInt(5);
        UiObject2 gallery = phone.findObject(By.text("相册"));
        verify("gallery not opened", gallery != null);
        sleepInt(3);
        addStep("相册由Z到A排序");
        press_right(3);
        press_menu(1);
        UiObject2 content1=waitForObj(contentS);
        verify("menu菜单没有弹出", content1 != null);
        for(int a=0;a<5;a++) {
            UiObject2 rank = phone.findObject(By.text("排序"));
            if (rank.isSelected()) {
                break;
            } else {
                press_down(1);
            }
        }
        press_center(1);
        for(int b=0;b<5;b++) {
            UiObject2 bigToSmall = phone.findObject(bigToSmallS);
            if (bigToSmall!=null) {
                break;
            } else {
                press_down(1);
            }
        }
        UiObject2 bigToSmall1 = phone.findObject(bigToSmallS);
        verify("没有找到由Z到A排序", bigToSmall1 != null);
        bigToSmall1.click();
        bigToSmall1.click();
        sleepInt(1);
        press_back(1);
    }
}