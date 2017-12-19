package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import junit.framework.Assert;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Created by pc7 on 7/28/17.
 */

public class LeChildStress extends LetvTestCase {
    int count = 0;
    @Test
    @CaseName("儿童桌面分类视频随机播放")
    public void testDesktopVideo() throws UiObjectNotFoundException, RemoteException {
        addStep("切换到儿童桌面");
        gotoHomeScreen("儿童");
        press_down(1);
        press_back(1);
        press_down(8);
        int left=0,right=0,down=0,up=0;
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            try{
                addStep(".............looper : " + Loop);
                if (Loop % 2 == 0) {
                    down = getRandom(16);
                    up=down;
                    addStep("按下键"+down+"次");
                    press_down(down);
                } else {
                    press_up(up);
                }
                UiObject2 child=phone.findObject(By.text("儿童").selected(true));
                UiObject2 child2=phone.findObject(By.text("儿童").focused(true));
                verify("不在儿童桌面",child!=null||child2!=null);
                if (Loop % 2 == 0) {
                    right= getRandom(5);
                    left=right;
                    addStep("按右键"+right+"次");
                    press_right(right);
                } else {
                    addStep("按左键"+left+"次");
                    press_left(left);
                }
                child=phone.findObject(By.text("儿童").selected(true));
                verify("不在儿童桌面",child!=null);
                addStep("打开该儿童节目并播放60S");
                press_center(1);
                UiObject2 adapter=waitForObj(By.clazz("android.widget.AdapterView"));
                if(adapter!=null){
                    press_center(1);
                }
                sleepInt(60);
                press_back(2);
                for(int i=0;i<3;i++){
                    UiObject2 launcher=waitForObj(By.pkg("com.stv.launcher"));
                    if(launcher==null){
                        press_back(1);
                    }else break;
                }

            }
            catch (Exception e){
                try{
                    count++;
                    failCount(count,getIntParams("Loop"),e.getMessage());
                    addStep("切换到儿童桌面");
                    gotoHomeScreen("儿童");
                    press_down(1);
                    press_back(1);
                    press_down(8);
                    addStep(".............looper : " + Loop);
                    if (Loop % 2 == 0) {
                        down = getRandom(16);
                        up=down;
                        addStep("按下键"+down+"次");
                        press_down(down);
                    } else {
                        press_up(up);
                    }
                    UiObject2 child=phone.findObject(By.text("儿童").selected(true));
                    UiObject2 child2=phone.findObject(By.text("儿童").focused(true));
                    verify("不在儿童桌面",child!=null||child2!=null);
                    if (Loop % 2 == 0) {
                        right= getRandom(5);
                        left=right;
                        addStep("按右键"+right+"次");
                        press_right(right);
                    } else {
                        addStep("按左键"+left+"次");
                        press_left(left);
                    }
                    child=phone.findObject(By.text("儿童").selected(true));
                    verify("不在儿童桌面",child!=null);
                    addStep("打开该儿童节目并播放60S");
                    press_center(1);
                    UiObject2 adapter=waitForObj(By.clazz("android.widget.AdapterView"));
                    if(adapter!=null){
                        press_center(1);
                    }
                    sleepInt(60);
                    press_back(2);
                    for(int i=0;i<3;i++){
                        UiObject2 launcher=waitForObj(By.pkg("com.stv.launcher"));
                        if(launcher==null){
                            press_back(1);
                        }else break;
                    }

                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }

            }
            addStep("返回到儿童桌面");
        }
        press_back(4);
    }




    @Test
    @CaseName("进入儿童TV进行搜索播放")
    public void testChileVideo() throws UiObjectNotFoundException, RemoteException {
        addStep("从应用进入到儿童TV");
//        launchApp(AppName.ChildrenTV, IntentConstants.ChildrenTV);
        for(int Loop=0;Loop < getIntParams("Loop");Loop++){
            LanuchChildrenTV();
            UiObject2 skip=waitForObj(By.res("com.letv.tv.lechild:id/register_sex_btn_skip"));
            if(skip!=null){
                skip.click();
                skip.click();
            }
            try{
                ChileVideo();
            }
            catch (Exception e){
                try{
                    count++;
                    failCount(count,getIntParams("Loop"),e.getMessage());
                    LanuchChildrenTV();
                    ChileVideo();
                }
                catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
            press_back(10);
        }
    }
    public void ChileVideo() throws UiObjectNotFoundException, RemoteException {
        addStep("儿童TV进行搜索播放");
        press_right(5);
        press_up(3);
        UiObject2 search=waitForObj(By.res("com.letv.tv.lechild:id/lechild_search"));
        check("未进入搜索",search!=null);
        search.click();
        search.click();
        search.click();
        sleepInt(3);
        UiObject2 C = phone.findObject(By.clazz("android.widget.TextView").text("C"));
        UiObject2 J = phone.findObject(By.clazz("android.widget.TextView").text("J"));
        UiObject2 F = phone.findObject(By.clazz("android.widget.TextView").text("F"));
        UiObject2 X = phone.findObject(By.clazz("android.widget.TextView").text("X"));
        C.click();C.click();J.click();J.click();F.click();F.click();X.click();X.click();
        sleepInt(1);
        addStep("进行儿童视频，播放5分钟");
        UiObject2 playSets = waitForObj(By.text(Pattern.compile("超级飞侠.*")));
        if(playSets!=null) {
            check("未进入超级飞侠视频播放", playSets != null);
            playSets.click();
            playSets.click();
            press_center(2);
            sleepInt(60);
            press_down(1);
            press_right(1);
            press_center(1);
            sleepInt(60);
        }else {
            press_back(1);
            C.click();
            C.click();
            J.click();
            J.click();
            F.click();
            F.click();
            X.click();
            X.click();
            sleepInt(1);
            check("未进入超级飞侠视频播放", playSets != null);
            playSets.click();
            playSets.click();
            press_center(2);
            sleepInt(60);
            press_down(1);
            press_right(1);
            press_center(1);
            sleepInt(60);
        }

        press_back(2);
        /*Object2 lechild_logo=phone.findObject(By.clazz("android.widget.ImageView").res("com.letv.tv.lechild:id/lechild_search"));
        for (int t=0;t<4;t++) {
            if (lechild_logo == null) {
                press_back(2);
                press_up(1);
                sleepInt(5);
            }
            check("未退出到儿童TVlogo",lechild_logo!=null);
            break;
        }*/
    }




    @Test
    @CaseName("进入儿童TV反复进入与切换画面")
    public void testChileSwichover() throws UiObjectNotFoundException, RemoteException {
        for(int Loop=0;Loop<getIntParams("Loop");Loop++){
            try{
                ChileSwichover();
            }
            catch (Exception e){
                try{
                    count++;
                    failCount(count,getIntParams("Loop"),e.getMessage());
                    ChileSwichover();
                }
                catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
        press_back(5);
    }
    public void ChileSwichover() throws UiObjectNotFoundException, RemoteException {
        addStep("进入儿童TV");
        LanuchChildrenTV();
        UiObject2 skip=waitForObj(By.res("com.letv.tv.lechild:id/register_sex_btn_skip"));
        if(skip!=null){
            skip.click();
            skip.click();
        }
        addStep("儿童TV反复切换画面");
            press_right(getRandom(30));
            press_left(getRandom(30));
        sleepInt(2);
    }


    public void LanuchChildrenTV()throws UiObjectNotFoundException, RemoteException{
        addStep("进入应用儿童TV");
        gotoHomeScreen("应用");
        retry();
        press_down(3);
        press_right(3);
        UiObject2 allapp=phone.findObject(By.text(Pattern.compile("全部应用")));
        if(allapp!=null){
            allapp.click();
            press_down(5);
        }
        UiObject2 ChildrenTV=waitForObj(By.res("com.stv.plugin.app:id/cellview_label").text("儿童TV"));
        check("未进入儿童TV",ChildrenTV!=null);
        clickAndWaitForNewWindow(ChildrenTV);
        sleepInt(2);
    }
}


