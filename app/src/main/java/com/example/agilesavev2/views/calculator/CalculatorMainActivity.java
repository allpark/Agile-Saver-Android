package com.example.agilesavev2.views.calculator;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.agilesavev2.R;
import com.example.agilesavev2.databinding.ActivityCalculatorMainBinding;
import com.example.agilesavev2.utils.DatePicker;
import com.example.agilesavev2.utils.TimePicker;
import com.example.agilesavev2.views.calculator.assets.AccountListViewActivity;
import com.example.agilesavev2.views.category.CategoryActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class CalculatorMainActivity extends AppCompatActivity implements CalculatorContract.CalculatorView , TimePickerDialog.OnTimeSetListener {
    private int modes=0; //0 = income   1 = expense  3=transfer
    private Button income, dateButton, expense,  timePicker;
    private TextView calculator_screen, mode, selectedAccount, currency_type;
    private String category;
    private DatePickerDialog datePickerDialog;
    private CalculatorPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ActivityCalculatorMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_calculator_main);
        Window g=getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        getSupportActionBar().hide();
        presenter = new CalculatorPresenter(this);
        binding.setPresenter(presenter);
        mode=findViewById(R.id.mode);
        income=findViewById(R.id.incomeBtn);
        expense=findViewById(R.id.expenseBtn);
        calculator_screen=findViewById(R.id.textView10);
        selectedAccount = findViewById(R.id.selectedAccount);
        selectedAccount.setText(getIntent().getStringExtra("SELECTED"));
        currency_type = findViewById(R.id.currency_type);
        currency_type.setText(presenter.getCurrencyType());

        incomeMode();
        dateButton= findViewById(R.id.date_picker);
        DatePicker datePicker = new DatePicker(dateButton, getIntent(), this);

        timePicker = findViewById(R.id.time_picker);
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        timePicker.setText((hour <10? "0"+hour : hour)+":"+(minute<10?"0"+minute : minute));



    }





    @Override
    public void updateScreen(String num) {
        if(num.equals("ERROR") || num.equals("Infinity")){
            calculator_screen.setText("ERROR");
            return;
        }
        double value = Double.parseDouble(num);
        if(value==(int)value){
            calculator_screen.setText(Integer.toString((int)value));
            return;
        }
        String newVal=String.format("%.2f", Double.parseDouble(num));
        calculator_screen.setText(newVal);
    }

    @Override
    public void incomeMode() {
        income.setBackgroundResource(R.drawable.button_background2_selected);
        expense.setBackgroundResource(R.drawable.button_background2);
        modes=0;
        mode.setText("+");
        income.setClickable(false);
        expense.setClickable(true);
    }

    @Override
    public void expenseMode() {
        expense.setBackgroundResource(R.drawable.button_background2_selected);
        income.setBackgroundResource(R.drawable.button_background2);
        modes=1;
        mode.setText("-");
        expense.setClickable(false);
        income.setClickable(true);
    }

    @Override
    public void showCategory() {
        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra("ID", getIntent().getStringExtra("ID"));
        startActivityForResult(intent,1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                TextView category = findViewById(R.id.selectedCategory);
                this.category=data.getStringExtra("CATEGORY");
                System.out.println(this.category);
                getIntent().putExtra("CATEGORY",this.category);
                category.setText(this.category);

            }
        }else if(requestCode==2){
            if(resultCode==RESULT_OK){
                TextView account = findViewById(R.id.selectedAccount);
                account.setText(data.getStringExtra("SELECTED_ACCOUNT"));
                currency_type.setText(presenter.getCurrencyType());
            }
        }
    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onReject() {
        if(getSelectedAccountID()==null){
            Toast.makeText(this, "Select an account", Toast.LENGTH_SHORT).show();
        }
        else if(getIntent().getStringExtra("CATEGORY")==null){
            Toast.makeText(this, "Select a category", Toast.LENGTH_SHORT).show();
        }

        else if(calculator_screen.getText().toString().equals("0")){
            Toast.makeText(this, "Value cannot be 0", Toast.LENGTH_SHORT).show();
        }
        else if(getName().trim().equals("")){
            Toast.makeText(this, "Give it a name", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void save() {
        String expense= calculator_screen.getText().toString();
        String mode = this.mode.getText().toString();
        double val = Double.parseDouble(mode+expense);
        Toast.makeText(this, "Transaction added", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK,getIntent());
        finish();
    }

    @Override
    public void cancel() {
        finish();
    }

    @Override
    public void getMode() {

    }

    @Override
    public void addDecimal() {
        String str = calculator_screen.getText().toString();
        if(!str.contains(".")){
            calculator_screen.setText(str+".");
        }

    }

    @Override
    public Intent getTransactionData() {
        String transaction=calculator_screen.getText().toString();
        getIntent().putExtra("TRANSACTION",transaction);
        return getIntent();
    }

    @Override
    public String transactionType() {
        if(modes==0) return "INCOME";
        if(modes==1) return "EXPENSE";
        if(modes==2) return "TRANSFER";
        return null;
    }

    @Override
    public String getSelectedAccountName() {
        return selectedAccount.getText().toString();
    }

    @Override
    public Integer getSelectedAccountID() {
        HashMap<String, Integer> map = (HashMap<String, Integer>) getIntent().getSerializableExtra("ACCOUNTS");
        return map.get(getSelectedAccountName());
    }

    @Override
    public void showAccountList() {
        getIntent().setClass(this, AccountListViewActivity.class);
        startActivityForResult(getIntent(),2);
    }

    @Override
    public void showNotification() {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "budgets")
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Budget alert!")
                .setContentText("You have ran out of budget for "+getIntent().getStringExtra("CATEGORY"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(100,builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Agile Saver notifcation";
            String description = "Channel for agile saver notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("budgets", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showTimePicker() {
        DialogFragment timepicker = new TimePicker();
        timepicker.show(getSupportFragmentManager(), "Time Picker");
    }

    @Override
    public String getDateTime() {
        String date = dateButton.getText().toString();
        String time = timePicker.getText().toString();

        //formatting process
        String[] dateArr = date.split(" ");
        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP","OCT", "NOV", "DEC"};
        String month_number = Integer.toString(Arrays.asList(months).indexOf(dateArr[0])+1);

        String year = dateArr[2];
        String day = dateArr[1].length()<2? "0"+dateArr[1] : dateArr[1];
        String month = month_number.length()<2 ? "0"+month_number : month_number;

        return year+"-"+month+"-"+day+" "+time+":00 +0000";
    }

    @Override
    public String getName() {
        return ((TextView)findViewById(R.id.editText)).getText().toString();
    }

    @Override
    public String getUserId() {
        return getIntent().getStringExtra("ID");
    }

    @Override
    public String getInputValue() {
        return calculator_screen.getText().toString().trim();
    }

    @Override
    public boolean formValidate() {
        if(getSelectedAccountID()==null){
            Toast.makeText(this, "Select an account", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(getIntent().getStringExtra("CATEGORY")==null){
            Toast.makeText(this, "Select a category", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(calculator_screen.getText().toString().equals("0")){
            Toast.makeText(this, "Value cannot be 0", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(getName().trim().equals("")){
            Toast.makeText(this, "Give it a name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        timePicker.setText((hourOfDay<10? "0"+hourOfDay : hourOfDay )+":"+(minute<10?"0"+minute : minute));
    }
}