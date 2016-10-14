package com.links.links;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.links.links.data.LinksContract;
import com.links.links.modules.News;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mahmoud on 9/12/2016.
 */
public class Utility {

    public static void insertNews(Context context, News news) {
        ContentValues values = new ContentValues();
        values.put(LinksContract.NewsTable.COLUMN_TITLE, news.getTitle());
        values.put(LinksContract.NewsTable.COLUMN_BODY, news.getBody());
        values.put(LinksContract.NewsTable.COLUMN_DATE, news.getDate());
        values.put(LinksContract.NewsTable.COLUMN_SERVER_ID, news.get_id());
        values.put(LinksContract.NewsTable.COLUMN_IMG_LINK, news.getImg_link());
        context.getContentResolver().insert(LinksContract.NewsTable.CONTENT_URI, values);
    }

    public static boolean isEmptyOrNull(String string) {
        return string == null || string.equals("") || string.trim().equals("");
    }

    public static Bundle fromJson(JSONObject s) {
        Bundle bundle = new Bundle();

        for (Iterator<String> it = s.keys(); it.hasNext(); ) {
            String key = it.next();
            JSONArray arr = s.optJSONArray(key);
            Double num = s.optDouble(key);
            String str = s.optString(key);

            if (arr != null && arr.length() <= 0)
                bundle.putStringArray(key, new String[]{});

            else if (arr != null && !Double.isNaN(arr.optDouble(0))) {
                double[] newarr = new double[arr.length()];
                for (int i = 0; i < arr.length(); i++)
                    newarr[i] = arr.optDouble(i);
                bundle.putDoubleArray(key, newarr);
            } else if (arr != null && arr.optString(0) != null) {
                String[] newarr = new String[arr.length()];
                for (int i = 0; i < arr.length(); i++)
                    newarr[i] = arr.optString(i);
                bundle.putStringArray(key, newarr);
            } else if (!num.isNaN())
                bundle.putDouble(key, num);

            else if (str != null)
                bundle.putString(key, str);

            else
                System.err.println("unable to transform json to bundle " + key);
        }

        return bundle;
    }

    public static void uploadRegistrationToServer(String refreshedToken) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference().child("users").getRef();

        String key = ref.push().getKey();

        Map<String, Object> values = new HashMap<>();

        values.put("token", refreshedToken);

        Map<String, Object> fina = new HashMap<>();
        fina.put("/users/" + key, values);
        database.getReference().updateChildren(fina);

    }


}
