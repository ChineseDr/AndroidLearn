package com.ray.noaidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class BookManager2Impl extends Binder implements IBookManager2 {
    /**
     * Construct the stub at attach it to the interface.
     * 第一个参数需要注册的类
     * 第二个参数接口描述符，本类实现IBookManager2接口,
     * DESCRIPTOR声明在IBookManager2接口，可以像声明在本类中的属性一样使用
     */
    public BookManager2Impl() {
        this.attachInterface(this, DESCRIPTOR);
    }

    /**
     * Cast an IBinder object into an com.ray.noaidl.IBookManager2 interface,
     * generating a proxy if needed.
     * 把一个IBinder对象转换成IBookManager2接口，如果需要使用内部代理类
     *
     * @param obj
     * @return
     */
    public static IBookManager2 asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }
        //查找本地接口对象
        IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
        //查询到的本地接口对象不为空且是IBookManager2类型
        if ((iin != null) && (iin instanceof IBookManager2)) {
            //则把接口对象转成IBookManager2返回
            return (IBookManager2) iin;
        }
        //如果本地接口没有找到，通过代理查询远程接口
        return new BookManager2Impl.Proxy(obj);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags)
            throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSACTION_addBookList:
                data.enforceInterface(DESCRIPTOR);
                Book2 arg0;
                if (0 != data.readInt()) {
                    arg0 = Book2.CREATOR.createFromParcel(data);
                } else {
                    arg0 = null;
                }
                this.addBookList(arg0);
                return true;
            case TRANSACTION_getBookList:
                data.enforceInterface(DESCRIPTOR);
                List<Book2> result = this.getBookList();
                reply.writeNoException();
                reply.writeTypedList(result);
                return true;
        }
        return super.onTransact(code, data, reply, flags);
    }

    @Override
    public List<Book2> getBookList() throws RemoteException {
        //todo
        return null;
    }

    @Override
    public void addBookList(Book2 book2) throws RemoteException {
        //todo
    }

    private static class Proxy implements IBookManager2 {
        private IBinder mRemote;

        public Proxy(IBinder mRemote) {
            this.mRemote = mRemote;
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }

        public String getInterfaceDesriptor() {
            return DESCRIPTOR;
        }

        @Override
        public List<Book2> getBookList() throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            List<Book2> result;
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getBookList, data, reply, 0);
                reply.readException();
                result = reply.createTypedArrayList(Book2.CREATOR);
            } finally {
                reply.recycle();
                data.recycle();
            }
            return result;
        }

        @Override
        public void addBookList(Book2 book2) throws Exception {
            Parcel data=Parcel.obtain();
            Parcel reply=Parcel.obtain();
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                if (book2!=null){
                    data.writeInt(1);
                    book2.writeToParcel(data,0);
                }else {
                    data.writeInt(0);
                }
                mRemote.transact(TRANSACTION_addBookList,data,reply,0);
                reply.readException();
            }finally {
                data.recycle();
                reply.recycle();
            }

        }


    }
}
