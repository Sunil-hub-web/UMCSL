package in.co.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.co.extra.Appurl;
import in.co.fragment.PrintDocument;
import in.co.modelclass.AssignCustomer_ModelClass;
import in.co.modelclass.Report_ModelClass;
import in.co.umcsl.R;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    Context context;
    ArrayList<Report_ModelClass> report;
    ArrayList<Report_ModelClass> sellProduct;

    public ReportAdapter(ArrayList<Report_ModelClass> report, FragmentActivity activity) {

        this.context = activity;
        this.report = report;
        this.sellProduct = new ArrayList<>();
        this.sellProduct.addAll(report);

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
         holder.text_CollectAmount.setText("Rs. "+rep.getCollectAmount());
         holder.text_UserName.setText(rep.getUName());
         holder.text_BalanceAmount.setText("Rs. "+rep.getBalanceAmount());

         holder.printfile.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 String vocherno = String.valueOf(rep.getVocherNumber());

                 if (vocherno.equals("null")){

                     Toast.makeText(context, "vocherNumber not equals null", Toast.LENGTH_SHORT).show();

                 }else{

                     AppCompatActivity activity = (AppCompatActivity) v.getContext();
                     PrintDocument myFragment = new PrintDocument();
                     Bundle bundle=new Bundle();
                     bundle.putString("VocherNumber", rep.getVocherNumber());
                     bundle.putString("Youkey", "ReportFragment");
                     myFragment.setArguments(bundle);
                     activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment)
                             .addToBackStack(null).commit();
                 }

             }
         });

    }

    @Override
    public int getItemCount() {
        return report.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_OpeningDate,text_AccountNo,text_CollectAmount,text_BalanceAmount,printfile,text_UserName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_OpeningDate = itemView.findViewById(R.id.text_OpeningDate);
            text_AccountNo = itemView.findViewById(R.id.text_AccountNo);
            text_CollectAmount = itemView.findViewById(R.id.text_CollectAmount);
            text_BalanceAmount = itemView.findViewById(R.id.text_BalanceAmount);
            printfile = itemView.findViewById(R.id.printfile);
            text_UserName = itemView.findViewById(R.id.text_UserName);
        }
    }

    public void filter(CharSequence charSequence){

        ArrayList<Report_ModelClass> tempArrayList = new ArrayList<Report_ModelClass>();

        if(!TextUtils.isEmpty(charSequence)){

            for(Report_ModelClass item : report){

                if(item.getAccountNo().contains((charSequence))){
                    tempArrayList.add(item);
                }
            }

        }else{

            report.addAll(sellProduct);
        }

        report.clear();
        report.addAll(tempArrayList);
        notifyDataSetChanged();
        tempArrayList.clear();
    }
}
