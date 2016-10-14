package com.links.links;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PortfoloFragment extends Fragment {


    public PortfoloFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_portfolo, container, false);

        ArrayList<Item> items = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Item temp =  new Item();
            temp.setLink("www.google.com");
            temp.setLogo(R.drawable.vit);
            temp.setName("vitaBoom");
            temp.setService("website");
            items.add(temp);
        }

        RecyclerView RView = (RecyclerView) view.findViewById(R.id.portfolio_recycler_view);

        TextView emptyView = (TextView) view.findViewById(R.id.empty_view_portflio);

        PortfolioAdapter adapter = new PortfolioAdapter(getActivity(),
                items,
                new PortfolioAdapter.PortfolioAdapterOnClickHandler() {
                    @Override
                    public void onClick(int position) {

                    }
                },
                emptyView
        );


        RView.setAdapter(adapter);
        RView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        RView.setItemAnimator(itemAnimator);

        return view;
    }

    public static class Item {
        private int logo;
        private String name;
        private String service;
        private String link;

        public int getLogo() {
            return logo;
        }

        public void setLogo(int logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

}
