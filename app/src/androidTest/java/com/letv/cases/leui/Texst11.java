package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/7/10.
 */

public class Texst11 extends LetvTestCase{
    int count=0;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        phone.registerWatcher("ReLogin",ReLogin);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        phone.removeWatcher("ReLogin");
    }


    @Test
    @CaseName("软件测试")
    public  void testsoft()throws UiObjectNotFoundException,RemoteException{
        UiObject2 tools=waitForObj(By.res("com.stv.plugin.app:id/app_folder_item_label").text("工具"));
        check("未进入工具",tools!=null);
        tools.click();
        tools.click();
        sleepInt(6);
        exitApp();
    }


    private  final UiWatcher ReLogin = new UiWatcher() {
        public boolean checkForCondition() {
            UiObject2 uniformWindows = phone.findObject(By.text(Pattern.compile(".*您的账号已在另一台电视设备上登录，请重新进行登录.*",Pattern.DOTALL)));
            if (uniformWindows != null) {
                UiObject2 relogin = phone.findObject(By.text("重新登录"));
                relogin.clickAndWait(Until.newWindow(), 2000);
                if (relogin!=null){
                    relogin.clickAndWait(Until.newWindow(), 2000);
                }

                sleep(1000);
                UiObject2 account=waitForObj(By.res("com.stv.t2.account:id/grid_item"));
                account.clickAndWait(Until.newWindow(), 2000);
                account.click();
                press_up(2);
                press_center(1);
                addStep("UIwatcher  登录账号");
                return true;
            } else {
                return false;
            }
        }
    };
}
