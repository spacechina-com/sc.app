package com.sc.app.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RestTemplateUtil {

	@Autowired
	RestTemplate restTemplate;

	private String getBaseUrl(String serviceId, String restfulUrl) {
		return "http://" + serviceId + "/" + restfulUrl;
	}

	private String getBaseGetUrl(String serviceId, String restfulUrl,
			Map<String, Object> params) {
		String url = getBaseUrl(serviceId, restfulUrl);

		if (null != params && !params.isEmpty()) {
			StringBuilder templateStr = new StringBuilder();
			Iterator<Entry<String, Object>> items = params.entrySet()
					.iterator();
			while (items.hasNext()) {
				Entry<String, Object> item = items.next();
				String key = item.getKey();
				if (StringUtils.isNotBlank(key)) {
					if (templateStr.length() == 0) {
						templateStr.append(key + "={" + key + "}");
					} else {
						templateStr.append("&" + key + "={" + key + "}");
					}
				}
			}

			url += "?" + templateStr.toString();
		} else {
			params = new HashMap<String, Object>();
		}

		return url;
	}

	public <O> O post(String serviceId, String restfulUrl,
			Map<String, Object> params, Class<O> clazz) {

		if (null == params || params.isEmpty()) {
			params = new HashMap<String, Object>();
		}

		ResponseEntity<O> responseEntity = restTemplate
				.postForEntity(getBaseUrl(serviceId, restfulUrl), params,
						clazz, new Object[0]);

		return responseEntity.getBody();

	}

	public <O> O get(String serviceId, String restfulUrl,
			Map<String, Object> params, Class<O> clazz) {
		String url = getBaseGetUrl(serviceId, restfulUrl, params);
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
				.fromUriString(url);
		UriComponents uriComponents = uriComponentsBuilder.build().encode();
		if (null != params && !params.isEmpty()) {
			uriComponentsBuilder.build().expand(params).encode();
		} else {
			params = new HashMap<String, Object>();
		}

		return restTemplate.getForObject(uriComponents.toUri(), clazz);

	}

	public <T> List<T> exchange(String serviceId, String restfulUrl,
			HttpMethod method, Map<String, Object> params,
			ParameterizedTypeReference<List<T>> responseType) {
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(
				params);
		return restTemplate.exchange(getBaseUrl(serviceId, restfulUrl), method,
				requestEntity, responseType).getBody();
	}

	public <T> List<T> postForList(String serviceId, String restfulUrl,
			Map<String, Object> params,
			ParameterizedTypeReference<List<T>> responseType) {
		return exchange(serviceId, restfulUrl, HttpMethod.POST, params,
				responseType);
	}
}
