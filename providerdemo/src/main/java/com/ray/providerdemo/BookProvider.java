package com.ray.providerdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * uri说明：1.content://com.ray.providerdemo/table
 *           查找表中的所有数据
 *         2.content://com.ray.providerdemo/table/id
 *         【content://】协议头
 *         【com.ray.providerdemo】authorities，自定义
 *         【table】表名
 *         【id】表中某一列
 *           查找这个表中的某个ID的数据
 *        使用通配符来匹配这两种格式
 *        *匹配任意长度
 */
public class BookProvider extends ContentProvider {
    private static final int BOOK_DIR=0;
    private static final int BOOK_ITEM=1;
    private static final int CATEGORY_DIR=2;
    private static final int CATEGORY_ITEM=3;

    private static final String BOOK_LIST_TYPE="vnd.providerdemo";
    private static final String CATEGORY_LIST_TYPE="";
    private static final String PACK="com.ray.providerdemo";
    private static UriMatcher uriMatcher;
    RayDataBaseHelper dataBaseHelper;

    /*
     * 组装规则 authority path 自定义常量
     *
     */
    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PACK,"book",BOOK_DIR);
        uriMatcher.addURI(PACK,"book/#",BOOK_ITEM);
        uriMatcher.addURI(PACK,"category",CATEGORY_DIR);
        uriMatcher.addURI(PACK,"category/#",CATEGORY_ITEM);
    }

    /**
     * 初始化ContentProvider时调用，通常在这里对数据库进行创建和升级操作
     * 当ContentResolver尝试访问数据是才会创建provider
     * @return true创建成功，false失败
     */
    @Override
    public boolean onCreate() {
        dataBaseHelper=new RayDataBaseHelper(getContext(),"book.db",null,1);
        return true;
    }

    /**
     *
     * @param uri 查找哪张表
     * @param projection 查询哪些列
     * @param selection 约束哪些行 sql语句中的“=？“”
     * @param selectionArgs 要查询的 ？的值
     * @param sortOrder 对结果进行排序
     * @return 查询到的结果保存在Curse对象中
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db=dataBaseHelper.getReadableDatabase();
        Cursor cursor=null;
        switch(uriMatcher.match(uri)){
            case BOOK_DIR:
                //查询表中所有数据

                break;
            case BOOK_ITEM:
                //查询表中某条数据
                break;
            case CATEGORY_DIR:

                break;
            case CATEGORY_ITEM:

                break;
        }
        return cursor;
    }

    /**
     * 根据传入的uri来返回相应的MIME类型
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:

                break;
            case BOOK_ITEM:

                break;
            case CATEGORY_DIR:

                break;
            case CATEGORY_ITEM:

                break;
        }
        return null;
    }


    /**
     *
     * @param uri  确认哪张表，
     * @param values 待添加的数据保存在ContentResolver中
     * @return
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:

                break;
        }
        return null;
    }


    /**
     *
     * @param uri 确认哪张表，
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    /**
     *
     * @param uri 确认哪张表，
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
