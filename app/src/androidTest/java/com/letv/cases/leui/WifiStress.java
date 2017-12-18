package com.letv.cases.leui;

import android.os.Build;
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
 * Created by zhoujine on 2016/6/12.
 */
public class WifiStress extends LetvTestCase {
    int count =0;

    @Test
    @CaseName("打开关闭Wifi")

    public void testWifiSwitch() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("打开设置-网络设置");
        if (Build.MODEL.contains("Letv X50 Air")||Build.DEVICE.contains("mstarnapoli_4k2k")
                ||Build.MODEL.contains("Letv S40 Air")||Build.DEVICE.contains("mstarnapoli") ){
            phone.pressKeyCode(KEY_SETTING);
        }
        else if(LetvUI(6.5)) {
                phone.pressKeyCode(KEY_SETTING);
                sleepInt(3);
                press_down(3);
                UiObject2 nete = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_network"));
                check("网络设置没有找到",nete != null);
                nete.click();

            }
        else {
            phone.pressKeyCode(KEY_SETTING);
            press_up(1);
            for (int i = 0; i < 6; i++) {
                UiObject2 network = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_picture"));
                UiObject2 networkP = network.getParent().getParent().getParent();
                if (!networkP.isFocused()) {
                    press_down(1);
                } else {
                    verify("网络设置没有选中", networkP.isFocused());
                    break;
                }
            }
            press_center(1);
            sleepInt(1);
            UiObject2 wifi = waitForObj(By.clazz("android.widget.RadioButton").text("无线网络"));
            verify("未找到网络设置", wifi != null);
            press_up(1);
            sleepInt(2);
        }
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                if (Build.MODEL.contains("Letv X50 Air")||Build.DEVICE.contains("mstarnapoli_4k2k")
                        ||Build.MODEL.contains("Letv S40 Air")||Build.DEVICE.contains("mstarnapoli") ){
                    WifiSwitch918();
                }else if(LetvUI(6.0)){
                    WifiSwitch938();
                }else {
                    WifiSwitch();
                }
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("应用");
                    addStep("打开设置-网络设置");
                    phone.pressKeyCode(KEY_SETTING);
                    press_up(1);
                    for(int i=0;i<5;i++){
                        UiObject2 network=waitForObj(By.text("网络设置"));
                        UiObject2 networkP=network.getParent().getParent().getParent();
                        if(!networkP.isFocused()){
                            press_down(1);
                        }else {
                            check("网络设置没有选中",networkP.isFocused());
                            break;
                        }
                    }
                    press_center(1);
                    sleepInt(1);
                    UiObject2 wifi1=waitForObj(By.clazz("android.widget.RadioButton").text("无线网络"));
                    check("未找到网络设置", wifi1 != null);
                    press_up(1);
                    sleepInt(2);
                    WifiSwitch();
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }
    public void WifiSwitch() throws UiObjectNotFoundException, RemoteException {
        addStep("打开无线");
        turnOn();
        addStep("关闭无线");
        turnOff();
    }


    public void WifiSwitch938() throws UiObjectNotFoundException, RemoteException {
        addStep("打开无线");
        turnOn();
        addStep("关闭无线");
        turnOff();
    }

    public void turnOn(){
        UiObject2 wifi1=waitForObj(By.text("已关闭"));
        if(wifi1!=null){
            press_right(2);
            press_center(1);
            sleepInt(20);
        }
        UiObject2 wifi2=waitForObj(By.text(Pattern.compile("已打开|已开启")));
        UiObject2 wifiImg=waitForObj(By.res("com.stv.globalsetting:id/wifiIcon_imageview"));
        check("无线没有打开",wifi2!=null);
        check("没有搜索到可用的无线",wifiImg!=null);
    }
    public void turnOff(){
        UiObject2 wifi1=waitForObj(By.text(Pattern.compile("已打开|已开启")));
        if(wifi1!=null){
            press_right(2);
            press_center(1);
            sleepInt(20);
        }
        UiObject2 wifi2=waitForObj(By.text("已关闭"));
        UiObject2 wifiImg=waitForObj(By.res("com.stv.globalsetting:id/wifiIcon_imageview"));
        check("无线没有关闭",wifi2!=null&&wifiImg==null);
    }


    public void WifiSwitch918() throws UiObjectNotFoundException, RemoteException {
        addStep("打开无线");
        UiObject2 net=waitForObj(By.text("网络"));
        verify("不能找到网络",net!=null);
        net.click();
        UiObject2 wifi=waitForObj(By.text("无线网络"));
        verify("不能找到无线网络",wifi!=null);
        wifi.click();
        UiObject2 close=waitForObj(By.text("关闭"));
        if (close!=null){
            press_up(2);
            press_right(1);
        }
        sleepInt(10);
        addStep("关闭无线");
        UiObject2 open=waitForObj(By.text("开启"));
        if (open!=null){
            press_up(2);
            press_right(1);
        }
        sleepInt(10);
        press_back(2);
    }



    @Test
    @CaseName("查动多屏互动")

    public void testScreenInteraction() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("打开设置-网络设置");
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                phone.pressKeyCode(KEY_SETTING);
                UiObject2 network = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_network"));
                check("没有进入设置网络",network!=null);
                network.click();
                network.click();
                press_down(3);
                UiObject2 screeninteraction = waitForObj(By.text("多屏互动"));
                check("未进入多屏互动",screeninteraction !=null);
                screeninteraction.click();
                screeninteraction.click();
                press_right(1);
                sleepInt(1);
                press_down(1);
                ScreenInteraction();
                }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("应用");
                    addStep("打开设置-网络设置");
                    ScreenInteraction();
                    }
                    catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
            press_back(4);
        }
    }
    public void ScreenInteraction() throws UiObjectNotFoundException, RemoteException {
        for (int i=0 ;i<5;i++) {
            UiObject2 openclose = waitForObj(By.res("com.stv.globalsetting:id/status_textview").text(Pattern.compile("已.*")));
            check("未打开DLNA&Airplay", openclose != null);
            addStep("打开互动");
            turnOn1();
            addStep("关闭互动");
            turnOff1();
        }
    }



    public void turnOn1(){
        UiObject2 wifi1=waitForObj(By.text("已关闭"));
        if(wifi1!=null){
            press_right(1);
            press_center(1);
            sleepInt(3);
        }
        UiObject2 wifi2=waitForObj(By.text(Pattern.compile("已打开|已开启")));
        UiObject2 wifiImg=waitForObj(By.res("com.stv.globalsetting:id/status_textview"));
        check("无线没有打开",wifi2!=null);
        check("没有搜索到可用的无线",wifiImg!=null);
    }
    public void turnOff1(){
        UiObject2 wifi1=waitForObj(By.text("已开启"));
        if(wifi1!=null){
            press_right(1);
            press_center(1);
            sleepInt(3);
        }
        UiObject2 wifi3=waitForObj(By.text("已关闭"));
        UiObject2 wifiImg=waitForObj(By.res("com.stv.globalsetting:id/status_textview"));
        check("无线没有关闭",wifi3!=null&&wifiImg!=null);
    }
}