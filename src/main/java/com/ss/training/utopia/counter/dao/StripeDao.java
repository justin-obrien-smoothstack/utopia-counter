package com.ss.training.utopia.counter.dao;

import org.springframework.stereotype.Component;
import com.stripe.Stripe;
import com.ss.training.utopia.counter.Secrets;

/**
 * @author Justin O'Brien
 */
@Component
public class StripeDao {
	static {
		Stripe.apiKey = Secrets.stripeKey;
	}
}
