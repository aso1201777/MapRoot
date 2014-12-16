package com.example.maproot;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


public class Bus extends Activity implements OnClickListener{

    private ListView bus_time_show;
    String b1=null;
    String b2=null;
    String b3=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = getIntent();
            List time_list_array = i.getStringArrayListExtra("time_list");
            //　レイアウトに記載したSpinnerを取得し、選択肢を設定
            bus_time_show = (ListView)this.findViewById(R.id.bus_time_show);
            // Stringの配列をリストに変換
            // List list= Arrays.asList(bus_list_array);
            // Stringのリストをスピナーに登録



            b1 = i.getStringExtra("b1");
            b2 = i.getStringExtra("b2");
            b3 = i.getStringExtra("b3");
            TextView tv1 = (TextView)findViewById(R.id.text);

            tv1.setText( b1 + "発" );

            this.setListView(time_list_array);

         // buttonを取得
            ImageButton button1 = (ImageButton) findViewById(R.id.button1);
            button1.setOnClickListener(this);
            ImageButton button2 = (ImageButton) findViewById(R.id.button2);
            button2.setOnClickListener(this);
            ImageButton button3 = (ImageButton) findViewById(R.id.button3);
            button3.setOnClickListener(this);
            ImageButton button4 = (ImageButton) findViewById(R.id.button4);
            button4.setOnClickListener(this);

    }

    private void setListView(List<String> bus_list){

        // Listに登録するアダプタを作る
        ArrayAdapter<String> bus_stop_list = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item
        );
        bus_stop_list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int max = bus_list.size();
        //　選択肢を登録
        for(int i=0; i<max; i++){
            bus_stop_list.add(bus_list.get(i));
        }
        // スピナーに登録
        bus_time_show.setAdapter(bus_stop_list);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timetable_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {






        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		// 生成して代入用のIntentインスタンス変数を用意
		Intent intent = null;
		switch(v.getId()){ //どのボタンが押されたか判定
			case R.id.button1: // button1が押された
			// 平日
			// インテントのインスタンス生成
			intent = new Intent(Bus.this, Bus_time.class);
			Log.d("check", b1);
			intent.putExtra("b1",b1);
			intent.putExtra("b2",b2);
			intent.putExtra("b3",b3);
			intent.putExtra("b4","1");
			//次画面のアクティビティ
			startActivity(intent);
			break;

			case R.id.button2: // button2が押された
			// 土曜
			// インテントのインスタンス生成
			intent = new Intent(Bus.this, Bus_time.class);
			Log.d("check", b1);
			intent.putExtra("b1",b1);
			intent.putExtra("b2",b2);
			intent.putExtra("b3",b3);
			intent.putExtra("b4","2");
			//次画面のアクティビティ
			startActivity(intent);
			break;

			case R.id.button3: // button3が押された
			// 日曜・祝日
			// インテントのインスタンス生成
	    		intent = new Intent(Bus.this, Bus_time.class);
				Log.d("check", b1);
				intent.putExtra("b1",b1);
				intent.putExtra("b2",b2);
				intent.putExtra("b3",b3);
				intent.putExtra("b4","3");
				startActivity(intent);
			break;

			case R.id.button4: // button3が押された
				// 戻るボタン
				intent = new Intent(Bus.this, Time.class);
				//次画面のアクティビティ
				startActivity(intent);
				break;

		}

    }


}
