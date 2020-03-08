package com.zhaojin.cachingrealm_rxjava_retrofit_2.ui.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhaojin.cachingrealm_rxjava_retrofit_2.R;
import com.zhaojin.cachingrealm_rxjava_retrofit_2.services.response.ImageResponse;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ImageResponse> imageRespons;
    private Activity context;
    private RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.ic_reload)
            .error(R.drawable.ic_reload);

    public ImageAdapter(List<ImageResponse> imageRespons, Activity context) {
        this.imageRespons = imageRespons;
        this.context = context;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_issue, parent, false);
        return new IssueViewHolder(view);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((IssueViewHolder) holder).setData(imageRespons.get(position));
    }

    @Override public int getItemCount() {
        return imageRespons == null ? 0 : imageRespons.size();
    }

    private class IssueViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvBody;
        private ImageView img_thumb;

        IssueViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvBody = itemView.findViewById(R.id.tvBody);
            img_thumb = itemView.findViewById(R.id.img_thumb);
        }

        public void setData(ImageResponse imageResponse) {
            tvTitle.setText(imageResponse.getName());
            tvBody.setText(imageResponse.getTitle());
            Glide.with(context).load(imageResponse.getThumbnail()).into(img_thumb);
            img_thumb.setOnClickListener(v->{
                showDialog(imageResponse.getUrl() ,1);
            });
            tvBody.setOnClickListener(v->{

                showDialog(imageResponse.getImageWebSearchUrl() ,2);
            });
        }

        private void showDialog(String url, int type){
            if(type ==1) {
                LayoutInflater li = context.getLayoutInflater();
                View v = li.inflate(R.layout.dialog_detail, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(v);
                builder.show();
                ImageView img_original = v.findViewById(R.id.img_original);
                Glide.with(context).load(url).apply(options).into(img_original);
            }else{
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        }
    }


}
