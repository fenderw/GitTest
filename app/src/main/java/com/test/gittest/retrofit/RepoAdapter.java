package com.test.gittest.retrofit;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.gittest.R;
import com.test.gittest.models.Repo;
import com.test.gittest.utils.ISO8601;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by F on 22/02/17.
 */

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private List<Repo> repos;
    private String userName;
    private Context context;

    public RepoAdapter(Context context, String userName, List<Repo> repos) {
        this.context = context;
        this.userName = userName;
        this.repos = repos;
    }

    @Override
    public RepoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RepoAdapter.ViewHolder holder, int position) {
        Repo pm = repos.get(position);
        if (pm.getOwnerName().equals(userName))
            holder.name.setText(pm.getName());
        else
            holder.name.setText(pm.getFullName());
        holder.description.setText(context.getResources().getString(R.string.repo_description) + " " + pm.getDescription());
        holder.created_at.setText(context.getResources().getString(R.string.repo_created_at) + " " + pm.getCreatedAt());
        holder.updated_at.setText(context.getResources().getString(R.string.repo_updated_at) + " " + pm.getUpdatedAt());
        holder.stargazers_count.setText(context.getResources().getString(R.string.repo_stars) + " " + pm.getStargazersCount().toString());
        holder.language.setText(context.getResources().getString(R.string.repo_language) + " " + pm.getLanguage());
    }

    @Override
    public int getItemCount() {
        if (repos == null)
            return 0;
        return repos.size();
    }

    public List<Repo> getCollection() {
        return repos;
    }

    /**
     * Sort repos by name in ascending order
     */
    public void sortByFullNameAsc() {
        if (repos != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                repos.sort((o1, o2) -> o1.getFullName().compareToIgnoreCase(o2.getFullName()));
            } else {
                //
                Collections.sort(repos, (o1, o2) -> o1.getFullName().compareToIgnoreCase(o2.getFullName()));
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
                Collections.sort(repos, (o1, o2) -> datefromStr(o1.getCreatedAt()).compareTo(datefromStr(o2.getCreatedAt())));
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
        TextView description;
        TextView created_at;
        TextView updated_at;
        TextView stargazers_count;
        TextView language;

        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_name);
            description = (TextView) itemView.findViewById(R.id.item_description);
            created_at = (TextView) itemView.findViewById(R.id.item_created_at);
            updated_at = (TextView) itemView.findViewById(R.id.item_updated_at);
            stargazers_count = (TextView) itemView.findViewById(R.id.item_stargazers_count);
            language = (TextView) itemView.findViewById(R.id.item_language);
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
