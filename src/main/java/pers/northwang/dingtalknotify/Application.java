package pers.northwang.dingtalknotify;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.codec.binary.Base64;
import pers.northwang.dingtalknotify.entity.CliOptions;
import pers.northwang.dingtalknotify.func.RequestBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Application {

  private final static String DOC_URL = "https://open.dingtalk.com/document/group/custom-robot-access";

  public static void main(String[] args) {
    try {
      run(args);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private static void run(String[] args) throws Exception {
    var cliOptions = getArgs(args);
    String webHook;
    if (isBlank(cliOptions.getSecret())) {
      webHook = getWebHook(cliOptions.getAccessToken());
    } else {
      webHook = getWebHook(cliOptions.getAccessToken(), cliOptions.getSecret());
    }
    sendNotification(webHook).params(cliOptions.getBody());
  }

  private static boolean isBlank(String str) {
    return str == null || "".equals(str.replaceAll(" ", ""));
  }

  private static CliOptions getArgs(String[] args) {
    var accessToken = new Option("accessToken", true, "Dingtalk webhook access token");
    accessToken.setRequired(true);

    var secret = new Option("secret", true, "Dingtalk webhook secret for sign");
    secret.setRequired(false);

    var payload = new Option("payload", true, "Dingtalk webhook request json style body. More usage to see " + DOC_URL);
    payload.setRequired(true);

    var options = new Options();
    options.addOption(accessToken);
    options.addOption(secret);
    options.addOption(payload);

    var cliOptions = new CliOptions();
    var parser = new DefaultParser();
    var helper = new HelpFormatter();
    try {
      var cli = parser.parse(options, args);
      for (var option : cli.getOptions()) {
        var clazz = cliOptions.getClass();
        var field = clazz.getDeclaredField(option.getOpt());
        field.setAccessible(true);
        field.set(cliOptions, option.getValue());
      }
    } catch (Exception e) {
      e.printStackTrace();
      helper.printHelp("->", options, true);
    }

    return cliOptions;
  }

  private static String getSign(String secret, long timestamp) throws Exception {
    var stringToSign = timestamp + "\n" + secret;
    var mac = Mac.getInstance("HmacSHA256");
    mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
    var signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
    return URLEncoder.encode(new String(Base64.encodeBase64(signData)), StandardCharsets.UTF_8);
  }

  private static String getWebHook(String accessToken, String secret) throws Exception {
    var timestamp = System.currentTimeMillis();
    var sign = getSign(secret, timestamp);
    var url = "https://oapi.dingtalk.com/robot/send";
    return url + "?access_token=" + accessToken + "&sign=" + sign + "&timestamp=" + timestamp;
  }

  private static String getWebHook(String accessToken) {
    var url = "https://oapi.dingtalk.com/robot/send";
    return url + "?access_token=" + accessToken;
  }

  private static RequestBuilder<String> sendNotification(String webHook) {
    var httpClient = new OkHttpClient();
    return payload -> {
      var reqBody = RequestBody.create(payload, MediaType.get("application/json"));
      var request = new Request.Builder().url(webHook).post(reqBody).build();

      try (var response = httpClient.newCall(request).execute()) {
        var body = response.body();
        response.close();
        if (!response.isSuccessful() || body == null) {
          throw new Exception(response.message());
        }
        System.out.println(body.string());
      }
    };
  }
}
