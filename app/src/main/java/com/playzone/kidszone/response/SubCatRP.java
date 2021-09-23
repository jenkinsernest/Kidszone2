package com.playzone.kidszone.response;


import com.google.gson.annotations.SerializedName;
import com.playzone.kidszone.models.SubCategoryList;

import java.io.Serializable;
import java.util.List;

public class SubCatRP implements Serializable {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("ads_param")
    private String ads_param;

    @SerializedName("EBOOK_APP")
    private List<SubCategoryList> subCategoryLists;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getAds_param() {
        return ads_param;
    }

    public List<SubCategoryList> getSubCategoryLists() {
        return subCategoryLists;
    }
}
