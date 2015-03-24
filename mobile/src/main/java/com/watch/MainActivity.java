package com.watch;

import android.app.Activity;
//import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.watch.R;

import java.util.ArrayList;
import java.util.List;

//adb -d forward tcp:5601 tcp:5601
public class MainActivity extends Activity implements
        DataApi.DataListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private int count2 = 0;
    List<String> list;
    private TextView mTextView;


    private static final String COUNT_KEY = "com.example.key.count";

    private GoogleApiClient mGoogleApiClient;

    // Create a data map and put data in it
    private void increaseCounter() {
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/count");
        putDataMapReq.getDataMap().putInt(COUNT_KEY, count2++);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        PendingResult<DataApi.DataItemResult> pendingResult =
                Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);


        Toast.makeText(getApplicationContext(),"Increase "+count2,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.mtext);

        list = new ArrayList<>();
        list.add("item 1");
        list.add("item 2");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();

        ((Button) findViewById(R.id.bt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Bt","click ");
                increaseCounter();
            }
        });
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.d("Mobile>>>", "onConnected");
        mTextView.setText("Conection Conected");
        Toast.makeText(getApplicationContext(),"Connected ",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Mobile>>>", "onConnectionSuspended");
        Toast.makeText(getApplicationContext(),"Connect Suspended ",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        Log.d("Mobile>>>", "onDataChanged");
        Toast.makeText(getApplicationContext(),"Data Changed ",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Mobile>>>", "onConnectionFailed");
        Toast.makeText(getApplicationContext(),"Connected FAILED ",Toast.LENGTH_LONG).show();
    }
}
