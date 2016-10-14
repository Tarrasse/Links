package com.links.links.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.links.links.Utility;
import com.links.links.data.LinksContract.*;

/**
 * Created by mahmoud on 9/12/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static String DBNAME = "links.db";
    public static int VERSION = 8;

    public DBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    private String createTableQuestions = "CREATE TABLE " + QuestionColumn.TABLE_NAME + " (" +
            QuestionColumn._ID + " INTEGER PRIMARY KEY," +
            QuestionColumn.COLUMN_BODY + " TEXT UNIQUE NOT NULL, " +
            QuestionColumn.COLUMN_DATE + " TEXT NOT NULL, " +
            QuestionColumn.COLUMN_USER + " TEXT NOT NULL, " +
            QuestionColumn.COLUMN_ANS + " TEXT NOT NULL " +
            " );";

    private String createTableAns = "CREATE TABLE " + AnswersColumn.TABLE_NAME + " (" +
            AnswersColumn._ID + " INTEGER PRIMARY KEY," +
            AnswersColumn.COLUMN_BODY + " TEXT UNIQUE NOT NULL, " +
            AnswersColumn.COLUMN_DATE + " TEXT NOT NULL, " +
            AnswersColumn.COLUMN_QUESTION + " TEXT NOT NULL " +
            " );";

    private String createTableServices = "CREATE TABLE " + ServicesTable.TABLE_NAME + " (" +
            ServicesTable._ID + " INTEGER PRIMARY KEY," +
            ServicesTable.COLUMN_BODY + " TEXT UNIQUE NOT NULL, " +
            ServicesTable.COLUMN_NAME + " TEXT NOT NULL " +
            " );";

    private String createTablePortfolio = "CREATE TABLE " + PortfolioTable.TABLE_NAME + " (" +
            PortfolioTable._ID + " INTEGER PRIMARY KEY," +
            PortfolioTable.COLUMN_COMPANY_NAME + " TEXT UNIQUE NOT NULL, " +
            PortfolioTable.COLUMN_LOGO_LINK + " TEXT NOT NULL, " +
            PortfolioTable.COLUMN_TYPE + " TEXT NOT NULL " +
            " );";

    private String createTableNews = "CREATE TABLE " + NewsTable.TABLE_NAME + " (" +
            NewsTable._ID + " INTEGER PRIMARY KEY," +
            NewsTable.COLUMN_DATE + " TEXT , " +
            NewsTable.COLUMN_TITLE + " TEXT , " +
            NewsTable.COLUMN_BODY + " TEXT , " +
            NewsTable.COLUMN_SERVER_ID + " TEXT , " +
            NewsTable.COLUMN_IMG_LINK + " TEXT " +
            " );";


    private String createTableCourses = "CREATE TABLE " + CoursesTable.TABLE_NAME + " (" +
            CoursesTable._ID + " INTEGER PRIMARY KEY," +
            CoursesTable.COLUMN_NAME + " TEXT NOT NULL, " +
            CoursesTable.COLUMN_CODE+ " TEXT NOT NULL, " +
            CoursesTable.COLUMN_SERVER_ID+ " INTEGER, " +
            CoursesTable.COLUMN_AVAILABLE_SEATS+ " INTEGER, " +
            CoursesTable.COLUMN_START_DATE+ " DATE, " +
            CoursesTable.COLUMN_DESC+ " TEXT NOT NULL, " +
            CoursesTable.COLUMN_IMG+ " TEXT " +
            " );";


    @Override
    public void onCreate(SQLiteDatabase db) {

        //create all tables

        db.execSQL(createTableAns);
        db.execSQL(createTablePortfolio);
        db.execSQL(createTableServices);
        db.execSQL(createTableQuestions);
        db.execSQL(createTableNews);
        db.execSQL(createTableCourses);



        //inserting some fake data for testing
//        for (int i = 0; i <5 ; i++) {
//            ContentValues values =  new ContentValues();
//            values.put(NewsTable.COLUMN_TITLE, "fake data no: " + i);
//            values.put(NewsTable.COLUMN_BODY, Utility.DummyMessageForTesting);
//            values.put(NewsTable.COLUMN_DATE, "19/8/2016");
//            values.put(NewsTable.COLUMN_SERVER_ID, i);
//            db.insert(
//                    NewsTable.TABLE_NAME,
//                    null,
//                    values
//                    );
//        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //delete all the data in case of upgrade the dataBase
        db.execSQL("DROP TABLE IF EXISTS " + PortfolioTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ServicesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionColumn.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AnswersColumn.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NewsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CoursesTable.TABLE_NAME);


        //creates all the tables again the upgraded version
        onCreate(db);
    }
}
