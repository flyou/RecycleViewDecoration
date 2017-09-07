package com.flyou.recycleviewdecoration.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fzl on 2017/08/15.
 * VersionCode: 1
 * Desc:
 */

public class RecycleDataBean implements Parcelable {
    private String image;
    private String title;
    private String type;


    public RecycleDataBean() {
    }

    public RecycleDataBean(String image, String title, String type) {
        this.image = image;
        this.title = title;
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeString(this.title);
        dest.writeString(this.type);
    }

    protected RecycleDataBean(Parcel in) {
        this.image = in.readString();
        this.title = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<RecycleDataBean> CREATOR = new Parcelable.Creator<RecycleDataBean>() {
        @Override
        public RecycleDataBean createFromParcel(Parcel source) {
            return new RecycleDataBean(source);
        }

        @Override
        public RecycleDataBean[] newArray(int size) {
            return new RecycleDataBean[size];
        }
    };
}
