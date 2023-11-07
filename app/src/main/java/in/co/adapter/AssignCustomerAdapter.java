package in.co.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.co.modelclass.AssignCustomer_ModelClass;
import in.co.modelclass.Report_ModelClass;
import in.co.umcsl.R;

public class AssignCustomerAdapter extends RecyclerView.Adapter<AssignCustomerAdapter.ViewHolder> {

    Context context;
    ArrayList<AssignCustomer_ModelClass> assigncus;
    ArrayList<AssignCustomer_ModelClass> sellProduct;

    public AssignCustomerAdapter(ArrayList<AssignCustomer_ModelClass> assigncustomer, FragmentActivity activity) {

        this.context = activity;
        this.assigncus = assigncustomer;
        this.sellProduct = new ArrayList<>();
        this.sellProduct.addAll(assigncustomer);
    }

    @NonNull
    @Override
    public AssignCustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assigncustomer,parent,false);
        return new AssignCustomerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignCustomerAdapter.ViewHolder holder, int position) {

        AssignCustomer_ModelClass assis = assigncus.get(position);

        holder.text_AccountNo.setText(assis.getAccountNo());
        holder.text_OpeningDate.setText(assis.getDate());
        holder.text_CustomerName.setText(assis.getName());
        holder.text_MobileNumber.setText(assis.getMobileNo());
        holder.text_Statues.setText(assis.getStatues());
    }

    @Override
    public int getItemCount() {
        return assigncus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_OpeningDate,text_AccountNo,text_CustomerName,text_MobileNumber,text_Statues;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_Statues = itemView.findViewById(R.id.text_Statues);
            text_OpeningDate = itemView.findViewById(R.id.text_OpeningDate);
            text_AccountNo = itemView.findViewById(R.id.text_AccountNo);
            text_CustomerName = itemView.findViewById(R.id.text_CustomerName);
            text_MobileNumber = itemView.findViewById(R.id.text_MobileNumber);
        }
    }

    public void filter(CharSequence charSequence){

        ArrayList<AssignCustomer_ModelClass> tempArrayList = new ArrayList<AssignCustomer_ModelClass>();

        if(!TextUtils.isEmpty(charSequence)){

            for(AssignCustomer_ModelClass item : assigncus){

                if(item.getAccountNo().contains((charSequence))){
                    tempArrayList.add(item);
                }
            }

        }else{

            assigncus.addAll(sellProduct);
        }

        assigncus.clear();
        assigncus.addAll(tempArrayList);
        notifyDataSetChanged();
        tempArrayList.clear();
    }
}
