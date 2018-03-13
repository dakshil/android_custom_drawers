package ioandroid_custom_drawers.github.httpsdakshil.navigation_drawers;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
/**
 *
 * Created by Dakshil Shah
 * Copyright © 2018 Dakshil. All rights reserved.
 */

public class fetchJSONFile extends AsyncTask<Void, Void, String> {

    private Context context;
    private String fileName = "";
    private String path = "";

    public fetchJSONFile(Context gotContext, String gotFileName) {
        context=gotContext;
        fileName=gotFileName;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            String fileURL = "https://dakshil.github.io/android_custom_drawers/" + fileName;
            URL url = new URL(fileURL);

            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = in.read(buf);
            while (n!=-1)
            {
                out.write(buf, 0, n);
                n=in.read(buf);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();
            String filePath = context.getFilesDir().getPath() + File.separator  + fileName;
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(response);
            fos.close();

            path = context.getFilesDir().getAbsolutePath() + "/" + fileName;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    @Override
    protected void onPostExecute(String path) {
        returnFilePath(path);
    }

    private String returnFilePath(String result) {
        return result;
    }

}

