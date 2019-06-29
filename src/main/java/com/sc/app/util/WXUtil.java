package com.sc.app.util;

import net.sf.json.JSONObject;

public class WXUtil {

	private static String appId = "wx1927131d08fe2a04";

	private static String appSecret = "2f0d28354ccb7290ca1ac16c4dda02e3";

	public static String token() {
		String rs = null;
		try {
			String str = HttpUtils.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
					+ appId + "&secret=" + appSecret);
			JSONObject obj = JSONObject.fromObject(str);
			rs = obj.getString("access_token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static String openId(String code) {
		String rs = null;
		try {
			String str = HttpUtils.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret="
					+ appSecret + "&code=" + code + "&grant_type=authorization_code");
			JSONObject obj = JSONObject.fromObject(str);
			rs = obj.getString("openid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static JSONObject sendMessage(String message, String openID) {
		String token = token();
		String json = "{\"touser\": \"" + openID + "\",\"msgtype\": \"text\", \"text\": {\"content\": \"" + message
				+ "\"}}";
		JSONObject rs = null;
		try {
			String str = HttpUtils.post("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token,
					json);
			rs = JSONObject.fromObject(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static JSONObject user(String openID) {
		String token = token();
		JSONObject rs = null;
		try {
			String str = HttpUtils.get("https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + token + "&openid="
					+ openID + "&lang=zh_CN");
			rs = JSONObject.fromObject(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
}
