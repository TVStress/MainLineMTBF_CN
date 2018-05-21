package com.letv.cases.leui;

import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.PkgName;
import com.letv.common.LetvTestCase;

import org.junit.After;
import org.junit.Test;
import org.junit.Assert;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;


public class DownLoadStress extends LetvTestCase {

    int count=0;
    BySelector clearDataS = By.clazz("android.widget.TextView").text(Pattern.compile("数据清理|Data clear"));
    BySelector okButtonS = By.text(Pattern.compile("确定|OK"));
    BySelector downCenter = By.text(Pattern.compile("下载中心"));
    BySelector addressBarS = By.clazz("android.widget.EditText").text(Pattern.compile("搜索或输入网址|Search or type URL"));
    BySelector check1S = By.clazz("android.widget.TextView").text(Pattern.compile("设置|Settings"));
    BySelector check2S = By.clazz("android.widget.TextView").text(Pattern.compile("清除网页缓存|Clear web cache"));
    BySelector check3S = By.clazz("android.widget.TextView").text(Pattern.compile("清除历史记录|Clear history"));
    BySelector check4S = By.clazz("android.widget.TextView").text(Pattern.compile("清除所有 Cookie 数据|Clear all cookie data"));
    BySelector clearAllS = By.clazz("android.widget.TextView").text(Pattern.compile("全部清除|Clear all"));
    BySelector goButtonS = By.res("com.android.browser:id/go");
    BySelector allowBtnS = By.clazz("android.widget.Button").text(Pattern.compile("允许|Allow"));
    BySelector numberS = By.res("com.android.browser:id/tabcount");
    BySelector dlUndoneS = By.clazz("android.widget.TextView").text(Pattern.compile("暂无下载任务|No download tasks"));
    BySelector dlManageS = By.clazz("android.widget.TextView").text(Pattern.compile("下载管理|Download"));
    BySelector cancelS = By.clazz("android.widget.TextView").text(Pattern.compile("取消|Cancel"));
    BySelector deAllS = By.text(Pattern.compile("全部删除|Clear all"));
    BySelector dlDoneS = By.clazz("android.widget.TextView").text(Pattern.compile("已完成|Completed"));
    BySelector curlBarS = By.clazz("android.widget.EditText");
    BySelector shareDeviceS = By.clazz("android.widget.TextView").text("共享设备");
    BySelector menuBartext = By.clazz("android.widget.TextView").text("按菜单键更多操作");
    BySelector letvS=By.pkg("com.letv.tv");
    BySelector homePageS = By.text("首页");
    BySelector extStorageDeviceSUB = By.text("USB");
    BySelector exit = By.textContains("退出播放");
    BySelector leVideoS=By.text(Pattern.compile("乐视视频|乐视网TV版|超级影视"));
    BySelector intStorageDeviceS = By.clazz("android.widget.TextView").text("本机存储");
    BySelector backButtonS = By.text("退出播放");


    @Test
    @CaseName("下载中心添加下载")
    public void testLoadCenterAdd()throws UiObjectNotFoundException,RemoteException{
        addStep("进入到应用文件管理");
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        press_down(1);
        try {
            LoadCenterAdd();
        }
        catch (Exception e){
            try{
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入到应用文件管理");
                launchApp(AppName.Filemanager, PkgName.Filemanager);
                press_down(1);
                LoadCenterAdd();
            }
            catch (RuntimeException re){
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }
    }
    public void LoadCenterAdd()throws UiObjectNotFoundException,RemoteException{
        addStep("进入远程存储SMB服务");
        UiObject2 shareDevice = waitForObj(shareDeviceS);
        clickAndWaitForNewWindow(shareDevice);
        sleepInt(1);
        press_right(4);
        sleepInt(2);
        UiObject2 Smbenter1 = waitForObj(By.text(Pattern.compile("SMB")));
        UiObject2 Smbenter2 = waitForObj(By.text("10.58.81.227"));
        if(Smbenter1 != null || Smbenter2 != null) {
            Smbenter1.click();
            Smbenter2.click();
            sleepInt(5);
            press_right(1);
            check("未进入SMB服务器", menuBartext != null);
            sleepInt(5);
        }
        else{
            press_back(3);
            addSMBset_login();
        }
        press_left(2);
        press_menu(1);
        press_down(3);
        UiObject2 vid= waitForObj(By.text(Pattern.compile("视图")));
//        check("未进入菜单视图",vid !=null);
        vid.click();
        UiObject2 grid= waitForObj(By.text(Pattern.compile("网格")));
//        check("未进入菜单网格",grid !=null);
        grid.click();
        addStep("添加多个剧集");
        press_left(2);
        for(int i=0;i<10;i++) {
            addStep("添加剧集"+(i+1));
            press_left(1);
            UiObject2 AAA = phone.findObject(By.text(Pattern.compile("AAA")));
            AAA.click();
            press_right(1);
            UiObject2 video = phone.findObject(By.text(Pattern.compile("video")));
            video.click();
            sleepInt(2);
            press_left(1);
            press_right(i+1);
            sleepInt(3);
            press_menu(1);
            UiObject2 down = waitForObj(By.text(Pattern.compile("下载")));
            check("未进入菜单下载", down != null);
            down.click();
            sleepInt(2);
            UiObject2 oktg = phone.findObject(By.text("确定"));
            if(oktg!=null){
                oktg.click();
                sleepInt(2);
            }
            press_right(1);
            UiObject2 localstor =phone.findObject(extStorageDeviceSUB);
            clickAndWaitForNewWindow(localstor);
            check("未进入本机存储", localstor != null);
            sleepInt(2);
            UiObject2 look = waitForObj(By.text("立即查看"));
            check("未弹出立即查看",look!=null);
            look.click();
            sleepInt(2);
            addStep("进入下载中");
            UiObject2 download = waitForObj(By.res("eui.tv:id/textView").text(Pattern.compile("下载中")));
            check("未进入下载中心",download!=null);
            download.click();
            press_right(5);
            sleepInt(3);
            press_right(5);
            sleepInt(3);
            press_back(1);
            sleepInt(2);
        }
    }

    public void addSMBset_login() throws UiObjectNotFoundException, RemoteException {
        for(int i=0;i<2;i++) {
            addStep("进入到应用桌面");
            launchApp(AppName.Filemanager, PkgName.Filemanager);
            press_right(5);
            addStep("进入共享设备");
            UiObject2 shareDevice = waitForObj(shareDeviceS);
            clickAndWaitForNewWindow(shareDevice);
            sleepInt(2);
            UiObject2 addshare = waitForObj(By.res("com.stv.filemanager:id/text_name").text("添加设备"));
            check("未进入添加设备", addshare != null);
            addshare.click();
            sleepInt(1);
            UiObject2 addSMB = waitForObj(By.res("com.stv.filemanager:id/text_smb").text("SMB"));
            check("未进入添加设备", addSMB != null);
            addSMB.click();
            sleepInt(1);
            UiObject2 addIP = waitForObj(By.text("IP 地址"));
            check("未找到iP界面", addIP != null);
            UiObject2 addip = waitForObj(By.clazz("android.widget.EditText"));
            addip.click();
            press_center(1);
            sleepInt(1);
            UiObject2 IP = waitForObj(By.text(Pattern.compile("IP 地址"))).getParent().findObject(By.clazz("android.widget.EditText"));
            verify("can't find ip address.", IP != null);
//        IP.setText(getStrParams("IP"));
            IP.setText("10.58.81.227");
            sleepInt(2);
            press_back(1);
            sleepInt(1);
            press_down(2);

            UiObject2 ok = waitForObj(By.text("确定"));
            ok.click();
            ok.click();
            sleepInt(2);


            UiObject2 worldtext = waitForObj(By.text(Pattern.compile("请输入SMB设备.*")));
            if (worldtext != null) {
                UiObject2 userName = waitForObj(By.text(Pattern.compile("用户名.*"))).getParent().findObject(By.clazz("android.widget.EditText"));
                check("can't find userName.", userName != null);
//        userName.setText(getStrParams("USERNAME"));
                userName.setText("pc7");
                sleepInt(2);
                press_down(1);

                UiObject2 passwd1 = waitForObj(By.text("密    码")).getParent().findObject(By.clazz("android.widget.EditText"));
                check("can't find passwd.", passwd1 != null);
                sleepInt(2);
                passwd1.setText("123456");
                sleepInt(2);
                press_center(1);

                UiObject2 ok1 = waitForObj(By.text("确定"));
                if (ok1 != null) {
                    ok1.click();
                    sleepInt(2);
                } else {
                    press_down(2);
                    press_center(1);
                    sleepInt(2);
                }
            } else {
                press_center(1);
            }
        }


    }


    @Test
    @CaseName("下载中心删除")
    public void testDownloadAndDelete() throws UiObjectNotFoundException, RemoteException{
        addStep("进入到应用文件管理");
        launchApp(AppName.Filemanager, PkgName.Filemanager);
        press_down(1);
        try {
            loadAndDelete();
        }
        catch (Exception e){
            try{
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                addStep("进入到应用文件管理");
                launchApp(AppName.Filemanager, PkgName.Filemanager);
                press_down(1);
                loadAndDelete();
            }
            catch (RuntimeException re){
                screenShot();
                junit.framework.Assert.fail(re.getMessage());
            }
        }
    }
    public void loadAndDelete() throws UiObjectNotFoundException, RemoteException{
        LoadCenterAdd();
        addStep("进入下载中心");
        launchApp(AppName.Download,PkgName.Download);
        press_down(1);
        addStep("进入下载完成的");
        UiObject2 overed=phone.findObject(By.text(Pattern.compile("已完成")));
        check("未进入完成",overed!=null);
        overed.click();
        press_right(3);
        for(int i=0;i<20;i++) {
            addStep("删除下载和下载任务");
            UiObject2 overed1 = waitForObj(By.text("已完成"));
            UiObject2 delete = waitForObj(By.res("com.stv.downloads:id/delete_button"));
            check("未进行删除按钮", overed1 != null || delete != null);
            delete.click();
            UiObject2 deleteAndFile = waitForObj(By.text("删除任务和文件"));
            deleteAndFile.click();
            sleepInt(2);
            press_up(1);
            sleepInt(1);
        }
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
            press_center(2);
            sleepInt(1);
            UiObject2 download1 = waitForObj(By.text(Pattern.compile(".*下载.*")));
            check("下载按钮不存在", download1 != null);
            press_right(i+1);
        }
        addStep("退出乐视网tv版");
        press_back(4);
        exitApp();
        addStep("进入下载中心播放正在下载的视频");
        launchAppByPackage(PkgName.Download);
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
        verify("未进入到乐视网TV版", waitForExist(letvS, 15000));
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
            sleepInt(3);
        }
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


}
