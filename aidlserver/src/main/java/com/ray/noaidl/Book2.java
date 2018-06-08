package com.ray.noaidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parcel内部包装了可序列化的数据，可以在Binder中自由传输
 * 在序列化过程中需要实现功能有序列化，反序列化，内容描述
 * 序列化由writeToParcel完成，最终通过Parcel的write方法写入
 * 反序列化由CREATOR完成，
 * 内容描述由describeContents完成
 */
public class Book2 implements Parcelable{
    private int bookID;
    private String bookName;

    public Book2(int bookID, String bookName) {
        this.bookID = bookID;
        this.bookName = bookName;
    }

    //返回当前对象的描述
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *
     * @param dest
     * @param flags 几乎所有情况都是0
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookID);
        dest.writeString(bookName);
    }

    /**
     *反序列化
     */
    public static final Parcelable.Creator<Book2> CREATOR=new Parcelable.Creator<Book2>() {
        /**
         * 从序列化后的对象中创建原始对象
         * @param source
         * @return
         */
        @Override
        public Book2 createFromParcel(Parcel source) {
            return new Book2(source);
        }

        /**
         * 创建指定长度的的原始对象数组
         * @param size
         * @return
         */
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
