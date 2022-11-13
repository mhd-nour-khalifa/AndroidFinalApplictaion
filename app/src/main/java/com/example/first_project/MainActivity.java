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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //Implementing Calender function
    final Calendar myCalendar = Calendar.getInstance();

    EditText dateField, studentIdField, studentNameField, studentLastNameField, studentGpaField, additionInfoField;
    Spinner departmentSpinner, facultySpinner, birthPlaceSpinner;
    TextView submittedTxt, genderTxt, facultyTxt, departmentTxt, scholarshipTxt;
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

        //limits decimal palaces
        InputFilter filter = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint=1;
            final int maxDigitsAfterDecimalPoint=2;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {

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
        studentGpaField.setFilters(new InputFilter[] { filter });

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

        // Initialize spinner and getting adaptor array resource from string xml
        facultySpinner = findViewById(R.id.spinnerFaculty);
        departmentSpinner = findViewById(R.id.spinnerDepartment);
        birthPlaceSpinner = findViewById(R.id.spinnerBirthPlace);

        final String [] faculty = {"Business","Engineering and Natural Sciences"};
        final String [] departmentOfBusiness = {"","Business Administration", "Management Information Systems","International Finance","International Trade and Business"};
        final String [] departmentOfEngineering = {"","Civil Engineering","Computer Engineering" ,"Computer Science","Electrical and Electronics"};

        ArrayAdapter<String> facultyAdapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_dropdown_item, faculty);
        final ArrayAdapter<String> departmentOfBusinessAdapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_dropdown_item, departmentOfBusiness);
        final ArrayAdapter<String> departmentOfEngineeringAdapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_dropdown_item, departmentOfEngineering);
        final ArrayAdapter birthPlaceAdapter = ArrayAdapter.createFromResource(this,R.array.tr_city_number, android.R.layout.simple_spinner_dropdown_item);

        facultySpinner.setAdapter(facultyAdapter);
        // Spinner Toast This callback is invoked only when the newly selected position is different from the previously selected position or if there was no selected item.
        facultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (position == 0 ) {
                    departmentSpinner.setAdapter(departmentOfBusinessAdapter);

                } else {
                    departmentSpinner.setAdapter(departmentOfEngineeringAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

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
                DatePickerDialog dialog =  new DatePickerDialog(MainActivity.this,date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.show();
            }
        });

        exitBtn = findViewById(R.id.btnExit);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(1);
            }
        });

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
                birthPlaceSpinner.setSelection(0);

                additionalReqCheckBox.setChecked(false);

                additionInfoField.setVisibility(View.GONE);
                submittedTxt.setVisibility(View.GONE);

                scholarshipRadioGroup.clearCheck();
                genderRadioGroup.clearCheck();
            }
        });

        submitBtn = findViewById(R.id.btnSubmit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
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
                SpannableStringBuilder birthPlaceBuilder = updateString(studentNameField.getHint().toString() + ": ", cityFromPlateNum);
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

    //creates half bold text half colored text
    private SpannableStringBuilder updateString(String boldTxt, String normalTxt) {
        SpannableStringBuilder boldStr = new SpannableStringBuilder(boldTxt);
        boldStr.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, boldTxt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder colorStr = new SpannableStringBuilder(normalTxt);
        colorStr.setSpan(new ForegroundColorSpan(Color.RED), 0, normalTxt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableStringBuilder str = boldStr.append(colorStr);
        return str;
    }

    //Updating Calender Function
    private void updateLabel(){
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        dateField.setText(dateFormat.format(myCalendar.getTime()));
    }
}