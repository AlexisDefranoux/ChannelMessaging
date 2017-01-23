package alexis.defranoux.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener {

    private Button btValider;
    private TextView txtId;
    private TextView txtMdp;
    private EditText edId;
    private EditText edMdp;
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btValider = (Button) findViewById(R.id.btValider);
        btValider.setOnClickListener(this);

        txtId = (TextView) findViewById(R.id.txtId);
        txtMdp = (TextView) findViewById(R.id.txtMdp);

        edId = (EditText) findViewById(R.id.edId);
        edMdp = (EditText) findViewById(R.id.edMdp);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btValider)
        {
            HashMap<String, String> connectInfo = new HashMap<>();
            connectInfo.put("username", edId.getText().toString());
            connectInfo.put("password", edMdp.getText().toString());
            Async Async = new Async(getApplicationContext(), connectInfo,"http://www.raphaelbischof.fr/messaging/?function=connect");
            Async.setOnDownloadCompleteListener(this);
            Async.execute();
        }
    }

    @Override
    public void onDownloadComplete(String result) {
        Gson gson = new Gson();
        Callback obj = gson.fromJson(result, Callback.class);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("accesstoken", obj.accesstoken);
        editor.commit();

        if(obj.code !=200)
            Toast.makeText(getApplicationContext(),"Erreur !",Toast.LENGTH_SHORT).show();
        else {
            Intent myIntent = new Intent(getApplicationContext(), ChannelListActivity.class);
            startActivity(myIntent);
        }
    }
}