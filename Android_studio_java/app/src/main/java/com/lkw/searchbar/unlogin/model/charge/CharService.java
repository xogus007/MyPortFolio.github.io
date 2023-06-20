package com.lkw.searchbar.unlogin.model.charge;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CharService {
//    @GET("/plug_data")
//    Call<List<CharDocument>> getDatabaseItems();

    // http://~~~/plug_data2?usingType=X&speedType=Y&chargeType=Z
    // with @Query
    // http://~~~/plug_data2?using=X&speed=Y&charge=Z
    @GET("/plug_data2")
    Call<List<ChargeDocuments>> getDatabaseItems2(
        @Query("cpStat") int usingType,
        @Query("chargeTp") int speedType,
        @Query("cpTp") int chargeType
    );
}
