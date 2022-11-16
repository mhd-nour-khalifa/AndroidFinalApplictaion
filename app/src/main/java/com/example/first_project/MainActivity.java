package com.example.first_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    //Implementing Calender function
    final Calendar myCalendar = Calendar.getInstance();

    EditText dateField, studentIdField, studentNameField, studentLastNameField, studentGpaField, additionInfoField;
    Spinner departmentSpinner, facultySpinner, birthPlaceSpinner;
    TextView submittedTxt, genderTxt, facultyTxt, departmentTxt, scholarshipTxt,birthPlaceTxt;;
    Button exitBtn, resetBtn, submitBtn;
    RadioGroup genderRadioGroup, scholarshipRadioGroup;
    RadioButton maleBtn, femaleBtn, fullBtn, halfBtn, noneBtn;
    CheckBox additionalReqCheckBox;

    int cityPlateNum;
    String[] cityArray = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityArray = getResources().getStringArray(R.array.tr_city);

        studentIdField = findViewById(R.id.studentId);
        studentNameField = findViewById(R.id.studentName);
        studentLastNameField = findViewById(R.id.studentLastName);
        studentGpaField = findViewById(R.id.studentGPA);
        additionInfoField = findViewById(R.id.additionalInfo);
        birthPlaceTxt = findViewById(R.id.birthPlaceHint);


        submittedTxt = findViewById(R.id.submission);
        departmentTxt = findViewById(R.id.departmentHint);
        genderTxt = findViewById(R.id.genderHint);
        facultyTxt = findViewById(R.id.facultyHint);
        scholarshipTxt = findViewById(R.id.scholarshipHint);

        genderRadioGroup = findViewById(R.id.genderRadioGrp);
        scholarshipRadioGroup = findViewById(R.id.scholarshipRadioGrp);

        maleBtn = findViewById(R.id.maleRadioBtn);
        femaleBtn = findViewById(R.id.femaleRadioBtn);
        fullBtn = findViewById(R.id.scholarshipFull);
        halfBtn = findViewById(R.id.scholarshipHalf);
        noneBtn = findViewById(R.id.scholarshipNone);

        additionalReqCheckBox = findViewById(R.id.checkBox);



        // CheckBox for visbility and Disbility
        additionalReqCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    additionInfoField.setVisibility(View.VISIBLE);
                } else {
                    additionInfoField.setVisibility(View.GONE);
                }
            }
        });
        ///////////////////////////////////////////////End OF CheckBox Visibility///////////////////////////////////////////////////


        // Initialize spinner and getting adaptor array resource from string xml
        facultySpinner = findViewById(R.id.spinnerFaculty);
        departmentSpinner = findViewById(R.id.spinnerDepartment);

        String [] faculty = {"","Business","Engineering and Natural Sciences","Architecture"};
        String [] departmentOfBusiness = {"","Business Administration", "Management Information Systems","International Finance","International Trade and Business"};
        String [] departmentOfEngineering = {"","Civil Engineering","Computer Engineering" ,"Computer Science","Electrical and Electronics"};
        String [] departmentOfArchitecture = {"","Architecture","Industrial Design" ,"Interior Design"};

        ArrayAdapter<String> facultyAdapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_dropdown_item, faculty);

        ArrayAdapter<String> departmentOfBusinessAdapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_dropdown_item,
                departmentOfBusiness);
        ArrayAdapter<String> departmentOfEngineeringAdapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_dropdown_item,
                departmentOfEngineering);
        ArrayAdapter<String> departmentOfArchitectureAdapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_dropdown_item,
                departmentOfArchitecture);


        facultySpinner.setAdapter(facultyAdapter);
        // Spinner Toast This callback is invoked only when the newly selected position is different from the previously selected position or if there was no selected item.
        facultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (position == 1 ) {
                    departmentSpinner.setAdapter(departmentOfBusinessAdapter);

                } if(position == 2) {
                    departmentSpinner.setAdapter(departmentOfEngineeringAdapter);
                }if (position ==3){
                    departmentSpinner.setAdapter(departmentOfArchitectureAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ///////////////////////////////////////////////End OF Spinner Department and Facilty///////////////////////////////////////////////////

        //limits decimal palaces for Gpa
         InputFilter filter = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint=1;
            final int maxDigitsAfterDecimalPoint=2;
            final double min=0.0;
            final double max=4.0;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {

                Double input = 0.0;
                try {
                    input = Double.parseDouble(dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length()));
                } catch (NumberFormatException nfe) { }

                if (!isInRange(min, max, input))
                    return "";

                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source.subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([1-9]{1})([0-9]{0,"+(maxDigitsBeforeDecimalPoint-1)+"})?)?(\\.[0-9]{0,"+maxDigitsAfterDecimalPoint+"})?"
                )) {
                    if(source.length() == 0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;
            }
        };
        ///////////////////////////////////////////////End OF Gpa Decimal///////////////////////////////////////////////////

        //Initialize birthplace and getting adaptor array resource from string xml
        ArrayAdapter birthPlaceAdapter = ArrayAdapter.createFromResource(this,R.array.tr_city_number, android.R.layout.simple_spinner_dropdown_item);

        birthPlaceSpinner = findViewById(R.id.spinnerBirthPlace);

        birthPlaceSpinner.setAdapter(birthPlaceAdapter);
        birthPlaceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                cityPlateNum = (int) birthPlaceSpinner.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ///////////////////////////////////////////////End OF Birth Place ///////////////////////////////////////////////////


        /*int city = (int) birthPlaceSpinner.getSelectedItemId();
        String[] city_id = getResources().getStringArray(R.array.tr_city);

        String plaka = city_id[city];

        System.out.println("ali "+plaka);*/

        /*birthPlaceSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });*/

        // Implementing myCalender variable into DataPickerDialog function
        dateField = (EditText) findViewById(R.id.studentBirthDate);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        //Action performance when edit text clicked
        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog =  new DatePickerDialog(MainActivity.this,date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
            }
        });
        ///////////////////////////////////////////////End OF DataPicker///////////////////////////////////////////////////


        //Exit Button
        exitBtn = findViewById(R.id.btnExit);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(1);
            }
        });
        ///////////////////////////////////////////////End OF Exit Buttons///////////////////////////////////////////////////


        //Reset Button
        resetBtn = findViewById(R.id.btnReset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentIdField.setText("");
                studentNameField.setText("");
                studentLastNameField.setText("");
                dateField.setText("");
                studentGpaField.setText("");
                submittedTxt.setText("");
                additionInfoField.setText("");

                facultySpinner.setSelection(0);
                departmentSpinner.setSelection(0);
                birthPlaceSpinner.setSelection(0);

                additionalReqCheckBox.setChecked(false);

                additionInfoField.setVisibility(View.GONE);
                submittedTxt.setVisibility(View.GONE);

                scholarshipRadioGroup.clearCheck();
                genderRadioGroup.clearCheck();
            }
        });
        ///////////////////////////////////////////////End OF Reset Buttons///////////////////////////////////////////////////



        submitBtn = findViewById(R.id.btnSubmit);
             submitBtn.setOnClickListener(new View.OnClickListener() {
            // Submit buttons initializing all the things to get the output when it clicked./////////////////////////
            @Override
            public void onClick(View view) {
                String id = studentIdField.getText().toString();
                String name = studentNameField.getText().toString();
                String surname = studentLastNameField.getText().toString();
                String birthDate = dateField.getText().toString();
                String gpa = studentGpaField.getText().toString();
                String department = departmentSpinner.getSelectedItem().toString();
                String faculty = facultySpinner.getSelectedItem().toString();
                String additionalInfo = additionInfoField.getText().toString();

                String cityFromPlateNum = cityArray[cityPlateNum];

                if (!(id.length() > 0) || !(name.length() > 0) || !(surname.length() > 0) || !(birthDate.length() > 0) || !(gpa.length() > 0)) {
                    Toast toast= Toast.makeText(MainActivity.this,"Please fill the required places..", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                if (id.length() < 11) {
                    Toast toast= Toast.makeText(MainActivity.this,"Student Id must be 11 digits..", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                if (!(maleBtn.isChecked() || femaleBtn.isChecked())) {
                    Toast toast= Toast.makeText(MainActivity.this,"Please choose your gender..", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                if (department == "") {
                    Toast toast= Toast.makeText(MainActivity.this,"Please choose a department..", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                if (!(fullBtn.isChecked() || halfBtn.isChecked() || noneBtn.isChecked())) {
                    Toast toast= Toast.makeText(MainActivity.this,"Please choose your scholarship..", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                if (additionalReqCheckBox.isChecked()) {
                    if (!(additionalInfo.length() > 0)) {
                        Toast toast= Toast.makeText(MainActivity.this,"Please fill the additional info..", Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                }

                submittedTxt.setVisibility(View.VISIBLE);

                SpannableStringBuilder idBuilder = updateString(studentIdField.getHint().toString() + ": ", id);
                SpannableStringBuilder nameBuilder = updateString(studentNameField.getHint().toString() + ": ", name);
                SpannableStringBuilder surnameBuilder = updateString(studentLastNameField.getHint().toString() + ": ", surname);
                SpannableStringBuilder birthdateBuilder = updateString(dateField.getHint().toString() + ": ", birthDate);
                SpannableStringBuilder birthPlaceBuilder = updateString(birthPlaceTxt.getText().toString() + ": ", cityFromPlateNum);
                SpannableStringBuilder genderBuilder = updateString(genderTxt.getText().toString() + ": " , getGender());
                SpannableStringBuilder facultyBuilder = updateString(facultyTxt.getText().toString() + ": " , faculty);
                SpannableStringBuilder departmentBuilder = updateString(departmentTxt.getText().toString() + ": " , department);
                SpannableStringBuilder gpaBuilder = updateString(studentGpaField.getHint().toString() + ": " , gpa);
                SpannableStringBuilder scholarBuilder = updateString(scholarshipTxt.getText().toString() + ": " , getScholarship());
                SpannableStringBuilder additionalInfoBuilder = updateString(additionInfoField.getHint().toString() + ": " , additionalInfo);

                idBuilder.append("\n");
                nameBuilder.append("\n");
                surnameBuilder.append("\n");
                birthdateBuilder.append("\n");
                birthPlaceBuilder.append("\n");
                genderBuilder.append("\n");
                facultyBuilder.append("\n");
                departmentBuilder.append("\n");
                gpaBuilder.append("\n");
                scholarBuilder.append("\n");
                additionalInfoBuilder.append("\n");

                SpannableStringBuilder wholeWord = new SpannableStringBuilder();
                wholeWord.append(idBuilder);
                wholeWord.append(nameBuilder);
                wholeWord.append(surnameBuilder);
                wholeWord.append(birthdateBuilder);
                wholeWord.append(birthPlaceBuilder);
                wholeWord.append(genderBuilder);
                wholeWord.append(facultyBuilder);
                wholeWord.append(departmentBuilder);
                wholeWord.append(gpaBuilder);
                wholeWord.append(scholarBuilder);
                wholeWord.append(additionalInfoBuilder);

                submittedTxt.setText(wholeWord);
            }
        });
    }
    ///////////////////////////////////////////////End OF Submit Buttons///////////////////////////////////////////////////


    //gets the selected gender text
    private String getGender() {
        String gender = "";

        if (maleBtn.isChecked()){
            gender = maleBtn.getText().toString();
        }
        if (femaleBtn.isChecked()) {
            gender = femaleBtn.getText().toString();
        }

        return gender;
    }
    ///////////////////////////////////////////////End OF Gender Text//////////////////////////////////////////////////////



    //gets the selected scholarship text
    private String getScholarship() {
        String scholarship = "";

        if (fullBtn.isChecked()){
            scholarship = fullBtn.getText().toString();
        }
        if (halfBtn.isChecked()) {
            scholarship = halfBtn.getText().toString();
        }
        if (noneBtn.isChecked()) {
            scholarship = noneBtn.getText().toString();
        }

        return scholarship;
    }
    ///////////////////////////////////////////////End OF ScholarShip///////////////////////////////////////////////////


    //creates half bold text half colored text for the output half red and half black
    private SpannableStringBuilder updateString(String boldTxt, String normalTxt) {
        SpannableStringBuilder boldStr = new SpannableStringBuilder(boldTxt);
        boldStr.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, boldTxt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder colorStr = new SpannableStringBuilder(normalTxt);
        colorStr.setSpan(new ForegroundColorSpan(Color.RED), 0, normalTxt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder str = boldStr.append(colorStr);
        return str;
    }
    ///////////////////////////////////////////////End OF Text Bold///////////////////////////////////////////////////


    //Updating Calender Function
    private void updateLabel(){
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        dateField.setText(dateFormat.format(myCalendar.getTime()));
    }
    ///////////////////////////////////////////////End OFCalender///////////////////////////////////////////////////
    
     private boolean isInRange(double a, double b, double c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
