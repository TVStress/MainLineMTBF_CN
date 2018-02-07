package com.letv.cases.leui;


import android.os.RemoteException;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;

import org.junit.Test;
public class DesktopSwitchStress extends LetvTestCase{

    @Test
    @CaseName("各个桌面切换")
    public void testDesktop() throws UiObjectNotFoundException, RemoteException{
        addStep("切换到应用桌面");
        gotoHomeScreen("应用");
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper : " + Loop);
            addStep("在桌面向左滑动15次");
            press_left(15);
            addStep("在桌面向右滑动15次");
            press_right(15);
            press_back(3);
        }
    }
}