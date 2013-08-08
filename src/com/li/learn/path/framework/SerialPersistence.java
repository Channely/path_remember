package com.li.learn.path.framework;

import android.content.Context;
import android.util.Log;

import java.io.*;

public class SerialPersistence {
    private static String NAME = "state_backup";
    private Context context;

    public SerialPersistence(Context context) {
        this.context = context;
    }

    public <T extends Serializable> void saveSerialObject(String fileName, T object) {
        OutputStream fos = getFileOutputStream(fileName);
        if (fos == null) return;
        serializeObjectToDisk(fos, object);
    }

    public <T extends Serializable> T readSerialObject(String fileName) {
        InputStream fis = getFileInputStream(fileName);
        if (fis == null) return null;
        return (T) readObjectFromStream(fis);
    }

    private boolean serializeObjectToDisk(OutputStream os, Serializable object) {
        boolean result = false;
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(os);
            oos.writeObject(object);
            result = true;
        } catch (IOException e) {
            Log.e("IOUtil", "serializeObjectToDisk IO error", e);
            e.printStackTrace();
        } finally {
            closeOutputStream(oos);
            closeOutputStream(os);
        }
        return result;
    }

    private Object readObjectFromStream(InputStream fis) {
        Object object = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
            object = ois.readObject();
        } catch (Exception e) {
            Log.e("IOUtil", "readObjectFromMemory error", e);
            e.printStackTrace();
        } finally {
            closeInputStream(ois);
            closeInputStream(fis);
        }
        return object;
    }

    private static void closeOutputStream(OutputStream fos) {
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeInputStream(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private InputStream getFileInputStream(String filename) {
        InputStream fis = null;
        try {
            String[] files = context.fileList();
            for (String file : files) {
                if (!file.equalsIgnoreCase(filename)) continue;
                fis = context.openFileInput(filename);
                break;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fis;
    }

    private FileOutputStream getFileOutputStream(String filename) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fos;
    }
}
