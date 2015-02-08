package com.itforhumanity.mypda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Pattern;

import models.CustomTrack;
import models.Doctor;
import models.DoctorTrack;
import models.DoctorTracks;
import models.Doctors;
import models.Track;
import models.Tracks;
import utils.FileInspector;
import utils.MySharedFileHelper;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.intelimina.biomedis.R;
import com.intelimina.unilab.TimedActivity;

import controllers.ContentMenuController;
import controllers.NavController;
import controllers.TrackSupervisor;

public class ChooseDoctorActivity extends TimedActivity implements OnTouchListener {

	// ------------SINGLETON---------------
	private static ChooseDoctorActivity instance;

	public static ChooseDoctorActivity getInstance() {
		return instance;
	}

	@Override
	protected void onStart() { 
		super.onStart();
		Log.e("ChooseDoctorActivity", "onStart");
		
		//if standalone mode
		//check if shared file has been modified
		//if so, return to home screen
		if(
			getSharedPreferences("CurrentUser", MODE_PRIVATE).getBoolean("asplugin", false)
			&&
			!MySharedFileHelper.compareSharedfiledata()
		)
		{
			Log.e("Access violation!", "Not launched from local app: Exit to HomeActivity");
			NavController.getInstance().setDestination("home");
			finish();
			return;
		}
		else if(NavController.getInstance().getDestination().contentEquals("home"))
		{
			finish();
		}
		else
		{
			instance = ChooseDoctorActivity.this;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.e("ChooseDoctorActivity", "onStop");
		instance = null;
	}

	// ----------END SINGLETON---------	
    //----------TIMER FUNCTION-------------
    //UI references
    private LinearLayout mLinearLayout;
    
    public boolean onTouch(View v, MotionEvent event)
    {
        final int actionPerformed = event.getAction();

        //reset idle timer
        // put this here so that the touching of empty space is captured too
        //  it seems that LinearLayout doesn't trigger a MotionEvent.ACTION_UP or MotionEvent.ACTION_MOVE
        if (actionPerformed == MotionEvent.ACTION_DOWN)
        {
            super.onTouch();
        }

        return false;//do not consume event!
    }
	private void setupTimer()
	{
        //get references to all of your widgets
        mLinearLayout = (LinearLayout) findViewById(R.id.rootlayout);

        //set your widgets as touchable
        mLinearLayout.setOnTouchListener(ChooseDoctorActivity.this);

        searchbar = (EditText)findViewById(R.id.search);
        searchbar.setOnTouchListener(ChooseDoctorActivity.this);
        

        ListView view1 = (ListView)findViewById(R.id.tasks_list_view);
        view1.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            	onTouch();
            }
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
            	onTouch();
			}
        });	
        
	}    
    //---------- END TIMER FUNCTION-------------
	private final String tablename="doctors";
	public static ArrayList<Doctor> doctors;
	ArrayList<String> doctornames;


//	@Override
//	protected void onPause() {
//		super.onPause();
//		Log.e("ChooseDoctorActivity", "onPause");
//	}
	@Override
	protected void onRestart() {
		super.onRestart();
		Log.e("ChooseDoctorActivity", "onRestart");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_doctor);
		instance = ChooseDoctorActivity.this;

		setupView();	
		setupTimer();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_doctor, menu);
		return true;
	}
	EditText searchbar;
	ListView doctorListView;
	
	private void setupView()
	{
		
        doctorListView = (ListView) findViewById (R.id.tasks_list_view);

		//---------SETUP SEARCH BAR--------
		   searchbar = (EditText)findViewById(R.id.search);
		   searchbar.addTextChangedListener(new TextWatcher(){
		        public void afterTextChanged(Editable s) {
		    		onTouch();
		    		
		        	rebuildDoctorListView();
//		        	doctors.clear();
//		            for(Doctor doctor:Doctors.getDoctors().values())
//		            {
//		            	if(Pattern.compile(Pattern.quote(searchbar.getText().toString()), Pattern.CASE_INSENSITIVE).matcher(doctor.getName()).find())
////		            	if(doctor.getName()(searchbar.getText().toString()))
//		            		doctors.add(doctor);
//		            }
//		        	
//		        	doctorListView.invalidateViews();
		        }
		        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		        public void onTextChanged(CharSequence s, int start, int before, int count){}
		    }); 		

		//=====read doctors table and add results to listview=======
		doctors= new ArrayList<Doctor>();
		doctornames= new ArrayList<String>();

//        for(Doctor doctor:Doctors.getDoctors().values()){doctors.add(doctor);}
//        //sort doctors by name
//        Collections.sort(doctors);
		//build doctor's list for the first time
    	rebuildDoctorListView();


        if (doctorListView != null) {
//            doctorListView.setAdapter(new ArrayAdapter<Doctor>(ChooseDoctorActivity.this,
//              android.R.layout.simple_list_item_1, doctors));
            doctorListView.setAdapter(new ArrayAdapter<String>(ChooseDoctorActivity.this,
                    android.R.layout.simple_list_item_1, doctornames));
            
            //this displays the puzzle
//            doctorListView.setOnItemClickListener(getPuzzleOnItemClickListener());	
            
            //this displays the new menu style edetail
            doctorListView.setOnItemClickListener(getMenuOnItemClickListener());	
        
        }	   
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		onTouch();
		
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_home:
			NavController.getInstance().setDestination("home");
			finish();
			return true;
		case R.id.menu_back:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void search(View button) {
//        Intent intent = new Intent(ChooseDoctorActivity.this, DBDumpActivity.class);
//        startActivity(intent);
	}
	
	//this clicklistener displays the puzzle for the old version
	private OnItemClickListener getPuzzleOnItemClickListener()
	{
		return new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
			    {
			      Doctor doc = ((Doctor)parent.getItemAtPosition(position));
			      if(doc.hasContentSets())
			      {
			    	  //get doctortrackdata for this doctor
			    	  ArrayList<DoctorTrack> doctrackslist=DoctorTracks.getByDoctorId(doc.getId());

			    	  //look for the next incomplete track
			    	  Track tracktoplay=null;
			    	  for(DoctorTrack datum:doctrackslist)
			    	  {
			    		  if(!datum.getCompleted())
			    		  {
			    			  tracktoplay=Tracks.getById(datum.getTrack_id()); 
			    			  break;
			    		  }
			    	  }
			    	  //if this doctor has all tracks completed, 
			    	  if(tracktoplay==null)
			    	  {
	      					Toast.makeText(ChooseDoctorActivity.this, "No more tracks to play for "+doc.getName(), Toast.LENGTH_LONG).show();
			    	  }
			    	  else
			    	  //launch showpuzzle activity to play that track
			    	  {
			    		  boolean inspectfirst=false;
			    		  //inspect if all resources intact
			    		  //if inspectfirst==true
			    		  if(inspectfirst && !FileInspector.getInstance().inspectTrack(tracktoplay))
			    		  {
	      					Toast.makeText(ChooseDoctorActivity.this, "Please wait for all downloads to complete", Toast.LENGTH_LONG).show();
			    		  }
			    		  else
			    		  {
    			    		  TrackSupervisor.getInstance().setTrack(tracktoplay);
    			    		  TrackSupervisor.getInstance().setDoc(doc);
								Intent intent = new Intent(ChooseDoctorActivity.this, ShowPuzzleActivity.class);
								startActivity(intent);
			    		  }
			    	  }
			    	  
			    	  //toast name of first track
//  					Toast.makeText(ChooseDoctorActivity.this, Tracks.getTracks().get(doc.getTrackId(0)).getName(), Toast.LENGTH_LONG).show();
			      }
			      else
			      {
    					Toast.makeText(ChooseDoctorActivity.this, "No tracks assigned for this doctor", Toast.LENGTH_LONG).show();
			      }

			    }};
	}
	private OnItemClickListener getMenuOnItemClickListener()
	{
	 	return new OnItemClickListener() 
	 	{
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
		    {
				ChooseDoctorActivity.getInstance().onTouch();
				
//				Doctor doc = ((Doctor)parent.getItemAtPosition(position));
				Doctor doc=doctors.get(position);
				HashMap<String,CustomTrack> tracks=doc.getTracks();
				if(tracks.size()==0)
				{
					Toast.makeText(ChooseDoctorActivity.this, "No tracks assigned for "+doc.getName(), Toast.LENGTH_LONG).show();
					return;
				}
				
//				ContentMenuController.getInstance().setDoctor(doc);
//				Intent intent = new Intent(ChooseDoctorActivity.this, ContentMenuActivity.class);
//				startActivity(intent);
				
				ContentMenuController.getInstance().setDoctor(doc);
				Intent intent = new Intent(ChooseDoctorActivity.this, ChooseTrackActivity.class);
				startActivity(intent);
		    }
		};
	}	
	protected void rebuildDoctorListView() {
		String tracknames;
		HashMap<String,CustomTrack> tracks;

    	doctornames.clear();
    	doctors.clear();
    	boolean match=false;
        for(Doctor doctor:Doctors.getItemsFilteredByUserAssignmentAndBusinessUnit().values())
        {
        	
    		if(searchbar.getText().toString().isEmpty())
        		match=true;//include all doctors if no search keyword is entered
    		else
    		{
    			//if search keyword is found in doctor name, match=true
    			match=(Pattern.compile(Pattern.quote(searchbar.getText().toString()), Pattern.CASE_INSENSITIVE).matcher(doctor.getName()).find());
    		}
            		
        	if(match)
//        	if(doctor.getName()(searchbar.getText().toString()))
        	{
            	tracknames="";
          	  	doctors.add(doctor);
          	  	tracks=doctor.getTracks();

          	  	if(tracks.size()>0)
          	  	{
              	  	//implode track names into string
              	  	boolean first=true;
              	  	for(CustomTrack track:tracks.values())
              	  	{
              	  		if(!first)
          	  			{
                  	  		tracknames+=", ";
          	  			}
              	  		first=false;
              	  		tracknames+=track.getName();
              	  	}
          	  	}
          	  	else
          	  	{
          	  		tracknames+="none";
          	  	}
          	  	
      	  		tracknames=" (Tracks: "+tracknames+")";
          	  	doctornames.add(doctor.getName()+tracknames);
        	}
        }
        Collections.sort(doctornames);
        Collections.sort(doctors);
    	
        doctorListView.invalidateViews();

		
	}
}
