package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.itforhumanity.mypda.R;

public class InputFragment extends Fragment {
	public static final String ARG_SECTION_NUMBER = "section_number";

	View rootView;
	EditText txtInput;
	
	public InputFragment() {
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_input, container, false);
		txtInput = (EditText) rootView.findViewById(R.id.txtInput);
		//this is how to extract data from the bundle
		//getArguments().getInt(ARG_SECTION_NUMBER))
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
