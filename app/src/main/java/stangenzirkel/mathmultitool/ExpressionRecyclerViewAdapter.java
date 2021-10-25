package stangenzirkel.mathmultitool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import stangenzirkel.mathmultitool.calculator.Calculator;
import stangenzirkel.mathmultitool.calculator.Expression;
import stangenzirkel.mathmultitool.usefulfunctions.UsefulFunctions;

public class ExpressionRecyclerViewAdapter extends RecyclerView.Adapter<ExpressionRecyclerViewAdapter.ExpressionViewHolder> {
    String tag = "RVAdapterTag";

    public static class ExpressionViewHolder extends RecyclerView.ViewHolder{
        String tag = "ExpressionViewHolderTag";

        CardView cv;
        TextView expressionTV;
        TextView resultTV;

        Expression expression;

        ExpressionViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv_expression);
            expressionTV = itemView.findViewById(R.id.card_expression);
            resultTV = itemView.findViewById(R.id.card_result);
        }

        public Expression getExpression() {
            return expression;
        }

        public void setExpression(Expression expression) {
            this.expression = expression;
        }
    }

    List<Expression> expressions;

    ExpressionRecyclerViewAdapter(List<Expression> expressions){
        this.expressions = expressions;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ExpressionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_saved_expressions_item, viewGroup, false);
        ExpressionViewHolder expressionViewHolder = new ExpressionViewHolder(v);
        return expressionViewHolder;
    }

    @Override
    public void onBindViewHolder(ExpressionViewHolder expressionViewHolder, int i) {
        expressionViewHolder.expressionTV.setText(Calculator.expressionToString(expressions.get(i)));
        expressionViewHolder.resultTV.setText(expressions.get(i).getResult());
        expressionViewHolder.setExpression(expressions.get(i));
    }

    @Override
    public int getItemCount() {
        return expressions.size();
    }
}