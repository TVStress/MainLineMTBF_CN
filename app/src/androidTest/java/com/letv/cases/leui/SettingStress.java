package com.letv.cases.leui;

import android.os.Build;
import android.os.RemoteException;
import android.provider.Contacts;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.regex.Pattern;


public class SettingStress extends LetvTestCase{

    int count = 0;
    @Test
    @CaseName("从各个桌面进入设置")
    public void testSetting() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        phone.pressKeyCode(KEY_SETTING);
        sleepInt(1);
        UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_advance"));
        verify("没有找到系统设置", sytemSetting != null);
        sleepInt(5);
        sytemSetting.click();
        press_center(1);
        sleepInt(5);
        press_back(1);
        for (int Loop = 0; Loop <getIntParams("Loop");Loop++) {
            addStep(".............looper : " +Loop);
            try {
                toSetting();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("应用");
                    addStep("切换到应用桌面通过按键打开设置");
                    phone.pressKeyCode(KEY_SETTING);
                    sleepInt(1);
                    sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_advance"));
                    verify("没有找到系统设置",sytemSetting !=null);
                    sytemSetting.click();
                    press_center(1);
                    press_back(1);
                    toSetting();
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }

    public void toSetting() throws UiObjectNotFoundException, RemoteException {
        // String[] desktops={"信号源","搜索","应用","LIVE|Live|首页","体育","乐见","购物","儿童","游戏"};
        gotoHomeScreen("应用");

        //com.stv.launcher:id/tab_scrollview
        UiObject2 home1 = waitForObj(By.res("com.stv.launcher:id/tabspace")).getChildren().get(0);
        int homeCount = home1.getChildCount();
        verify("1111111",home1!= null);
        press_left(7);
        //     for(int j=0;j<homeCount;j++)
        //      {
        //       desktops[j] = home1.getChildren().get(j).getChildren().get(0).getText();
        //      addStep("____" +  desktops[j] + "__桌面");
        //    }

        for(int j=0;j<homeCount;j++)
        {
            //  addStep("切换到" + desktops[j] + "桌面");
            gotoHomeScreen("信号源");
            press_right(j);
            int m = GenerateRandom(3);
            int n = GenerateRandom(3);
            press_down(m);
            press_center(1);
            press_right(n);
            press_center(1);
            sleepInt(1);
            addStep("遥控设置键->设置");
            phone.pressKeyCode(KEY_SETTING);
            sleepInt(1);
            UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_advance"));
            verify("没有找到系统设置", sytemSetting != null);
            press_center(1);
            press_back(4);
        }
        press_up(2);
        addStep("遥控设置键->设置");
        phone.pressKeyCode(KEY_SETTING);
        sleepInt(1);

        UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_advance"));
        sleepInt(2);
        verify("没有找到系统设置", sytemSetting != null);
        press_back(4);
    }

    @Test
    @CaseName("设置_压力测试_显示屏幕保护")
    public void testScreenSaver() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            try {
                ScreenSaver();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("应用");
                    addStep("切换到应用桌面通过按键打开设置");
                    ScreenSaver();
                } catch (RuntimeException re) {
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        press_back(3);
        }
    }
    public void ScreenSaver()throws UiObjectNotFoundException,RemoteException{
        phone.pressKeyCode(KEY_SETTING);
        sleepInt(1);
        press_down(2);
        UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_advance"));
        verify("没有找到系统设置", sytemSetting != null);
        sytemSetting.click();
        sleepInt(1);
        press_down(1);
        UiObject2 show = waitForObj(By.res("com.stv.globalsetting:id/advance_general"));
        verify("没有找到通用", show != null);
        show.click();
        show.click();
        sleepInt(1);
//        UiObject2 on_off= waitForObj(By.res("eui.tv:id/textView").text("开关机"));
//        check("没有找到开关机",on_off !=null);
//        on_off.click();
//        on_off.click();
        press_right(2);
        press_down(3);
        addStep("进入屏幕保护");
        UiObject2 screenSaver = waitForObj(By.res("com.stv.globalsetting:id/title_textview").text("屏幕保护"));
        verify("没有找到屏幕保护", screenSaver != null);
        for (int j = 0; j < 4; j++) {
            press_center(1);
            press_up(5);
            press_center(1);
            sleepInt(2);
            for (int k = 0; k < 4; k++) {
                press_center(1);
                sleepInt(1);
                press_down(1);
                press_center(1);
                sleepInt(1);
//            sleepInt(1);
//            press_center(1);
//            addStep("屏幕保护5分钟");
//            UiObject2 fiveMin = waitForObj(By.res("eui.tv:id/dialog_list_item_text").text("5分钟（默认）"));
//            verify("没有出现5分钟屏保界面",fiveMin!=null);
//
//
//            press_center(1);
//            press_down(1);
//            addStep("屏幕保护10分钟");
//            UiObject2 tenMin = waitForObj(By.res("eui.tv:id/dialog_list_item_text").text("10分钟"));
//            verify("没有出现10分钟屏保界面", tenMin!=null);
//            press_center(1);
//
//            press_center(1);
//            press_down(2);
//            addStep("屏幕保护15分钟");
//            UiObject2 Min15 = waitForObj(By.res("eui.tv:id/dialog_list_item_text").text("15分钟"));
//            verify("没有出现15分钟屏保界面", Min15!=null);
//            press_center(1);
//
//            press_center(1);
//            press_down(3);
//            addStep("屏幕保护30分钟");
//            UiObject2 Min30 = waitForObj(By.res("eui.tv:id/dialog_list_item_text").text("30分钟"));
//            verify("没有出现30分钟屏保界面", Min30 != null);
//            press_center(1);
//
//            press_center(1);
//            press_down(4);
//            addStep("屏幕保护关闭");
//            UiObject2 close = waitForObj(By.res("eui.tv:id/dialog_list_item_text").text("关闭"));
//            verify("没有出现关闭屏保界面", close!=null);
//            press_center(1);
//
//            press_center(1);
//            press_up(4);
//            fiveMin = waitForObj(By.res("eui.tv:id/dialog_list_item_text").text("5分钟（默认）"));
//            verify("没有出现5分钟屏保界面", fiveMin != null);
//            press_center(1);
            }
        }
    }

    @Test
    @CaseName("调试设置_压力测试_声音输出")
    public void testAudioOutput() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        for(int Loop=0;Loop<getIntParams("Loop");Loop++){
            try{
                AudioOutput();
            }catch (Exception e){
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("应用");
                    addStep("切换到应用桌面通过按键打开设置");
                    AudioOutput();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }

            }
            press_back(3);
        }

    }
    public void AudioOutput() throws UiObjectNotFoundException, RemoteException{
        phone.pressKeyCode(KEY_SETTING);
        sleepInt(1);
        addStep("进入系统设置");
        for (int i = 0; i < 6; i++) {
            UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_sound_mode"));
            if (sytemSetting != null) {
                check("没有找到系统设置", sytemSetting != null);
                sytemSetting.click();
                break;
            } else {
                press_down(1);
            }
        }
        addStep("进入系统设置中声音");
            String arr[]={"audio","headset","speaker"};
            for (int i = 0; i < arr.length; i++) {
                UiObject2 sytemSound = waitForObj(By.res("com.stv.globalsetting:id/dialog_voice_output"));
                check("没有找到系统设置声音", sytemSound != null);
                sytemSound.click();
                sytemSound.click();
                addStep("进入电视声音扬声器输出");
                UiObject2 output = waitForObj(By.res("com.stv.globalsetting:id/output_"+arr[i]));
                check("没有找到电视扬声器选项", output != null);
                output.click();
                output.click();
            }

    }

    @Test
    @CaseName("调试设置_压力测试_声音模式")
    public void testSound_Mode() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        for(int Loop=0;Loop<getIntParams("Loop");Loop++){
            try{
                Sound_Mode();
            }catch (Exception e){
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("应用");
                    addStep("切换到应用桌面通过按键打开设置");
                    Sound_Mode();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }

            }
            press_back(4);
        }

    }
    public void Sound_Mode() throws UiObjectNotFoundException, RemoteException{
        phone.pressKeyCode(KEY_SETTING);
        sleepInt(1);
        addStep("进入系统设置");
        for (int i = 0; i < 6; i++) {
            UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_sound_mode"));
            if (sytemSetting != null) {
                check("没有找到系统设置", sytemSetting != null);
                sytemSetting.click();
                break;
            } else {
                press_down(1);
            }
        }

        String arr[]={"close","standard","music","movie","news","game"};

        for (int i = 0; i < arr.length; i++) {
            UiObject2 dialog_voice = waitForObj(By.res("com.stv.globalsetting:id/dialog_voice"));
            check("没有找到系统设置声音", dialog_voice != null);
            dialog_voice.click();
            dialog_voice.click();
            sleepInt(1);
            UiObject2 dialog_mode = waitForObj(By.res("com.stv.globalsetting:id/"+arr[i]));
            check("没有找到系统设置声音", dialog_mode != null);
            dialog_mode.click();
            dialog_mode.click();
            sleepInt(1);
        }

    }

    @Test
    @CaseName("设置_压力测试_声音音频_杜比夜间模式")
    public void testDolbyNight() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        for(int Loop=0;Loop<getIntParams("Loop");Loop++) {
            try {
                phone.pressKeyCode(KEY_SETTING);
                sleepInt(1);
                addStep("进入设置声音");
                for (int i = 0; i < 6; i++) {
                    UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_sound_mode"));
                    if (sytemSetting != null) {
                        check("没有找到系统设置", sytemSetting != null);
                        sytemSetting.click();
                        break;
                    } else {
                        press_down(1);
                    }
                }
                addStep("进入声音杜比夜间模式");
                for (int i = 0; i < 6; i++) {
                    UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/dialog_voice_dolby"));
                    if (sytemSetting != null) {
                        check("没有找到系统设置", sytemSetting != null);
                        sytemSetting.click();
                        press_center(1);
                        sleepInt(1);
                        break;
                    } else {
                        press_down(1);
                    }
                }
                press_back(3);
            }catch (RuntimeException re){
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }
    }

    @Test
    @CaseName(" 设置_压力测试_壁挂音效_杜比夜间模式")
    public void testWallHanging() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        for(int Loop=0;Loop<getIntParams("Loop");Loop++) {
            try {
                phone.pressKeyCode(KEY_SETTING);
                sleepInt(1);
                addStep("进入设置声音");
                for (int i = 0; i < 6; i++) {
                    UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_sound_mode"));
                    if (sytemSetting != null) {
                        check("没有找到系统设置", sytemSetting != null);
                        sytemSetting.click();
                        break;
                    } else {
                        press_down(1);
                    }
                }
                addStep("进入声音壁挂音效");
                for (int i = 0; i < 6; i++) {
                    UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/dialog_voice_wall_model"));
                    if (sytemSetting != null) {
                        check("没有找到系统设置", sytemSetting != null);
                        sytemSetting.click();
                        press_center(1);
                        sleepInt(1);
                        break;
                    } else {
                        press_down(1);
                    }
                }
                press_back(3);
            }catch (RuntimeException re){
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }
    }

    @Test
    @CaseName("设置_压力测试_声音音频_平衡")
    public void testWallHangingtestBalance() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        for(int Loop=0;Loop<getIntParams("Loop");Loop++) {
            try {
                phone.pressKeyCode(KEY_SETTING);
                sleepInt(1);
                addStep("进入设置声音");
                for (int i = 0; i < 6; i++) {
                    UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_sound_mode"));
                    if (sytemSetting != null) {
                        check("没有找到系统设置", sytemSetting != null);
                        sytemSetting.click();
                        break;
                    } else {
                        press_down(1);
                    }
                }
                addStep("进入声音壁挂音效");
                for (int i = 0; i < 6; i++) {
                    UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/dialog_voice_balance"));
                    if (sytemSetting != null) {
                        check("没有找到系统设置", sytemSetting != null);
                        sytemSetting.click();
                        press_center(1);
                        sleepInt(1);
                        break;
                    } else {
                        press_down(1);
                    }
                }
                press_right(GenerateRandom(5));
                sleepInt(1);

                press_left(GenerateRandom(5));
                sleepInt(1);
                press_right(GenerateRandom(10));
                sleepInt(1);
                press_left(GenerateRandom(10));
                sleepInt(1);

                press_right(GenerateRandom(50));
                sleepInt(1);
                press_left(GenerateRandom(50));
                sleepInt(1);

                press_right(GenerateRandom(100));
                sleepInt(1);
                press_left(GenerateRandom(100));
                sleepInt(1);

                press_left(GenerateRandom(50));
                sleepInt(1);
                press_right(GenerateRandom(60));
                sleepInt(1);

                press_left(GenerateRandom(60));
                sleepInt(1);
                press_right(GenerateRandom(80));
                sleepInt(1);

                press_back(3);
            }catch (RuntimeException re){
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }





        }
    }

    @Test
    @CaseName("设置_压力测试_系统声音_开机视频音")
    public void testBootAudio() throws UiObjectNotFoundException, RemoteException {

        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        for(int Loop=0;Loop<getIntParams("Loop");Loop++) {
            try {
                phone.pressKeyCode(KEY_SETTING);
                sleepInt(1);
                addStep("进入设置声音");
                for (int i = 0; i < 6; i++) {
                    UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_sound_mode"));
                    if (sytemSetting != null) {
                        check("没有找到系统设置", sytemSetting != null);
                        sytemSetting.click();
                        break;
                    } else {
                        press_down(1);
                    }
                }
                addStep("进入声音开机视频音");
                for (int i = 0; i < 10; i++) {
                    UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/dialog_voice_effect_boot"));
                    if (sytemSetting != null) {
                        check("没有找到系统设置", sytemSetting != null);
                        sytemSetting.click();
                        press_center(1);
                        sleepInt(1);
                        break;
                    } else {
                        press_down(8);
                    }
                }
                press_back(3);
            }catch (RuntimeException re){
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }

    }

    @Test
    @CaseName("设置_压力测试_系统声音_按键音")
    public void testPressSound() throws UiObjectNotFoundException, RemoteException {

        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        for(int Loop=0;Loop<getIntParams("Loop");Loop++) {
            try {
                phone.pressKeyCode(KEY_SETTING);
                sleepInt(1);
                addStep("进入设置声音");
                for (int i = 0; i < 6; i++) {
                    UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_sound_mode"));
                    if (sytemSetting != null) {
                        check("没有找到系统设置", sytemSetting != null);
                        sytemSetting.click();
                        break;
                    } else {
                        press_down(1);
                    }
                }
                addStep("进入声音按键音");
                for (int i = 0; i < 10; i++) {
                    UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/dialog_voice_keypad"));
                    if (sytemSetting != null) {
                        check("没有找到系统设置", sytemSetting != null);
                        sytemSetting.click();
                        press_center(1);
                        sleepInt(1);
                        break;
                    } else {
                        press_down(1);
                    }
                }
                press_back(3);
            }catch (RuntimeException re){
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }

    }

    @Test
    @CaseName("设置_压力测试_系统声音_屏保音")
    public void testScreenSaverSound() throws UiObjectNotFoundException, RemoteException {

        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        for(int Loop=0;Loop<getIntParams("Loop");Loop++) {
            try {
                phone.pressKeyCode(KEY_SETTING);
                sleepInt(1);
                addStep("进入设置声音");
                for (int i = 0; i < 6; i++) {
                    UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_sound_mode"));
                    if (sytemSetting != null) {
                        check("没有找到系统设置", sytemSetting != null);
                        sytemSetting.click();
                        break;
                    } else {
                        press_down(1);
                    }
                }
                addStep("进入声音屏保音");
                for (int i = 0; i < 10; i++) {
                    UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/dialog_voice_saver"));
                    if (sytemSetting != null) {
                        check("没有找到系统设置", sytemSetting != null);
                        sytemSetting.click();
                        press_center(1);
                        sleepInt(1);
                        break;
                    } else {
                        press_down(8);
                    }
                }
                press_back(3);
            }catch (RuntimeException re){
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }

    }

    @Test
    @CaseName("设置_压力测试_时间和语言_24小时制")
    public void testTime24r() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        for(int Loop=0;Loop<getIntParams("Loop");Loop++){
            try{
                Time24r();
            }catch (Exception e){
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("应用");
                    addStep("切换到应用桌面通过按键打开设置");
                    Time24r();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }

            }
            press_back(3);
        }
    }
    public void Time24r() throws UiObjectNotFoundException, RemoteException{
        phone.pressKeyCode(KEY_SETTING);
        addStep("进入系统设置");
        for (int i = 0; i < 6; i++) {
            UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_advance"));
            if (sytemSetting != null) {
                check("没有找到系统设置", sytemSetting != null);
                sytemSetting.click();
                break;
            } else {
                press_down(1);
            }
        }
        addStep("进入系统设置中通用");
        for (int j = 0; j < 6; j++) {
            UiObject2 systemSeting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_title"));
            UiObject2 sytemSound = waitForObj(By.res("com.stv.globalsetting:id/advance_general"));
            if (sytemSound != null || systemSeting != null) {
                check("没有找到系统设置声音", sytemSound != null);
                sytemSound.click();
                sytemSound.click();
                break;
            } else {
                press_down(1);
            }
        }
        sleepInt(1);
        addStep("进入时间和语言");
        UiObject2 output = waitForObj(By.res("eui.tv:id/textView").text("时间和语言"));
        check("没有找到输出选项", output != null);
        output.click();
        output.click();
        sleepInt(1);
        press_right(2);
        addStep("进入24小时开启与关闭");
        UiObject2 is24h = phone.findObject(By.res("com.stv.globalsetting:id/title_textview").text("24小时制"));
        check("未进入24h", is24h != null);
        sleepInt(1);
        UiObject2 status = waitForObj(By.res("com.stv.globalsetting:id/status_textview"));
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 2; i++) {
                if (status.getText().equals("已开启")) {
                    press_center(1);
                }
            }
            status.getText().equals("已关闭");
            press_center(1);
        }
    }

    @Test
    @CaseName("设置_压力测试_时间和语言_时区")
    public void testTimeZone() throws UiObjectNotFoundException, RemoteException {

        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        phone.pressKeyCode(KEY_SETTING);
        sleepInt(1);
        press_down(5);
//        UiObject2 sytemSetting = waitForObj(By.text("系统设置"));
//        verify("没有找到系统设置", sytemSetting != null);
//        sytemSetting.click();
        press_center(1);
        sleepInt(3);
//        UiObject2 general = waitForObj(By.text("通用"));
//        verify("没有找到通用界面", general != null);
//        general.click();
//        general.click();

        press_down(2);
        press_center(1);
        sleepInt(2);

        press_down(1);
        press_right(1);
        press_down(1);

        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++)
        {
            addStep("------------------Looper" +  Loop);

            addStep("打开时区");
            press_center(1);
            if(Loop % 2 == 0)
            {
                press_down(GenerateRandom(4));
            }else
            {
                press_up(GenerateRandom(4));
            }
            press_center(1);
            sleepInt(1);
            addStep("检查时间显示是否正确");
            press_back(2);

            sleepInt(2);
            UiObject2 beTime = waitForObj(By.res("com.stv.launcher:id/title_time"));
            verify("没有显示时间",beTime != null);

            sleepInt(1);
            addStep("再次进入设置");
            phone.pressKeyCode(KEY_SETTING);
            sleepInt(1);
//            sytemSetting = waitForObj(By.text("系统设置"));
//            verify("没有找到系统设置", sytemSetting != null);
//            sytemSetting.click();
            press_down(5);
            press_center(1);
            sleepInt(3);
//            general = waitForObj(By.text("通用"));
//            verify("没有找到通用界面", general != null);
//            general.click();
//            general.click();
            press_down(2);
            press_center(1);
            sleepInt(2);
            press_down(1);
            press_right(1);
            press_down(1);

        }
    }

    @Test
    @CaseName("设置_压力测试_安全模式_未知来源的应用")
    public void testUnkownApp() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        for(int Loop=0;Loop<getIntParams("Loop");Loop++){
            try{
                UnkownApp();
            }catch (Exception e){
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("应用");
                    addStep("切换到应用桌面通过按键打开设置");
                    UnkownApp();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }

            }
            press_back(3);
        }
    }
    public void UnkownApp() throws UiObjectNotFoundException, RemoteException{
        phone.pressKeyCode(KEY_SETTING);
        addStep("进入系统设置");
        for (int i = 0; i < 6; i++) {
            UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_advance"));
            if (sytemSetting != null) {
                check("没有找到系统设置", sytemSetting != null);
                sytemSetting.click();
                break;
            } else {
                press_down(1);
            }
        }
        addStep("进入系统设置中通用");
        for (int j = 0; j < 6; j++) {
            UiObject2 systemSeting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_title"));
            UiObject2 sytemSound = waitForObj(By.res("com.stv.globalsetting:id/advance_general"));
            if (sytemSound != null || systemSeting != null) {
                check("没有找到系统设置声音", sytemSound != null);
                sytemSound.click();
                sytemSound.click();
                break;
            } else {
                press_down(1);
            }
        }
        sleepInt(1);
        UiObject2 output = waitForObj(By.res("eui.tv:id/textView").text("安全"));
        check("没有找到安全", output != null);
        output.click();
        output.click();
        sleepInt(1);
        press_right(2);
        addStep("进入未知来源的应用");
        UiObject2 unknown = phone.findObject(By.text("未知来源的应用"));
        check("未进入24h", unknown != null);
        sleepInt(1);
        UiObject2 status = waitForObj(By.res("com.stv.globalsetting:id/status_textview"));
        addStep("进入未知来源的开启与关闭");
        for (int j = 0; j < 5; j++) {

            for (int i = 0; i < 2; i++) {
                if (status.getText().equals("已开启")) {
                    press_center(1);
                }
            }
            status.getText().equals("已关闭");
            press_center(1);
            sleepInt(2);
            UiObject2 ok_press = waitForObj(By.text("确定"));
            if (ok_press!=null){
                check("没有弹出待确认按钮", ok_press != null);
                press_left(1);
                press_center(1);
            }
            check("未进入未知来源的应用", unknown != null);
        }
    }

    @Test
    @CaseName("设置_压力测试_安全模式_位置信息")
    public void testLocation() throws UiObjectNotFoundException, RemoteException {
        for (int Loop=0;Loop<getIntParams("Loop");Loop++){
        addStep("进入应用桌面");
        gotoHomeScreen("应用");

            addStep("进入系统设置");
            phone.pressKeyCode(KEY_SETTING);
        /*UiObject2 systemsetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_advance").text("系统设置"));
        verify("没有找到系统设置",systemsetting!= null);
        systemsetting.click();
        press_center(1);*/
            press_up(6);
            sleepInt(1);
            press_down(6);
            sleepInt(1);
            press_center(1);//时常通过resID找不到系统设置，故用此方法执行
            addStep("进入通用设置");
        /*UiObject2 general = waitForObj(By.res("com.stv.globalsetting:id/advance_general"));
        verify("没有找到通用设置",general != null);
        general.click();
        press_center(1);*/
            press_down(2);
            sleepInt(1);
            press_center(1);
            addStep("进入安全设置");
            /*UiObject2 safesetting = waitForObj(By.res("eui.tv:id/textView").text("安全"));
            verify("没有找到安全设置",safesetting!=null);
            safesetting.click();
            safesetting.click();*/
            press_down(4);
            sleepInt(1);
            press_right(1);
            press_down(2);
            press_center(10);
        }

    }
}
