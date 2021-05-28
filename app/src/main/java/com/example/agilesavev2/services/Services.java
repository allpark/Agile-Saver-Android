package com.example.agilesavev2.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.agilesavev2.assets.Presenter;
import com.example.agilesavev2.fragments.saving_goals.SavingGoal;
import com.example.agilesavev2.models.Budgets.Budget;
import com.example.agilesavev2.models.ai.LineGraphData;
import com.example.agilesavev2.models.currency.Currency;
import com.example.agilesavev2.models.saving_goal.SavingsGoal;
import com.example.agilesavev2.models.subscriptions.Subscription;
import com.example.agilesavev2.models.subscriptions.SubscriptionAvg;
import com.example.agilesavev2.models.transactions.Category;
import com.example.agilesavev2.models.transactions.Transaction;
import com.example.agilesavev2.models.transactions.TransactionSimple;
import com.example.agilesavev2.models.users.Account;
import com.example.agilesavev2.models.users.User;
import com.example.agilesavev2.models.users.UserPayInfo;
import com.example.agilesavev2.views.login.LoginPresenter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Services {
    private Retrofit retrofit;
    private Repository repository;
    private static DatabaseHelper databaseHelper;
    //http://10.0.2.2:3000/ for logcal back end
    //https://agile-saver-back-end.herokuapp.com/ for remote heroku back end
    private String BASE_URL = "https://agile-saver-back-end.herokuapp.com/";
    private Presenter presenter;


    public Services(Presenter presenter) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        repository = retrofit.create(Repository.class);
        this.presenter = presenter;

    }

    public Services(Presenter presenter, Context context) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        repository = retrofit.create(Repository.class);
        this.presenter = presenter;
        System.out.println(context);
        databaseHelper = new DatabaseHelper(context);

    }

    public void cacheUserData(int id){
        HashMap<String,String> map = new HashMap<>();
        map.put("userID",Integer.toString(id));


        Call<List<Transaction>> call1 = repository.getAllUserTransactionData(map);
        call1.enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if(response.code()==200){
                    databaseHelper.truncateTable("transactions");
                    response.body().forEach(t ->{
                        System.out.println("transactions");
                        HashMap<String,String> values = new HashMap<>();
                        values.put("id", Integer.toString(t.getId()));
                        values.put("userID", Integer.toString(t.getUserID()));
                        values.put("accountID", Integer.toString(t.getAccountID()));
                        values.put("transactionValue", Double.toString(t.getTransaction()));
                        values.put("type", t.getType());
                        values.put("currency", t.getCurrency());
                        values.put("subCategory", t.getSubCategory());
                        values.put("date", t.getDate());
                        values.put("name", t.getName());
                        values.put("category", t.getCategory());
                        values.put("balance", Double.toString(t.getBalance()));
                        databaseHelper.populateTable("transactions", values);
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {

            }
        });




        Call<List<Account>> call2 = repository.getAccounts(map);
        call2.enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                if (response.code() == 200) {
                    databaseHelper.truncateTable("account");
                    response.body().forEach(a -> {
                        System.out.println("accounts");
                        HashMap<String, String> values = new HashMap<>();
                        values.put("id", Integer.toString(a.getID()));
                        values.put("userID", Integer.toString(a.getUserID()));
                        values.put("currency", a.getCurrency());
                        values.put("balance", Double.toString(a.getBalance()));
                        values.put("name", a.getName());
                        values.put("type", a.getType());
                        databaseHelper.populateTable("account", values);
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {

            }
        });




        Call<List<SavingsGoal>> call3 = repository.getSavingGoals(map);
        call3.enqueue(new Callback<List<SavingsGoal>>() {
            @Override
            public void onResponse(Call<List<SavingsGoal>> call, Response<List<SavingsGoal>> response) {
                if(response.code()==200){

                    // is response.body two dimensional?
                    //    java.lang.ClassCastException: java.util.ArrayList cannot be cast to com.example.agilesavev2.models.SavingsGoal.SavingsGoal
                    databaseHelper.truncateTable("savings");
                    response.body().forEach(b -> {
                        System.out.println(b);
                        HashMap<String, String> values2 = new HashMap<>();
                        values2.put("id", Integer.toString(b.getId()));
                        values2.put("userID", Integer.toString(b.getUserID()));
                        values2.put("goalName", b.getGoalname());
                        values2.put("targetDate", b.getTargetdate());
                        values2.put("startDate", b.getStartDate());
                        values2.put("reached", Boolean.toString(b.isReached()));
                        values2.put("progress", Double.toString(b.getProgress()));
                        values2.put("targetGoal", Double.toString(b.getTargetGoal()));
                        values2.put("achievedDate", b.getAchieveddate());

                        databaseHelper.populateTable("savings", values2);
                        // target goal was set to 100
                    });
                }
            }

            @Override
            public void onFailure(Call<List<SavingsGoal>> call, Throwable t) {

            }
        });




        Call<List<Budget>> call4 = repository.getBudgets(map);
        call4.enqueue(new Callback<List<Budget>>() {
            @Override
            public void onResponse(Call<List<Budget>> call, Response<List<Budget>> response) {
                if (response.code() == 200) {
                    databaseHelper.truncateTable("budgets");
                    response.body().forEach(b -> {
                        System.out.println("budgets");
                        HashMap<String, String> values = new HashMap<>();
                        values.put("id", Integer.toString(b.getId()));
                        values.put("userID", Integer.toString(b.getUserid()));
                        values.put("target", Double.toString(b.getTarget()));
                        values.put("name", b.getName());
                        values.put("passed", Boolean.toString(b.isPassed()));
                        values.put("date", b.getDate());
                        values.put("progress", Double.toString(b.getProgress()));
                        values.put("category", b.getCategory());
                        databaseHelper.populateTable("budgets", values);
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Budget>> call, Throwable t) {

            }
        });




        Call<UserPayInfo> call5 = repository.getPayInfo(map);
        call5.enqueue(new Callback<UserPayInfo>() {
            @Override
            public void onResponse(Call<UserPayInfo> call, Response<UserPayInfo> response) {
                databaseHelper.truncateTable("userPayInfo");
                if (response.code() == 200) {
                    UserPayInfo info = (UserPayInfo) response.body();
                    System.out.println(info);
                    HashMap<String, String> values = new HashMap<>();
                    values.put("id", Integer.toString(info.getId()));
                    values.put("userID", Integer.toString(info.getUserID()));
                    values.put("payDayType", info.getPayDayType());
                    values.put("nextPayDate", info.getNextPayDate());
                    values.put("pay", Double.toString(info.getPay()));

                    databaseHelper.populateTable("userPayInfo", values);
                }
            }

            @Override
            public void onFailure(Call<UserPayInfo> call, Throwable t) {
                System.out.println("Something went wrong " + t.getMessage());
            }
        });



        Call<List<Subscription>> call6 = repository.getSubscriptions(map);
        call6.enqueue(new Callback<List<Subscription>>() {
            @Override
            public void onResponse(Call<List<Subscription>> call, Response<List<Subscription>> response) {
                if (response.code() == 200) {
                    databaseHelper.truncateTable("subscriptions");
                    List<Subscription> subList = (List<Subscription>) response.body();
                    subList.forEach(s->{
                        HashMap<String, String> values = new HashMap<>();
                        values.put("id", Integer.toString(s.getId()));
                        values.put("userID", Integer.toString(s.getUserID()));
                        values.put("name", s.getName());
                        values.put("category", s.getCategory());
                        values.put("started",s.getStarted());
                        values.put("subscriptionValue", Double.toString(s.getSubscriptionValue()));

                        databaseHelper.populateTable("subscriptions", values);
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Subscription>> call, Throwable t) {
            }
        });




        Call<List<Category>> call7 = repository.getCategories(map);
        call7.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.code() == 200) {
                    databaseHelper.truncateTable("categories");
                    List<Category> subList = (List<Category>) response.body();
                    subList.forEach(s -> {
                        HashMap<String, String> values = new HashMap<>();
                        values.put("id", Integer.toString(s.getId()));
                        values.put("userID", Integer.toString(s.getUserID()));
                        values.put("categoryName", s.getCategoryName());

                        databaseHelper.populateTable("categories", values);
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
            }
        });


    }





    public void getAllUserTransactionsBetweenDates(HashMap<String , String> map, Presenter presenter){

        Call<List<TransactionSimple>> call = repository.getAllUserTransactionsBetweenDates(map);
        List<LineGraphData> list = new ArrayList<>();

        call.enqueue(new Callback<List<TransactionSimple>>() {
            @Override
            public void onResponse(Call<List<TransactionSimple>> call, Response<List<TransactionSimple>> response) {
                System.out.println(response.body());
                presenter.handleOnSuccess(1, response.body());
            }

            @Override
            public void onFailure(Call<List<TransactionSimple>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


        presenter.handleOnSuccess(2, list);

    }

    public void getAILineChartData(HashMap<String , String> map, Presenter presenter){

        Call<List<LineGraphData>> call = repository.getAIBudgetData(map);
        List<LineGraphData> list = new ArrayList<>();

        call.enqueue(new Callback<List<LineGraphData>>() {
            @Override
            public void onResponse(Call<List<LineGraphData>> call, Response<List<LineGraphData>> response) {
                System.out.println(response.body());
                presenter.handleOnSuccess(1, response.body());
            }

            @Override
            public void onFailure(Call<List<LineGraphData>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


        presenter.handleOnSuccess(2, list);

    }



    public void handleLogin(String username, String password, LoginPresenter presenter) {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        Call<User> call = repository.executeLogin(map);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    presenter.handleSuccess(response.body());
                } else {
                    presenter.handleFailure();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
                presenter.handleConnectionFailure();
            }
        });
    }

    public void getUserData(String id, Presenter presenter){
        HashMap<String, String> map = new HashMap<>();
        map.put("id",id);
        Call<User> call = repository.getUser(map);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code()==200){
                    presenter.handleOnSuccess(1,response.body());
                }else{
                    presenter.handleOnReject();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void completeSignUp(HashMap<String,String> map, Presenter presenter){
        Call<User> call = repository.updateUser(map);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code()==200){
                    presenter.handleOnSuccess(1,response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    public void updateAccountBalance(HashMap<String,String> map, Presenter presenter){
        databaseHelper.executeSQL("UPDATE account SET balance ="+map.get("balance")+" WHERE name = '"+map.get("name")+"' AND userID = "+map.get("userID"));
        presenter.handleOnSuccess(16,null);
        Call<Void> call = repository.updateBalance(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200){
                    presenter.handleOnSuccess(2,null);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });

    }






    public void handleRegister(String username, String email, String password, Presenter presenter) {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("email", email);
        map.put("password", password);
        Call<Void> call = repository.executeRegister(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println(response.code());
                if (response.code() == 200) {
                    presenter.handleOnSuccess(3,null);
                } else if(response.code()==404){
                    presenter.handleOnSuccess(4, null);
                }
                else if(response.code()==400){
                    presenter.handleOnReject();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }


    public void getUserTransactionData(HashMap<String,String> map, Presenter presenter) {
        try {
            int userID= Integer.parseInt(map.get("userID"));
            int accountID = Integer.parseInt(map.get("accountID"));

            Cursor res = databaseHelper.doQuery("SELECT * FROM transactions WHERE userID ="+userID+" AND accountID = "+accountID+" AND date  > DATETIME('now', '-45 day') ORDER BY date DESC;");
            List<Transaction> list = new ArrayList<>();
            while(res.moveToNext()){
                Transaction transaction = new Transaction(Integer.parseInt(res.getString(0)),
                        Integer.parseInt(res.getString(1)),
                        Integer.parseInt(res.getString(2)),
                        Double.parseDouble(res.getString(3)),
                        res.getString(4),res.getString(5),
                        res.getString(6),res.getString(7),
                        res.getString(8),res.getString(9) ,
                        Double.parseDouble(res.getString(10)));
                list.add(transaction);

            }
            presenter.handleOnSuccess(4, list);
        }catch (Exception e){
            System.out.println("Something went wrong "+e.getMessage());
        }




    }

    /* ===============send saving goal==================
           id, userID, goalName, targetDate, achievedDate, startDate,
           progress, reached;
       */
    public void addSavingGoal(HashMap<String,String> map){
        ContentValues contentValues = new ContentValues();
        for(Map.Entry<String,String> entry : map.entrySet()) contentValues.put(entry.getKey(),entry.getValue());
        Boolean test = databaseHelper.insertIntoTable("savings",contentValues);
        sendSavingGoal(map, presenter);
    }


    public void deleteSavingGoal(HashMap<String,String> map){
        // i guess you could add a callback function here that would refresh fragments and send out a request to retrieve savings data yet again
        databaseHelper.executeSQL("DELETE FROM savings WHERE id ="+ map.get("id"));
        presenter.handleOnSuccess(13,null);



    }
    public void deleteSavingOnBackEnd(HashMap<String,String> map){
        Call<Void> call = repository.deleteSaving(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("SOMETHING WENT WRONG "+t.getMessage());
            }
        });
    }
    public void updateSavingGoal(HashMap<String, String> map){

        // format date to standard SQL format
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy");
        SimpleDateFormat fromDate = new SimpleDateFormat("yyyy-MM-dd");
        try{
            date = format.parse(map.get("targetgoal"));
        }
        catch (Exception e){
            System.out.println("bad date format");
        }
        System.out.println(fromDate);

        // get updated reached
        boolean reached = Double.parseDouble(map.get("progress"))>=Double.parseDouble(map.get("targetgoal"));
        databaseHelper.executeSQL("UPDATE savings SET targetGoal="+map.get("targetgoal")+",progress=" + map.get("progress") + ",targetDate='"+date+"',reached='" + reached +"' WHERE id = "+map.get("id"));
        presenter.handleOnSuccess(14,null);
    }

    public void updateSavingGoalOnBackEnd(HashMap<String,String> map){
        boolean reached = Double.parseDouble(map.get("progress"))>=Double.parseDouble(map.get("targetgoal"));
        map.put("reached", Boolean.toString(reached));
        Call<Void> call = repository.updateSaving(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("SOMETHING WENT WRONG "+t.getMessage());
            }
        });
    }





    public void sendSavingGoal(HashMap<String , String> map, Presenter presenter){
        Call<Void> call = repository.sendSavingGoal(map);


        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }


    public void getSavings(String id, Presenter presenter){



        // trying to remap these values
        Cursor res = databaseHelper.doQuery("SELECT * FROM savings WHERE userid ="+ id);
        List<SavingsGoal> list = new ArrayList<>();
        while(res.moveToNext()){
            // id, userID, goalname, String targetdate, String startDate, boolean reached, double progress, double targetgoal, String achieveddat
            SavingsGoal saving = new SavingsGoal(
                    res.getInt(0),
                    res.getInt(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    Boolean.parseBoolean(res.getString(5)),
                    res.getDouble(6),
                    Double.parseDouble(res.getString(7)), // time to go to sleep
                    res.getString(8)
            );

            list.add(saving);
        }

        presenter.handleOnSuccess(2, list);

    }

//
    public void updateUser(HashMap<String,String> map, Presenter presenter){

        // okay, so we need to make another post route that updates the following fields in table named users
        //

        Call<User> call = repository.updateUser(map);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code()==200){
                    presenter.handleOnSuccess(2,response.body());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }

    public void deleteUser(HashMap<String,String> map, Presenter presenter){
        Call<Void> call = repository.deleteUser(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                presenter.handleOnSuccess(15,null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("SOMETHING WENT WRONG "+t.getMessage());
            }
        });

    }



    /* ===============Budgets==================
   */
    public void addBudget(HashMap<String , String> map, Presenter presenter){
        ContentValues contentValues = new ContentValues();
        for(Map.Entry<String,String> entry : map.entrySet()) contentValues.put(entry.getKey(),entry.getValue());
        Boolean test = databaseHelper.insertIntoTable("budgets",contentValues);
        presenter.handleOnSuccess(6, null);
        sendBudget(map);

    }
    public void sendBudget(HashMap<String , String> map){
        Call<Void> call = repository.sendBudget(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void getBudget(String id,Presenter presenter){
        Cursor res = databaseHelper.doQuery("SELECT * FROM budgets WHERE userid ="+ id);
        List<Budget> list = new ArrayList<>();
        while(res.moveToNext()){
            Budget budget = new Budget(Integer.parseInt(res.getString(0)),
                    Integer.parseInt(res.getString(1)),
                    Integer.parseInt(res.getString(2)),
                    res.getString(3),
                    Boolean.parseBoolean(res.getString(4)),
                    res.getString(5),
                    Double.parseDouble(res.getString(6)),
                    res.getString(7));
            list.add(budget);
        }
        presenter.handleOnSuccess(7, list);


    }

    public void deleteBudget(HashMap<String, String> map){
        databaseHelper.executeSQL("DELETE FROM budgets WHERE name = '"+ map.get("name")+"' AND userID = "+map.get("userID"));

        Call<Void> call = repository.deleteBudget(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Something went wrong "+t.getMessage());
            }
        });
    }

    public void updateBudget(HashMap<String, String> map){
        if(map.get("target")!=null){
            databaseHelper.executeSQL("UPDATE budgets SET progress ="+map.get("progress")+", target = "+map.get("target")+" WHERE name = '"+map.get("name")+"'");
        }else{
            databaseHelper.executeSQL("UPDATE budgets SET progress ="+map.get("progress")+" WHERE name = '"+map.get("name")+"'");
        }

        Call<Void> call = repository.updateBudget(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Something went wrong "+t.getMessage());
            }
        });

    }

    public boolean incrementBudget(HashMap<String, String> map){
        databaseHelper.executeSQL("UPDATE budgets SET progress = progress+"+map.get("progress")+" WHERE category = '"+map.get("category")+"' AND userID ="+map.get("userID"));
        Cursor res = databaseHelper.doQuery("SELECT * FROM budgets WHERE category = '"+map.get("category")+"' AND progress>=target AND userID ="+map.get("userID"));
        Call<Void> call = repository.updateBudget(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {


            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Something went wrong "+t.getMessage());
            }
        });

        return res.getCount()>0;

    }


    public void addAccount(HashMap<String, String> map, Presenter presenter) {
        ContentValues contentValues = new ContentValues();
        for(Map.Entry<String,String> entry : map.entrySet()) contentValues.put(entry.getKey(),entry.getValue());
        databaseHelper.insertIntoTable("account", contentValues);
        Call<Void> call = repository.addAccount(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                presenter.handleOnSuccess(8, null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Something went wrong "+t.getMessage());
            }
        });



    }


    public void deleteAccount(HashMap<String, String> map){
        databaseHelper.executeSQL("DELETE FROM account WHERE name = '"+map.get("name")+"' AND userID = "+map.get("userID"));
        Call<Void> call = repository.deleteAccount(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("SOMETHING WENT WRONG "+t.getMessage());
            }
        });
        presenter.handleOnSuccess(16,null);
    }

    public void getAccounts(HashMap<String,String> map , Presenter presenter){
        List<Account> list = new ArrayList<>();
        int userID = Integer.parseInt(map.get("userID"));
        Cursor res = databaseHelper.doQuery("SELECT * FROM account WHERE userID = "+userID);
        try {
            while(res.moveToNext()){
                Account account = new Account(Integer.parseInt(res.getString(0)),
                        Integer.parseInt(res.getString(1)),
                        res.getString(2),
                        Double.parseDouble(res.getString(3)),
                        res.getString(4),
                        res.getString(5),
                        false);

                list.add(account);
            }
        }catch (Exception e){

        }

        presenter.handleOnSuccess(9, list);


    }

    public Account getAccount(HashMap<String,String> map , Presenter presenter){
        int userID = Integer.parseInt(map.get("userID"));//storing userID in this variable
        int accountID = Integer.parseInt(map.get("accountID"));//storing accountID in this variable
        Cursor res = databaseHelper.doQuery("SELECT * FROM account WHERE userID = "+userID+" AND  id = "+accountID+";");

        //public Account(int id, int userid, String currency,double balance, String name, String type)
        res.moveToNext();

        return new Account(Integer.parseInt(res.getString(0)),
                Integer.parseInt(res.getString(1)),
                res.getString(2),
                Double.parseDouble(res.getString(3)),
                res.getString(4),
                res.getString(5),
                Boolean.parseBoolean(res.getString(6)));
    }

    public void addTransaction(HashMap<String,String> map , Presenter presenter){
        ContentValues contentValues = new ContentValues();

        Cursor res = databaseHelper.doQuery("SELECT * FROM account WHERE id = "+ map.get("accountID"));
        res.moveToNext();

        float balance = Float.parseFloat(res.getString(3));
        String currency = res.getString(2);
        map.put("balance",Double.toString(balance));
        map.put("currency",currency);
        contentValues.put("transactionValue", map.get("transaction"));
        contentValues.put("type", map.get("type"));
        contentValues.put("currency", currency);
        contentValues.put("date", map.get("date"));
        contentValues.put("balance", balance);
        contentValues.put("name", map.get("name"));
        contentValues.put("category", map.get("category"));
        contentValues.put("userID", map.get("userID"));
        contentValues.put("accountID", map.get("accountID"));
        databaseHelper.insertIntoTable("transactions", contentValues);

//        double val = map.get("type").equals("EXPENSE")? -Double.parseDouble(map.get("transaction")) : Double.parseDouble(map.get("transaction"));
//
//        databaseHelper.executeSQL("UPDATE account SET balance = balance + "+val+" WHERE id = "+ map.get("accountID"));


        map.put("name", map.get("category"));
        map.put("progress",map.get("transaction"));

        if(map.get("type").equals("EXPENSE") ){
            if(incrementBudget(map)){
                presenter.handleOnSuccess(4,null);
            }else{
                presenter.handleOnSuccess(5,null);
            }
        }



    }

    public void sendTransactionData(HashMap<String,String>map){
        Call<Void> call = repository.sendTransactionData(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("SOMETHING WENT WRONG "+t.getMessage());
            }
        });

    }



    public void setRememberLogin(String username, String password){
        databaseHelper.truncateTable("saved_login");
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password", password);
        databaseHelper.insertIntoTable("saved_login", contentValues);
    }

    public void dontRememberLogin(){
        databaseHelper.truncateTable("saved_login");
    }

    public HashMap<String,String> getRememberLogin(){
        HashMap<String, String> login_details = new HashMap<>();
        login_details.put("username","");
        login_details.put("password","");
        Cursor res = databaseHelper.doQuery("SELECT * FROM saved_login;");
        if(res.getCount()!=0){
            res.moveToNext();
            login_details.put("username",res.getString(1));
            login_details.put("password", res.getString(2));
        }
        return login_details;
    }



    public boolean addSubscription(HashMap<String,String> map){
        ContentValues contentValues = new ContentValues();
        for(Map.Entry<String,String> entry : map.entrySet())contentValues.put(entry.getKey(),entry.getValue());
        boolean res = databaseHelper.insertIntoTable("subscriptions",contentValues);
        presenter.handleOnSuccess(17,null);
        return res;
    }

    public void sendSubscription(HashMap<String, String> map){
        Call<Void> call = repository.sendSubscription(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Something went wrong "+t.getMessage());
            }
        });
    }

    public void getSubscriptions(HashMap<String,String> map){
        Cursor res = databaseHelper.doQuery("SELECT * FROM subscriptions WHERE userID ="+map.get("userID"));
        List<Subscription> subscriptions = new ArrayList<>();
        while(res.moveToNext()){
            //public Subscriptions(int id, int userID, String name, String category, String started, double subscriptionValue)
            Subscription subscription = new Subscription(Integer.parseInt(res.getString(0)),
                    Integer.parseInt(res.getString(1)),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    Double.parseDouble(res.getString(5)));
            subscriptions.add(subscription);
        }
        presenter.handleOnSuccess(5, subscriptions);
    }

    public void getSubscriptionAvg(){
        Call<List<SubscriptionAvg>> call = repository.getSubscriptionAvg();
        call.enqueue(new Callback<List<SubscriptionAvg>>() {
            @Override
            public void onResponse(Call<List<SubscriptionAvg>> call, Response<List<SubscriptionAvg>> response) {
                if(response.code()==200){
                    databaseHelper.truncateTable("subscription_avg");
                    List<SubscriptionAvg> list = (List<SubscriptionAvg>) response.body();
                    databaseHelper.truncateTable("subscription_avg");
                    list.forEach(c ->{
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("category",c.getCategory());
                        contentValues.put("avg",c.getAvg());
                        databaseHelper.insertIntoTable("subscription_avg", contentValues);
                    });


                }
            }

            @Override
            public void onFailure(Call<List<SubscriptionAvg>> call, Throwable t) {
                System.out.println("Something went wrong "+t.getMessage());
            }
        });
    }

    public HashMap<String, Double> getSubscriptionAvgValues(){
        HashMap<String, Double> map = new HashMap<>();
        Cursor res =databaseHelper.doQuery("SELECT * FROM subscription_avg;");
        while(res.moveToNext()){
            map.put(res.getString(1),res.getDouble(2));
        }
        return map;

    }

    public boolean isSubscriptionExist(HashMap<String,String> map){
        Cursor res = databaseHelper.doQuery("SELECT * FROM subscriptions WHERE userID = "+map.get("userID")+" AND name = '"+map.get("name")+"' AND category = '"+map.get("category")+"';");
        return res.getCount()>0;
    }

    public void updateSubscription(HashMap<String, String> map){
        databaseHelper.executeSQL("UPDATE subscriptions SET name = '"+map.get("name")+"', subscriptionValue = '"+map.get("subscriptionValue")+"', category= '"+map.get("category")+"'  WHERE name = '"+map.get("name")+"' AND userID = "+map.get("userID"));
        presenter.handleOnSuccess(11,null);
        Call<Void> call = repository.updateSubscripton(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Something went wrong "+t.getMessage());
            }
        });

    }
    public void deleteSubscription(HashMap<String, String > map){
        databaseHelper.executeSQL("DELETE FROM subscriptions WHERE name = '"+ map.get("name")+"' AND userID = "+map.get("userID"));
        presenter.handleOnSuccess(12,null);
        Call<Void> call = repository.deleteSubscription(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Something went wrong "+t.getMessage());
            }
        });
    }

    public void getCurrencyConversionRates(HashMap<String,String> map){
        Call<List<Currency>> call = repository.getCurrencyConversionRates(map);
        call.enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                databaseHelper.truncateTable("currency_conversion_chart");
                List<Currency> currencyList = (List<Currency>)response.body();
                currencyList.forEach(c ->{
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("CURRENCY",c.getCurrency());
                    contentValues.put("CONVERSION_VALUE",c.getConversionRate());
                    databaseHelper.insertIntoTable("currency_conversion_chart", contentValues);
                });
                presenter.handleOnSuccess(6, null);
            }

            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {

            }
        });
    }

    public static double getCurrencyConversionRate(String currency){
        Cursor res =databaseHelper.doQuery("SELECT * FROM currency_conversion_chart WHERE CURRENCY = '"+currency+"'");
        res.moveToNext();
        return Double.parseDouble(res.getString(2));
    }


    public void addPayInfo(HashMap<String, String> map){
        ContentValues contentValues = new ContentValues();
        for(Map.Entry<String,String> entry : map.entrySet()) contentValues.put(entry.getKey(),entry.getValue());
        databaseHelper.executeSQL("DELETE FROM userPayInfo WHERE userID = "+map.get("userID"));
        databaseHelper.insertIntoTable("userPayInfo", contentValues);
        sendPayInfo(map);
    }

    public void sendPayInfo(HashMap<String, String> map){
        Call<Void> call = repository.addPayInfo(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                presenter.handleOnSuccess(7, null);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Something went wrong "+t.getMessage());
            }
        });
    }



    public void getPayInfo(String id){
        Cursor res =databaseHelper.doQuery("SELECT * FROM  userPayInfo WHERE userID = "+id);
        res.moveToNext();
        try {
            UserPayInfo userPayInfo = new UserPayInfo(res.getInt(0),
                    res.getInt(1),
                    res.getString(3),
                    res.getString(2),
                    res.getDouble(4));
            presenter.handleOnSuccess(8,userPayInfo);

        }catch (Exception e){
            presenter.handleOnReject();
        }


    }

    public void addCategory(HashMap<String, String> map){
        ContentValues contentValues = new ContentValues();
        for(Map.Entry<String,String> entry : map.entrySet()) contentValues.put(entry.getKey(),entry.getValue());
        databaseHelper.insertIntoTable("categories", contentValues);
        sendCategory(map);
        presenter.handleOnSuccess(16,null);
    }

    public void sendCategory(HashMap<String, String> map){
        Call<Void> call = repository.addCategory(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Something went wrong "+t.getMessage());
            }
        });
    }

    public void getCategories(String id){
        Cursor res =databaseHelper.doQuery("SELECT * FROM  categories WHERE userID = "+id);
        try {
            ArrayList<Category> categories = new ArrayList<>();
            while(res.moveToNext()){
                Category category = new Category(res.getInt(0), res.getInt(1), res.getString(2));
                categories.add(category);
            }


            presenter.handleOnSuccess(8,categories);

        }catch (Exception e){
            presenter.handleOnReject();
        }


    }



    public void updatePayDate(HashMap<String, String> map){
        databaseHelper.executeSQL("UPDATE userPayInfo SET nextPayDate ='"+map.get("nextPayDate")+"' WHERE id = "+map.get("userID"));
        Call<Void> call = repository.updatePayDate(map);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("SOMETHING WENT WRONG "+t.getMessage());
            }
        });

    }




}