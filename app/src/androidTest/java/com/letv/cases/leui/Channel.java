package com.letv.cases.leui;


import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;
public class Channel extends LetvTestCase{
    int count =0;

    @After
    public void tearDown(){
//        press_vol_down(20);
    }
    @Test
    @CaseName("轮播频道稳定性测试")
    public void testChannel() throws UiObjectNotFoundException, RemoteException {

        addStep("进入轮播频道");
        gotoHomeScreen("LIVE|Live|首页");
        sleepInt(10);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
//            try {
                Channel();
//            }catch (Exception e){
//                try {
//                    count ++;
//                    failCount(count, getIntParams("Loop"), e.getMessage());
//                    addStep("进入轮播频道");
//                    gotoHomeScreen("应用");
//                    press_home(1);
//                    sleepInt(5);
//                    Channel();
//                }catch (RuntimeException re){
//                    screenShot();
//                    Assert.fail(re.getMessage());
//                }
//            }
        }
    }

    public void Channel() throws UiObjectNotFoundException, RemoteException {
        addStep("打开频道列表");
        press_center(1);
        sleepInt(2);
        addStep("播放节目60S");
        press_down(1);
        press_center(1);
        sleepInt(60);
        addStep("打开节目预览表");
        press_down(1);
        sleepInt(1);
        press_back(1);
        addStep("调整清晰度");
        press_menu(1);
        sleepInt(3);
        UiObject2 menuTag = waitForObj(By.text(Pattern.compile("菜单|收藏该节目")));
        check("菜单未打开不存在", menuTag != null);
        sleepInt(1);
        UiObject2 clarity1 = waitForObj(By.clazz("android.widget.TextView").text("清晰度"));
        check("清晰度不存在", clarity1 != null);
        for(int i=0;i<5;i++){
            UiObject2 clarityP = clarity1.getParent().getParent();
            if(!clarityP.isFocused()){
                press_down(1);
            }else break;
        }
        UiObject2 currentCla = clarity1.getParent().findObject(By.clazz("android.widget.TextSwitcher")).findObject(By.clazz("android.widget.TextView"));
        addStep("当前清晰度为" + currentCla.getText());
        sleepInt(1);
        press_center(1);
        sleepInt(5);
        UiObject2 currentCla1 = clarity1.getParent().findObject(By.clazz("android.widget.TextSwitcher")).findObject(By.clazz("android.widget.TextView"));
        addStep("切换至" + currentCla1.getText());
        //clarity.click();
        press_center(1);
        sleepInt(5);
        UiObject2 currentCla2 = clarity1.getParent().findObject(By.clazz("android.widget.TextSwitcher")).findObject(By.clazz("android.widget.TextView"));
        addStep("切换至" + currentCla2.getText());
        //clarity.click();
        press_center(1);
        sleepInt(5);
        addStep("退出菜单");
        press_menu(1);
        sleepInt(200);

        addStep("弹出时间轴");
        press_down(1);
        press_left(10);
        sleepInt(10);

        press_down(1);
        press_right(10);
        sleepInt(5);
        addStep("按下音量加和减各5次");
        press_vol_up(5);
        sleepInt(1);
        press_vol_down(5);
        sleepInt(20);
    }

    @Test
    @CaseName("轮播频道频繁切换频道")
    public void testChangChannel() throws UiObjectNotFoundException, RemoteException {
        BySelector listS = By.text("简约列表");
        gotoHomeScreen("LIVE|Live|首页");
        sleepInt(5);
        press_back(1);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            UiObject2 list = waitForObj(listS);
            verify("简约列表不存在", list != null);
            addStep("在Live界面预览框界面上下浏览");
            press_down(22);
            UiObject2 list1 = waitForObj(listS);
            verify("简约列表不存在", list1 != null);
            press_up(22);
            addStep("通过LIVE节目预览窗口更换节目10次");
            UiObject2 mine = waitForObj(By.text(Pattern.compile("我的|简约列表")));
            verify("我的不存在", mine != null);
            mine.click();
            mine.click();
            sleepInt(1);
            press_left(1);
            press_right(1);
            for (int i = 0; i < 3; i++) {
                press_down(1);
                press_center(1);
                sleepInt(10);
                UiObject2 list2 = phone.findObject(listS);
                UiObject2 sports= phone.findObject(By.pkg("com.lesports.tv"));
                if(list2 == null) {
                    if (sports != null) {
                        press_home(1);
                    } else press_back(1);
                }
                sleepInt(1);
                UiObject2 list3 = waitForObj(listS);
                verify("简约列表不存在", list3 != null);
            }
        }
    }
}


