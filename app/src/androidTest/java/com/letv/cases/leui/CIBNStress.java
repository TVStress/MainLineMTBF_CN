package com.letv.cases.leui;

import android.os.Build;
import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.text.Selection;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import junit.framework.Assert;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class CIBNStress extends LetvTestCase {
    int count = 0;

//    @Override
//    public void tearDown() throws Exception {
//        press_back(4);
//        verify("没有返回桌面", phone.getCurrentPackageName().equals(PACKAGE_HOME));
//    }

    public void loginAccount() throws UiObjectNotFoundException, RemoteException {
        UiObject2 loginNow = phone.findObject(By.text("立即登录"));
        if (loginNow != null) {
            addStep("登录会员");
            loginNow.click();
            sleepInt(4);
            UiObject2 login = waitForObj(By.text(Pattern.compile(".*添加帐号.*|.*帐号密码登录.*|.*其他帐号登录.*")));
            if (login != null) {
                login.click();
                sleepInt(2);
            }
            sleepInt(2);
            UiObject2 userName = waitForObj(By.text(Pattern.compile("乐视帐号|会员帐号|超级电视帐号"))).getParent().findObject(By.clazz("android.widget.EditText"));
            verify("can't find userName.", userName != null);
            userName.setText(getStrParams("USERNAME"));
            sleepInt(2);
            press_down(1);
            UiObject passwd = null;
            if (LetvUI(6.0)) {
                passwd = phone.findObject(new UiSelector().className("android.widget.EditText").instance(1));
                check("can't find passwd.", passwd != null);
                sleepInt(2);
                if (Build.DEVICE.contains("U4")) {
                    passwd = phone.findObject(new UiSelector().className("android.widget.EditText").instance(1));
                    check("can't find passwd.", passwd != null);
                    sleepInt(2);
                }
            } else {
                passwd = phone.findObject(new UiSelector().className("android.widget.EditText").instance(1));
                check("can't find passwd.", passwd != null);
                sleepInt(2);
            }
            passwd.setText(getStrParams("PASSWORD"));
            sleepInt(1);
            UiObject2 loginNow1 = phone.findObject(By.clazz("android.widget.Button").text("立即登录"));
            verify("can't find loginNow.", loginNow1 != null);
            press_down(1);
            press_center(1);
            sleepInt(7);
        }
    }

    BySelector CIBNS = By.pkg("cn.cibntv.ott");
    BySelector alltv = By.text(Pattern.compile("全部"));
    BySelector cibnPlay = By.text(Pattern.compile("播放|第.*集|继续播放"));
    BySelector exitPlay = By.text(Pattern.compile("退出播放|退出.*"));

    @Test
    @CaseName("CIBN高清影视应用里反复滑动")
    public void testCIBNSwipe() throws UiObjectNotFoundException, RemoteException {
        cibnEnter();
        UiObject2 install = phone.findObject(By.text("下载"));
        if (install != null) {
            install.click();
            install.click();
            for (int i = 0; i < 8; i++) {
                UiObject2 open = waitForObj(By.text("打开"));
                if (open != null) {
                    clickAndWaitForNewWindow(open);
                    break;
                }
                sleepInt(7);
            }
        }
        sleepInt(10);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            loginAccount();
            try {
                sleepInt(10);
                CIBNSwipe();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    cibnEnter();
                    CIBNSwipe();
                } catch (RuntimeException re) {
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void CIBNSwipe() throws UiObjectNotFoundException {
        addStep("上下键各10次");
        press_down(10);
        UiObject2 CIBN = phone.findObject(CIBNS);
        check("不在CIBN高清影视应用", CIBN != null);
        press_up(10);
        UiObject2 CIBN1 = phone.findObject(CIBNS);
        check("不在CIBN高清影视应用", CIBN1 != null);
        for (int i = 0; i < 10; i++) {
            press_right(10);
            UiObject2 CIBN3 = phone.findObject(CIBNS);
            check("不在CIBN高清影视应用", CIBN3 != null);
            press_left(10);
            press_down(1);
            UiObject2 CIBN2 = phone.findObject(CIBNS);
            check("不在CIBN高清影视应用", CIBN2 != null);
        }
    }
    public void cibnEnter() throws UiObjectNotFoundException {
        press_back(2);
        gotoHomeScreen("应用");
        press_down(1);
        press_back(3);
        press_down(2);
        press_right(3);
        UiObject2 allapp = phone.findObject(By.text(Pattern.compile("全部应用")));
        check("未进入全部应用", allapp != null);
        allapp.click();
        sleepInt(1);
        press_down(10);
        sleepInt(2);
        UiObject2 cibnapp = phone.findObject(By.text("CIBN推荐应用"));
        cibnapp.click();
        press_down(1);
        UiObject2 cibn = phone.findObject(By.text(Pattern.compile("CIBN.*")));
        if (cibn != null) {
            cibn.click();
            sleepInt(5);
        } else {
            press_center(1);
            sleepInt(5);
        }
        UiObject2 retry=waitForObj(By.text("重试"));
        for(int k=0;k<3;k++){
            if(retry!=null){
                retry.click();
                retry.click();
            }
        }
        UiObject2 close = phone.findObject(By.text("立即关闭"));
        if (close!=null) {
            check("未出现关闭", close!=null);
            close.click();
            close.click();
        }
    }


    @Test
    @CaseName("CIBN高清影视各频道列表观看视频")
    public void testtLiveChannel() throws UiObjectNotFoundException, RemoteException {
        for (int Loop=0;Loop<getIntParams("Loop");Loop++) {
            try {
                cibnEnter();
                LiveChannel();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    cibnEnter();
                    LiveChannel();
                } catch (RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void LiveChannel() throws UiObjectNotFoundException, RemoteException {
        press_down(4);
        sleepInt(1);
        String arr[] = {"首页", "会员", "分类", "发现", "我的"};
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < arr.length; i++) {
                UiObject2 list = waitForObj(By.text(arr[i]));
                list.click();
                sleepInt(2);
            }
        }
        UiObject2 listclass = waitForObj(By.text("分类"));
        listclass.click();
        listclass.click();
        sleepInt(2);
        press_up(1);

//        String arrlist[] = {"cn.cibntv.ott:id/large_image", };

//        for (int j = 0; j < arrlist.length; j++) {
            addStep("进入任意电影播放60s并退出");
            UiObject2 film = waitForObj(By.res("cn.cibntv.ott:id/large_image"));
            check("未进入电影", film != null);
            film.click();
            film.click();
            UiObject2 all1 = waitForObj(alltv);
            all1.click();
            all1.click();
            sleepInt(2);
            press_right(1);
            press_down(1);
            press_center(1);
            UiObject2 play1 = waitForObj(cibnPlay);
            play1.click();
            play1.click();
            sleepInt(60);
            exitApp();
//            UiObject2 exitplayone = waitForObj(exitPlay);
//            exitplayone.click();
//            exitplayone.click();
            sleepInt(2);
            press_back(4);
            sleepInt(1);
    }


    @Test
    @CaseName("小C精选高清影视反复滑动、任意视频播放、暂停")
    public void testBigCSwipePlay() throws UiObjectNotFoundException, RemoteException {
        addStep("进入小C精选应用里");
        gotoHomeScreen("小C精选");
        for (int Loop=0;Loop<getIntParams("Loop");Loop++) {
            try {
                BigCSwipePlay();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("小C精选");
                    BigCSwipePlay();
                } catch (RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
        press_back(4);
    }
    public void BigCSwipePlay() throws UiObjectNotFoundException, RemoteException{
        press_down(1);
        press_back(2);
        press_down(4);
        UiObject2 foryouRecomment = phone.findObject(By.text("为你推荐"));
        check("未进入为你推荐界面", foryouRecomment != null);
        addStep("进行小C精选界面视频反复滑动");
        for (int i = 0; i < 5; i++) {
            press_down(7);
            press_right(2);
            press_up(7);
            press_left(2);
            UiObject2 bigC = phone.findObject(By.text("小C精选"));
            check("未在小C精选画面", bigC != null);
        }
        press_back(2);
        press_down(4);
        addStep("进入为你推荐播放");
        UiObject2 foryouRecomment1 = phone.findObject(By.text("为你推荐"));
        check("未进入为你推荐界面", foryouRecomment1 != null);
        for (int j = 0; j < 5; j++) {
            press_center(1);
            sleepInt(1);
            UiObject2 play2 = waitForObj(cibnPlay);
            check("未进入播放", play2 != null);
            play2.click();
            addStep("播放视频1分钟");
            sleepInt(60);
            addStep("播放视频反复播放、暂停10次");
            for (int i = 0; i < 10; i++) {
                press_center(1);
                sleepInt(2);
                press_center(1);
                sleepInt(4);
            }
            press_back(1);
            exitplayTV();
            press_down(1);
            sleepInt(1);
        }
        press_back(2);
    }
    public void exitplayTV(){
        addStep("退出播放");
        press_back(1);
        sleepInt(2);
        exitApp();
//        UiObject2 exitplay1 = waitForObj(exitPlay);
//        exitplay1.click();
        sleepInt(2);
        press_back(1);
        sleepInt(1);
    }
}
