package SplitWise.Expense.Split;

import java.util.List;

public class EqualExpenseSplit implements ExpenseSplit {
    @Override
    public void validateSplitRequest(List<Split> splitList, double totalAmount) {
        for(Split split:splitList){
            if(split.getAmountOwe()!=totalAmount/ splitList.size()){

                throw new RuntimeException("amount should be matching and equal");
            }
        }
    }
}
