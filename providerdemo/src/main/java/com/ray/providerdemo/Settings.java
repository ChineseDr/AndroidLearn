package com.ray.providerdemo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
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
import android.text.Layout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Settings extends PreferenceActivity
        implements Preference.OnPreferenceClickListener,
        DialogInterface.OnClickListener {

    public static boolean isProvder = false;

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


    RayDataBaseHelper dbhelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.seetings);
        dbhelper = new RayDataBaseHelper(this);
        initPreference();
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

            }
        }
    };

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mUIHandler.sendEmptyMessage(INITDATA);
            }
        }).start();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
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
            insertToDatabase(book);
        }
        return true;
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
                EditText priceNum = view.findViewById(R.id.price_edit);

                String name = bookName.getText().toString();
                String author = authorName.getText().toString();
                String pages = pagesNum.getText().toString();
                String price = priceNum.getText().toString();
                Book book = new Book(name, author, Integer.valueOf(pages), Double.valueOf(price));
                insertBook(book);

            }
        });
        final AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void showEditeDialog() {

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
        db.insert(RayDataBaseHelper.TABLE_BOOK, null, values);
        return true;
    }

    private boolean deleteFromDatabase() {
        return true;
    }

    private boolean updateToDatabase(Book newBook, Book oldBook) {
        return true;
    }

    private boolean queryFromDatebase() {
        String[] Projection = new String[]{KEYID, AUTHOR, PRICE, PAGE, BOOKNAME};
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (LANG_KEY.equals(key)) {
//            dbhelper = new RayDataBaseHelper(this, "books.db", null, 1);
//            dbhelper.getWritableDatabase();
        } else if (ADD_KEY.equals(key)) {
//            dbhelper=new RayDataBaseHelper(this,"book.db",null,1);
//            dbhelper.getWritableDatabase();
            showAddBookDialog();
        }
        return false;
    }

    private void updateUI() {

    }

    private void updateBookUIList(Book books) {
        mListPre.removeAll();
        Preference book = new Preference(this);
        String title = books.getName() + ": " + books.getAuthor();
        book.setTitle(title);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    /**
     * 1.Task实例化在UI 线程创建
     * 2.excute方法必须在UI线程中调用
     * 3.其他方法不要手动调用，由系统自动调用
     */
    private class QuertAsyncTask extends AsyncTask<Void, Void, Void> {

        /**
         * 在耗时操作前执行，一般做些初始化
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
            publishProgress();//更新任务进度
            return null;
        }

        /**
         * doInBackground执行结束后，返回数据给UI线程，并在界面展示给用户，可以对UI控件进行设置
         *
         * @param aVoid
         */
        @Override
        protected void onPostExecute(Void aVoid) {
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
            super.onCancelled();
        }
    }
}
