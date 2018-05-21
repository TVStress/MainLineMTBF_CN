package com.letv.cases.leui;

import android.os.Build;
import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;
import android.support.test.uiautomator.Until;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.PkgName;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import org.junit.Test;

import java.io.File;
import java.util.regex.Pattern;
/**
 * Created by wangyaxiu on 2016/12/14.
 */

public class LeTVStress extends LetvTestCase{
    int count=0;
    public void getFileNameAndDell() {
        String path = "/mnt/usb/"; // 路径

        File f = new File(path);
        if (!f.exists()) {
            System.out.println(path + " not exists");
            return;
        }

        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {
                System.out.println(fs.getName() + " [目录]");
                String adr=fs.getName();
                addStep(adr );
                delFolder("/mnt/usb/"+adr+"/letvdownload");

            } else {
                System.out.println(fs.getName());
            }
        }
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        phone.registerWatcher("cacelAD", cacelAD);
        phone.registerWatcher("ReLogin",ReLogin);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        phone.removeWatcher("ReLogin");
        launchAppByPackage(PkgName.Download);
//        sleepInt(2);
//        press_down(2);
//        press_right(4);
//        UiObject2 dell=null;
        for (int a=0;a<11;a++){
            UiObject2 dell=waitForObj(By.res("com.stv.downloads:id/delete_button"));
            if (dell==null){
                break;
            }
            press_center(2);
            sleepInt(2);
        }
        addStep("删除letvdownload文件夹");
        getFileNameAndDell();
        phone.removeWatcher("cacelAD");
        for(int i=0;i<10;i++){
            UiObject2 exitApp =phone.findObject(By.text("退出"));
            if(exitApp!=null){
                exitApp.click();
                UiObject2 exitApp1 =phone.findObject(By.text("退出"));
                if(exitApp1!=null)exitApp1.click();
                sleepInt(1);
            }
        }
    }

    BySelector letvS=By.pkg("com.letv.tv");
    BySelector homePageS = By.text("首页");
    BySelector exit = By.textContains("退出播放");
    BySelector leVideoS=By.text(Pattern.compile("乐视视频|乐视网TV版|超级影视"));
    BySelector backButtonS = By.text("退出播放");

    @Test
    @CaseName("反复进入乐视影视应用")
    public void testLetvLoop() throws UiObjectNotFoundException,RemoteException {
        for(int Loop=1;Loop<=getIntParams("Loop");Loop++) {
            addStep("进入视级影视");
            enter();
            loginAccount();
            sleepInt(2);
            addStep("退出视级影视");
        }
    }

    @Test
    @CaseName("乐视网TV版遍历菜单并播放视频")
    public void testLetvTraverseAndPlayVideo() throws UiObjectNotFoundException,RemoteException {
        enter();
        loginAccount();
        UiObject2 clickO = phone.findObject(By.text("点击哦"));
        if(clickO!=null){
            press_back(1);
            sleepInt(1);
        }
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper : " + Loop);
            try {
                LetvTraverseAndPlayVideo();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    enter();
                    loginAccount();
                    UiObject2 click1 = phone.findObject(By.text("点击哦"));
                    if(click1!=null){
                        press_back(1);
                        sleepInt(1);
                    }
                    LetvTraverseAndPlayVideo();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
    }

    public void LetvTraverseAndPlayVideo() throws UiObjectNotFoundException {
        String menus[] = { "首页","会员","分类", "发现", "我的" };
        for (int i = 0; i < menus.length; i++) {
            String menuName = menus[i];
            addStep("切换到" + menuName + "菜单");
            UiObject2 menu1 = waitForObj(By.text(Pattern.compile(menuName)));
            check("不存在" + menuName, menu1 != null);
            menu1.click();
            menu1.click();
            sleepInt(4);
            press_right(1);

        }
        for (int i = 0; i <5;i++){
            press_back(1);
            sleepInt(1);
            UiObject2 exit = phone.findObject(By.text("退出"));
            if(exit!=null)break;
        }
        UiObject2 exit1 = phone.findObject(By.text("退出"));
        check("退出不存在", exit1 != null);
        press_back(1);
        sleepInt(2);
        addStep("进入首页界面");
        UiObject2 homePage = phone.findObject(By.text("首页"));
        homePage.click();
        sleepInt(1);
        check("未能切换到首页界面", homePage.isSelected());
        press_up(2);
        UiObject2 video=waitForObj(By.res("com.letv.tv:id/item_firstpage_right_2_1"));
        check("未能要播放的视频", video != null);
        video.click();
        video.click();
        sleepInt(1);
        press_center(1);
        sleepInt(8);
        addStep("播放任意视频");
        UiObject2 playVideo1 = waitForObj(By.text(Pattern.compile(".*播放|播放|第.*集")));
        if(playVideo1!=null){
            check("can't find playvideo", playVideo1 != null);
            playVideo1.click();
        }else {
            UiObject2 videoBrowse= waitForObj(By.clazz("android.view.View"));
            check("can't find small window of vidoe", videoBrowse != null);
            videoBrowse.click();
            videoBrowse.click();
            sleep(1);
        }
        sleepInt(60);
        press_back(1);
        sleepInt(1);
        UiObject2 exit=null;
        for (int a=0;a<3;a++) {
            exit=phone.findObject(By.text("退出播放"));
            if (exit != null) {
                exit.click();
                break;
            }
            press_back(1);
        }
        sleepInt(1);
        UiObject2 firstPage =null;
        for (int b=0;b<3;b++) {
            firstPage = phone.findObject(By.text("首页"));
            if (firstPage != null) {
                break;
            }
            press_back(1);
        }
        sleepInt(2);
    }

    public void updateLeTV() throws UiObjectNotFoundException {
        UiObject2 UpdateButton = phone.findObject(By.textContains("立即升级"));
        if (UpdateButton!=null) {
            addStep("出现升级界面，开始升级");
            UpdateButton.click();
            sleepInt(15);
        }
        // 进入APP，激活会员
        for (int a = 0; a < 3; a++) {
            UiObject2 ignore = phone.findObject(By.textContains("跳过"));
            if (ignore!=null) {
                addStep("跳过激活会员");
                ignore.click();
                sleepInt(3);
            }
        }
        // 跳过会员超高清看
        UiObject2 keepwatching = phone.findObject(By.textContains("无品质继续看"));
        if (keepwatching!=null) {
            addStep("跳过会员超清看");
            keepwatching.click();
            sleepInt(3);
        }

    }

    public void loginAccount() throws UiObjectNotFoundException,RemoteException{
        UiObject2 loginNow = phone.findObject(By.text("立即登录"));
        if(loginNow!=null) {
            addStep("登录会员");
            loginNow.click();
            sleepInt(4);
            UiObject2 login = waitForObj(By.text(Pattern.compile(".*添加帐号.*|.*帐号密码登录.*|.*其他帐号登录.*")));
            if (login!=null) {
                login.click();
                sleepInt(2);
            }
            sleepInt(2);
            if(Build.DEVICE.contains("U4")){
                press_back(1);
            }
            UiObject2 userName =waitForObj(By.text(Pattern.compile("乐视帐号|会员帐号|超级电视帐号|帐        号"))).getParent().findObject(By.clazz("android.widget.EditText"));
            verify("can't find userName.", userName != null);
            userName.setText(getStrParams("USERNAME"));
            sleepInt(2);
            press_down(1);
            UiObject passwd=null;
            if(LetvUI(6.0)){
                passwd=phone.findObject(new UiSelector().className("android.widget.EditText").instance(1));
                check("can't find passwd.", passwd != null);
                sleepInt(2);
                if(Build.DEVICE.contains("U4")){
                    passwd = phone.findObject(new UiSelector().className("android.widget.EditText").instance(1));
                    check("can't find passwd.", passwd != null);
                    sleepInt(2);
                }
            }else {
                passwd = phone.findObject(new UiSelector().className("android.widget.EditText").instance(1));
                check("can't find passwd.", passwd != null);
                sleepInt(2);
            }
            passwd.setText(getStrParams("PASSWORD"));
            sleepInt(1);
            UiObject2 loginNow1 = phone.findObject(By.clazz("android.widget.Button").text("立即登录"));
            verify("can't find loginNow.", loginNow1!=null);
            press_down(1);
            press_center(1);
            sleepInt(7);
        }
    }


    @Test
    @CaseName("下载中心里相关操作")
    public void testLetvDownloadAndDelete() throws UiObjectNotFoundException, RemoteException {
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            addStep("下载中心里相关操作Loop:"+Loop);
            try {
                LetvDownloadAndDelete();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    LetvDownloadAndDelete();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
        addStep("删除已完成中的视频");
        press_left(4);
        press_down(1);
        press_right(5);
        for (int i = 0; i < 5; i++) {
            UiObject2 noVideo1 = waitForObj(By.text(Pattern.compile("没有.*下载任务")));
            if (noVideo1 == null) {
                press_center(1);
                sleepInt(1);
                press_center(1);
            } else break;
        }
        press_back(3);
    }

    public void LetvDownloadAndDelete() throws UiObjectNotFoundException, RemoteException {
        enter();
        sleepInt(3);
        loginAccount();
        press_down(2);
        addStep("进入频道界面");
        sleepInt(2);
        UiObject2 channelPage =null;
        channelPage = phone.findObject(By.text(Pattern.compile("分类")));
        for (int i=0;i<4;i++) {
            channelPage = phone.findObject(By.text(Pattern.compile("分类")));
            if (channelPage == null) {
                sleepInt(4);
            }
        }
        verify("没有找到分类", channelPage != null);
        channelPage.click();
        sleepInt(1);
        check("未能切换到分类界面", channelPage.isSelected());
        UiObject2 entertainment = waitForObj(By.text("电视剧"));
        check("电视剧不存在", entertainment != null);
        clickAndWaitForNewWindow(entertainment);
        sleepInt(2);
        UiObject2 search = waitForObj(By.res("com.letv.tv:id/global_navi_btn_search"));
        check("电视剧不存在", search != null);
        search.click();
        search.click();
        sleepInt(2);
        UiObject2 W = waitForObj(By.text("W"));
        UiObject2 M = waitForObj(By.text("M"));
        UiObject2 N = waitForObj(By.text("N"));
        W.click();
        W.click();
        M.click();
        M.click();
        N.click();
        N.click();
        sleepInt(1);
        press_right(4);
        sleepInt(5);
        press_center(1);
        UiObject2 download = waitForObj(By.text("下载"));
        check("下载按钮不存在", download != null);
        download.click();
        download.click();
        UiObject2 downloadInterface = waitForObj(By.text("1"));
        check("未进入到下载界面", downloadInterface != null);
        press_down(1);
        for (int i = 0; i < 1; i++) {
            press_center(1);
            sleepInt(6);
            UiObject2 definition = null;
            for (int a=0;a<4;a++){
                definition = waitForObj(By.text(Pattern.compile("1080P|超清|高清|标清|流畅")));
                if (definition!=null){
                    break;
                }
                sleepInt(2);
            }
            check("视频清晰度选择不存在", definition != null);
            press_right(5);
            press_center(1);
            sleepInt(1);
            UiObject2 download1 = waitForObj(By.text(Pattern.compile("下载.*")));
            check("下载按钮不存在", download1 != null);
            press_right(i);
        }
        addStep("退出乐视网tv版");
        press_back(4);
        exitApp();
        addStep("进入下载中心播放正在下载的视频");
//        launchAppByPackage(PkgName.Download);
        launchApp(AppName.Download,PkgName.Download);
        UiObject2 downloadUI = waitForObj(By.text("已完成"));
        check("未进入到下载界面", downloadUI != null);
        for (int a=0;a<15;a++){
            UiObject2 done=waitForObj(By.text(Pattern.compile("没有.*下载任务")));
            if (done!=null){
                break;
            }
            sleepInt(10);
        }
        press_down(2);
        sleepInt(1);
        press_right(1);
        sleepInt(8);
        UiObject2 videoDownloaded = waitForObj(By.clazz("android.support.v7.widget.RecyclerView")).getChildren().get(0);
        check("未进入到下载界面", videoDownloaded != null);
        videoDownloaded.click();
        videoDownloaded.click();
        sleepInt(30);
        UiObject2 playVideo = waitForObj(By.pkg(Pattern.compile("com.letv.tv|com.stv.videoplayer")));
        if (playVideo==null){
            press_center(1);
        }
        check("未能正常播放视频", playVideo != null);
        press_back(1);
        UiObject2 exitVideo = phone.findObject(By.text("退出播放"));
        if (exitVideo != null) {
            exitVideo.click();
            sleepInt(1);
        }
        addStep("删除一正在下载的视频");
        sleepInt(2);
        press_down(2);
        press_right(3);

        for (int i = 0; i < 10; i++) {
            UiObject2 noVideo = waitForObj(By.text(Pattern.compile("没有.*下载任务")));
            if (noVideo == null) {
                press_center(1);
                UiObject2 delete = waitForObj(By.text(Pattern.compile("删除任务和文件")));
                verify("删除任务和文件不存在",delete!=null);
                press_center(1);
                sleepInt(2);
            } else break;
        }
        press_right(1);
        sleepInt(3);
        press_left(1);
        UiObject2 noVideo = waitForObj(By.text(Pattern.compile("没有.*下载任务")));
        check("删除失败", noVideo != null);
    }

    public void enter() throws UiObjectNotFoundException {
        addStep("进入乐视视频");
        launchApp(AppName.LeTv,PkgName.LeTv);
        sleepInt(5);
        updateLeTV();
        check("未进入到乐视网TV版", waitForExist(letvS, 15000));
    }


    @Test
    @CaseName("播放乐视网TV版中视频后查看播放记录")
    public void testPlayRecord() throws UiObjectNotFoundException ,RemoteException{
        enter();
        loginAccount();
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                PlayRecord();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    enter();
                    loginAccount();
                    PlayRecord();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }

    }

    public void PlayRecord() throws UiObjectNotFoundException {
        UiObject2 firstPage = waitForObj(By.text("首页"));
        check("没有首页按钮", firstPage != null);
        firstPage.click();
        sleepInt(1);
        press_up(2);
        addStep("在乐视网TV版中视频文件播放，查看播放记录");
        press_right(1);
        press_down(1);
        press_center(1);
        sleepInt(4);
        UiObject2 play=waitForObj(By.text(Pattern.compile("第.*集|继续播放|本集重播|播放")));
        verify("failed to find the play butten",play!=null);
        play.click();
        sleepInt(30);
        press_back(1);
        addStep("进入播放记录,查看任意记录进行播放");
        UiObject2 backButton=waitForObj(backButtonS);
        if(backButton!=null) clickAndWaitForNewWindow(backButton);
        for(int i=0;i<4;i++){
            UiObject2 firstPage1 = phone.findObject(By.text("首页"));
            if(firstPage1==null){
                press_back(1);
            }else break;
        }
        sleepInt(1);
        UiObject2 record = waitForObj(By.res("com.letv.tv:id/layout_home_firstpage")).getChildren().get(6);
        check("没有找到记录收藏按钮", record != null);
        record.click();
        record.click();
        sleepInt(1);
        UiObject2 continueButton1 = waitForObj(By.text(Pattern.compile("继续播放|本集重播")));
        if(continueButton1==null) {
            press_center(1);
        }
        UiObject2 continueButton = waitForObj(By.text(Pattern.compile("继续播放|本集重播")));
        check("没有找到继续播放按钮", continueButton != null);
        continueButton.click();
        continueButton.click();
        sleepInt(10);
        addStep("退出播放，删除播放记录的全部信息");
        press_back(1);
        UiObject2 backButton1=waitForObj(backButtonS);
        if(backButton1!=null) {
            backButton1.click();
            sleepInt(3);
        }
        press_back(1);
        sleepInt(2);
        press_menu(1);
        press_center(1);
        UiObject2 deleteButton = waitForObj(By.text("删除"));
        check("确认删除按钮不存在", deleteButton!=null);
        clickAndWaitForNewWindow(deleteButton);
        UiObject2 empty = waitForObj(By.text("暂无播放记录，快去观看吧"));
        check("播放记录删除不成功", empty!=null);
        press_back(1);
    }



    @Test
    @CaseName("乐视网TV版点播视频，快进/快退、暂停/播放，切换清晰度/画面比例，进行视频切换")
    public void testLetvVideo() throws UiObjectNotFoundException ,RemoteException{
        enter();
        loginAccount();
        addStep("找到电视列表，查看任意电视视频");
        press_up(1);
        press_right(3);
        for (int a=0;a<3;a++) {
            UiObject2 TV = waitForObj(By.text("电视剧"));
            if (TV!=null) {
                verify("没有找到电视剧列表", TV != null);
                TV.click();
                TV.click();
                break;
            }
        }
        sleepInt(1);
        UiObject2 televisionShows=phone.findObject(By.text(Pattern.compile(".*更新至.*")));
        if(televisionShows!=null){
            televisionShows.click();
            televisionShows.click();
            sleepInt(1);
        }else {
            press_down(2);
            press_center(1);
        }
        sleepInt(3);
        UiObject2 detail = waitForObj(By.text("详情"));
        verify("未进入到电视剧详情页", detail != null);
        press_center(1);
        sleepInt(10);
        for (int loop = 0; loop < getIntParams("Loop"); loop++) {
            addStep("第" + loop + "循环");
            try {
                LetvVideo();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    enter();
                    loginAccount();
                    addStep("找到电视列表，查看任意电视视频");
                    UiObject2 TV1=waitForObj(By.text("电视剧"));
                    check("没有找到电视剧列表", TV1 != null);
                    TV1.click();
                    sleepInt(1);
                    UiObject2 televisionShows1=waitForObj(By.text(Pattern.compile("更新至.*集")));
                    check("没有找到电视剧", televisionShows1 != null);
                    televisionShows1.click();
                    televisionShows1.click();
                    sleepInt(1);
                    UiObject2 detai2 = waitForObj(By.text("详情"));
                    check("未进入到电视剧详情页", detai2 != null);
                    press_center(1);
                    sleepInt(10);
                    LetvVideo();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
    }

    public void LetvVideo() throws UiObjectNotFoundException {
        addStep("暂停、播放、快进、快退操作");
        press_center(1);
        press_right(10);
        sleepInt(10);
        press_center(1);
        press_left(10);
        sleepInt(4);
        press_center(1);
        sleepInt(2);
        press_center(1);
        addStep("按菜单键，进行切换视频清晰度和画面比例");
        addStep("转换清晰度");
        for (int i = 0; i < 4; i++) {
            press_menu(1);
            UiObject2 menu=null;
            menu=phone.findObject(By.clazz("android.widget.HorizontalScrollView"));
            if (menu==null){
                press_menu(1);
                break;
            }
            menu=waitForObj(By.clazz("android.widget.HorizontalScrollView"));
            check("menu菜单没有弹出",menu!=null);
            press_left(2);
            press_right(1);
            press_down(1);
            press_center(1);
            sleepInt(20);
            for (int a=0;a<3;a++) {
                UiObject2 replay = phone.findObject(By.text(Pattern.compile("超级影视.*")));
                if (replay != null) {
                    sleepInt(5);
                }
            }
        }
        addStep("切换屏幕尺寸");
        for (int i = 0; i < 3; i++) {
            press_menu(1);
            sleepInt(2);
            UiObject2 menu=null;
            menu=waitForObj(By.clazz("android.widget.HorizontalScrollView"));
            if (menu==null){
                press_menu(1);
                sleepInt(2);
            }
            menu=waitForObj(By.clazz("android.widget.HorizontalScrollView"));
            check("menu菜单没有弹出", menu != null);
            press_left(3);
            press_right(2);
            press_down(1);
            press_center(1);
            sleepInt(20);
            UiObject2 feedBack=phone.findObject(By.text("联系客服"));
            if(feedBack!=null){
                press_back(1);
            }
        }
        if(Build.DEVICE.contains("U4")){
            press_menu(1);
            press_right(2);
            press_up(2);
            press_center(1);
        }
        addStep("按下键，在列表中选择任意视频，播放");
        press_down(1);
        press_right(1);
        press_center(1);
        sleepInt(15);
    }


    @Test
    @CaseName("观看一个电影10分钟，并收藏")
    public void testPlayMovie()throws UiObjectNotFoundException,RemoteException {
        addStep("进入乐视网TV版");
        launchApp(AppName.LeTv,PkgName.LeTv);
        sleepInt(5);
        updateLeTV();
        loginAccount();
        addStep("进入频道");
        UiObject2 ObjChns = waitForObj(By.clazz("android.widget.TextView").text(Pattern.compile(".*频道.*|分类")));
        verify("没有找到频道列表", ObjChns != null);
        ObjChns.click();
        clickAndWaitForNewWindow(ObjChns);

        addStep("进入电影频道");
        UiObject2 ObjChan1 = waitForObj(By.clazz("android.widget.TextView").text("电影"));
        verify("频道电影不存在", ObjChan1 != null);
        addStep("点击频道名称");
        ObjChan1.click();
        ObjChan1.click();
        sleepInt(1);
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                PlayMovie();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    addStep("进入乐视网TV版");
                    launchApp(AppName.LeTv, PkgName.LeTv);
                    sleepInt(5);
                    updateLeTV();
                    loginAccount();
                    addStep("进入频道");
                    UiObject2 ObjChns1 = waitForObj(By.clazz("android.widget.TextView").text(Pattern.compile("频道.*|分类")));
                    check("没有找到频道列表", ObjChns1 != null);
                    ObjChns1.click();
                    clickAndWaitForNewWindow(ObjChns1);

                    addStep("进入电影频道");
                    UiObject2 ObjChan2 = waitForObj(By.clazz("android.widget.TextView").text("电影"));
                    check("频道电影不存在", ObjChan2 != null);
                    addStep("点击频道名称");
                    ObjChan2.click();
                    ObjChan2.click();
                    sleepInt(1);
                    PlayMovie();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
        exitApp();
    }

    public void PlayMovie()throws UiObjectNotFoundException {
        addStep("进入排行并打开电影详情页面");
        UiObject2 top=waitForObj(By.text("热播榜"));
        check("没有找到电影排行菜单",top!=null);
        top.click();
        top.click();
        sleepInt(1);
        press_down(2);
        press_center(1);
        sleepInt(2);
        UiObject2 filmName=waitForObj(By.res("com.letv.tv:id/tv_name"));
        check("未找到电影名字",filmName!=null);
        String name=filmName.getText();
        UiObject2 playBtn = waitForObj(By.text(Pattern.compile("播放|继续播放|预告")));
        check("未从播放界面返回回到视频详情页", playBtn != null);
        playBtn.click();
        sleepInt(3*60);
//        sleepInt(60);
        press_back(1);
        UiObject2 exitApp = waitForObj(By.text(Pattern.compile("退出播放")));
        check("未找到退出播放", exitApp != null);
        exitApp.click();
        sleepInt(1);
        UiObject2 favariteCancel = waitForObj(By.text("已收藏"));
        if (favariteCancel != null) {
            addStep("取消收藏该电影");
            favariteCancel.click();
            favariteCancel.click();
            sleepInt(1);
        }
        addStep("收藏该电影");
        UiObject2 favarites = waitForObj(By.text("收藏"));
        check("未找到收藏按钮", favarites != null);
        favarites.click();
        if (favarites != null) favarites.click();
        sleepInt(2);
        screenShot();
        press_back(1);
        UiObject2 recorder = waitForObj(By.res("com.letv.tv:id/global_navi_btn_history"));
        check("未找到播放记录按钮", recorder != null);
        recorder.click();
        recorder.click();
        sleepInt(2);
        addStep("进入追剧收藏播放收藏的电影30s");
        UiObject2 favarite = waitForObj(By.text("追剧收藏"));
        check("未找到播放记录按钮", favarite != null);
        favarite.click();
        favarite.click();
        sleepInt(2);
        press_down(1);
        press_center(1);
        UiObject2 playBtn1 = waitForObj(By.text(Pattern.compile("播放|继续播放|预告")));
        check("未从播放界面返回回到视频详情页", playBtn1 != null);
        playBtn1.click();
        sleepInt(30);
        press_back(1);
        UiObject2 exitApp1 = waitForObj(By.text(Pattern.compile("退出播放")));
        check("未找到退出播放", exitApp1 != null);
        exitApp1.click();
        exitApp1.click();
        sleepInt(1);
        UiObject2 favariteCancel1 = waitForObj(By.text("已收藏"));
        addStep("取消收藏该电影");
        favariteCancel1.click();
        favariteCancel1.click();
        sleepInt(3);
        press_back(1);
        sleepInt(1);
        UiObject2 film = phone.findObject(By.text(name));
        check("取消收藏不成功", film == null);
        press_back(1);
    }




    private final UiWatcher cacelAD = new UiWatcher() {
        public boolean checkForCondition() {
            UiObject2 fileExsit = phone.findObject(By.text("立即参与活动"));
            if (fileExsit!= null) {
                addStep("file exists");
                press_back(1);
                sleepInt(1);
                return true;
            } else {
                return false;
            }
        }
    };



    //帐号重新登录
    private  final UiWatcher ReLogin = new UiWatcher() {
        public boolean checkForCondition() {
            UiObject2 uniformWindows = phone.findObject(By.text(Pattern.compile(".*您的账号已在另一台电视设备上登录，请重新进行登录.*",Pattern.DOTALL)));
            if (uniformWindows != null) {
                UiObject2 relogin = phone.findObject(By.text("重新登录"));
                relogin.clickAndWait(Until.newWindow(), 200);
//                if (relogin!=null){
//                    relogin.clickAndWait(Until.newWindow(), 200);
//                }
//                sleepInt(3);
                UiObject2 account=waitForObj(By.res("com.stv.t2.account:id/grid_item"));
//                account.clickAndWait(Until.newWindow(), 200);
                account.click();
                UiObject2 account1=waitForObj(By.text(Pattern.compile(".*集|播放.*|.*播放|集.*")));
                account1.click();
                account1.click();
                addStep("UIwatcher  登录账号");
                return true;
            } else {
                return false;
            }
        }
    };

}
