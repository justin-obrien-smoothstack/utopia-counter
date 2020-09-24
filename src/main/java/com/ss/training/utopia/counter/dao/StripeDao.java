package com.ss.training.utopia.counter.dao;

import org.springframework.stereotype.Component;

import com.ss.training.utopia.counter.Secrets;
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
public class StripeDao {

	static {
		Stripe.apiKey = Secrets.stripeKey;
	}

	public String charge(String token, long price) throws StripeException {
		return Charge.create(ChargeCreateParams.builder().setAmount(price).setCurrency("usd").setSource(token).build())
				.getId();
	}

	public void refund(String chargeId) throws StripeException {
		Refund.create(RefundCreateParams.builder().setCharge(chargeId).build());
	}

}
