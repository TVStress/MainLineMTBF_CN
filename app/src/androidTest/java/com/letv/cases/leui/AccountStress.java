package com.letv.cases.leui;

import android.os.Build;
import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.PkgName;
import com.letv.common.LetvTestCase;
import junit.framework.Assert;
import org.junit.Test;
import java.util.regex.Pattern;

public class AccountStress extends LetvTestCase{
    int count = 0;

/*    @Test
    @CaseName("screen shot")
    public void testScreenshot() throws UiObjectNotFoundException, RemoteException {
        screenShot();
        sleepInt(1);
    }

    @Override
    public void tearDown() throws Exception {
        //   super.tearDown();
    }
    */

    @Test
    @CaseName("删除、登录、乐视账号")
    public void testAccountLogin() throws UiObjectNotFoundException, RemoteException {
//        SuperTV();
        launchApp(AppName.LeAccount,PkgName.LeAccount);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            addStep("删除、登录、乐视账号 Loop "+Loop);
            try {
                if(LetvUI(6.5)){
                    addStep("UI6.5");
                    AccountLogin938 ();
                }else {
                    addStep("UI5.9");
                    AccountLogin();
                }
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("进入乐视帐号");
                    launchApp(AppName.LeAccount,PkgName.LeAccount);
//                    SuperTV();
                    sleepInt(2);
                    if(LetvUI(6.0)){
                        addStep("UI6.0");
                        AccountLogin938();
                    }else {
                        addStep("UI5.9");
                        AccountLogin();
                    }
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
        sleepInt(2);
        launchApp(AppName.LeAccount, PkgName.LeAccount);
//        SuperTV();
        press_center(1);
        sleepInt(2);
        press_back(1);
    }

    public void AccountLogin() throws UiObjectNotFoundException,RemoteException{
        UiObject2 letvAccount = waitForObj(By.clazz("android.widget.TextView").textContains("帐号"));
        check("can't find letvAccount.", letvAccount!=null);
        sleepInt(1);
        delAccount();
        UiObject2 quitBtn1 = phone.findObject(By.clazz("android.widget.TextView").text("退出登录"));
        if (quitBtn1!=null) {
            addStep("已存在乐视帐号，退出登录");
            press_right(5);
            press_center(1);
            sleepInt(2);
            UiObject2 confirm = phone.findObject(By.clazz("android.widget.Button").text("退出"));
            check("can't find confirm.", confirm != null);
            confirm.click();
            sleepInt(4);
        }
        UiObject2 login = null;
        for (int a=0;a<3;a++){
            login = phone.findObject(By.text(Pattern.compile("添加帐号|帐号密码登录|.*帐号登录.*")));
            if (login!=null) {
                addStep("使用账号密码登录");
                login.click();
                sleepInt(2);
                break;
            }
//            launchApp(AppName.LeAccount,PkgName.LeAccount);
            SuperTV();
        }
        sleepInt(2);
        UiObject2 userName = phone.findObject(By.text(Pattern.compile("乐视帐号|会员帐号"))).getParent().findObject(By.clazz("android.widget.EditText"));
        check("can't find userName.", userName != null);
        userName.setText(getStrParams("USERNAME"));
        sleepInt(2);
        press_down(1);
        UiObject passwd1=phone.findObject(new UiSelector().className("android.widget.EditText").instance(1));
        UiObject2 passd1 = phone.findObject(By.text("密        码")).getParent().findObject(By.res("eui:id/edit_text"));
        check("can't find passwd.", passwd1!=null);
        sleepInt(2);
        passwd1.setText(getStrParams("PASSWORD"));
        sleepInt(1);
        UiObject2 loginNow = phone.findObject(By.clazz("android.widget.Button").text("立即登录"));
        check("can't find loginNow.", loginNow!=null);
        UiObject2 cancelBtn = phone.findObject(By.clazz("android.widget.TextView").text("取消"));
        loginNow.click();
        sleepInt(1);
        press_center(1);
        for(int i=0;i<120;i++){
            sleep(1);
            UiObject2 quitBtn = phone.findObject(By.clazz("android.widget.TextView").text("退出登录"));
            if(quitBtn!=null){
                break;
            }
        }
        if (cancelBtn!=null) {
            cancelBtn.click();
            sleepInt(4);
        }
    }

    public void delAccount() throws UiObjectNotFoundException {
        for(int j=0;j<3;j++){
            UiObject2 accountUsed = phone.findObject(By.text("选择要登录的帐号"));
            if (accountUsed != null) {
                press_right(1);
                check("there is no account loged in before", accountUsed != null);
                press_menu(1);
                sleepInt(1);
                UiObject2 delAccount = phone.findObject(By.res("com.stv.t2.account:id/menu_item_delete"));
                check("there is no account loged in before", delAccount != null);
                sleepInt(1);
                delAccount.click();
                sleepInt(1);
                UiObject2 confirm = phone.findObject(By.text("确定"));
                check("delete account note don't exists", confirm != null);
                confirm.click();
                sleepInt(1);
            }else {
                break;
            }
        }
    }

    public void AccountLogin938() throws UiObjectNotFoundException,RemoteException{
        UiObject2 letvAccount = waitForObj(By.clazz("android.widget.TextView").text(Pattern.compile("帐号.*|帐户: .*|超级电视帐号|会员帐号")));
        check("can't find letvAccount.", letvAccount!=null);
        sleepInt(1);
        for(int j=0;j<3;j++){
            UiObject2 accountUsed = waitForObj(By.text(Pattern.compile("选择要登录的帐号")));     //删除帐号登陆
            if (accountUsed != null){
                press_right(1);
                check("there is no account loged in before1", accountUsed != null);
                press_menu(1);
                sleepInt(1);
                UiObject2 delAccount = phone.findObject(By.res(Pattern.compile("eui.tv:id/list_item_1_title|com.stv.t2.account:id/menu_item_delete")).text("删除帐号"));
                check("there is no account loged in before2", delAccount != null);
                sleepInt(1);
                delAccount.click();
                sleepInt(1);
                UiObject2 confirm = phone.findObject(By.text("确定"));
                check("delete account note don't exists", confirm != null);
                confirm.click();
                sleepInt(1);
            }else {
                break;
            }
        }
        UiObject2 quitBtn1 = phone.findObject(By.clazz("android.widget.TextView").text("退出登录"));   //退出帐号登陆
        if (quitBtn1!=null) {
            addStep("已存在乐视帐号，退出登录");
            press_right(5);
            press_center(1);
            sleepInt(2);
            UiObject2 confirm = phone.findObject(By.clazz("android.widget.Button").text(Pattern.compile("确定|退出")));
            check("can't find confirm.", confirm != null);
            confirm.click();
            sleepInt(4);
        }
        UiObject2 login = null;
        for (int a=0;a<3;a++){
            login = phone.findObject(By.text(Pattern.compile("添加帐号|帐号密码登录|.*帐号登录.*")));//其他帐号登录注册
            if (login!=null) {
                addStep("使用账号密码登录");
                login.click();
                sleepInt(2);
                break;
            }
//            launchApp(AppName.LeAccount,PkgName.LeAccount);
            SuperTV();
        }
        if(Build.DEVICE.contains("U4")){
            press_back(1);
        }
        sleepInt(2);
        UiObject2 userName = phone.findObject(By.text(Pattern.compile("乐视帐号|会员帐号|超级电视帐号|帐        号"))).getParent().findObject(By.clazz("android.widget.EditText"));
        check("can't find userName.", userName != null);
        userName.setText(getStrParams("USERNAME"));
        sleepInt(2);
        press_down(1);

        UiObject passwd1=phone.findObject(new UiSelector().className("android.widget.EditText").instance(1));

        check("can't find passwd.", passwd1!=null);
        sleepInt(2);
        passwd1.setText(getStrParams("PASSWORD"));
        sleepInt(1);
        UiObject2 loginNow = phone.findObject(By.clazz("android.widget.Button").text("立即登录"));
        check("can't find loginNow.", loginNow!=null);
        sleepInt(1);
        UiObject2 cancelBtn = phone.findObject(By.clazz("android.widget.TextView").text("取消"));
        loginNow.click();
        //loginNow.click();
        sleepInt(1);
        press_center(1);
        for(int i=0;i<120;i++){
            sleep(1);
            UiObject2 quitBtn = phone.findObject(By.clazz("android.widget.TextView").text("退出登录"));
            if(quitBtn!=null){
                break;
            }
        }
        if (cancelBtn!=null) {
            cancelBtn.click();
            sleepInt(4);
        }

    }

    public void SuperTV(){
        gotoHomeScreen("应用");
        press_down(3);
        addStep("进入乐视帐号");
        UiObject2 superTV = waitForObj(By.res("com.stv.plugin.app:id/cellview_label").text("超级电视帐号"));
        check("未进入乐视帐号",superTV!=null);
        superTV.click();
        superTV.click();
        sleepInt(2);
    }
}