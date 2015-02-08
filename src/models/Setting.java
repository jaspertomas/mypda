package models;

import java.util.ArrayList;
import java.util.Date;

import utils.StandardDateHelper;
import android.database.Cursor;

public class Setting {
    //------------FIELDS-----------
    public static final String tablename="settings";
    //field names
    public static String[] fields={
            "id"
            ,"value"
            ,"type"
            };
    //field types
    public static String[] fieldtypes={
            "varchar(50)"
            ,"varchar(255)"
            ,"varchar(20)"
            };
    //-----------------------

    public String id;
    public String value;
    public String type;

    public Setting() {
    }
    public Setting(Cursor c) {
        id=c.getString(c.getColumnIndex("id"));
        value=c.getString(c.getColumnIndex("value"));
        type=c.getString(c.getColumnIndex("type"));
    }

//	public String getUuid()
//	{
//		return id.toString()+"-";
//	}

    public String getId() {
            return id;
    }

    public void setId(String id) {
            this.id = id;
    }

    public String getValue() {
            return value;
    }

    public void setValue(String value) {
            this.value = value;
    }

    public String getType() {
            return type;
    }

    public void setType(String type) {
            this.type = type;
    }


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 

            //add values for each field here
            if(withId)values.add(id);
            values.add(value);
            values.add(type);

            return values;
    }
    public void delete()
    {
            Settings.delete(this);
    }
    public void save()
    {
            if(id==null || id.isEmpty() )
                    Settings.insert(this);
            else
                    Settings.update(this);
    }
    @Override
    public String toString()
    {
            return id;
    }
}
