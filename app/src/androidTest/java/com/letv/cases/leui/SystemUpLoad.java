package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import org.junit.Test;

public class SystemUpLoad extends LetvTestCase {

    @Test
    @CaseName("系统更新")
    public void testSystemUpgrade() throws RemoteException {
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
    public void testFactoryReset() throws RemoteException {
        addStep("恢复出厂设置");
        if (LetvUI(6.0)){
            addStep("UI6.0版本恢复出厂");
            gotoHomeScreen("应用");
            addStep("进入设置");
            phone.pressKeyCode(KEY_SETTING);
            press_down(6);
            UiObject2 systemsetting = waitForObj(By.res("com.stv.globalsetting:id/letv_setting_base_advance"));
            verify("系统设置没有找到", systemsetting != null);
            systemsetting.click();
            UiObject2 general = waitForObj(By.res("com.stv.globalsetting:id/advance_general"));
            verify("通用没有找到", general != null);
            general.click();
            addStep("进入恢复出厂");
            UiObject2 FactoryReset = waitForObj(By.text("恢复出厂"));
            verify("恢复出厂没有找到", FactoryReset!=null);
            FactoryReset.click();
            press_right(1);
            UiObject2 FactoryResetSetting = waitForObj(By.res("com.stv.globalsetting:id/enter_button").text("恢复出厂设置"));
            verify("恢复出厂设置没有找到", FactoryResetSetting!=null);
            FactoryResetSetting.click();
            addStep("点击恢复出厂设置");
            UiObject2 ok=waitForObj(By.res("eui.tv:id/button_horizontal_first").text("确定"));
            verify("确定按钮不存在",ok!=null);
            ok.click();
            addStep("恢复出厂完成");
        }else {
            addStep("UI6.0以下版本恢复出厂");
            gotoHomeScreen("应用");
            addStep("进入设置");
            phone.pressKeyCode(KEY_SETTING);
            UiObject2 systemsetting = waitForObj(By.res("com.stv.globalsetting:id/name_textview").text("系统设置"));
            verify("系统设置没有找到", systemsetting != null);
            systemsetting.click();
            UiObject2 general = waitForObj(By.res("com.stv.globalsetting:id/title").text("通用"));
            verify("通用没有找到", general != null);
            general.click();
            sleepInt(2);
            press_down(4);
            addStep("进入恢复出厂");
            UiObject2 FactoryReset = waitForObj(By.text("恢复出厂"));
            verify("恢复出厂没有被选中", FactoryReset.isFocused());
            press_right(2);
            UiObject2 FactoryResetSetting = waitForObj(By.res("com.stv.globalsetting:id/enter_button").text("恢复出厂设置"));
            verify("恢复出厂设置没有被选中", FactoryResetSetting.isFocused());
            FactoryResetSetting.click();
            addStep("点击恢复出厂设置");
            UiObject2 factoryReset = waitForObj(By.res("com.stv.globalsetting:id/first").text("恢复出厂"));
            verify("恢复出厂按钮不存在", factoryReset != null);
            factoryReset.click();
            addStep("恢复出厂完成");
        }
    }

    @Test
    @CaseName("TV测试跳过开机向导")
    public void testMTBFinitialize() throws RemoteException {
        addStep("跳过引导界面");
        if (LetvUI(6.0)){
            addStep("UI6.0 TV跳过开机向导");
            UiObject2 skip = waitForObj(By.res("com.stv.guider:id/btn_skip_setting").text("跳过向导"));
            verify("跳过向导不存在", skip != null);
            skip.click();
            UiObject2 ok1 = waitForObj(By.text("确定"));

            ok1.click();
            sleepInt(10);
            addStep("将首页设为开机默认桌面");
            press_right(10);
            UiObject2 deskmanage = waitForObj(By.res("com.stv.launcher:id/manager_bt"));
            verify("桌面管理不存在", deskmanage != null);
            deskmanage.click();
            UiObject2 desk=waitForObj(By.res("com.stv.launcher:id/tv_page_title").text("桌面管理"));
            verify("没有进入桌面管理",desk!=null);
            sleepInt(2);
            press_left(1);
            press_right(1);
            press_up(1);
            press_center(1);
            press_back(1);
            addStep("TV初始化完成");
        }else {
            addStep("UI6.0之下TV跳过开机向导");
            UiObject2 skip = waitForObj(By.res("com.stv.guider:id/btn_skip_setting").text("跳过向导"));
            verify("跳过向导不存在", skip != null);
            skip.click();
            UiObject2 ok1 = waitForObj(By.text("确定"));
            ok1.click();
            UiObject2 ok = waitForObj(By.text("提交"));
            verify("提交不存在", ok != null);
            ok.click();
            press_right(1);
            UiObject2 Retain = waitForObj(By.text("坚持保留"));
            verify("坚持保留不存在", Retain != null);
            Retain.click();
            sleepInt(10);
            addStep("将首页设为开机默认桌面");
            press_right(10);
            UiObject2 deskmanage = waitForObj(By.res("com.stv.launcher:id/manager_bt").text("桌面管理"));
            verify("桌面管理不存在", deskmanage != null);
            deskmanage.click();
            sleepInt(2);
            press_left(1);
            press_right(2);
            press_up(1);
            press_back(1);
            addStep("TV初始化完成");
        }
    }
}
