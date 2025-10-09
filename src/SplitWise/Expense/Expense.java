package SplitWise.Expense;

import SplitWise.Expense.Split.Split;
import SplitWise.User.User;

import java.util.ArrayList;
import java.util.List;

public class Expense {

    String expenseId;
    String description;
    double ExpenseAmount;
    User paidByUser;
    ExpenseSplitType splitType;
    List<Split> splitDetails=new ArrayList<>();

    public Expense(String expenseId, String description, double expenseAmount, User paidByUser, ExpenseSplitType splitType, List<Split> splitDetails) {
        this.expenseId = expenseId;
        this.description = description;
        ExpenseAmount = expenseAmount;
        this.paidByUser = paidByUser;
        this.splitType = splitType;
        this.splitDetails.addAll(splitDetails);
    }
}
