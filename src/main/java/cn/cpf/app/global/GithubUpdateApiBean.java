package cn.cpf.app.global;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GithubUpdateApiBean {

    private String url;
    private String assets_url;
    private String upload_url;
    private Date html_url;
    private long id;
    private Author author;
    private String node_id;
    private String tag_name;
    private String target_commitish;
    private String name;
    private boolean draft;
    private boolean prerelease;
    private Date created_at;
    private Date published_at;
    private List<Assets> assets;
    private Date tarball_url;
    private Date zipball_url;
    private Date body;

    @Data
    public static class Author {
        private String login;
        private long id;
        private String node_id;
        private String avatar_url;
        private String gravatar_id;
        private String url;
        private String html_url;
        private String followers_url;
        private String following_url;
        private String gists_url;
        private String starred_url;
        private String subscriptions_url;
        private String organizations_url;
        private String repos_url;
        private String events_url;
        private String received_events_url;
        private String type;
        private boolean site_admin;
    }

    @Data
    public static class Assets {
        private String url;
        private long id;
        private String node_id;
        private String name;
        private String label;
        private Uploader uploader;
        private String content_type;
        private String state;
        private long size;
        private int download_count;
        private Date created_at;
        private Date updated_at;
        private String browser_download_url;

        @Data
        public static class Uploader {

            private String login;
            private long id;
            private String node_id;
            private String avatar_url;
            private String gravatar_id;
            private String url;
            private String html_url;
            private String followers_url;
            private String following_url;
            private String gists_url;
            private String starred_url;
            private String subscriptions_url;
            private String organizations_url;
            private String repos_url;
            private String events_url;
            private String received_events_url;
            private String type;
            private boolean site_admin;
        }


    }

}