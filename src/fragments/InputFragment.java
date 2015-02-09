package fragments;

import holders.InputHolder;

import java.util.Date;

import models.Input;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.itforhumanity.mypda.MainActivity;
import com.itforhumanity.mypda.R;

public class InputFragment extends Fragment {
	public static final String ARG_SECTION_NUMBER = "section_number";

	View rootView;
	EditText txtInput;
	Button btnSave,btnDelete,btnCategorize,btnList;
	
	Context context;
	
	public InputFragment(Context context) {
		this.context=context;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_input, container, false);
		txtInput = (EditText) rootView.findViewById(R.id.txtInput);
		//this is how to extract data from the bundle
		//getArguments().getInt(ARG_SECTION_NUMBER))

        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new OnClickListener() {@Override public void onClick(View v) {save();}});		
		
        btnCategorize = (Button) rootView.findViewById(R.id.btnCategorize);
        btnCategorize.setOnClickListener(new OnClickListener() {@Override public void onClick(View v) {categorize();}});		
		
        btnList = (Button) rootView.findViewById(R.id.btnList);
        btnList.setOnClickListener(new OnClickListener() {@Override public void onClick(View v) {list();}});		

        btnDelete = (Button) rootView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new OnClickListener() {@Override public void onClick(View v) {delete();}});		

        return rootView;
	}
//	@Override
//	public void onStart()
//	{
//		super.onStart();
//	}
	public void onTabSelected()
	{
		//txtInput.setText("");
		if(InputHolder.getInput()==null)
			btnDelete.setEnabled(false);
		else
			btnDelete.setEnabled(true);
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
			input.setDateCreated(today);
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
			list();
		}
	}
	public void categorize()
	{
		//go to tab 3 (category input)
		((MainActivity)getActivity()).switchFragment(3);
	}
	public void list()
	{
		//hide layout input
		//show layout list
		
		//for now
		//switch to tab 2 (input list)
		((MainActivity)getActivity()).switchFragment(2);
	}
}
