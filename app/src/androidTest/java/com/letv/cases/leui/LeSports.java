package com.letv.cases.leui;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiWatcher;

import com.letv.common.CaseName;
import com.letv.common.LetvTestCase;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Created by zhoujine on 2016/6/8.
 */
public class LeSports extends LetvTestCase{
    int count=0;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        phone.registerWatcher("nexTime", nexTime);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        phone.removeWatcher("nexTime");
    }
    private final UiWatcher nexTime = new UiWatcher() {
        public boolean checkForCondition() {
            UiObject2 nextTime = phone.findObject(By.text(Pattern.compile("下次再说|暂不领取")));
            if (nextTime != null) {
                nextTime.click();
                nextTime.click();
                sleepInt(5);
                return true;
            } else {
                return false;
            }
        }
    };
    @Test
    @CaseName("乐视体育观看热门视频")
    public void testVideoSports() throws UiObjectNotFoundException {
        gotoHomeScreen("体育");
        addStep("进入体育桌面的比赛大厅");
        UiObject2 recommandVideo=waitForObj(By.res("com.lesports.launcher:id/recommend_img_lesports"));
        verify("没有比赛大厅入口", recommandVideo != null);
        recommandVideo.click();
        recommandVideo.click();
        sleepInt(4);
        UiObject2 fetch=phone.findObject(By.text(Pattern.compile("暂不领取|下次再说")));
        if(fetch!=null){
            fetch.click();
            fetch.click();
            sleepInt(1);
        }
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            addStep(".............looper : " + Loop);
            try {
                UiObject2 update=waitForObj(By.text("立即升级"));
                if (update!=null){
                    update.click();
                }
                VideoSports();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("体育");
                    addStep("进入体育桌面的热门视频");
                    UiObject2 recommandVideo1=waitForObj(By.res("com.lesports.launcher:id/lesports_entrance1"));
                    check("没有热门视频入口", recommandVideo1 != null);
                    recommandVideo1.click();
                    recommandVideo1.click();
                    sleepInt(4);
                    UiObject2 fetch1=phone.findObject(By.text(Pattern.compile("暂不领取|下次再说")));
                    if(fetch1!=null){
                        fetch1.click();
                        fetch1.click();
                        sleepInt(1);
                    }
                    VideoSports();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
        press_back(3);
        exitApp();
        verify("未返回桌面",phone.getCurrentPackageName().equals(PACKAGE_HOME));
    }

    public void VideoSports() throws UiObjectNotFoundException {
        addStep("播放下一个视频1分钟");
        UiObject2 homePage=waitForObj(By.text("首页"));
        check("未返回乐视体育首页", homePage != null);
        homePage.click();
        homePage.click();
        sleepInt(1);
        press_down(1);
        press_right(1);
        press_center(1);
        UiObject2 player=waitForObj(By.res("com.lesports.tv:id/lesports_player_content_layout"));
        check("没有找到视频播放预览窗口", player != null);
        player.click();
        player.click();
        sleepInt(30);
        press_center(1);
        sleepInt(2);
        UiObject2 pause = waitForObj(By.res("com.lesports.tv:id/play_pause_pic"));
        if(pause!=null){
            addStep("暂停，播放视频");
            press_center(1);
        }
        sleepInt(60);
        press_back(1);
        UiObject2 player1 = waitForObj(By.res("com.lesports.tv:id/lesports_player_content_layout"));
        check("未返回到视频播放小窗口", player1 != null);
        for(int i=0;i<3;i++){
            UiObject2 homePage1=phone.findObject(By.text("首页"));
            if(homePage1==null){
                press_back(1);
            }else break;
        }
    }

    @Test
    @CaseName("乐视体育遍历各菜单并观看比赛")
    public void testMenuView() throws UiObjectNotFoundException {
        gotoHomeScreen("体育");
        addStep("进入体育桌面的比赛大厅");
        UiObject2 videoHot=waitForObj(By.res("com.lesports.launcher:id/recommend_img_lesports"));
        verify("没有比赛大厅入口", videoHot != null);
        videoHot.click();
        videoHot.click();
        sleepInt(4);
        UiObject2 fetch=phone.findObject(By.text("暂不领取"));
        if(fetch!=null){
            fetch.click();
            fetch.click();
            sleepInt(1);
        }
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                MenuView();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("体育");
                    addStep("进入体育桌面的比赛大厅");
                    UiObject2 videoHot1=waitForObj(By.res("com.lesports.launcher:id/lesports_entrance1"));
                    check("没有热门视频入口", videoHot1 != null);
                    videoHot1.click();
                    videoHot1.click();
                    sleepInt(4);
                    UiObject2 fetch1=phone.findObject(By.text("暂不领取"));
                    if(fetch1!=null){
                        fetch1.click();
                        fetch1.click();
                        sleepInt(1);
                    }
                    MenuView();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
            }
        }

    public void MenuView() throws UiObjectNotFoundException {
        addStep("左右切换菜单10次");
        UiObject2 homePage=waitForObj(By.text("首页"));
        check("未找到首页菜单", homePage != null);
        homePage.click();
        sleepInt(5);
        check("未找到首页菜单", homePage.isSelected());
        press_right(8);
        press_left(8);
        UiObject2 homePage1=waitForObj(By.text("首页"));
        check("未找到首页菜单", homePage1.isSelected());
        UiObject2 competation=waitForObj(By.text("比赛大厅"));
        check("未找到首页菜单", competation != null);
        competation.click();
        competation.click();
        sleepInt(1);
        check("未找到首页菜单", competation.isSelected());
        UiObject2 all=waitForObj(By.text("全部"));
        all.click();
        all.click();
        sleepInt(2);
        int count=waitForObj(By.res("com.lesports.tv:id/hall_menu_button_container")).getChildCount();
        for(int j=0;j<count-3;j++){
            press_right(1);
            press_center(1);
            sleepInt(1);
        }
        UiObject2 recommand=waitForObj(By.text("推荐"));
        check("没有找到推荐项",recommand!=null);
        recommand.click();
        recommand.click();
        sleepInt(1);
        press_down(2);
        press_center(1);
        sleepInt(30);
        for(int k=0;k<4;k++){
            UiObject2 homePage3=phone.findObject(By.text("首页"));
            if(homePage3==null){
                press_back(1);
                sleepInt(1);
            }else break;
        }
    }

    @Test
    @CaseName("体育桌面反复播放热门比赛视频")
    public void testPlayHot() throws UiObjectNotFoundException {
        gotoHomeScreen("体育");
        for (int Loop = 0; Loop < getIntParams("Loop"); Loop++) {
            System.out.println(".............looper : " + Loop);
            try {
                PlayHot();
            }catch (Exception e){
                try {
                    count ++;
                    failCount(count, getIntParams("Loop"), e.getMessage());
                    gotoHomeScreen("体育");
                    PlayHot();
                }catch (RuntimeException re){
                    screenShot();
                    junit.framework.Assert.fail(re.getMessage());
                }
            }
        }
        addStep("返回体育桌面");
        press_back(3);
        exitApp();
        verify("未返回桌面", phone.getCurrentPackageName().equals(PACKAGE_HOME));
    }

    public void PlayHot() throws UiObjectNotFoundException {
        addStep("进入体育桌面的播放推荐比赛");
        UiObject2 videoHot=waitForObj(By.res("com.lesports.launcher:id/recommend_img_lesports"));
        check("没有体育大厅入口", videoHot != null);
        videoHot.click();
        videoHot.click();
        sleepInt(5);
        UiObject2 recommend=waitForObj(By.text("首页"));
        verify("failed to found recommend butten",recommend!=null);
        recommend.click();
        recommend.click();
        press_down(1);
        press_right(1);
        press_center(1);
        addStep("播放视频1分钟");
        UiObject2 player=waitForObj(By.res("com.lesports.tv:id/lesports_player_ui_layout"));
        check("没有找到视频播放预览窗口", player != null);
        player.click();
        player.click();
        sleepInt(60);
        press_back(1);
        UiObject2 player1 = waitForObj(By.res("com.lesports.tv:id/lesports_player_ui_layout"));
        check("未返回到视频播放小窗口", player1 != null);
        addStep("返回到体育桌面");
        press_home(1);
    }
}