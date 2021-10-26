package stangenzirkel.mathmultitool;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import stangenzirkel.mathmultitool.calculator.Calculator;
import stangenzirkel.mathmultitool.calculator.Expression;
import stangenzirkel.mathmultitool.usefulfunctions.UsefulFunctions;

public class ExpressionRecyclerViewAdapter extends RecyclerView.Adapter<ExpressionRecyclerViewAdapter.ExpressionViewHolder> {
    String tag = "ExpressionRecyclerViewAdapterTag";
    SavedExpressionsFragment fragment;

    public static class ExpressionViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {
        String tag = "ExpressionViewHolderTag";

        CardView cv;
        TextView expressionTV;
        TextView resultTV;
        ImageButton btn_del;

        Expression expression;
        SavedExpressionsFragment fragment;

        ExpressionViewHolder(View itemView, SavedExpressionsFragment fragment) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv_expression);
            expressionTV = itemView.findViewById(R.id.card_expression);
            resultTV = itemView.findViewById(R.id.card_result);
            btn_del = itemView.findViewById(R.id.btn_del_expression);

            itemView.setOnClickListener(this);
            btn_del.setOnClickListener(this);

            this.fragment = fragment;

        }

        public Expression getExpression() {
            return expression;
        }

        public void setExpression(Expression expression) {
            this.expression = expression;
        }

        @Override
        public void onClick(View v) {
            Log.d(tag, "Click to " + v.getClass().getName());

            switch (v.getId()) {
                case R.id.btn_del_expression:
                    Log.d(tag, "Clicked to del expression " + expression.toString());
                    CalculatorDB.getInstance().deleteExpression(expression.getId());
                    fragment.initializeAdapter();
                    break;

                default:
                    Log.d(tag, "Clicked to expression card " + expression.toString());
                    String expressionString =  UsefulFunctions.joinString("|", expression.getExpressionParts());
                    SharedPreferences.Editor editor = fragment.getActivity().getPreferences(Activity.MODE_PRIVATE).edit();
                    editor.putString("expressionKey", expressionString);
                    editor.apply();
                    ((MainActivity) fragment.getActivity()).onExpressionCardClick();
                    break;
            }
        }
    }

    List<Expression> expressions;

    ExpressionRecyclerViewAdapter(List<Expression> expressions, SavedExpressionsFragment fragment){
        this.expressions = expressions;
        this.fragment = fragment;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ExpressionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_saved_expressions_item, viewGroup, false);
        ExpressionViewHolder expressionViewHolder = new ExpressionViewHolder(v, fragment);
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