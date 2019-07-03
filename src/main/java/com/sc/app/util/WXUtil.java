package com.sc.app.util;

import net.sf.json.JSONObject;

public class WXUtil {

	private String appId;

	private String appSecret;

	public WXUtil(String appId, String appSecret) {
		this.appId = appId;
		this.appSecret = appSecret;
	}

	public String token() {
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

	public String openId(String code) {
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

	public JSONObject sendMessage(String message, String openID) {
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

	public JSONObject user(String openID) {
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
