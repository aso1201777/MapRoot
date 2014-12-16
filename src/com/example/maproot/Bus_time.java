package com.example.maproot;



import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Bus_time extends Activity {

    String b1=null;
    String b2=null;
    String b3=null;
    String b4=null;

    String url = "http://mk1147.esy.es/bus_time.php";

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        b1 = intent.getStringExtra("b1");
        b2 = intent.getStringExtra("b2");
        b3 = intent.getStringExtra("b3");
        b4 = intent.getStringExtra("b4");


        AsyncHttpClient client = new AsyncHttpClient();


        final RequestParams params = new RequestParams();

        params.put("a1",b2 );
        params.put("a2",b1 );
        params.put("a3",b3 );
        params.put("a4",b4 );


        // phpへ送信
        client.post(url, params, new MyAsyncHttpResponseHandler());

    }

    // phpからの応答に反応するクラス
    public class MyAsyncHttpResponseHandler extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] response)  {
            // called when response HTTP status is "200 OK"


            try{
                String res = new String(response);
                JSONArray json_array_org = new JSONArray(res);
                //                JSONObject json_object = new JSONObject(res);
                JSONObject json_object = json_array_org.getJSONObject(0);
                JSONArray json_array = json_object.getJSONArray("json_array");

                // JSONArrayからリストを作る
                int max = json_array.length();
                ArrayList<String> time_list = new ArrayList<String>();
                for(int i=0; i<max; i++){
                    time_list.add(json_array.getString(i));
                }
                // json_array
                //JSONArray json_array = new JSONArray(res);
                //JSONObject firstEvent = (JSONObject)json_array.get(0);
                //String bus_stop_name = firstEvent.getString("bus_stop_name");



                // 画面遷移
                Intent i = new Intent(Bus_time.this, Bus_time2.class);
                // Intent i = new Intent(getApplicationContext(),Timetable.class);
                // i.putExtra("bus_list", bus_list);
                i.putStringArrayListExtra("time_list", time_list);
                i.putExtra("b1",b1);
                i.putExtra("b2",b2);
                i.putExtra("b3",b3);

                startActivity(i);
            }
            catch(JSONException je){
                // JSONエラー時はここでキャッチ
                je.printStackTrace();
            }

        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            int errcode = statusCode;
            String aaa = "";
            Log.d("debug", "statusCode = " + statusCode);
            Log.d("debug","error = " );
            e.printStackTrace();
            // Log.d("debug","headers = " + headers.toString());
        }
    }}
