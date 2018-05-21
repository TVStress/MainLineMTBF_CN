package com.letv.cases.leui;

import android.os.Build;
import android.os.RemoteException;
import android.provider.Contacts;
import android.support.annotation.Nullable;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.PkgName;
import com.letv.common.LetvTestCase;

import org.junit.After;
import org.junit.Test;
import org.junit.Assert;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;


public class GameCentreStress extends LetvTestCase{
    int count=0;


    BySelector download = By.clazz("android.widget.TextView").text(Pattern.compile("立即安装"));
    BySelector check1S = By.clazz("android.widget.TextView").text(Pattern.compile("安装|Install"));
    BySelector openApp = By.clazz("android.widget.TextView").text(Pattern.compile("立即打开|打开|Open"));

    @Test
    @CaseName("进入桌面游戏反复切换画面")
    public void testGameDeskSwitch() throws UiObjectNotFoundException, RemoteException {
        addStep("打开同游戏桌面");
        gotoHomeScreen("游戏");
        press_down(1);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("进入游戏");
            try {
                GameDeskSwitch();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("打开游戏桌面");
                    gotoHomeScreen("游戏");
                    GameDeskSwitch();
                } catch (RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void GameDeskSwitch() throws UiObjectNotFoundException, RemoteException {
        String arrGameDeskSwitch[] = {"猜你喜欢","花式抽卡，欧气满满.*","与您一起，见证宝贝的点滴成长", "畅销榜", "大屏格斗更激爽！", "飙升榜","德州麻将斗地主，象棋斗牛赢三张","感受枪林弹雨的超神快感","热门榜","休闲益智"};
        for (int i = 0; i < arrGameDeskSwitch.length; i++) {
            press_back(2);
            press_down(6 + i);
            addStep("进入到" + arrGameDeskSwitch[i] + "切换3次");
            int GameDesk = phone.findObject(By.text(Pattern.compile(arrGameDeskSwitch[i]))).getParent().getChildCount()+3;
            check("未进入" + arrGameDeskSwitch[i], GameDesk != 0);
            press_right(GameDesk);
            press_left(GameDesk);
        }
        press_back(3);
    }

    @Test
    @CaseName("游戏中心菜单列表遍列")
    public void testGameCenterlist()throws UiObjectNotFoundException, RemoteException {
        addStep("打开应用桌面");
        launchApp(AppName.GameCenter, PkgName.GameCenter);
       try {
           GameCenterlist();
       }
       catch (Exception e){
           try{
               count++;
               failCount(count,getIntParams("Loop"),e.getMessage());
               launchApp(AppName.GameCenter, PkgName.GameCenter);
               GameCenterlist();
           }
           catch (RuntimeException re){
               screenShot();
               junit.framework.Assert.fail(re.getMessage());

           }
       }
        exitApp();
    }
    public void GameCenterlist()throws UiObjectNotFoundException,RemoteException{
        for (int Loop = 0; Loop < getIntParams("Loop");Loop++) {
            addStep("................Loop" + Loop);
            addStep("进入游戏中心");
            press_up(1);
            String arr[] = {"推荐", "排行", "视频", "分类", "运动派", "体感"};
            for (int i = 0; i < arr.length; i++) {
                addStep("进入"+arr[i]+"列表");
                UiObject2 list = waitForObj(By.text(arr[i]));
                list.click();
                sleepInt(2);
                press_down(4);
                press_right(1);
                sleepInt(3);
            }
        }
    }

    @Test
    @CaseName("游戏中心安装App打开并卸载")
    public void testGameCenterloadApp()throws UiObjectNotFoundException, RemoteException {
        for (int Loop=0; Loop < getIntParams("Loop"); Loop++) {
            try {
                GameCenteloadApp();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    GameCenteloadApp();
                } catch (RuntimeException re) {
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());

                }
            }

        }
        press_back(4);
    }

    public void GameCenteloadApp()throws UiObjectNotFoundException, RemoteException{
        addStep("打开应用桌面");
        launchApp(AppName.GameCenter, PkgName.GameCenter);
        addStep("进入游戏中心");
        for (int i = 0; i < 6; i++) {
            UiObject2 tigan = waitForObj(By.text("体感"));
            tigan.click();
        }
        press_up(1);
        press_right(1);
        UiObject2 search = waitForObj(By.clazz("android.widget.ImageView"));
        check("未进入搜索界面", search != null);
        press_down(1);
        sleepInt(2);
        UiObject2 textinput = waitForObj(By.res("com.letv.tvos.gamecenter:id/search_edit_text"));
        check("未输入首字母搜索", textinput != null);
        textinput.setText("WKSZ");
        UiObject2 targetgameApp = waitForObj(By.res(Pattern.compile("com.letv.tvos.gamecenter:id/search_result_recyclerView"))).findObjects(By.clazz("android.widget.TextView")).get(0);
        String getappName = targetgameApp.findObject(By.clazz("android.widget.TextView")).getText();
        check("根据输入的字母查找失败", getappName != null);
        sleepInt(3);
        targetgameApp.click();
        targetgameApp.click();
        sleepInt(3);
        addStep("打开App详情并安装并打开");
        UiObject2 check1 = phone.findObject(download);
        if (check1 != null) {
            check("安装按钮不存在", check1 != null);
            clickAndWaitForNewWindow(check1);
            UiObject2 check2 = waitForObj(openApp, 30000L);
            check("网络不稳定安装未成功", check2 != null);
            check2.click();
        } else {
            UiObject2 check2 = waitForObj(openApp);
//            check("打开按钮不存在", check2 != null);
            check2.click();
            sleepInt(2);
        }
        sleepInt(10);
        addStep("退出游戏App");
        press_back(3);
        exitApp();
        gotoHomeScreen("应用");
        addStep("进入应用管理");
        press_down(2);
        press_menu(1);
        sleepInt(1);
        UiObject2 appManager = waitForObj(By.text("应用管理"));
        appManager.click();
        sleepInt(1);
        UiObject2 apping = waitForObj(By.text(getappName));
        apping.click();
        sleepInt(1);
        UiObject2 appdetails = waitForObj(By.text("应用详情"));
        check("未进入应用详情界面", appdetails != null);
        appdetails.click();
        press_right(3);
        press_down(3);
        addStep("卸载游戏App");
        UiObject2 uninstallApp = waitForObj(By.text("卸载应用"));
        uninstallApp.click();
        sleepInt(2);
        UiObject2 appOk = waitForObj(By.text("确定"));
        appOk.click();
        sleepInt(1);
        press_center(1);
        press_back(4);
    }
}
