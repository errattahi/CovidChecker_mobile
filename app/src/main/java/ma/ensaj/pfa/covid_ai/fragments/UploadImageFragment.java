package ma.ensaj.pfa.covid_ai.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ma.ensaj.pfa.covid_ai.BuildConfig;
import ma.ensaj.pfa.covid_ai.R;
import ma.ensaj.pfa.covid_ai.retrofit.network.RetrofitInstance;
import ma.ensaj.pfa.covid_ai.retrofit.service.DataService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImageFragment extends Fragment {
    private TextView textView,textView2;
    private ProgressBar progressBar;
    private ImageView imageView;
    private Bitmap rgbFrameBitmap = null;
    private Uri fileUri = null;
    private Button upload_button,retry,learn_more;
    private CardView card_view_result;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fargment_upload_image, container, false);

        if (ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CAMERA},
                    1);

        } else {
            // Permission has already been granted
        }

        textView = root.findViewById(R.id.textView);
        textView2 = root.findViewById(R.id.textView2);
        progressBar = root.findViewById(R.id.progressBar);
        imageView = root.findViewById(R.id.imageView);
        upload_button = root.findViewById(R.id.upload_button);
        card_view_result = root.findViewById(R.id.card_view_result);
        retry = root.findViewById(R.id.retry);
        learn_more = root.findViewById(R.id.learn_more);

        retry.setVisibility(View.GONE);
        learn_more.setVisibility(View.GONE);

        progressBar.setVisibility(View.GONE);
        card_view_result.setVisibility(View.GONE);
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    builder.setTitle("Upload image");
                    builder.setItems(options, (dialog, item) -> {
                        if (options[item].equals("Take Photo")) {
                            Intent pictureIntent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                            } catch (IOException ex) {
                            }
                            if (photoFile != null) {
                                Uri photoURI = FileProvider.getUriForFile(root.getContext(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
                                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                        photoURI);
                                startActivityForResult(pictureIntent,
                                        100);
                            }
                        } else if (options[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 200);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CAMERA},
                            1);

                }

            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retry.setVisibility(View.GONE);
                learn_more.setVisibility(View.GONE);

                progressBar.setVisibility(View.VISIBLE);
                card_view_result.setVisibility(View.GONE);
                if (ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                    builder.setTitle("Upload image");
                    builder.setItems(options, (dialog, item) -> {
                        if (options[item].equals("Take Photo")) {
                            Intent pictureIntent = new Intent(
                                    MediaStore.ACTION_IMAGE_CAPTURE);
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                            } catch (IOException ex) {
                            }
                            if (photoFile != null) {
                                Uri photoURI = FileProvider.getUriForFile(root.getContext(), BuildConfig.APPLICATION_ID + ".provider", photoFile);
                                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                        photoURI);
                                startActivityForResult(pictureIntent,
                                        100);
                            }
                        } else if (options[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 200);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CAMERA},
                            1);

                }

            }
        });


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("code", resultCode + "");

        if (requestCode == 100) {
            String selectedImage = imageFilePath;
            Log.d("path", selectedImage + "");
            uploadFile(selectedImage, requestCode);
        } else if (requestCode == 200 && resultCode == -1 && data != null) {
            Uri selectedImage = data.getData();
            uploadFile(selectedImage.toString(), requestCode);
        }
    }

    String imageFilePath;

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private void uploadFile(String fileUri, int code) {
        this.fileUri = Uri.parse(fileUri);
        File file = null;
        if (code == 100) {
            file = new File(fileUri);
        } else if (code == 200) {
            file = new File(getRealPathFromURI(Uri.parse(fileUri)));
        }
        imageView.setImageURI(Uri.parse(fileUri));
        imageView.setElevation(10f);
        progressBar.setVisibility(View.VISIBLE);
        upload_button.setVisibility(View.GONE);


        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        String format = file.getName().split("\\.")[1];
        String name = getArguments().getString("userId") + "." + format;
        MultipartBody.Part requestImage = MultipartBody.Part.createFormData("file", name, requestFile);

        DataService service = RetrofitInstance.getInstance().create(DataService.class);

        Call<ResponseBody> call = service.predict(requestImage);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    card_view_result.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    retry.setVisibility(View.VISIBLE);
                    learn_more.setVisibility(View.VISIBLE);
                    String result = response.body().string();
                    textView.setText("The probability that you have COVID 19 is: ");
                    textView2.setText(result+" %");

                    if(Float.parseFloat(result)>=50.0){
                        textView2.setTextColor(getResources().getColor(R.color.red));
                        getActivity().findViewById(R.id.upload).setBackgroundColor(getResources().getColor(R.color.red));
                        learn_more.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LearnMoreNegativeFragment lmnf = new LearnMoreNegativeFragment();
                                getFragmentManager().beginTransaction().replace(R.id.container, lmnf).commit();
                            }
                        });
                    }else {
                        textView2.setTextColor(getResources().getColor(R.color.green));
                        getActivity().findViewById(R.id.upload).setBackgroundColor(getResources().getColor(R.color.green));
                        learn_more.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LearnMorePositiveFragment lmpf = new LearnMorePositiveFragment();
                                getFragmentManager().beginTransaction().replace(R.id.container, lmpf).commit();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



       /*
        if (isOnline()) {


        } else {
            Toast.makeText(getApplication(),"Internet Not Available, Cross Check Your Internet Connectivity and Try Again!",Toast.LENGTH_LONG).show();


            try {
                Model model = Model.newInstance(getApplication());

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                InputStream input = this.getContentResolver().openInputStream(this.fileUri);
                BitmapFactory.decodeStream(input, null, options);
                input.close();

                rgbFrameBitmap = Bitmap.createBitmap(options.outWidth, options.outHeight, Bitmap.Config.ARGB_8888);
                //rgbFrameBitmap.setPixels(getRgbBytes(), 0, previewWidth, 0, 0, previewWidth, previewHeight);

                TensorImage tfImage = TensorImage.fromBitmap(rgbFrameBitmap);


                Model.Outputs outputs = model.process(tfImage.getTensorBuffer());

                textView.setText(String.valueOf(outputs.getOutputFeature0AsTensorBuffer().getFloatArray()[0]));

                model.close();
            } catch (IOException e) {
                // TODO Handle the exception
            }


        }*/

    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


}
