package models;

import java.util.ArrayList;
import java.util.Date;

import utils.MyDatabaseHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CategoryFields {
    //------------FIELDS-----------
    public static final String tablename=CategoryField.tablename;
    public static String[] fields=CategoryField.fields;
    public static String[] fieldtypes=CategoryField.fieldtypes;
    //-----------------------
    //-------------------------TABLE FUNCTIONS---------------------

    //-----------getter functions----------
    public static CategoryField getById(Integer id) {
            ArrayList<CategoryField> map=select(" where id = '"+id.toString()+"'");
            for(CategoryField item:map)return item;
            return null;
    }
    public static CategoryField getByCategoryId(Integer category_id)
    {
            ArrayList<CategoryField> items=select(" where category_id = '"+category_id.toString()+"'");
            for(CategoryField item:items)return item;
            return null;
    }	
    public static ArrayList<CategoryField> selectByCategoryId(Integer category_id)
    {
            return select(" where category_id = '"+category_id.toString()+"'");
    }	
    public static CategoryField getByLabel(String label)
    {
            ArrayList<CategoryField> items=select(" where label = '"+label+"'");
            for(CategoryField item:items)return item;
            return null;
    }	
    public static ArrayList<CategoryField> selectByLabel(String label)
    {
            return select(" where label = '"+label+"'");
    }	
    public static CategoryField getByRank(Integer rank)
    {
            ArrayList<CategoryField> items=select(" where rank = '"+rank.toString()+"'");
            for(CategoryField item:items)return item;
            return null;
    }	
    public static ArrayList<CategoryField> selectByRank(Integer rank)
    {
            return select(" where rank = '"+rank.toString()+"'");
    }	
    public static CategoryField getByType(Integer type)
    {
            ArrayList<CategoryField> items=select(" where type = '"+type.toString()+"'");
            for(CategoryField item:items)return item;
            return null;
    }	
    public static ArrayList<CategoryField> selectByType(Integer type)
    {
            return select(" where type = '"+type.toString()+"'");
    }	
    //-----------database functions--------------

	public static void delete(CategoryField item)
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
	public static Integer insert(CategoryField item)
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
	public static void update(CategoryField item)
	{
		SQLiteDatabase db = MyDatabaseHelper.getInstance().getWritableDatabase();

		db.execSQL(
		"update "+tablename+" set "+implodeFieldsWithValues(item,false)+" where id = '"+item.getId()
		+"';");
		db.close();
	}
	public static ArrayList<CategoryField> select(String criteria) {
		ArrayList<CategoryField> items = new ArrayList<CategoryField>();
		SQLiteDatabase db = MyDatabaseHelper.getInstance()
				.getWritableDatabase();

		Cursor cursor = db.rawQuery("SELECT * FROM "+tablename+" "+criteria, null);
		items = new ArrayList<CategoryField>();
		while (cursor.moveToNext()) {
			items.add(new CategoryField(cursor));
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
    public static String implodeValues(CategoryField item,boolean withId)
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
    public static String implodeFieldsWithValues(CategoryField item,boolean withId)
    {
            ArrayList<String> values=item.implodeFieldValuesHelper(true);//get entire list of values; whether the id is included will be dealt with later.

            if(values.size()!=fields.length)
            {
                    System.err.println("CategoryFields:implodeFieldsWithValues(): ERROR: values length does not match fields length");
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
