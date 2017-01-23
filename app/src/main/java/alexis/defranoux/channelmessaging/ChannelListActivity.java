package alexis.defranoux.channelmessaging;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by defranoa on 23/01/2017.
 */
public class ChannelListActivity extends AppCompatActivity {

    private ListView listView;
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_list_activity);

        listView = (ListView) findViewById(R.id.listView);

        HashMap<String, String> connectInfo = new HashMap<>();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        connectInfo.put("accesstoken", settings.getString("accesstoken",""));
        Async Async = new Async(getApplicationContext(), connectInfo,"http://www.raphaelbischof.fr/messaging/?function=getchannels");
        Async.setOnDownloadCompleteListener(this);
        Async.execute();
    }

    public void onDownloadComplete(String result) {
        Gson gson = new Gson();
        Channel obj = gson.fromJson(result, Channel.class);
    }
}
