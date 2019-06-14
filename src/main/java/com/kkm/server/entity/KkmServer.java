package com.kkm.server.entity;

import com.google.gson.JsonObject;
import okhttp3.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.IOException;

@Entity
@Table

public class KkmServer {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    private String serverAddress;

    public KkmServer() {
    }

    public KkmServer(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String sendPost(String json) throws IOException {//TODO:  забирать из файла свойств???
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder()
                .url(getServerAddress())
                .addHeader("Authorization", "Basic YWRtaW46")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body());
        return response.body().string();
    }
}
