package com.letv.cases.leui;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;


public class DesktopApplicationRedact extends LetvTestCase{

    int count;

    @Test
    @CaseName("进入应用桌面新建文件夹编辑")
    public void testApplicationRedact() throws UiObjectNotFoundException {
        for (int Loop=0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper: " + Loop);
            try {
                addStep("进入应用桌面");
                ApplicationRedact();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    ApplicationRedact();
                } catch (RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }

            }
        }
    }
    public void ApplicationRedact() throws UiObjectNotFoundException {
        gotoHomeScreen("应用");
        press_down(1);
        press_back(3);
        press_down(2);
        press_menu(1);
        addStep("进入新建文件夹");
//        UiObject2 title=waitForObj(By.res("eui.tv:id/list_item_1_title").text("新建文件夹"));
//        check("未进入新文件夹",title!=null);
//        title.click();
        press_up(5);
        press_down(1);
        press_center(1);
        addStep("应用app移动新建文件夹里");
        for(int i =0;i<8;i++) {
            press_right(1);
            press_center(1);
        }
        press_home(1);

        press_back(3);
        gotoHomeScreen("应用");
        press_down(3);

        for(int t=0;t<3;t++) {
            UiObject2 folder = waitForObj(By.res("com.stv.plugin.app:id/app_folder_item_label").text(Pattern.compile("新建文件夹.*")));
            if (folder != null) {
//                check("未进入桌面新文件夹", folder != null);
                folder.click();
                break;
            } else {
                press_down(2);
            }
        }

        addStep("新建文件夹app移动到应用桌面");
        UiObject2 workfolder1 = waitForObj(By.res("com.stv.plugin.app:id/app_folder_workspace_title").text(Pattern.compile("新建文件夹")));
//        check("no work新建文件夹",workfolder1!=null);
        press_menu(1);
//        UiObject2 modle_app = waitForObj(By.res("eui.tv:id/list_item_1_title").text("移动应用"));
////        check("未进入移动应用",modle_app!=null);
//        modle_app.click();
        press_up(3);
        press_center(1);
        press_center(1);
        for(int k=0;k <9;k++){
            UiObject2 all_app = waitForObj(By.clazz("android.widget.TextView").text("移动到全部应用"));
            if(all_app!=null) {
                press_center(2);
                press_up(2);
            }else {
                press_back(4);
                exitApp();
                break;
            }
        }
        press_back(1);
    }














    @Test
    @CaseName("进入应用桌面新建文件夹编辑")
    public void testApplicationRedact928() throws UiObjectNotFoundException {
        for (int Loop=0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper: " + Loop);
            try {
                addStep("进入应用桌面");
                ApplicationRedact928();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    ApplicationRedact928();
                } catch (RuntimeException re) {
                    screenShot();
                    Assert.fail(re.getMessage());
                }

            }
            press_home(2);
        }
    }
    public void ApplicationRedact928() throws UiObjectNotFoundException {
        gotoHomeScreen("应用");
        press_down(1);
        press_back(3);
        press_down(2);
        press_menu(1);
        press_up(5);
        press_down(1);
        addStep("进入新建文件夹");
        press_center(2);
        addStep("应用app移动新建文件夹里");
        for(int i =0;i<8;i++) {
            press_right(1);
            press_center(1);
        }
        press_home(1);

        press_back(3);
        gotoHomeScreen("应用");
        press_down(3);

        for(int t=0;t<3;t++) {
            UiObject2 folder = waitForObj(By.text(Pattern.compile("新建文件夹.*")));
            if (folder != null) {
                check("未进入桌面新文件夹", folder != null);
                folder.click();
                break;
            } else {
                press_down(2);
            }
        }

        addStep("新建文件夹app移动到应用桌面");
        UiObject2 workfolder1 = waitForObj(By.text(Pattern.compile("新建文件夹.*")));
        check("no work新建文件夹",workfolder1!=null);
        press_menu(1);
        press_up(5);
        addStep("进入新建文件夹");
        press_center(2);
        for(int k=0;k <9;k++){
            UiObject2 all_app = waitForObj(By.text("移动到全部应用"));
            if(all_app!=null) {
                press_center(2);
                press_up(2);
            }else {
                press_back(4);
                exitApp();
                press_home(1);
                break;
            }
        }
        press_back(1);
    }
}
