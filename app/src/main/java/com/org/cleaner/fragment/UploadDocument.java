package com.org.cleaner.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.org.cleaner.activitys.MyConnectionReceiverCleaner;
import com.org.clinify.R;

public class UploadDocument extends Fragment {

    ImageView updoc1;
    ImageView updoc2;
    ImageView updoc3;
    ImageView updoc4;
    private StorageReference storageReference;
    private String userId;
    private StorageReference sr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_upload_document, container, false);

        MyConnectionReceiverCleaner myConnectionReceiver = new MyConnectionReceiverCleaner(getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        getActivity().registerReceiver(myConnectionReceiver, intentFilter);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        }

        updoc1 = v.findViewById(R.id.updoc1);
        updoc2 = v.findViewById(R.id.updoc2);
        updoc3 = v.findViewById(R.id.updoc3);
        updoc4 = v.findViewById(R.id.updoc4);


        updoc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1001);
            }
        });

        updoc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1002);
            }
        });

        updoc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1003);
            }
        });

        updoc4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1004);
            }
        });


        storageReference = FirebaseStorage.getInstance().getReference();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StorageReference storageReferences = storageReference.child("Cleaner/" + userId + "/Documents/" + "/profile1.jpg");
                storageReferences.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (uri != null) {
                            if (getActivity() != null) {
                                Glide.with(getActivity())
                                        .load(uri)
                                        .centerCrop()
                                        .placeholder(R.drawable.ic_person)
                                        .into(updoc1);
                            }

                        }
                    }
                });
            }
        }, 500);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StorageReference storageReferences = storageReference.child("Cleaner/" + userId + "/Documents/" + "/profile2.jpg");
                storageReferences.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (uri != null) {
                            if (getActivity() != null) {
                                Glide.with(getActivity())
                                        .load(uri)
                                        .centerCrop()
                                        .placeholder(R.drawable.ic_person)
                                        .into(updoc2);
                            }

                        }
                    }
                });
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StorageReference storageReferences = storageReference.child("Cleaner/" + userId + "/Documents/" + "/profile3.jpg");
                storageReferences.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (uri != null) {
                            if (getActivity() != null) {
                                Glide.with(getActivity())
                                        .load(uri)
                                        .centerCrop()
                                        .placeholder(R.drawable.ic_person)
                                        .into(updoc3);
                            }
                        }
                    }
                });
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StorageReference storageReferences = storageReference.child("Cleaner/" + userId + "/Documents/" + "/profile4.jpg");
                storageReferences.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (uri != null) {
                            if (getActivity() != null) {
                                Glide.with(getActivity())
                                        .load(uri)
                                        .centerCrop()
                                        .placeholder(R.drawable.ic_person)
                                        .into(updoc4);
                            }
                        }
                    }
                });
            }
        }, 500);

        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK) {
                //code review completed
                if (data != null) {
                    Uri imageUri = data.getData();
                    //profileImg.setImageURI(imageUri);
                    sr = storageReference.child("Cleaner/" + userId + "/Documents/" + "/profile1.jpg");
                    sr.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getActivity(), "Image saved successfully", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Image not saved", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }


        if (requestCode == 1002) {
            if (resultCode == Activity.RESULT_OK) {
                //code review completed
                if (data != null) {
                    Uri imageUri = data.getData();
                    //profileImg.setImageURI(imageUri);
                    sr = storageReference.child("Cleaner/" + userId + "/Documents/" + "/profile2.jpg");
                    sr.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getActivity(), "Image saved successfully", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Image not saved", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }

        if (requestCode == 1003) {
            if (resultCode == Activity.RESULT_OK) {
                //code review completed
                if (data != null) {
                    Uri imageUri = data.getData();
                    //profileImg.setImageURI(imageUri);
                    sr = storageReference.child("Cleaner/" + userId + "/Documents/" + "/profile3.jpg");
                    sr.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getActivity(), "Image saved successfully", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Image not saved", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }

        if (requestCode == 1004) {
            if (resultCode == Activity.RESULT_OK) {
                //code review completed
                if (data != null) {
                    Uri imageUri = data.getData();
                    //profileImg.setImageURI(imageUri);
                    sr = storageReference.child("Cleaner/" + userId + "/Documents/" + "/profile4.jpg");
                    sr.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getActivity(), "Image saved successfully", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Image not saved", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }


    }


}