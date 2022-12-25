# Usage

```yml
steps:
  - name: Send dingtalk notification
    uses: northwang-personal/dingtalk-notify@latest
    with:
      # (Required) DingTalk webhook access token
      access_token: xxxxxxxxxxxx
      # (Required) DingTalk webhook request json style body. More usage to see https://open.dingtalk.com/document/group/custom-robot-access
      payload: >-
        {
          "at": {
            "atMobiles":["180xxxxxx"],
            "atUserIds":["user123"],
            "isAtAll": false
          },
          "text": {"content":"I am me, @XXX is a different firework"},
          "msgtype":"text"
        }
      # (Optional) DingTalk webhook secret for sign
      secret: xxxxxxxxxxxx
```