package com.links.links;


import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.links.links.data.LinksContract;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment  {


    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private SwipeRefreshLayout swipeContainer;
    private LinearLayoutManager mLayoutManager;



    private int LOADER_LABILE = 0;


    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_LABILE, null, mLoaderCallBacks);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        View emptyListViewIndecator = rootView.findViewById(R.id.news_eempty_textView);

        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(false);
            }
        });

        mNewsAdapter = new NewsAdapter(
                getActivity(),
                null,
                new NewsAdapter.NewsAdapterOnClickHandler() {
                    @Override
                    public void onClick(int position) {

                    }
                },
                emptyListViewIndecator
        );

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.News_RecyclerView);
        mRecyclerView.setAdapter(mNewsAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_up);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutManager.scrollToPositionWithOffset(mNewsAdapter.getItemCount()-1, 0);
            }
        });

        return rootView;
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallBacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new CursorLoader(
                    getActivity(),
                    LinksContract.NewsTable.CONTENT_URI,
                    LinksContract.NewsTable.COLUMNS,
                    null,
                    null,
                    null
            );
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mNewsAdapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mNewsAdapter.swapCursor(null);
        }
    };

}
