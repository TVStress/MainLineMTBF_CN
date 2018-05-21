package com.letv.cases.leui;


import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiAutomatorBridge;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiWatcher;

import com.letv.common.AppName;
import com.letv.common.CaseName;
//import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import junit.framework.Assert;

import org.junit.Test;

import java.util.regex.Pattern;
public class CinemaStress extends LetvTestCase {
    int count = 0;

    @Test
    @CaseName("进入桌面同步院线反复切换画面")
    public void testCinemaModeDesk() throws UiObjectNotFoundException, RemoteException {
        addStep("打开同步院线桌面");
        gotoHomeScreen("同步剧场");
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("进入院线热映");
            try {
                CinemaModeDesk();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("打开同步院线桌面");
                    gotoHomeScreen("同步剧场");
                    CinemaModeDesk();
                } catch (RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void CinemaModeDesk() throws UiObjectNotFoundException, RemoteException {
        for (int i=1;i<=2;i++){
            press_back(3);
            if(i==1){
                press_down(1);
            }
            if (i==2){
                press_down(3);
            }
            press_right(3);
            press_left(3);
        }

//        String arrCinemaMode[] = {".*影帝，拼演技.*", "变异怪兽活人祭",".*蜘蛛.*","豆瓣高分电影","速度与激情系列","国产冷门佳片"};
//        for (int i = 0; i < arrCinemaMode.length; i++) {
//            press_back(2);
//            sleepInt(3);
//            press_down(3 + i);
//            addStep("进入到" + arrCinemaMode[i] + "切换3次");
//            int spiderMan = phone.findObject(By.text(Pattern.compile(arrCinemaMode[i]))).getParent().findObject(By.res("com.stv.plugin.cinema:id/content_poster")).getChildCount();
//            check("未进入" + arrCinemaMode[i], spiderMan != 0);
//            press_right(spiderMan-1);
//            press_left(spiderMan-1);
//            UiObject2 desktop1=phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile("同步剧场")).selected(true));
//            UiObject2 desktop2=phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile("同步剧场")).focused(true));
//            verify("没有返回到同步剧场桌面", desktop1 != null || desktop2 != null);
//        }
    }

    @Test
    @CaseName("进入桌面同步院线遍历全部并播放2分钟")
    public void testCinemaModeDeskPlay() throws UiObjectNotFoundException, RemoteException {
        addStep("打开同步院线桌面");
        gotoHomeScreen("同步剧场");
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("进入院线热映");
            if(Loop%2 == 0){
                UiObject2 poster1 = waitForObj(By.res("com.stv.plugin.cinema:id/hit_poster1"));
                poster1.click();
                poster1.click();
            }
            if (Loop%2 ==1){
                press_down(2);
                UiObject2 poster2 = waitForObj(By.res("com.stv.plugin.cinema:id/content_poster1"));
                poster2.click();
                poster2.click();
            }

            try{
                CinemamodeDeskPlay();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("打开同步院线桌面");
                    gotoHomeScreen("同步剧场");
                    addStep("进入院线热映");
                    if(Loop%2 == 0){
                        UiObject2 poster1 = waitForObj(By.res("com.stv.plugin.cinema:id/hit_poster1"));
                        poster1.click();
                        poster1.click();
                    }
                    if (Loop%2 ==1){
                        press_down(2);
                        UiObject2 poster2 = waitForObj(By.res("com.stv.plugin.cinema:id/content_poster1"));
                        poster2.click();
                        poster2.click();
                    }
                    CinemamodeDeskPlay();
                } catch (RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void CinemamodeDeskPlay() throws UiObjectNotFoundException, RemoteException {
            sleepInt(1);
            addStep("进入同步剧场播放2分钟");
            UiObject2 film_play = waitForObj(By.res("com.letv.tv:id/tv_play").text(Pattern.compile(".*播放.*")));
            check("未进入同步影院播放",film_play !=null);
            film_play.click();
            sleepInt(60);
            press_back(1);
            sleepInt(1);
            addStep("退出同步剧场视频");
            UiObject2 exit_play =waitForObj(By.res("com.letv.tv:id/exit_recommend_button").text("退出播放"));
            sleepInt(1);
            exit_play.click();
            press_back(3);


//        String arrCinemaMode[] = {".*影帝，拼演技.*", "变异怪兽活人祭",".*蜘蛛.*","豆瓣高分电影","速度与激情系列","国产冷门佳片"};
//        for (int i = 0; i < arrCinemaMode.length; i++) {
//            press_back(2);
//            sleepInt(2);
//            press_down(3 + i);
//            addStep("进入" + arrCinemaMode[i] + "播放60秒");
//
//            UiObject2 spiderMan = phone.findObject(By.text(Pattern.compile(arrCinemaMode[i]))).getParent().findObject(By.res(Pattern.compile("com.stv.plugin.cinema:id/classical_poster1|com.stv.plugin.cinema:id/content_poster1")));
//            check("未进入" + arrCinemaMode[i], spiderMan != null);
//            spiderMan.click();
//            spiderMan.click();
//            sleepInt(60);
//            exitcinema();
//            sleepInt(5);
//            UiObject2 desktop1=phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile("同步剧场")).selected(true));
//            UiObject2 desktop2=phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile("同步剧场")).focused(true));
//            check("没有返回到同步剧场桌面", desktop1 != null || desktop2 != null);
//        }
    }

    public void exitcinema() {
        for (int j = 0; j < 6; j++) {
            press_back(1);
            UiObject2 exitPlay = phone.findObject(By.text(Pattern.compile("退出播放|是")));
            if (exitPlay != null) {
                sleepInt(2);
                exitPlay.click();
            }
            UiObject2 coming = phone.findObject(By.text(Pattern.compile("即将上映.*")));
            if (coming != null) break;
        }
    }

    public void deskNO() {
        UiObject2 deskno = phone.findObject(By.text("重试"));
        for (int i = 0; i < 3; i++) {
            if (deskno != null) {
                deskno.click();
                sleepInt(5);
            }
        }
    }

    @Test
    @CaseName("从应用进入同步剧场反复进入")
    public void testLanuchGolive() throws UiObjectNotFoundException ,RemoteException {
        gotoHomeScreen("应用");
        deskNO();
        for (int Loop = 0;Loop < getIntParams("Loop");Loop++) {
            addStep("......Loop :"+Loop);
            try {
                LanuchGolive();
                press_back(3);
            }
            catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("应用");
                    LanuchGolive();
                    press_back(3);

                } catch (RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void LanuchGolive()throws UiObjectNotFoundException, RemoteException{
        addStep("进入Golive");
        gotoHomeScreen("应用");
        press_down(1);
        press_back(2);
        press_down(3);
        press_right(3);
        UiObject2 allapp=phone.findObject(By.text(Pattern.compile("全部应用")));
        if(allapp!=null){
            allapp.click();
            press_down(5);
        }
        UiObject2 Golive=waitForObj(By.res("com.stv.plugin.app:id/cellview_label").text("同步院线"));
        check("未进入同步剧场",Golive!=null);
        clickAndWaitForNewWindow(Golive);
        sleepInt(12);
        UiObject2 quanqiubo = waitForObj(By.res("com.golive.letvcinema:id/image_logo"));
        check("没有进入Golive页面",quanqiubo != null);
    }

    @Test
    @CaseName("从应用进入同步剧场反复切换海报")
    public void testSwitchPoster() throws UiObjectNotFoundException ,RemoteException {
        gotoHomeScreen("应用");
        deskNO();
        try {
            LanuchGolive();
            SwitchPoster();
            exitApp();

        }
        catch (Exception e) {
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                gotoHomeScreen("应用");
                LanuchGolive();
                SwitchPoster();
                exitApp();

            } catch (RuntimeException re) {
                screenShot();
                Assert.fail(re.getMessage());
            }
        }
        press_back(4);
    }
    public void SwitchPoster() throws UiObjectNotFoundException ,RemoteException{
        for (int Loop = 0;Loop < getIntParams("Loop");Loop++){
            addStep("............Loop :"+Loop);
            addStep("向左切换海报");
            press_right(GenerateRandom(20));
            sleep(1);
            addStep("向右切换海报");
            press_left(GenerateRandom(20));
            sleep(1);
            UiObject2 loginworld=waitForObj(By.res("com.golive.letvcinema:id/image_logo"));
            check("没有在Golive页面",loginworld!= null);
        }
    }

    @Test
    @CaseName("从应用进入同步剧场反复切换底部标签")
    public void testSwitchBottomItem() throws UiObjectNotFoundException ,RemoteException {
        gotoHomeScreen("应用");
        deskNO();
        try {
            LanuchGolive();
            SwitchBottomItem();
            exitApp();
            press_back(3);
        }
        catch (Exception e) {
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                gotoHomeScreen("应用");
                LanuchGolive();
                SwitchBottomItem();
                exitApp();
                press_back(3);

            } catch (RuntimeException re) {
                screenShot();
                Assert.fail(re.getMessage());
            }
        }
    }
    public void SwitchBottomItem()throws UiObjectNotFoundException, RemoteException{
        for (int Loop = 0;Loop < getIntParams("Loop");Loop++){
            addStep("切换底部标签");
            addStep("........loop"+Loop);
            for (int i =0;i<5;i++){
                press_down(1);
                press_right(4);
                sleepInt(1);
                press_left(4);
            }
        }

    }

    @Test
    @CaseName("从应用进入同步剧场反复提交反馈")
    public void testReportQuestion() throws UiObjectNotFoundException ,RemoteException {
        gotoHomeScreen("应用");
        deskNO();
        try {
            LanuchGolive();
            ReportQuestion();
        }
        catch (Exception e) {
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                gotoHomeScreen("应用");
                LanuchGolive();
                ReportQuestion();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }
        exitApp();
        press_back(3);
    }
    public void ReportQuestion()throws UiObjectNotFoundException,RemoteException{
        press_down(3);
        press_right(4);
        UiObject2 more = waitForObj(By.res("com.golive.letvcinema:id/btn_main_aty_more").text("更多"));
        verify("没有找到更多", more != null);
        more.click();
        press_up(1);
        sleepInt(2);
        for (int Loop = 0;Loop<getIntParams("Loop");Loop++) {
            addStep("............Loop :" + Loop);
            UiObject2 question = waitForObj(By.res("com.golive.letvcinema:id/tv_more_tab_feedback").text("问题反馈"));
            check("没有找到问题反馈", question != null);
            question.click();
            sleepInt(2);
            press_down(1);
            press_down(GenerateRandom(4));
            sleepInt(2);
            press_center(2);
            UiObject2 sendquestion=waitForObj(By.res("com.golive.letvcinema:id/btn_submit").text("提交反馈"));
            check("未进入提交反馈",sendquestion!=null);
            sendquestion.click();
            press_center(1);
            sleepInt(4);
        }
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();
        phone.registerWatcher("nexTime", nexTime);
    }
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        phone.removeWatcher("nexTime");
    }
    private final UiWatcher nexTime = new UiWatcher() {
        public boolean checkForCondition() {
            UiObject2 nextTime = phone.findObject(By.text(Pattern.compile("下次再说|暂不领取")));
            if (nextTime != null) {
                nextTime.click();
                nextTime.click();
                sleepInt(5);
                return true;
            } else {
                return false;
            }
        }
    };
}

