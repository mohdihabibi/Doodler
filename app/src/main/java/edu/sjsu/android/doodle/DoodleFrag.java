package edu.sjsu.android.doodle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.raed.drawingview.BrushView;
import com.raed.drawingview.DrawingView;
import com.raed.drawingview.brushes.BrushSettings;
import com.raed.drawingview.brushes.Brushes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import yuku.ambilwarna.AmbilWarnaDialog;


public class DoodleFrag extends Fragment{
    public final static int PICK_PHOTO_CODE = 1046;
    int defColor;
    DrawingView drawingView;
    ImageButton  chooseBrushBtn,selectPhotoBtn, savebtn;
    Button sBrush, mBrush, lBrush;
    EditText etDescription;
    Button selectColorBtn;
    private View view;
    public IndividualDoodle post;
    public String id;
    public File photoFile;
    BrushSettings brushSettings;
    BrushView brushView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.doodle_frag, container, false);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chooseBrushBtn =view.findViewById(R.id.brushbtn);
        savebtn =view.findViewById(R.id.save_btn);
        selectColorBtn = view.findViewById(R.id.colorbtn);
        selectPhotoBtn =view.findViewById(R.id.photo_insert_btn);
        etDescription = view.findViewById(R.id.description);
        drawingView = view.findViewById(R.id.draw);
        brushView = view.findViewById(R.id.brush_view);
        brushView.setDrawingView(drawingView);
        brushSettings = drawingView.getBrushSettings();

        chooseBrushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog bDialog = new Dialog(getContext());
                bDialog.setTitle("Select a size for brush");
                bDialog.setContentView(R.layout.brush_chooser);
                sBrush = bDialog.findViewById(R.id.small_brush);
                mBrush = bDialog.findViewById(R.id.medium_brush);
                lBrush = bDialog.findViewById(R.id.large_brush);
                sBrush.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        brushSettings.setBrushSize(Brushes.PENCIL, 0.2f);
                        bDialog.dismiss();
                    }
                });

                mBrush.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        brushSettings.setBrushSize(Brushes.PENCIL, 0.5f);
                        bDialog.dismiss();
                    }
                });

                lBrush.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        brushSettings.setBrushSize(Brushes.PENCIL, 1.0f);
                        bDialog.dismiss();
                    }
                });
                bDialog.show();

            }
        });


        selectPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, PICK_PHOTO_CODE);
                }
            }
        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder saveDialog = new AlertDialog.Builder(getContext());
                saveDialog.setTitle("Would you like to save the picture?");
                saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Bitmap bitmap = drawingView.exportDrawing();
                        photoFile = convertBitmapToFile(bitmap);
                        String description = etDescription.getText().toString();
                        ParseUser user = ParseUser.getCurrentUser();
                        saveAndPostImage(description,user,photoFile);
                        Toast.makeText(getContext(), "Photo is saved", Toast.LENGTH_SHORT).show();

                    }
                });

                saveDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                saveDialog.show();
            }
        });
        selectColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorPalette();
            }
        });
    }

    private void saveAndPostImage(String des, ParseUser user, File file) {
        post = new IndividualDoodle();
        post.setImage(new ParseFile(file));
        post.setDescription(des);
        post.setUser(user);

        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                id = post.getObjectId();
                if(e != null){
                    e.printStackTrace();
                    return;
                }
                etDescription.setText("");
                drawingView.setBackgroundImage(null);
                drawingView.clear();
            }
        });
    }

    private void colorPalette() {
        AmbilWarnaDialog colorPalette = new AmbilWarnaDialog(getContext(), defColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defColor = color;
                brushSettings.setColor(defColor);
            }
        });
        colorPalette.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri photoUri = data.getData();
            Bitmap photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            drawingView.setBackgroundImage(photo);

        }
    }

    private File convertBitmapToFile(Bitmap bitmap) {
        File f = new File(getContext().getCacheDir(), "draw.png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }



}

