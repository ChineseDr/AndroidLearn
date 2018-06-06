package com.ray.noaidl;

import android.os.IBinder;
import android.os.IInterface;
import java.util.List;

public interface IBookManager2 extends IInterface{
    //声明Binder描述符，Binder唯一标识符，一般用当前类名
    static final String DESCRIPTOR="com.ray.noaidl.IBookManager2";

    //声明方法的id
    static final int TRANSACTION_getBookList= IBinder.FIRST_CALL_TRANSACTION+0;
    static final int TRANSACTION_addBookList= IBinder.FIRST_CALL_TRANSACTION+1;

    public List<Book2> getBookList() throws Exception;
    public void addBookList(Book2 book2) throws Exception;

}
