package record;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircraftgame.R;

import java.util.ArrayList;
import java.util.List;

public class RankBoardAdapter extends RecyclerView.Adapter<RankBoardAdapter.ViewHolder> {
    private List<PlayerRecord> mrecords;
    private List<Boolean> checkList = new ArrayList<>();
    private List<String> selectedRecordTimeList = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        CheckBox checkBox;
        TextView rank;
        View slice1;
        TextView name;
        View slice2;
        TextView score;
        View slice3;
        TextView time;
        View bottomBound;

        public ViewHolder(@NonNull View view) {
            super(view);
            linearLayout = view.findViewById(R.id.RankBoardItemInnerLinearLayout);
            checkBox = view.findViewById(R.id.checkBox1);
            rank = view.findViewById(R.id.header1);
            slice1 = view.findViewById(R.id.view1);
            name = view.findViewById(R.id.header2);
            slice2 = view.findViewById(R.id.view2);
            score = view.findViewById(R.id.header3);
            slice3 = view.findViewById(R.id.view3);
            time = view.findViewById(R.id.header4);
            bottomBound = view.findViewById(R.id.view4);
        }
    }

    public RankBoardAdapter(List<PlayerRecord> records){
        mrecords = records;
        for (int i = 0; i < records.size(); i++){
            checkList.add(false);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_rank_board_view,
                                                                     parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position){
        PlayerRecord pr = mrecords.get(position);
        holder.rank.setText("" + (position+1));
        holder.name.setText(pr.getName());
        holder.score.setText("" + pr.getScore());
        holder.time.setText(pr.getTime());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.d("isCalled","Yes! " + position + " " + isChecked);
                checkList.set(position, isChecked);
                Log.d("checkListSize","" + checkList.size());
            }
        });
        holder.checkBox.setChecked(checkList.get(position));
    }

    @Override
    public int getItemCount(){
        return mrecords.size();
    }

    public void deleteSelectedRow(){
        for(int i = 0; i < mrecords.size(); i++){
            if(checkList.get(i) != null && checkList.get(i)){
                mrecords.remove(i);
                checkList.remove(i);
                i--;
            }
        }
        notifyDataSetChanged();
    }

    public void checkDeletingRecord(){
        for(int i = 0; i < checkList.size(); i++){
            if(checkList.get(i) != null && checkList.get(i)){
                selectedRecordTimeList.add(mrecords.get(i).getTime());
            }
        }
    }
    public List<String> getSelectedRecordTimeList() {
        return selectedRecordTimeList;
    }
}
