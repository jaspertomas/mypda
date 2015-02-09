package fragments;

import holders.InputHolder;

import java.util.ArrayList;
import java.util.Date;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.itforhumanity.mypda.MainActivity;
import com.itforhumanity.mypda.R;

public class InputFragment extends Fragment {
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static final Integer MODE_INPUT=1;
	public static final Integer MODE_LIST=2;
	Integer mode=MODE_INPUT;
	
	//for input
	EditText txtInput;
	Button btnSave,btnDelete,btnCategorize,btnList,btnBack;
	LinearLayout inputLayout,listLayout;

	//for list----------
	private final String tablename="inputs";
	public static ArrayList<Input> listItems;
	ArrayList<String> listLabels;

	EditText txtSearch;
	ListView listView;
	
	//---------
	View rootView;
	Context context;
	
	public InputFragment(Context context) {
		this.context=context;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_input, container, false);
		inputLayout = (LinearLayout) rootView.findViewById(R.id.inputlayout);
		listLayout = (LinearLayout) rootView.findViewById(R.id.listlayout);

		//for input-----------
		txtInput = (EditText) rootView.findViewById(R.id.txtInput);
		//this is how to extract data from the bundle
		//getArguments().getInt(ARG_SECTION_NUMBER))

        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new OnClickListener() {@Override public void onClick(View v) {save();}});		
		
        btnCategorize = (Button) rootView.findViewById(R.id.btnCategorize);
        btnCategorize.setOnClickListener(new OnClickListener() {@Override public void onClick(View v) {categorize();}});		
		
        btnList = (Button) rootView.findViewById(R.id.btnList);
        btnList.setOnClickListener(new OnClickListener() {@Override public void onClick(View v) {listMode();}});		

        btnDelete = (Button) rootView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new OnClickListener() {@Override public void onClick(View v) {delete();}});		

        btnBack = (Button) rootView.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new OnClickListener() {@Override public void onClick(View v) {inputMode();}});		

        //for list-------
		txtSearch = (EditText) rootView.findViewById(R.id.txtSearch);
		//this is how to extract data from the bundle
		//getArguments().getInt(ARG_SECTION_NUMBER))
		
        listView = (ListView) rootView.findViewById (R.id.tasks_list_view);

		//---------SETUP SEARCH BAR--------
		   txtSearch = (EditText)rootView.findViewById(R.id.txtSearch);
		   txtSearch.addTextChangedListener(new TextWatcher(){
		        public void afterTextChanged(Editable s) {
		    		
		        	rebuildListView();
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
		listItems= new ArrayList<Input>();
		listLabels= new ArrayList<String>();

    	rebuildListView();


        if (listView != null) {
//            doctorListView.setAdapter(new ArrayAdapter<Doctor>(ChooseDoctorActivity.this,
//              android.R.layout.simple_list_item_1, doctors));
            listView.setAdapter(new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, listLabels));
            
            //this displays the puzzle
//            doctorListView.setOnItemClickListener(getPuzzleOnItemClickListener());	
            
            //this displays the new menu style edetail
            listView.setOnItemClickListener(getMenuOnItemClickListener());	
        }
        
        //----------
        
        return rootView;
	}
//	@Override
//	public void onStart()
//	{
//		super.onStart();
//	}
	public void onTabSelected()
	{
		if(mode==MODE_LIST)listMode();
		else inputMode();
	}
	public void onTabUnselected()
	{
	}
	public void onTabReselected()
	{
	}
	public void save()
	{
//    	InputListFragment inputListFragment = new InputListFragment(context);
//    	
//        FragmentTransaction trans = getFragmentManager().beginTransaction();
//        trans.replace(R.id.fragmentLayout, inputListFragment);
//		trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//		trans.addToBackStack(null);
//		trans.commit();
//		Log.i("","hi");
     	
//    	((MainActivity)getActivity()).switchFragment(1);
		
		//create mode
		Input input=InputHolder.getInput();
		if(input==null)
		{
			Date today=new Date();
			input=new Input();
			input.setDateCreated(today);
			input.setDateModified(today);
			input.setContent(txtInput.getText().toString());
			input.save();
			InputHolder.setInput(input);
			btnDelete.setEnabled(true);
		}
		//edit mode
		else
		{
			Date today=new Date();
			input.setDateModified(today);
			input.setContent(txtInput.getText().toString());
			input.save();
		}
	}
	public void delete()
	{
		Input input=InputHolder.getInput();
		if(input==null)
		{
			//do nothing
		}
		//edit mode
		else
		{
			input.delete();
			//nothing to delete anymore
			btnDelete.setEnabled(false);
			//go to list
			listMode();
		}
	}
	public void categorize()
	{
		//go to tab 3 (category input)
		((MainActivity)getActivity()).switchFragment(3);
	}
	
	public void listMode()
	{
		//hide layout input
		//show layout list
		
		//for now
		//switch to tab 2 (input list)
		//((MainActivity)getActivity()).switchFragment(2);
		inputLayout.setVisibility(View.GONE);
		listLayout.setVisibility(View.VISIBLE);
		mode=MODE_LIST;
	}
	public void inputMode()
	{
		inputLayout.setVisibility(View.VISIBLE);
		listLayout.setVisibility(View.GONE);
		mode=MODE_INPUT;
		
		//if in create mode (no input in input holder)
		if(InputHolder.getInput()==null)
		{
			txtInput.setText("");
			btnDelete.setEnabled(false);
		}
		//else in edit mode (there is an input in input holder)
		else
		{
			txtInput.setText(InputHolder.getInput().getContent());
			btnDelete.setEnabled(true);
		}
		
	}

	//for list-------

	protected void rebuildListView() {
    	listLabels.clear();
    	listItems.clear();
    	boolean match=false;
        for(Input input:Inputs.select(""))
        {
        	
    		if(txtSearch.getText().toString().isEmpty())
        		match=true;//include all doctors if no search keyword is entered
    		else
    		{
    			//if search keyword is found in doctor name, match=true
    			match=(Pattern.compile(Pattern.quote(txtSearch.getText().toString()), Pattern.CASE_INSENSITIVE).matcher(input.getContent()).find());
    		}
            		
        	if(match)
        	{
          	  	listItems.add(input);
          	  	listLabels.add(input.getDateCreated()+": "+input.getContent());
        	}
        }
        listView.invalidateViews();

		
	}

	private OnItemClickListener getMenuOnItemClickListener()
	{
	 	return new OnItemClickListener() 
	 	{
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) 
		    {
				Input input=listItems.get(position);
				InputHolder.setInput(input);
				
				//go back to input mode
				inputMode();
		    }
		};
	}	
	
	//---------------
}
/*
//				ContentMenuController.getInstance().setDoctor(doc);
//				Intent intent = new Intent(ChooseDoctorActivity.this, ChooseTrackActivity.class);
//				startActivity(intent);

 * */
