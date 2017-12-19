package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;

import junit.framework.Assert;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Created by pc7 on 9/22/17.
 */

public class Text12 extends LetvTestCase{
    int count=0;
    @Test
    @CaseName("应用测试")
        public void testScreenRatio() throws UiObjectNotFoundException, RemoteException {
//            addStep("进入Live");
            gotoHomeScreen("首页");
            sleepInt(5);
                press_menu(1);
                press_down(1);
                sleepInt(2);
                UiObject2 menu = waitForObj(By.text(Pattern.compile("菜单")));
                verify("没有菜单或者收藏该节目按钮", menu != null);
                press_down(1);

                UiObject2 screenRatio = waitForObj(By.clazz("android.widget.TextView").text("画面比例"));
                verify("没有找到画面比例", screenRatio != null);
                press_right(1);

                for (int i = 0; i < 5; i++) {
                    UiObject2 screenRatioP = screenRatio.getParent().getParent();
                    if (!screenRatioP.isFocused()) {
                        press_down(1);
                    } else break;
                }

                UiObject2 currentRat = screenRatio.getParent().findObject(By.clazz("android.widget.TextSwitcher")).findObject(By.clazz("android.widget.TextView"));
                addStep("当前的画面比例为" + currentRat.getText());
                sleepInt(1);
                press_right(1);
                sleepInt(10);

                currentRat = screenRatio.getParent().findObject(By.clazz("android.widget.TextSwitcher")).findObject(By.clazz("android.widget.TextView"));
                addStep("切换为" + currentRat.getText());

                press_right(1);
                sleepInt(10);

                addStep("退出菜单");
                press_menu(1);
                sleepInt(20);
//            }
        }

//            UiObject2 menu = waitForObj(By.text(Pattern.compile("菜单|.*收藏该节目.*")));
//            verify("没有菜单或者收藏该节目按钮", menu != null);


//            UiObject2 screenRatio = waitForObj(By.clazz("android.widget.TextView").text("画面比例"));
//            verify("没有找到画面比例", screenRatio != null);
//            screenRatio.click();
//            screenRatio.click();


//            for (int i = 0; i < 5; i++) {
//                UiObject2 screenRatioP = screenRatio.getParent().getParent();
//                if (!screenRatioP.isFocused()) {
//                    press_down(1);
//                } else break;
//            }
//
//            UiObject2 currentRat = screenRatio.getParent().findObject(By.clazz("android.widget.TextSwitcher")).findObject(By.clazz("android.widget.TextView"));
//            addStep("当前的画面比例为" + currentRat.getText());
//            sleepInt(1);
//            press_center(1);
//            sleepInt(10);
//
//            currentRat = screenRatio.getParent().findObject(By.clazz("android.widget.TextSwitcher")).findObject(By.clazz("android.widget.TextView"));
//            addStep("切换为" + currentRat.getText());
//
//            press_center(1);
//            sleepInt(10);
//
//            addStep("退出菜单");
//            press_menu(1);
//            sleepInt(20);

    }

