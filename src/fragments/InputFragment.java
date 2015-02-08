package fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.itforhumanity.mypda.R;

public class InputFragment extends Fragment {
	public static final String ARG_SECTION_NUMBER = "section_number";

	View rootView;
	EditText txtInput;
	Button btnSave;
	
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
        btnSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
            	InputListFragment inputListFragment = new InputListFragment(context);
            	
                FragmentTransaction trans = getFragmentManager().beginTransaction();
                trans.replace(R.id.fragmentLayout, inputListFragment);
				trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
				trans.addToBackStack(null);
				trans.commit();
				Log.i("","hi");
             	
            	
            }

        });		
		
		
		return rootView;
	}
//	@Override
//	public void onStart()
//	{
//		super.onStart();
//	}
	public void onTabSelected()
	{
		txtInput.setText("");
	}
	public void onTabUnselected()
	{
	}
	public void onTabReselected()
	{
	}
}
