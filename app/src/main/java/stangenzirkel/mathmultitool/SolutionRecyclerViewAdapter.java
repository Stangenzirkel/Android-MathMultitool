package stangenzirkel.mathmultitool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import stangenzirkel.mathmultitool.converter.SolutionPart;
import stangenzirkel.mathmultitool.usefulfunctions.UsefulFunctions;

public class SolutionRecyclerViewAdapter extends RecyclerView.Adapter<SolutionRecyclerViewAdapter.SolutionViewHolder> {
    String tag = "RVAdapterTag";

    public static class SolutionViewHolder extends RecyclerView.ViewHolder{
        String tag = "SolutionViewHolderTag";

        CardView cv;
        TextView headerTV;
        TextView bodyTV;

        SolutionPart solutionPart;

        SolutionViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv_solution_part);
            headerTV = itemView.findViewById(R.id.card_header);
            bodyTV = itemView.findViewById(R.id.card_text);
        }

        public SolutionPart getSolutionPart() {
            return solutionPart;
        }

        public void setSolutionPart(SolutionPart solutionPart) {
            this.solutionPart = solutionPart;
        }
    }

    List<SolutionPart> solutionParts;

    SolutionRecyclerViewAdapter(List<SolutionPart> solutionParts){
        this.solutionParts = solutionParts;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public SolutionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_solution_item, viewGroup, false);
        SolutionViewHolder solutionViewHolder = new SolutionViewHolder(v);
        return solutionViewHolder;
    }

    @Override
    public void onBindViewHolder(SolutionViewHolder solutionViewHolder, int i) {
        solutionViewHolder.headerTV.setText(solutionParts.get(i).getHeader());
        solutionViewHolder.bodyTV.setText(UsefulFunctions.joinString("\n", Arrays.asList(solutionParts.get(i).getText())));
        solutionViewHolder.setSolutionPart(solutionParts.get(i));
    }

    @Override
    public int getItemCount() {
        return solutionParts.size();
    }
}