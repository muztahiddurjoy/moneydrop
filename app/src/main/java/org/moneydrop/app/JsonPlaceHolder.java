package org.moneydrop.app;

import org.moneydrop.app.DataClasses.EmailSenderDataset;
import org.moneydrop.app.DataClasses.NetworkDataset;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolder {
    @POST("emailsend")
    Call<EmailSenderDataset> sendEmail(EmailSenderDataset dataset);

    @GET("json/")
    Call<NetworkDataset> getNetWorks();
}
