package models;

import java.util.ArrayList;
import java.util.Date;

import utils.MyDatabaseHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EngineField {
    //------------FIELDS-----------
    public static final String tablename=EngineField.tablename;
    public static String[] fields=EngineField.fields;
    public static String[] fieldtypes=EngineField.fieldtypes;
    //-----------------------
    //-------------------------TABLE FUNCTIONS---------------------

    //-----------getter functions----------
    public static EngineField getById(Integer id) {
            ArrayList<EngineField> map=select(" where id = '"+id.toString()+"'");
            for(EngineField item:map)return item;
            return null;
    }
    public static EngineField getByEngineId(Integer engine_id)
    {
            ArrayList<EngineField> items=select(" where engine_id = '"+engine_id.toString()+"'");
            for(EngineField item:items)return item;
            return null;
    }	
    public static ArrayList<EngineField> selectByEngineId(Integer engine_id)
    {
            return select(" where engine_id = '"+engine_id.toString()+"'");
    }	
    public static EngineField getByLabel(String label)
    {
            ArrayList<EngineField> items=select(" where label = '"+label+"'");
            for(EngineField item:items)return item;
            return null;
    }	
    public static ArrayList<EngineField> selectByLabel(String label)
    {
            return select(" where label = '"+label+"'");
    }	
    public static EngineField getByRank(Integer rank)
    {
            ArrayList<EngineField> items=select(" where rank = '"+rank.toString()+"'");
            for(EngineField item:items)return item;
            return null;
    }	
    public static ArrayList<EngineField> selectByRank(Integer rank)
    {
            return select(" where rank = '"+rank.toString()+"'");
    }	
    public static EngineField getByType(Integer type)
    {
            ArrayList<EngineField> items=select(" where type = '"+type.toString()+"'");
            for(EngineField item:items)return item;
            return null;
    }	
    public static ArrayList<EngineField> selectByType(Integer type)
    {
            return select(" where type = '"+type.toString()+"'");
    }	
    //-----------database functions--------------

	public static void delete(EngineField item)
	{
		SQLiteDatabase db = MyDatabaseHelper.getInstance().getWritableDatabase();
		db.execSQL("delete from "+tablename+" where id = '"+item.getId()+"';");
		db.close();
	}
	public static void delete(Integer id)
	{
		SQLiteDatabase db = MyDatabaseHelper.getInstance().getWritableDatabase();
		db.execSQL("delete from "+tablename+" where id = '"+id+"';");
		db.close();
	}
	public static Integer insert(EngineField item)
	{
		SQLiteDatabase db = MyDatabaseHelper.getInstance().getWritableDatabase();
		
		if(fieldtypes[0].contains("int"))
		{
			db.execSQL("INSERT INTO "+tablename+" ("+implodeFields(false)+")VALUES (" 
					+implodeValues(item, false)
					+");");
		}
		else
		if(fieldtypes[0].contains("varchar"))
		{
			db.execSQL("INSERT INTO "+tablename+" ("+implodeFields(true)+")VALUES (" 
					+implodeValues(item, true)
					+");");
		}

            //fetch last insert id
		Cursor cursor = db.rawQuery("SELECT last_insert_rowid() FROM "+tablename+" ", null);
		cursor.moveToFirst();
		Integer result=cursor.getInt(0);
		cursor.close();

		db.close();
		return result;
	}
	public static void update(EngineField item)
	{
		SQLiteDatabase db = MyDatabaseHelper.getInstance().getWritableDatabase();

		db.execSQL(
		"update "+tablename+" set "+implodeFieldsWithValues(item,false)+" where id = '"+item.getId()
		+"';");
		db.close();
	}
	public static ArrayList<EngineField> select(String criteria) {
		ArrayList<EngineField> items = new ArrayList<EngineField>();
		SQLiteDatabase db = MyDatabaseHelper.getInstance()
				.getWritableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM "+tablename+" "+criteria, null);
		items = new ArrayList<EngineField>();
		while (cursor.moveToNext()) {
			items.add(new EngineField(cursor));
		}
		cursor.close();
		db.close();
		return items;
	}
	public static Integer count(String criteria) {
		SQLiteDatabase db = MyDatabaseHelper.getInstance().getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT count(*) FROM "+tablename+" "+criteria, null);
		cursor.moveToFirst();
		Integer result=cursor.getInt(0);
		cursor.close();
		db.close();
		return result;
	}
	public static Integer getLastInsertId() {
		SQLiteDatabase db = MyDatabaseHelper.getInstance().getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT last_insert_rowid() FROM "+tablename+" ", null);
		cursor.moveToFirst();
		Integer result=cursor.getInt(0);
		cursor.close();
		db.close();
		return result;
	}

    //-----------database helper functions--------------
    public static String implodeValues(EngineField item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(withId);
            String output="";
            for(String value:values)
            {
                    if(!output.isEmpty())
                            output+=",";
                    output+=(value!=null?"'"+value+"'":"null");
            }
            return output;
    }
    public static String implodeFields(boolean withId)
    {
            String output="";
            for(String field:fields)
            {
                    if(!withId && field.contentEquals("id"))continue;
                    if(!output.isEmpty())
                            output+=",";
                    output+=field;
            }
            return output;
    }
    public static String implodeFieldsWithValues(EngineField item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(true);//get entire list of values; whether the id is included will be dealt with later.

            if(values.size()!=fields.length)
            {
                    System.err.println("EngineField:implodeFieldsWithValues(): ERROR: values length does not match fields length");
            }

            String output="";
            for(int i=0;i<fields.length;i++)
            {
                    if(!withId && fields[i].contentEquals("id"))continue;
                    if(!output.isEmpty())
                            output+=",";
                    output+=fields[i]+"="+(values.get(i)!=null?"'"+values.get(i)+"'":"null");
            }
            return output;
    }	
    public static String implodeFieldsWithTypes()
    {
            String output="";
            for(int i=0;i<fields.length;i++)
            {
                    if(fields[i].contentEquals(fields[0]))//fields[0] being the primary key
                            output+=fields[i]+" "+fieldtypes[i]+" PRIMARY KEY";
                    else
                            output+=","+fields[i]+" "+fieldtypes[i];
            }
            return output;
    }	
    public static String createTable()
    {
            return "CREATE TABLE IF NOT EXISTS "+tablename+" ("+implodeFieldsWithTypes()+" );";
    }
    public static String deleteTable()
    {
            return "DROP TABLE IF EXISTS "+tablename;
    }
}
