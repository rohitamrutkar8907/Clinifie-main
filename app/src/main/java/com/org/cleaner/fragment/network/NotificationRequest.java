package com.org.cleaner.fragment.network;


import com.org.cleaner.fragment.model.NotificationReq;
import com.org.cleaner.fragment.model.NotificationResponce;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificationRequest {

    @Headers(
            {"Content-Type:application/json",
                    "Authorization:key=AAAA0dsh3hE:APA91bGfTtX7kdHuNF7-u53scFqAU4GSsUtksgpCoovplOLzKQxKBLau8Mi6JNY654cL8G2UKvOsu6KnjeGs087TyetjuOhSbrmOxqgzlp75lVnCD1UwiSdV5Iqj3JT_W5_ZubxCMm78"})
    @POST("send")
    Call<NotificationResponce> sent(@Body NotificationReq req);

}