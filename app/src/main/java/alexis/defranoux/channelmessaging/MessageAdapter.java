package alexis.defranoux.channelmessaging;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by defranoa on 30/01/2017.
 */
public class MessageAdapter extends ArrayAdapter<Message> {

    TextView txtMessage;
    TextView txtNom;
    TextView txtDate;
    ImageView imageView;
    private Context context;

    public MessageAdapter(Context context, int textViewResourceId, List<Message> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_message, parent, false);

        txtMessage = (TextView) rowView.findViewById(R.id.txtMessage);
        txtMessage.setText(getItem(position).message);

        txtDate = (TextView) rowView.findViewById(R.id.txtDate);
        txtDate.setText(getItem(position).date);

        txtNom = (TextView) rowView.findViewById(R.id.txtNom);
        txtNom.setText(getItem(position).username);

        imageView = (ImageView) rowView.findViewById(R.id.imageView);

        //Image sans cercle
        //Glide.with(getContext()).load(getItem(position).imageUrl).into(imageView);

        Glide.with(getContext())
                .load(getItem(position).imageUrl)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(imageView);

        return rowView;
    }

}
