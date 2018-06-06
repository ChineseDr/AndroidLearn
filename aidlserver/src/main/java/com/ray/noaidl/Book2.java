package com.ray.noaidl;

import android.os.Parcel;
import android.os.Parcelable;

import com.ray.aidlserver.Book;

public class Book2 implements Parcelable{
    private int bookID;
    private String bookName;

    public Book2(int bookID, String bookName) {
        this.bookID = bookID;
        this.bookName = bookName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 写入
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookID);
        dest.writeString(bookName);
    }

    public static final Parcelable.Creator<Book2> CREATOR=new Parcelable.Creator<Book2>() {
        @Override
        public Book2 createFromParcel(Parcel source) {
            return new Book2(source);
        }

        @Override
        public Book2[] newArray(int size) {
            return new Book2[size];
        }
    };

    private Book2(Parcel in){
        bookID=in.readInt();
        bookName=in.readString();
    }
}
