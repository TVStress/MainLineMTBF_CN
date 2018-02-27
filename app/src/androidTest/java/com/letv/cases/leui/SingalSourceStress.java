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

public class SingalSourceStress extends LetvTestCase {
    int count = 0;

    @Test
    @CaseName("信号源切换")

    public void testChangeSignal() throws UiObjectNotFoundException,RemoteException{
        addStep("切换到信号源桌面");
        gotoHomeScreen("信号源");
        press_back(1);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper : " + Loop);
            try {
                if(LetvUI(6.5)){
                    ChangeSignal938 ();
                }else {
                    ChangeSignal();
                }
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("切换到信号源桌面");
                    gotoHomeScreen("信号源");
                    sleepInt(3);
                    press_back(1);
                    if(LetvUI(6.0)){
                        ChangeSignal938 ();
                    }else {
                        ChangeSignal();
                    }

                } catch (RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }

    public void ChangeSignal() throws UiObjectNotFoundException {
        UiObject2 listView1=phone.findObject(By.res("com.stv.signalsourcemanager:id/signal_list_view"));
        check("各个信号源列表没有弹出",listView1!=null);
        int count=listView1.getChildCount();
        press_up(count);
        for (int j = 0; j < count; j++) {
            UiObject2 listView=phone.findObject(By.res("com.stv.signalsourcemanager:id/signal_list_view"));
            check("各个信号源列表没有弹出",listView!=null);
            UiObject2 signal=listView.getChildren().get(j);
            String signalName = signal.findObject(By.clazz("android.widget.TextView")).getText();
            addStep("信号源切换到" + signalName);
            if(!signal.isSelected())press_down(1);
            if(!signal.isSelected())press_down(1);
            check(signalName + " ", signal.isSelected());
            press_down(1);
            press_center(1);
            sleepInt(5);
            press_back(1);
            sleepInt(1);
        }
    }

    public void ChangeSignal938() throws UiObjectNotFoundException, RemoteException {
//        String[] desktops={"应用","首页","乐见"," 体育","搜索"};
        String[] singals={"HDMI 1","HDMI 2","数字电视","模拟电视","视频"};

        if(Build.PRODUCT.contains("Iris")){
            singals = null;
            singals= new String[]{"HDMI1","HDMI2","HDMI3"};
        }

//        for(int j=0;j<desktops.length;j++){

            for (int i = 0; i < singals.length; i++) {
                addStep("切换到" + singals[i] + "桌面");
//                gotoHomeScreen(desktops[j]);
                phone.pressKeyCode(KEY_SETTING);
                sleepInt(3);
                UiObject2 singalResource1 = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_input"));
                check("没有找到信号源", singalResource1 != null);
                singalResource1.click();
                addStep("遥控设置键->设置->信号源->" + singals[i]);
                press_up(1);
                press_down(i);
                press_center(1);
                sleepInt(3);
                press_back(2);
                UiObject2 view=waitForObj(By.res("com.stv.signalsourcemanager:id/port_info_content1").text(singals[i]));
//                check("没有切换到"+singals[i],view!=null);
                UiObject2 singalPkg = waitForObj(By.pkg("com.stv.signalsourcemanager"));
                check("未进入信号源界面", singalPkg != null);
            }
        }
    }
