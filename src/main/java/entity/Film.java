package entity;

/**
 * Фильм
 */
public class Film {
    private int     id;
    private String  title;
    private String  description;
    private int     year;
    private int     languageId;
    private int     originalLanguageId;
    private int     rentalDuration;
    private float   rentalRate;
    private int     length;
    private float   replacementCost;
    private String  rating;
    private String  specialFeatures;
    private int     lastUpdate;

    public Film(int id, String title, String description, int year, int languageId,
                int originalLanguageId, int rentalDuration, float rentalRate, int length, float replacementCost,
                String rating, String specialFeatures, int lastUpdate){
        this.id = id;
        this.title = title;
        this.description = description;
        this.year = year;
        this.languageId = languageId;
        this.originalLanguageId = originalLanguageId;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.length= length;
        this.replacementCost = replacementCost;
        this.rating = rating;
        this.specialFeatures= specialFeatures;
        this.lastUpdate= lastUpdate;
    }

    public Film(String title, String description, int year, int languageId,
                int originalLanguageId, float rentalRate, int length, float replacementCost,
                String rating, String specialFeatures, int lastUpdate){

        this.title = title;
        this.description = description;
        this.year = year;
        this.languageId = languageId;
        this.originalLanguageId = originalLanguageId;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.length= length;
        this.replacementCost = replacementCost;
        this.rating = rating;
        this.specialFeatures= specialFeatures;
        this.lastUpdate= lastUpdate;
    }

    public Film() {

    }


    @Override
    public String toString(){
        String me = id + " " + title+ " " + description;
        return me;
    }

    public int getId() {
        return id;
    }

    public String getTitle()                {  return  title; }
    public String getDescription()          {  return description;}
    public int getYear()                    {  return year;}
    public int getLanguageId()              {  return languageId; }
    public int getOriginalLanguageId()      {  return originalLanguageId; }
    public int getRentalDuration()          {  return rentalDuration; }
    public float getRentalRate()            {  return rentalRate; }
    public int getLength()                  {  return length;}
    public float getReplacementCost()       {  return replacementCost;}
    public String getRating()               {  return rating;}
    public String getSpecialFeatures()      {  return specialFeatures;}
    public int getLastUpdate()              {  return lastUpdate;}



    public void setTitle(String title)                          {  this.title =   title; }
    public void setDescription(String description)              {  this.description =  description;}
    public void setYear(int year)                               {  this.year = year;}
    public void setLanguageId(int language)                     {  this.languageId =  languageId; }
    public void setOriginalLanguageId(int originaLanguageId)    {  this.originalLanguageId =  originalLanguageId; }
    public void setRentalDuration(int rentalDuration)           {  this.rentalDuration =  rentalDuration; }
    public void setRentalRate(float rentalRate)                 {  this.rentalRate =  rentalRate; }
    public void setLength(int length)                           {  this.length =  length;}
    public void setReplacementCost(float replacementCost)       {  this.replacementCost =  replacementCost;}
    public void setRating(String rating)                        {  this.rating = rating;}
    public void setSpecialFeatures(String specialFeatures)      {  this.specialFeatures =  specialFeatures;}
    public void setLastUpdate(int lastUpdate)                   {  this.lastUpdate =  lastUpdate;}


}
