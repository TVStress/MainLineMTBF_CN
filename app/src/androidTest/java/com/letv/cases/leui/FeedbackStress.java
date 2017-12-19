package com.letv.cases.leui;


import android.media.JetPlayer;
import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;

import com.letv.common.AppName;
import com.letv.common.CaseName;
import com.letv.common.IntentConstants;
import com.letv.common.LetvTestCase;
import com.letv.common.PkgName;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;

import java.io.ObjectStreamException;
import java.util.regex.Pattern;

public class FeedbackStress extends LetvTestCase{
    int count=0;

    @Test
    @CaseName("问题反馈标签切换")
    public void testFeedbackLabel() throws ObjectStreamException,RemoteException {
        addStep("进入问题反馈");
        launchApp(AppName.Feedback, IntentConstants.Feedback);
        for(int Loop=0;Loop<getIntParams("Loop");Loop++) {
            addStep(".........Loop"+Loop);
            addStep("切换标签");
            String feedback[] = {"提交问题.*", "我的反馈.*", "常见问题.*", "联系客服.*"};
            for (int i = 0; i < feedback.length; i++) {
                press_center(1);
                press_right(1);
                press_left(1);
                press_down(1);
            }
            press_up(4);
            sleepInt(1);
        }
    }


    @Test
    @CaseName("问题反馈提交问题")
    public void testFeedbackSubmitQuestion() throws ObjectStreamException,RemoteException {
        addStep("进入问题反馈");
        launchApp(AppName.Feedback, IntentConstants.Feedback);
        for(int Loop=0;Loop<getIntParams("Loop");Loop++) {
            addStep("..........Loop"+Loop);
        try{
            SubmitQuestion();
        }
        catch(Exception e){
            try{
                count++;
                failCount(count,getIntParams("Loop"),e.getMessage());
                launchApp(AppName.Feedback, IntentConstants.Feedback);
                SubmitQuestion();
            }
            catch (RuntimeException re){
                screenShot();
                Assert.fail(re.getMessage());

                }
            }
        }
    }
    public void SubmitQuestion()throws ObjectStreamException,RemoteException{
            addStep("进入提交问题");
            String[] feedback = {"提交问题.*", "我的反馈.*", "常见问题.*", "联系客服.*"};
            UiObject2 submitQuestion = waitForObj(By.text(Pattern.compile(feedback[0])));
            check("未进入提交问题", submitQuestion != null);
            submitQuestion.click();
            sleepInt(1);
            press_right(2);
            sleepInt(1);
            addStep("问题描述");
            UiObject2 questiontext = waitForObj(By.clazz("android.widget.EditText"));
            check("can't find userName.", questiontext != null);
            questiontext.setText("测试问题反馈");
            sleepInt(1);
            press_down(1);
            UiObject2 phototext = waitForObj(By.text("联系方式")).getParent().findObject(By.clazz("android.widget.EditText"));
            check("can't find userName.", phototext != null);
            phototext.setText("0100000");
            sleepInt(2);
            press_down(2);
            addStep("提交问题");
            UiObject2 questionok = waitForObj(By.text("提交"));
            questionok.click();
            questionok.click();
            sleepInt(2);
//            press_right(1);
//            UiObject2 exitquestion = waitForObj(By.text("我的反馈"));
//            exitquestion.click();
//            sleepInt(1);
//            press_left(1);
//            UiObject2 mysubmit = waitForObj(By.text(Pattern.compile(feedback[1])));
//            check("未进入提交问题", mysubmit != null);
//            mysubmit.click();
//            sleepInt(1);
            press_left(2);
    }


    @Test
    @CaseName("问题反馈我的反馈")
    public void testMySubitQuestion()throws ObjectStreamException, RemoteException {
        addStep("进入问题反馈");
        launchApp(AppName.Feedback, IntentConstants.Feedback);
        for(int Loop=0;Loop<getIntParams("Loop");Loop++) {
            addStep("..........Loop"+Loop);
            try{
                MySubitQuestion();
            }
            catch(Exception e){
                try{
                    count++;
                    failCount(count,getIntParams("Loop"),e.getMessage());
                    launchApp(AppName.Feedback, IntentConstants.Feedback);
                    MySubitQuestion();
                }
                catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());

                }
            }
        }

    }
    public void MySubitQuestion()throws ObjectStreamException, RemoteException{
        addStep("进入我的反馈");
        String[] feedback = {"提交问题.*", "我的反馈.*", "常见问题.*", "联系客服.*"};
        UiObject2 mysubmit = waitForObj(By.text(Pattern.compile(feedback[1])));
        check("未进入我的反馈", mysubmit != null);
        mysubmit.click();
        mysubmit.click();
        sleepInt(2);
        press_right(2);
        sleepInt(1);
        addStep("查出我的反馈状态");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                press_center(1);
                sleepInt(1);
                press_back(1);
                UiObject2 myfeedback=waitForObj(By.text("我的反馈"));
                check(myfeedback!=null);
                sleepInt(1);
                press_right(1);
                sleepInt(1);
            }
            press_left(1);
            sleepInt(1);
            press_down(1);
            sleepInt(1);
        }
        press_left(3);
    }

    @Test
    @CaseName("问题反馈我的反馈")
    public void testCommonProblem()throws ObjectStreamException, RemoteException {
        addStep("进入问题反馈");
        launchApp(AppName.Feedback, IntentConstants.Feedback);
        for(int Loop=0;Loop<getIntParams("Loop");Loop++) {
            addStep("..........Loop"+Loop);
            try{
                CommonProblem();
            }
            catch(Exception e){
                try{
                    count++;
                    failCount(count,getIntParams("Loop"),e.getMessage());
                    launchApp(AppName.Feedback, IntentConstants.Feedback);
                    CommonProblem();
                }
                catch (RuntimeException re){
                    screenShot();
                    Assert.fail(re.getMessage());

                }
            }
        }

    }
    public void CommonProblem()throws ObjectStreamException,RemoteException{
        addStep("进入常见问题");
        String[] feedback = {"提交问题.*", "我的反馈.*", "常见问题.*", "联系客服.*"};
        UiObject2 commonproblem = waitForObj(By.text(Pattern.compile(feedback[2])));
        check("未进入我的反馈", commonproblem != null);
        commonproblem.click();
        commonproblem.click();
        sleepInt(2);
        press_right(2);
        sleepInt(1);
        addStep("查看常见问题详情");
        for(int i=0;i<6;i++) {
            UiObject2 lookparticular = waitForObj(By.text("查看详情"));
            check("未找到查看详情", lookparticular != null);
            press_center(1);
            sleepInt(1);
            UiObject2 colse1 = waitForObj(By.text("关闭"));
            check("未弹出关闭按钮", colse1 != null);
            colse1.click();
            sleepInt(2);
            press_down(1);
            sleepInt(1);
        }
        press_left(2);
    }
}
