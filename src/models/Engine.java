package models;

import java.util.ArrayList;
import java.util.Date;

import utils.StandardDateHelper;
import android.database.Cursor;

public class Engine {
    //------------FIELDS-----------
    public static final String tablename="engines";
    //field names
    public static String[] fields={
            "id"
            ,"name"
            ,"description"
            ,"type_id"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"varchar(50)"
            ,"text"
            ,"int(11)"
            };
    //-----------------------

    public Integer id;
    public String name;
    public String description;
    public Integer type_id;

    public Engine() {
    }
    public Engine(Cursor c) {
        id=c.getInt(c.getColumnIndex("id"));
        name=c.getString(c.getColumnIndex("name"));
        description=c.getString(c.getColumnIndex("description"));
        type_id=c.getInt(c.getColumnIndex("type_id"));
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

    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public String getDescription() {
            return description;
    }

    public void setDescription(String description) {
            this.description = description;
    }

    public Integer getTypeId() {
            return type_id;
    }

    public void setTypeId(Integer type_id) {
            this.type_id = type_id;
    }


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 

            //add values for each field here
            if(withId)values.add(id!=null?id.toString():null);
            values.add(name);
            values.add(description);
            values.add(type_id!=null?type_id.toString():null);

            return values;
    }
    public void delete()
    {
            Engines.delete(this);
    }
    public void save()
    {
            if(id==null || id==0)
                    id=Engines.insert(this);
            else
                    Engines.update(this);
    }
    @Override
    public String toString()
    {
            return id.toString();
    }
}
