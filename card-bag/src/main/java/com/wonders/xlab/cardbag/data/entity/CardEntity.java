package com.wonders.xlab.cardbag.data.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hua on 16/8/21.
 */

public class CardEntity extends RealmObject implements Parcelable {
    @PrimaryKey
    private long mId;
    private String mCardName;
    private String mBarCode;
    private String mImgUrl = "http://upload-images.jianshu.io/upload_images/598650-71ec1d3457194419.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";
    private String mImgFilePath;
    private String mFrontImgUrl;
    private String mFrontImgFilePath;
    private String mBackImgUrl;
    private String mBackImgFilePath;
    private long mCreateDate;

    public CardEntity() {
    }

    public CardEntity(CardEntity cardEntity) {
        if (cardEntity != null) {
            setId(cardEntity.getId());
            setCardName(cardEntity.getCardName());
            setBarCode(cardEntity.getBarCode());
            setImgUrl(cardEntity.getImgUrl());
            setImgFilePath(cardEntity.getImgFilePath());
            setFrontImgUrl(cardEntity.getFrontImgUrl());
            setFrontImgFilePath(cardEntity.getFrontImgFilePath());
            setBackImgUrl(cardEntity.getBackImgUrl());
            setBackImgFilePath(cardEntity.getBackImgFilePath());
            setCreateDate(cardEntity.getCreateDate());
        }
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        mImgUrl = !TextUtils.isEmpty(imgUrl) ? imgUrl : "http://upload-images.jianshu.io/upload_images/598650-71ec1d3457194419.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";
    }

    public String getCardName() {
        return mCardName;
    }

    public void setCardName(String cardName) {
        mCardName = cardName;
    }

    public String getImgFilePath() {
        return mImgFilePath;
    }

    public void setImgFilePath(String imgFilePath) {
        mImgFilePath = imgFilePath;
    }

    public String getBarCode() {
        return mBarCode;
    }

    public void setBarCode(String barCode) {
        mBarCode = barCode;
    }

    public String getFrontImgUrl() {
        return mFrontImgUrl;
    }

    public void setFrontImgUrl(String frontImgUrl) {
        mFrontImgUrl = frontImgUrl;
    }

    public String getFrontImgFilePath() {
        return mFrontImgFilePath;
    }

    public void setFrontImgFilePath(String frontImgFilePath) {
        mFrontImgFilePath = frontImgFilePath;
    }

    public String getBackImgUrl() {
        return mBackImgUrl;
    }

    public void setBackImgUrl(String backImgUrl) {
        mBackImgUrl = backImgUrl;
    }

    public String getBackImgFilePath() {
        return mBackImgFilePath;
    }

    public void setBackImgFilePath(String backImgFilePath) {
        mBackImgFilePath = backImgFilePath;
    }

    public long getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(long createDate) {
        mCreateDate = createDate;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mId);
        dest.writeString(this.mCardName);
        dest.writeString(this.mBarCode);
        dest.writeString(this.mImgUrl);
        dest.writeString(this.mImgFilePath);
        dest.writeString(this.mFrontImgUrl);
        dest.writeString(this.mFrontImgFilePath);
        dest.writeString(this.mBackImgUrl);
        dest.writeString(this.mBackImgFilePath);
        dest.writeLong(this.mCreateDate);
    }

    protected CardEntity(Parcel in) {
        this.mId = in.readLong();
        this.mCardName = in.readString();
        this.mBarCode = in.readString();
        this.mImgUrl = in.readString();
        this.mImgFilePath = in.readString();
        this.mFrontImgUrl = in.readString();
        this.mFrontImgFilePath = in.readString();
        this.mBackImgUrl = in.readString();
        this.mBackImgFilePath = in.readString();
        this.mCreateDate = in.readLong();
    }

    public static final Creator<CardEntity> CREATOR = new Creator<CardEntity>() {
        @Override
        public CardEntity createFromParcel(Parcel source) {
            return new CardEntity(source);
        }

        @Override
        public CardEntity[] newArray(int size) {
            return new CardEntity[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardEntity that = (CardEntity) o;

        return mId == that.mId;

    }

    @Override
    public int hashCode() {
        return (int) (mId ^ (mId >>> 32));
    }
}
