package com.example.agilesavev2.services;

import com.example.agilesavev2.models.Budgets.Budget;
import com.example.agilesavev2.models.ai.LineGraphData;
import com.example.agilesavev2.models.currency.Currency;
import com.example.agilesavev2.models.saving_goal.SavingsGoal;
import com.example.agilesavev2.models.subscriptions.Subscription;
import com.example.agilesavev2.models.subscriptions.SubscriptionAvg;
import com.example.agilesavev2.models.transactions.Category;
import com.example.agilesavev2.models.transactions.SpendingGoal;
import com.example.agilesavev2.models.transactions.Transaction;
import com.example.agilesavev2.models.transactions.TransactionSimple;
import com.example.agilesavev2.models.users.Account;
import com.example.agilesavev2.models.users.User;
import com.example.agilesavev2.models.users.UserPayInfo;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Repository {

    @POST("/login")
    Call<User> executeLogin(@Body HashMap<String,String> map);


    @POST("/register")
    Call<Void> executeRegister(@Body HashMap<String,String> map);

    @POST("/getAIBudgetData")
    Call<List<LineGraphData>> getAIBudgetData(@Body HashMap<String , String> map);


    @POST("/getAllUserTransactionsBetweenDates")
    Call<List<TransactionSimple>> getAllUserTransactionsBetweenDates(@Body HashMap<String , String> map);

    @POST("/userData")
    Call<User> getUser(@Body HashMap<String , String> map);

    @POST("/updateBalance")
    Call<Void> updateBalance(@Body HashMap<String,String> map);

    @POST("/getBudget")
    Call<List<Budget>> getBudgets(@Body HashMap<String,String> map);

    @POST("/sendTransactionData")
    Call<Void> sendTransactionData(@Body HashMap<String,String> map);

    @POST("/getTransactionData")
    Call<List<Transaction>> getTransactionData(@Body HashMap<String,String> map);

    @POST("/userSavingGoals")
    Call<List<SpendingGoal>> getSpendingGoals(@Body HashMap<String,String> map);

    @POST("/getBalance")
    Call<User> getBalance(@Body HashMap<String,String> map);

    @POST("/updateUser")
    Call<User>updateUser (@Body HashMap<String,String> map);

    @POST("/sendBudget")
    Call<Void> sendBudget(@Body HashMap<String,String> map);

    // savings goal
    // send saving goal
    @POST("/sendSavingGoal")
    Call<Void> sendSavingGoal(@Body HashMap<String,String> map);

    // get all saving goals (given userID)
    @POST("/getSavingGoals")
    Call<List<SavingsGoal>> getSavingGoals(@Body HashMap<String,String> map);

    @POST("/deleteSaving")
    Call<Void> deleteSaving(@Body HashMap<String, String> map);

    @POST("/updateSaving")
    Call<Void> updateSaving(@Body HashMap<String, String> map);

    @POST("/updateBudget")
    Call<Void> updateBudget(@Body HashMap<String,String> map);

    @POST("/deleteBudget")
    Call<Void> deleteBudget(@Body HashMap<String, String> map);

    @POST("/addAccount")
    Call<Void> addAccount(@Body HashMap<String,String> map);

    @POST("/getAccounts")
    Call<List<Account>> getAccounts(@Body HashMap<String, String> map);

    @POST("/deleteAccount")
    Call<Void> deleteAccount(@Body HashMap<String, String> map);

    @POST("/getAllTransactionData")
    Call<List<Transaction>> getAllUserTransactionData(@Body HashMap<String, String> map);

    @POST("/getCurrencyConversionRates")
    Call<List<Currency>> getCurrencyConversionRates (@Body HashMap<String,String> map);

    @POST("/addPayInfo")
    Call<Void> addPayInfo(@Body HashMap<String, String> map);

    @POST("/getPayInfo")
    Call<UserPayInfo> getPayInfo(@Body HashMap<String, String> map);

    @POST("/addCategory")
    Call<Void> addCategory(@Body HashMap<String, String> map);

    @POST("/getCategories")
    Call<List<Category>> getCategories(@Body HashMap<String, String> map);

    @POST("/addSubscription")
    Call<Void> sendSubscription(@Body HashMap<String, String> map);

    @POST("/getSubscriptions")
    Call<List<Subscription>> getSubscriptions(@Body HashMap<String, String> map);

    @GET("/getSubscriptionAVG")
    Call<List<SubscriptionAvg>> getSubscriptionAvg();

    @POST("/updateSubscription")
    Call<Void> updateSubscripton(@Body HashMap<String, String> map);

    @POST("/deleteSubscription")
    Call<Void> deleteSubscription(@Body HashMap<String, String> map);

    @POST("/deleteUser")
    Call<Void> deleteUser(@Body HashMap<String, String> map);

    @POST("/updatePayDate")
    Call<Void> updatePayDate(@Body HashMap<String, String> map);

}
