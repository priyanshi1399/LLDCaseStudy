package SplitWise.User;

import SplitWise.UserExpenseBalanceSheet;

public class User {

    String userId;
    String userName;
    UserExpenseBalanceSheet userExpenseBalanceSheet;


    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.userExpenseBalanceSheet = new UserExpenseBalanceSheet();
    }


    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public UserExpenseBalanceSheet getUserExpenseBalanceSheet() {
        return userExpenseBalanceSheet;
    }
}
