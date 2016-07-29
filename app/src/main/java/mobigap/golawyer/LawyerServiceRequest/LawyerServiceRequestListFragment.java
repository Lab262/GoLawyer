package mobigap.golawyer.LawyerServiceRequest;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.InjectView;
import mobigap.golawyer.Model.ServiceRequestModel;
import mobigap.golawyer.R;

public class LawyerServiceRequestListFragment extends Fragment {

    ListView listView;
    View view;

    public static LawyerServiceRequestListFragment newInstance() {
        LawyerServiceRequestListFragment fragment = new LawyerServiceRequestListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_lawyer_service_request_list, container, false);

        this.listView = (ListView) this.view.findViewById(R.id.serviceRequestListView);

        return this.view;
    }

    @Override
    public void onStart() {
        super.onStart();

        this.loadRequestedServicesList(this.getDummyData());
    }

    private void loadRequestedServicesList(ServiceRequestModel[] servicesRequested) {
        LawyerServiceRequestListAdapter adapter = new LawyerServiceRequestListAdapter(getActivity().getApplicationContext(), servicesRequested);
        listView.setAdapter(adapter);
    }

    private ServiceRequestModel[] getDummyData() {

        ServiceRequestModel service1 = new ServiceRequestModel("http://image","Troy Beck", "Informação do pedido",true);
        ServiceRequestModel service2 = new ServiceRequestModel("http://image","Mathilda Robbins", "Informação do pedido",false);
        ServiceRequestModel service3 = new ServiceRequestModel("http://image","Samuel Cook", "Informação do pedido",false);
        ServiceRequestModel service4 = new ServiceRequestModel("http://image","Bettie Mills", "Informação do pedido",false);
        ServiceRequestModel service5 = new ServiceRequestModel("http://image","Alexander Hill ", "Informação do pedido",false);
        ServiceRequestModel service6 = new ServiceRequestModel("http://image","Caroly Stanley ", "Informação do pedido",false);
        ServiceRequestModel service7 = new ServiceRequestModel("http://image","Benjamin  Medna ", "Informação do pedido",false);

        ServiceRequestModel[] dummyData = new ServiceRequestModel[7];
        dummyData[0] = service1;
        dummyData[1] = service2;
        dummyData[2] = service3;
        dummyData[3] = service4;
        dummyData[4] = service5;
        dummyData[5] = service6;
        dummyData[6] = service7;

        return dummyData;

    }
}
