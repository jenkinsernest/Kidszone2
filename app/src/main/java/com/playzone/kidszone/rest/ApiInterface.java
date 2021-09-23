package com.playzone.kidszone.rest;

import com.playzone.kidszone.response.AboutUsRP;
import com.playzone.kidszone.response.AppRP;
import com.playzone.kidszone.response.AuthorDetailRP;
import com.playzone.kidszone.response.AuthorRP;
import com.playzone.kidszone.response.AuthorSpinnerRP;
import com.playzone.kidszone.response.BookDetailRP;
import com.playzone.kidszone.response.BookRP;
import com.playzone.kidszone.response.CatRP;
import com.playzone.kidszone.response.CatSpinnerRP;
import com.playzone.kidszone.response.CommentRP;
import com.playzone.kidszone.response.ContactRP;
import com.playzone.kidszone.response.DataRP;
import com.playzone.kidszone.response.DeleteCommentRP;
import com.playzone.kidszone.response.FaqRP;
import com.playzone.kidszone.response.FavouriteRP;
import com.playzone.kidszone.response.GetReportRP;
import com.playzone.kidszone.response.HomeRP;
import com.playzone.kidszone.response.LoginRP;
import com.playzone.kidszone.response.MyRatingRP;
import com.playzone.kidszone.response.PrivacyPolicyRP;
import com.playzone.kidszone.response.ProfileRP;
import com.playzone.kidszone.response.RatingRP;
import com.playzone.kidszone.response.RegisterRP;
import com.playzone.kidszone.response.SubCatRP;
import com.playzone.kidszone.response.SubCatSpinnerRP;
import com.playzone.kidszone.response.UserCommentRP;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    //get app data
    @POST("api2.php")
    @FormUrlEncoded
    Call<AppRP> getAppData(@Field("data") String data);

    //login
    @POST("api2.php")
    @FormUrlEncoded
    Call<LoginRP> getLogin(@Field("data") String data);

    //login check
    @POST("api2.php")
    @FormUrlEncoded
    Call<LoginRP> getLoginDetail(@Field("data") String data);

    //register
    @POST("api2.php")
    @FormUrlEncoded
    Call<RegisterRP> getRegisterDetail(@Field("data") String data);

    //forget password
    @POST("api2.php")
    @FormUrlEncoded
    Call<DataRP> getForgetPassword(@Field("data") String data);

    //profile
    @POST("api2.php")
    @FormUrlEncoded
    Call<ProfileRP> getProfile(@Field("data") String data);

    //edit profile
    @POST("api2.php")
    @Multipart
    Call<DataRP> getEditProfile(@Part("data") RequestBody data, @Part MultipartBody.Part part);

    //update password
    @POST("api2.php")
    @FormUrlEncoded
    Call<DataRP> updatePassword(@Field("data") String data);

    //home page
    @POST("api2.php")
    @FormUrlEncoded
    Call<HomeRP> getHome(@Field("data") String data);

    //category
    @POST("api2.php")
    @FormUrlEncoded
    Call<CatRP> getCategory(@Field("data") String data);

    //sub category
    @POST("api2.php")
    @FormUrlEncoded
    Call<SubCatRP> getSubCategory(@Field("data") String data);

    //category spinner list
    @POST("api2.php")
    @FormUrlEncoded
    Call<CatSpinnerRP> getCatSpinner(@Field("data") String data);

    //sub category spinner list
    @POST("api2.php")
    @FormUrlEncoded
    Call<SubCatSpinnerRP> getSubCatSpinner(@Field("data") String data);

    //category by id book list
    @POST("api2.php")
    @FormUrlEncoded
    Call<BookRP> getCatBook(@Field("data") String data);

    //author
    @POST("api2.php")
    @FormUrlEncoded
    Call<AuthorRP> getAuthor(@Field("data") String data);

    //author spinner list
    @POST("api2.php")
    @FormUrlEncoded
    Call<AuthorSpinnerRP> getAuthorSpinner(@Field("data") String data);

    //author detail
    @POST("api2.php")
    @FormUrlEncoded
    Call<AuthorDetailRP> getAuthorDetail(@Field("data") String data);

    //author by book
    @POST("api2.php")
    @FormUrlEncoded
    Call<BookRP> getAuthorBook(@Field("data") String data);

    //continue reading book
    @POST("api2.php")
    @FormUrlEncoded
    Call<DataRP> submitContinueReading(@Field("data") String data);

    //latest and popular book
    @POST("api2.php")
    @FormUrlEncoded
    Call<BookRP> getLatestBook(@Field("data") String data);

    //Favourite book
    @POST("api2.php")
    @FormUrlEncoded
    Call<FavouriteRP> getFavouriteBook(@Field("data") String data);

    //related book
    @POST("api2.php")
    @FormUrlEncoded
    Call<BookRP> getRelated(@Field("data") String data);

    //search book
    @POST("api2.php")
    @FormUrlEncoded
    Call<BookRP> getSearchBook(@Field("data") String data);

    //book detail
    @POST("api2.php")
    @FormUrlEncoded
    Call<BookDetailRP> getBookDetail(@Field("data") String data);

    //get all comment
    @POST("api2.php")
    @FormUrlEncoded
    Call<CommentRP> getAllComment(@Field("data") String data);

    //comment
    @POST("api2.php")
    @FormUrlEncoded
    Call<UserCommentRP> submitComment(@Field("data") String data);

    //delete comment
    @POST("api2.php")
    @FormUrlEncoded
    Call<DeleteCommentRP> deleteComment(@Field("data") String data);

    //get my rating
    @POST("api2.php")
    @FormUrlEncoded
    Call<MyRatingRP> getMyRating(@Field("data") String data);

    //rating book
    @POST("api2.php")
    @FormUrlEncoded
    Call<RatingRP> submitRating(@Field("data") String data);

    //get report book
    @POST("api2.php")
    @FormUrlEncoded
    Call<GetReportRP> getBookReport(@Field("data") String data);

    //report book
    @POST("api2.php")
    @FormUrlEncoded
    Call<DataRP> submitBookReport(@Field("data") String data);

    //get about us
    @POST("api2.php")
    @FormUrlEncoded
    Call<AboutUsRP> getAboutUs(@Field("data") String data);

    //get privacy policy
    @POST("api2.php")
    @FormUrlEncoded
    Call<PrivacyPolicyRP> getPrivacyPolicy(@Field("data") String data);

    //get faq
    @POST("api2.php")
    @FormUrlEncoded
    Call<FaqRP> getFaq(@Field("data") String data);


    //get contact us list
    @POST("api2.php")
    @FormUrlEncoded
    Call<ContactRP> getContactSub(@Field("data") String data);

    //Submit contact
    @POST("api2.php")
    @FormUrlEncoded
    Call<DataRP> submitContact(@Field("data") String data);

}
