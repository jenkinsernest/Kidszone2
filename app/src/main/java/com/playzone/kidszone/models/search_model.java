package com.playzone.kidszone.models;

public class search_model {



    String title;

    String pix_url;
    String category;
    String url;






    public String geturl() {
        return url;
    }

    public void seturl(String url) {
        this.url = url;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    public String getPix_url() {
        return pix_url;
    }

    public void setPix_url(String pix_url) {
        this.pix_url = pix_url;
    }





    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public search_model(String title, String pix_url, String category,  String url){

        this.title=title;
        this.pix_url= pix_url;
        this.category= category;
        this.url= url;

    }
    public search_model( ){

    }




}
