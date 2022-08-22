package Cann.MDP.SecondDiet;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class WeatherAdapter extends ArrayAdapter<Weather>
{
    //declaring variables and arrays
    private final Context context;
    private final ArrayList<Weather> values;

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
        //changing the imageView to a different image in the drawable folder
        switch (values.get(position).getTitle()) {
            case "Sunny":
                ivDay.setImageResource(R.drawable.day_clear);
                break;
            case "Partly Cloudy":
                ivDay.setImageResource(R.drawable.cloudy);
                break;
            case "rain":
                ivDay.setImageResource(R.drawable.day_rain);
                break;
        }
        return rowView;
    }
}