package mobigap.golawyer.Map;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import mobigap.golawyer.Extensions.AlertManager;
import mobigap.golawyer.Protocols.OnFragmentInteractionListener;
import mobigap.golawyer.R;

public class MapFragment extends Fragment implements OnMapReadyCallback, DialogInterface.OnClickListener, LocationSource.OnLocationChangedListener {

    private OnFragmentInteractionListener mListener;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private String nameLocalPermission;

    private static final int PERMISSION_LOCATION = 1;
    private static final int GPS_RESULT = 1;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        nameLocalPermission = Manifest.permission.ACCESS_FINE_LOCATION;
        checkAccessLocalPermission();
        setPropertiesView(view);
        return view;
    }


    private void setPropertiesView(View view){
        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);

        int searchSrcTextId = getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = (EditText) searchView.findViewById(searchSrcTextId);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));

        int closeButtonId = getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButtonImage = (ImageView) searchView.findViewById(closeButtonId);
        closeButtonImage.setColorFilter(getResources().getColor(R.color.white));

        int searchButtonId = getResources().getIdentifier("android:id/search_button", null, null);
        ImageView searchButtonImage = (ImageView) searchView.findViewById(searchButtonId);
        searchButtonImage.setColorFilter(getResources().getColor(R.color.white));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        // TODO: Adicionar marcadores  customizados e de acordo com os advogados....
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMyLocationEnabled(true);
    }

    private void checkAccessLocalPermission() {
        if (hasPermission(nameLocalPermission)) {
            mapFragment.getMapAsync(this);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), nameLocalPermission)) {
                AlertManager.showAlert(getActivity(),getString(R.string.permission_gps), getString(R.string.permission_gps_accepted),
                getString(R.string.permission_gps_denied),this);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{nameLocalPermission},
                        PERMISSION_LOCATION);
            }
        }
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getActivity(), perm));
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{nameLocalPermission},
                PERMISSION_LOCATION);
    }

    @Override
    public void onLocationChanged(Location location) {

        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Você"));

    }
}

