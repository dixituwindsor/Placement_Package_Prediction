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

public class CLAdapter extends RecyclerView.Adapter<CLAdapter.holder> {

    Context context;
    ArrayList<DataModel> dataModelArrayList;

    public CLAdapter(Context context, ArrayList<DataModel> dataModelArrayList) {
        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.companyjob,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
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

        String id = dataModel.getId();
        String title = dataModel.getTitle();

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,dataModel.getId()+dataModel.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,ApplyCompany.class);
                intent.putExtra("ID",dataModel.getId());
                intent.putExtra("TITLE",dataModel.getTitle());
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
