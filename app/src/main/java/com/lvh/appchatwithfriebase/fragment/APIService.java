package com.lvh.appchatwithfriebase.fragment;

import com.lvh.appchatwithfriebase.notifycation.MyResponse;
import com.lvh.appchatwithfriebase.notifycation.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService  {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization: key=AAAA6mRsjmc:APA91bE-xnnpwaVk941-wahaLj7fwsS-tUV0JFzX0UbWJQzZeBKxZDMZufr6F404EbwpQW5J1Mj2xmNjxcIZsuC819n5_DEfqEBRGCpTUdpnzHTTyEPryHe_lx8BwM11RnXKu-3iwr35"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotifycation(@Body Sender body);
}
