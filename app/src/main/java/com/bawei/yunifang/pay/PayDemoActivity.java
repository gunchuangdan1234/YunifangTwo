package com.bawei.yunifang.pay;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.bawei.yunifang.R;


public class PayDemoActivity extends FragmentActivity {

	// 商户PID
	public static final String PARTNER = "2088901305856832";
	// 商户收款账号
	public static final String SELLER = "8@qdbaiu.com";
	// 商户私钥，pkcs8格式
	//public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALHM4EPr7ovEgJbbBt4GCHWPoTjghK6iabEFjc5phhaKpGS1qD/7RciaK7wXYzLNLWOCVIW++JUap+4BXn4nV/sUGV204bmWaWP8O2upNjRvVkQMzBclbH0FZGcn3cIryKiuxQcaXXxuyv2JMQKF64NR2n0SM396fsMUBIprXtAgMBAAECgYEAhOMRQvKVSdVGHnn5OfvcWrFM2EvGHxe4UtxJzlpEI5jfoX28EIXm9DV2NpBsTc3X/KWuMoA3HonbtSFE1JjnQbukUmqXPbxm3IkMT+dbKxDYn4kfblkHHlTdxWiYq2zyu0Om7UvS0dvZJQCwZoIc1BEGs3N0zA98+TxoZ2JEECQQDjEDs20D9zEIABdb0zGtu+L0qLP6NKIofkIe21DFFEwX+a3UQqYqb3ofvDoBEPoO+Kd+tWWl24EIUgDFK/uZVAkEAyHV5S/LmOU48sagvj+odYdwk5shB3FFc8uiWDqat3k3BTgAoTtxLWD980F1BH6M1oNHSlbusqzxf/Puaq5OQJAA+MpQFz8WRn1NxEu9gsFrBHfMtcaEpDYxr0V9r2JP5iLx7lLPcfJSbXmeojpAd19fF9lNgtCA3606MGNQR8KQJBAK70kKQv4K3dUe3AhtB+INpLFn9NorsKnHRJeUZaRV/Dv1YDnyqM7Jmd96Kn8YHaYaD14HTc8uwU6rYSyEVF0kCQDLPd24B+gjGxSSU2a5OdbzrWcE2cjIF0RyNiMErqcvdjCNtHZAmZBSsJTwmcECoHMUa2ZCOD1G//sxWZOFrM=";
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM/KCxg/OIj6er2GEig0DOkHqBSzEPHGigMbTXP1k2nrxEHeE59xOOuyovQH/A1LgbuVKyOac3uAN4GXIBEoozRVzDhu5dobeNm48BPcpYSAfvN3K/5GLacvJeENqsiBx8KufM/9inlHaDRQV7WhX1Oe2ckat1EkdHwxxQgc36NhAgMBAAECgYEAwn3sWpq6cUR65LD8h9MIjopTImTlpFjgz72bhsHDZK6A+eJDXcddrwh7DI34t/0IBqu+QEoOc/f0fIEXS9hMwTvFY59XG7M8M6SdeaAbemrGmZ1IdD6YDmpbQFHn2ishaYF0YDZIkBS3WLDFrtk/efaarBCpGAVXeEiVQE4LewECQQD5W1rpkq+dHDRzzdtdi1bJ479wun5CfmVDVb2CJH7Iz0t8zyp/iEVV2QELnxZMphmoSzKaLXutTTj2OImpzCtRAkEA1VMxG6nQq9NkU51H1+I3mlUXRZ0XeFA1GFJ7xWpNRAVhEWlDz2zy9v/gX+RFpNC3f5uznycas70Xp78ew43TEQJAZFFqi9mlqTF1sLk67bFnIyXrGPEOZrXvC13tNfR0xVkQZ4/46wHp0xXQo9pG4GNaoyhNnVV7EkelCPnJ+HPZYQJAQh6T9QgQZoGR8hyovPAf3dUL7oa/VIo/urcuJ8VIB5JHQNdIrk0NjaNHj1E4iNosVgATj3vWWel9IIArb99QkQJAKvfm78lwnImtg5IM604hdn/Wu1XF8tpxsKLWcnfchMr0bM9rCmKmhAY+wdmqSyPZRiNb1QaaaDTqJxLy6AnQ+Q==";
	//public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANO3X4W7A7xuI+Nvw9o/NYRyNNy4MrcRl6x/MPT+mlsBDia0X2SPII071PlwC96WR1XZPrVv2XExGiVvzeEG+5+eKcIrt8pGJDjkLgBF0QrLXvA9Js9u2lYfICPGlBdJUyWbR40mDfLMPAdPb+2rqKWeaDsiZwu0J83vguHkp/AgMBAAECgYACFJc3tTLAM0TfA0YlauGXv5eY7H74oxfAZD1l1rk8gm1j6gwUMe9G41oNhWnxkpFKbFYDxVP62+aVnSWkxHDLZ4IrShBDyrLEf9eG28dtpEOAnPUNCSM0qfIKQCVcZaIzaQyzlFjHCEQqxw14bd1rVZyLT1JrlFDKTUlojoQJBAPULnCwPmv8uMKf7DwcSLGhJ2Jf1ZERfxyqYqg+uE7JyLkr9WpaxYcAEgvkNPq7YKrsF2CRAzUSfy6fKK6FkGMCQQDdLlVDrBjjSGyTXublW77C8ILkcHbMRfmAq1zC6Z92gWgjXrDDwcNTsz/WuNrl6co3qjCC0mev/HHOztAOI1AkEAmyLtjYuXsmR403CDtBNSX/gYkyP9hbgpbJNYXzLzL3ftCSaGaf3rvitQYsJ649DhRKRPkawhCIguNB0vgC7RwJAKyiDUrUeFhtntEnsWGL4SNw+fOtwHEkFJmpJq27Swtj1bRFpBImmh4qOhmODsyEvaDU5Sg2sVda4jlYkpKNkQJAFPS33VKCuCaPs0QlfMDlsLtBRryT20DrMyhPma+Y35ZMAWu50nazKXzTfqkWDEWSztYAyTxCfs6xCI6sQw9A==";
//	  public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKc5GE5gFxlG+VFa" +
//			"Mr+AI5xfopUGHmiimDy/FOQpcyt4PhQ9HaXOqGpPqicmFpYh98Rp0CjelQ7merY2" +
//			"eKbJ/8YrDWqIfK5Ztp8+bMwqGNw1kLZX+VnVoHNKV7fIODuu/3HPcJlO33dMLT7i" +
//			"TNxP2vmgwBZtul40Nn3wfjmkS53BAgMBAAECgYEAjd2LOFqe3gvvUcwSx5H/JBei" +
//			"R4lEXQKr4sU9BQABO/yrteHPeC4lhOOG9+WBPecdWt1mAnYqwNRZgKIfo2g1e1qO" +
//			"UTnPt9BYgzQkEhE9uIz9s4U4WjBOWrrjP83jHtv+OClWuu83nttvmtXW6vdc0N3g" +
//			"LkLLz0VHVOt9Kq1idkECQQDdHminZYsWeA1L32S6H++F80r9snc102N/HBWEqE0V" +
//			"Spm5ROB1lx6B+6JFRHiyV6h1BRLgJguNdvPh3fZ4d9YZAkEAwZotSMp56Z5He2vl" +
//			"meHwuuBJZBW44d8km/gIkYUI2qtmVkKKSz5YgjmTfXPL/bo8v0O0PN5Y9FICSI7k" +
//			"0eXp6QJAD6iUnq4ZMGkl/+Z8+pVCYAj/mYGm4X/rvtedSnykoMpconsXLhyag3fv" +
//			"YNpddbuxaUAHuynWjZkL4kXWq2dJGQJAa5dIFUzY5Zjdttxmk9c5llEw2BsuTPBa" +
//			"gwcbk+tv2T4puERwYJJ8U5q1uaFRu2b1fYGdt7oFi3nL5RDsXy27iQJAJhyS/hxO" +
//			"UGYeiF7nClFb1iYrl+mKsXd059w2douQLl0yHENzcsY3Gk8hDBBJwX6utgAMMDph" +
//			"/cwp6mqPH007mw==";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	private static final int SDK_PAY_FLAG = 1;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayDemoActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			default:
				break;
			}
		};
	};
	public static String name;
	public static String dec;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//接收传来的值
		Intent inn=getIntent();
		name = inn.getStringExtra("name");
		dec = inn.getStringExtra("dec");
		setContentView(R.layout.pay_main);
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(View v) {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
							//
							finish();
						}
					}).show();
			return;
		}
		String orderInfo = getOrderInfo("孙梦龙", dec, "0.01");

		/**
		 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
		 */
		String sign = sign(orderInfo);
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/**
		 * 完整的符合支付宝参数规范的订单信息
		 */
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(PayDemoActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo, true);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
	 * 
	 * @param v
	 */
	public void h5Pay(View v) {
		Intent intent = new Intent(this, H5PayDemoActivity.class);
		Bundle extras = new Bundle();
		/**
		 * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
		 * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
		 * 商户可以根据自己的需求来实现
		 */
		String url = "http://m.taobao.com";
		// url可以是一号店或者淘宝等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
		extras.putString("url", url);
		intent.putExtras(extras);
		startActivity(intent);
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	private String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

}
