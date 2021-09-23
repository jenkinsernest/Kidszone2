package com.playzone.kidszone.response;


import com.google.gson.annotations.SerializedName;
import com.playzone.kidszone.models.AuthorSpinnerList;

import java.io.Serializable;
import java.util.List;

public class AuthorSpinnerRP implements Serializable {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("author_name")
    private List<AuthorSpinnerList> authorSpinnerLists;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<AuthorSpinnerList> getAuthorSpinnerLists() {
        return authorSpinnerLists;
    }
}
