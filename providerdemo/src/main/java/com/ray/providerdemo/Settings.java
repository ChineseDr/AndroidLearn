package com.ray.providerdemo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceActivity;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class Settings extends PreferenceActivity
        implements Preference.OnPreferenceClickListener,
        DialogInterface.OnClickListener {

    public static final String LANG_KEY="btn_languages";
    public static final String ADD_KEY="btn_Add";
    public static final String LIST_KEY="book_list";
    public static final String ENABLE_KEY="enable";
    public static final String EDIT_KEY="edit";
    public static final String SELECT_KEY="m_select";

    private PreferenceScreen mLangPre;
    private PreferenceScreen mAddPre;
    private PreferenceCategory mListPre;
    private CheckBoxPreference mEnablePre;
    private EditTextPreference mEditPre;
    private MultiSelectListPreference mMSelectPre;

    RayDataBaseHelper dbhelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.seetings);
        initPreference();
    }

    private void initPreference(){
        mLangPre= (PreferenceScreen) findPreference("btn_languages");
        mAddPre= (PreferenceScreen) findPreference("btn_Add");
        mListPre= (PreferenceCategory) findPreference("book_list");
        mEnablePre= (CheckBoxPreference) findPreference("enable");
        mEditPre = (EditTextPreference) findPreference("edit");
        mMSelectPre= (MultiSelectListPreference) findPreference("m_select");
        mLangPre.setOnPreferenceClickListener(this);
        mAddPre.setOnPreferenceClickListener(this);
        mMSelectPre.setOnPreferenceClickListener(this);
    }

    private boolean insertBook(Book book){
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",book.getName());
        values.put("author",book.getAuthor());
        return true;
    }

    private void setEidtPre(){
        //mEditPre.setNegativeButtonText();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key=preference.getKey();
        if(LANG_KEY.equals(key)){
            dbhelper=new RayDataBaseHelper(this,"books.db",null,1);
            dbhelper.getWritableDatabase();
        }else if (ADD_KEY.equals(key)){
            dbhelper=new RayDataBaseHelper(this,"book.db",null,1);
            dbhelper.getWritableDatabase();
        }
        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
