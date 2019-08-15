package sportapp.rider.com.myapplication.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import sportapp.rider.com.myapplication.model.ArticleResponseModel;
import sportapp.rider.com.myapplication.model.NewsResponseModel;

public interface SportService {

    @GET("news?format=json")
    Call<List<NewsResponseModel>> getSportsNews();

    @GET("{SiteName}/{UrlName}/news/{UrlFriendlyDate}/{UrlFriendlyHeadline}?format=json")
    Call<ArticleResponseModel> getSportsArticles(@Path("SiteName") String siteName,
                                                 @Path("UrlName") String urlName,
                                                 @Path("UrlFriendlyDate") String urlFriendlyDate,
                                                 @Path("UrlFriendlyHeadline") String urlFriendlyHeadline);

}

