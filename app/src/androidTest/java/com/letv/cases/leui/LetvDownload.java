package com.letv.cases.leui;



import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;

import junit.framework.Assert;

import org.junit.Test;

import java.util.regex.Pattern;

public class LetvDownload extends LetvTestCase {
    int count =0;
    @Test
    @CaseName("乐视视频里下载视频")
    public void testLetvDownload() throws UiObjectNotFoundException, RemoteException {
        gotoHomeScreen("应用");
        launchApp(AppName.LeTv, IntentConstants.LeTv);
        sleepInt(5);
        loginAccount();
        UiObject2 skip =phone.findObject(By.text("跳过"));
        if(skip !=null){
            skip.click();
            skip.click();
            sleepInt(1);
        }
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            try {
                LetvDownload(Loop);
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("应用");
                    launchApp(AppName.LeTv, IntentConstants.LeTv);
                    sleepInt(5);
                    loginAccount();
                    UiObject2 skip1 =phone.findObject(By.text("跳过"));
                    if(skip1 !=null){
                        skip1.click();
                        skip1.click();
                        sleepInt(1);
                    }
                    LetvDownload(Loop);
                }catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());
                }
            }
        }
    }

    public void LetvDownload(int Loop) throws UiObjectNotFoundException, RemoteException {
        addStep("打开搜索，搜索武媚娘传奇");
        sleepInt(3);
        UiObject2 search =waitForObj(By.res("com.letv.tv:id/global_navi_btn_search"));
        check("搜索按钮不存在", search != null);
        search.click();
        clickAndWaitForNewWindow(search);
        UiObject2 know =phone.findObject(By.text("知道了"));
        if(know !=null){
            press_center(1);
        }
        UiObject2 W = phone.findObject(By.clazz("android.widget.TextView").text("W"));
        UiObject2 M = phone.findObject(By.clazz("android.widget.TextView").text("M"));
        UiObject2 N = phone.findObject(By.clazz("android.widget.TextView").text("N"));
        UiObject2 C = phone.findObject(By.clazz("android.widget.TextView").text("C"));
        UiObject2 Q = phone.findObject(By.clazz("android.widget.TextView").text("Q"));
        W.click();W.click();M.click();M.click();N.click();N.click();C.click();C.click();Q.click();Q.click();
        sleepInt(3);
        press_right(6);
        UiObject2 movie =waitForObj(By.text(Pattern.compile("武媚娘传奇.*")));
        check("武媚娘传奇不存在", movie !=null);
        press_center(1);
        UiObject2 movie1 =waitForObj(By.text(Pattern.compile("武媚娘传奇.*")));
        if(movie1!=null){
            movie1.click();
            sleepInt(1);
        }
        UiObject2 tab =waitForObj(By.text("详情"));
        check("没有找到详情", tab != null);
        tab.click();
        addStep("下载武媚娘传奇");
        UiObject2 download =waitForObj(By.text("下载"));
        check("下载按钮不存在", download != null);
        download.click();
        clickAndWaitForNewWindow(download);
        press_down(1);
        press_right(Loop);
        press_center(1);
        UiObject2 select=waitForObj(By.text("选择下载清晰度"));
        check("清晰度选择不存在", select != null);
        press_center(1);
        UiObject2 play1=waitForObj(By.text(Pattern.compile("1-.*")));
        check("选择集数不存在", play1 != null);
        press_back(4);
        UiObject2 search1 =waitForObj(By.res("com.letv.tv:id/global_navi_btn_search"));
        if(search1==null)press_back(1);
    }

    public void loginAccount(){
        UiObject2 loginNow = phone.findObject(By.text("立即登录"));
        if(loginNow!=null) {
            addStep("登录会员");
            loginNow.click();
            sleepInt(2);
            UiObject2 login = phone.findObject(By.text(Pattern.compile(".*添加帐号.*|.*帐号密码登录.*|.*其他帐号登录.*")));
            if (login!=null) {
                login.click();
                sleepInt(2);
            }
            sleepInt(2);
            UiObject2 userName = phone.findObject(By.text(Pattern.compile("乐视帐号|会员帐号"))).getParent().findObject(By.clazz("android.widget.EditText"));
            verify("can't find userName.", userName != null);
            userName.setText(getStrParams("USERNAME"));
            sleepInt(2);
            press_down(1);
            UiObject2 passwd = phone.findObject(By.text(Pattern.compile("密  .*码"))).getParent().findObject(By.clazz("android.widget.EditText"));
            verify("can't find passwd.", passwd != null);
            passwd.setText(getStrParams("PASSWORD"));
            sleepInt(1);
            UiObject2 loginNow1 = phone.findObject(By.clazz("android.widget.Button").text("立即登录"));
            verify("can't find loginNow.", loginNow1!=null);
            loginNow1.click();
            loginNow1.click();
            sleepInt(1);
        }
    }
}
