package alexis.defranoux.channelmessaging.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import alexis.defranoux.channelmessaging.Async;
import alexis.defranoux.channelmessaging.Channels;
import alexis.defranoux.channelmessaging.MessageAEnvoyer;
import alexis.defranoux.channelmessaging.MessageActivity;
import alexis.defranoux.channelmessaging.MessageAdapter;
import alexis.defranoux.channelmessaging.Messages;
import alexis.defranoux.channelmessaging.OnDownloadCompleteListener;
import alexis.defranoux.channelmessaging.R;

/**
 * Created by defranoa on 30/01/2017.
 */
public class MessageFragment extends Fragment implements OnDownloadCompleteListener, View.OnClickListener{

    public ListView listView;
    public Button btEnvoyer;
    public EditText edMessage;
    public static final String PREFS_NAME = "MyPrefsFile";
    public int id =-1;
    public Handler handler = new Handler();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.channel_message_fragment,container);
        listView = (ListView)v.findViewById(R.id.ltMessage);
        btEnvoyer = (Button)v.findViewById(R.id.btEnvoyer);
        return v;
    }

    public void changChannelId(int channelId){
        id = channelId;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        id = getActivity().getIntent().getIntExtra("id",-1);
        btEnvoyer.setOnClickListener(this);

        Runnable r = new Runnable() {
            public void run() {
                if(id != -1) {
                    HashMap<String, String> connectInfo = new HashMap<>();

                    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                    connectInfo.put("accesstoken", settings.getString("accesstoken", ""));
                    connectInfo.put("channelid", String.valueOf(id));

                    Async async = new Async(getActivity(), connectInfo, "http://www.raphaelbischof.fr/messaging/?function=getmessages", 1);
                    async.setOnDownloadCompleteListener(MessageFragment.this);
                    async.execute();

                }
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);

    }


    public void onDownloadComplete(String result, int requestCode) {

        Gson gson = new Gson();

        if(requestCode == 1) {

            Messages obj = gson.fromJson(result, Messages.class);
            int index = listView.getFirstVisiblePosition();
            View v = listView.getChildAt(0);
            int top = (v == null) ? 0 : (v.getTop() - listView.getPaddingTop());
            listView.setAdapter(new MessageAdapter(getActivity(), R.layout.channel_message_fragment, obj.messages));
            listView.setSelectionFromTop(index, top);

        }else {

            MessageAEnvoyer obj = gson.fromJson(result, MessageAEnvoyer.class);

            if(obj.code == 200) {

                Toast.makeText(getActivity(),"Message envoyé !",Toast.LENGTH_SHORT).show();
                edMessage.setText("");

            }

            else{

                Toast.makeText(getActivity(),"Erreur !",Toast.LENGTH_SHORT).show();

            }
        }
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btEnvoyer) {

            HashMap<String, String> connectInfo = new HashMap<>();
            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
            connectInfo.put("accesstoken", settings.getString("accesstoken",""));
            connectInfo.put("channelid", String.valueOf(id) );
            connectInfo.put("message", edMessage.getText().toString());
            Async Async = new Async(getActivity(), connectInfo,"http://www.raphaelbischof.fr/messaging/?function=sendmessage",2);
            Async.setOnDownloadCompleteListener(this);
            Async.execute();

        }
    }
}
