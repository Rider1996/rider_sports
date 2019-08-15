package sportapp.rider.com.myapplication.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import sportapp.rider.com.myapplication.Constance;
import sportapp.rider.com.myapplication.R;
import sportapp.rider.com.myapplication.adapter.SportAdapter;
import sportapp.rider.com.myapplication.model.NewsResponseModel;
import sportapp.rider.com.myapplication.viewmodel.SportViewModel;

public class SportsNewsFragments extends EmptyFragment {

    public static final String TAG = SportsArticleFragment.class.getName();
    private SportViewModel mSportsNewsViewModel;
    TextView textViewTitle;
    TextView textViewSubtitle;
    RecyclerView recyclerViewSportsItem;
    ProgressBar progressBar;
    ConstraintLayout constraintLayoutTitle;
    ImageView imageView;
    TextView textViewSportName;
    private List<NewsResponseModel> sportNewsResponseModelList;
    private SportAdapter sportsNewsAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSportsNewsViewModel = ViewModelProviders.of(this).get(SportViewModel.class);
        // mSportsNewsViewModel.clearLiveData();
        changeTitle(Constance.NEWS);
        actionBarColor();
        hideBackButton();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sportNewsResponseModelList = new ArrayList<>();

        if (isNetworkAvailable()) {
            mSportsNewsViewModel.getSport();

            progressBar.setVisibility(View.VISIBLE);

            mSportsNewsViewModel.getSportNewsResponseModeLiveData().observe(getViewLifecycleOwner(), new Observer<List<NewsResponseModel>>() {
                @Override
                public void onChanged(@Nullable final List<NewsResponseModel> model) {
                    if (model != null) {

                        progressBar.setVisibility(View.GONE);

                        if (SportsNewsFragments.this.getActivity() == null) return;
                        recyclerViewSportsItem.setBackgroundColor(ContextCompat.getColor(SportsNewsFragments.this.getActivity(), R.color.colorInactiveText));
                        constraintLayoutTitle.setBackgroundColor(ContextCompat.getColor(SportsNewsFragments.this.getActivity(), R.color.colorTertiaryTint60));
                        //textViewSportName.setBackground(ContextCompat.getDrawable(SportsNewsFragments.this.getActivity(), R.drawable.oval_shape));

                        textViewSportName.setText(model.get(4).category);
                        textViewTitle.setText(model.get(4).headline);
                        textViewSubtitle.setText(model.get(4).urlFriendlyDate);

                        String url = model.get(4).imageUrlRemote + model.get(4).largeImageName;
                        Glide
                                .with(SportsNewsFragments.this)
                                .load(url)
                                .centerCrop()
                                .placeholder(R.drawable.ic_launcher_background)
                                .into(imageView);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putString(Constance.SITE_NAME, model.get(2).siteName);
                                bundle.putString(Constance.URL_NAME, model.get(2).urlName);
                                bundle.putString(Constance.URL_FRIENDLY_DATE, model.get(2).urlFriendlyDate);
                                bundle.putString(Constance.URL_FRIENDLY_HEADLINE, model.get(2).urlFriendlyHeadline);
                                SportsArticleFragment sportsArticleFragment = new SportsArticleFragment();
                                sportsArticleFragment.setArguments(bundle);
                                if (getActivity() == null) return;
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.addToBackStack(SportsArticleFragment.TAG);
                                fragmentTransaction.replace(R.id.sports_news_container, sportsArticleFragment, SportsArticleFragment.TAG);
                                fragmentTransaction.commit();
                            }
                        });

                        sportNewsResponseModelList = model;

                        sportsNewsAdapter = new SportAdapter(SportsNewsFragments.this.getContext(), sportNewsResponseModelList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(SportsNewsFragments.this.getContext(), RecyclerView.VERTICAL, false);
                        recyclerViewSportsItem.setLayoutManager(layoutManager);
                        recyclerViewSportsItem.setAdapter(sportsNewsAdapter);
                        sportsNewsAdapter.setOnItemClickListener(new SportAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View itemView, int position) {
                                Bundle mBundle = new Bundle();
                                mBundle.putString(Constance.SITE_NAME, sportNewsResponseModelList.get(position).siteName);
                                mBundle.putString(Constance.URL_NAME, sportNewsResponseModelList.get(position).urlName);
                                mBundle.putString(Constance.URL_FRIENDLY_DATE, sportNewsResponseModelList.get(position).urlFriendlyDate);
                                mBundle.putString(Constance.URL_FRIENDLY_HEADLINE, sportNewsResponseModelList.get(position).urlFriendlyHeadline);

                                SportsArticleFragment sportsArticleFragment = new SportsArticleFragment();
                                sportsArticleFragment.setArguments(mBundle);
                                if (getActivity() == null) return;
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.addToBackStack(SportsArticleFragment.TAG);
                                fragmentTransaction.replace(R.id.sports_news_container, sportsArticleFragment, SportsArticleFragment.TAG);
                                fragmentTransaction.commit();

                            }
                        });

                    }
                }
            });

            mSportsNewsViewModel.getNetworkFailure().observe(getViewLifecycleOwner(), content -> {
                if (content != null)
                    showNoInternetConnection(getActivity(), Constance.NO_CONNECTION, content.getMessage(), (dialogInterface, i) -> connectionSettingsButton());
            });
        } else {
            progressBar.setVisibility(View.GONE);
            showNoInternetConnection(getActivity(), Constance.NO_CONNECTION, getResources().getString(R.string.connect_to_wifi_or_turn_on_mobile_data), (dialogInterface, i) -> connectionSettingsButton());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        setupViews(view);
        return view;

    }

    @Override
    public void onPause() {
        super.onPause();
        mSportsNewsViewModel.clearLiveData();
    }



    public void setupViews(View view) {
        textViewTitle =view.findViewById(R.id.textViewSubtitle);
        textViewSubtitle =view.findViewById(R.id.textViewSubtitle);
        recyclerViewSportsItem =view.findViewById(R.id.recyclerViewSportsItem);
        imageView =view.findViewById(R.id.imageView);
        textViewSubtitle =view.findViewById(R.id.textViewSubtitle);
        progressBar = view.findViewById(R.id.progressBar);
        constraintLayoutTitle = view.findViewById(R.id.constraintLayoutTitle);
        textViewSportName = view.findViewById(R.id.textViewSportName);
    }

    private void connectionSettingsButton() {

    }
}
