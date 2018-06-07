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
     * aidl机制会在客户端也生成同样的文件，客户端调用asInterface方法是调用的客户端的
     * 如果客户端有实现了IBookManager2的子类就返回查询到的本地服务
     * 如果没有则返回Proxy进行远程调用
     * @param obj  客户端调用传入的Binder对象
     * @return 服务端实现了IBookManager2接口的服务类对象
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

    /**
     * 此方法运行在服务端的Binder线程池中，来处理客户端的远程请求
     * 客户端发起跨进程请求后，远程请求会被系统封装之后交给此方法中处理
     * @param code 服务端通过code确认客户端的请求目标方法是哪个
     * @param data 从data中取出目标方法所需的参数，然后执行目标方法
     * @param reply 目标方法执行完毕后向reply写入返回值
     * @param flags
     * @return 返回值为false，客户端请求失败，可被做为权限控制
     * @throws RemoteException
     */
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
                //运行目标方法，目标方法由子类具体实现（服务中的匿名内部类）
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

        /**
         * 该方法运行在客户端，由客户端调用，
         * @return
         * @throws RemoteException
         */
        @Override
        public List<Book2> getBookList() throws RemoteException {
            //创建输入型Parcel对象
            Parcel data = Parcel.obtain();
            //创建输出型Parcel对象
            Parcel reply = Parcel.obtain();
            //返回的List对象
            List<Book2> result;
            try {
                //
                data.writeInterfaceToken(DESCRIPTOR);
                //调用Binder对象的transact方法发起远程请求（RPC），
                // 此时服务端的onTransact方法被调用，传入参数
                mRemote.transact(TRANSACTION_getBookList, data, reply, 0);
                reply.readException();
                //从reply取出远程调用返回的结果
                result = reply.createTypedArrayList(Book2.CREATOR);
            } finally {
                reply.recycle();
                data.recycle();
            }
            //返回reply
            return result;
        }

        @Override
        public void addBookList(Book2 book2) throws Exception {
            //创建输入输出Parcel
            Parcel data = Parcel.obtain();
            Parcel reply = Parcel.obtain();
            try {
                data.writeInterfaceToken(DESCRIPTOR);
                if (book2 != null) {
                    data.writeInt(1);
                    book2.writeToParcel(data, 0);
                } else {
                    data.writeInt(0);
                }
                //发起远程调用
                mRemote.transact(TRANSACTION_addBookList, data, reply, 0);
                reply.readException();
            } finally {
                data.recycle();
                reply.recycle();
            }
        }

    }
}
