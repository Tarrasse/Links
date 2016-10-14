package com.links.links;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ITFragment extends Fragment {


    public ITFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_services_it, container, false);
        Button orederButton = (Button) rootView.findViewById(R.id.it_fragment_order_button);
        orederButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                intent.putExtra(ContactUsActivity.EXTRA_SERVIC_TYPE, "IT service");

                startActivity(intent);
            }
        });

        return rootView;
    }

}
