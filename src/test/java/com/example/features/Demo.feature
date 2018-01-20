Feature: do welcome

  Scenario Outline: do welcome
    Given 收件人'<recip>'
    When 给收件人发送邮件'<title>','<content>'
    Then 收件人收到邮件,内容为'<content>'
    Examples:
      | recip                     | title | content             |
      | li.wenkui@zte.com.cn      | nihao | nihao liwenkui      |
      | hu.bin@zte.com.cn         | nihao | nihao hubin         |
      | zhong.miaoding@zte.com.cn | nihao | nihao zhongmiaoding |