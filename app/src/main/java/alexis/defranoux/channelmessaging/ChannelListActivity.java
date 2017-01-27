package alexis.defranoux.channelmessaging;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by defranoa on 23/01/2017.
 */
public class ChannelListActivity extends AppCompatActivity implements OnDownloadCompleteListener, AdapterView.OnItemClickListener {

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
        Async async = new Async(getApplicationContext(), connectInfo,"http://www.raphaelbischof.fr/messaging/?function=getchannels");
        async.setOnDownloadCompleteListener(this);
        async.execute();
    }

    public void onDownloadComplete(String result) {
        Gson gson = new Gson();
        Channels obj = gson.fromJson(result, Channels.class);
        listView.setAdapter(new ChannelAdapter(getApplicationContext(), R.layout.channel_list_activity, R.layout.rowlayout, obj.channels));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
