package in.co.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import com.google.android.material.button.MaterialButton;

import java.io.IOException;

import in.co.umcsl.R;

public class AccountCreationFragment extends Fragment {

    EditText serach_Id;
    RadioGroup radioGroup;
    RadioButton text_MemberId, text_NewMemberShip, memberShipDetails;
    LinearLayout userDetails;

    EditText edit_IdentityProofFile, edit_AddressProofFile, edit_Photo, edit_Signature, edit_SignatureNominee,
            edit_SignatureNominee1, edit_IntroducerSignature2, edit_IntroducerSignature1;

    Spinner spinner_SelectidProof, spinner_AddressProof;
    String[] addressProofName = {"-Document Proof Name-", "Driving License", "Adhaar(UID)", "Passport", "voter id"};

    MaterialButton btn_ChooseFileidentity, btn_ChooseFileAddress, btn_ChooseFilePhoto, btn_ChooseFileSignature,
            btn_ChooseFileNominee, btn_ChooseFileNominee1, btn_ChooseFileIntroducer1, btn_ChooseFileIntroducer2;

    ImageView img_viewUserPhoto, img_viewUserSignature, img_viewSignatureNominee, img_viewSignatureNominee1;

    public static final int IMAGE_CODE = 1;

    String imageSelect;

    ActivityResultLauncher<Intent> resultLauncher;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acccreation, container, false);

        TextView name_text = view.findViewById(R.id.name_text);

        init(view);

        name_text.setText("Account Creation");

        userDetails.setVisibility(View.GONE);
        serach_Id.setVisibility(View.GONE);

        ArrayAdapter selectidProof = new ArrayAdapter(getActivity(), R.layout.spinneritem, addressProofName);
        selectidProof.setDropDownViewResource(R.layout.spinnerdropdownitem);
        spinner_SelectidProof.setAdapter(selectidProof);
        spinner_SelectidProof.setSelection(-1, true);

        ArrayAdapter addressProof = new ArrayAdapter(getActivity(), R.layout.spinneritem, addressProofName);
        addressProof.setDropDownViewResource(R.layout.spinnerdropdownitem);
        spinner_AddressProof.setAdapter(addressProof);
        spinner_AddressProof.setSelection(-1, true);


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

                        serach_Id.setVisibility(View.VISIBLE);
                        userDetails.setVisibility(View.VISIBLE);

                    } else if (str_radioButton.equals("New MemberShip")) {

                        serach_Id.setVisibility(View.GONE);
                        userDetails.setVisibility(View.VISIBLE);

                    }

                }
            }
        });

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


        return view;
    }

    public void init(View view) {


        serach_Id = view.findViewById(R.id.serach_Id);
        text_MemberId = view.findViewById(R.id.text_MemberId);
        text_NewMemberShip = view.findViewById(R.id.text_NewMemberShip);
        radioGroup = view.findViewById(R.id.radioGroup);
        userDetails = view.findViewById(R.id.userDetails);

        spinner_SelectidProof = view.findViewById(R.id.spinner_SelectidProof);
        spinner_AddressProof = view.findViewById(R.id.spinner_AddressProof);

        btn_ChooseFileidentity = view.findViewById(R.id.btn_ChooseFileidentity);
        btn_ChooseFileAddress = view.findViewById(R.id.btn_ChooseFileAddress);
        btn_ChooseFilePhoto = view.findViewById(R.id.btn_ChooseFilePhoto);
        btn_ChooseFileSignature = view.findViewById(R.id.btn_ChooseFileSignature);
        btn_ChooseFileNominee = view.findViewById(R.id.btn_ChooseFileNominee);
        btn_ChooseFileNominee1 = view.findViewById(R.id.btn_ChooseFileNominee1);
        btn_ChooseFileIntroducer1 = view.findViewById(R.id.btn_ChooseFileIntroducer1);
        btn_ChooseFileIntroducer2 = view.findViewById(R.id.btn_ChooseFileIntroducer2);

        img_viewUserPhoto = view.findViewById(R.id.img_viewUserPhoto);
        img_viewUserSignature = view.findViewById(R.id.img_viewUserSignature);
        img_viewSignatureNominee = view.findViewById(R.id.img_viewSignatureNominee);
        img_viewSignatureNominee1 = view.findViewById(R.id.img_viewSignatureNominee1);

        edit_IdentityProofFile = view.findViewById(R.id.edit_IdentityProofFile);
        edit_AddressProofFile = view.findViewById(R.id.edit_AddressProofFile);
        edit_Photo = view.findViewById(R.id.edit_Photo);
        edit_Signature = view.findViewById(R.id.edit_Signature);
        edit_SignatureNominee = view.findViewById(R.id.edit_SignatureNominee);
        edit_SignatureNominee1 = view.findViewById(R.id.edit_SignatureNominee1);
        edit_IntroducerSignature2 = view.findViewById(R.id.edit_IntroducerSignature2);
        edit_IntroducerSignature1 = view.findViewById(R.id.edit_IntroducerSignature1);

    }

    public void selectImage() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLauncher.launch(Intent.createChooser(intent, "title"));
    }
}
