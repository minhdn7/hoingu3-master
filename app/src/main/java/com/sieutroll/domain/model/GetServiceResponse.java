package com.sieutroll.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MinhDN on 7/5/2018.
 */
public class GetServiceResponse {
    @SerializedName("data")
    @Expose
    @Getter @Setter
    public Data data;

    public static class Data{
        @SerializedName("total")
        @Expose
        @Getter @Setter
        public Integer total;

        @SerializedName("item")
        @Expose
        @Getter @Setter
        public List<Item> item = null;
    }

    public static class Item{
        @SerializedName("total_view")
        @Expose
        @Getter @Setter
        public String totalView;

        @SerializedName("avatar_full")
        @Expose
        @Getter @Setter
        public String avatarFull;

        @SerializedName("campain_type")
        @Expose
        @Getter @Setter
        public String campainType;

        @SerializedName("url_download")
        @Expose
        @Getter @Setter
        public String urlDownload;

        @SerializedName("app_manager_id")
        @Expose
        @Getter @Setter
        public String appManagerId;

        @SerializedName("total_like")
        @Expose
        @Getter @Setter
        public String totalLike;

        @SerializedName("app_content")
        @Expose
        @Getter @Setter
        public String appContent;

        @SerializedName("app_name")
        @Expose
        @Getter @Setter
        public String appName;

        @SerializedName("packagename")
        @Expose
        @Getter @Setter
        public String packagename;

        @SerializedName("avatar")
        @Expose
        @Getter @Setter
        public String avatar;

        @SerializedName("total_download")
        @Expose
        @Getter @Setter
        public String totalDownload;

        @SerializedName("app_description")
        @Expose
        @Getter @Setter
        public String appDescription;
    }
}
