package com.ss.training.utopia.counter.service;

import org.springframework.stereotype.Component;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.RefundCreateParams;

/**
 * @author Justin O'Brien
 */
@Component
public class StripeWrapper {

	public String createChargeGetId(ChargeCreateParams params) throws StripeException {
		Stripe.apiKey = "sk_test_51GwErbJwa8c7tq3Odc3WXzypPn0OPpPAd6O5gjvRBBEb15K77CX8D2XSGyyXYgbpNJ0tW52TNRY8ox0o8iKgTkqj00v7B6meHs";
		return Charge.create(params).getId();
	}

	public Refund createRefund(RefundCreateParams params) throws StripeException {
		Stripe.apiKey = "sk_test_51GwErbJwa8c7tq3Odc3WXzypPn0OPpPAd6O5gjvRBBEb15K77CX8D2XSGyyXYgbpNJ0tW52TNRY8ox0o8iKgTkqj00v7B6meHs";
		return Refund.create(params);
	}

}
