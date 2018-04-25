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

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;



public class ThemesStress extends LetvTestCase{
    int count=0;

    @Test
    @CaseName("测试主题商店")
    public void testThemes()throws UiObjectNotFoundException, RemoteException{
        addStep("切换到应用桌面");
        gotoHomeScreen("应用");
        deskNO();
        for(int Loop=0;Loop<getIntParams("Loop");Loop++) {
            try {
                Themes();
            } catch (Exception e) {
                try {
                    failCount(count++, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("应用");
                    deskNO();
                    Themes();
                } catch (RuntimeException re) {
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
        myThemes();

    }
    public void Themes()throws UiObjectNotFoundException, RemoteException{
        press_down(1);
        press_back(2);
        press_down(5);

        UiObject2 themes = phone.findObject(By.res("com.stv.plugin.app:id/cellview_label").text("主题"));
//        launchApp(AppName.Music,IntentConstants.Music);
        check("未进入主题",themes != null);
        themes.click();
//        themes.click();
        sleepInt(2);
        press_right(2);
        addStep("进入全部主题");
        UiObject2 allThemes = waitForObj(By.res("com.stv.thememanager:id/fl_theme_all"));
        verify("没有找到全部",allThemes!= null);
        sleepInt(1);
        allThemes.click();
        sleepInt(1);
        addStep("进入免费主题");
        for (int i=0;i<3;i++) {
            UiObject2 freethemes = waitForObj(By.text("免费主题"));
            check("没有找到免费主题", freethemes != null);
            freethemes.click();
            press_center(1);
        }
        sleepInt(1);
        addStep("选择一项免费主题");
        press_right(1);//将焦点移动至免费主题的第一个位置
        press_center(1);
        sleepInt(1);
        addStep("立即使用");
        press_down(1);
        UiObject2 useimmediately = waitForObj(By.text(Pattern.compile("立即使用|立即使用(免费)|当前使用")));
        if (useimmediately!=null) {
            verify("没有找到立即使用", useimmediately != null);
            useimmediately.click();
            useimmediately.click();
            sleepInt(5);
        }
        addStep("返回应用桌面");
        press_back(3);
        sleepInt(3);
    }
    public void myThemes()throws UiObjectNotFoundException, RemoteException{
        addStep("切换到应用桌面");
        gotoHomeScreen("应用");
        deskNO();
        press_down(1);
        press_back(2);
        sleepInt(1);
        press_down(5);
//        UiObject2 allapp=phone.findObject(By.text(Pattern.compile("全部应用")));
//        if(allapp!=null){
//            allapp.click();
//            sleepInt(5);
//            press_down(5);
//        }
        UiObject2 themes = phone.findObject(By.res("com.stv.plugin.app:id/cellview_label").text("主题"));
        check("未进入主题",themes != null);
        themes.click();
        sleepInt(2);
        press_right(2);
        addStep("进入我的主题");
        UiObject2 myThemes = waitForObj(By.res("com.stv.thememanager:id/fl_theme_self"));
        verify("没有找到我的",myThemes!= null);
        sleepInt(1);
        myThemes.click();
        sleepInt(2);
        press_left(1);
        press_center(1);
        sleepInt(2);
        press_down(2);
        addStep("立即使用");
        sleepInt(2);
        UiObject2 use1=waitForObj(By.res("com.stv.thememanager:id/btn_use").text("立即使用"));
        UiObject2 use2=waitForObj(By.res("com.stv.thememanager:id/btn_use").text("当前使用"));
        if(use1!=null){
            check("未进入立即使用",use1!=null);
            use1.click();
            press_center(1);
            sleepInt(5);
        }
        else if(use2!=null){
            check("未进入当前使用",use2!=null);
            use2.click();
            press_center(1);
            sleepInt(5);
        }
        press_back(4);
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
}
