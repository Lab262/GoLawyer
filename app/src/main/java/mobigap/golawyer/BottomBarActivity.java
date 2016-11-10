package mobigap.golawyer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.aurelhubert.ahbottomnavigation.*;

import mobigap.golawyer.Enums.BottomBarOption;
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.LawyerServiceRequest.LawyerServiceRequestListFragment;
import mobigap.golawyer.Map.MapFragment;
import mobigap.golawyer.Map.MapListActivity;
import mobigap.golawyer.Profile.ProfileFragment;
import mobigap.golawyer.Protocols.OnFragmentInteractionListener;


public class BottomBarActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener, OnFragmentInteractionListener {

    private AHBottomNavigation bottomBar;
    private FragmentManager fragmentManager;
    private BottomBarOption bottomBarOption;
    private Menu menu;

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

        if (position==BottomBarOption.PROFILE.ordinal()){
            if (bottomBarOption!=BottomBarOption.PROFILE){
                bottomBarOption = BottomBarOption.PROFILE;
                changeFragmentOptionBottomBar();
            }
        } else if (position==BottomBarOption.SERVICE.ordinal()) {
            if (bottomBarOption != BottomBarOption.SERVICE) {
                bottomBarOption = BottomBarOption.SERVICE;
                changeFragmentOptionBottomBar();
            }
        }else if (position==BottomBarOption.MAP.ordinal()) {
            if (bottomBarOption != BottomBarOption.MAP) {
                bottomBarOption = BottomBarOption.MAP;
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
                        .replace(R.id.container, MapFragment.newInstance())
                        .commit();
                if (menu != null){
                    menu.findItem(R.id.action_edit_profile).setVisible(false);
                    menu.findItem(R.id.action_map_list).setVisible(true);
                }
                break;
            case SERVICE:
                getSupportActionBar().setTitle(R.string.name_bottom_bar_service);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, LawyerServiceRequestListFragment.newInstance())
                        .commit();
                if (menu != null){
                    menu.findItem(R.id.action_edit_profile).setVisible(false);
                    menu.findItem(R.id.action_map_list).setVisible(false);
                }

                break;
            case PROFILE:
                getSupportActionBar().setTitle(R.string.name_bottom_bar_profile);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ProfileFragment.newInstance())
                        .commit();
                if (menu != null){
                    menu.findItem(R.id.action_edit_profile).setVisible(true);
                    menu.findItem(R.id.action_map_list).setVisible(false);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        menu.findItem(R.id.action_edit_profile).setVisible(false);
        menu.findItem(R.id.action_map_list).setVisible(true);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_map_list:
                ActivityManager.changeActivity(BottomBarActivity.this, MapListActivity.class);
                break;
            case R.id.action_edit_profile:
                //TODO: Chamar o editar perfil.
                //ActivityManager.changeActivity(BottomBarActivity.this, MapListActivity.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
