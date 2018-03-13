package ioandroid_custom_drawers.github.httpsdakshil.navigation_drawers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;

/**
 * Created by Dakshil Shah
 * Copyright © 2018 Dakshil. All rights reserved.
 */

public class parseJSONFile {

    static class Characters {
        String name;
    }
    static class Books {
        int bookID;
        String bookName;
        List<Characters> characters;
    }
    static class JsonData {
        List<Books> books;
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * @param activity
     */

    //above api 23, require user to grant explicit permission to app to access external storage
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public JsonData bookDataParse(String jFilePath,Activity activity, Context context) throws IOException {
        verifyStoragePermissions(activity);
        String json = readFile(jFilePath, context);
        Gson gson = new Gson();
        JsonData data = gson.fromJson(json, JsonData.class);
        return data;
    }

    private String readFile(String jFilePath, Context context) throws IOException {
        FileInputStream fileIN = new FileInputStream (new File(jFilePath));
        StringBuffer data = new StringBuffer("");

        byte[] buffer = new byte[1024];
        int n;
        while ((n = fileIN.read(buffer)) != -1)
        {
            data.append(new String(buffer, 0, n));
        }
        fileIN.close();
        return data.toString();
    }
}
