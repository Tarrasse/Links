package com.links.links;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.links.links.data.LinksContract.*;
import com.squareup.picasso.Picasso;


/**
 * Created by mahmoud on 9/13/2016.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder> {

    private Cursor mCursor;
    private Context mContext;
    private View mEmptyView;
    private NewsAdapterOnClickHandler mClickHandler;
    private int mPastPosition = -1;

    public NewsAdapter(Context context, Cursor c, NewsAdapterOnClickHandler clickHandler, View emptyView) {
        mCursor = c;
        mContext = context;
        mEmptyView = emptyView;
        mClickHandler = clickHandler;
        mPastPosition = -1;
    }


    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (parent instanceof RecyclerView) {
            mPastPosition++;
            mCursor.moveToPosition(mPastPosition);

            View view = LayoutInflater.from(mContext).inflate(R.layout.news_list_item_img, parent, false);
            view.setFocusable(true);
            view.setClickable(true);
            return new NewsAdapterViewHolder(view);

        } else {
            throw new RuntimeException("Not bound to RecyclerViewSelection");
        }
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {

        mCursor.moveToPosition(position);

        String title = mCursor.getString(NewsTable.TITLE_INDEX);
        String body = mCursor.getString(NewsTable.BODY_INDEX);
        String date = mCursor.getString(NewsTable.DATE_INDEX);
        String imgLink = mCursor.getString(NewsTable.IMG_LINK_INDX);

        holder.mTitle.setText(title);
        holder.mBody.setText(body);
        holder.mdate.setText(date);


        if (!Utility.isEmptyOrNull(imgLink)) {
            Picasso.with(mContext).load(imgLink).into(holder.mImage);
        } else {
            holder.mImage.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if (mCursor == null)
            return 0;
        else
            return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mTitle;
        public final TextView mBody;
        public final TextView mdate;
        public final ImageView mImage;
        public final View view;

        public NewsAdapterViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.news_title);
            mBody = (TextView) view.findViewById(R.id.news_body);
            mdate = (TextView) view.findViewById(R.id.news_date);
            this.view = view;

            mImage = (ImageView) view.findViewById(R.id.news_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }

    public static interface NewsAdapterOnClickHandler {
        void onClick(int position);
    }


}
