package in.co.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.co.modelclass.Report_ModelClass;
import in.co.umcsl.R;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    Context context;
    ArrayList<Report_ModelClass> report;

    public ReportAdapter(ArrayList<Report_ModelClass> report, FragmentActivity activity) {

        this.context = activity;
        this.report = report;

    }

    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder holder, int position) {

        Report_ModelClass rep = report.get(position);

         holder.text_AccountNo.setText(rep.getAccountNo());
         holder.text_OpeningDate.setText(rep.getDate()+""+rep.getTime());
         holder.text_CollectAmount.setText(rep.getCollectAmount());
         holder.text_BalanceAmount.setText(rep.getBalanceAmount());

    }

    @Override
    public int getItemCount() {
        return report.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_OpeningDate,text_AccountNo,text_CollectAmount,text_BalanceAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_OpeningDate = itemView.findViewById(R.id.text_OpeningDate);
            text_AccountNo = itemView.findViewById(R.id.text_AccountNo);
            text_CollectAmount = itemView.findViewById(R.id.text_CollectAmount);
            text_BalanceAmount = itemView.findViewById(R.id.text_BalanceAmount);
        }
    }
}
