package com.example.maproot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity  extends FragmentActivity {

    GoogleMap gMap;
    private static final int MENU_A = 0;
    private static final int MENU_B = 1;
    private static final int MENU_c = 2;

    public static String posinfo = "";
    public static String info_A = "";
    public static String info_B = "";
    ArrayList<LatLng> markerPoints;

    public static MarkerOptions options;

    public ProgressDialog progressDialog;

    public String travelMode = "driving";//default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //プログレス
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("検索中です......");
        progressDialog.hide();


        //初期化
        markerPoints = new ArrayList<LatLng>();


        SupportMapFragment mapfragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);


        gMap = mapfragment.getMap();

    //初期位置
        LatLng location = new LatLng(33.590205 ,130.419238);

        LatLng e_l = new LatLng(33.590638, 130.412498);

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        MarkerOptions options = new MarkerOptions();
        MarkerOptions end = new MarkerOptions();

        options.position(location);
        options.title("博多駅前");
        options.snippet(location.toString());

        end.position(e_l);
        end.title("キャナルシティ博多前");
        end.snippet(e_l.toString());


        // マップにマーカーを追加
        gMap.addMarker(options);
        gMap.addMarker(end);




        }



    private void routeSearch(){
        progressDialog.show();

        LatLng origin = markerPoints.get(0);
        LatLng dest = markerPoints.get(1);


        String url = getDirectionsUrl(origin, dest);

        DownloadTask downloadTask = new DownloadTask();


        downloadTask.execute(url);

    }

    private String getDirectionsUrl(LatLng origin,LatLng dest){


        String str_origin = "origin="+origin.latitude+","+origin.longitude;


        String str_dest = "destination="+dest.latitude+","+dest.longitude;


        String sensor = "sensor=false";

        //パラメータ
        String parameters = str_origin+"&"+str_dest+"&"+sensor + "&language=ja" + "&mode=" + travelMode;

        //JSON指定
        String output = "json";


        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);


            urlConnection = (HttpURLConnection) url.openConnection();


            urlConnection.connect();


            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


    private class DownloadTask extends AsyncTask<String, Void, String>{
    //非同期で取得

        @Override
        protected String doInBackground(String... url) {


            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }


        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);
        }
    }

    /*parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{


        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                parseJsonpOfDirectionAPI parser = new parseJsonpOfDirectionAPI();


                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        //ルート検索で得た座標を使って経路表示
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {


            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            if(result.size() != 0){

                for(int i=0;i<result.size();i++){
                    points = new ArrayList<LatLng>();
                    lineOptions = new PolylineOptions();


                    List<HashMap<String, String>> path = result.get(i);


                    for(int j=0;j<path.size();j++){
                        HashMap<String,String> point = path.get(j);

                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);

                        points.add(position);
                    }

                    //ポリライン
                    lineOptions.addAll(points);
                    lineOptions.width(10);
                    lineOptions.color(0x550000ff);

                }

                //描画
                gMap.addPolyline(lineOptions);
            }else{
                gMap.clear();
                Toast.makeText(MainActivity.this, "ルート情報を取得できませんでした", Toast.LENGTH_LONG).show();
            }
            progressDialog.hide();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        menu.add(0, MENU_A,   0, "Info");
        menu.add(0, MENU_B,   0, "Legal Notices");
        menu.add(0, MENU_c,   0, "Mode");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() )
        {
            case MENU_A:
                //show_mapInfo();
                return true;

            case MENU_B:
                //Legal Notices(免責事項)

                String LicenseInfo = GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(getApplicationContext());
                AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(MainActivity.this);
                LicenseDialog.setTitle("Legal Notices");
                LicenseDialog.setMessage(LicenseInfo);
                LicenseDialog.show();

                return true;

            case MENU_c:
                //show_settings();
                return true;

            }
        return false;
    }

    //リ･ルート検索
    private void re_routeSearch(){
        progressDialog.show();

        LatLng origin = markerPoints.get(0);
        LatLng dest = markerPoints.get(1);

        //
        gMap.clear();

        //マーカー
        //A
        options = new MarkerOptions();
        options.position(origin);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.green));
        options.title("出発地");
        options.draggable(true);
        gMap.addMarker(options);
        //B
        options = new MarkerOptions();
        options.position(dest);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.red));
        options.title("到着地");
        options.draggable(true);
        gMap.addMarker(options);


        String url = getDirectionsUrl(origin, dest);

        DownloadTask downloadTask = new DownloadTask();


        downloadTask.execute(url);

    }
}

