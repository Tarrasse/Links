package com.links.links.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.links.links.data.LinksContract.*;

import java.net.PortUnreachableException;
import java.security.PublicKey;

/**
 * Created by mahmoud on 9/12/2016.
 */
public class LinksProvider extends ContentProvider {

    private DBHelper mOpenHelper;

    public static final int SERVICE = 100;
    public static final int SERVICE_WITH_ID = 101;
    public static final int PORTFOLIO = 200;
    public static final int COMPANY_WITH_ID = 201;
    public static final int QUESTIONS = 300;
    public static final int QUESTIONS_WITH_ID = 301;
    public static final int ANSWER = 400;
    public static final int ANSWER_WITH_ID = 401;
    public static final int ANSWER_WITH_QUESTION_ID = 402;
    public static final int NEWS = 500;
    public static final int NEWS_WITH_ID = 501;
    public static final int COURSES = 600;
    public static final int COURSES_WITH_ID = 601;




    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = LinksContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, LinksContract.PATH_SERVICE, SERVICE);
        matcher.addURI(authority, LinksContract.PATH_SERVICE + "/#", SERVICE_WITH_ID);
        matcher.addURI(authority, LinksContract.PATH_PORTFOLIO_ITEM, PORTFOLIO);
        matcher.addURI(authority, LinksContract.PATH_PORTFOLIO_ITEM + "/#", COMPANY_WITH_ID);
        matcher.addURI(authority, LinksContract.PATH_ANSWER, ANSWER);
        matcher.addURI(authority, LinksContract.PATH_ANSWER + "/#", ANSWER_WITH_QUESTION_ID);
        matcher.addURI(authority, LinksContract.PATH_QUESTION, QUESTIONS);
        matcher.addURI(authority, LinksContract.PATH_QUESTION + "/#", QUESTIONS_WITH_ID);
        matcher.addURI(authority, LinksContract.PATH_NEWS, NEWS);
        matcher.addURI(authority, LinksContract.PATH_NEWS + "/#", NEWS_WITH_ID);
        matcher.addURI(authority, LinksContract.PATH_COURSES, COURSES);
        matcher.addURI(authority, LinksContract.PATH_COURSES+ "/#", COURSES_WITH_ID);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        switch (sUriMatcher.match(uri)){
            case SERVICE:
                cursor = db.query(
                        ServicesTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case SERVICE_WITH_ID:
                cursor = db.query(
                        ServicesTable.TABLE_NAME,
                        projection,
                        ServicesTable._ID + "=?",
                        new String[]{
                                String.valueOf(ContentUris.parseId(uri))
                        },
                        null,
                        null,
                        sortOrder
                );
                break;
            case PORTFOLIO:
                cursor = db.query(
                        PortfolioTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case COMPANY_WITH_ID:
                cursor = db.query(
                        PortfolioTable.TABLE_NAME,
                        projection,
                        PortfolioTable._ID+"=?",
                        new String[]{
                                String.valueOf(ContentUris.parseId(uri))
                        },
                        null,
                        null,
                        sortOrder
                );

                break;
            case ANSWER:
                cursor = db.query(
                        AnswersColumn.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case ANSWER_WITH_QUESTION_ID:
                cursor = db.query(
                        AnswersColumn.TABLE_NAME,
                        projection,
                        PortfolioTable._ID+"=?",
                        new String[]{
                                String.valueOf(ContentUris.parseId(uri))
                        },
                        null,
                        null,
                        sortOrder
                );
                break;
            case QUESTIONS:
                cursor = db.query(
                        QuestionColumn.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case QUESTIONS_WITH_ID:
                cursor = db.query(
                        QuestionColumn.TABLE_NAME,
                        projection,
                        CoursesTable._ID+"=?",
                        new String[]{
                                String.valueOf(ContentUris.parseId(uri))
                        },
                        null,
                        null,
                        sortOrder
                );
                break;
            case NEWS:
                cursor = db.query(
                        NewsTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case NEWS_WITH_ID:
                cursor = db.query(
                        NewsTable.TABLE_NAME,
                        projection,
                        CoursesTable._ID+"=?",
                        new String[]{
                                String.valueOf(ContentUris.parseId(uri))
                        },
                        null,
                        null,
                        sortOrder
                );
                break;
            case COURSES:
                cursor = db.query(
                        CoursesTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case COURSES_WITH_ID:
                cursor = db.query(
                        CoursesTable.TABLE_NAME,
                        projection,
                        CoursesTable._ID+"=?",
                        new String[]{
                                String.valueOf(ContentUris.parseId(uri))
                        },
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri retUri;
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        long id;
        switch (sUriMatcher.match(uri)){
            case SERVICE:
            case SERVICE_WITH_ID:
                id = db.insert(
                        ServicesTable.TABLE_NAME,
                        null,
                        values
                );
                break;
            case PORTFOLIO:
            case COMPANY_WITH_ID:
                id = db.insert(
                        PortfolioTable.TABLE_NAME,
                        null,
                        values
                );
                break;
            case ANSWER:
            case ANSWER_WITH_QUESTION_ID:
                id = db.insert(
                        AnswersColumn.TABLE_NAME,
                        null,
                        values
                );
                break;
            case QUESTIONS:
            case QUESTIONS_WITH_ID:
                id = db.insert(
                        QuestionColumn.TABLE_NAME,
                        null,
                        values
                );
                break;
            case NEWS:
            case NEWS_WITH_ID:
                id = db.insert(
                        NewsTable.TABLE_NAME,
                        null,
                        values
                );
                break;
            case COURSES:
            case COURSES_WITH_ID:
                id = db.insert(
                        CoursesTable.TABLE_NAME,
                        null,
                        values
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }
        if(id < 0){
            throw new android.database.SQLException("Failed to insert row into " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        long id;
        switch (sUriMatcher.match(uri)){
            case SERVICE:
            case SERVICE_WITH_ID:
                id = db.delete(
                        ServicesTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            case PORTFOLIO:
            case COMPANY_WITH_ID:
                id = db.delete(
                        PortfolioTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            case ANSWER:
            case ANSWER_WITH_QUESTION_ID:
                id = db.delete(
                        AnswersColumn.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            case QUESTIONS:
            case QUESTIONS_WITH_ID:
                id = db.delete(
                        QuestionColumn.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            case NEWS:
            case NEWS_WITH_ID:
                id = db.delete(
                        NewsTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            case COURSES:
            case COURSES_WITH_ID:
                id = db.delete(
                        CoursesTable.TABLE_NAME,
                        selection,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return (int) id;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}

