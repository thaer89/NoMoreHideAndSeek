package com.example.hideandseek;



import java.util.Locale;

import com.example.morehideandseek.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private Location userParkLocation;
	private Location currentLocation;
	private PlaceholderFragment mainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");

				userParkLocation = Location.CreateParkLocation(contents);
				if (userParkLocation != null) {
					// Handle successful scan
					Toast toast = Toast.makeText(this, "Park Location Saved",
							Toast.LENGTH_LONG);
					mainFragment.getScanCurrentButton().setEnabled(true);
					mainFragment.getScanCurrentButton().setAlpha(1.0f);
					mainFragment.getScanParkButton().setEnabled(false);
					mainFragment.getScanParkButton().setAlpha(0.5f);
					mainFragment.getParklocatintextView().setText(
							userParkLocation.toString());
					toast.setGravity(Gravity.BOTTOM, 25, 400);
					toast.show();
					

				} else {
					// Handle successful scan
					Toast toast = Toast.makeText(this,
							"Fail to save park location,Please try again",
							Toast.LENGTH_LONG);
					toast.show();
				}

			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				Toast toast = Toast.makeText(this, "Scan was Cancelled!",
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.BOTTOM, 25, 400);
				toast.show();

			}
		}

		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				currentLocation = Location.CreateParkLocation(contents);
				if (currentLocation != null) {

					// 1. Instantiate an AlertDialog.Builder with its
					// constructor
					ContextThemeWrapper ctw = new ContextThemeWrapper( this, R.style.AlertDialogCustom );
					AlertDialog.Builder builder = new AlertDialog.Builder(ctw);
					builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				               // User clicked OK button
				           }
				       });
					builder.setTitle("Vehicle Parking Directions");
					// 2. Chain together various setter methods to set the
					// dialog characteristics
					String msg="";
					if(userParkLocation.floor!=currentLocation.floor)
					{
						if(userParkLocation.floor>currentLocation.floor){
							msg=String.format(Locale.ENGLISH, "You are in %s  floor, please go up to %s floor,section \"%s\" and row %d",
									Location.ordinal(currentLocation.getFloor()),Location.ordinal(userParkLocation.getFloor()
											),userParkLocation.getSection(),userParkLocation.getRow());
						}else{
							msg=String.format(Locale.ENGLISH,"You are in %s  floor, please go down to %s floor,section \"%s\" and row %d",
									Location.ordinal(currentLocation.getFloor()),Location.ordinal(userParkLocation.getFloor()
											),userParkLocation.getSection(),userParkLocation.getRow());
						}
					}else if(!userParkLocation.section.equals(currentLocation.section))
					{
						msg=String.format(Locale.ENGLISH,"You are in \"%s\" section,Please go to section  \"%s\"  and row %d",
								currentLocation.getSection(),userParkLocation.getSection(),userParkLocation.getRow());
					}else if(userParkLocation.row!=currentLocation.row){
						msg=String.format(Locale.ENGLISH,"You are in %d row,Please go to row %d",
								currentLocation.getRow(),userParkLocation.getRow());
					}else{
						msg=String.format("Your in right location, if you dont find your car its meant its stolen :p");
					}
					builder.setMessage(msg);

					// 3. Get the AlertDialog from create()
					AlertDialog dialog = builder.create();
					dialog.show();

				} else {
					// Handle successful scan
					Toast toast = Toast.makeText(this,
							"Fail to scan your location,Please try again",
							Toast.LENGTH_LONG);
					toast.show();
				}

			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				Toast toast = Toast.makeText(this, "Scan was Cancelled!",
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.BOTTOM, 25, 400);
				toast.show();

			}
		}
	}

	public PlaceholderFragment getMainFragment() {
		return mainFragment;
	}

	public void setMainFragment(PlaceholderFragment mainFragment) {
		this.mainFragment = mainFragment;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private Button scanParkButton;
		private Button scanCurrentButton;
		private Button resetButton;
		private TextView parklocatintextView;

		public Button getScanParkButton() {
			return scanParkButton;
		}

		public Button getScanCurrentButton() {
			return scanCurrentButton;
		}

		public TextView getParklocatintextView() {
			return parklocatintextView;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			scanParkButton = (Button) rootView.findViewById(R.id.scanParkSpot);
			scanCurrentButton = (Button) rootView
					.findViewById(R.id.scanCurrentLocation);
			resetButton=(Button) rootView.findViewById(R.id.resetbutton);
			parklocatintextView = (TextView) rootView
					.findViewById(R.id.ParkLocationText);
			scanParkButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent intent = new Intent(
							"com.google.zxing.client.android.SCAN");
					intent.setPackage("com.example.morehideandseek");
					intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
					getActivity().startActivityForResult(intent, 0);

				}
			});
			resetButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					scanParkButton.setEnabled(true);
					getScanParkButton().setAlpha(1.0f);
					scanCurrentButton.setEnabled(false);
					getScanCurrentButton().setAlpha(0.5f);
					
					parklocatintextView.setText(R.string.NoLocation);
					
				}
			});
			scanCurrentButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent intent = new Intent(
							"com.google.zxing.client.android.SCAN");
					intent.setPackage("com.example.morehideandseek");
					intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
					getActivity().startActivityForResult(intent, 1);

				}
			});
			((MainActivity) getActivity()).setMainFragment(this);
			return rootView;
		}

	}

}
