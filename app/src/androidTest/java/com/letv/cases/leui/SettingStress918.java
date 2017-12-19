package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;

import org.junit.Assert;
import org.junit.Test;


public class SettingStress918 extends LetvTestCase{

    int count = 0;
    @Test
    @CaseName("从各个桌面进入设置")
    public void testSetting() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        phone.pressKeyCode(KEY_SETTING);

        sleepInt(1);
         UiObject2 sytemSetting = waitForObj(By.text("系统"));
        /*UiObject2 sytemSetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_advance"));
        verify("没有找到系统设置", sytemSetting != null);*/
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
                    sytemSetting = waitForObj(By.text("系统"));
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
            UiObject2 sytemSetting = waitForObj(By.text("系统"));
            verify("没有找到系统设置", sytemSetting != null);
            sytemSetting.click();
            press_center(1);
            press_back(4);
        }
        press_up(2);
        addStep("遥控设置键->设置");
        phone.pressKeyCode(KEY_SETTING);
        sleepInt(1);
//        press_center(1);
//        press_back(1);
        UiObject2 sytemSetting = waitForObj(By.text("系统"));
        sleepInt(2);
        verify("没有找到系统设置", sytemSetting != null);
        press_back(4);
    }

    @Test
    @CaseName("设置_压力测试_显示屏幕保护")
    public void testScreenSaver() throws UiObjectNotFoundException, RemoteException {

        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        phone.pressKeyCode(KEY_SETTING);
        sleepInt(1);
        press_left(10);
        //UiObject2 sytemSetting =waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_advance").text("系统设置"));
//        UiObject2 sytemSetting = waitForObj(By.text("系统设置"));
        //verify("没有找到系统设置", sytemSetting != null);
        //sytemSetting.click();
        sleepInt(1);
        press_down(1);
        press_right(2);
        press_center(1);

        sleepInt(2);

        press_down(4);
//        UiObject2 show = waitForObj(By.res("com.stv.globalsetting:id/advance_general").text("通用"));
//        verify("没有找到通用",show !=null);
//        show.click();
//        show.click();
        press_center(1);
        sleepInt(2);

        UiObject2 screenSaver = waitForObj(By.res("com.stv.globalsetting:id/title_textview").text("屏幕保护"));
        verify("没有找到屏幕保护", screenSaver!= null);

        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++)
        {
            sleepInt(1);
            press_center(1);
            UiObject2 fourMin = waitForObj(By.text("4分钟"));
            verify("没有出现4分钟屏保界面",fourMin!=null);

            press_center(1);
            UiObject2 eightMin = waitForObj(By.text("8分钟"));
            verify("没有出现8分钟屏保界面", eightMin!=null);

            press_center(1);
            UiObject2 Min12 = waitForObj(By.text("12分钟"));
            verify("没有出现12分钟屏保界面", Min12!=null);

            press_center(1);
            UiObject2 close = waitForObj(By.text("关闭"));
            verify("没有出现关闭屏保界面", close!=null);

            press_center(1);
            fourMin = waitForObj(By.res("eui.tv:id/dialog_list_item_text").text("5分钟（默认）"));
            verify("没有出现5分钟屏保界面", fourMin != null);

            press_back(1);
            press_left(10);
        }

    }

    @Test
    @CaseName("调试设置_压力测试_声音输出")
    public void testAudioOutput() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        phone.pressKeyCode(KEY_SETTING);
        sleepInt(1);
        //UiObject2 sytemSetting =waitForObj(By.text("系统设置"));
        //verify("没有找到系统设置",sytemSetting!= null);
        // sytemSetting.click();
        press_left(10);
        press_right(1);

        press_center(1);
//        UiObject2 sound = waitForObj(By.text("声音模式"));
//        verify("没有找到声音界面",sound != null);
//        sound.click();
//        sound.click();
        sleepInt(2);
        //进入声音模式
        press_center(1);

        for(int Loop = 0; Loop <getIntParams("Loop");Loop++)
        {
            addStep(".....................Loop="+Loop);
            //循环进入电视扬声器
            press_center(1);
            //选择电视扬声器
            // UiObject2 speakerTV = waitForObj(By.res("eui.tv:id/dialog_list_item_text").text("电视扬声器"));
            UiObject2 speakerTV = waitForObj(By.text("电视扬声器"));

            //判断是否为选中状态
            //verify("没有选中电视扬声器",speakerTV.isChecked());
           verify("没有选中电视扬声器",speakerTV!=null);

            sleepInt(5);

            //再次进入弹窗界面
            press_center(1);
            // UiObject2 sound1 = waitForObj(By.clazz("android.widget.RadioButton").text("外接音响"));
            UiObject2 sound1 = waitForObj(By.res("eui.tv:id/dialog_list_item_text").text("外接音响"));
            // verify("没有选中外接音响", sound1.isChecked());
            verify("没有选中外接音响", sound1!=null);

            sleepInt(5);
            press_back(1);

        }
    }

    @Test
    @CaseName("设置_压力测试_声音音频_杜比夜间模式")
    public void testDolbyNight() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        phone.pressKeyCode(KEY_SETTING);
        sleepInt(1);
        //UiObject2 sytemSetting =waitForObj(By.text("系统设置"));
        //verify("没有找到系统设置",sytemSetting!= null);
        // sytemSetting.click();
        press_left(10);
        press_right(1);

        press_center(1);
//        UiObject2 sound = waitForObj(By.text("声音模式"));
//        verify("没有找到声音界面",sound != null);
//        sound.click();
//        sound.click();
        sleepInt(2);
        //进入声音模式
        press_center(1);
        for (int Loop =0;Loop<getIntParams("Loop");Loop++){
            press_down(3);
            press_center(1);
            sleepInt(1);
            press_center(1);
            sleepInt(1);
            press_back(1);
            press_center(1);
        }
    }



    @Test
    @CaseName("设置_压力测试_声音音频_平衡")
    public void testBalance() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        phone.pressKeyCode(KEY_SETTING);
        sleepInt(1);
        //UiObject2 sytemSetting =waitForObj(By.text("系统设置"));
        //verify("没有找到系统设置",sytemSetting!= null);
        // sytemSetting.click();
        press_left(10);
        press_right(1);

        press_center(1);
//        UiObject2 sound = waitForObj(By.text("声音模式"));
//        verify("没有找到声音界面",sound != null);
//        sound.click();
//        sound.click();
        sleepInt(2);
        //进入声音模式
        press_center(1);

        for(int Loop = 0;Loop<getIntParams("Loop");Loop++)
        {
            UiObject2 speakTV =waitForObj(By.text("电视扬声器"));
            if (speakTV ==null){
                press_center(1);
            }else {
                break;
            }
            press_down(2);
            press_right(GenerateRandom(5));
            sleepInt(1);
//            press_up(GenerateRandom(5));
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
            sleepInt(2);

            press_left(GenerateRandom(60));
            sleepInt(1);
            press_right(GenerateRandom(80));
            sleepInt(2);

            press_left(GenerateRandom(100));
            sleepInt(1);
            press_right(GenerateRandom(100));
            sleepInt(2);
        }
    }

    @Test
    @CaseName("设置_压力测试_系统声音_开机视频音")
    public void testBootAudio() throws UiObjectNotFoundException, RemoteException {

        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        phone.pressKeyCode(KEY_SETTING);
        sleepInt(1);
        //UiObject2 sytemSetting =waitForObj(By.text("系统设置"));
        //verify("没有找到系统设置",sytemSetting!= null);
        // sytemSetting.click();
        press_left(10);
        press_right(1);

        press_center(1);
//        UiObject2 sound = waitForObj(By.text("声音模式"));
//        verify("没有找到声音界面",sound != null);
//        sound.click();
//        sound.click();
        sleepInt(2);
        //进入声音模式
        UiObject2 speakTV =waitForObj(By.text("电视扬声器"));
        if (speakTV == null){
            press_center(1);
        }else {

        }

        press_center(1);
        press_down(3);

        addStep("切换开机视频音");
        for(int Loop = 0;Loop<getIntParams("Loop");Loop++)
        {
            sleepInt(1);
            press_center(1);
            sleepInt(1);
            press_center(1);
            sleepInt(1);
            press_center(1);
            sleepInt(1);
            press_center(1);
            sleepInt(1);
            press_center(1);
            sleepInt(1);
            press_center(1);
            sleepInt(1);
            press_center(1);
            sleepInt(1);
            press_center(1);
        }

    }



    @Test
    @CaseName("设置_压力测试_时间和语言_24小时制")
    public void testTime24r() throws UiObjectNotFoundException, RemoteException {
        for(int Loop=0;Loop<getIntParams("Loop");Loop++){
            gotoHomeScreen("应用");
            addStep("切换到应用桌面通过按键打开设置");
            phone.pressKeyCode(KEY_SETTING);
            sleepInt(1);
            //UiObject2 sytemSetting =waitForObj(By.text("系统设置"));
            //verify("没有找到系统设置",sytemSetting!= null);
            // sytemSetting.click();
            press_left(10);
            sleepInt(1);
            press_down(1);
            press_right(2);
            press_center(1);
            sleepInt(1);
            press_down(3);
            press_center(1);

            for(int a=0;a<5;a++){
                press_center(1);
                sleepInt(1);
                press_center(1);
                sleepInt(1);
            }
        }

        /*gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        phone.pressKeyCode(KEY_SETTING);
        sleepInt(1);
        press_down(5);
//        UiObject2 sytemSetting = waitForObj(By.text("系统设置"));
//        verify("没有找到系统设置", sytemSetting != null);
//        sytemSetting.click();
        //进入通用
        press_center(1);
//        UiObject2 general = waitForObj(By.text("通用"));
//        verify("没有找到通用界面", general != null);
//        general.click();
//        general.click();
        press_down(2);
        press_center(1);
        sleepInt(1);

        press_down(1);
        press_right(1);

        boolean is24hr =false;

        UiObject2 status =waitForObj(By.res("com.stv.globalsetting:id/status_textview"));
        // String statusStr =  status.getText();
        if(status.getText().equals("已开启")) press_center(1);

        for (int Loop = 0; Loop < getIntParams("Loop");Loop++)
        {
            addStep("------------------Looper" +  Loop);
            if (Loop % 2 == 0)
            {
                addStep("打开24小时制");
                press_center(1);
                addStep("检查24小时制显示是否正确");
                press_back(2);

                sleepInt(2);
                UiObject2 beTime = waitForObj(By.res("com.stv.launcher:id/title_time"));
                verify("没有显示时间",beTime != null);
                UiObject2 be12hr = waitForObj(By.res("com.stv.launcher:id/ampm_time"));
                if(be12hr != null)
                {
                    throw new RuntimeException("设置为24小时制，不应显示12小时时间");
                }

            }else
            {
                addStep("关闭24小时制");
                press_center(1);
                addStep("检查关闭24小时制显示是否正确");
                press_back(2);
                sleepInt(2);
                UiObject2 beTime = waitForObj(By.res("com.stv.launcher:id/title_time"));
                verify("没有显示时间",beTime != null);
                UiObject2 be12hr = waitForObj(By.res("com.stv.launcher:id/ampm_time"));
                if(be12hr == null)
                {
                    throw new RuntimeException("设置为12小时制，不应显示24小时时间");
                }
            }
            addStep("再次进入设置");
            phone.pressKeyCode(KEY_SETTING);
            sleepInt(1);
            press_down(5);
//             sytemSetting = waitForObj(By.text("系统设置"));
//            verify("没有找到系统设置", sytemSetting != null);
//            sytemSetting.click();

            press_center(1);
//            general = waitForObj(By.text("通用"));
//            verify("没有找到通用界面", general != null);
//            general.click();
//            general.click();
            press_down(2);
            press_center(1);
            press_down(1);
            press_right(1);

        }*/

    }





}
