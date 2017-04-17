package com.lzr.autolayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.TypedValue;
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

public class CardActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView tvTitle;
    private Button btnSkip;
    private ImageView ivBack;
    private RecyclerView rvContents;
    private List<ImageInfor> lists;
    private MyAdapter adpater;
    private SwipeRefreshLayout mSwipeRefreshLayout;


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
        //添加加载时颜色变化
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        //调整进度条距离屏幕顶部的距离
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        rvContents.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);



            }
        });

        adpater.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Toast.makeText(getApplication(), "点击了：" + postion, Toast.LENGTH_SHORT).show();
            }
        });

        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN | ItemTouchHelper.UP
                | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT | ItemTouchHelper.START
                | ItemTouchHelper.END) {


            /**
             * 当Item被拖拽的时候被回调
             *
             * @param recyclerView     recyclerView
             * @param viewHolder    拖拽的ViewHolder
             * @param target 目的地的viewHolder
             * @return
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Collections.swap(lists, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adpater.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            /**
             *   当滑动的时候调用
             * @param viewHolder
             * @param direction
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                lists.remove(viewHolder.getAdapterPosition());
                adpater.notifyItemRemoved(viewHolder.getAdapterPosition());
            }


            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (viewHolder != null && actionState != ItemTouchHelper.ACTION_STATE_SWIPE) {
                    viewHolder.itemView.setScaleX(1.5F);
                    viewHolder.itemView.setScaleY(1.5F);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);

                viewHolder.itemView.setScaleX(1F);
                viewHolder.itemView.setScaleY(1F);
            }

//            @Override
//            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
//                    final float alpha = 1.0f - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
//                    viewHolder.itemView.setAlpha(alpha);
//                    viewHolder.itemView.setTranslationX(dX);
//                }
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvContents);
    }

    private void initDatas() {
        lists = new ArrayList<>();
        lists.add(new ImageInfor(R.mipmap.timg, "安卓"));
        lists.add(new ImageInfor(R.mipmap.timg, "Win"));
        lists.add(new ImageInfor(R.mipmap.timg, "苹果"));
        lists.add(new ImageInfor(R.mipmap.timg, "黑莓"));
        lists.add(new ImageInfor(R.mipmap.timg, "华为"));
        lists.add(new ImageInfor(R.mipmap.timg, "小米"));
    }

    private void initViews() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        rvContents = (RecyclerView) findViewById(R.id.rv_contents);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.card_refresh);
        tvTitle.setText("卡片");
        btnSkip.setVisibility(View.GONE);
        ivBack.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    /**
     * recyclerview下拉刷新
     */
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<ImageInfor> newDatas = new ArrayList<ImageInfor>();
                for (int i = 0; i < 5; i++) {
                    int index = i + 1;
                    newDatas.add(new ImageInfor(R.mipmap.timg, index + ""));
                }
                adpater.addItem(newDatas);
                //设置SwipeRefreshLayout当前是否处于刷新状态，一般是在请求数据的时候设置为true，在数据被加载到View中后，设置为false。
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(CardActivity.this, "更新了五条数据...", Toast.LENGTH_SHORT).show();
            }
        }, 5000);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<ImageInfor> list;

        public MyAdapter(List<ImageInfor> list) {
            this.list = list;
        }

        /**
         *  添加数据
         * @param newLists
         */
        public void addItem(List<ImageInfor> newLists) {
            //新集合中添加旧有数据,然后清除旧数据集合,
            // 旧集合重新添加新集合,,完成排序,否则新增数据会在最底出现
            newLists.addAll(list);
            list.removeAll(list);
            list.addAll(newLists);
            notifyDataSetChanged();
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
