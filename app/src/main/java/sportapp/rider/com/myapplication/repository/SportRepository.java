package sportapp.rider.com.myapplication.repository;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sportapp.rider.com.myapplication.ApiGateway;
import sportapp.rider.com.myapplication.Constance;
import sportapp.rider.com.myapplication.core.Content;
import sportapp.rider.com.myapplication.model.ArticleResponseModel;
import sportapp.rider.com.myapplication.model.NewsResponseModel;
import sportapp.rider.com.myapplication.service.SportService;


public class SportRepository {
    @SuppressLint("StaticFieldLeak")
    private static SportRepository instance;
    private SportService sportService;
    private MutableLiveData<List<NewsResponseModel>> newsResponseModelLiveData;
    private List<NewsResponseModel> newsResponseModels;
    private ArticleResponseModel articleResponseModel;
    private MutableLiveData<ArticleResponseModel> sportArticleResponseModelLiveData;
    private MutableLiveData<Content> networkFailure;


    public static SportRepository getInstance(Context context) {
        if (instance == null) instance = new SportRepository(context);
        return instance;
    }


    public SportRepository(Context context) {

        sportService = ApiGateway.getSportsNewsCall();
        newsResponseModelLiveData = new MutableLiveData<>();
        sportArticleResponseModelLiveData = new MutableLiveData<>();
        networkFailure = new MutableLiveData<>();
    }




    //method to call the news

    public void getSport(){
        clearLiveData();
        Call<List<NewsResponseModel>> newsCall = sportService.getSportsNews();
        newsCall.enqueue(new Callback<List<NewsResponseModel>>() {
            @Override
            public void onResponse(Call<List<NewsResponseModel>> call, Response<List<NewsResponseModel>> response) {
                if(response.code()==200){
                    newsResponseModels= response.body();
                    newsResponseModelLiveData.postValue(newsResponseModels);

                }
            }

            @Override
            public void onFailure(Call<List<NewsResponseModel>> call, Throwable t) {
                networkFailure.postValue(new Content(Content.Status.FAILED, Constance.NETWORK_ISSUES));

            }
        });
    }


    public MutableLiveData<List<NewsResponseModel>> getSportsNewsCall(){
        return newsResponseModelLiveData;
    }

    //method to call article
    public void getSportsArticles(String siteName,String urlName,String urlFriendlyDate ,String urlFriendlyHeadline){
        clearLiveData();
        Call<ArticleResponseModel> articleCall = sportService.getSportsArticles(siteName,urlName,urlFriendlyDate,urlFriendlyHeadline);
        articleCall.enqueue(new Callback<ArticleResponseModel>() {
            @Override
            public void onResponse(Call<ArticleResponseModel> call, Response<ArticleResponseModel> response) {
                if(response.code()==200){
                    articleResponseModel= response.body();
                    sportArticleResponseModelLiveData.postValue(articleResponseModel);


                }

            }

            @Override
            public void onFailure(Call<ArticleResponseModel> call, Throwable t) {
                networkFailure.postValue(new Content(Content.Status.FAILED, Constance.NETWORK_ISSUES));

            }
        });


    }

    public MutableLiveData<ArticleResponseModel> getArticlesNewsCall() {
        return sportArticleResponseModelLiveData;
    }

    public MutableLiveData<Content> getNetworkFailure() {
        return networkFailure;
    }


    public void clearLiveData() {
        sportArticleResponseModelLiveData = new MutableLiveData<>();
        sportArticleResponseModelLiveData.postValue(null);

        newsResponseModelLiveData = new MutableLiveData<>();
        newsResponseModelLiveData.postValue(null);

        networkFailure = new MutableLiveData<>();
        networkFailure.postValue(null);
    }




}