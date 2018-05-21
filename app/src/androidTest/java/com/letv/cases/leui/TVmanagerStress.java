package com.letv.cases.leui;

import android.content.Intent;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.PkgName;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import org.junit.After;
import org.junit.Test;

import java.util.Random;
import java.util.regex.Pattern;


public class TVmanagerStress extends LetvTestCase{

    @Test
    @CaseName("电视管家测试")
    public void testTVManger()throws UiObjectNotFoundException, RemoteException{

        for (int Loop = 0; Loop < getIntParams("Loop");Loop++) {

            addStep(".............looper : " + Loop);

            try {
                addStep("打开应用桌面，进入电视管家");
                launchApp(AppName.TvManager, PkgName.TvManager);

                UiObject2 OneKeyExam = waitForObj(By.res("com.stv.helper.main:id/onekey_exam_label").text(Pattern.compile("一键体检")));

                verify("OneKeyExam not found",OneKeyExam != null);
                press_back(1);

                addStep("退出电视管家");
                press_back(2);
            }catch (Exception e) {
                try {
                    addStep("打开应用桌面，进入电视管家");
                    launchApp(AppName.TvManager, PkgName.TvManager);

                    //UiObject2 OneKeyExam = waitForObj(By.res("com.stv.helper.main:id/onekey_exam_label").text(Pattern.compile("一键体检")));
                    UiObject2 OneKeyExam=waitForObj(By.clazz("").text(""));
                    verify("OneKeyExam not found",OneKeyExam != null);
                    press_back(1);

                    addStep("退出电视管家");
                    press_back(2);
                }catch (RuntimeException re) {
                    screenShot();
                    org.junit.Assert.fail(re.getMessage());
                }
            }
        }

    }
}
