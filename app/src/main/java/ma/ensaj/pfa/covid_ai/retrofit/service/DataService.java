package ma.ensaj.pfa.covid_ai.retrofit.service;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataService {
    @Headers("Content-Type: application/json")
    @POST("/saveUser")
    Call<String> saveUser(@Body JsonObject body);

    @Multipart
    @POST("/predict")
    Call<ResponseBody> predict(@Part MultipartBody.Part image);
}
