package cn.cpf.app.util;

import com.github.cosycode.common.lang.ShouldNotHappenException;
import com.github.cosycode.common.util.io.FileSystemUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.Objects;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;

/**
 * <b>Description : </b>
 * <p>
 * <b>created in </b> 2021/4/19
 *
 * @author CPF
 * @since
 **/
public class HttpUtils {


    /**
     * CURL 发送请求
     *
     * @param url
     * @return
     */
    public static String sendGetRequest(String url) {
        HttpGet httpGet = new HttpGet(url);
        String result = null;
        int status = 0;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build(); CloseableHttpResponse response = httpClient.execute(httpGet)) {
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            // 响应状态
            status = response.getStatusLine().getStatusCode();
            // 响应结果
            result = EntityUtils.toString(responseEntity);
            if (status != 200) {
                result = null;
            }
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static boolean download(String url, File savePath, LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(savePath);
        CloseableHttpClient client = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
                .build();
        // cookie时间可能会出错，设置下
        HttpGet httpget = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpget);
            HttpEntity entity = response.getEntity();
            // 文件大小
            final long contentLength = entity.getContentLength();
            if (!savePath.exists()) {
                FileSystemUtils.insureFileDirExist(savePath.getParentFile());
            }
            try (InputStream is = entity.getContent(); OutputStream outputStream = new FileOutputStream(savePath)) {
                // 根据实际运行效果 设置缓冲区大小
                final int cache = 8 * 1024;
                byte[] buffer = new byte[cache];
                long porcess = 0;
                int ch = -1;
                while ((ch = is.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, ch);
                    porcess += ch;
                    longBinaryOperator.applyAsLong(contentLength, porcess);
                }
            } catch (FileNotFoundException e) {
                throw new ShouldNotHappenException(e);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 从输入流转向输出流
     *
     * @param inputStream
     * @param outputStream
     * @param longConsumer
     * @throws IOException
     */
    public static void streamTransfer(InputStream inputStream, OutputStream outputStream, LongConsumer longConsumer) throws IOException {
        // 根据实际运行效果 设置缓冲区大小
        final int cache = 8 * 1024;
        byte[] buffer = new byte[cache];
        long porcess = 0;
        int ch = -1;
        while ((ch = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, ch);
            porcess += ch;
            longConsumer.accept(porcess);
        }
    }


}
