package com.li.learn.path.tasks;

import android.os.AsyncTask;
import com.li.learn.path.components.PathItemList;

public class FetchDataTask extends AsyncTask<Integer, Void, String[]> {

    private static final int PAGE_SIZE = 12;
    private PathItemList pathItemList;

    public FetchDataTask(PathItemList pathItemList) {
        this.pathItemList = pathItemList;
    }

    @Override
    protected String[] doInBackground(Integer... params) {
        int begin = params[0];
        String data[] = new String[PAGE_SIZE];
        for (int i = 0; i < PAGE_SIZE; i++) {
            data[i] = begin + i + 1 + "";
        }
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String[] data) {
        pathItemList.appendData(data);
    }
}
