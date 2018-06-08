package com.ray.noaidl;

import android.os.Parcel;
import android.os.Parcelable;

public class User2 implements Parcelable {
    public int userId;
    public String userName;
    public boolean isMale;

    public Book2 book2;

    public User2(int userId, String userName, boolean isMale, Book2 book2) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
        this.book2 = book2;
    }


    public static final Creator<User2> CREATOR = new Creator<User2>() {
        @Override
        public User2 createFromParcel(Parcel in) {
            return new User2(in);
        }

        @Override
        public User2[] newArray(int size) {
            return new User2[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeByte((byte) (isMale ? 1 : 0));
        dest.writeParcelable(book2, flags);
    }

    private User2(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readByte() != 0;
        //由于book2是另一个可序列化对象
        book2 = in.readParcelable(Book2.class.getClassLoader());
    }
}
