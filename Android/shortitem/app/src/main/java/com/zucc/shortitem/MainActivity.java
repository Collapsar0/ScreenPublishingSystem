package com.zucc.shortitem;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zucc.shortitem.Beans.MarqueeTextView;
import com.zucc.shortitem.Beans.MyViewFlipperAdapter;
import com.zucc.shortitem.adapter.ViewFlipperAdapter;
import com.zucc.shortitem.util.BrightnessUtil;
import com.zucc.shortitem.util.CacheDataManager;
import com.zucc.shortitem.util.JSONParser;
import com.zucc.shortitem.websocket.JWebSocketClient;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button btn_shot;
    private Button btn_open;
    private Button btn_reopen;
    private Button btn_sound;
    private Button btn_light;
    private Button btn_update;
    private Button btn_clean;

    private Dialog dialog;
    private TextView textView;
    private SeekBar seekBar;
    private MarqueeTextView marqueeTextView;
    private MyViewFlipperAdapter avf_view;
    private JWebSocketClient client;
    private AppCompatActivity activity;
    String TAG="BloodSugarActivity";
    private Context mContext;

    private int num = 0;
    private int[] time;
    private List<String> data = new ArrayList<>();
    private List<String> resdata = new ArrayList<>();
    private String id;
    private List<programJson> content = new ArrayList<>();

    public static final String URL_PROGRAM =
            "http://47.99.58.44:8081/program/getProgramByID?programID=";

    public static final String URL_PIC =
            "http://47.99.58.44:8081/material/getMaterialURLByID?materialID=";

    public static final String URL_NOTICE =
            "http://47.99.58.44:8081/notice/getNoticeByID?noticeID=";

    public static class programJson{
        Integer materialID;
        Integer playbackTime;
    }
    // ?????????
    private static final String BOUNDARY = "----WebKitFormBoundaryT1HoybnYeFOGFlBR";
    /**
     * HttpUrlConnection?????????????????????
     * @param params ????????????
     * @param fileFormName ????????????????????????
     * @param uploadFile ???????????????
     * @param newFileName ????????????????????????????????????????????????????????????
     * @param urlStr url
     * @throws IOException
     */
    public void uploadForm(Map<String, String> params, String fileFormName, File uploadFile, String newFileName,
                                  String urlStr){

        try {
            if (newFileName == null || newFileName.trim().equals("")) {
                newFileName = uploadFile.getName();
            }
            StringBuilder sb = new StringBuilder();
            /**
             * ?????????????????????
             */
            if (params != null) {
                for (String key : params.keySet()) {
                    sb.append("--" + BOUNDARY + "\r\n");
                    sb.append("Content-Disposition: form-data; name=\"" + key + "\"" + "\r\n");
                    sb.append("\r\n");
                    sb.append(params.get(key) + "\r\n");
                }
            }

            /**
             * ??????????????????
             */
            sb.append("--" + BOUNDARY + "\r\n");
            sb.append("Content-Disposition: form-data; name=\"" + fileFormName + "\"; filename=\"" + newFileName + "\""
                    + "\r\n");
            sb.append("Content-Type: image/jpeg" + "\r\n");// ???????????????????????????????????????????????????????????????ContentType
            sb.append("\r\n");

            byte[] headerInfo = sb.toString().getBytes("UTF-8");
            byte[] endInfo = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");


            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // ??????????????????????????????????????????
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            conn.setRequestProperty("Content-Length",
                    String.valueOf(headerInfo.length + uploadFile.length() + endInfo.length));
            conn.setDoOutput(true);

            OutputStream out = conn.getOutputStream();
            InputStream in = new FileInputStream(uploadFile);
            // ???????????? ?????????????????????????????????????????????????????????
            out.write(headerInfo);
            // ????????????
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            // ????????????
            out.write(endInfo);
            in.close();
            out.close();
            if (conn.getResponseCode() == 200) {
                BufferedReader back = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String data = back.readLine().toString();
                sendMsg("{from: Android001, to: PC001, text: "+java.net.URLEncoder.encode(data.substring(60,data.length()-3))+"}");
                System.out.println("??????????????????");
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        avf_view = (MyViewFlipperAdapter) findViewById(R.id.flipper);
        avf_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showMenu();
            }
        });
        mContext = getBaseContext();
        //????????????
        marqueeTextView = (MarqueeTextView)findViewById(R.id.tv_scroll);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        marqueeTextView.setGravity(Gravity.CENTER);
        marqueeTextView.setHeight(0);
        marqueeTextView.setScrollWidth(wm.getDefaultDisplay().getWidth());
        marqueeTextView.setBackgroundResource(R.color.black);
        marqueeTextView.setCoordinateY(180);
        marqueeTextView.setCurrentPosition(0);//????????????????????????????????????????????????

        //???????????????
        ViewFlipperAdapter viewFlipperAdapter = new ViewFlipperAdapter(this, data);
        //??????????????????????????????
        //avf_view.setInAnimation(ObjectAnimator.ofFloat(avf_view, View.TRANSLATION_Y,150.0f,0.0f));
        //avf_view.setOutAnimation(ObjectAnimator.ofFloat(avf_view, View.TRANSLATION_Y,0.0f,-150.0f));
        avf_view.setAdapter(viewFlipperAdapter);
        avf_view.startFlipping();
    }

    /* ??????????????? */
    private void initBrightness(){
        //????????????????????????????????????seekbar??????????????????
        seekBar.setMax(BrightnessUtil.getMaxBrightness(MainActivity.this));
        //????????????????????????????????????seekbar???????????????
        seekBar.setProgress(BrightnessUtil.getBrightness(MainActivity.this));
    }

    private void showMenu(){
        dialog = new Dialog(this);
        //????????????????????????
        View popup_view = getLayoutInflater().inflate(R.layout.menu_pop, null);
        //????????????
        btn_shot = (Button)  popup_view.findViewById(R.id.screen_shot);
        btn_open = (Button)  popup_view.findViewById(R.id.time_open);
        btn_reopen = (Button)  popup_view.findViewById(R.id.reopen);
        btn_sound = (Button)  popup_view.findViewById(R.id.sound_ctl);
        btn_light = (Button)  popup_view.findViewById(R.id.light_ctl);
        btn_update = (Button)  popup_view.findViewById(R.id.update);
        btn_clean = (Button)  popup_view.findViewById(R.id.clean);


        btn_shot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = null;
                Bitmap bitmap = screenShot(activity);
                if (!isEmptyBitmap(bitmap)) {
                    file = compressImage(bitmap);
                }
                // ????????????
                HashMap<String , String> params = new HashMap<>();
                params.put("user", "admin");

                File finalFile = file;
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        uploadForm(params, "file", finalFile, "ss.jpg", "http://47.99.58.44:8081/file/upload");
                    }
                }).start();
            }
        });
        btn_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLight();
            }
        });
        btn_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new clearCache()).start();
            }
        });
        btn_reopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getPackageManager()
                        .getLaunchIntentForPackage(getApplication().getPackageName());
                PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1?????????????????????
                System.exit(0);
            }
        });
        //??????????????????Dialog
        dialog.setContentView(popup_view);
        //????????????Activity???????????????
        Window dialogWindow = dialog.getWindow();
        //??????Dialog?????????????????????
        dialogWindow.setGravity(Gravity.RIGHT);
        //?????????????????????
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//??????Dialog?????????????????????
        //????????????????????????????????????
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        //????????????????????????
        dialogWindow.setAttributes(lp);
        dialog.show();//???????????????
    }

    private void showLight() {
            dialog = new Dialog(this);
            //????????????????????????
            View popup_view = getLayoutInflater().inflate(R.layout.light_pop, null);
            //????????????
            textView = (TextView) popup_view.findViewById(R.id.tv_light_value);
            seekBar = (SeekBar) popup_view.findViewById(R.id.seekbar_light);
            initBrightness();
            //??????????????????Dialog
            dialog.setContentView(popup_view);
            //????????????Activity???????????????
            Window dialogWindow = dialog.getWindow();
            //??????Dialog?????????????????????
            dialogWindow.setGravity(Gravity.TOP);
            //?????????????????????
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.y = 0;//??????Dialog?????????????????????
            //????????????????????????????????????
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            //????????????????????????
            dialogWindow.setAttributes(lp);
            dialog.show();//???????????????

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    /*?????????????????????????????? */
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        //Log.i(TAG, "????????????");
                    }
                    /*??????????????????????????????*/
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //Log.i(TAG, "????????????");
                    }
                    /* ??????????????????????????????*/
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        textView.setText("????????????" + progress);
                        //???????????????????????????????????????
                        BrightnessUtil.SetSystemLight(progress,MainActivity.this);

            }
    });
    }

    /**
     * ????????????????????????????????????????????????Status Bar??????
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap screenShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int statusBarHeight = getStatusBarHeight(activity);
        int width = (int) getDeviceDisplaySize(activity)[0];
        int height = (int) getDeviceDisplaySize(activity)[1];
        Bitmap ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return ret;
    }

    public static float[] getDeviceDisplaySize(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        float[] size = new float[2];
        size[0] = width;
        size[1] = height;
        return size;
    }

    public static int getStatusBarHeight(Context context) {
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * Bitmap?????????????????????
     */
    public static boolean isEmptyBitmap(Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }
    public  File compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//???????????????????????????100????????????????????????????????????????????????baos???
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //?????????????????????????????????????????????500kb,??????????????????
            baos.reset();//??????baos?????????baos
            options -= 10;//???????????????10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//????????????options%?????????????????????????????????baos???
            long length = baos.toByteArray().length;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        //?????????
        String filename = format.format(date);

        File file = new File( mContext.getFilesDir(), filename+"png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

        Log.i("TAG", "compressImage: " + file);
        // recycleBitmap(bitmap);
        return file;
    }
    @Override
    protected void onResume() {
        super.onResume();

        //mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//??????????????????
        if (client == null) {
            Log.e(TAG, "``````````````````````onResume");
            initWebSocket();
        } else if (!client.isOpen()) {
            //reconnectWs();//????????????????????????????????????
        }
    }
    public void initWebSocket() {
        URI uri = URI.create("ws://47.99.58.44:8081/imserver/Android001");
        //TODO ??????websocket
        client = new JWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
                if (!message.equals("Heartbeat")) {
                    Log.e(TAG, "websocket???????????????" + message);
                    if (message.contains("text")) {
                        if(message.contains("notice")){
                            new FetchNoticeTask().execute(URL_NOTICE + message.substring(31,message.length()-2));
                        }else if(message.contains("reboot")) {
                            Intent intent = getPackageManager()
                                    .getLaunchIntentForPackage(getApplication().getPackageName());
                            PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                            AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1?????????????????????
                            System.exit(0);
                        }else if(message.contains("screen")) {
                            File file = null;
                            Bitmap bitmap = screenShot(activity);
                            if (!isEmptyBitmap(bitmap)) {
                                file = compressImage(bitmap);
                            }
                            // ????????????
                            HashMap<String , String> params = new HashMap<>();
                            params.put("user", "admin");

                            File finalFile = file;
                            new Thread(new Runnable(){
                                @Override
                                public void run() {
                                    uploadForm(params, "file", finalFile, "ss.jpg", "http://47.99.58.44:8081/file/upload");
                                }
                            }).start();
                        }else if(message.contains("clear")) {
                        }
                        else {
                            num = 0;
                            avf_view.stopFlipping();
                            new FetchProgramTask().execute(URL_PROGRAM + message.substring(24,message.length()-2));
                        }
                    }
                }
            }

            @Override
            public void onOpen(ServerHandshake handshakedata) {
                super.onOpen(handshakedata);
                Log.e(TAG, "websocket????????????");

            }

            @Override
            public void onError(Exception ex) {
                super.onError(ex);
                Log.e(TAG, "websocket???????????????" + ex);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                super.onClose(code, reason, remote);
                if (code!=1000) {
                    //reconnectWs();//????????????????????????
                }
                Log.e(TAG, "websocket?????????????????code:" + code + "??reason:" + reason + "??remote:" + remote);
            }
        };
        //TODO ??????????????????
        client.setConnectionLostTimeout(110 * 1000);
        //TODO ??????websocket
        new Thread() {
            @Override
            public void run() {
                try {
                    //connectBlocking?????????????????????????????????????????????????????????????????????????????????
                    client.connectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        sendMsg("{ from:Android001, to:PC001 ,text:1}");
    }

    /**
     * ????????????
     *
     * @param msg
     */
    public void sendMsg(String msg) {
        if (null != client) {
            Log.e("", "^_^Websocket??????????????????-----------------------------------^_^" + msg);
            if (client.isOpen()) {
                client.send(msg);
            }

        }
    }

    /**
     * ????????????
     */
    private void reconnectWs() {
        mHandler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    Log.e("????????????", "");
                    client.reconnectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * ????????????
     */
    private void closeConnect() {
        try {
            //??????websocket
            if (null != client) {
                client.close();
            }
            //????????????
            if (mHandler != null) {
                mHandler.removeCallbacksAndMessages(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }
    }

    private static final long HEART_BEAT_RATE = 10 * 1000;//??????10??????????????????????????????????????????
    private Handler mHandler = new Handler();
    //?????????????????????
    private Runnable timeRunnable = new Runnable(){
        public void run(){
            avf_view.showNext();
            avf_view.stopFlipping();
            if (num >= time.length) {
                num = 0;
            }
            if (!data.isEmpty())
                System.out.println(time[num]+"  "+data.get(num)+"  "+num);
            mHandler.postDelayed(picRunnable,time[num]*1000 );
            mHandler.postDelayed(this, time[num++] * 1000);
        }
    };
    //????????????
    private Runnable picRunnable = new Runnable(){
        public void run(){
            if(!resdata.equals(data)){
                System.out.println(data.get(0));
                ViewFlipperAdapter viewFlipperAdapter = new ViewFlipperAdapter(MainActivity.this, data);
                avf_view.setAdapter(viewFlipperAdapter);
                avf_view.startFlipping();
                resdata = new ArrayList<>(data);
            }
            if (num >= time.length) {
                num = 0;
            }
        }
    };
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (client != null) {
                if (client.isClosed()) {
                    Log.e("???????????????websocket????????????1", "");
                    //reconnectWs();//????????????????????????????????????
                } else {
                    Log.e("???????????????websocket????????????2", "");
                    sendMsg("Heartbeat");
                }
            } else {
                Log.e("???????????????websocket????????????????????????", "");
                //??????client?????????????????????????????????
                client = null;
                initWebSocket();
            }
            //????????????????????????????????????????????????????????????
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    private class FetchProgramTask extends AsyncTask<String, Void, JSONObject> {
        /**
         * @param jsonObject
         * @deprecated
         */
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                if (jsonObject == null) {
                    throw new JSONException("no data available.");
                }
                mHandler.removeCallbacks(timeRunnable);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONObject program = data.getJSONObject("program");
                String programcontent = program.getString("programcontent");
                String resolvingpower = program.getString("resolvingpower");
                if (resolvingpower.equals("1080x1920")){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }else{
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                content = getGsonList(programcontent);
                System.out.println(content);
                time = new int[content.size()];
                for (int i = 0;i<content.size();i++) {
                    time[i]=content.get(i).playbackTime;
                    new FetchPicTask().execute(URL_PIC + content.get(i).materialID);
                }
                mHandler.postDelayed(timeRunnable, 0);
                mHandler.postDelayed(picRunnable, 0);
            } catch (JSONException e) {
                Toast.makeText(
                        MainActivity.this,
                        "There's been a JSON exception: " + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                e.printStackTrace();
            }
        }

        /**
         * @param params
         * @deprecated
         */
        @Override
        protected JSONObject doInBackground(String... params) {
            return new JSONParser().getJSONFromUrl(params[0]);
        }
    }
    private class FetchPicTask extends AsyncTask<String, Void, JSONObject> {
        /**
         * @param jsonObject
         * @deprecated
         */
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                if (jsonObject == null) {
                    throw new JSONException("no data available.");
                }
                JSONObject data1 = jsonObject.getJSONObject("data");
                String equipmentGroup = data1.getString("equipmentGroup");
                String url = equipmentGroup.replaceAll("localhost","10.0.2.2");
                if(resdata.equals(data)){
                    data = new ArrayList<>();
                    data.add(url);
                }else {
                    data.add(url);
                }
            } catch (JSONException e) {
                Toast.makeText(
                        MainActivity.this,
                        "There's been a JSON exception: " + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                e.printStackTrace();
            }
        }

        /**
         * @param params
         * @deprecated
         */
        @Override
        protected JSONObject doInBackground(String... params) {
            return new JSONParser().getJSONFromUrl(params[0]);
        }
    }
    //??????????????????
    private class FetchNoticeTask extends AsyncTask<String, Void, JSONObject> {
        /**
         * @param jsonObject
         * @deprecated
         */
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                if (jsonObject == null) {
                    throw new JSONException("no data available.");
                }
                JSONObject data = jsonObject.getJSONObject("data");
                JSONObject notice = data.getJSONObject("notice");
                System.out.println(notice);
                String noticecontent = notice.getString("noticecontent");
                String playbackspeed = notice.getString("playbackspeed");
                String textposition = notice.getString("textposition");
                String textcolor = notice.getString("textcolor");
                int textsize = notice.getInt("textsize");
                String backgroundcolor = notice.getString("backgroundcolor");
                int backgroundheight = notice.getInt("backgroundheight");
                FrameLayout.LayoutParams layoutparams = new FrameLayout.LayoutParams(100,100);
                marqueeTextView.setTextColor(Color.parseColor(textcolor));
                ViewGroup.MarginLayoutParams margin1 = new ViewGroup.MarginLayoutParams(
                        marqueeTextView.getLayoutParams());
                if (textposition.equals("???")){
                    margin1.setMargins(0, 0, 0, 0);
                }else if (textposition.equals("???")){
                    margin1.setMargins(0, 700, 0, 0);
                }else{
                    margin1.setMargins(0, 1400, 0, 0);
                }
                FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(margin1);
                marqueeTextView.setLayoutParams(layoutParams1);
                if (playbackspeed.equals("???")) {
                    marqueeTextView.setSpeed(10);
                }
                else if(playbackspeed.equals("???")) {
                    marqueeTextView.setSpeed(5);
                }
                else {
                    marqueeTextView.setSpeed(1);
                }
                marqueeTextView.setCoordinateY(180+textsize*5);
                marqueeTextView.setHeight(backgroundheight*10);
                marqueeTextView.setBackgroundColor(Color.parseColor(backgroundcolor));
                marqueeTextView.setTextSize(textsize*5);
                marqueeTextView.setText(noticecontent);
            } catch (JSONException e) {
                Toast.makeText(
                        MainActivity.this,
                        "There's been a JSON exception: " + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
                e.printStackTrace();
            }
        }

        /**
         * @param params
         * @deprecated
         */
        @Override
        protected JSONObject doInBackground(String... params) {
            return new JSONParser().getJSONFromUrl(params[0]);
        }
    }
    class clearCache implements Runnable {
        @Override
        public void run() {
            try {
                CacheDataManager.clearAllCache(MainActivity.this);

                Thread.sleep(1000);

                if (CacheDataManager.getTotalCacheSize(MainActivity.this).startsWith("0")) {

                    mHandler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                return;
            }
        }
    }
    public List<programJson> getGsonList(String json) {
        Gson gson = new Gson();
        String content = null;
        try {
            content = java.net.URLDecoder.decode(json, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return gson.fromJson(content, new TypeToken<List<programJson>>() {
        }.getType());
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}