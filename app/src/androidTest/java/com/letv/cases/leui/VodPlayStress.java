package com.letv.cases.leui;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiWatcher;

import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VodPlayStress extends LetvTestCase {
    int count = 0;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        phone.registerWatcher("continueSee", continueSee);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        phone.removeWatcher("continueSee");
    }

    private final UiWatcher continueSee = new UiWatcher() {
        public boolean checkForCondition() {
            UiObject2 continueSeeing = phone.findObject(By.text(Pattern.compile("无品质继续.*")));
            if (continueSeeing != null) {
                press_right(1);
                continueSeeing.click();
                continueSeeing.click();
                sleepInt(5);
                return true;
            } else {
                return false;
            }
        }
    };


    @Test
    @CaseName("进入桌面管理调整桌面乐见")
    public void testDeskSwitchLeVideo() throws UiObjectNotFoundException, RemoteException{
        DesktopAdjustment("找视频|乐见");

    }



    @Test
    @CaseName("乐见(视频)桌面播放视频")
    public void testTVPlay() throws UiObjectNotFoundException , RemoteException {
        gotoHomeScreen("找视频|乐见");
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper : " + Loop);
            try {
                TVPlay();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("找视频|乐见");
                    press_down(1);
                    UiObject2 poster2=waitForObj(By.clazz("android.widget.FrameLayout").res("com.stv.plugin.video:id/poster_large"));
                    check("视频桌面没有找到海报", poster2 != null);
                    poster2.click();
                    poster2.click();
                    sleepInt(2);
                    updateAPP();
                    TVPlay();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
        press_back(3);
    }

    public void TVPlay() throws UiObjectNotFoundException , RemoteException {
        addStep("在乐见(视频)桌面选择任意海报，进入");
        updateAPP();
        press_down(1);
        UiObject2 poster1=waitForObj(By.clazz("android.widget.FrameLayout").res("com.stv.plugin.video:id/poster_large"));
        check("视频桌面没有找到海报", poster1 != null);
        poster1.click();
        poster1.click();
        sleepInt(2);
        addStep("打开视频详情");
        UiObject2 play1 = waitForObj(By.text(Pattern.compile("第.*集|.*播放|预告")));
        if(play1!=null){
            check("Not open video detail", play1 != null);
            play1.click();
        }
        addStep("播放视频，播放5分钟,并调节声音");
        sleepInt(80);
        checkAccountLogin();
        sleepInt(5);
        sleepInt(24);
        press_vol_up(10);
        press_vol_down(10);
        sleepInt(2);
        addStep("返回点播桌面");
        exitApp();
        sleepInt(2);
        press_back(3);
        sleepInt(1);
        UiObject2 desktop1=phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile("找视频|乐见")).selected(true));
        UiObject2 desktop2=phone.findObject(By.pkg("com.stv.launcher").text(Pattern.compile("找视频|乐见")).focused(true));
        verify("没有返回到视频桌面", desktop1 != null || desktop2 != null);
    }

    public void initClarity() throws UiObjectNotFoundException , RemoteException {
        gotoHomeScreen("找视频|乐见");
        sleepInt(5);
        press_down(2);
        checkAccountLogin();
        addStep("进入视频桌面的搜索");
//        UiObject2 search1 = waitForObj(By.res("com.stv.plugin.video:id/poster_no_title_2"));
        UiObject2 search1 = waitForObj(By.res("com.stv.plugin.video:id/poster_no_title_2"));
        UiObject2 search2 = waitForObj(By.res("com.stv.plugin.video:id/poster_no_title_2"));
        verify("没有找到乐见桌面的搜索框",search1!=null||search2!=null);
        if(callShell("getprop ro.product.uitype").contains("cibn")){
            search2.click();
            search2.click();
        }else {
            search1.click();
            search1.click();
        }
        sleepInt(1);
        for (int i=0;i<3;i++) {
            UiObject2 update = phone.findObject(By.res("com.letv.leso:id/update"));
            if (update != null) {
                update.click();
                sleepInt(10);
            }
        }
        addStep("搜索WMNCQ，得到电视剧武媚娘传奇");
        // 首拼音搜索 武媚娘传奇
//        press_center(2);
        UiObject2 clear=phone.findObject(By.res("com.letv.leso:id/searchboard_clear_btn"));
        if(clear!=null){
            clear.click();
            clear.click();
        }

        UiObject2 W = waitForObj(By.clazz("android.widget.TextView").text("W"));
        UiObject2 M = waitForObj(By.clazz("android.widget.TextView").text("M"));
        UiObject2 N = waitForObj(By.clazz("android.widget.TextView").text("N"));
        UiObject2 C = waitForObj(By.clazz("android.widget.TextView").text("C"));
        UiObject2 Q = waitForObj(By.clazz("android.widget.TextView").text("Q"));
        W.click();W.click();M.click();M.click();N.click();N.click();C.click();C.click();Q.click();Q.click();
        sleepInt(1);

        addStep("进行播放视频，播放5分钟");
        UiObject2 playSets = waitForObj(By.text(Pattern.compile("武媚娘传奇.*")));
        verify("武媚娘传奇不存在",playSets!=null);
        playSets.click();
        sleepInt(1);
        press_right(1);
        press_center(2);
        sleepInt(3);
        UiObject2 advertising=phone.findObject(By.text("立即领取"));
        if(advertising!=null){
            press_right(2);
            press_center(1);
        }

        UiObject2 playDetail = waitForObj(By.clazz("android.widget.TextView").text("详情"));
        verify("未进入详情页", playDetail!=null);
        UiObject2 playNo = waitForObj(By.text(Pattern.compile("第.*集|播放")));
        String strPlayNo = playNo.getText();
        int num = getNumber(strPlayNo);
        press_center(1);
    }



    @Test
    @CaseName("乐见(视频)桌面进入搜索播放视频，切换视频清晰度或画面比例")
    public void testChClarity() throws UiObjectNotFoundException , RemoteException {
        initClarity();
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                ChClarity();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    initClarity();
                    ChClarity();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
        addStep("退出播放返回桌面");
        exitApp();
        press_back(3);
    }

    public void ChClarity() throws UiObjectNotFoundException , RemoteException {
        sleepInt(230);
        checkAccountLogin();
        for (int i=0; i<5; i++)
        {
            addStep("视频播放中，按菜单键，调出菜单列表，选择视频任意清晰度，按确定键");
            press_menu(1);
            UiObject2 chclar = waitForObj(By.clazz("android.widget.HorizontalScrollView"));
            check("没有显示清晰度和比例菜单", chclar != null);
            press_left(4);
            press_right(1);
            UiObject2 chclar1 = phone.findObject(By.clazz("android.widget.HorizontalScrollView")).getChildren().get(0).getChildren().get(1);
            check("没有选中清晰度列表", chclar1.hasObject(By.selected(true)));
            press_up(1);
            press_center(1);
            sleepInt(15);
        }


        int boundSize=0;
        for (int i=0; i<2; i++)
        {
            addStep("视频播放中，按菜单键，调出菜单列表，选择视频任意画面比例，按确定键");
            UiObject2 view = phone.findObject(By.clazz("android.view.View"));
            String viewBound = view.getVisibleBounds().toString();
            System.out.println(viewBound);
            press_menu(1);
            sleepInt(1);
            press_left(4);
            press_right(2);
            UiObject2 chclar = phone.findObject(By.clazz("android.widget.HorizontalScrollView")).getChildren().get(0).getChildren().get(2);
            check("没有显示清晰度和比例菜单", chclar.hasObject(By.selected(true)));
            press_down(1);
            press_center(1);
            sleepInt(2);
            UiObject2 view1 = phone.findObject(By.clazz("android.view.View"));
            String viewBound_aft = view1.getVisibleBounds().toString();
            System.out.println(viewBound_aft);
            if(!viewBound_aft.equals(viewBound))boundSize++;
            System.out.println(boundSize);
        }
        check("切换比例设置不成功", boundSize > 0);

        addStep("回到初始画面比例");
        press_menu(1);
        press_up(2);
        press_center(1);
    }

    @Test
    @CaseName("乐见(视频)桌面进入搜索播放视频，切换集数")
    public void testChPay() throws UiObjectNotFoundException , RemoteException {
        gotoHomeScreen("找视频|乐见");
        sleepInt(5);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                ChPlay();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("找视频|乐见");
                    sleepInt(5);
                    ChPlay();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
    }

    public void ChPlay() throws UiObjectNotFoundException , RemoteException {
        addStep("进入视频桌面的搜索");
        UiObject2 search1 = waitForObj(By.res("com.stv.plugin.video:id/poster_no_title_2"));
        UiObject2 search2 = waitForObj(By.res("com.stv.plugin.video:id/poster_no_title_2"));
        check("没有找到乐见桌面的搜索框",search1!=null||search2!=null);
        if(callShell("getprop ro.product.uitype").contains("cibn")){
            search2.click();
            search2.click();
        }else {
            search1.click();
            search1.click();
        }
        sleepInt(2);
        UiObject2 leSo = waitForObj(By.pkg("com.letv.leso"));
        check("未进入乐看搜索", leSo != null);
        addStep("搜索WMNCQ，得到电视剧武媚娘传奇");
        // 首拼音搜索 武媚娘传奇
        UiObject2 W = phone.findObject(By.clazz("android.widget.TextView").text("W"));
        UiObject2 M = phone.findObject(By.clazz("android.widget.TextView").text("M"));
        UiObject2 N = phone.findObject(By.clazz("android.widget.TextView").text("N"));
        UiObject2 C = phone.findObject(By.clazz("android.widget.TextView").text("C"));
        UiObject2 Q = phone.findObject(By.clazz("android.widget.TextView").text("Q"));
        W.click();W.click();M.click();M.click();N.click();N.click();C.click();C.click();Q.click();Q.click();
        sleepInt(1);

        addStep("进行播放视频，播放5分钟");
        UiObject2 playSets = waitForObj(By.clazz("android.widget.TextView").text("武媚娘传奇"));
        playSets.click();
        press_right(1);
        press_center(2);

        sleepInt(2);
        UiObject2 playDetail = waitForObj(By.clazz("android.widget.TextView").text("详情"));
        check("未进入详情页", playDetail!=null);
        UiObject2 playNo = waitForObj(By.text(Pattern.compile("第.*集|播放")));
        String strPlayNo = playNo.getText();
        int num = getNumber(strPlayNo);
        press_center(1);
        sleepInt(230);
//        sleepInt(60);
        checkAccountLogin();
        addStep("按向下键切集");
        press_down(1);
        sleepInt(1);
        if (num >= 60) {
            addStep("向左切三集");
            press_left(3);
            press_center(1);
            sleepInt(15);
            press_back(1);
            UiObject2 exitPlay = null;
            for (int a=0;a<3;a++){
                exitPlay = waitForObj(By.text("退出播放"));
                if (exitPlay!=null){
                    break;
                }
                press_back(1);
            }
            check("没有找到退出播放按钮", exitPlay!=null);
            exitPlay.click();
            sleepInt(2);
            check("未回到详情页", playDetail!=null);
            String strPlayNoAft = playNo.getText();
            addStep("从" + strPlayNo + "切换到了" + strPlayNoAft);
            int numAft = getNumber(strPlayNoAft);
//            check("切换集数和实际不符", numAft - num == -3);
        } else {
            addStep("向右切三集");
            press_right(3);
            press_center(1);
            sleepInt(15);
            press_back(1);
            UiObject2 exitPlay = null;
            for (int a=0;a<3;a++){
                exitPlay = waitForObj(By.text("退出播放"));
                if (exitPlay!=null){
                    break;
                }
                press_back(1);
            }
            check("没有找到退出播放按钮", exitPlay!=null);
            exitPlay.click();
            sleepInt(2);
            check("未回到详情页", playDetail != null);
            String strPlayNoAft = playNo.getText();
            addStep("从" + strPlayNo + "切换到了" + strPlayNoAft);
            int numAft = getNumber(strPlayNoAft);
            check("切换集数和实际不符", numAft - num == 3);
        }
        press_back(3);
        sleepInt(1);
        check("没有返回视频桌面", phone.getCurrentPackageName().equals(PACKAGE_HOME));
    }

    //截取字符串中的数字
    private int getNumber(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return Integer.parseInt(matcher.group(0));
        }
        return 0;
    }

    public void updateAPP() {
        UiObject2 update = phone.findObject(By.clazz("android.widget.Button").textContains("升级"));
        if (update!=null) {
            addStep("更新乐视视频应用");
            update.click();
            sleepInt(30);
            UiObject2 confirm = phone.findObject(By.clazz("android.widget.Button").textContains("确定"));
            if (confirm!=null) {
                confirm.click();
                sleepInt(5);
            }
            UiObject2 install = phone.findObject(By.clazz("android.widget.Button").textContains("安装"));
            if (install!=null) {
                install.click();
                sleepInt(15);
            }
            UiObject2 open = phone.findObject(By.clazz("android.widget.Button").textContains("打开"));
            if (open!=null) {
                open.click();
                sleepInt(5);
            }
            addStep("重新打开点播桌面");
            gotoHomeScreen("视频|乐见");
            sleepInt(5);
            UiObject2 play = phone.findObject(By.text(Pattern.compile("视频|乐见")));
            verify("Not on the VOD desktop", play!=null);
            play.click();
            sleepInt(5);
            press_center(1);
        }
    }

    public void checkAccountLogin(){
            UiObject2 loginNow = phone.findObject(By.clazz("android.widget.Button").text("立即登录"));
            if(loginNow!=null) {
                addStep("登录会员");
                loginNow.click();
                sleepInt(2);
                UiObject2 loginNow2 = phone.findObject(By.clazz("android.widget.Button").text("立即登录"));
                check("can't find loginNow.", loginNow2 != null);
                loginNow2.click();
                sleepInt(2);
                UiObject2 MTBFAccount = phone.findObject(By.text(Pattern.compile("MTBF.*")));
                if(MTBFAccount!=null){
                    check("can't find loginNow.", MTBFAccount != null);
                    MTBFAccount.click();
                    sleepInt(2);
                    UiObject2 passwd = phone.findObject(By.text("密        码")).getParent().findObject(By.clazz("android.widget.EditText"));
                    if(passwd!=null){
                        passwd.setText(getStrParams("PASSWORD"));
                        sleepInt(1);
                    }
                }else {
                    UiObject2 login = phone.findObject(By.text(Pattern.compile(".*添加帐号.*|.*帐号密码登录.*|.*帐号登录.*")));
                    if (login!=null) {
                        login.click();
                        sleepInt(2);
                    }
                    sleepInt(2);
                    UiObject2 userName = phone.findObject(By.text("乐视帐号")).getParent().findObject(By.clazz("android.widget.EditText"));
                    check("can't find userName.", userName != null);
                    userName.setText(getStrParams("USERNAME"));
                    sleepInt(2);
                    press_down(1);
                    UiObject2 passwd = phone.findObject(By.text("密        码")).getParent().findObject(By.clazz("android.widget.EditText"));
                    check("can't find passwd.", passwd != null);
                    passwd.setText(getStrParams("PASSWORD"));
                    sleepInt(1);
                    UiObject2 loginNow1 = phone.findObject(By.clazz("android.widget.Button").text("立即登录"));
                    check("can't find loginNow.", loginNow1 != null);
                    loginNow.click();
                    sleepInt(1);
                }
        }
    }
}
