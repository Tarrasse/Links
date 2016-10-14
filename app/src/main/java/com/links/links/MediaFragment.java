package com.links.links;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by mahmoud on 10/2/2016.
 */
public class MediaFragment extends Fragment {



    public MediaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_services_media, container, false);

        Button orderButton = (Button) rootView.findViewById(R.id.media_fragment_order_button);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                intent.putExtra(ContactUsActivity.EXTRA_SERVIC_TYPE, "Media service");

                startActivity(intent);
            }
        });

        return rootView;
    }
}
