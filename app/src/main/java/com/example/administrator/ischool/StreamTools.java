package com.example.administrator.ischool;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wangchentao on 2017/11/19.
 */

public class StreamTools {
    public static String parseInputStream(InputStream in){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while((len = in.read(buffer)) != -1){
                baos.write(buffer,0,len);
            }
            String result = new String(baos.toByteArray(),"utf-8");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
