package com.wfy.server.utils.cmc;

import com.fhic.business.server.vo.HttpResponseVo;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class HttpClientUtilWithStatus {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtilWithStatus.class);
	private static PoolingHttpClientConnectionManager connManager = null;
	private static CloseableHttpClient httpclient = null;
	private static int MAX_CONNECTIONS = 100;

	static {
		try {
			SSLContext sslContext = SSLContexts.custom().useTLS().build();
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} }, null);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", PlainConnectionSocketFactory.INSTANCE)
					.register("https", new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)).build();

			connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
					.build();
			connManager.setDefaultConnectionConfig(connectionConfig);
			connManager.setMaxTotal(100);
			connManager.setDefaultMaxPerRoute(MAX_CONNECTIONS);
			httpclient = HttpClients.custom().setConnectionManager(connManager).build();

		} catch (KeyManagementException e) {
			logger.error("KeyManagementException", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException", e);
		}
	}

	public static HttpResponseVo post(String reqURL, String requestBody) throws Exception {
		HttpPost httpPost = new HttpPost(reqURL);
		StringEntity myEntity = new StringEntity(requestBody, Consts.UTF_8);
		httpPost.setEntity(myEntity);
		return doRequest(httpPost);

	}

	public static HttpResponseVo post(String reqURL, InputStream requestContent) throws Exception {
		HttpPost httpPost = new HttpPost(reqURL);
		InputStreamEntity myEntity = new InputStreamEntity(requestContent);
		httpPost.setEntity(myEntity);
		return doRequest(httpPost);
	}

	public static HttpResponseVo put(String reqURL, String requestBody) throws Exception {
		HttpPut httpPut = new HttpPut(reqURL);
		StringEntity myEntity = new StringEntity(requestBody, Consts.UTF_8);
		httpPut.setEntity(myEntity);
		return doRequest(httpPut);
	}

	private static HttpResponseVo doRequest(HttpRequestBase request) throws Exception {
		HttpResponseVo chrv = new HttpResponseVo();

		request.addHeader("Content-Type", "application/json; charset=utf-8");
		CloseableHttpResponse response = httpclient.execute(request);
		try {
			try {
				chrv.setResponseCode(response.getStatusLine().getStatusCode());

				HttpEntity entity = response.getEntity();
				try {
					if (null != entity) {
						String responseContent = EntityUtils.toString(entity, Consts.UTF_8);
						chrv.setResponseStr(responseContent);
					}
				} finally {
					if (entity != null) {
						entity.getContent().close();
					}
				}
			} finally {
				if (response != null) {
					response.close();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			request.releaseConnection();
		}
		return chrv;
	}

	public static HttpResponseVo put(String reqURL, InputStream requestContent) throws Exception {
		HttpPut httpPut = new HttpPut(reqURL);
		InputStreamEntity myEntity = new InputStreamEntity(requestContent);
		httpPut.setEntity(myEntity);
		return doRequest(httpPut);

	}

	public static HttpResponseVo get(String reqURL) throws Exception {
		HttpGet httpGet = new HttpGet(reqURL);
		return doRequest(httpGet);
	}

	public static HttpResponseVo delete(String reqURL) throws Exception {
		HttpDelete httpDelete = new HttpDelete(reqURL);
		return doRequest(httpDelete);
	}
}
