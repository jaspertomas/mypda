package fragments;

import java.util.ArrayList;
import java.util.regex.Pattern;

import models.Input;
import models.Inputs;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.itforhumanity.mypda.R;

public class InputListFragment extends Fragment {
	public static final String ARG_SECTION_NUMBER = "section_number";
	private final String tablename="inputs";
	public static ArrayList<Input> doctors;
	ArrayList<String> doctornames;
	Context context;

	View rootView;
	EditText txtSearch;
	
	public InputListFragment(Context context) {
		this.context=context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_input, container, false);
		txtSearch = (EditText) rootView.findViewById(R.id.txtSearch);
		//this is how to extract data from the bundle
		//getArguments().getInt(ARG_SECTION_NUMBER))
		
        doctorListView = (ListView) rootView.findViewById (R.id.tasks_list_view);

		//---------SETUP SEARCH BAR--------
		   searchbar = (EditText)rootView.findViewById(R.id.txtSearch);
		   searchbar.addTextChangedListener(new TextWatcher(){
		        public void afterTextChanged(Editable s) {
		    		
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
		doctors= new ArrayList<Input>();
		doctornames= new ArrayList<String>();

    	rebuildDoctorListView();


        if (doctorListView != null) {
//            doctorListView.setAdapter(new ArrayAdapter<Doctor>(ChooseDoctorActivity.this,
//              android.R.layout.simple_list_item_1, doctors));
            doctorListView.setAdapter(new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, doctornames));
            
            //this displays the puzzle
//            doctorListView.setOnItemClickListener(getPuzzleOnItemClickListener());	
            
            //this displays the new menu style edetail
            doctorListView.setOnItemClickListener(getMenuOnItemClickListener());	
        
        }	   		
		
		
		return rootView;
	}
//	@Override
//	public void onStart()
//	{
//		super.onStart();
//	}
	public void onTabSelected()
	{
		txtSearch.setText("");
	}
	public void onTabUnselected()
	{
	}
	public void onTabReselected()
	{
	}


	EditText searchbar;
	ListView doctorListView;
	
	protected void rebuildDoctorListView() {
    	doctornames.clear();
    	doctors.clear();
    	boolean match=false;
        for(Input doctor:Inputs.select(""))
        {
        	
    		if(searchbar.getText().toString().isEmpty())
        		match=true;//include all doctors if no search keyword is entered
    		else
    		{
    			//if search keyword is found in doctor name, match=true
    			match=(Pattern.compile(Pattern.quote(searchbar.getText().toString()), Pattern.CASE_INSENSITIVE).matcher(doctor.getContent()).find());
    		}
            		
        	if(match)
        	{
          	  	doctors.add(doctor);
          	  	doctornames.add(doctor.getDateCreated()+": "+doctor.getContent());
        	}
        }
        doctorListView.invalidateViews();

		
	}

	private OnItemClickListener getMenuOnItemClickListener()
	{
	 	return new OnItemClickListener() 
	 	{
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
		    {
				Input doc=doctors.get(position);

//				ContentMenuController.getInstance().setDoctor(doc);
//				Intent intent = new Intent(ChooseDoctorActivity.this, ChooseTrackActivity.class);
//				startActivity(intent);
		    }
		};
	}	

}
