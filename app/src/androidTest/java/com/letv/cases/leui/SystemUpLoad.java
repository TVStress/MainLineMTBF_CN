package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import org.junit.Test;

public class SystemUpLoad extends LetvTestCase {
    int count=0;

    @Test
    @CaseName("系统更新")
    public void testSystemUpgrade() throws UiObjectNotFoundException, RemoteException {
        try {
            SystemUpgrade();
        }catch (Exception e){
            try {
                count++;
                failCount(count, getIntParams("Loop"), e.getMessage());
                SystemUpgrade();
            }catch (RuntimeException re){
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }
    }
    public void SystemUpgrade() throws UiObjectNotFoundException,RemoteException {
        addStep("进入系统更新");
        press_back(3);
        launchApp(AppName.SystemUpdate, IntentConstants.SystemUpdate);
        addStep("点击离线更新");
        UiObject2 offineUpdate = waitForObj(By.text("离线更新"));
        verify("office update button does not exist",offineUpdate!=null);
        offineUpdate.click();
        sleepInt(1);
        offineUpdate = waitForObj(By.text("离线更新"),3000);
        if(offineUpdate != null){
            clickAndWaitForNewWindow(offineUpdate);
        }
        addStep("更新 update.zip");
        UiObject2 update = waitForObj(By.textContains("更新 update.zip"));
        verify("not found update package", update != null);
        update.click();
        sleepInt(1);
        update = waitForObj(By.textContains("更新 update.zip"),3000);
        if(update != null){
            clickAndWaitForNewWindow(update);
        }
        UiObject2 rebootUpdate = null;
        for(int i=0;i<50; i++){
            rebootUpdate = phone.findObject(By.text("重启更新"));
            if (rebootUpdate != null) {
                break;
            }
            sleepInt(5);
        }
        rebootUpdate = phone.findObject(By.text("重启更新"));
        verify("reboot update does not exist", rebootUpdate != null);
        rebootUpdate.click();
        sleepInt(1);
        rebootUpdate = phone.findObject(By.text("重启更新"));
        if(rebootUpdate != null){
            press_center(1);
        }
        addStep("更新成功");
    }

    @Test
    @CaseName("恢复出厂")
    public void testFactoryReset() throws UiObjectNotFoundException, RemoteException {
        for (int i =0;i<5;i++) {
            addStep("恢复出厂设置");
            gotoHomeScreen("应用");
            addStep("进入设置");
            phone.pressKeyCode(KEY_SETTING);
            press_down(6);
            UiObject2 systemsetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_advance"));
            verify("系统设置没有找到", systemsetting != null);
            systemsetting.click();
            systemsetting.click();
            UiObject2 general = waitForObj(By.res("com.stv.globalsetting:id/advance_general"));
            verify("通用没有找到", general != null);
            general.click();
            addStep("进入恢复出厂");
            UiObject2 FactoryReset = waitForObj(By.text("恢复出厂"));
            verify("恢复出厂没有找到", FactoryReset != null);
            FactoryReset.click();
            FactoryReset.click();
            press_right(1);
            UiObject2 FactoryResetSetting = waitForObj(By.res("com.stv.globalsetting:id/enter_button").text("恢复出厂设置"));
            verify("恢复出厂设置没有找到", FactoryResetSetting != null);
            FactoryResetSetting.click();
            FactoryResetSetting.click();
            addStep("点击恢复出厂设置");
            UiObject2 ok = waitForObj(By.text("确定"));
            if (ok != null) {
                verify("确定按钮不存在", ok != null);
                ok.click();
                ok.click();
            } else {
                press_left(1);
                press_center(1);
            }
            break;
        }
    }
    public void FactoryReset() throws UiObjectNotFoundException,RemoteException {
        addStep("恢复出厂设置");
        gotoHomeScreen("应用");
        addStep("进入设置");
        phone.pressKeyCode(KEY_SETTING);
        press_down(6);
        UiObject2 systemsetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_advance"));
        verify("系统设置没有找到", systemsetting != null);
        systemsetting.click();
        systemsetting.click();
        UiObject2 general = waitForObj(By.res("com.stv.globalsetting:id/advance_general"));
        verify("通用没有找到", general != null);
        general.click();
        addStep("进入恢复出厂");
        UiObject2 FactoryReset = waitForObj(By.text("恢复出厂"));
        verify("恢复出厂没有找到", FactoryReset!=null);
        FactoryReset.click();
        FactoryReset.click();
        press_right(1);
        UiObject2 FactoryResetSetting = waitForObj(By.res("com.stv.globalsetting:id/enter_button").text("恢复出厂设置"));
        verify("恢复出厂设置没有找到", FactoryResetSetting!=null);
        FactoryResetSetting.click();
        FactoryResetSetting.click();
        addStep("点击恢复出厂设置");
        UiObject2 ok=waitForObj(By.text("确定"));
        if(ok!=null){
        verify("确定按钮不存在",ok!=null);
            ok.click();
            ok.click();
        }else {
            press_left(1);
            press_center(1);
        }
    }

    @Test
    @CaseName("TV测试跳过开机向导")
    public void testskipinitialize() throws UiObjectNotFoundException, RemoteException {
            try {
                skipinitialize();
            }catch (Exception e){
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    skipinitialize();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
            press_back(3);
            press_home(1);
    }
    public void skipinitialize() throws UiObjectNotFoundException, RemoteException {
            press_down(2);
            press_center(1);
            press_left(1);
            press_center(1);
            press_down(4);
            press_center(1);
            sleepInt(10);
            press_back(4);
//        addStep("跳过引导界面");
//            addStep("TV跳过开机向导");
//            UiObject2 skip = waitForObj(By.text("跳过向导"));
//            if(skip!=null) {
//                press_down(2);
//                verify("跳过向导不存在", skip != null);
//                skip.click();
//                sleepInt(3);
//            }else {
//                press_down(2);
//                press_center(1);
//            }
//            sleepInt(3);
//            press_left(1);
//            UiObject2 ok = waitForObj(By.text("确定"));
//            if (ok!=null) {
//                check("未进入确定ok", ok != null);
//                press_left(1);
//                ok.click();
//                sleepInt(3);
//            }else {
//                press_left(1);
//                press_center(1);
//            }
//            press_center(1);
//            press_down(3);
//            UiObject2 greenAndcontion = waitForObj(By.text("同意并继续"));
//            if (greenAndcontion!=null){
//                press_down(3);
//                check("未进入同意并继续",greenAndcontion!=null);
//                greenAndcontion.click();
//                press_center(1);
//                sleepInt(10);
//            }else {
//                press_down(3);
//                press_center(1);
//                sleepInt(10);
//            }
//            addStep("将首页设为开机默认桌面");
//            press_right(10);
//            UiObject2 deskmanage = waitForObj(By.res("com.stv.launcher:id/manager_bt"));
//            if(deskmanage != null){
//            verify("桌面管理不存在", deskmanage != null);
//            deskmanage.click();
//            }
//            UiObject2 desk=waitForObj(By.res("com.stv.launcher:id/tv_page_title").text("桌面管理"));
//            if(desk != null) {
//            verify("没有进入桌面管理", desk != null);
//            sleepInt(2);
//            press_left(1);
//            press_right(1);
//            press_up(1);
//            press_center(1);
//            addStep("TV初始化完成");
//        }
    }
}
