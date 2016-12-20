package mobigap.golawyer.Map;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.JsonHttpResponseHandler;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;
import mobigap.golawyer.Extensions.ActivityManager;
import mobigap.golawyer.Extensions.AlertManager;
import mobigap.golawyer.Extensions.FeedbackManager;
import mobigap.golawyer.Model.LawyerModel;
import mobigap.golawyer.Persistence.ApplicationState;
import mobigap.golawyer.Profile.ProfileLawyerActivity;
import mobigap.golawyer.Protocols.OnFragmentInteractionListener;
import mobigap.golawyer.R;
import mobigap.golawyer.Requests.LawyerRequest;
import mobigap.golawyer.Requests.Requester;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        DialogInterface.OnClickListener, LocationSource.OnLocationChangedListener,
        GoogleMap.OnInfoWindowClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, SearchView.OnQueryTextListener {

    private OnFragmentInteractionListener mListener;

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private String nameLocalPermission;
    private LatLng userLatLng;
    private Marker currentLocationMarker;

    private static final int PERMISSION_LOCATION = 1;
    private static final int ZOOM_USER = 10;
    private static final int ZOOM_LAWYER = 14;
    private ProgressDialog progressDialog;
    private SearchView searchView;
    private HashMap<LatLng, String> markerHashMap = new HashMap<>();
    private ArrayList<LawyerModel> lawyerModelArrayList;

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


    private void setPropertiesView(View view) {
        searchView = (SearchView) view.findViewById(R.id.searchView);

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

        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.getId();
            }
        });
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

    private MarkerOptions createMarkerMap(Double latitude, Double longitude, String name) {
        LatLng point = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point);
        markerOptions.title(name);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_map));
        return markerOptions;
    }

    private void addPinsMap() {
        lawyerModelArrayList = ApplicationState.sharedState().getLawyersRequestModels();

        for (int i = 0; i < lawyerModelArrayList.size(); i++) {
            LawyerModel lawyerModel = lawyerModelArrayList.get(i);
            MarkerOptions markerOptions = createMarkerMap(lawyerModel.getLatitude(), lawyerModel.getLongitude(), lawyerModel.getName());
            markerHashMap.put(markerOptions.getPosition(), lawyerModel.getIdLawyer());
            mMap.addMarker(markerOptions);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.setOnInfoWindowClickListener(this);
            buildGoogleApiClient();
            mGoogleApiClient.connect();
        }


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void checkAccessLocalPermission() {
        if (hasPermission(nameLocalPermission)) {
            mapFragment.getMapAsync(this);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), nameLocalPermission)) {
                AlertManager.showAlert(getActivity(), getString(R.string.permission_gps), getString(R.string.permission_gps_accepted),
                        getString(R.string.permission_gps_denied), this);
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

        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }

        userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(userLatLng);
        markerOptions.title("Você");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentLocationMarker = mMap.addMarker(markerOptions);
    }

    private void getLawyers() {
        progressDialog = FeedbackManager.createProgressDialog(getActivity(), getString(R.string.placeholder_message_dialog));

        if (userLatLng == null) {
            userLatLng = new LatLng(-15,-18);
        }

        LawyerRequest.getLawyers(String.valueOf(userLatLng.latitude), String.valueOf(userLatLng.longitude), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                ApplicationState.sharedState().setLawyersRequestModels(null);
                if (Requester.haveSuccess(response)) {
                    //Get ServiceRequestModel
                    JSONArray arrayLawyersRequestModel = Requester.getJsonArray(response, LawyerModel.keyItensDataModel);
                    for (int i = 0; i < arrayLawyersRequestModel.length(); i++) {
                        JSONObject jsonObject = Requester.getJsonObject(arrayLawyersRequestModel, i);
                        LawyerModel lawyerRequestModel = new LawyerModel(jsonObject);
                        ApplicationState.sharedState().getLawyersRequestModels().add(lawyerRequestModel);
                    }

                    //Update view
                    addPinsMap();

                } else {
                    createToast(response);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                createErrorToast();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                createErrorToast();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                createErrorToast();
            }
        });
    }

    private void createToast(JSONObject response) {
        FeedbackManager.createToast(getActivity(), response);
    }

    private void createErrorToast() {
        FeedbackManager.feedbackErrorResponse(getActivity(), progressDialog);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String idLawyer = markerHashMap.get(marker.getPosition());
        Bundle bundle = new Bundle();
        bundle.putString(Requester.keyMessage, idLawyer);
        ActivityManager.changeActivity(getActivity(), ProfileLawyerActivity.class, bundle);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }else {
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                //place marker at current position
                //mGoogleMap.clear();
                userLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(userLatLng);
                markerOptions.title("Você");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                currentLocationMarker = mMap.addMarker(markerOptions);

                //zoom to current position:
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(userLatLng).zoom(ZOOM_USER).build();

                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
            }

            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(15000); //5 seconds
            mLocationRequest.setFastestInterval(10000); //3 seconds
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            getLawyers();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (lawyerModelArrayList!=null){
            filterList(query);
        }
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public void filterList(String stringFilter){
        ArrayList<LawyerModel> arrayFiltered = new ArrayList<>();
        for (LawyerModel lawyerModel : lawyerModelArrayList){
            if (lawyerModel.getName().toLowerCase().contains(stringFilter.toLowerCase())){
                arrayFiltered.add(lawyerModel);
            }
        }
        if (arrayFiltered.size()==1){
            LawyerModel lawyerModel = arrayFiltered.get(0);
            LatLng lawyerLatLng = new LatLng(lawyerModel.getLatitude(),lawyerModel.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(lawyerLatLng).zoom(ZOOM_LAWYER).build();

            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }
    }
}

