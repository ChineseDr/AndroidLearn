package com.ray.providerdemo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceActivity;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.Layout;
import android.text.TextWatcher;
import android.text.method.DialerKeyListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Settings extends PreferenceActivity
        implements Preference.OnPreferenceClickListener,
        DialogInterface.OnClickListener {
    private static final String TAG = "Settings";

    public static boolean isProvder = false;

    private static final int MENU_DELET = 11;
    private static final int MENU_EDIT = 12;
    private static final int MENU_READING = 13;
    private static final int MENU_READ = 14;


    public static final String LANG_KEY = "btn_languages";
    public static final String ADD_KEY = "btn_Add";
    public static final String LIST_KEY = "book_list";
    public static final String ENABLE_KEY = "enable";
    public static final String EDIT_KEY = "edit";
    public static final String SELECT_KEY = "m_select";

    private final int INITDATA = 100;

    private final String KEYID = "_id";
    private final String BOOKNAME = "name";
    private final String AUTHOR = "author";
    private final String PAGE = "page";
    private final String PRICE = "price";


    private PreferenceScreen mLangPre;
    private PreferenceScreen mAddPre;
    private PreferenceCategory mListPre;
    private CheckBoxPreference mEnablePre;
    private EditTextPreference mEditPre;
    private MultiSelectListPreference mMSelectPre;

    ArrayList<Book> mBookList = new ArrayList<Book>();
    HashMap<String, Book> mBookMap = new HashMap<String, Book>();

    QueryAsyncTask mQueryTask = null;


    RayDataBaseHelper dbhelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.seetings);
        dbhelper = new RayDataBaseHelper(this);
        initPreference();
        registerForContextMenu(this.getListView());
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INITDATA:
                    doQuery();
                    break;
                default:
                    break;
            }
        }
    };

    private void doQuery() {
        if (mQueryTask != null) {
            mQueryTask.cancel(true);
            mQueryTask = null;
        }
        mQueryTask = new QueryAsyncTask();
        mQueryTask.execute();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mUIHandler.sendEmptyMessage(INITDATA);
            }
        }).start();
    }

    private void initMap() {
        mBookMap = new HashMap<String, Book>();
        for (Book book : mBookList) {
            if (mQueryTask.isCancelled()) {
                break;
            }
            String mapKey = String.valueOf(book.getKeyId());
            mBookMap.put(mapKey, book);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        if (info == null) {
            Log.d(TAG, "onCreateContextMenu: is null");
            return;
        }
        //控件在全部空间中的位置，从0开始算起
        int position = info.position;
        if (position >= 6) {

            Log.d(TAG, "onCreateContextMenu: position " + position + " list length " + mBookList.size());
            int index = position - 6;
            Book book = mBookList.get(index);
            String bookName = book.getName();
            menu.setHeaderTitle(bookName);
            menu.add(3, MENU_READ, 0, "READ");
            menu.add(2, MENU_READING, 0, "Reading");
            menu.add(1, MENU_EDIT, 0, "Edit");
            menu.add(0, MENU_DELET, 0, "Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index=info.position-6;
        Book book=mBookList.get(index);
        switch (item.getItemId()) {
            case MENU_DELET:
                deleteBook(book);
                break;
            case MENU_EDIT:
                showEditeDialog(book);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void initPreference() {
        mLangPre = (PreferenceScreen) findPreference("btn_languages");
        mAddPre = (PreferenceScreen) findPreference("btn_Add");
        mListPre = (PreferenceCategory) findPreference("book_list");
        mEnablePre = (CheckBoxPreference) findPreference("enable");
        mEditPre = (EditTextPreference) findPreference("edit");
        mMSelectPre = (MultiSelectListPreference) findPreference("m_select");
        mLangPre.setOnPreferenceClickListener(this);
        mAddPre.setOnPreferenceClickListener(this);
        mMSelectPre.setOnPreferenceClickListener(this);
    }

    private boolean insertBook(Book book) {
        if (isProvder) {

        } else {
            if (insertToDatabase(book)) {
                mBookList.add(book);
                updateBookUIList();
            } else {
                Log.d(TAG, "insertBook: Failed!");
            }
        }
        return true;
    }

    private boolean deleteBook(Book book){
        if (isProvder){
            return true;
        }else {
            if (deleteFromDatabase(book)){
                mBookList.remove(book);
                updateBookUIList();
                return true;
            }else {
                return false;
            }
        }

    }

    private void showAddBookDialog() {
        //取得布局服务（LayoutInflater）实例
        LayoutInflater inflater = LayoutInflater.from(this);
        //使用布局服务加载视图
        final View view = inflater.inflate(R.layout.add_book, null);
        //创建AlertDialog.Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置dialog布局
        builder.setView(view);
        builder.setTitle("Add Book");
        //添加确认按钮
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText bookName = view.findViewById(R.id.book_name_edit);
                EditText authorName = view.findViewById(R.id.author_edit);
                EditText pagesNum = view.findViewById(R.id.pages);
                pagesNum.setKeyListener(new DialerKeyListener());
                //设置编辑框输入类型（数字）必须同时设置TYPE_CLASS_NUMBER和TYPE_NUMBER_VARIATION_NORMAL才可生效
                //pagesNum.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_VARIATION_NORMAL);
                EditText priceNum = view.findViewById(R.id.price_edit);
                priceNum.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                ///设置编辑框输入类型(小数)必须同时设定才可生效
                //priceNum.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_FLAG_DECIMAL);

                String name = bookName.getText().toString();
                String author = authorName.getText().toString();
                String pages = pagesNum.getText().toString();
                String price = priceNum.getText().toString();
                if ((!"".equals(pages)) && (!"".equals(price))) {
                    Book book = new Book(name, author, Integer.valueOf(pages), Double.valueOf(price));
                    insertBook(book);
                }
            }
        });
        final AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void showEditeDialog(final Book oldBook) {
        LayoutInflater inflater=LayoutInflater.from(this);
        final View editView=inflater.inflate(R.layout.add_book,null);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setView(editView);
        builder.setTitle("Edit Book");
        builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final EditText bookName=editView.findViewById(R.id.book_name_edit);
                final EditText bookAuthor=editView.findViewById(R.id.author_edit);
                final EditText bookPage=editView.findViewById(R.id.pages);
                final EditText bookPrice=editView.findViewById(R.id.price_edit);

                bookName.setText(oldBook.getName());
                bookAuthor.setText(oldBook.getAuthor());
                bookPage.setText(String.valueOf(oldBook.getPages()));
                bookPrice.setText(String.valueOf(oldBook.getPrice()));
            }
        });
        final AlertDialog dialog=builder.create();
        dialog.show();
    }

    private boolean insertToDatabase(Book newBook) {
        if (newBook == null) {
            return false;
        }
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //key是数据库字段，
        values.put("name", newBook.getName());
        values.put("author", newBook.getAuthor());
        values.put("price", newBook.getPrice());
        values.put("page", newBook.getPages());
        //删除数据库中数据
        long line = db.insert(RayDataBaseHelper.TABLE_BOOK, null, values);
        //如果返回-1操作数据库失败
        if (line == -1) {
            return false;
        } else {
            return true;
        }
    }

    private boolean deleteFromDatabase(Book book) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        String where = BOOKNAME + "=?" ;
        String[] whereArgs = new String[]{book.getName()};
        //修改数据库
        long line = db.delete(RayDataBaseHelper.TABLE_BOOK, where, null);
        if (line == -1) {
            return false;
        } else {
            return true;
        }

    }

    private boolean updateToDatabase(Book oldBook, Book newBook) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        final String name = newBook.getName();
        final String author = newBook.getAuthor();
        final int page = newBook.getPages();
        final double price = newBook.getPrice();
        values.put(BOOKNAME, name);
        values.put(AUTHOR, newBook.getAuthor());
        values.put(PAGE, Integer.valueOf(newBook.getPages()));
        values.put(PRICE, Double.valueOf(newBook.getPrice()));
        String where = KEYID + "=" + oldBook.getKeyId();
        long line = db.update(RayDataBaseHelper.TABLE_BOOK, values, where, null);
        if (line == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * query至少接收7个参数：第一个参数：表名；第二个参数：指定要查询的列
     * 第三个参数：where约束条件，第四个参数：where约束条件站位符的值
     * 第五个参数：groupBy指定需要group by的列：group by column
     * 第六个参数：group by后的结果进一步约束：having column=value
     * 第七个参数：查询结果的排序方式：order by column1，column2
     *
     * @return
     */
    private boolean queryFromDatebase() {
        String[] Projection = new String[]{KEYID, AUTHOR, PRICE, PAGE, BOOKNAME};
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        //查询数据库中的数据，参数全部传入null表示查询整张表
        Cursor cursor = db.query(RayDataBaseHelper.TABLE_BOOK, null,
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            Book book = new Book();
            book.setKeyId(cursor.getInt(0));
            book.setName(cursor.getString(1));
            book.setAuthor(cursor.getString(2));
            book.setPages(cursor.getInt(3));
            book.setPrice(cursor.getDouble(4));
            mBookList.add(book);
        }
        if (cursor != null) {
            cursor.close();
        }
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (preference.equals(mLangPre)) {

        } else if (preference.equals(mAddPre)) {
            showAddBookDialog();
        }else if (preference.equals(mEnablePre)){
            Log.d(TAG, "onPreferenceClick: mEnablePre isChecked"+mEnablePre.isChecked());
            mListPre.setEnabled(mEnablePre.isChecked());
        }else if (preference.equals(mEditPre)){

        }else if (preference.equals(mMSelectPre)){

        }
        return false;
    }

    private void updateUI() {

    }

    private void updateBookUIList() {
        mListPre.removeAll();
        for (final Book book : mBookList) {
            Log.d(TAG, "updateBookUIList: " + book.getName());
            Preference bookPre = new Preference(this);
            String title = book.getName() + ": " + book.getAuthor();
            //设置Preference标题（第一行）
            bookPre.setTitle(title);
            //设置Preference内容（第二行）
            bookPre.setSummary(String.valueOf(book.getPrice()));
            bookPre.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    showEditeDialog(book);
                    return true;
                }
            });
            mListPre.addPreference(bookPre);
        }

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    /**
     * 1.Task实例化在UI 线程创建
     * 2.excute方法必须在UI线程中调用
     * 3.其他方法不要手动调用，由系统自动调用
     */
    private class QueryAsyncTask extends AsyncTask<Void, Void, Void> {

        /**
         * 在耗时操作前执行，一般做些初始化
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            if (mQueryTask!=null){
//                mQueryTask.cancel(true);
//                mQueryTask=null;
//            }
        }

        /**
         * 执行耗时操作，可以调用publishProgress来触发onProgressUpdate更新UI
         * 此方法必须实现
         *
         * @param voids
         * @return
         */
        @Override
        protected Void doInBackground(Void... voids) {
            //publishProgress();//更新任务进度
            if (queryFromDatebase()) {
                initMap();
                updateBookUIList();
            }
            return null;
        }

        /**
         * doInBackground执行结束后，返回数据给UI线程，并在界面展示给用户，可以对UI控件进行设置
         *
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            updateBookUIList();
            super.onPostExecute(aVoid);
        }

        /**
         * doInBackground中每次调用publishProgress都会触发此方法
         * 此方法运行在UI线程中，可对UI控件进行操作
         *
         * @param values
         */
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            updateUI();
            super.onCancelled();
        }
    }
}
