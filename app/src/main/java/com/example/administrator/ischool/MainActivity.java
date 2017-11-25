package com.example.administrator.ischool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.net.HttpURLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private EditText et_studentId;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_studentId = (EditText) findViewById(R.id.et_studentId);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    public void login(View v){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    //获得学生id与密码
                    String studentId = et_studentId.getText().toString().trim();
                    String password = et_password.getText().toString().trim();

                    //组拼数据
                String data = null;

                    data = "studentId=" + URLEncoder.encode(studentId, "utf-8")+"&password="+URLEncoder.encode(password,"utf-8");
                //组拼请求路径
                    String path = "http://192.168.1.101:5678/StudentApp/login.php";
                    //建立连接
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                    //设置连接参数
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(5000);
                    connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                    connection.setRequestProperty("Content-Length",data.length()+"");
                    connection.setDoOutput(true);
                    connection.getOutputStream().write(data.getBytes());

                    //获得返回码并处理
                    int code = connection.getResponseCode();
                    if(code == 200){
                        InputStream in = connection.getInputStream();
                        String result = StreamTools.parseInputStream(in);
                        int resultCode = Integer.parseInt(result);
                        if(resultCode == 0){
                            //TODO 跳转至课表页面
                            showToast("登录成功，跳转课表页面将在后续完成");
                        }else if(resultCode == 1){
                            showToast("账号不存在");
                        }else if(resultCode == 2){
                            showToast("密码错误");
                        }
                    }else{
                        showToast("服务器繁忙，请稍后再试");
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ConnectException e) {
                    showToast("服务器繁忙，请稍后再试");
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void seePassword(View v){
        et_password.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    public void back(View v){
        finish();
    }

    private void showToast(final String content){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
