package com.example.maproot;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Time extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    String sw = "1";
    Button search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time);

        /*
        // Listの作成
        ArrayList<String> list = new ArrayList<String>();
        // Listにデータを入れる
        list.add("博多駅前");
        list.add("駅前四丁目");
        list.add("TVQ前");
        list.add("キャナルシティ博多前");
        list.add("南新地");
        list.add("春吉");
        list.add("天神バスセンター前");
        list.add("天神（大和証券前）");
        list.add("市役所北口");
        list.add("東中洲");
        list.add("川端町・博多座前");
        list.add("土居町");
        list.add("呉服町");
        list.add("奥の堂");
        list.add("祇園町");
        list.add("駅前一丁目");

        // Adapterの作成
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        // ドロップダウンのレイアウトを指定
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // ListViewにAdapterを関連付ける
        Spinner spinner = (Spinner) findViewById(R.id.bus_stop);
        spinner.setAdapter(adapter);

*/


    }


    @Override
    protected void onResume() {
        super.onResume();

        ImageButton search = (ImageButton)findViewById(R.id.search);
        search.setOnClickListener(this);

        ImageButton button4 = (ImageButton)findViewById(R.id.button4);
        button4.setOnClickListener(this);


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);

        radioGroup.setOnCheckedChangeListener(this);
        // 指定した ID のラジオボタンをチェックします
        radioGroup.check(R.id.Out);


    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        RadioButton radioButton = (RadioButton) findViewById(checkedId);
        // ラジオボタンの選択状態を取得
        RadioButton radioButton = (RadioButton) findViewById(checkedId);
        // getId()でラジオボタンを識別し、ラジオボタンごとの処理を行う
        boolean checked = radioButton.isChecked();
        switch (radioButton.getId()) {
            case R.id.Out:
                if (checked) {
                    // Listの作成
                    ArrayList<String> list = new ArrayList<String>();
                    // Listにデータを入れる
                    list.add("博多バスターミナル");
                    list.add("駅前一丁目");
                    list.add("祇園町");
                    list.add("奥の堂");
                    list.add("呉服町");
                    list.add("土居町");
                    list.add("川端町・博多座前");
                    list.add("東中洲");
                    list.add("市役所北口");
                    list.add("天神コア前");
                    list.add("天神大丸前");
                    list.add("天神一丁目");
                    list.add("春吉");
                    list.add("南新地");
                    list.add("キャナルイーストビル前");
                    list.add("TVQ前");
                    list.add("駅前四丁目");

                    // Adapterの作成
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
                    // ドロップダウンのレイアウトを指定
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // ListViewにAdapterを関連付ける
                    Spinner spinner = (Spinner) findViewById(R.id.bus_stop);
                    spinner.setAdapter(adapter);

                    sw = "1";
                }
                break;
            case R.id.In:
                if (checked) {
                    // Listの作成
                    ArrayList<String> list = new ArrayList<String>();
                    // Listにデータを入れる

                    list.add("博多駅前");
                    list.add("駅前四丁目");
                    list.add("TVQ前");
                    list.add("キャナルシティ博多前");
                    list.add("南新地");
                    list.add("春吉");
                    list.add("天神バスセンター前");
                    list.add("天神（大和証券前）");
                    list.add("市役所北口");
                    list.add("東中洲");
                    list.add("川端町・博多座前");
                    list.add("土居町");
                    list.add("呉服町");
                    list.add("奥の堂");
                    list.add("祇園町");
                    list.add("駅前一丁目");


                    // Adapterの作成
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
                    // ドロップダウンのレイアウトを指定
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // ListViewにAdapterを関連付ける
                    Spinner spinner = (Spinner) findViewById(R.id.bus_stop);
                    spinner.setAdapter(adapter);

                    sw = "2";
                }
                break;
            default:
                break;
        }
    }


    String url = "http://mk1147.esy.es/bus_time.php";

    public void onClick(View v) {
        switch(v.getId()){

		case R.id.button4: // button3が押された
			// 戻るボタン
			Intent in = new Intent(Time.this, Top.class);
			//次画面のアクティビティ
			startActivity(in);
			break;


            case R.id.search:
                Integer h = ((TimePicker)findViewById(R.id.bus_time)).getCurrentHour();
                Integer m = ((TimePicker)findViewById(R.id.bus_time)).getCurrentMinute();

                String hour,minute;
                if(h < 10){
                    hour = "0" + String.valueOf(h);
                }else{
                    hour = String.valueOf(h);
                }

                if(m < 10){
                    minute = "0" + String.valueOf(m);
                }else{
                    minute = String.valueOf(m);
                }


                String t_bus = hour + minute;


                Spinner spinner = (Spinner)findViewById(R.id.bus_stop);
                String bus = (String) spinner.getSelectedItem();


                Calendar calendar = Calendar.getInstance();


                // 祝日判定
                Date dt = calendar.getTime();
                String res = Holiday.queryHoliday(dt);

                int weekday = 0;
                if(res==null){
                    // 祝日じゃない場合

                    // 曜日を取得
                    switch(calendar.get(Calendar.DAY_OF_WEEK)){
                        // 日曜
                        case 1:
                            weekday = 3;
                            break;
                        // 月曜
                        case 2:
                            weekday = 1;
                            break;
                        // 火曜
                        case 3:
                            weekday = 1;
                            break;
                        // 水曜
                        case 4:
                            weekday = 1;
                            break;
                        // 木曜
                        case 5:
                            weekday = 1;
                            break;
                        // 金曜
                        case 6:
                            weekday = 1;
                            break;
                        // 土曜
                        case 7:
                            weekday = 2;
                            break;
                    }

                }else{
                    // 祝日の場合
                    weekday = 3;
                }

                AsyncHttpClient client = new AsyncHttpClient();

                if (v.getId() == R.id.search) {


                    final RequestParams params = new RequestParams();

                    Log.d("sw", sw);
                    params.put("a1",t_bus );
                    params.put("a2",bus );
                    params.put("a3",sw );
                    params.put("a4",weekday );


                    // phpへ送信
                    client.post(url, params, new MyAsyncHttpResponseHandler());
                }
            break;
        }
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

                Spinner spinner = (Spinner)findViewById(R.id.bus_stop);
                String bus = (String) spinner.getSelectedItem();


                Integer h = ((TimePicker)findViewById(R.id.bus_time)).getCurrentHour();
                Integer m = ((TimePicker)findViewById(R.id.bus_time)).getCurrentMinute();

                String hour,minute;
                if(h < 10){
                    hour = "0" + String.valueOf(h);
                }else{
                    hour = String.valueOf(h);
                }

                if(m < 10){
                    minute = "0" + String.valueOf(m);
                }else{
                    minute = String.valueOf(m);
                }


                String t_bus = hour + minute;

                // 画面遷移
                Intent i = new Intent(Time.this, Bus.class);
                // Intent i = new Intent(getApplicationContext(),Timetable.class);
                // i.putExtra("bus_list", bus_list);
                i.putStringArrayListExtra("time_list", time_list);
                i.putExtra("b1",bus);
                i.putExtra("b2",t_bus);
                i.putExtra("b3",sw);

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
    }
}

