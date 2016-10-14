package com.links.links;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mahmoud on 9/27/2016.
 */
public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.PortfolioAdapterViewHolder> {

    public static String LOG_TAG = PortfolioAdapter.class.getSimpleName();

    private ArrayList<PortfoloFragment.Item> mItems;
    private Context mContext ;
    private View mEmptyView;
    private PortfolioAdapterOnClickHandler mClickHandler;

    private boolean type = false;

    public PortfolioAdapter(Context context, ArrayList<PortfoloFragment.Item> c, PortfolioAdapterOnClickHandler clickHandler, View emptyView) {
        mItems = c;
        mContext = context;
        mEmptyView = emptyView;
        mClickHandler = clickHandler;

        Log.i(LOG_TAG, "initializing the adapter");
    }


    @Override
    public PortfolioAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if ( parent instanceof RecyclerView ) {
            View view;
            if (type){
                view = LayoutInflater.from(mContext).inflate(R.layout.portfolio_list_item_ltr, parent, false);
                type = false ;
            }else {
                view = LayoutInflater.from(mContext).inflate(R.layout.portfolio_list_item_rtl, parent, false);
                type = true ;
            }
            view.setFocusable(true);
            view.setClickable(true);
            Log.i(LOG_TAG, "creating View");
            return new PortfolioAdapterViewHolder(view);
        } else {
            throw new RuntimeException("Not bound to RecyclerViewSelection");
        }
    }
    @Override
    public void onBindViewHolder(final PortfolioAdapterViewHolder holder, int position) {
        Log.i(LOG_TAG, "binding View");
        final PortfoloFragment.Item item = mItems.get(position);

        holder.mLogo.setImageResource(item.getLogo());
        holder.mName.setText(item.getName());
        holder.mVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + item.getLink())));
            }
        });
        holder.mService.setText(item.getService());

        View view = holder.view;

        holder.mVisit.setVisibility(View.INVISIBLE);
        holder.mName.setVisibility(View.INVISIBLE);
        holder.mService.setVisibility(View.INVISIBLE);

        if (type){
            view.setTranslationX(500);
            view.animate().setDuration(300).translationXBy(-500).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    holder.mName.setTranslationX(500);
                    holder.mName.setVisibility(View.VISIBLE);
                    holder.mName.animate().translationXBy(-500).setDuration(100).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            holder.mService.setTranslationX(500);
                            holder.mService.setVisibility(View.VISIBLE);
                            holder.mService.animate().setDuration(100).translationXBy(-500).setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    holder.mVisit.setTranslationX(500);
                                    holder.mVisit.setVisibility(View.VISIBLE);
                                    holder.mVisit.animate().setDuration(100).translationXBy(-500);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }else{
            view.setTranslationX(-500);
            view.animate().setDuration(300).translationXBy(500).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    holder.mName.setTranslationX(-500);
                    holder.mName.setVisibility(View.VISIBLE);

                    holder.mName.animate().translationXBy(500).setDuration(100).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            holder.mService.setTranslationX(-500);
                            holder.mService.setVisibility(View.VISIBLE);


                            holder.mService.animate().setDuration(100).translationXBy(500).setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    holder.mVisit.setTranslationX(-500);
                                    holder.mVisit.setVisibility(View.VISIBLE);
                                    holder.mVisit.animate().setDuration(100).translationXBy(500);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        if (getItemCount()>0){
            mEmptyView.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        if (mItems ==null)
            return 0;
        else
            return mItems.size();
    }

    public void swapCursor(ArrayList<PortfoloFragment.Item> newCursor) {
        mItems = newCursor;
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public class PortfolioAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mName;
        public final TextView mService;
        public final Button mVisit;
        public final ImageView mLogo;
        public final View view;
        public PortfolioAdapterViewHolder(View view) {
            super(view);
            mName = (TextView) view.findViewById(R.id.company_name);
            mService = (TextView) view.findViewById(R.id.service_name);
            mVisit =(Button) view.findViewById(R.id.visit_button);
            mLogo = (ImageView) view.findViewById(R.id.portfolio_irem_logo);
            this.view = view;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }

    public static interface PortfolioAdapterOnClickHandler {
        void onClick(int position);
    }


}
