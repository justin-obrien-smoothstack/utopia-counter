package com.ss.training.utopia.counter.service;

import org.springframework.stereotype.Component;

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
		return Charge.create(params).getId();
	}
	
	public Refund createRefund(RefundCreateParams params)throws StripeException{
		return Refund.create(params);
	}

}
