package sportapp.rider.com.myapplication.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.text.HtmlCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import sportapp.rider.com.myapplication.Constance;
import sportapp.rider.com.myapplication.R;
import sportapp.rider.com.myapplication.core.Content;
import sportapp.rider.com.myapplication.viewmodel.SportViewModel;

public class SportsArticleFragment extends EmptyFragment {

    public static final String TAG = SportsArticleFragment.class.getName();
    private SportViewModel mSportsNewsViewModel;
    private String siteName, urlName, urlFriendlyDate, urlFriendlyHeadline;
    ImageView imageViewArticle;
    TextView textViewTitleArticle;
    TextView textViewSubtitleArticle;
    TextView textViewBody;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSportsNewsViewModel = ViewModelProviders.of(this).get(SportViewModel.class);
        mSportsNewsViewModel.clearLiveData();
        changeTitle(Constance.ARTICLE);
        actionBarColor();
        showBackButton();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isNetworkAvailable()) {
            //In future we will have a progressbar here as well
            Bundle mBundle = getArguments();
            if (mBundle != null) {
                siteName = mBundle.getString(Constance.SITE_NAME);
                urlName = mBundle.getString(Constance.URL_NAME);
                urlFriendlyDate = mBundle.getString(Constance.URL_FRIENDLY_DATE);
                urlFriendlyHeadline = mBundle.getString(Constance.URL_FRIENDLY_HEADLINE);
            }


            mSportsNewsViewModel.getSportsArticles(siteName, urlName, urlFriendlyDate, urlFriendlyHeadline);

            mSportsNewsViewModel.getSportArticleResponseModeLiveData().observe(getViewLifecycleOwner(), sportArticleResponseModel -> {
                if (sportArticleResponseModel != null) {

                    String url = sportArticleResponseModel.imageUrlLocal + sportArticleResponseModel.largeImageName;
                    Glide
                            .with(this)
                            .load(url)
                            .centerCrop()
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(imageViewArticle);
                    textViewBody.setText(HtmlCompat.fromHtml(sportArticleResponseModel.body, 0));
                    textViewTitleArticle.setText(sportArticleResponseModel.headline);
                    textViewSubtitleArticle.setText(sportArticleResponseModel.urlFriendlyDate);
                }
            });

            mSportsNewsViewModel.getNetworkFailure().observe(getViewLifecycleOwner(), (Content content) -> {
                if (content != null) {
                    showNoInternetConnection(getActivity(), Constance.NO_CONNECTION, content.getMessage(), (DialogInterface dialogInterface, int i) -> {
                        connectionSettingsButton();
                    });
                }
            });

        } else {
            showNoInternetConnection(getActivity(), Constance.NO_CONNECTION, getResources().getString(R.string.connect_to_wifi_or_turn_on_mobile_data), (dialogInterface, i) -> connectionSettingsButton());
        }
    }

    private void connectionSettingsButton() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_article, container, false);
        setupViews(view);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mSportsNewsViewModel.clearLiveData();
    }

    public void setupViews(View view){
        imageViewArticle =view.findViewById(R.id.imageViewArticle);
        textViewTitleArticle =view.findViewById(R.id.textViewTitleArticle);
        textViewSubtitleArticle =view.findViewById(R.id.textViewSubtitleArticle);
        textViewBody =view.findViewById(R.id.textViewBody);
    }
}


