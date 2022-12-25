package pers.northwang.dingtalknotify.entity;

public class CliOptions {

  private String accessToken;

  private String secret;

  private String payload;

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

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  @Override
  public String toString() {
    return "CliOptions{" +
      "accessToken='" + accessToken + '\'' +
      ", secret='" + secret + '\'' +
      ", body='" + payload + '\'' +
      '}';
  }
}
