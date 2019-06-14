package com.kkm.server.cash.requests;

import com.google.gson.JsonObject;

public interface KkmRequest {
    public abstract JsonObject toJson();
}