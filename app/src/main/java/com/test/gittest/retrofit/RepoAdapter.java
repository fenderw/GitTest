package com.test.gittest.retrofit;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.gittest.R;
import com.test.gittest.utils.ISO8601;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by F on 22/02/17.
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private List<RepoPostModel> repos;

    public RepoAdapter(List<RepoPostModel> repos) {
        this.repos = repos;
    }

    @Override
    public RepoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RepoAdapter.ViewHolder holder, int position) {
        RepoPostModel pm = repos.get(position);
        holder.name.setText(pm.getName());
        holder.full_name.setText(pm.getFullName());
        holder.created_at.setText(pm.getCreatedAt().toString());
    }

    @Override
    public int getItemCount() {
        if (repos == null)
            return 0;
        return repos.size();
    }

    public List<RepoPostModel> getCollection() {
        return repos;
    }

    /**
     * Sort repos by name in ascending order
     */
    public void sortByFullNameAsc() {
        if (repos != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                repos.sort((o1, o2) -> o1.getFullName().compareTo(o2.getFullName()));
            } else {
                //
                Collections.sort(repos, new Comparator<RepoPostModel>() {
                    public int compare(RepoPostModel o1, RepoPostModel o2) {
                        return o1.getFullName().compareTo(o2.getFullName());
                    }
                });
            }
            notifyDataSetChanged();
        }
    }


    /**
     * Sort repos by date in ascending order
     */
    public void sortByDateAsc() {
        if (repos != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                repos.sort((o1, o2) -> datefromStr(o1.getCreatedAt()).compareTo(datefromStr(o2.getCreatedAt())));
            } else {
                //
                Collections.sort(repos, new Comparator<RepoPostModel>() {
                    public int compare(RepoPostModel o1, RepoPostModel o2) {
                        return datefromStr(o1.getCreatedAt()).compareTo(datefromStr(o2.getCreatedAt()));
                    }
                });
            }
            notifyDataSetChanged();
        }
    }

    /**
     * Transform ISO 8601 string to Date object
     */
    private Date datefromStr(String date_str) {
        Date date;
        try {
            date = ISO8601.toDate(date_str);
        } catch (ParseException e) {
            // theoretically it shouldn't happen
            Log.w(getClass().getSimpleName(), e.toString());
            return new Date();
        }
        return date;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView full_name;
        TextView created_at;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_name);
            full_name = (TextView) itemView.findViewById(R.id.item_full_name);
            created_at = (TextView) itemView.findViewById(R.id.item_created_at);
        }
    }
}
