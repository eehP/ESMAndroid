package org.libreapps.rest.ViewSearch;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("bills")
    Call<List<BillJSON>> getBills();
}