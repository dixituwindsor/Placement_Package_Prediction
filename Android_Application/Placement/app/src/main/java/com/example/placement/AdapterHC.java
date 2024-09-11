package com.example.placement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterHC extends RecyclerView.Adapter<AdapterHC.holder> {

    Context context;
    ArrayList<DataModel> dataModelArrayList;

    public AdapterHC(Context context, ArrayList<DataModel> dataModelArrayList) {
        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    @NonNull
    @Override
    public AdapterHC.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.companyjob,parent,false);
        return new AdapterHC.holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHC.holder holder, int position) {
        DataModel dataModel = dataModelArrayList.get(position);

        holder.cname.setText(dataModel.getCname());
        holder.hscp.setText(dataModel.getHscp());
        holder.gender.setText(dataModel.getGender());
        holder.sscp.setText(dataModel.getSscp());
        holder.degreep.setText(dataModel.getDegreep());
        holder.degreet.setText(dataModel.getDegreet());
        holder.workex.setText(dataModel.getWorkex());
        holder.etestp.setText(dataModel.getEtestp());
        holder.specialization.setText(dataModel.getSpecialization());
        holder.mbap.setText(dataModel.getMbap());
        holder.salary.setText(dataModel.getSalary());
        holder.location.setText(dataModel.getLocation());
        holder.bond.setText(dataModel.getBond());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,dataModel.getId()+dataModel.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,company_job_action.class);
                intent.putExtra("IDC",dataModel.getId());
                intent.putExtra("TITLEC",dataModel.getTitle());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }


    class holder extends RecyclerView.ViewHolder
    {
        TextView cname,gender,sscp,hscp,degreep,degreet,workex,etestp,specialization,mbap,salary,location,bond;
        CardView card;

        public holder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.cjcvdetails);
            cname = itemView.findViewById(R.id.cjtvppcname);
            gender = itemView.findViewById(R.id.cjtvppgender);
            sscp = itemView.findViewById(R.id.cjtvppsscp);
            hscp = itemView.findViewById(R.id.cjtvpphscp);
            degreep = itemView.findViewById(R.id.cjtvppdegreep);
            degreet = itemView.findViewById(R.id.cjtvppdegreet);
            workex = itemView.findViewById(R.id.cjtvppworkex);
            etestp = itemView.findViewById(R.id.cjtvppetestp);
            specialization = itemView.findViewById(R.id.cjtvppspecialisation);
            mbap = itemView.findViewById(R.id.cjtvppmbap);
            salary = itemView.findViewById(R.id.cjtvppsalary);
            location = itemView.findViewById(R.id.cjtvpplocation);
            bond = itemView.findViewById(R.id.cjtvppbond);
        }
    }
}
