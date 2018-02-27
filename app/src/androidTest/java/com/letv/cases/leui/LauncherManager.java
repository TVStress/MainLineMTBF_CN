package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;

import org.junit.Test;

import java.util.List;


public class LauncherManager extends LetvTestCase {
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        gotoHomeScreen("应用");
        UiObject2 deskManager=waitForObj(By.text("桌面管理"));
        verify("桌面管理没有找到", deskManager != null);
        deskManager.click();
        sleepInt(1);
        UiObject2 appDesk=waitForObj(By.res("com.stv.launcher:id/recycler_view")).findObject(By.text("应用"));
        verify("没有去掉体育桌面", appDesk != null);
        addStep("把应用桌面移动到所有桌面的最后");
        appDesk.click();
        sleepInt(1);
        press_center(1);
        UiObject2 arrow=waitForObj(By.res("com.stv.launcher:id/arrowleft"));
        verify("移动箭头不存在", arrow != null);
        press_right(6);
        press_back(2);
    }
    @Test
    @CaseName("进入桌面管理调整桌面顺序")
    public void testLauncherLocation() throws UiObjectNotFoundException, RemoteException {
        addStep("进入应用桌面");
        gotoHomeScreen("应用");
        addStep("进入桌面管理");
        UiObject2 deskManager=waitForObj(By.text("桌面管理"));
        verify("桌面管理没有找到", deskManager != null);
        deskManager.click();
        sleepInt(3);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper : " + Loop);
            for(int i=3;i<9;i++){
                List<UiObject2> sportsDesk=waitForObj(By.res("com.stv.launcher:id/recycler_view")).getChildren();
                verify("桌面顺序调整各桌面不存在", sportsDesk != null);
                UiObject2  deskTop=sportsDesk.get(3);
                verify("要移动的桌面不存在", deskTop != null);
                String name=deskTop.findObject(By.clazz("android.widget.TextView")).getText();
                deskTop.click();
                sleepInt(1);
                UiObject2  deskTop1=sportsDesk.get(3);
                verify("第三个桌面没有选中", deskTop1.isFocused());
                sleepInt(1);
                press_center(1);
                UiObject2 arrow=waitForObj(By.res("com.stv.launcher:id/arrowright"));
                verify("移动箭头不存在", arrow != null);
                press_right(5);
                addStep("调整" + name + "桌面顺序向右5个");
                List<UiObject2> sportsDesk1=waitForObj(By.res("com.stv.launcher:id/recycler_view")).getChildren();
                verify("桌面顺序调整各桌面不存在", sportsDesk != null);
                UiObject2  deskTop2=sportsDesk1.get(3);
                verify("要移动的桌面不存在", deskTop2 != null);
                String name1=deskTop2.findObject(By.clazz("android.widget.TextView")).getText();
                deskTop2.click();
                sleepInt(1);
                press_center(1);
                UiObject2 arrow1=waitForObj(By.res("com.stv.launcher:id/arrowright"));
                verify("移动箭头不存在", arrow1 != null);
                press_right(5);
                addStep("调整" + name1 + "桌面顺序向右5个");
                if(i%2==1) {
                    UiObject2 gameDesk = waitForObj(By.res("com.stv.launcher:id/recycler_view")).findObject(By.text("游戏"));
                    if(gameDesk!=null){
                        UiObject2 gameView = waitForObj(By.res("com.stv.launcher:id/viewpager")).findObject(By.text("游戏"));
                        gameView.click();
                        sleepInt(1);
                    }
                    UiObject2 gameDesk1 = waitForObj(By.res("com.stv.launcher:id/recycler_view")).findObject(By.text("游戏"));
                    verify("没有去掉游戏桌面", gameDesk1 == null);
                    addStep("去掉游戏桌面");
                }
                if(i%2==0){
                    UiObject2 gameDesk = waitForObj(By.res("com.stv.launcher:id/recycler_view")).findObject(By.text("游戏"));
                    if(gameDesk==null){
                        UiObject2 gameView = waitForObj(By.res("com.stv.launcher:id/viewpager")).findObject(By.text("游戏"));
                        gameView.click();
                        sleepInt(1);
                    }
                    UiObject2 gameDesk1 = waitForObj(By.res("com.stv.launcher:id/recycler_view")).findObject(By.text("游戏"));
                    verify("没有加上游戏桌面", gameDesk1 != null);
                    addStep("加上游戏桌面");
                }
                press_back(2);
                sleepInt(10);
                press_right(2);
                UiObject2 deskManager1=waitForObj(By.text("桌面管理"));
                verify("桌面管理没有找到", deskManager1 != null);
                deskManager1.click();
                sleepInt(1);
            }
        }
     press_back(3);
    }
}