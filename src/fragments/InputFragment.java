package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.itforhumanity.mypda.R;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class InputFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";

	public InputFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_input,
				container, false);
		EditText txtInput = (EditText) rootView
				.findViewById(R.id.txtInput);
		txtInput.setText(Integer.toString(getArguments().getInt(
				ARG_SECTION_NUMBER)));
		return rootView;
	}
}
