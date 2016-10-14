package com.links.links.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mahmoud on 9/12/2016.
 */
public class LinksContract {

    public static final String CONTENT_AUTHORITY = "com.links.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_QUESTION = "question";
    public static final String PATH_ANSWER = "answer";
    public static final String PATH_SERVICE = "service";
    public static final String PATH_PORTFOLIO_ITEM = "company";
    public static final String PATH_NEWS = "news";
    public static final String PATH_COURSES = "courses";

    public static final class QuestionColumn implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_QUESTION).build();

        public static final String TABLE_NAME = "questions";

        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_ANS = "answer";



        public static Uri buildWithUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String[] COLUMNS = {
                _ID,
                COLUMN_BODY,
                COLUMN_DATE,
                COLUMN_USER,
                COLUMN_ANS
        };
    }

    public static final class AnswersColumn implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ANSWER).build();

        public static final String TABLE_NAME = "answers";

        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_QUESTION = "questions";

        public static Uri buildWithUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String[] COLUMNS = {
                _ID,
                COLUMN_DATE,
                COLUMN_BODY,
                COLUMN_QUESTION
        };

    }

    public static final class ServicesTable implements BaseColumns{


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SERVICE).build();

        public static final String TABLE_NAME = "services";

        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_NAME = "name";

        public static Uri buildWithUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String[] COLUMNS = {
                _ID,
                COLUMN_BODY,
                COLUMN_NAME
        };



    }

    public static final class PortfolioTable implements BaseColumns {


        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PORTFOLIO_ITEM).build();

        public static final String TABLE_NAME = "portfolio";

        public static final String COLUMN_COMPANY_NAME = "name";
        public static final String COLUMN_LOGO_LINK = "logo";
        public static final String COLUMN_TYPE = "type";

        public static Uri buildWithUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String[] COLUMNS = {
                _ID,
                COLUMN_COMPANY_NAME,
                COLUMN_LOGO_LINK,
                COLUMN_TYPE
        };

        public static final int ID_INDEX = 0;
        public static final int COMPANY_NAME_INDEX = 1;
        public static final int LOGO_LINK_INDEX = 2;
        public static final int TYPE_INDEX = 3;

    }

    public static final class NewsTable implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_NEWS).build();

        public static final String TABLE_NAME = "news";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_SERVER_ID = "id";
        public static final String COLUMN_IMG_LINK = "img_link";



        public static Uri buildWithUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String[] COLUMNS = {
                _ID,
                COLUMN_TITLE,
                COLUMN_BODY,
                COLUMN_DATE,
                COLUMN_SERVER_ID,
                COLUMN_IMG_LINK
        };

        public static final int ID_INDEX = 0;
        public static final int TITLE_INDEX = 1;
        public static final int BODY_INDEX = 2;
        public static final int DATE_INDEX = 3;
        public static final int SERVER_ID_INDEX = 4;
        public static final int IMG_LINK_INDX = 5;
    }

    public static class CoursesTable implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_COURSES).build();


        public static String TABLE_NAME = "courses";

        public static String COLUMN_NAME = "name";
        public static String COLUMN_DESC = "desc";
        public static String COLUMN_IMG = "img_link";
        public static String COLUMN_AVAILABLE_SEATS = "available_seats";
        public static String COLUMN_START_DATE = "start_date";
        public static String COLUMN_CODE = "code";
        public static String COLUMN_SERVER_ID = "id";


        public static Uri buildWithUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String[] COLUMNS = {
                _ID,
                COLUMN_NAME,
                COLUMN_DESC,
                COLUMN_IMG,
                COLUMN_AVAILABLE_SEATS,
                COLUMN_START_DATE,
                COLUMN_SERVER_ID,
                COLUMN_CODE
        };

        public static final int _ID_INDEX = 0;
        public static final int NAME_INDEX = 1;
        public static final int DESC_INDEX = 2;
        public static final int IMG_INDEX = 3;
        public static final int AVILABLE_SEATS_INDEX = 4;
        public static final int START_DATE_INDEX = 5;
        public static final int CODE_INDEX = 6;
        public static final int SERVER_ID_INDEX = 7;

    }



}
