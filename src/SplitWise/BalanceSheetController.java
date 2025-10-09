package SplitWise;

import SplitWise.Expense.Split.Split;
import SplitWise.User.User;

import java.util.List;
import java.util.Map;

public class BalanceSheetController {

    //step1- update for payer amount suppose Alice bob charlie Alice paid 300
    //Step 2: Loop through everyone who owes
    //Step 3: If payer = owe user (self-payment)

    //this will be called whenever new expense created
    public void updateUserExpenseBalanceSheet(User expensePaidBy, List<Split> splits,double totalExpenseAmount){


        //update the total amount paid of expense paid by user
        UserExpenseBalanceSheet paidByUserExpenseSheet =expensePaidBy.getUserExpenseBalanceSheet();
        paidByUserExpenseSheet .setTotalPayment(paidByUserExpenseSheet .getTotalPayment()+totalExpenseAmount);


        for(Split split:splits){
            User userOwe=split.getUser();

            UserExpenseBalanceSheet oweUserExpenseSheet =userOwe.getUserExpenseBalanceSheet();
            double oweAmount=split.getAmountOwe();

            //Step 3: If payer = owe user (self-payment)
            if(expensePaidBy.getUserId().equals(userOwe.getUserId())){
                paidByUserExpenseSheet .setTotalYouGetBack(paidByUserExpenseSheet .getTotalYouGetBack()+oweAmount);
            }
            else{

                //update the balance of paid user
                // Update payer’s get back
                paidByUserExpenseSheet.setTotalYouGetBack(paidByUserExpenseSheet.getTotalYouGetBack()+oweAmount);

               // Step 5: Maintain mapping of who owes whom
                Balance userOweBalance;

                if(paidByUserExpenseSheet.getUserVsBalance().containsKey(userOwe.getUserId())){


                    userOweBalance=paidByUserExpenseSheet.getUserVsBalance().get(userOwe.getUserId());

                }
                else{
                    userOweBalance=new Balance();
                    paidByUserExpenseSheet.getUserVsBalance().put(userOwe.getUserId(), userOweBalance);

                }
                userOweBalance.setAmountGetBack(userOweBalance.getAmountGetBack()+oweAmount);


                //update the balance sheet of owe user

                //Step 6: Update owe user’s sheet
                oweUserExpenseSheet.setTotalYouOwe(oweUserExpenseSheet.getTotalYouOwe() + oweAmount);
                oweUserExpenseSheet.setTotalYourExpense(oweUserExpenseSheet.getTotalYourExpense() + oweAmount);

                //Step 7: Track opposite balance for the owe user
                //this is updating the per person balance map
                Balance userPaidBalance;
                if(oweUserExpenseSheet.getUserVsBalance().containsKey(expensePaidBy.getUserId())){
                    userPaidBalance = oweUserExpenseSheet.getUserVsBalance().get(expensePaidBy.getUserId());
                }
                else{
                    userPaidBalance = new Balance();
                    oweUserExpenseSheet.getUserVsBalance().put(expensePaidBy.getUserId(), userPaidBalance);
                }
                userPaidBalance.setAmountOwe(userPaidBalance.getAmountOwe() + oweAmount);
            }


        }
        }

        public void showBalanceSheetOfUser(User user){

            System.out.println("-----------------------------------");

            System.out.println("balance sheet of user: "+user.getUserId());

            UserExpenseBalanceSheet userExpenseBalanceSheet=user.getUserExpenseBalanceSheet();

            System.out.println("TotalYourExpense: "+userExpenseBalanceSheet.getTotalYourExpense());
            System.out.println("TotalGetBack : "+userExpenseBalanceSheet.getTotalYouGetBack());
            System.out.println("TotalYourOwe: "+userExpenseBalanceSheet.getTotalYouOwe());
            System.out.println("TotalPaymentMade: "+userExpenseBalanceSheet.getUserVsBalance().entrySet());

            for(Map.Entry<String,Balance> entry:userExpenseBalanceSheet.getUserVsBalance().entrySet()){

                String userId=entry.getKey();
                Balance balance=entry.getValue();

                System.out.println("userID: " +userId+ "YourGetBack: "+balance.getAmountGetBack()+ "YourOwe: "+balance.getAmountOwe());
            }

            System.out.println( "_____________________________________________________________________");




    }
}
