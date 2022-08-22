package Cann.MDP.SecondDiet;
//William Cann - S2125914
public class Weather {
    private static String image;
    //declaring the variables that will hold the information from the xml pull parser
    public String title;
    public String pubDate;
    public String desc;

    //getters
    public String getTitle() {return title;}
    public String getPubDate() {return pubDate;}
    public String getDesc() {return desc;}

    public static String getImage() {return image;}

    //constructor
    public Weather(String title, String pubDate, String desc, String image) {
        this.title = title;
        this.pubDate = pubDate;
        this.desc = desc;
        this.image = image;
    }
}
