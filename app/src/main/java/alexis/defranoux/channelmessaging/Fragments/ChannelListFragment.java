package alexis.defranoux.channelmessaging.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

import alexis.defranoux.channelmessaging.Async;
import alexis.defranoux.channelmessaging.ChannelAdapter;
import alexis.defranoux.channelmessaging.Channels;
import alexis.defranoux.channelmessaging.MessageActivity;
import alexis.defranoux.channelmessaging.OnDownloadCompleteListener;
import alexis.defranoux.channelmessaging.R;

/**
 * Created by defranoa on 27/02/2017.
 */
public class ChannelListFragment extends Fragment implements OnDownloadCompleteListener, AdapterView.OnItemClickListener {

    private ListView listView;
    public static final String PREFS_NAME = "MyPrefsFile";
    public Channels obj;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.channel_list_fragment,container);
        listView = (ListView)v.findViewById(R.id.listView);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        HashMap<String, String> connectInfo = new HashMap<>();
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        connectInfo.put("accesstoken", settings.getString("accesstoken",""));
        Async async = new Async(getActivity(), connectInfo,"http://www.raphaelbischof.fr/messaging/?function=getchannels",0);
        async.setOnDownloadCompleteListener(this);
        async.execute();
    }

    public void onDownloadComplete(String result, int requestCode) {
        Gson gson = new Gson();
        obj = gson.fromJson(result, Channels.class);
        listView.setAdapter(new ChannelAdapter(getActivity(), R.layout.channel_list_fragment, R.layout.rowlayout, obj.channels));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(getActivity(), MessageActivity.class);
        myIntent.putExtra("id", obj.channels.get(position).channelID);
        startActivity(myIntent);
    }
}
