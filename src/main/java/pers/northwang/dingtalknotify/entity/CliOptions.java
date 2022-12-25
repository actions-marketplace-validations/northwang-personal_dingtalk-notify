package pers.northwang.dingtalknotify.entity;

public class CliOptions {

  private String accessToken;

  private String secret;

  private String body;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return "CliOptions{" +
      "accessToken='" + accessToken + '\'' +
      ", secret='" + secret + '\'' +
      ", body='" + body + '\'' +
      '}';
  }
}
