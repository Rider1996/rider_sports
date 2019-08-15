package sportapp.rider.com.myapplication;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sportapp.rider.com.myapplication.service.SportService;

public class ApiGateway {

    public static SportService getSportsNewsCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constance.NEWS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(SportService.class);

    }
}

