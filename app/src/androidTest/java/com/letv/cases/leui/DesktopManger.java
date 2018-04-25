package com.letv.cases.leui;


import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

public class DesktopManger extends LetvTestCase {
    int count=0;

    @Test
    @CaseName("进入桌面管理调整桌面乐见")
    public void testDeskSwitchLeVideo() throws UiObjectNotFoundException, RemoteException {
        addStep("进入管理桌面");
        gotoHomeScreen("应用");
        UiObject2 deskManager = waitForObj(By.clazz("android.widget.ImageButton").res("com.stv.launcher:id/manager_bt"));
        check("桌面管理没有找到", deskManager != null);
        deskManager.click();
        deskManager.click();
        sleepInt(1);
        try {
//              if (LetvUI(6.5)){
            DeskSwitchLeVideo();
//                }else {
//                DeskSwitch();
//                }
        } catch (Exception e) {
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                DeskSwitchLeVideo();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
            press_back(2);
        }
    }
    public void DeskSwitchLeVideo() throws UiObjectNotFoundException, RemoteException {
        for(int i =0;i<4;i++) {
            sleepInt(2);
            UiObject2 launchLeVideo = waitForObj(By.res("com.stv.launcher:id/tv_title").text("应用")).getParent();
            if(launchLeVideo.isFocused()){
                press_center(1);
                press_right(3);
                press_center(1);
                press_left(1);
                press_center(1);
                press_right(3);
                press_down(1);
                press_center(1);
                break;
            }
            else {
                press_right(1);
            }
        }
        sleepInt(2);
        for(int j=0;j<3;j++) {
            sleepInt(2);
            UiObject2 launchLeVideo = waitForObj(By.res("com.stv.launcher:id/tv_title").text("乐见")).getParent();
            if(launchLeVideo.isFocused()){
                press_center(1);
                press_up(1);
                press_right(3);
                press_center(1);
                break;
            }
            else {
                press_left(1);
            }
        }

    }


    @Test
    @CaseName("进入桌面管理调整桌面购物")
    public void testDeskSwitchShoping() throws UiObjectNotFoundException, RemoteException {
        addStep("进入管理桌面");
        gotoHomeScreen("应用");
        UiObject2 deskManager = waitForObj(By.res("com.stv.launcher:id/manager_bt"));
        check("桌面管理没有找到", deskManager != null);
        deskManager.click();
        deskManager.click();
        sleepInt(1);
        try {
//              if (LetvUI(6.5)){
            DeskSwitchShoping();
//                }else {
//                DeskSwitch();
//                }
        } catch (Exception e) {
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                DeskSwitchShoping();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
            press_back(2);
        }
    }
    public void DeskSwitchShoping() throws UiObjectNotFoundException, RemoteException {

        for(int i =0;i<4;i++) {
            sleepInt(2);
            UiObject2 launchLeVideo = waitForObj(By.res("com.stv.launcher:id/tv_title").text("应用")).getParent();
            if(launchLeVideo.isFocused()){
                press_center(1);
                press_right(3);
                press_center(1);
                press_left(1);
                press_center(1);
                press_right(3);
                press_down(1);
                press_center(1);
                break;
            }
            else {
                press_right(1);
            }
        }
        sleepInt(2);
        for(int j=0;j<3;j++) {
            sleepInt(2);
            UiObject2 launchLeVideo = waitForObj(By.res("com.stv.launcher:id/tv_title").text("购物")).getParent();
            if(launchLeVideo.isFocused()){
                press_center(1);
                press_up(1);
                press_right(3);
                press_center(1);
                break;
            }
            else {
                press_left(1);
            }
        }

    }


    @Test
    @CaseName("进入桌面管理调整桌面搜索")
    public void testDeskSwitchScarch() throws UiObjectNotFoundException, RemoteException {
        addStep("进入管理桌面");
        gotoHomeScreen("应用");
        UiObject2 deskManager = waitForObj(By.res("com.stv.launcher:id/manager_bt"));
        check("桌面管理没有找到", deskManager != null);
        deskManager.click();
        deskManager.click();
        sleepInt(1);
        try {
//              if (LetvUI(6.5)){
            DeskSwitchScarch();
//                }else {
//                DeskSwitch();
//                }
        } catch (Exception e) {
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                DeskSwitchScarch();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
            press_back(2);
        }
    }
    public void DeskSwitchScarch() throws UiObjectNotFoundException, RemoteException {

        for(int i =0;i<4;i++) {
            sleepInt(2);
            UiObject2 launchLeVideo = waitForObj(By.res("com.stv.launcher:id/tv_title").text("应用")).getParent();
            if(launchLeVideo.isFocused()){
                press_center(1);
                press_right(3);
                press_center(1);
                press_left(1);
                press_center(1);
                press_right(3);
                press_down(1);
                press_center(1);
                break;
            }
            else {
                press_right(1);
            }
        }
        sleepInt(2);
        for(int j=0;j<3;j++) {
            sleepInt(2);
            UiObject2 launchLeVideo = waitForObj(By.res("com.stv.launcher:id/tv_title").text("搜索")).getParent();
            if(launchLeVideo.isFocused()){
                press_center(1);
                press_up(1);
                press_right(3);
                press_center(1);
                break;
            }
            else {
                press_left(1);
            }
        }

    }


    @Test
    @CaseName("进入桌面管理调整桌面我的乐范")
    public void testDeskSwitchMyLefan() throws UiObjectNotFoundException, RemoteException {
        addStep("进入管理桌面");
        gotoHomeScreen("应用");
        UiObject2 deskManager = waitForObj(By.res("com.stv.launcher:id/manager_bt"));
        check("桌面管理没有找到", deskManager != null);
        deskManager.click();
        deskManager.click();
        sleepInt(1);
        try {
//              if (LetvUI(6.5)){
            DeskSwitchScarchMyLefan();
//                }else {
//                DeskSwitch();
//                }
        } catch (Exception e) {
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                DeskSwitchScarchMyLefan();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
            press_back(2);
        }
    }
    public void DeskSwitchScarchMyLefan() throws UiObjectNotFoundException, RemoteException {
        for(int i =0;i<4;i++) {
            sleepInt(2);
            UiObject2 launchLeVideo = waitForObj(By.res("com.stv.launcher:id/tv_title").text("应用")).getParent();
            if(launchLeVideo.isFocused()){
                press_center(1);
                press_right(3);
                press_center(1);
                press_left(1);
                press_center(1);
                press_right(3);
                press_down(1);
                press_center(1);
                break;
            }
            else {
                press_right(1);
            }
        }
        sleepInt(2);
        for(int j=0;j<3;j++) {
            sleepInt(2);
            UiObject2 launchL1 = waitForObj(By.res("com.stv.launcher:id/tv_title").text("我的乐范")).getParent();
            if(launchL1.isFocused()){
                press_center(1);
                press_up(1);
                press_right(3);
                press_center(1);
                break;
            }
            else {
                press_left(1);
            }
        }

    }

    @Test
    @CaseName("进入桌面管理调整桌面")
    public void testDeskmanger() throws UiObjectNotFoundException, RemoteException {
        addStep("进入管理桌面");
        gotoHomeScreen("应用");
        UiObject2 deskManager = waitForObj(By.clazz("android.widget.ImageButton").res("com.stv.launcher:id/manager_bt"));
        check("桌面管理没有找到", deskManager != null);
        deskManager.click();
        deskManager.click();
        sleepInt(1);
        try {
//              if (LetvUI(6.5)){
            DeskSwitchmanger();
//                }else {
//                DeskSwitch();
//                }
        } catch (Exception e) {
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                DeskSwitchmanger();
            } catch (RuntimeException re) {
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
            press_back(2);
        }
    }
    public void DeskSwitchmanger() throws UiObjectNotFoundException, RemoteException {

        String arr[] = {"我的乐范","购物", "搜索"};
        for(int u= 0; u< arr.length;u++) {
                for (int i = 0; i < 4; i++) {
                    sleepInt(2);
                    UiObject2 launchLeVideo = waitForObj(By.res("com.stv.launcher:id/tv_title").text("应用")).getParent();
                    if (launchLeVideo.isFocused()) {
                        press_center(1);
                        press_right(3);
                        press_center(1);
                        press_left(1);
                        press_center(1);
                        press_right(3);
                        press_down(1);
                        press_center(1);
                        break;
                    } else {
                        press_right(1);
                    }
                }
            sleepInt(2);
            for (int j = 0; j < 3; j++) {
                sleepInt(2);
                UiObject2 launchLeVideo = waitForObj(By.res("com.stv.launcher:id/tv_title").text(arr[u])).getParent();
                if (launchLeVideo.isFocused()) {
                    press_center(1);
                    press_up(1);
                    press_right(3);
                    press_center(1);
                    break;
                } else {
                    press_left(1);
                }
            }


        }

    }


    @Test
    @CaseName("进入桌面管理调整桌面顺序")
    public void testDeskSwitch648() throws UiObjectNotFoundException, RemoteException {
        addStep("进入管理桌面");
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            gotoHomeScreen("应用");
            UiObject2 deskManager=null;
            UiObject2 deskManager938=null;
            deskManager=phone.findObject(By.text("桌面管理"));
            deskManager938=phone.findObject(By.res("com.stv.launcher:id/manager_bt"));
            if (deskManager!=null){
                deskManager.click();
            }else {
                deskManager938.click();
            }
            verify("桌面管理没有找到", deskManager != null||deskManager938!=null);
            sleepInt(1);
            System.out.println(".............looper : " + Loop);
            try {
                if (LetvUI(6.5)){
                    DeskSwitch648();
//                }else {
//                    DeskSwitch();
                }
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("进入管理桌面");
                    gotoHomeScreen("应用");
                    deskManager=phone.findObject(By.text("桌面管理"));
                    deskManager938=phone.findObject(By.res("com.stv.launcher:id/manager_bt"));
                    if (deskManager!=null){
                        deskManager.click();
                    }else {
                        deskManager938.click();
                    }
                    verify("桌面管理没有找到", deskManager != null||deskManager938!=null);
                    deskManager.click();
                    sleepInt(1);
                    if (LetvUI(6.5)){
                        DeskSwitch648();
                    }else {
                        DeskSwitch();}
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
        press_home(1);
    }

    public void DeskSwitch() throws UiObjectNotFoundException, RemoteException {
        addStep("调整开机默认桌面");
        UiObject2 home=waitForObj(By.res("com.stv.launcher:id/recycler_view"));
        int homeCount=home.getChildCount();
        UiObject2 live1=waitForObj(By.res("com.stv.launcher:id/tv_title").text(Pattern.compile("Live|LIVE|首页")));
        verify("live not exists",live1!=null);
        live1.click();
        sleepInt(1);
        UiObject2 live2=waitForObj(By.res("com.stv.launcher:id/tv_title").text(Pattern.compile("Live|LIVE|首页")));
        verify("live not selected",live2.isFocused());
        press_up(1);
        sleepInt(1);

        UiObject2 home2=waitForObj(By.res("com.stv.launcher:id/recycler_view"));
        UiObject2 live=home2.getChildren().get(1);
        check("没有找到", live != null);
        live.click();
        sleepInt(1);
        addStep("调整桌面顺序");
        UiObject2 home1=waitForObj(By.res("com.stv.launcher:id/recycler_view"));
        for(int i=2;i<homeCount;i++){
            UiObject2 desktops=home1.getChildren().get(i);
            check("没有找到",desktops!=null);
            desktops.click();
            sleepInt(1);
            press_center(1);
            press_right(3);
            press_left(3);
            press_back(1);
        }
    }

    public void DeskSwitch648() throws UiObjectNotFoundException, RemoteException {
        UiObject2 bigc =waitForObj(By.res("com.stv.launcher:id/rlv_to_add")).findObject(By.res("com.stv.launcher:id/tv_title").text("小C精选"));
        if(bigc!=null) {
            press_down(3);
            bigc.click();
            press_center(1);
            press_up(1);
            press_right(2);
            press_back(1);
            press_left(6);
        }
        addStep("调整开机默认桌面");//ui6.0目前不支持click
        UiObject2 singalSource=waitForObj(By.text(Pattern.compile("信号源"))).getParent();
        for (int a=0;a<10;a++){
            if (singalSource.isEnabled()){
                break;
            }
            press_left(1);
        }
        UiObject2 home=waitForObj(By.res("com.stv.launcher:id/rlv_in_use"));
        check("没有进入桌面管理",home!=null);
        int homeCount=home.getChildCount();
        press_right(1);
        UiObject2 live2=waitForObj(By.text(Pattern.compile("Live|LIVE|首页"))).getParent();
        verify("live not focused",live2.isFocused());
        UiObject2 sethome=phone.findObject(By.res("设为主桌面"));
        if (sethome!=null){
            press_up(1);
            press_center(1);
            press_down(1);
        }
        check("没有将首页设置为主桌面",sethome==null);
        sleepInt(1);
        addStep("调整桌面顺序");
        for(int i=0;i<3;i++){
            press_right(1);
            press_center(1);
            press_right(2);
            press_left(2);
            press_back(1);
        }
        press_left(9);

        DeskSwitchmanger();

  }

}



