package com.wfy.server.utils.cmc;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
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
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
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
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE)
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

	public static String post(String reqURL, String requestBody) throws Exception {
		HttpPost httpPost = new HttpPost(reqURL);
		StringEntity myEntity = new StringEntity(requestBody, Consts.UTF_8);
		httpPost.setEntity(myEntity);
		return doRequest(httpPost);
	}

	public static String post(String reqURL, InputStream requestContent) throws Exception {
		HttpPost httpPost = new HttpPost(reqURL);
		InputStreamEntity myEntity = new InputStreamEntity(requestContent);
		httpPost.setEntity(myEntity);
		return doRequest(httpPost);
	}

	public static String put(String reqURL, String requestBody) throws Exception {
		HttpPut httpPut = new HttpPut(reqURL);
		StringEntity myEntity = new StringEntity(requestBody, Consts.UTF_8);
		httpPut.setEntity(myEntity);
		return doRequest(httpPut);
	}

	public static String put(String reqURL, InputStream requestContent) throws Exception {
		HttpPut httpPut = new HttpPut(reqURL);
		InputStreamEntity myEntity = new InputStreamEntity(requestContent);
		httpPut.setEntity(myEntity);
		return doRequest(httpPut);
	}

	private static String doRequest(HttpRequestBase request) throws Exception {
		String responseContent = null;
		request.addHeader("Content-Type", "application/json; charset=utf-8");
		CloseableHttpResponse response = httpclient.execute(request);
		try {
			try {
				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					responseContent = response.getStatusLine().getReasonPhrase();
					System.out.println(EntityUtils.toString(response.getEntity(), Consts.UTF_8));
				} else {
					HttpEntity entity = response.getEntity();
					try {
						if (null != entity) {
							responseContent = EntityUtils.toString(entity, Consts.UTF_8);
						}
					} finally {
						if (entity != null) {
							entity.getContent().close();
						}
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
		return responseContent;
	}

	public static String get(String reqURL) throws Exception {
		HttpGet httpGet = new HttpGet(reqURL);
		return doRequest(httpGet);
	}

	public static String get(String reqURL, Map<String, String> headers) throws Exception {
		HttpGet httpGet = new HttpGet(reqURL);
		for (Map.Entry<String, String> ent : headers.entrySet()) {
			httpGet.addHeader(ent.getKey(), ent.getValue());
		}
		return doRequest(httpGet);
	}

	public static String delete(String reqURL) throws Exception {
		HttpDelete httpDelete = new HttpDelete(reqURL);
		return doRequest(httpDelete);
	}

	public static String uploadFile(String reqURL, String path, String fileName) throws Exception {
		String responseContent = null;
		HttpPost httppost = new HttpPost(reqURL);
		httppost.addHeader("Content-Type", "image/jpeg");
		try {
			FileBody bin = new FileBody(new File(path + "/" + fileName));
			StringBody comment = new StringBody(fileName);
			MultipartEntity reqEntity = new MultipartEntity();
			reqEntity.addPart("filename", comment);
			reqEntity.addPart("file", bin);
			httppost.setEntity(reqEntity);

			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				try {
					if (null != entity) {
						responseContent = EntityUtils.toString(entity, Consts.UTF_8);
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
			httppost.releaseConnection();
		}
		return responseContent;
	}

	public static String ossPost(String reqURL, String requestBody) throws Exception {
		HttpPost httpPost = new HttpPost(reqURL);
		StringEntity myEntity = new StringEntity(requestBody, Consts.UTF_8);
		httpPost.setEntity(myEntity);
		return doRequest(httpPost);
	}

	public static boolean uploadToOss(String url, Map<String, String> params, List<File> files) throws Exception {
		boolean ret = false;
		HttpURLConnection con;
		OutputStream os;
		String delimiter = "--";
		String boundary = "SwA" + Long.toString(System.currentTimeMillis()) + "SwA";
		try {
			con = (HttpURLConnection) (new URL(url)).openConnection();
			con.setRequestMethod("POST");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charsert", "UTF-8");
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			// con.setRequestProperty("Authorization", AUTH_HEADER_VALUE);
			con.connect();
			os = con.getOutputStream();

			for (String paramName : params.keySet()) {
				os.write((delimiter + boundary + "\r\n").getBytes());
				os.write(("Content-Disposition: form-data; name=\"" + paramName + "\"" + "\r\n").getBytes());
				os.write(("\r\n" + params.get(paramName) + "\r\n").getBytes());
			}
			for (File file : files) {
				os.write((delimiter + boundary + "\r\n").getBytes());
				os.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" + params.get("key") + "\"" + "\r\n").getBytes());
				os.write(("Content-Type: " + params.get("Content-Type") + "\r\n").getBytes());
				os.write("\r\n".getBytes());
				InputStream is = new FileInputStream(file);
				logger.info("file.exists()=" + file.exists());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					os.write(buffer, 0, len);
				}
				is.close();
				os.write("\r\n".getBytes());
			}
			os.write((delimiter + boundary + delimiter + "\r\n").getBytes());
			os.flush();
			os.close();
			// 得到响应
			int statusCode = con.getResponseCode();
			logger.info("requestUrl:{}, statusCode:{}, ResponseMessage:{}.", url, statusCode, con.getResponseMessage());
			if (statusCode >= 200 && statusCode < 400) {
				ret = true;
			}
			con.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} catch (ProtocolException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return ret;
	}

}
