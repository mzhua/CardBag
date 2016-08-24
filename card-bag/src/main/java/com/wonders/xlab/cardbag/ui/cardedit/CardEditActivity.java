package com.wonders.xlab.cardbag.ui.cardedit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.util.ImageViewUtil;
import com.wonders.xlab.cardbag.widget.RatioImageView;
import com.wonders.xlab.cardbag.widget.TopBar;
import com.wonders.xlab.qrscanner.BarCodeEncoder;
import com.wonders.xlab.qrscanner.XQrScanner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardEditActivity extends Activity {
    private final int REQUEST_CODE_MASK = 1;
    private final int REQUEST_CODE_SCAN_BAR_CODE = REQUEST_CODE_MASK << 1;
    private final int REQUEST_TAKE_PHOTO_CARD_FRONT = REQUEST_CODE_MASK << 2;
    private final int REQUEST_TAKE_PHOTO_CARD_BACK = REQUEST_CODE_MASK << 3;


    private TopBar mTopBar;
    private RatioImageView mIvCard;
    private RatioImageView mIvCardFront;
    private RatioImageView mIvCardBack;
    private RatioImageView mIvBarCode;
    private EditText mEtCardName;
    private TextView mTvBarCode;

    private String mCardId;

    private String mBarCode;
    private Bitmap mBitmapBarCodeTmp;
    private String mCardFrontPhotoPath;
    private String mCardBackPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_card_edit_activity);
        mTopBar = (TopBar) findViewById(R.id.top_bar);
        mIvCard = (RatioImageView) findViewById(R.id.iv_card);
        mIvCardFront = (RatioImageView) findViewById(R.id.iv_card_front);
        mIvCardBack = (RatioImageView) findViewById(R.id.iv_card_back);
        mIvBarCode = (RatioImageView) findViewById(R.id.iv_bar_code);
        mEtCardName = (EditText) findViewById(R.id.et_card_name);
        mTvBarCode = (TextView) findViewById(R.id.tv_bar_code);

        Intent intent = getIntent();
        if (intent != null) {
            mCardId = intent.getStringExtra("id");
            String cardName = intent.getStringExtra("cardName");
            String cardImageUrl = intent.getStringExtra("cardImageUrl");
            mEtCardName.setText(cardName);
            ImageViewUtil.load(this, cardImageUrl, mIvCard);
        }

        if (mEtCardName.length() > 0) {
            mEtCardName.setSelection(mEtCardName.length());
        }
        mTopBar.setTitle(TextUtils.isEmpty(mCardId) ? getString(R.string.title_card_edit_add) : getString(R.string.title_card_edit_edit));
    }

    public void scanBarCode(View view) {
        XQrScanner.getInstance()
                .startForResult(this, REQUEST_CODE_SCAN_BAR_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN_BAR_CODE:
                    mBarCode = data.getStringExtra(XQrScanner.EXTRA_RESULT_BAR_OR_CODE_STRING);
                    mTvBarCode.setText(mBarCode);
                    BarCodeEncoder ecc = new BarCodeEncoder(mIvBarCode.getWidth(), mIvBarCode.getHeight());
                    try {
                        mBitmapBarCodeTmp = ecc.barcode(mBarCode);
                        mIvBarCode.setImageBitmap(mBitmapBarCodeTmp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case REQUEST_TAKE_PHOTO_CARD_FRONT:
                    setPic(mIvCardFront, mCardFrontPhotoPath);
                    break;
                case REQUEST_TAKE_PHOTO_CARD_BACK:
                    setPic(mIvCardBack, mCardBackPhotoPath);
                    break;
            }
        }
    }

    public void shotCardFront(View view) {
        dispatchTakePictureIntent("front", REQUEST_TAKE_PHOTO_CARD_FRONT);
    }

    public void shotCardBack(View view) {
        dispatchTakePictureIntent("back", REQUEST_TAKE_PHOTO_CARD_BACK);
    }

    private void setPic(ImageView imageView, String photoPath) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    private File createImageFile(String fileName, int requestCode) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = fileName + "_JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO_CARD_FRONT:
                mCardFrontPhotoPath = image.getAbsolutePath();
                break;
            case REQUEST_TAKE_PHOTO_CARD_BACK:
                mCardBackPhotoPath = image.getAbsolutePath();
                break;
        }
        return image;
    }

    private void dispatchTakePictureIntent(String fileName, int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(fileName, requestCode);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = Uri.fromFile(photoFile);//FileProvider.getUriForFile(this,"com.wonders.xlab.cardbag.fileprovider",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }
}
