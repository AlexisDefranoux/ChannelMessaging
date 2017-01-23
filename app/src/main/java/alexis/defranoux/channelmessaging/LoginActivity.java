package alexis.defranoux.channelmessaging;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnDownloadCompleteListener {

    private Button btValider;
    private TextView txtId;
    private TextView txtMdp;
    private EditText edId;
    private EditText edMdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btValider = (Button) findViewById(R.id.btValider);
        //btValider.setOnClickListener(this);

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
            connectInfo.put("id", edId.getText().toString());
            connectInfo.put("mdp", edMdp.getText().toString());
            Async Async = new Async(getApplicationContext(), connectInfo);
            Async.setOnDownloadCompleteListener(this);
            Async.execute();
        }

    }

    @Override
    public void onDownloadComplete(String result) {
        Gson gson = new Gson();
        Callback obj = gson.fromJson(result, Callback.class);

    }
}