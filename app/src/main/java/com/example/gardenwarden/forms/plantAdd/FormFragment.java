package com.example.gardenwarden.forms.plantAdd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;

import com.example.gardenwarden.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int waterId;

    ArrayList<RadioButton> radioButtons;
    OnFormFragmentInteractionListener callback;


    public void setClickedListener(OnFormFragmentInteractionListener callback) {
        this.callback = callback;
    }

    public FormFragment(int waterId2) {
        waterId=waterId2;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FormFragment newInstance(String param1, String param2) {
        FormFragment fragment = new FormFragment(0);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_form, container, false);
        Button button = root.findViewById(R.id.form_date_picker);
        radioButtons = new ArrayList<>();
        radioButtons.add((RadioButton) root.findViewById(R.id.form_radio_button1));
        radioButtons.add((RadioButton) root.findViewById(R.id.form_radio_button2));
        radioButtons.add((RadioButton) root.findViewById(R.id.form_radio_button3));
        radioButtons.add((RadioButton) root.findViewById(R.id.form_radio_button4));

        radioButtons.get(waterId).setChecked(true);

        final NumberPicker pickerMinute = root.findViewById(R.id.form_time_picker_minutes);
        pickerMinute.setMinValue(0);
        pickerMinute.setMaxValue(7);
        pickerMinute.setDisplayedValues(new String[]{"00", "30", "00","30","00", "30", "00","30"});
        pickerMinute.setOnLongPressUpdateInterval(100);

        final NumberPicker pickerHour= root.findViewById(R.id.form_time_picker_hours);

        final EditText editText = root.findViewById(R.id.form_edit_text);

        pickerHour.setMinValue(0);
        pickerHour.setMaxValue(23);

        pickerHour.setDisplayedValues(new String[]{"01", "02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"});

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("eoo", String.valueOf(pickerMinute.getValue()));
                int waterLevel = 0;

                for(int i=0; i<radioButtons.size();i++){
                    if(radioButtons.get(i).isChecked())
                        waterLevel=i;
                }
                FormPlant plant = new FormPlant(waterLevel,editText.getText().toString(),pickerMinute.getValue(),pickerHour.getValue()+1);
                callback.onAddButtonClick(plant);
            }
        });


        return root;
    }

    public interface OnFormFragmentInteractionListener {
        void onAddButtonClick(FormPlant plant);
    }
}