package com.bawei.yunifang.pay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.yunifang.R;

public class ExternalFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View vv=inflater.inflate(R.layout.pay_external, container, false);
		TextView product_subject= (TextView) vv.findViewById(R.id.product_subject);
		TextView product_dec= (TextView) vv.findViewById(R.id.product_dec);
		product_subject.setText(PayDemoActivity.name);
		product_dec.setText(PayDemoActivity.dec);
		return vv;
	}
}
