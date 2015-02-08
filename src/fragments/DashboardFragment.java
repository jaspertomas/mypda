package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itforhumanity.mypda.R;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class DashboardFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";

	public DashboardFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dashboard,
				container, false);
		TextView dummyTextView = (TextView) rootView
				.findViewById(R.id.section_label);
//		dummyTextView.setText(Integer.toString(getArguments().getInt(
//				ARG_SECTION_NUMBER)));
		dummyTextView.setText("DashboardFragment");
		return rootView;
	}
	public void onTabSelected()
	{
	}
	public void onTabUnselected()
	{
	}
	public void onTabReselected()
	{
	}
}
