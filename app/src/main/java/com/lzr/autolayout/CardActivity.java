package com.lzr.autolayout;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvTitle;
    private Button btnSkip;
    private ImageView ivBack;
    private RecyclerView rvContents;
    private List<ImageInfor> lists;
    private MyAdapter adpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        initViews();
        initDatas();
        setAdapter();
    }

    private void setAdapter() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvContents.setLayoutManager(manager);
        rvContents.setItemAnimator(new DefaultItemAnimator());
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvContents.setHasFixedSize(true);
        adpater = new MyAdapter(lists);
        rvContents.setAdapter(adpater);

        adpater.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Toast.makeText(getApplication(),"点击了：" + postion,Toast.LENGTH_SHORT).show();
            }
        });

        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN | ItemTouchHelper.UP
                | ItemTouchHelper.RIGHT| ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT| ItemTouchHelper.LEFT | ItemTouchHelper.START
                |ItemTouchHelper.END) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Collections.swap(lists, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adpater.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                lists.remove(viewHolder.getAdapterPosition());
                adpater.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder != null){
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                    viewHolder.itemView.setScaleX(1.5F);
                    viewHolder.itemView.setScaleY(1.5F);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
                viewHolder.itemView.setScaleX(1F);
                viewHolder.itemView.setScaleY(1F);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    final float alpha = 1.0f - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView( rvContents);
    }

    private void initDatas() {
        lists = new ArrayList<>();
        lists.add(new ImageInfor(R.mipmap.ic_launcher, "安卓"));
        lists.add(new ImageInfor(R.mipmap.ic_launcher, "Win"));
        lists.add(new ImageInfor(R.mipmap.ic_launcher, "苹果"));
        lists.add(new ImageInfor(R.mipmap.ic_launcher, "黑莓"));
        lists.add(new ImageInfor(R.mipmap.ic_launcher, "华为"));
        lists.add(new ImageInfor(R.mipmap.ic_launcher, "小米"));
    }

    private void initViews() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        rvContents = (RecyclerView) findViewById(R.id.rv_contents);
        tvTitle.setText("卡片");
        btnSkip.setVisibility(View.GONE);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<ImageInfor> list;

        public MyAdapter(List<ImageInfor> list) {
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_card, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
            myViewHolder.iv_backgroud.setBackgroundResource(list.get(i).getImageId());
            myViewHolder.tv_title.setText(list.get(i).getName());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private ImageView iv_backgroud;
            private TextView tv_title;

            public MyViewHolder(View itemView) {
                super(itemView);
                iv_backgroud = (ImageView) itemView.findViewById(R.id.picture);
                tv_title = (TextView) itemView.findViewById(R.id.name);
                tv_title.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(view, getPosition());
                }
            }
        }

        private MyItemClickListener mItemClickListener;

        /**
         * 设置Item点击监听
         *
         * @param listener
         */
        public void setOnItemClickListener(MyItemClickListener listener) {
            this.mItemClickListener = listener;
        }

    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }

}
