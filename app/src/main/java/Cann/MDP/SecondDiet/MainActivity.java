package Cann.MDP.SecondDiet;
//William Cann - S2125914

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity
{
    ListView lvRss;
    ArrayList<Weather> list;
    private Button button;
    int i, eventType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvRss = findViewById(R.id.lvRss);
        button = findViewById(R.id.button);
        list = new ArrayList<>();    //array list that stores information from the Weather class

        //setting up the button with a counter that enables user to cycle through a list of links
        button.setOnClickListener(view -> {
           i++;
           if(i == 7) {i = 1;} //simple loop that restarts back to 1 after user cycles through list of links
           new ProcessInBackground().execute();
           list.clear();
        });
    }

    //connecting application to the open connection
    public InputStream getInputStream(URL url)
    {
        try
        {
            return url.openConnection().getInputStream();
        }
        catch (IOException e) {return null;}
    }

    //loading information on link
    @SuppressLint("StaticFieldLeak")
    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception>
    {
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        Exception exception = null;

        @Override
        protected void onPreExecute()       //displays message while rss feed is loading data from rss link
        {
            super.onPreExecute();
            pd.setMessage("Loading RSS feed");
            pd.show();
        }

        //using the pull parser to add information into list view to display title, description and pubDate items in rss feed

        @Override
        @SuppressLint("SetTextI18n")
        protected Exception doInBackground(Integer... params)
        {
            try {
                //establishing the links
                URL Glasgow = new URL("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2648579");
                URL London = new URL("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643743");
                URL NewYork = new URL("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/5128581");
                URL Oman = new URL("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/287286");
                URL Mauritius = new URL("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154");
                URL Bangladesh = new URL("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/1185241");

                //setting up the pull parser
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                //once button is pressed counter is incremented then displays the information related to the counter
                if(i == 1)
                {
                    button.setText("Glasgow");
                    xpp.setInput(getInputStream(Glasgow), "UTF-8");
                }
                else if(i==2)
                {
                    button.setText("London");
                    xpp.setInput(getInputStream(London), "UTF-8");
                }
                else if(i==3)
                {
                    button.setText("New York");
                    xpp.setInput(getInputStream(NewYork), "UTF-8");
                }
                 else if(i==4)
                {
                   button.setText("Oman");
                   xpp.setInput(getInputStream(Oman), "UTF-8");
                }
                 else if(i==5)
                {
                    button.setText("Mauritius");
                    xpp.setInput(getInputStream(Mauritius), "UTF-8");
                }
                else if (i==6)
                {
                    button.setText("Bangladesh");
                    xpp.setInput(getInputStream(Bangladesh), "UTF-8");
                }

                //inputting the url to receive the information from the rss feed
                boolean insideItem = false;
                eventType = xpp.getEventType();
                String title = null, desc = null, pubDate = null, image = null;
                while (eventType != XmlPullParser.END_DOCUMENT)     //while loop ensuring that the xml parser is not at the end of the document
                {
                    if (eventType == XmlPullParser.START_TAG)
                    {
                        if (xpp.getName().equalsIgnoreCase("image"))     //the pull parser must first initialize if it is in the correct tag in this case the tag is item<>
                        {
                        insideItem = true;
                            if (xpp.getName().equalsIgnoreCase("url"))     //the pull parser must first initialize if it is in the correct tag in this case the tag is item<>
                            {
                                image = xpp.nextText();
                            }
                        }

                        if (xpp.getName().equalsIgnoreCase("item"))     //the pull parser must first initialize if it is in the correct tag in this case the tag is item<>
                        {
                            insideItem = true;
                        }
                        else if (xpp.getName().equalsIgnoreCase("title"))   //this element adds the text from the title <tag> to the setTitle variable in the Weather class
                        {
                            if (insideItem)
                            {
                                title = xpp.nextText();
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("description"))     //this element adds the text from the description <tag> to the setDesc variable in the Weather class
                        {
                            if (insideItem)
                            {
                                desc = xpp.nextText();
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("pubDate"))      //this element adds the text from the pubDate <tag> to the setPubDate variable in the Weather class
                        {
                            if (insideItem) {
                                pubDate = xpp.nextText();
                            }
                        }
                    }
                    else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))      //once at the end tag </item> saves information from pull parser into the weather constructor
                    {
                        Weather theWeather = new Weather(title, pubDate, desc, image);
                        list.add(theWeather);
                        insideItem = false;
                    }
                    eventType = xpp.next();     //moves to next item tag
                }

            }
            catch (XmlPullParserException | IOException e) {exception = e;}     //exceptions
            return exception;
        }

        @Override
        protected void onPostExecute(Exception s)
        {
            super.onPostExecute(s);
            WeatherAdapter adapter = new WeatherAdapter(MainActivity.this, list);
            lvRss.setAdapter(adapter);
            pd.dismiss();
        }
    }
}
