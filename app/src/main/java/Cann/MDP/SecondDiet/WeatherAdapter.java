package Cann.MDP.SecondDiet;
//William Cann - S2125914

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class WeatherAdapter extends ArrayAdapter<Weather>
{
    //declaring variables and arrays
    private final Context context;
    private final ArrayList<Weather> values;
    Bitmap b;

    //constructor
    public WeatherAdapter(Context context, ArrayList<Weather> list)
    {
        super(context, R.layout.row_layout, list);
        this.context = context;
        this.values = list;
    }
    //inserting the information into the custom list view
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);

        //declaring the text views in the custom list view
        TextView tvDay = rowView.findViewById(R.id.tvDay);
        TextView tvPubDate = rowView.findViewById(R.id.tvPubDate);
        TextView tvDesc = rowView.findViewById(R.id.tvDescription);
        ImageView ivDay = rowView.findViewById(R.id.ivDay);
        //setting the text inside the custom list view place holders according to the getters in the Weather class
        tvDay.setText(values.get(position).getTitle());
        tvPubDate.setText(values.get(position).getPubDate());
        tvDesc.setText(values.get(position).getDesc());

        try
        {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)
                    new URL(Weather.getImage()).getContent());
            ivDay.setImageBitmap(bitmap);
        }
        catch (IOException e) {e.printStackTrace();}


        return rowView;
    }

}