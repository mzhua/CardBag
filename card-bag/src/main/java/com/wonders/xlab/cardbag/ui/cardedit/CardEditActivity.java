package com.wonders.xlab.cardbag.ui.cardedit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.util.ImageViewUtil;
import com.wonders.xlab.cardbag.widget.RatioImageView;
import com.wonders.xlab.cardbag.widget.TopBar;

public class CardEditActivity extends Activity {
    private TopBar mTopBar;
    private RatioImageView mIvCard;
    private RatioImageView mIvCardFront;
    private RatioImageView mIvCardBack;
    private RatioImageView mIvBarCode;
    private EditText mEtCardName;

    private String mCardId;

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

        Intent intent = getIntent();
        if (intent != null) {
            mCardId = intent.getStringExtra("id");
            String cardName = intent.getStringExtra("cardName");
            String cardImageUrl = intent.getStringExtra("cardImageUrl");
            mEtCardName.setText(cardName);
            ImageViewUtil.load(this, cardImageUrl, mIvCard);
        }

        mTopBar.setTitle(TextUtils.isEmpty(mCardId) ? getString(R.string.title_card_edit_add) : getString(R.string.title_card_edit_edit));
    }
}
