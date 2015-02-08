package models;

import java.util.ArrayList;
import java.util.Date;

import utils.StandardDateHelper;
import android.database.Cursor;

public class Input {
    //------------FIELDS-----------
    public static final String tablename="inputs";
    //field names
    public static String[] fields={
            "id"
            ,"content"
            ,"date_created"
            ,"date_modified"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"text"
            ,"date"
            ,"date"
            };
    //-----------------------

    public Integer id;
    public String content;
    public Date date_created;
    public Date date_modified;

    public Input() {
    }
    public Input(Cursor c) {
        id=c.getInt(c.getColumnIndex("id"));
        content=c.getString(c.getColumnIndex("content"));
        date_created=StandardDateHelper.toDate(c.getString(c.getColumnIndex("date_created")));
        date_modified=StandardDateHelper.toDate(c.getString(c.getColumnIndex("date_modified")));
    }

//	public String getUuid()
//	{
//		return id.toString()+"-";
//	}

    public Integer getId() {
            return id;
    }

    public void setId(Integer id) {
            this.id = id;
    }

    public String getContent() {
            return content;
    }

    public void setContent(String content) {
            this.content = content;
    }

    public Date getDateCreated() {
            return date_created;
    }

    public void setDateCreated(Date date_created) {
            this.date_created = date_created;
    }

    public Date getDateModified() {
            return date_modified;
    }

    public void setDateModified(Date date_modified) {
            this.date_modified = date_modified;
    }


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 

            //add values for each field here
            if(withId)values.add(id!=null?id.toString():null);
            values.add(content);
            values.add(date_created!=null?date_created.toString():null);
            values.add(date_modified!=null?date_modified.toString():null);

            return values;
    }
    public void delete()
    {
            Inputs.delete(this);
    }
    public void save()
    {
            if(id==null || id==0)
                    id=Inputs.insert(this);
            else
                    Inputs.update(this);
    }
    @Override
    public String toString()
    {
            return id.toString();
    }
}
