package models;

import java.util.ArrayList;
import java.util.Date;

import utils.StandardDateHelper;
import android.database.Cursor;

public class DashletField {
    //------------FIELDS-----------
    public static final String tablename="dashlet_fields";
    //field names
    public static String[] fields={
            "id"
            ,"dashlet_id"
            ,"label"
            ,"rank"
            ,"type"
            };
    //field types
    public static String[] fieldtypes={
            "int(11)"
            ,"int(11)"
            ,"varchar(50)"
            ,"int(11)"
            ,"int(11)"
            };
    //-----------------------

    public Integer id;
    public Integer dashlet_id;
    public String label;
    public Integer rank;
    public Integer type;

    public DashletField() {
    }
    public DashletField(Cursor c) {
        id=c.getInt(c.getColumnIndex("id"));
        dashlet_id=c.getInt(c.getColumnIndex("dashlet_id"));
        label=c.getString(c.getColumnIndex("label"));
        rank=c.getInt(c.getColumnIndex("rank"));
        type=c.getInt(c.getColumnIndex("type"));
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

    public Integer getDashletId() {
            return dashlet_id;
    }

    public void setDashletId(Integer dashlet_id) {
            this.dashlet_id = dashlet_id;
    }

    public String getLabel() {
            return label;
    }

    public void setLabel(String label) {
            this.label = label;
    }

    public Integer getRank() {
            return rank;
    }

    public void setRank(Integer rank) {
            this.rank = rank;
    }

    public Integer getType() {
            return type;
    }

    public void setType(Integer type) {
            this.type = type;
    }


    //database functions
    public ArrayList<String> implodeFieldValuesHelper(boolean withId)
    {
            ArrayList<String> values=new ArrayList<String>(); 

            //add values for each field here
            if(withId)values.add(id!=null?id.toString():null);
            values.add(dashlet_id!=null?dashlet_id.toString():null);
            values.add(label);
            values.add(rank!=null?rank.toString():null);
            values.add(type!=null?type.toString():null);

            return values;
    }
    public void delete()
    {
            DashletFields.delete(this);
    }
    public void save()
    {
            if(id==null || id==0)
                    id=DashletFields.insert(this);
            else
                    DashletFields.update(this);
    }
    @Override
    public String toString()
    {
            return id.toString();
    }
}
