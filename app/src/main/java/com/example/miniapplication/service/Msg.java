package com.example.miniapplication.service;

import android.os.Parcel;
import android.os.Parcelable;

public class Msg implements Parcelable {

    protected Msg(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<Msg> CREATOR = new Creator<Msg>() {
        @Override
        public Msg createFromParcel(Parcel in) {
            return new Msg(in);
        }

        @Override
        public Msg[] newArray(int size) {
            return new Msg[size];
        }
    };

    private String message;

    public Msg(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO
        dest.writeString(message);
    }

    public void readFromParcel(Parcel dest){
        // TODO
        message = dest.readString();
    }
}
