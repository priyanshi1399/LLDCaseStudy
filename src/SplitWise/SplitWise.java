package SplitWise;

import SplitWise.Expense.ExpenseSplitType;
import SplitWise.Expense.Split.Split;
import SplitWise.Group.Group;
import SplitWise.Group.GroupController;
import SplitWise.User.User;
import SplitWise.User.UserController;

import java.util.ArrayList;
import java.util.List;

public class SplitWise {

    UserController userController;
    GroupController groupController;

    BalanceSheetController balanceSheetController;

    SplitWise(){
        userController=new UserController();
        groupController=new GroupController();
        balanceSheetController=new BalanceSheetController();

    }


    public void demo(){

        setupUserAndGroup();

        //Step-1 add member to group
        Group group=groupController.getGroup("G1001");
        group.addMember(userController.getUser("U2001"));
        group.addMember(userController.getUser("U3001"));

        //Step-2 create an expense inside a group
        List<Split> splits=new ArrayList<>();

        //create an expense inside group
        Split split1=new Split(userController.getUser("U1001"),300);
        Split split2=new Split(userController.getUser("U2001"),300);
        Split split3=new Split(userController.getUser("U3001"),300);
        splits.add(split1);
        splits.add(split2);
        splits.add(split3);
        group.createExpense("Exp1001","Breakfast",900,splits, ExpenseSplitType.EQUAL,userController.getUser("U2001"));

        for(User user:userController.getAllUsers()){
            balanceSheetController.showBalanceSheetOfUser(user);
        }
    }

    public void setupUserAndGroup(){

        //onboard user to splitwise app
        addUsersToSplitWiseApp();

        //craete a group by user1
        User user1=userController.getUser("U1001");
        groupController.createNewGroup("G1001","Outing with friends",user1);
            }


            private void addUsersToSplitWiseApp(){

        //adding user1
                User user1=new User("U1001","User1");

                //adding user2

                User user2=new User("U2001","User2");

                //adding User3
                User user3=new User("U3001","User3");

                userController.addUser(user1);
                userController.addUser(user2);
                userController.addUser(user3);
            }
}
