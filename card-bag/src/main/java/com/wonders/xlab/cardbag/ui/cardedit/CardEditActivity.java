package com.wonders.xlab.cardbag.ui.cardedit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.BaseContract;
import com.wonders.xlab.cardbag.base.MVPActivity;
import com.wonders.xlab.cardbag.util.FileUtil;
import com.wonders.xlab.cardbag.util.ImageViewUtil;
import com.wonders.xlab.cardbag.widget.RatioImageView;
import com.wonders.xlab.cardbag.widget.TopBar;
import com.wonders.xlab.qrscanner.BarCodeEncoder;
import com.wonders.xlab.qrscanner.XQrScanner;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;

import static android.R.attr.maxHeight;
import static android.R.attr.maxWidth;

public class CardEditActivity extends MVPActivity {
    private final int REQUEST_CODE_MASK = 1;
    private final int REQUEST_CODE_SCAN_BAR_CODE = REQUEST_CODE_MASK << 1;
    private final int REQUEST_CODE_TAKE_PHOTO_CARD_FRONT = REQUEST_CODE_MASK << 2;
    private final int REQUEST_CODE_TAKE_PHOTO_CARD_BACK = REQUEST_CODE_MASK << 3;
    private final int REQUEST_CODE_CROP_FRONT = REQUEST_CODE_MASK << 4;
    private final int REQUEST_CODE_CROP_BACK = REQUEST_CODE_MASK << 5;


    private TopBar mTopBar;
    private RatioImageView mIvCard;
    private RatioImageView mIvCardFront;
    private RatioImageView mIvCardBack;
    private RatioImageView mIvBarCode;
    private EditText mEtCardName;
    private TextView mTvBarCode;
    private ImageView mIvClear;

    private String mCardId;

    private String mBarCode;
    private Bitmap mBitmapBarCodeTmp;
    private String mCardFrontPhotoPath;
    private String mCardBackPhotoPath;

    private UCrop.Options mOptions;

    @Override
    protected BaseContract.Presenter getPresenter() {
        return null;
    }

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
        mIvClear = (ImageView) findViewById(R.id.iv_clear);

        setupViewListener();

        initialWithExtra();

        initUCropOptions();
    }

    private void initialWithExtra() {
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

    private void setupViewListener() {
        mEtCardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mIvClear.setVisibility(s.length() <= 0 ? View.INVISIBLE : View.VISIBLE);
            }
        });
        mIvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtCardName.setText("");
            }
        });
    }

    private void initUCropOptions() {
        if (mOptions == null) {
            mOptions = new UCrop.Options();
            int color = getResources().getColor(R.color.topBarBackground);
            mOptions.setToolbarColor(color);
            mOptions.setToolbarWidgetColor(getResources().getColor(R.color.textBlack));
            mOptions.setStatusBarColor(getResources().getColor(R.color.textBlack));
            mOptions.setLogoColor(getResources().getColor(R.color.textBlack));
        }
    }

    public void scanBarCode(View view) {
        XQrScanner.getInstance()
                .startForResult(this, REQUEST_CODE_SCAN_BAR_CODE);
    }

    public void shotCardFront(View view) {
        dispatchTakePictureIntent("front", REQUEST_CODE_TAKE_PHOTO_CARD_FRONT);
    }

    public void shotCardBack(View view) {
        dispatchTakePictureIntent("back", REQUEST_CODE_TAKE_PHOTO_CARD_BACK);
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
                case REQUEST_CODE_TAKE_PHOTO_CARD_FRONT:
                    UCrop.of(Uri.parse("file:" + mCardFrontPhotoPath), Uri.parse("file:" + mCardFrontPhotoPath))
                            .withAspectRatio(3, 2)
                            .withMaxResultSize(maxWidth, maxHeight)
                            .withOptions(mOptions)
                            .start(this, REQUEST_CODE_CROP_FRONT);
                    break;
                case REQUEST_CODE_TAKE_PHOTO_CARD_BACK:
                    UCrop.of(Uri.parse("file:" + mCardBackPhotoPath), Uri.parse("file:" + mCardBackPhotoPath))
                            .withAspectRatio(3, 2)
                            .withMaxResultSize(maxWidth, maxHeight)
                            .withOptions(mOptions)
                            .start(this, REQUEST_CODE_CROP_BACK);
                    break;
                case REQUEST_CODE_CROP_FRONT:
                    mIvCardFront.setImageURI(UCrop.getOutput(data));
                    break;
                case REQUEST_CODE_CROP_BACK:
                    mIvCardBack.setImageURI(UCrop.getOutput(data));
                    break;
                case UCrop.RESULT_ERROR:
                    final Throwable cropError = UCrop.getError(data);
                    showShortToast(cropError.getMessage());
                    break;
            }
        }
    }

    private void dispatchTakePictureIntent(String fileName, int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = FileUtil.createFile(this, fileName);
                if (photoFile == null) {
                    showShortToast("保存图片失败,请确认是否授权读写存储卡的权限以及存储卡是否正确挂载");
                    return;
                }
                switch (requestCode) {
                    case REQUEST_CODE_TAKE_PHOTO_CARD_FRONT:
                        mCardFrontPhotoPath = photoFile.getAbsolutePath();
                        break;
                    case REQUEST_CODE_TAKE_PHOTO_CARD_BACK:
                        mCardBackPhotoPath = photoFile.getAbsolutePath();
                        break;
                }
            } catch (IOException ex) {
                // Error occurred while creating the File
                showShortToast("保存图片失败,请确认是否授权读写存储卡的权限以及存储卡是否正确挂载");
            }
            // Continue only if the File was successfully created
            Uri photoURI = Uri.fromFile(photoFile);//FileProvider.getUriForFile(this,"com.wonders.xlab.cardbag.fileprovider",photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, requestCode);
        }
    }
}
