package cn.cpf.app.global;

import cn.cpf.app.util.HttpUtils;
import com.google.gson.Gson;
import lombok.Data;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <b>Description : </b> 升级
 * <p>
 *     1. 检查是否是最新版
 *     2. 下载最新版本安装包
 *     3. 关闭当前程序, 由系统运行cmd命令完成文件替换操作.
 *     4. 退出
 *
 * <b>created in </b> 2021/4/19
 *
 * @author CPF
 * @since 1.0
 **/
public class AppUpdateHelper {

    /**
     * 当前版本号
     */
    public static final String CURRENT_VERSION = "0.1";

    /**
     * 获取当前最新版本的地址
     */
    private static final String LATEST_VERSION_SELECT_URL = "https://api.github.com/repos/cosycode/common-lang/releases/latest";

    public static void main(String[] args) {

    }

    private GithubUpdateApiBean updateApiBean;

    public String getVersionInfo(){
        String result = HttpUtils.sendGetRequest(LATEST_VERSION_SELECT_URL);
        if(result!=null){
            updateApiBean = new Gson().fromJson(result, GithubUpdateApiBean.class);
            return "Version info get success";
        }else{
            return "Version info get failed";
        }
    }

    public String getLatestVersion(){
        return updateApiBean.getTag_name();
    }

    public List<DownloadInfo> getDownLoadUrl(){
        // 返回当前文件的下载列表
        final List<GithubUpdateApiBean.Assets> assets = updateApiBean.getAssets();
        return assets.stream().map(it -> {
            DownloadInfo info = new DownloadInfo();
            info.setName(it.getName());
            info.setUrl(it.getBrowser_download_url());
            return info;
        }).collect(Collectors.toList());
    }

    // 下载最新版本
    public static String download(ArrayList<DownloadInfo> downloadList, JProgressBar bar) {
        StringBuffer sb = new StringBuffer();
        if (downloadList != null) {
            for (DownloadInfo info : downloadList) {
                String fileName = info.getName();
                String url = info.getUrl();
                boolean downloadResult = HttpUtils.download(url, new File(fileName), (sum, pro) -> {
                    bar.setValue((int) (pro / sum));
                    return 0;
                });
                if (downloadResult)
                    sb.append(fileName).append(" ");
            }
        }
        return sb.toString();
    }

    public String getCurrentVersion(){
        return "0.2";
    }

    @Data
    static class DownloadInfo {
        private String name;
        private String url;
    }
}
