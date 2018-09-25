package com.avalon.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>Http工具类
 * 
 * <p>Http工具类，为系统提供通用Http访问操作方法：
 * 
 * <p>1、发送GET请求；
 * <p>2、发送POST请求。
 * 
 */
public class HttpUtil {
    private static final Logger log = LogManager.getLogger(HttpUtil.class);

    /**
     * <p>发送GET请求 
     * @param  url GET请求地址
     * @return 与当前请求对应的响应内容字节数组
     */
    public static byte[] doGet(String url) {

        return HttpUtil.doGet(url, null, null, 0);
    }

    /**
     * <p>发送GET请求
     * @param  url       GET请求地址
     * @param  headerMap GET请求头参数容器
     * @return 与当前请求对应的响应内容字节数组 
     */
    public static byte[] doGet(String url, Map<String, String> headerMap) {
        return HttpUtil.doGet(url, headerMap, null, 0);
    }

    /**
     * <p>发送GET请求
     * @param  url       GET请求地址
     * @param  proxyUrl  代理服务器地址
     * @param  proxyPort 代理服务器端口号
     * @return 与当前请求对应的响应内容字节数组
     */
    public static byte[] doGet(String url, String proxyUrl, int proxyPort) {
        return HttpUtil.doGet(url, null, proxyUrl, proxyPort);
    }

    /**
     * <p>发送GET请求
     * @param  url       GET请求地址
     * @param  headerMap GET请求头参数容器
     * @param  proxyUrl  代理服务器地址
     * @param  proxyPort 代理服务器端口号
     * @return 与当前请求对应的响应内容字节数组
     */
    public static byte[] doGet(String url, Map<String, String> headerMap, String proxyUrl,
                               int proxyPort) {

        byte[] content = null;
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);

        if (headerMap != null) {

            //头部请求信息
            if (headerMap != null) {

                Iterator<Entry<String, String>> iterator = headerMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, String> entry = iterator.next();
                    getMethod.addRequestHeader(entry.getKey(), entry.getValue());
                }
            }
        }

        if (StringUtils.isNotBlank(proxyUrl)) {

            httpClient.getHostConfiguration().setProxy(proxyUrl, proxyPort);
        }

        //设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);
        //postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER , new DefaultHttpMethodRetryHandler());
        InputStream inputStream = null;
        try {

            if (httpClient.executeMethod(getMethod) == HttpStatus.SC_OK) {

                //读取内容
                inputStream = getMethod.getResponseBodyAsStream();
                content = IOUtils.toByteArray(inputStream);
            } else {
                log.error("Method failed: " + getMethod.getStatusLine());
            }
        } catch (IOException ex) {
            log.error(ex);
        } finally {
            IOUtils.closeQuietly(inputStream);
            getMethod.releaseConnection();
        }
        return content;
    }

    /**
     * <p>发送POST请求
     * @param  url          POST请求地址
     * @param  parameterMap POST请求参数容器
     * @return 与当前请求对应的响应内容字节数组
     */
    public static byte[] doPost(String url, Map<String, String> parameterMap) {
        return HttpUtil.doPost(url, null, parameterMap, null, null, 0);
    }

    /**
     * <p>发送POST请求
     * 
     * @param  url          POST请求地址
     * @param  parameterMap POST请求参数容器
     * @param  paramCharset 参数字符集名称
     * @return 与当前请求对应的响应内容字节数组 
     */
    public static byte[] doPost(String url, Map<String, String> parameterMap, String paramCharset) {
        return HttpUtil.doPost(url, null, parameterMap, paramCharset, null, 0);
    }

    /**
     * <p>发送POST请求
     * 
     * @param  url          POST请求地址
     * @param  headerMap    POST请求头参数容器
     * @param  parameterMap POST请求参数容器
     * @param  paramCharset 参数字符集名称
     * 
     * @return 与当前请求对应的响应内容字节数组
     * 
     * @modify 窦海宁, 2012-05-21
     */
    public static byte[] doPost(String url, Map<String, String> headerMap,
                                Map<String, String> parameterMap, String paramCharset) {
        return HttpUtil.doPost(url, headerMap, parameterMap, paramCharset, null, 0);
    }

    /**
     * <p>发送POST请求
     * 
     * @param  url          POST请求地址
     * @param  parameterMap POST请求参数容器
     * @param  paramCharset 参数字符集名称
     * @param  proxyUrl     代理服务器地址
     * @param  proxyPort    代理服务器端口号
     * 
     * @return 与当前请求对应的响应内容字节数组
     * 
     */
    public static byte[] doPost(String url, Map<String, String> parameterMap, String paramCharset,
                                String proxyUrl,
                                int proxyPort) {
        return HttpUtil.doPost(url, null, parameterMap, paramCharset, proxyUrl, proxyPort);
    }

    /**
     * <p>发送POST请求
     * 
     * @param  url          POST请求地址
     * @param  headerMap    POST请求头参数容器
     * @param  parameterMap POST请求参数容器
     * @param  paramCharset 参数字符集名称
     * @param  proxyUrl     代理服务器地址
     * @param  proxyPort    代理服务器端口号
     * 
     * @return 与当前请求对应的响应内容字节数组
     * 
     * @modify 窦海宁, 2012-05-21
     */
    public static byte[] doPost(String url, Map<String, String> headerMap,
                                Map<String, String> parameterMap, String paramCharset,
                                String proxyUrl, int proxyPort) {

        byte[] content = null;
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);

        if (StringUtils.isNotBlank(paramCharset)) {

            postMethod.getParams().setContentCharset(paramCharset);
            postMethod.getParams().setHttpElementCharset(paramCharset);
        }

        if (headerMap != null) {
            //头部请求信息
            if (headerMap != null) {
                Iterator<Entry<String, String>> iterator = headerMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, String> entry = iterator.next();
                    postMethod.addRequestHeader(entry.getKey(), entry.getValue());
                }
            }
        }

        Iterator<String> iterator = parameterMap.keySet().iterator();
        while (iterator.hasNext()) {

            String key = iterator.next();
            postMethod.addParameter(key, parameterMap.get(key));
        }

        if (StringUtils.isNotBlank(proxyUrl)) {

            httpClient.getHostConfiguration().setProxy(proxyUrl, proxyPort);
        }

        //设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);
        //postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER , new DefaultHttpMethodRetryHandler());
        InputStream inputStream = null;
        try {

            if (httpClient.executeMethod(postMethod) == HttpStatus.SC_OK) {

                //读取内容
                inputStream = postMethod.getResponseBodyAsStream();
                content = IOUtils.toByteArray(inputStream);
            } else {
                log.error("Method failed: " + postMethod.getStatusLine());
            }
        } catch (IOException ex) {
            log.error(ex);
            ex.printStackTrace();
        } finally {

            IOUtils.closeQuietly(inputStream);
            postMethod.releaseConnection();
        }
        return content;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("wd", "nima");
        byte[] b = doGet("http://www.baidu.com", map);
        System.out.println("-------------------" + new String(b));
    }

}