package in.co.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.co.extra.Appurl;
import in.co.extra.SessionManager;
import in.co.umcsl.DeshBoard;
import in.co.umcsl.LoginPage;
import in.co.umcsl.R;

public class AccountCreationFragment extends Fragment {
    RadioGroup radioGroup;
    DatePickerDialog datePicker;
    RadioButton text_MemberId, text_NewMemberShip, memberShipDetails;
    LinearLayout userDetails,serachMemberId;
    EditText edit_IdentityProofFile, edit_AddressProofFile, edit_Photo, edit_Signature, edit_SignatureNominee,edit_AccountNo,
            edit_SignatureNominee1, edit_IntroducerSignature2, edit_IntroducerSignature1,edit_serachId,edit_DateOfJoining,
            edit_ApplicantName,edit_Age,edit_FatherHusbandName,edit_MarkOfIdentity,edit_Gender,edit_Status,edit_Occupation,
            edit_DepositeAmt,edit_Address,edit_ContactNo,edit_Relation,edit_NomineeName1;
    Spinner spinner_SelectidProof, spinner_AddressProof;
    String[] addressProofName = {"-Document Proof Name-", "Driving License", "Adhaar(UID)", "Passport", "voter id"};
    MaterialButton btn_ChooseFileidentity, btn_ChooseFileAddress, btn_ChooseFilePhoto, btn_ChooseFileSignature,
            btn_ChooseFileNominee, btn_ChooseFileNominee1, btn_ChooseFileIntroducer1, btn_ChooseFileIntroducer2,btn_Serach;
    ImageView img_viewUserPhoto, img_viewUserSignature, img_viewSignatureNominee, img_viewSignatureNominee1,logout_bar_img;

    public static final int IMAGE_CODE = 1;
    String imageSelect,AccountNumber,Active,CreatedDate,DOB,Email,FatherHusbandName,MemberId,MemberName,Message,agentid,
            Mobilenumber,Status,message,Id,Name,Email1,Message1,Mobilenumber1,Status1,Address,str_AccountNo,str_DateOfJoining,
            str_ApplicantName,str_Age,str_Gender,str_ContactNo,str_DepositeAmt,str_Address,str_FatherName,str_Relation,str_NomineeName1,
            Message3,Status3;
    ActivityResultLauncher<Intent> resultLauncher;
    Button btn_signin;

    TextView accountNoExit;

    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acccreation, container, false);

        TextView name_text = view.findViewById(R.id.name_text);
        init(view);

        name_text.setText("Account Creation");

        sessionManager = new SessionManager(getContext());
        agentid = sessionManager.getUserID();

        Log.d("gfdtrctcfc",agentid);

        String date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        edit_DateOfJoining.setText(date);

        logout_bar_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sessionManager.logoutUser();

            }
        });

        userDetails.setVisibility(View.GONE);
        serachMemberId.setVisibility(View.GONE);

       /* ArrayAdapter selectidProof = new ArrayAdapter(getActivity(), R.layout.spinneritem, addressProofName);
        selectidProof.setDropDownViewResource(R.layout.spinnerdropdownitem);
        spinner_SelectidProof.setAdapter(selectidProof);
        spinner_SelectidProof.setSelection(-1, true);

        ArrayAdapter addressProof = new ArrayAdapter(getActivity(), R.layout.spinneritem, addressProofName);
        addressProof.setDropDownViewResource(R.layout.spinnerdropdownitem);
        spinner_AddressProof.setAdapter(addressProof);
        spinner_AddressProof.setSelection(-1, true);*/

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                memberShipDetails = (RadioButton) group.findViewById(checkedId);

                if (selectedId == -1) {

                    Toast.makeText(getActivity(), "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {

                    String str_radioButton = memberShipDetails.getText().toString();

                    if (str_radioButton.equals("Member Id")) {

                        serachMemberId.setVisibility(View.VISIBLE);
                        userDetails.setVisibility(View.VISIBLE);
                        message = "MemberId";

                        edit_AccountNo.setText("");
                        edit_ApplicantName.setText("");
                        edit_Age.setText("");
                        edit_FatherHusbandName.setText("");
                       // edit_Status.setText("");
                        edit_Address.setText("");



                    } else if (str_radioButton.equals("New MemberShip")) {

                        serachMemberId.setVisibility(View.GONE);
                        userDetails.setVisibility(View.VISIBLE);
                        message = "MemberShip";

                        edit_AccountNo.setText("");
                        edit_ApplicantName.setText("");
                        edit_Age.setText("");
                        edit_FatherHusbandName.setText("");
                       // edit_Status.setText("");
                        edit_Address.setText("");


                       /* str_AccountNo = edit_AccountNo.getText().toString().trim();
                        str_DateOfJoining = edit_DateOfJoining.getText().toString().trim();
                        str_ApplicantName = edit_ApplicantName.getText().toString().trim();
                        str_Age = edit_Age.getText().toString().trim();
                        str_Gender = edit_Gender.getText().toString().trim();
                        str_ContactNo = edit_ContactNo.getText().toString().trim();
                        str_DepositeAmt = edit_DepositeAmt.getText().toString().trim();

                        accountCreation(str_AccountNo,str_DateOfJoining,str_ApplicantName,str_Age,str_Gender,str_ContactNo,str_DepositeAmt);*/
                    }

                }
            }
        });
        btn_Serach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(message.equals("MemberId")){

                    if(edit_serachId.getText().toString().trim().equals("")){

                        Toast.makeText(getContext(), "Enter Your Member Id", Toast.LENGTH_SHORT).show();

                    }else{

                        String memberId = edit_serachId.getText().toString().trim();
                        getMemberDetails(memberId);
                    }
                }
            }
        });
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (edit_NomineeName1.getText().toString().trim().equals("")){

                    Toast.makeText(getActivity(), "Write Your Nominee Name", Toast.LENGTH_SHORT).show();

                } else if (edit_Relation.getText().toString().trim().equals("")) {

                    Toast.makeText(getActivity(), "Write Your Relation", Toast.LENGTH_SHORT).show();

                }else{

                    str_AccountNo = edit_AccountNo.getText().toString().trim();
                    str_DateOfJoining = edit_DateOfJoining.getText().toString().trim();
                    str_ApplicantName = edit_ApplicantName.getText().toString().trim();
                    str_Age = edit_Age.getText().toString().trim();
                    str_Gender = edit_Gender.getText().toString().trim();
                    str_ContactNo = edit_ContactNo.getText().toString().trim();
                    str_DepositeAmt = edit_DepositeAmt.getText().toString().trim();
                    str_Address = edit_Address.getText().toString().trim();
                    str_FatherName = edit_FatherHusbandName.getText().toString().trim();
                    str_NomineeName1 = edit_NomineeName1.getText().toString().trim();
                    str_Relation = edit_Relation.getText().toString().trim();

                    //accountCreation(str_AccountNo,str_DateOfJoining,str_ApplicantName,str_Age,str_FatherName,str_Address,
                       //     str_Gender,str_NomineeName1,str_Relation,str_ContactNo,str_DepositeAmt);

                    accountCreation(str_AccountNo,str_DateOfJoining,str_ApplicantName,str_Age,str_FatherName,str_Address,
                            str_Gender,str_ContactNo,str_DepositeAmt,str_NomineeName1,str_Relation,agentid);
                }
            }
        });

    /*
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                Intent intent = result.getData();

                if (intent != null) {

                    try {

                        if (imageSelect.equals("1")) {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), intent.getData());
                            img_viewUserPhoto.setImageBitmap(bitmap);
                            edit_Photo.setText("Image Selected");

                        } else if (imageSelect.equals("2")) {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), intent.getData());
                            img_viewUserSignature.setImageBitmap(bitmap);
                            edit_Signature.setText("Image Selected");

                        } else if (imageSelect.equals("3")) {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), intent.getData());
                            img_viewSignatureNominee.setImageBitmap(bitmap);
                            edit_SignatureNominee.setText("Image Selected");

                        } else if (imageSelect.equals("4")) {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), intent.getData());
                            img_viewSignatureNominee1.setImageBitmap(bitmap);
                            edit_SignatureNominee1.setText("Image Selected");

                        } else if (imageSelect.equals("5")) {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), intent.getData());
                            edit_IdentityProofFile.setText("Image Selected");

                        } else if (imageSelect.equals("6")) {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), intent.getData());
                            edit_AddressProofFile.setText("Image Selected");

                        } else if (imageSelect.equals("7")) {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), intent.getData());
                            edit_IntroducerSignature1.setText("Image Selected");

                        } else if (imageSelect.equals("8")) {

                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), intent.getData());
                            edit_IntroducerSignature2.setText("Image Selected");

                        }


                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        });
        btn_ChooseFilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageSelect = "1";
                selectImage();

            }
        });
        btn_ChooseFileSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageSelect = "2";
                selectImage();

            }
        });
        btn_ChooseFileNominee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageSelect = "3";
                selectImage();

            }
        });
        btn_ChooseFileNominee1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageSelect = "4";
                selectImage();

            }
        });
        btn_ChooseFileidentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageSelect = "5";
                selectImage();

            }
        });
        btn_ChooseFileAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageSelect = "6";
                selectImage();

            }
        });
        btn_ChooseFileIntroducer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageSelect = "7";
                selectImage();

            }
        });
        btn_ChooseFileIntroducer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageSelect = "8";
                selectImage();

            }
        });

        */
        edit_DateOfJoining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);

                datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        // adding the selected date in the edittext
                        //  binding.dojshg.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                        edit_DateOfJoining.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        int yearr = Calendar.getInstance().get(Calendar.YEAR);
                        int finalage = yearr - year;
                    }
                }, year, month, day);

                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                datePicker.show();

            }
        });

        edit_AccountNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



                if(!edit_AccountNo.getText().toString().trim().equals("")){

                    str_AccountNo = edit_AccountNo.getText().toString().trim();
                    accountValidation(str_AccountNo);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }

    public void init(View view) {

        text_MemberId = view.findViewById(R.id.text_MemberId);
        text_NewMemberShip = view.findViewById(R.id.text_NewMemberShip);
        radioGroup = view.findViewById(R.id.radioGroup);
        userDetails = view.findViewById(R.id.userDetails);

//        spinner_SelectidProof = view.findViewById(R.id.spinner_SelectidProof);
//        spinner_AddressProof = view.findViewById(R.id.spinner_AddressProof);
//
//        btn_ChooseFileidentity = view.findViewById(R.id.btn_ChooseFileidentity);
//        btn_ChooseFileAddress = view.findViewById(R.id.btn_ChooseFileAddress);
//        btn_ChooseFilePhoto = view.findViewById(R.id.btn_ChooseFilePhoto);
//        btn_ChooseFileSignature = view.findViewById(R.id.btn_ChooseFileSignature);
//        btn_ChooseFileNominee = view.findViewById(R.id.btn_ChooseFileNominee);
//        btn_ChooseFileNominee1 = view.findViewById(R.id.btn_ChooseFileNominee1);
        btn_ChooseFileIntroducer1 = view.findViewById(R.id.btn_ChooseFileIntroducer1);
        btn_ChooseFileIntroducer2 = view.findViewById(R.id.btn_ChooseFileIntroducer2);

//        img_viewUserPhoto = view.findViewById(R.id.img_viewUserPhoto);
//        img_viewUserSignature = view.findViewById(R.id.img_viewUserSignature);
//        img_viewSignatureNominee = view.findViewById(R.id.img_viewSignatureNominee);
//        img_viewSignatureNominee1 = view.findViewById(R.id.img_viewSignatureNominee1);
//
//        edit_IdentityProofFile = view.findViewById(R.id.edit_IdentityProofFile);
//        edit_AddressProofFile = view.findViewById(R.id.edit_AddressProofFile);
//        edit_Photo = view.findViewById(R.id.edit_Photo);
//        edit_Signature = view.findViewById(R.id.edit_Signature);
//        edit_SignatureNominee = view.findViewById(R.id.edit_SignatureNominee);
//        edit_SignatureNominee1 = view.findViewById(R.id.edit_SignatureNominee1);
        edit_IntroducerSignature2 = view.findViewById(R.id.edit_IntroducerSignature2);
        edit_IntroducerSignature1 = view.findViewById(R.id.edit_IntroducerSignature1);
        btn_Serach = view.findViewById(R.id.btn_Serach);
        edit_serachId = view.findViewById(R.id.edit_serachId);
        edit_DateOfJoining = view.findViewById(R.id.edit_DateOfJoining);
        edit_ApplicantName = view.findViewById(R.id.edit_ApplicantName);
        edit_Age = view.findViewById(R.id.edit_Age);
        edit_FatherHusbandName = view.findViewById(R.id.edit_FatherHusbandName);
       // edit_MarkOfIdentity = view.findViewById(R.id.edit_MarkOfIdentity);
        edit_Gender = view.findViewById(R.id.edit_Gender);
       // edit_Status = view.findViewById(R.id.edit_Status);
        edit_Occupation = view.findViewById(R.id.edit_Occupation);
        serachMemberId = view.findViewById(R.id.serachMemberId);
        edit_AccountNo = view.findViewById(R.id.edit_AccountNo);
        btn_signin = view.findViewById(R.id.btn_signin);
        edit_DepositeAmt = view.findViewById(R.id.edit_DepositeAmt);
        edit_Address = view.findViewById(R.id.edit_Address);
        edit_ContactNo = view.findViewById(R.id.edit_ContactNo);
        edit_Relation = view.findViewById(R.id.edit_Relation);
        edit_NomineeName1 = view.findViewById(R.id.edit_NomineeName1);
        logout_bar_img = view.findViewById(R.id.logout_bar_img);
        accountNoExit = view.findViewById(R.id.accountNoExit);
    }

    public void selectImage() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLauncher.launch(Intent.createChooser(intent, "title"));
    }

    public void getMemberDetails(String memberId){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Login Please Wait....");
        progressDialog.show();

        String urlLogin = Appurl.SearchMember+memberId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET,urlLogin, response -> {

            progressDialog.dismiss();

            try {
                JSONObject jsonObject = new JSONObject(response);

                String SVCSearchMemberResult = jsonObject.getString("SVCSearchMemberResult");
                JSONArray jsonArray_login = new JSONArray(SVCSearchMemberResult);

                for (int i= 0;i<jsonArray_login.length();i++){

                    JSONObject jsonObject_logindata = jsonArray_login.getJSONObject(0);

                    AccountNumber = jsonObject_logindata.getString("AccountNumber");
                    Active = jsonObject_logindata.getString("Active");
                    Address = jsonObject_logindata.getString("Address");
                    CreatedDate = jsonObject_logindata.getString("CreatedDate");
                    DOB = jsonObject_logindata.getString("DOB");
                    //Email = jsonObject_logindata.getString("Email");
                    FatherHusbandName = jsonObject_logindata.getString("FatherHusbandName");
                    MemberId = jsonObject_logindata.getString("MemberId");
                    MemberName = jsonObject_logindata.getString("MemberName");
                    Message = jsonObject_logindata.getString("Message");
                    Mobilenumber = jsonObject_logindata.getString("Mobilenumber");
                    Status = jsonObject_logindata.getString("Status");

                }

                if (Status.equals("0")){

                    Toast.makeText(getContext(), Message, Toast.LENGTH_SHORT).show();

                }else{

                    edit_AccountNo.setText(AccountNumber);
                    edit_DateOfJoining.setText(CreatedDate);
                    edit_ApplicantName.setText(MemberName);
                    edit_Age.setText(DOB);
                    edit_FatherHusbandName.setText(FatherHusbandName);
                    //edit_Status.setText(Status);
                    edit_Address.setText(Address);

//                    edit_AccountNo.setEnabled(false);
//                    edit_DateOfJoining.setEnabled(false);
//                    edit_ApplicantName.setEnabled(false);
//                    edit_Age.setEnabled(false);
//                    edit_FatherHusbandName.setEnabled(false);
//                    edit_Status.setEnabled(false);
//                    edit_Address.setEnabled(false);

                }


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }



        },error -> {

            progressDialog.dismiss();
            Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();

        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void accountCreation(String Accountno,String DateOfJoining,String ApplicantName,String Age,
                                String FathersName,String PresentAddress,String Gender,
                                String MobileNo,String DepositeAmt,String Nominee,String relation,String AgentID){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Account Creation Please Wait....");
        progressDialog.show();

        String createAcc = Appurl.CreateMemberAccount+"Accountno="+Accountno+"&DateOfJoining="
                +DateOfJoining+"&ApplicantName="+ApplicantName+"&Age="+Age+"&FathersName="
                +FathersName+"&PresentAddress="+PresentAddress+"&Gender"+Gender+"&MobileNo="+MobileNo+"&Nominee="+Nominee+"&relation="
                +relation+"&DepositeAmt="+DepositeAmt+"&AgentID="+AgentID;

        Log.d("jcretaeaccountg",createAcc);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,createAcc, response -> {

            progressDialog.dismiss();

            try {
                JSONObject jsonObject = new JSONObject(response);

                String SCreateMemberAccountResult = jsonObject.getString("SCreateDailyAccountResult");
                JSONArray jsonArray_login = new JSONArray(SCreateMemberAccountResult);

                for (int i= 0;i<jsonArray_login.length();i++){

                    JSONObject jsonObject_logindata = jsonArray_login.getJSONObject(0);

                    Email1 = jsonObject_logindata.getString("Email");
                    Id = jsonObject_logindata.getString("Id");
                    Message1 = jsonObject_logindata.getString("Message");
                    Mobilenumber1 = jsonObject_logindata.getString("Mobilenumber");
                    Name = jsonObject_logindata.getString("Name");
                    Status1 = jsonObject_logindata.getString("Status");

                }

                if (Status1.equals("0")){

                    Toast.makeText(getContext(), Message1, Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(getContext(), Message1, Toast.LENGTH_SHORT).show();

                    edit_AccountNo.setText("");
                    edit_DateOfJoining.setText("");
                    edit_ApplicantName.setText("");
                    edit_Age.setText("");
                    edit_FatherHusbandName.setText("");
                   // edit_Status.setText("");
                    edit_Address.setText("");
                }


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        },error -> {

            progressDialog.dismiss();
            Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();

        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void accountValidation(String Accountno){

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Account Creation Please Wait....");
        progressDialog.show();

        String createAcc = Appurl.SVCCheckAccountExists+"AccountNumber="+Accountno;

        Log.d("jcretaeaccountg",createAcc);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,createAcc, response -> {

            progressDialog.dismiss();

            try {
                JSONObject jsonObject = new JSONObject(response);

                String SCreateMemberAccountResult = jsonObject.getString("SVCCheckAccountExistsResult");
                JSONArray jsonArray_login = new JSONArray(SCreateMemberAccountResult);

                for (int i= 0;i<jsonArray_login.length();i++){

                    JSONObject jsonObject_logindata = jsonArray_login.getJSONObject(0);

                    Message3 = jsonObject_logindata.getString("Message");
                    Status3 = jsonObject_logindata.getString("Status");

                }

                if (Status3.equals("0")){

                   // Toast.makeText(getContext(), Message1, Toast.LENGTH_SHORT).show();
                    accountNoExit.setText(Message3);

                }else{

                   // Toast.makeText(getContext(), Message1, Toast.LENGTH_SHORT).show();
                    accountNoExit.setText(Message3);
                    accountNoExit.setTextColor(Color.GREEN);
                }


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        },error -> {

            progressDialog.dismiss();
            Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();

        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


}
