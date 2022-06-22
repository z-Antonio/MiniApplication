package com.example.miniapplication.service;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {
    protected Result(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    private String response;

    public Result(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO
        dest.writeString(response);
    }

    public void readFromParcel(Parcel dest){
        // TODO
        response = dest.readString();
    }
}
