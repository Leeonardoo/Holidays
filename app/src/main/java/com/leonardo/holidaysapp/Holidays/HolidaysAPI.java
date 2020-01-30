package com.leonardo.holidaysapp.Holidays;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HolidaysAPI {

    @GET("api/v2/publicholidays/{year}/{country}")
    Call<List<Holiday>> getHolidays(@Path("year") int year, @Path("country") String country);
}
