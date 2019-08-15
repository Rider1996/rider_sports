package sportapp.rider.com.myapplication.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sportapp.rider.com.myapplication.MainActivity;
import sportapp.rider.com.myapplication.R;

public class EmptyFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    public void hideBackButton() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hideBackButton();
        }
    }

    public void showBackButton() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showBackButton();
        }
    }

    public boolean isNetworkAvailable() {
        boolean isAvailable = false;
        if (getActivity() instanceof MainActivity) {
            isAvailable = ((MainActivity) getActivity()).isNetworkAvailable();
        }
        return isAvailable;
    }

    public void changeTitle(String title) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).changeTitle(title);
        }
        
    }

    public void actionBarColor() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).actionBarColor();
        }
    }

    public static void showNoInternetConnection(Activity context, String title, String message
            , DialogInterface.OnClickListener dialogInterface) {
        if (context == null) return;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setMessage(message);
        if (title != null) alertDialog.setTitle(title);
        if (dialogInterface != null) alertDialog.setPositiveButton("Dismiss", dialogInterface);
        else alertDialog.setPositiveButton("Dismiss", null);
        alertDialog.create().show();
    }

}

