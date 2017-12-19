package com.letv.cases.leui;

import android.os.Build;
import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.regex.Pattern;

public class Settings extends LetvTestCase{
    int count =0;

    @Test
    @CaseName("检查图像设置")
    public void testImageSetting() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("打开设置-图像");
        phone.pressKeyCode(KEY_SETTING);
        sleepInt(2);
        UiObject2 systemSettings = phone.findObject(By.text("系统设置"));
        verify("没有找到系统设置", systemSettings != null);
            if(Build.DEVICE.contains("X4_50")){
                imageMode938();
            }else {
                imageMode928();
            }
        press_back(1);
    }

    @Test
    @CaseName("从各个桌面通过设置进入信号源")
    public void testSingalSetting() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        addStep("切换到应用桌面通过按键打开设置");
        phone.pressKeyCode(KEY_SETTING);
        sleepInt(1);
        UiObject2 singalResource = waitForObj(By.text("信号源"));
        verify("没有找到信号源", singalResource != null);
            press_up(1);
            press_down(1);
            press_center(1);
            sleepInt(1);
            press_down(3);
        List<UiObject2> singals1=phone.findObject(By.res(Pattern.compile("com.stv.globalsetting:id/right_list|com.stv.t2.globalsetting:id/scrollview"))).findObjects(By.clazz("android.widget.TextView"));
        int count=singals1.size();
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper : " + Loop);
//            try {
                SingalSetting(count);
//            }catch (Exception e){
//                try {
//                    count ++;
//                    failCount(count, getIntParams("Loop"), e.getMessage());
//                    gotoHomeScreen("应用");
//                    addStep("切换到应用桌面通过按键打开设置");
//                    phone.pressKeyCode(KEY_SETTING);
//                    sleepInt(1);
//                    UiObject2 singalResource1 = waitForObj(By.text("信号源"));
//                    check("没有找到信号源", singalResource1 != null);
//                    if(Build.MODEL.contains("Letv S40 Air")||Build.MODEL.contains("Letv X50 Air")){
//                        singalResource.click();
//                        sleepInt(1);
//                    }else {
//                        press_up(1);
//                        press_down(1);
//                        press_center(1);
//                        sleepInt(1);
//                        press_down(3);
//                    }
//                    SingalSetting(count);
//                }catch (RuntimeException re){
//                    screenShot();
//                    Assert.fail(re.getMessage());
//                }
//            }
        }
    }

    public void SingalSetting(int count) throws UiObjectNotFoundException, RemoteException {
        String[] desktops={"儿童","搜索","应用","LIVE|Live|首页","乐见","同步剧场","游戏"};
        String[] singals={"模拟电视","数字电视","视频","HDMI1","HDMI2"};
        if(Build.PRODUCT.contains("Iris")){
            singals = null;
            singals= new String[]{"HDMI1","HDMI2","HDMI3"};
        }
        for(int j=0;j<desktops.length;j++){
//            gotoHomeScreen(desktops[j]);
            for (int i = 0; i < singals.length; i++) {
                addStep("切换到" + desktops[j] + "桌面");
                gotoHomeScreen(desktops[j]);
                phone.pressKeyCode(KEY_SETTING);
                sleepInt(3);
                UiObject2 singalResource1 = waitForObj(By.text("信号源"));
                check("没有找到信号源", singalResource1 != null);
                press_up(1);
                press_down(1);
                press_center(1);
                addStep("遥控设置键->设置->信号源->" + singals[i]);
                press_up(1);
                press_down(i);
                UiObject2 singal=waitForObj(By.text(singals[i]));
                verify(singal + "信号源不存在", singal != null);
                press_center(1);
                sleepInt(3);
                press_back(2);
                UiObject2 singalPkg = waitForObj(By.pkg("com.stv.signalsourcemanager"));
                check("未进入信号源界面", singalPkg != null);
            }
        }
    }

    public void imageMode938() throws UiObjectNotFoundException {
        press_up(1);
        UiObject2 imageBtn = waitForObj(By.res("com.stv.globalsetting:id/name_textview").text(Pattern.compile("图像.*")));
        verify("图像模式不存在", imageBtn != null);
        sleepInt(1);
        imageBtn.click();
        sleepInt(1);
        press_center(1);
        press_up(2);
        UiObject2 imageModeF=waitForObj(By.res("com.stv.globalsetting:id/right_list"));
        verify("imageModeF不存在", imageModeF!=null);
        UiObject2 imageMode1 = imageModeF.getChildren().get(0);
        UiObject2 imageMode2 =waitForObj(By.text("图像模式").res("com.stv.globalsetting:id/name_textview")) ;
        verify("图像模式不存在", imageMode1.isFocused()||imageMode2.isFocused());
        if(imageMode1!=null){
            imageMode1.click();
        }else imageMode2.click();
        sleepInt(1);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            String[] list={"标准","护眼","鲜艳","运动","游戏","影院","自定义"};
            for(int j=0;j<list.length;j++){
                addStep("选择" + list[j] + "模式");
                press_down(j);
                UiObject2 mode=phone.findObject(By.text(list[j]));
                verify(list[j] + "不存在", mode != null);
                UiObject2 modeF=mode.getParent().getParent().getParent();
                verify(list[j] + "没有选中", modeF.isFocused()||mode.isFocused());
                press_center(1);
                press_back(1);

                addStep("打开模式设置");
                UiObject2 modeSet = waitForObj(By.text("模式设置"));
                verify("模式设置不存在", modeSet != null);
                press_down(1);
                press_center(1);

                addStep("进入亮度");
                press_up(1);
                UiObject2 light = waitForObj(By.text("亮度"));
                verify("亮度不存在", light!=null);
                UiObject2 lightF = light.getParent().getParent().getParent();
                verify("亮度没有选中", lightF.isFocused()||light.isFocused());
                press_center(1);
                custom1("背光");
                custom1("亮度");
                custom1("对比度");
                press_back(1);
                press_down(1);

                addStep("进入色彩");
                UiObject2 color = waitForObj(By.text("色彩"));
                verify("色彩不存在", color!=null);
                UiObject2 colorF = color.getParent().getParent().getParent();
                verify("色彩没有选中", colorF.isFocused()||color.isFocused());
                press_center(1);
                sleepInt(1);
                custom1("饱和度");
                custom2("色温");
                press_back(1);
                UiObject2 color1 = waitForObj(By.text("色彩"));
                verify("色彩不存在", color1 != null);
                UiObject2 colorF1 = color1.getParent().getParent().getParent();
                verify("色彩没有选中", colorF1.isFocused()||color1.isFocused());

                addStep("调节清晰度");
                press_down(1);
                UiObject2 definition = waitForObj(By.text("清晰度"));
                verify("清晰度不存在", definition!=null);
                UiObject2 definitionF = definition.getParent().getParent().getParent();
                verify("清晰度没有选中", definitionF.isFocused()||definition.isFocused());
                press_center(1);
                sleepInt(1);
                custom1("锐度");
                press_back(1);
                UiObject2 definition1 = waitForObj(By.text("清晰度"));
                verify("清晰度不存在", definition1 != null);
                press_down(1);

                addStep("进入运动");
                UiObject2 motion = waitForObj(By.text("运动"));
                verify("运动字样不存在", motion!=null);
                UiObject2 motionF = motion.getParent().getParent().getParent();
                verify("运动没有选中", motionF.isFocused()||motion.isFocused());
                press_center(1);
                sleepInt(1);
                custom2("运动");
                press_back(2);
                press_down(2);

                addStep("重置已设置的项");
                UiObject2 clear = waitForObj(By.text("重置"));
                if(clear==null){
                    press_center(1);
                    press_down(7);
                }
                UiObject2 clear2 = waitForObj(By.text("重置"));
                verify("重置不存在", clear2!=null);
                press_center(1);
                UiObject2 clear1 = waitForObj(By.text("重置"));
                verify("重置没有选中", clear1 != null);
                press_center(1);
                press_back(1);
                press_up(1);
                UiObject2 imageMode3 = waitForObj(By.text("图像模式"));
                verify("图像模式不存在", imageMode3 != null);
                press_center(1);
            }
        }
    }


    public void custom1(String name) {
        UiObject2 item=waitForObj(By.res("com.stv.globalsetting:id/name_textview").text(Pattern.compile(name)));
        verify(name+"不存在",item!=null);
        UiObject2 itemF=item.getParent().getParent().getParent();
        verify(name+"没有选中",itemF.isFocused()||item.isFocused());
        press_center(1);
        sleepInt(1);
        addStep("调节" + name + "减小10次增大10次");
        press_down(10);
        press_up(10);
        press_back(1);
        press_down(1);
    }
    public void custom2(String name){
        press_center(3);
        addStep("调节"+name+"冷色中间色温");
        press_down(1);
        press_center(2);
        press_down(2);
        press_center(1);
    }
    public void imageMode928() throws UiObjectNotFoundException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            UiObject2 imageBtn = waitForObj(By.res("com.stv.globalsetting:id/name_textview").text(Pattern.compile("图像.*")));
            verify("图像模式不存在", imageBtn != null);
            sleepInt(1);
            imageBtn.click();
            sleepInt(1);
            press_center(1);
            UiObject2 list=phone.findObject(By.res("com.stv.globalsetting:id/right_list"));
            int count=list.getChildCount();
            for(int j=0;j<count-1;j++){
                UiObject2 mode = list.findObject(By.clazz("android.widget.TextView"));
                addStep("选择" + mode.getText() + "模式");
                press_down(1);
                press_center(1);
            }
            addStep("打开自定义");
            String[] lightlist={"背光","亮度","对比度","清晰度","饱和度","色温"};
            for(int i=0;i<lightlist.length;i++){
                if(i==lightlist.length-1){
                    press_center(3);
                    addStep("调节"+lightlist[i]+"冷色中间色温");
                    press_down(1);
                    press_center(2);
                    press_down(2);
                    press_center(1);
                }else {
                    press_center(1);
                    addStep("调节"+lightlist[i]+"减小10次增大10次");
                    press_down(10);
                    press_up(10);
                    press_back(1);
                    press_down(1);
                }
            }
            press_back(2);
            UiObject2 imageBtn1 = phone.findObject(By.res("com.stv.globalsetting:id/name_textview").text("图像模式"));
            if(imageBtn1 ==null){
                press_back(1);
            }
        }
    }

    public void imageMode2() throws UiObjectNotFoundException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            UiObject2 imageBtn = phone.findObject(By.clazz("android.widget.TextView").text("图像"));
            press_up(1);
            press_left(4);
            verify("图像按钮不存在", imageBtn!=null);
            imageBtn.click();

            addStep("在图像模式中切换");
            //press_up(10);
            UiObject2 imgMode = phone.findObject(By.clazz("android.widget.TextView").text("图像模式"));
            if (!(imgMode!=null)) {
                press_up(10);
            }
            addStep("切换背光");
            press_down(1);
            press_down(1);
            press_left(10);
            press_right(10);
            press_back(1);

            addStep("切换亮度");
            press_down(1);
            press_left(10);
            press_right(10);
            press_back(1);

            addStep("切换对比度");
            press_down(1);
            press_left(10);
            press_right(10);
            press_back(1);

/*            addStep("切换色彩");
            press_down(1);
            press_left(10);
            press_right(10);
            press_back(1);*/

            addStep("切换清晰度");
            press_down(1);
            press_left(5);
            press_right(5);
            press_back(1);

            addStep("切换饱和度");
            press_down(1);
            press_left(10);
            press_right(10);
            press_back(1);

            addStep("切换色温");
            press_down(1);
            UiObject2 imgCT = phone.findObject(By.clazz("android.widget.TextView").text("色温"));
            verify("色温按钮不存在", imgCT != null);
            for (int i = 0; i < 3; i++) {
                press_center(1);
            }

            addStep("切换场景模式");
            press_down(1);
            UiObject2 sceneMode = phone.findObject(By.clazz("android.widget.TextView").text("场景模式"));
            verify("色温按钮不存在", sceneMode!=null);
            for (int i = 0; i < 3; i++) {
                press_center(1);
            }

/*            addStep("重置图像");
            press_down(2);
            UiObject2 resetBtn = phone.findObject(By.clazz("android.widget.TextView").text("图像重置"));
            verify("图像重置按钮不存在", resetBtn!=null);
            resetBtn.click();
            UiObject2 okBtn = phone.findObject(By.clazz("android.widget.TextView").text("确定"));
            okBtn.click();*/
            press_back(1);
        }
    }
}
