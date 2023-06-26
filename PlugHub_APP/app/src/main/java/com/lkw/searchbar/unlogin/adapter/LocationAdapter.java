package com.lkw.searchbar.unlogin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lkw.searchbar.R;
import com.lkw.searchbar.unlogin.model.category_search.Document;
import com.lkw.searchbar.unlogin.utils.BusProvider;
import java.util.ArrayList;

public class LocationAdapter
        extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder>
        implements View.OnClickListener { //d
    Context context; // 어댑터를 사용하는 컨텍스트를 저장하는 변수
    ArrayList<Document> items; // Document 객체의 리스트를 저장하는 변수
    EditText searchView; //사용자가 선택한 위치 정보를 표시하기 위한 EditText를 저장하는 변수입니다.
    RecyclerView recyclerView;  //해당 어댑터가 사용되는 RecyclerView를 저장하는 변수입니다.

    private OnItemClickListener mItemClickListener; //d

    public OnItemClickListener getItemClickListener() { //d
        return mItemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) { //d
        this.mItemClickListener = itemClickListener;
    }

    public LocationAdapter(
        ArrayList<Document> items,
        Context context,
        EditText searchView,
        RecyclerView recyclerView
    ) {
        this.context = context;
        this.items = items;
        this.searchView = searchView;
        this.recyclerView = recyclerView;
    }

    @Override // 여기서는 items 리스트의 크기를 반환하도록 구현되어 있습니다.
    public int getItemCount() {
        return items.size();
    }

    // 새로운 Document 아이템을 item 리스트에 추가하는 역할
    public void addItem(Document item) {
        items.add(item);
    }

    // items 리스트의 모든 아이템을 제거하여 비우는 역할
    public void clear() {
        items.clear();
    }

    @Override //여기서는 Document 객체의 ID를 문자열로 변환하여 반환하도록 구현되어 있습니다.
    public long getItemId(int position) {
        return Long.parseLong(items.get(position).getId());
    }

    @Override // 메서드는 특정 위치에 대한 아이템의 뷰 타입을 반환합니다.
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClick(View v) { //d
        if(v == null) {
            return;
        }
        if(mItemClickListener == null) {
            return;
        }

        int position = recyclerView.getChildAdapterPosition(v);
        mItemClickListener.onItemClick(position, items.get(position));
    }

    @NonNull
    @Override
    // 뷰 홀더를 생성 =
    // 개별적인 데이터를 표시하기 위해 동적으로 바인딩하기 위함 =
    // 아이템을 묶고 LocationViewHolder로 전달
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_location, viewGroup, false);

        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int i) {
        final Document model = items.get(i);

        holder.itemView.setOnClickListener(this); // holder 안의 아이템 클릭시 1
        holder.placeNameText.setText(model.getPlaceName());
        holder.addressText.setText(model.getAddressName());

        // 리스트 안에 주소를 클릭했을 시, 이벤트 처리
        holder.placeNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 이벤트 버스 객체 생성후 model 데이터를 싣고 정류소 돌아다니는 역할
                BusProvider.getInstance().post(model);
            }
        });
    }


    // 전달받은 아이템들을 TextView 로 뿌리는 작업
    public class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView placeNameText;
        TextView addressText;

        public LocationViewHolder(@NonNull final View itemView) {
            super(itemView);
            placeNameText = itemView.findViewById(R.id.ltem_location_tv_placename);
            addressText = itemView.findViewById(R.id.ltem_location_tv_address);
        }
    }

    public interface OnItemClickListener { //d
        void onItemClick(int index, Document document);
    }

}
