package com.example.demo.CONST;

public interface Constant {
    String secret = "abcdefghijklmnOPQRSTUVWXYZ";
    String cookiename = "opentalk";

    long maxAge = 15 * 60 * 1000;

    int refreshExpirationDateInMs = 9000000;

    String jobGroup = "email-jobs";

    String jobDescription = "Send Email Job";

    String triggerGroup = "Quartz_Trigger";

    String triggerDescription = "Email trigger";

    String LANGUAGE_TAG = "Asia/Ho_Chi_Minh";

    String RECIPIENT = "recipient(s)";

    String NAME_OF_RECIPIENT = "name";

    String TOPIC = "topic";
    String cronExpression = "0 0 14-18 ? * WED *";

       String cronTest = "0/30 * * * * ? *";
//    String cronTest = "* * * * * ? *";
    String template = "template";
}
