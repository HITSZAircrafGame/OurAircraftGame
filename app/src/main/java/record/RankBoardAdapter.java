package record;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircraftgame.R;
import com.example.aircraftgame.RankBoard;

import java.util.ArrayList;
import java.util.List;

public class RankBoardAdapter extends RecyclerView.Adapter<RankBoardAdapter.ViewHolder> {
    private List<PlayerRecord> mrecords;
    private Context parContext;
    private ScoreBoard sb;

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView rank;
        TextView name;
        TextView score;
        TextView time;

        public ViewHolder(@NonNull View view) {
            super(view);
            linearLayout = view.findViewById(R.id.layout_rank_board_item);
            rank = view.findViewById(R.id.text_rank);
            name = view.findViewById(R.id.text_name);
            score = view.findViewById(R.id.text_score);
            time = view.findViewById(R.id.text_time);
        }
    }

    public RankBoardAdapter(Context context,ScoreBoard sb,List<PlayerRecord> records){
        this.mrecords = records;
        this.parContext = context;
        this.sb = sb;
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
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteTip(position);
                return true;
            }
        });
    }

    /**
     * 弹出对话框，提示是否需要删除选中的游玩记录
     **/
    private void deleteTip(int position){
        AlertDialog alert = null;
        AlertDialog.Builder bulider = null;
        String tipMessage = "您确定要删除第" + (position+1) + "名的玩家" + mrecords.get(position).getName() + "的记录吗？";
        bulider = new AlertDialog.Builder(parContext)
                .setTitle("删除记录")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(tipMessage)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sb.deleteRecords(mrecords.get(position).getTime());
                        mrecords.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(parContext, "删除成功",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(parContext, "删除取消",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        alert = bulider.create();
        alert.show();
    }

    @Override
    public int getItemCount(){
        return mrecords.size();
    }
}
