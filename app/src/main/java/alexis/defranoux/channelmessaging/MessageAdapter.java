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

/**
 * Created by defranoa on 30/01/2017.
 */
public class MessageAdapter extends ArrayAdapter<Message> {

    TextView txtMessage;
    TextView txtNom;
    TextView txtDate;
    ImageView imageView;

    public MessageAdapter(Context context, int textViewResourceId, List<Message> objects) {
        super(context, textViewResourceId, objects);
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
        /*Drawable drawable  = Drawable.createFromPath(getItem(position).imageUrl);
        imageView.setImageDrawable(drawable);*/

        Glide.with(getContext()).load(getItem(position).imageUrl).into(imageView);

        return rowView;
    }

}
