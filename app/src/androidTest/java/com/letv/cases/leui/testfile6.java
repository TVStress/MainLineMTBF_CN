package com.letv.cases.leui;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.LetvTestCase;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * 坚其志，苦其心，劳其力，事无大小，必有所成。
 * Created by pc7 on 5/21/18.
 */

public class testfile6 extends LetvTestCase{

    @Test
    public void test() throws Exception {
        gotoHomeScreen("首页");
        addStep("切换画面比例");
        press_down(3);
        press_back(1);
        sleepInt(2);
        press_menu(1);
        sleepInt(1);
        press_down(1);
        press_center(2);
//        press_down(1);
//        UiObject2 screenRat= phone.findObject(By.res("com.letv.android.tv.letvlive:id/bookmarkSwitcher"));
////                                                      com.letv.android.tv.letvlive:id/bookmarkSwitcher
//        verify("未进入画面比例",screenRat!=null);
//        for (int i = 0; i < 5; i++) {
//            UiObject2 screenRatioP = screenRat.getParent().getParent();
//            if (!screenRatioP.isFocused()) {
//                press_down(1);
//            } else break;
//        }
//        press_center(1);
//        sleepInt(5);

    }

}
