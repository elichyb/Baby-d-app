package com.elichy.baby_d.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.elichy.baby_d.Globals;
import com.elichy.baby_d.Models.ResAPIHandler;
import com.elichy.baby_d.R;
import com.elichy.baby_d.ViewAdds.BabySectionRecyclerViewAdapter;

import java.util.UUID;

import retrofit2.Retrofit;

public class BabyViewActivity extends AppCompatActivity implements BabySectionRecyclerViewAdapter.OnSectionListner
{
    private String[] babySections;
    private int colors[];
    private static final String TAG = "BabyViewActivity";
    private RecyclerView babySectionList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Retrofit retrofit;
    private String token;
    public static final String SHARED_PREFS = "sharedPrefs";
    private ResAPIHandler resAPIHandler;
    private UUID baby_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_view);
        Log.d(TAG, "onCreate: Start successfully");
        setInit();
        
    }

    private void setInit() {
        baby_id = UUID.fromString(getIntent().getStringExtra(Globals.BABY_ID));
        babySectionList = (RecyclerView) findViewById(R.id.babySectionList);
        colors = new int[]{R.color.pastelGreen, R.color.pasteRed, R.color.pasteYellow, R.color.pasteBlue};
        babySectionList.setHasFixedSize(true);
        babySections = getResources().getStringArray(R.array.babySections);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new BabySectionRecyclerViewAdapter(babySections, colors, this);
        babySectionList.setLayoutManager(mLayoutManager);
         babySectionList.setAdapter(mAdapter);
        loadData();
    }

    @Override
    public void onSectionClicked(int pos) {
        Intent intent;
        Log.d(TAG, "onSectionClicked: clicked on " + babySections[pos]);
        switch (babySections[pos]){
            case "Current Weight":
                Log.d(TAG, "onSectionClicked: Goes into current weight");
                intent = new Intent(this, BabyWeight.class);
                intent.putExtra("weight", Double.toString(0.0)); //todo get baby current weight for this.
                startActivity(intent);
                break;

            case "Diaper":
                Log.d(TAG, "onSectionClicked: Goes into dia[er activity");
                intent = new Intent(this, BabyDiaper.class);
                intent.putExtra("wetDiapers",   Double.toString(0.0)); //todo get baby current weight for this.
                intent.putExtra("dirtyDiapers", Double.toString(0.0)); //todo get baby current weight for this.
                startActivity(intent);
                break;
        }
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        this.token = sharedPreferences.getString(Globals.TOKEN,"");
    }
}