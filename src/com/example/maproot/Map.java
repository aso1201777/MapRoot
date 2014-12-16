package com.example.maproot;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class Map extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    String sw = "1";
    Button search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

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
                    Spinner spinner2 = (Spinner) findViewById(R.id.bus_stop2);
                    spinner2.setAdapter(adapter);

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
                    Spinner spinner2 = (Spinner) findViewById(R.id.bus_stop2);
                    spinner2.setAdapter(adapter);

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
			Intent in = new Intent(Map.this, Top.class);
			//次画面のアクティビティ
			startActivity(in);
			break;


            case R.id.search:
            	Intent i = new Intent(Map.this, MainActivity.class);
            	startActivity(i);
        }
    }


}

