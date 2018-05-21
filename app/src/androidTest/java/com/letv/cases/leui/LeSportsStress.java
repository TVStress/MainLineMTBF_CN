package com.letv.cases.leui;

import android.os.Build;
import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.PkgName;
import com.letv.common.LetvTestCase;

import junit.framework.Assert;

import org.junit.Test;

import java.util.regex.Pattern;

public class LeSportsStress extends LetvTestCase {

    int count = 0;
//    @Override
//    public void setUp() throws Exception {
//        super.setUp();
//        phone.registerWatcher("nexTime", nexTime);
//    }

//    @Override
//    public void tearDown() throws Exception {
//        super.tearDown();
//        phone.removeWatcher("nexTime");
//    }





//    private final UiWatcher nexTime = new UiWatcher() {
//        public boolean checkForCondition() {
//            UiObject2 nextTime = phone.findObject(By.text(Pattern.compile("下次再说|暂不领取|关闭弹窗")));
//            if (nextTime != null) {
//                nextTime.click();
//                nextTime.click();
//                sleepInt(5);
//                return true;
//            } else {
//                return false;
//            }
//        }
//    };

    public String arrSportdeskPlay[] = {"NBA","CBA","自行车","湖人"};

    @Test
    @CaseName("进入桌面体育反复切换画面")
    public void testLeSportdesk() throws UiObjectNotFoundException,RemoteException {
        addStep("进入超级体育");
        gotoHomeScreen("体育");
        press_down(1);
        try{
            LeSportdesk();
        }
        catch (Exception e){
            try{
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入超级体育");
                gotoHomeScreen("体育");
                LeSportdesk();
            }
            catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());
            }
        }
    }
    public void LeSportdesk() throws UiObjectNotFoundException,RemoteException{
        for (int i = 0; i < arrSportdeskPlay.length; i++) {
            press_back(2);
            press_down(4+i);
            addStep("进入" + arrSportdeskPlay[i] + "切换3次 ");
            int sportdesk = phone.findObject(By.text(Pattern.compile(arrSportdeskPlay[i]))).getParent().getChildCount();
            check("未切换到" + arrSportdeskPlay[i], sportdesk != 0);
            press_right(sportdesk-2);
            press_left(sportdesk-2);
        }
        for (int i = 4; i < 15; i++) {
            press_back(2);
            press_down(4+i);
            press_right(2);
            press_left(2);
        }
        press_back(3);
    }

    @Test
    @CaseName("进入桌面体育遍历全部赛事并播放60秒")
    public void testLeSportdeskPlay() throws UiObjectNotFoundException,RemoteException {
        addStep("进入超级体育");
        gotoHomeScreen("体育");
        try{
            LeSportdeskPlay();
        }
        catch (Exception e){
            try{
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入超级体育");
                gotoHomeScreen("体育");
                LeSportdeskPlay();
            }
            catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());
            }
        }
    }
    public void LeSportdeskPlay() throws UiObjectNotFoundException,RemoteException{
        for (int i = 0; i < arrSportdeskPlay.length; i++) {
            press_down(1);
            press_back(2);
            press_down(4 + i);
            addStep("进入" + arrSportdeskPlay[i] + "播放60秒");
            UiObject2 sportdeskPlay = phone.findObject(By.text(Pattern.compile(arrSportdeskPlay[i]))).getParent().findObject(By.res("com.lesports.launcher:id/theme_recom_3_1_lesports"));
            check("未进入" + arrSportdeskPlay[i], sportdeskPlay != null);
            sportdeskPlay.click();
            sportdeskPlay.click();
            sleepInt(60);
           exitsportdeskPlay();
        }
        press_back(3);
    }

    //退出桌面赛事
    public void exitsportdeskPlay() {
        for (int j = 0; j < 6; j++) {
            press_back(1);
            UiObject2 exitPlay = phone.findObject(By.text(Pattern.compile("退出播放|是")));
            if (exitPlay != null) {
                sleepInt(2);
                exitPlay.click();
            }
            UiObject2 coming = phone.findObject(By.text(Pattern.compile("即将上映.*")));
            if (coming != null) break;
        }
    }


    @Test
    @CaseName("从应用进入超级体育")
    public void testLanuchSport() throws UiObjectNotFoundException, RemoteException {
      try{
          LanuchSport();
      }
      catch (Exception e){
          try{
              count++;
              failCount(count,getIntParams("Loop"),e.getMessage());
              LanuchSport();
          }
          catch (RuntimeException re){
              screenShot();
              Assert.fail(re.getMessage());
          }
      }
    }
    public void LanuchSport()throws UiObjectNotFoundException,RemoteException{
    for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
        addStep("进入超级体育");
        launchApp(AppName.LeSports, PkgName.LeSports);
        upgrade();
        press_right(5);
        addStep("退出超级体育");
        press_back(1);
        sleepInt(2);
        exitAppt();
    }

}


    @Test
    @CaseName("超级体育反复切换顶部标签")
    public void testSwitchItem() throws UiObjectNotFoundException,RemoteException {
        addStep("进入超级体育");
        launchApp(AppName.LeSports, PkgName.LeSports);
        upgrade();
        try{
            SwitchItem();
        }
        catch (Exception e){
            try{
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入超级体育");
                launchApp(AppName.LeSports, PkgName.LeSports);
                SwitchItem();
            }
            catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());
            }
        }
    }
    public void SwitchItem() throws UiObjectNotFoundException,RemoteException{
        addStep("切换顶部标签");
        for (int Loop = 0;Loop < getIntParams("Loop");Loop++){
            addStep("..........Loop :"+Loop);
            press_right(5);
            sleepInt(1);
            addStep("反复切换顶部标签");
            press_left(5);
            sleepInt(1);
        }
        exitAppt();
    }


    @Test
    @CaseName("超级体育视频反复播放")
    public void testEntryMatch() throws UiObjectNotFoundException ,RemoteException{
        addStep("进入超级体育");
        launchApp(AppName.LeSports, PkgName.LeSports);
        upgrade();
        press_right(1);
        sleepInt(1);
        try{
            EntryMatch();
        }
        catch (Exception e){
            try{
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入超级体育");
                launchApp(AppName.LeSports, PkgName.LeSports);
                EntryMatch();
            }
            catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());
            }
        }
    }
    public void EntryMatch() throws UiObjectNotFoundException,RemoteException{
        for (int Loop = 0;Loop <getIntParams("Loop");Loop++){
            addStep("进入赛事");
            addStep("..........Loop :"+Loop);
            sleepInt(2);
            UiObject2 playing = waitForObj(By.res("com.lesports.tv:id/lesports_tab_game_hall").text("比赛大厅"));
            verify("未进入比赛大厅",playing !=null);
            playing.click();
            press_down(3);
            press_center(1);
            sleepInt(10);
            press_back(1);//退出播放
            addStep("退出赛事播放");
            UiObject2 exitplaying =waitForObj(By.text(Pattern.compile("退出播放")));
            if (exitplaying !=null) {
                exitplaying.click();
                exitplaying.click();
                sleepInt(1);
            }
            else {
                press_left(1);
                press_center(1);
                sleepInt(1);
            }
        }
        press_back(3);
        sleepInt(1);
        exitAppt();
    }

    @Test
    @CaseName("超级体育反复进入赛事")
    public void testEntrySportVideo() throws UiObjectNotFoundException,RemoteException {
        addStep("进入超级体育");
        launchApp(AppName.LeSports, PkgName.LeSports);
        upgrade();
        press_right(1);
        sleepInt(1);
        try{
            EntrySportVideo();
        }
        catch (Exception e){
            try{
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入超级体育");
                launchApp(AppName.LeSports, PkgName.LeSports);
                EntrySportVideo();
            }
            catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());
            }
        }

    }
    public void EntrySportVideo() throws UiObjectNotFoundException,RemoteException{
            UiObject2 playing = waitForObj(By.res("com.lesports.tv:id/lesports_tab_game_hall").text("比赛大厅"));
            verify("未进入比赛大厅",playing !=null);
            playing.click();
            press_down(3);
        for (int Loop = 0;Loop < 10;Loop++){
            addStep("..........Loop :"+Loop);
            addStep("进入赛事");
            if(Loop%2==0){
                press_left(1);
            }
            else if(Loop%2==1){
                press_right(1);
            }
            sleepInt(1);
            press_center(1);
            sleepInt(10);
            press_back(1);//退出播放
            addStep("退出赛事播放");
            UiObject2 exitplaying =waitForObj(By.text(Pattern.compile("退出播放")));
            if (exitplaying !=null) {
                exitplaying.click();
                exitplaying.click();
                sleepInt(1);
            }
            else {
                press_left(1);
                press_center(1);
                sleepInt(1);
            }
            }
        press_back(1);
        sleepInt(1);
        exitAppt();
        }


    @Test
    @CaseName("超级体育视频反复暂停播放")
    public void testPauseSportVideo() throws UiObjectNotFoundException,RemoteException {
        addStep("进入超级体育");
        launchApp(AppName.LeSports, PkgName.LeSports);
        try {
            PauseSportVideo();
        }
        catch (Exception e){
            try {
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入超级体育");
                launchApp(AppName.LeSports, PkgName.LeSports);
                PauseSportVideo();
            }
            catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());
            }
        }
    }
    public void PauseSportVideo()throws UiObjectNotFoundException, RemoteException{
        press_right(2);
        sleepInt(1);
        UiObject2 hour24=waitForObj(By.text("24小时"));
        check("未进入24小时",hour24!=null);
        hour24.click();
        sleepInt(1);
        press_down(1);
        sleepInt(1);
        press_center(1);
        sleepInt(1);
        for (int Loop = 0;Loop < getIntParams("Loop");Loop++){
            addStep("视频反复暂停播放");
            addStep("............Loop :"+Loop);
            press_center(1);
            for (int i =0;i<5;i++){
                sleepInt(5);
                press_center(1);
                sleepInt(3);
                press_center(1);
                sleepInt(3);
            }
        }
        press_back(2);
        sleepInt(2);
        exitAppt();
    }

    @Test
    @CaseName("超级体育反复切换清晰度")
    public void testSwitchClarity() throws UiObjectNotFoundException,RemoteException {
        addStep("进入超级体育");
        launchApp(AppName.LeSports, PkgName.LeSports);
        try {
            SwitchClarity();
        }
        catch (Exception e){
            try {
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入超级体育");
                launchApp(AppName.LeSports, PkgName.LeSports);
                SwitchClarity();
            }
            catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());
            }
        }
    }
    public void SwitchClarity()throws UiObjectNotFoundException,RemoteException{
        UiObject2 homepage=waitForObj(By.text("首页"));
        check("未进入24小时",homepage!=null);
        homepage.click();
        sleepInt(1);
        press_down(1);
        sleepInt(1);
        for (int Loop = 0;Loop < getIntParams("Loop");Loop++){
            addStep("反复切换清晰度");
            addStep("............Loop :"+Loop);
            press_center(1);
            sleepInt(1);
            for (int i =0;i<5;i++){
                sleepInt(5);
                press_menu(1);
                if(Loop%2==0){
                    press_left(GenerateRandom(3));
                }
                else if(Loop%2==1){
                    press_right(GenerateRandom(3));
                }
                press_center(1);
            }
            press_back(1);
            sleepInt(1);

        }
        press_back(2);
        sleepInt(2);
        exitAppt();
    }

    @Test
    @CaseName("超级体育反复切换画面比例")
    public void testSwitchRatio() throws UiObjectNotFoundException,RemoteException {
        addStep("进入超级体育");
        launchApp(AppName.LeSports, PkgName.LeSports);
        try {
            SwitchRatio();
        }
        catch (Exception e){
            try {
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入超级体育");
                launchApp(AppName.LeSports, PkgName.LeSports);
                SwitchRatio();
            }
            catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());
            }
        }
        exitApp();
        press_back(3);

    }
    public void SwitchRatio() throws UiObjectNotFoundException,RemoteException{
        press_right(2);
        sleepInt(1);
        press_down(GenerateRandom(3));
        for (int Loop = 0;Loop < getIntParams("Loop");Loop++){
            addStep("反复切换画面比例");
            addStep("............Loop :"+Loop);
            press_center(1);
            for (int i =0;i<5;i++){
                sleepInt(5);
                press_menu(1);
                press_right(1);
                press_down(GenerateRandom(2));
                press_up(GenerateRandom(2));
                press_center(1);
            }
            press_back(1);
            press_left(3);
            press_center(1);
            sleepInt(2);
            press_up(GenerateRandom(3));//随机选择一个视频
            press_down(1);
            press_right(GenerateRandom(3));
            press_left(GenerateRandom(1));
        }
        exitAppt();
        press_back(3);
    }

    @Test
    @CaseName("超级体育反复更改默认清晰度")
    public void testSwitchAutoClarity() throws UiObjectNotFoundException,RemoteException {
        addStep("进入超级体育");
        launchApp(AppName.LeSports, PkgName.LeSports);
        try {
            SwitchAutoClarity();
        }
        catch (Exception e){
            try {
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入超级体育");
                launchApp(AppName.LeSports, PkgName.LeSports);
                SwitchAutoClarity();
            }
            catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());
            }
        }



    }
    public void SwitchAutoClarity() throws UiObjectNotFoundException,RemoteException{
        addStep("进入设置");
        UiObject2 setting = waitForObj(By.res("com.lesports.tv:id/lesports_navigation_set_btn"));
        if (setting != null){
            setting.click();
            press_center(1);
        }else {
            press_right(10);
            sleepInt(1);
            press_center(1);
        }
        for (int Loop = 0;Loop < getIntParams("Loop");Loop++){
            addStep("反复更改默认清晰度");
            addStep("............Loop :"+Loop);
            for(int i=0;i<5;i++){
                press_center(1);
                press_right(5);
                press_left(GenerateRandom(4));
                press_center(1);
            }

        }
        exitAppt();
        press_back(3);
    }

    @Test
    @CaseName("超级体育反复更改默认画面比例")
    public void testSwitchAutoRatio() throws UiObjectNotFoundException,RemoteException {
        addStep("进入超级体育");
        launchApp(AppName.LeSports, PkgName.LeSports);
        try {
            SwitchAutoRatio();
        }
        catch (Exception e){
            try {
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入超级体育");
                launchApp(AppName.LeSports, PkgName.LeSports);
                SwitchAutoRatio();
            }
            catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());
            }
        }

    }
    public void SwitchAutoRatio() throws UiObjectNotFoundException,RemoteException{
        addStep("进入设置");
        UiObject2 setting = waitForObj(By.res("com.lesports.tv:id/lesports_navigation_set_btn"));
        if (setting != null){
            setting.click();
            press_center(1);
            press_right(1);
        }else {
            press_right(10);
            sleepInt(1);
            press_center(1);
            sleepInt(2);
            press_right(1);
        }
        for (int Loop = 0;Loop < getIntParams("Loop");Loop++){
            addStep("反复更改默认画面比例");
            addStep("............Loop :"+Loop);
            for(int i=0;i<5;i++){
                press_center(1);
                press_right(GenerateRandom(2));
                press_left(GenerateRandom(2));
                press_center(1);
            }
        }
        exitAppt();
        press_back(3);
    }

    @Test
    @CaseName("超级体育反复进入个人资料")
    public void testEnterPersonData() throws UiObjectNotFoundException,RemoteException {
        addStep("进入超级体育");
        launchApp(AppName.LeSports, PkgName.LeSports);
        PersonData();
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep("..........Loop :"+Loop);
            try {
                EnterPersonData();
            } catch (Exception e) {
                try {
                    count++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("进入超级体育");
                    launchApp(AppName.LeSports, PkgName.LeSports);
                    PersonData();
                    EnterPersonData();
                } catch (RuntimeException re) {
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }

            }
            press_back(2);
            exitAppt();
        }
    }
    public void EnterPersonData() throws UiObjectNotFoundException,RemoteException{


            addStep("进入个人资料");
            sleepInt(2);
            UiObject2 data =waitForObj(By.res("com.lesports.tv:id/lesports_tab_user_grow_up").text("个人资料"));
            check("没有进入个人资料页", data != null);
            if (data!= null){
                data.click();
                press_center(1);
                sleepInt(3);
            }
            addStep("返回个人资料");
            press_back(1);
            sleepInt(2);
        }



    @Test
    @CaseName("超级体育反复进入购买会员")
    public void testBuyAccount() throws UiObjectNotFoundException,RemoteException {
        addStep("进入超级体育");
        launchApp(AppName.LeSports, PkgName.LeSports);
        PersonData();
        try{
            BuyAccount();
        }
        catch(Exception e){
            try{
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入超级体育");
                launchApp(AppName.LeSports, PkgName.LeSports);
                PersonData();
                BuyAccount();
            }
            catch(RuntimeException re){
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }

    }
    public void BuyAccount()throws UiObjectNotFoundException,RemoteException{
        for (int Loop = 0;Loop < getIntParams("Loop");Loop++){
            addStep("反复进入购买会员");
            addStep("............Loop :"+Loop);
            addStep("进入购买会员");
            UiObject2 buyaccount = waitForObj(By.res("com.lesports.tv:id/lesports_tab_user_vip").text("购买会员"));
            check("没有进入购买会员页",buyaccount!=null);
            if (buyaccount != null){
                buyaccount.click();
                press_center(1);
                sleepInt(10);
                addStep("返回购买会员");
                press_back(1);
                sleepInt(1);
            }

        }
        press_back(2);
        exitAppt();
    }

    @Test
    @CaseName("超级体育反复进入会员协议")
    public void testMemberAgreement() throws UiObjectNotFoundException,RemoteException {
        addStep("进入超级体育");
        launchApp(AppName.LeSports, PkgName.LeSports);
        PersonData();
        try{
            MemberAgreement();
        }
        catch(Exception e){
            try{
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入超级体育");
                launchApp(AppName.LeSports, PkgName.LeSports);
                PersonData();
                MemberAgreement();
            }
            catch(RuntimeException re){
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }

    }
    public void MemberAgreement()throws UiObjectNotFoundException,RemoteException{
        for (int Loop = 0;Loop < getIntParams("Loop");Loop++){
            addStep("............Loop :"+Loop);
            addStep("进入会员协议");
            UiObject2 memberagreement = waitForObj(By.res("com.lesports.tv:id/lesports_tab_user_pay_agreement").text("会员协议"));
            check("未进入会员协议",memberagreement!=null);
            if (memberagreement != null){
                memberagreement.click();
                memberagreement.click();
                sleepInt(1);
                addStep("返回会员协议");
                press_back(1);
                sleepInt(1);
            }
        }
        press_back(2);
        exitAppt();
    }

    @Test
    @CaseName("超级体育反复进入消费记录")
    public void testExpensRecord() throws UiObjectNotFoundException,RemoteException {
        addStep("进入超级体育");
        launchApp(AppName.LeSports, PkgName.LeSports);
        PersonData();
        try{
            ExpensRecord();
        }
        catch(Exception e){
            try{
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入超级体育");
                launchApp(AppName.LeSports, PkgName.LeSports);
                PersonData();
                ExpensRecord();
            }
            catch(RuntimeException re){
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }

    }
    public void ExpensRecord()throws UiObjectNotFoundException, RemoteException{
        for (int Loop = 0;Loop < getIntParams("Loop");Loop++){
            addStep("............Loop :"+Loop);
            addStep("进入消费记录");
            UiObject2 memberagreement = waitForObj(By.res("com.lesports.tv:id/lesports_tab_user_pay_record").text("消费记录"));
            if ((memberagreement != null)){
                memberagreement.click();
                press_center(1);
                sleepInt(2);
                addStep("返回消费记录");
                press_back(1);
                sleepInt(1);
            }

        }
        press_back(2);
        exitAppt();
    }

    @Test
    @CaseName("超级体育反复进入退出登录")
    public void testLogout() throws UiObjectNotFoundException,RemoteException {
        addStep("进入超级体育");
        launchApp(AppName.LeSports, PkgName.LeSports);
        PersonData();
        try{
            Logout();
        }
        catch(Exception e){
            try{
                failCount(count++,getIntParams("Loop"),e.getMessage());
                addStep("进入超级体育");
                launchApp(AppName.LeSports, PkgName.LeSports);
                PersonData();
                Logout();
            }
            catch(RuntimeException re){
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }

    }
    public void Logout()throws UiObjectNotFoundException,RemoteException{
        for (int Loop = 0;Loop < getIntParams("Loop");Loop++){
            addStep("............Loop :"+Loop);
            addStep("进入退出登录");
            UiObject2 memberagreement = waitForObj(By.res("com.lesports.tv:id/lesports_tab_user_exit").text("退出登录"));
            if ((memberagreement != null)){
                check("未进入退出登录",memberagreement!=null);
                memberagreement.click();
                memberagreement.click();
                press_right(4);
                sleepInt(1);
                addStep("返回退出登录");
                press_back(1);
                sleepInt(1);
            }else {
                press_right(2);
                press_center(1);
                sleepInt(1);
                addStep("返回退出登录");
                press_back(1);
                sleepInt(1);
            }

        }
        press_back(2);
        exitAppt();
    }

    public void PersonData() throws RemoteException, UiObjectNotFoundException {
        addStep("进入用户信息界面");
        press_up(1);
        press_right(6);
        UiObject2 persondata =phone.findObject(By.res(Pattern.compile("com.lesports.tv:id/iv_navigation_user_avatar")));
        UiObject2 login1=waitForObj(By.res("com.lesports.tv:id/tv_navigation_login").text("登录"));
        if (persondata != null){
            check("未进入用户信息界面",persondata!=null);
            persondata.click();
            persondata.click();
        }
        else if(login1!=null){
            check("未进入用户登录界面",login1!=null);
            login1.click();
            login1.click();
            AccountLogin938();
//            sleepInt(1);
            press_right(1);
            UiObject2 persondata2 = waitForObj(By.res(Pattern.compile("com.lesports.tv:id/iv_navigation_user_avatar")));
            check("未进入用户信息界面2",persondata2!=null);
            persondata2.click();
            persondata2.click();
        }
        press_right(3);
    }

    public void AccountLogin938() throws UiObjectNotFoundException,RemoteException{
        UiObject2 letvAccount = waitForObj(By.clazz("android.widget.TextView").text(Pattern.compile("帐号.*|帐户: .*|超级电视帐号|会员帐号")));
        check("can't find letvAccount.", letvAccount!=null);
        sleepInt(1);
        for(int j=0;j<3;j++){
            UiObject2 accountUsed = phone.findObject(By.text("选择要登录的帐号"));     //删除帐号登陆
            if (accountUsed != null){
                press_right(1);
                check("there is no account loged in before", accountUsed != null);
                press_menu(1);
                sleepInt(1);
                UiObject2 delAccount = phone.findObject(By.res(Pattern.compile("eui.tv:id/list_item_1_title|com.stv.t2.account:id/menu_item_delete")));
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

    //CIBN超级体育升级
    public void upgrade(){
        UiObject2 upgrade =waitForObj(By.text("升级"));
        UiObject2 upgradeok =waitForObj(By.text("确定"));
        if(upgradeok!=null&&upgrade !=null){
            check("安装按钮不存在", upgradeok != null);
            clickAndWaitForNewWindow(upgradeok);
            UiObject2 nowintall=waitForObj(By.text(Pattern.compile("现在安装")),30000L);
            check("网络不稳定安装未成功", nowintall != null);
            nowintall.click();
        }
        UiObject2 nowintall=waitForObj(By.text(Pattern.compile("现在安装")));
        if(nowintall!=null){
            check("未进入现在安装", nowintall != null);
            nowintall.click();
            sleepInt(3);
        }
    }
    //CIBN超级体育退出
    public void exitAppt(){
        press_back(1);
        sleepInt(2);
        UiObject2 exitok=waitForObj(By.res("com.stv.bootadmanager:id/btn_app_exit_exit").text(Pattern.compile("退出.*")));
        if(exitok!=null){
            press_down(1);
            exitok.click();
            exitok.click();
            sleepInt(2);
        }
        else{
            press_center(1);
            sleepInt(2);
        }
    }

}