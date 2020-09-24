package com.ss.training.utopia.counter.dao;

import org.springframework.stereotype.Component;

import com.ss.training.utopia.counter.Secrets;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;

/**
 * @author Justin O'Brien
 */
@Component
public class StripeDao {

	static {
		Stripe.apiKey = Secrets.stripeKey;
	}

	public void refund(String stripeId) throws StripeException {
		Refund.create(RefundCreateParams.builder().setCharge(stripeId).build());
	}

}
