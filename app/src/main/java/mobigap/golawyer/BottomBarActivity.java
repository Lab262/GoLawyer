package mobigap.golawyer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.aurelhubert.ahbottomnavigation.*;

import mobigap.golawyer.Enums.BottomBarOption;
import mobigap.golawyer.Map.MapFragment;
import mobigap.golawyer.Profile.ProfileFragment;
import mobigap.golawyer.Protocols.OnFragmentInteractionListener;


public class BottomBarActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener, OnFragmentInteractionListener {

    private AHBottomNavigation bottomBar;
    private FragmentManager fragmentManager;
    private BottomBarOption bottomBarOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        initializeProperties();
        changeFragmentOptionBottomBar();
    }

    private void initializeProperties(){
        bottomBarOption = BottomBarOption.MAP;
        initializeBottomBar();
        fragmentManager = getSupportFragmentManager();
    }

    private void initializeBottomBar(){
        bottomBar = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.name_bottom_bar_map, R.drawable.bottom_bar_icon_map, R.color.blueApp);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.name_bottom_bar_service, R.drawable.bottom_bar_icon_service, R.color.blueApp);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.name_bottom_bar_profile, R.drawable.bottom_bar_icon_profile, R.color.blueApp);

        bottomBar.addItem(item1);
        bottomBar.addItem(item2);
        bottomBar.addItem(item3);

        bottomBar.setBehaviorTranslationEnabled(false);
        bottomBar.setAccentColor(Color.parseColor("#F63D2B"));
        bottomBar.setInactiveColor(Color.parseColor("#747474"));
        bottomBar.setForceTint(true);
        bottomBar.setForceTitlesDisplay(true);
        bottomBar.setColored(true);
        bottomBar.setCurrentItem(bottomBarOption.ordinal());
        bottomBar.setOnTabSelectedListener(this);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (position==BottomBarOption.MAP.ordinal()){
            if (bottomBarOption!=BottomBarOption.MAP){
                bottomBarOption = BottomBarOption.MAP;
                changeFragmentOptionBottomBar();
            }
        }else if (position==BottomBarOption.PROFILE.ordinal()){
            if (bottomBarOption!=BottomBarOption.PROFILE){
                bottomBarOption = BottomBarOption.PROFILE;
                changeFragmentOptionBottomBar();
            }
        }

        return true;
    }

    @Override
    public void onFragmentInteraction(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).addToBackStack("")
                .commit();
    }

    private void changeFragmentOptionBottomBar(){
        switch (bottomBarOption){
            case MAP:
                getSupportActionBar().setTitle(R.string.name_bottom_bar_map);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, MapFragment.newInstance("", ""))
                        .commit();
                break;
            case SERVICE:
//                getSupportActionBar().setTitle(R.string.name_bottom_bar_map);
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, MapFragment.newInstance("", ""))
//                        .commit();
                break;
            case PROFILE:
                getSupportActionBar().setTitle(R.string.name_bottom_bar_profile);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ProfileFragment.newInstance("", ""))
                        .commit();
                break;
        }
    }
}
