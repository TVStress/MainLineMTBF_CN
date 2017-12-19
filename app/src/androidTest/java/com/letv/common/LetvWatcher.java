package com.letv.common;

import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;

import junit.framework.Assert;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by letv on 2016/1/2.
 * Last edit by letv on 15:02 2016/1/2.
 */
public class LetvWatcher {
    private static final String TAG = LetvWatcher.class.getSimpleName();
    private static final UiDevice phone = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    public static final UiWatcher unicomWelcomWatcher = new UiWatcher() {
        public boolean checkForCondition() {
            UiObject2 uniformWindows = phone.findObject(By.text(Pattern.compile(".*欢迎您使用中国联通.*",Pattern.DOTALL)));
            if (uniformWindows != null) {
                UiObject2 ok = phone.findObject(By.clazz(
                        "android.widget.Button").text("确定"));
                if (ok != null) {
                    ok.clickAndWait(Until.newWindow(), 2000);
                }
                return true;
            } else {
                return false;
            }
        }
    };

    public static final UiWatcher anrWatcher = new UiWatcher() {
        public boolean checkForCondition() {
            UiObject2 anrWindows = phone.findObject(By.text(Pattern.compile(".*responding.*|.*无响应.*|.*no respons.*",Pattern.DOTALL)));
            if (anrWindows != null){
                LetvTestCase.screenShot();
                try {
                    UiObject2 ok = phone.findObject(By.clazz("android.widget.Button").text(Pattern.compile("REPORT|上报并关闭|问题反馈|Report")));
                    if (ok != null) {
                        ok.clickAndWait(Until.newWindow(),2000);
                        UiObject2 ok1 = phone.findObject(By.clazz("android.widget.Button").text(Pattern.compile("REPORT|上报并关闭|问题反馈|Report")));
                        if(ok1!=null){
                            ok1.click();
                        }
                    }
                    SystemClock.sleep(500);
                }finally {
                    LetvTestCase.send_status(LetvTestCase.STATUS_ANR, "ANR",
                            "Detected ANR when running case");
                    LetvTestCase.callShell("mv /data/anr " + LetvTestCase.ROOT_DIR + File.separator
                            + "AutoSmoke_UI30" + File.separator
                            + LetvTestCase.folderName + File.separator + "anr");
                    Assert.fail("ANR occurred");
                }
                return true;
            } else {
                return false;
            }
        }
    };

    public static final UiWatcher fcWatcher = new UiWatcher() {
        public boolean checkForCondition() {
            UiObject2 fcWindows = phone.findObject(By.text(Pattern.compile(".*(停止运行|上报错误|has stopped).*", Pattern.DOTALL)));
            if (fcWindows != null) {
                LetvTestCase.screenShot();
                try {
                    UiObject2 cancel =phone.findObject(By.text(Pattern.compile("取消|.*Cancel.*|Report")));
                    if (cancel != null) {
                        cancel.clickAndWait(Until.newWindow(),2000);
                    }

                } finally {
                    LetvTestCase.send_status(LetvTestCase.STATUS_FC, "FC",
                            "Detected FC when running case");
                    Assert.fail("FC occurred, the case is stoped!");
                }
                return true;
            } else {
                return false;
            }
        }
    };

    /**允许访问图片等权限申请**/
    public static final UiWatcher allowWatcher = new UiWatcher() {
        public boolean checkForCondition() {

            UiObject2 allowWindows = phone.findObject(By.text(Pattern.compile("要允许.*吗？", Pattern.DOTALL)));
            if (allowWindows != null) {
                UiObject2 allow = phone.findObject(By.text("允许"));
                if (allow != null) {
                    allow.click();
                    allow.wait(Until.gone(By.text(Pattern.compile("要允许.*吗？", Pattern.DOTALL))), 2000);
                    return true;
                } else {

                    return false;
                }
            }
            return false;
        }
    };
}
