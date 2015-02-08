package com.itforhumanity.mypda;

import java.util.Locale;

import utils.MyApplicationContextHolder;
import utils.MyDialogHelper;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import fragments.AnalysisFragment;
import fragments.DashboardFragment;
import fragments.InputFragment;
import fragments.ProcessingFragment;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
	InputFragment inputFragment;
	AnalysisFragment analysisFragment;
	ProcessingFragment processingFragment;
	DashboardFragment dashboardFragment;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		
		MyApplicationContextHolder.setAppContext(getApplicationContext());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
		
		switch(tab.getPosition())
		{
			//Input Fragment 
			case 0:
				if(inputFragment==null)break;
				inputFragment.onTabSelected();
				break;
			//Analysis Fragment 
			case 1:
				if(analysisFragment==null)break;
				analysisFragment.onTabSelected();
				break;
			//Processing Fragment 
			case 2:
//				MyDialogHelper.popup(MainActivity.this, "Hi!");
				if(processingFragment==null)break;
				processingFragment.onTabSelected();
				break;
			//DashBoard Fragment 
			case 3:default:
				if(dashboardFragment==null)break;
				dashboardFragment.onTabSelected();
				break;
		}
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		switch(tab.getPosition())
		{
			//Input Fragment 
			case 0:
				if(inputFragment==null)break;
				inputFragment.onTabUnselected();
				break;
			//Analysis Fragment 
			case 1:
				if(analysisFragment==null)break;
				analysisFragment.onTabUnselected();
				break;
			//Processing Fragment 
			case 2:
				if(processingFragment==null)break;
				processingFragment.onTabUnselected();
				break;
			//DashBoard Fragment 
			case 3:default:
				if(dashboardFragment==null)break;
				dashboardFragment.onTabUnselected();
				break;
		}
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		switch(tab.getPosition())
		{
			//Input Fragment 
			case 0:
				if(inputFragment==null)break;
				inputFragment.onTabReselected();
				break;
			//Analysis Fragment 
			case 1:
				if(analysisFragment==null)break;
				analysisFragment.onTabReselected();
				break;
			//Processing Fragment 
			case 2:
				if(processingFragment==null)break;
				processingFragment.onTabReselected();
				break;
			//DashBoard Fragment 
			case 3:default:
				if(dashboardFragment==null)break;
				dashboardFragment.onTabReselected();
				break;
		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Bundle args = new Bundle();
			switch(position)
			{
				//Input Fragment 
				case 0:
					inputFragment = new InputFragment(MainActivity.this);
					return inputFragment;
				//Analysis Fragment 
				case 1:
					analysisFragment = new AnalysisFragment();
					args.putInt(ProcessingFragment.ARG_SECTION_NUMBER, position + 1);
					analysisFragment.setArguments(args);
					return analysisFragment;
				//Processing Fragment 
				case 2:
					processingFragment = new ProcessingFragment();
					args.putInt(ProcessingFragment.ARG_SECTION_NUMBER, position + 1);
					processingFragment.setArguments(args);
					return processingFragment;
				//Dashboard Fragment 		
				case 3:default:
					dashboardFragment = new DashboardFragment();
					args.putInt(ProcessingFragment.ARG_SECTION_NUMBER, position + 1);
					dashboardFragment.setArguments(args);
					return dashboardFragment;
			}
		}
		@Override
		public int getCount() {
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			//Input Fragment label
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			//Analysis Fragment label
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			//Processing Fragment label
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			//Dashboard Fragment label
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			}
			return null;
		}
	}

	//------------------Input Fragment methods----------------
//	public void inputSave(View button)
//	{
//		if(inputFragment==null)return;
//		Log.i("","hi");
//	}
	//------------------Analysis Fragment methods----------------
	//------------------Processing Fragment methods----------------
	//------------------Dashboard Fragment methods----------------
}
