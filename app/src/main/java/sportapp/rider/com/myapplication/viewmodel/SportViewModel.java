package sportapp.rider.com.myapplication.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import java.util.List;
import sportapp.rider.com.myapplication.core.Content;
import sportapp.rider.com.myapplication.model.ArticleResponseModel;
import sportapp.rider.com.myapplication.model.NewsResponseModel;
import sportapp.rider.com.myapplication.repository.SportRepository;


public class SportViewModel extends AndroidViewModel {

    private SportRepository sportRepository;
    private LiveData<List<NewsResponseModel>> newsResponseModelLiveData;
    private LiveData<ArticleResponseModel> articleResponseModelLiveData;


    public SportViewModel(@NonNull Application application) {
        super(application);
        sportRepository = SportRepository.getInstance(application.getApplicationContext());
    }

    public void getSport()
    {
        sportRepository.getSport();
    }


    public LiveData<List<NewsResponseModel>> getSportNewsResponseModeLiveData() {

        if (newsResponseModelLiveData == null) {
            newsResponseModelLiveData = new MutableLiveData<>();
            newsResponseModelLiveData = sportRepository.getSportsNewsCall();
        }

        return newsResponseModelLiveData;
    }

    public void getSportsArticles(String siteName, String urlName, String urlFriendlyDate, String urlFriendlyHeadline) {
        sportRepository.getSportsArticles(siteName, urlName, urlFriendlyDate, urlFriendlyHeadline);
    }

    public LiveData<ArticleResponseModel> getSportArticleResponseModeLiveData() {

        if (articleResponseModelLiveData == null) {
            articleResponseModelLiveData = new MutableLiveData<>();
            articleResponseModelLiveData = sportRepository.getArticlesNewsCall();
        }
        return articleResponseModelLiveData;
    }

    public MutableLiveData<Content> getNetworkFailure() {
        return sportRepository.getNetworkFailure();
    }

    public void clearLiveData() {
        sportRepository.clearLiveData();
    }
}

