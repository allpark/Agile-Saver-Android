package com.example.agilesavev2.fragments.accounts;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.agilesavev2.R;
import com.example.agilesavev2.fragments.accounts.account.AccountFragment;
import com.example.agilesavev2.models.users.Account;
import com.example.agilesavev2.views.accounts.AccountTypeActivity;
import com.example.agilesavev2.views.home.HomeActivity;
import com.example.agilesavev2.views.settings.manage_accounts.ManageAccountsActivity;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BalanceListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BalanceListFragment extends Fragment implements BalanceListContract.BalanceListView {
    private BalanceListPresenter presenter;
    private ArrayList<AccountFragment> accountFragments;
    private  FlexboxLayout flexboxLayout;
    public BalanceListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_balance_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter= new BalanceListPresenter(getActivity().getIntent(),this, getContext());
        presenter.handleAdjustBalance(view.findViewById(R.id.adjust_balance));
        presenter.handleAddAccount(view.findViewById(R.id.add_account));
        presenter.handleSettings(view.findViewById(R.id.settings));
        presenter.getAccounts();

        


    }

    @Override
    public void showAdjustBalance() {
//        ((HomeActivity)getActivity()).displayAdjustBalance();
        Account account = presenter.getSelectedAccount();
        UpdateAccountBalanceDialog updateBalance = new UpdateAccountBalanceDialog(account);
        updateBalance.show(getChildFragmentManager(),null);
    }

    @Override
    public void showSettings() {
//        Intent intent = new Intent(getActivity(),ManageAccountsActivity.class);
//        intent.putExtra("ID",getActivity().getIntent().getStringExtra("ID"));
//        startActivity(intent);
        Toast.makeText(getContext(), "Feature not available :(", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayAccounts(List<Account> accounts) {
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        flexboxLayout= getView().findViewById(R.id.flex_container);
        ArrayList<AccountFragment> fragmentArrayList = new ArrayList<>();
        HashMap<String,Integer> accountMap = new HashMap<>();
        accountFragments = new ArrayList<>();
        if(getActivity().getIntent().getStringExtra("SELECTED")==null){
            if(accounts != null && accounts.size()>0) {
                getActivity().getIntent().putExtra("SELECTED", accounts.get(0).getName());
                getActivity().getIntent().putExtra("SELECTED_ACCOUNT_ID", Integer.toString(accounts.get(0).getID()));
            }
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                for(Account account : accounts){
                    accountMap.put(account.getName(),account.getID());
                    AccountFragment accountFragment = new AccountFragment();
                    accountFragment.setAccountName(account.getName());
                    accountFragment.setBalance(Double.toString(account.getBalance()));
                    accountFragment.setCurrencyType(account.getCurrency());
                    accountFragment.setParentView(BalanceListFragment.this);
                    accountFragments.add(accountFragment);
                    fragmentArrayList.add(accountFragment);
                    fragmentTransaction.add(flexboxLayout.getId(),accountFragment);

                }
                fragmentTransaction.commitNow();
                getActivity().getIntent().putExtra("ACCOUNTS", accountMap);
                refreshFragment();

                for(int i=0 ; i<flexboxLayout.getChildCount();i++){
                    View account = flexboxLayout.getChildAt(i);
                    FlexboxLayout.LayoutParams  flexboxLp =  (FlexboxLayout.LayoutParams) account.getLayoutParams();
                    flexboxLp.setFlexGrow(1);
                    flexboxLp.setMinWidth(450);
                    flexboxLp.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    flexboxLp.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);


                }


            }
        }, 100);








    }

    private FragmentManager.OnBackStackChangedListener backStackListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {

        }
    };

    @Override
    public void addAccount() {
        Intent intent = new Intent(getActivity(), AccountTypeActivity.class);
        intent.putExtra("ID",getActivity().getIntent().getStringExtra("ID"));
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            ((com.example.agilesavev2.assets.View)getActivity()).restartUI();
        }

    }

    @Override
    public void refreshFragment() {
        for(AccountFragment account : accountFragments){
            account.setSelected();
        }
        ((com.example.agilesavev2.assets.View)getActivity()).refreshFragments();
    }
}