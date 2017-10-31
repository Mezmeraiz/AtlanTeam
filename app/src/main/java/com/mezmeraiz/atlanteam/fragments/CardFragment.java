package com.mezmeraiz.atlanteam.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.mezmeraiz.atlanteam.R;
import com.mezmeraiz.atlanteam.SpaceDecoration;
import com.mezmeraiz.atlanteam.Utils;
import com.mezmeraiz.atlanteam.network.Request;
import com.mezmeraiz.atlanteam.network.model.Comment;
import com.mezmeraiz.atlanteam.network.model.Photo;
import com.mezmeraiz.atlanteam.network.model.Post;
import com.mezmeraiz.atlanteam.network.model.Users;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by max on 31.10.17.
 */

public class CardFragment extends Fragment {

    public final static int POSTS = 0;
    public final static int COMMENTS = 1;
    public final static int USERS = 2;
    public final static int PHOTOS = 3;
    public final static int TODOS = 4;
    private View mView;
    private AlertDialog mDialog;
    private Disposable mDisposable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_card, null);
        initViews();
        return mView;
    }

    private void initViews(){
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceDecoration((int)getResources()
                .getDimension(R.dimen.main_card_margins)));
        recyclerView.setAdapter(new CardAdapter());
    }

    public void onItemClick(int position){
        switch (position){
            case POSTS:
                openDialog(POSTS, 100, "Enter post id");
                break;
            case COMMENTS:
                openDialog(COMMENTS, 500, "Enter comment id");
                break;
            case USERS:
                loadUsers();
                break;
            case PHOTOS:
                loadPhoto(3);
                break;
            case TODOS:
                onItemClick(new Random().nextInt(4));
                break;
        }
    }

    private void openDialog(int type, int max, String title){
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog, null);
        ((TextView)dialogView.findViewById(R.id.textView_title)).setText(title);
        dialogView.findViewById(R.id.click_cancel).setOnClickListener(v -> {
            mDialog.cancel();
        });
        dialogView.findViewById(R.id.click_ok).setOnClickListener(v ->{
            int id;
            try {
                id = Integer.parseInt(((EditText) dialogView.
                        findViewById(R.id.editText_dialog))
                        .getText()
                        .toString());
            }catch (Exception e){
                mDialog.cancel();
                Utils.snackBar(getActivity(), "Wrong id");
                return;
            }
            if(id > max){
                mDialog.cancel();
                Utils.snackBar(getActivity(), "Wrong id");
                return;
            }
            if(type == POSTS)
                loadPosts(id);
            else if(type == COMMENTS)
                loadComments(id);
            mDialog.cancel();
            Utils.hideKeyboard(getContext(), dialogView.
                    findViewById(R.id.editText_dialog));
        });
        mDialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .create();
        mDialog.show();
    }

    private void loadPosts(int id) {
        if(mDisposable != null)
            mDisposable.dispose();
        setProgressVisibility(true);
        mDisposable = new Request().posts(id)
                .map(responseBody -> new GsonBuilder().create()
                        .fromJson(responseBody.string(), Post.class))
                .filter(post -> post != null && post.body != null)
                .map(post -> post.body)
                .doOnNext(body -> addFragment(TextFragment.create(body)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result ->
                        {
                            setProgressVisibility(false);
                        },
                        error ->
                        {
                            setProgressVisibility(false);
                            Utils.snackBar(getActivity(), "Error");
                        }
                );
    }

    private void loadComments(int id) {
        if(mDisposable != null)
            mDisposable.dispose();
        setProgressVisibility(true);
        mDisposable = new Request().comments(id)
                .map(responseBody -> new GsonBuilder().create()
                        .fromJson(responseBody.string(), Comment.class))
                .filter(comment -> comment != null && comment.body != null)
                .map(comment -> comment.body)
                .doOnNext(body -> addFragment(TextFragment.create(body)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result ->
                        {
                            setProgressVisibility(false);
                        },
                        error ->
                        {
                            setProgressVisibility(false);
                            Utils.snackBar(getActivity(), "Error");
                        }
                );
    }

    private void loadUsers(){
        if(mDisposable != null)
            mDisposable.dispose();
        setProgressVisibility(true);
        mDisposable = new Request().users()
                .map(responseBody -> new GsonBuilder().create()
                        .fromJson(responseBody.string(), Users.class))
                .filter(users -> users != null)
                .map(users -> getUsersString(users))
                .doOnNext(body -> addFragment(TextFragment.create(body)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result ->
                        {
                            setProgressVisibility(false);
                        },
                        error ->
                        {
                            setProgressVisibility(false);
                            Utils.snackBar(getActivity(), "Error");
                        }
                );
    }

    private void loadPhoto(int id){
        if(mDisposable != null)
            mDisposable.dispose();
        setProgressVisibility(true);
        mDisposable = new Request().photos(id)
                .map(responseBody -> new GsonBuilder().create()
                        .fromJson(responseBody.string(), Photo.class))
                .filter(photo -> photo != null && photo.url != null)
                .map(photo -> photo.url)
                .doOnNext(url -> addFragment(ImageFragment.create(url)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result ->
                        {
                            setProgressVisibility(false);
                        },
                        error ->
                        {
                            setProgressVisibility(false);
                            Utils.snackBar(getActivity(), "Error");
                        }
                );
    }

    private String getUsersString(Users users){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < users.size() && i < 5; i++){
            builder.append(users.get(i).name);
            if(i < users.size() - 1 && i < 4)
                builder.append("\n\n");
        }
        return builder.toString();
    }

    private void addFragment(Fragment fragment){
        try{
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, fragment)
                    .addToBackStack(null)
                    .commit();
        }catch (Exception e){}
    }

    private void setProgressVisibility(boolean visible){
        if(visible)
            mView.findViewById(R.id.progressView).setVisibility(View.VISIBLE);
        else
            mView.findViewById(R.id.progressView).setVisibility(View.GONE);
    }

    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.RecyclerViewHolder> {

        public final String[] mTitles = {"Posts", "Comments", "Users", "Photos", "Todos"};

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
            return new RecyclerViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            holder.mTitleTextView.setText(mTitles[position]);
            holder.itemView.setOnClickListener(v ->{
                onItemClick(position);
            });
        }

        @Override
        public int getItemCount() {
            return mTitles.length;
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder{

            public TextView mTitleTextView;

            public RecyclerViewHolder(View itemView) {
                super(itemView);
                mTitleTextView = (TextView)itemView.findViewById(R.id.textView_title);
            }
        }
    }
}
