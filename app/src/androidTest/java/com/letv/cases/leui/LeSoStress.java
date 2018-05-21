package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.PkgName;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import junit.framework.Assert;

import org.junit.Test;

import java.util.regex.Pattern;


public class LeSoStress extends LetvTestCase{
    int count=0;
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    @CaseName("进入桌面管理调整到桌面搜索")
    public void testDeskSwitchScarch() throws UiObjectNotFoundException, RemoteException {
    DesktopAdjustment("搜索");
    }

    @Test
    @CaseName("搜索桌面进入乐看搜索，反复搜索，并退出")
    public void testSearchExit() throws UiObjectNotFoundException, RemoteException {
        addStep("打开搜索桌面");
        gotoHomeScreen("搜索");
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper : " + Loop);
            try {
                SearchExit();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("打开搜索桌面");
                    gotoHomeScreen("搜索");
                    SearchExit();
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }

    public void SearchExit() throws UiObjectNotFoundException, RemoteException {
        sleepInt(2);
        addStep("进入乐看搜索");
        UiObject2 leSearch = waitForObj(By.res(Pattern.compile("com.stv.plugin.search:id/search_box|com.stv.plugin.search:id/search_box_notice")));
        check("没有找到搜索框", leSearch != null);
        leSearch.click();
//        leSearch.click();//有的平台需要点击两点才能进入；
        sleepInt(2);
        UiObject2 leSo = phone.findObject(By.pkg(Pattern.compile("com.letv.leso|com.letv.search.plugin|")));
        check("未进入乐看搜索", leSo != null);
        sleepInt(15);
        UiObject2 update=phone.findObject(By.text("马上体验"));
        if (update!=null){
            update.click();
            sleep(70);
            press_up(1);
            press_center(1);
        }
        addStep("选择电视剧榜单");
        press_right(7);
        sleepInt(3);
        UiObject2 listAll = waitForObj(By.clazz("android.widget.TextView").text("全部"));
        check("全部榜单不存在",listAll!=null);
        listAll.click();
        press_center(1);
        sleepInt(2);
        press_down(3);
        press_center(1);
        sleepInt(1);
        press_left(5);
        UiObject2 clear=waitForObj(By.res("com.letv.search.plugin:id/searchboard_clear_btn"));
        check("清除按钮不存在",clear!=null);
        clear.click();
        clear.click();
        addStep("搜索WMNCQ，得到电视剧武媚娘传奇");
        // 首拼音搜索 武媚娘传奇
        UiObject2 W = phone.findObject(By.clazz("android.widget.TextView").text("W"));
        UiObject2 M = phone.findObject(By.clazz("android.widget.TextView").text("M"));
        UiObject2 N = phone.findObject(By.clazz("android.widget.TextView").text("N"));
        UiObject2 C = phone.findObject(By.clazz("android.widget.TextView").text("C"));
        UiObject2 Q = phone.findObject(By.clazz("android.widget.TextView").text("Q"));
        W.click();W.click();M.click();M.click();N.click();N.click();C.click();C.click();Q.click();Q.click();
        sleepInt(1);
        UiObject2 playSets =waitForObj(By.text(Pattern.compile("武媚娘传奇.*")));
        check ("搜索结果不存在",playSets!=null);
        press_back(1);
    }


    @Test
    @CaseName("从应用桌面打开乐看搜索，反复搜索")
    public void testLesoAppDesk() throws UiObjectNotFoundException, RemoteException {
//    launchApp(AppName.LeSo, PkgName.LeSo);
    AppSearch();
    sleepInt(3);
    UiObject2 leSo = waitForObj(By.pkg("com.letv.leso"));
    verify("未进入乐看搜索", leSo != null);
    addStep("进入乐看搜索");
    UiObject2 search=null;
    search=waitForObj(By.res("com.letv.leso:id/search_box"));
    for (int i=0;i<5;i++){
        search=waitForObj(By.res("com.letv.leso:id/search_box"));
        if (search==null){
            sleepInt(3);
        }
    }
    verify("can't enter the leso", search != null);
    clickAndWaitForNewWindow(search);
    for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
        System.out.println(".............looper : " + Loop);
        try {
            LesoAppDesk();
        }catch (Exception e){
            try {
                count ++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                AppSearch();
//                launchApp(AppName.LeSo, PkgName.LeSo);
                sleepInt(1);
                UiObject2 leSo1 = waitForObj(By.pkg("com.letv.leso"));
                check("未进入乐看搜索", leSo1 != null);
                LesoAppDesk();
            }catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());
            }
        }
    }
    addStep("退出乐看搜索");
    press_back(2);
}

    public void LesoAppDesk() throws UiObjectNotFoundException, RemoteException {
        sleepInt(2);
        press_center(1);
        UiObject2 clear=null;
        clear = waitForObj(By.res(Pattern.compile("com.letv.leso:id/searchboard_clear_btn|com.letv.search.plugin:id/searchboard_clear_btn")));
        check("没有找到清空内容的按钮", clear != null);
        sleepInt(1);
        clear.click();
        clear.click();
        sleepInt(1);
        addStep("选择电视剧榜单");
        press_right(4);
        sleepInt(3);
        press_right(3);
        UiObject2 listAll = phone.findObject(By.clazz("android.widget.TextView").textContains("全部"));
        check("全部榜单不存在",listAll!=null);
        listAll.click();
        listAll.click();
        sleepInt(2);
        press_down(3);
        press_center(1);
        sleepInt(2);
        press_left(5);
        clear.click();
        clear.click();
        addStep("搜索WMNCQ，得到电视剧武媚娘传奇");
        // 首拼音搜索 武媚娘传奇
        UiObject2 W = phone.findObject(By.clazz("android.widget.TextView").text("W"));
        UiObject2 M = phone.findObject(By.clazz("android.widget.TextView").text("M"));
        UiObject2 N = phone.findObject(By.clazz("android.widget.TextView").text("N"));
        UiObject2 C = phone.findObject(By.clazz("android.widget.TextView").text("C"));
        UiObject2 Q = phone.findObject(By.clazz("android.widget.TextView").text("Q"));
        W.click();W.click();M.click();M.click();N.click();N.click();C.click();C.click();Q.click();Q.click();
        sleepInt(3);
        UiObject2 playSets = waitForObj(By.text(Pattern.compile("武媚娘传奇.*")));
        verify ("搜索结果不存在",playSets!=null);
        press_back(1);
    }

    public void AppSearch()throws UiObjectNotFoundException, RemoteException{
        addStep("搜索");
        gotoHomeScreen("应用");
        sleepInt(5);
        UiObject2 allapp=phone.findObject(By.text(Pattern.compile("全部应用")));
        if(allapp!=null){
            allapp.click();
            sleepInt(5);
            press_down(5);
        }else {
            press_down(5);
        }
        UiObject2 watchingHot=waitForObj(By.res("com.stv.plugin.app:id/cellview_label").text("万象搜索"));
        watchingHot.click();
        watchingHot.click();
    }

}
