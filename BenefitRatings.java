/**
 * 
 */
package ie.bil.projections.services.calculation.unitlinked.benefit;

import ie.bil.projections.services.calculation.unitlinked.core.EngineException;
import ie.bil.projections.services.calculation.unitlinked.enumerations.Enums.EnumBenefitRating;
import ie.bil.projections.services.calculation.unitlinked.helper.QuoteConst;

/**
 * @author Seamus Young
 * 
 *         Holds benefit rating information.
 */
public class BenefitRatings {
	/* Additive to charge amount */
	private double life1ChargeAdd;
	private double life2ChargeAdd;

	/* Multiply by charge amount */
	private double life1ChargeMultiply;
	private double life2ChargeMultiply;

	/* Add to age benefit rate */
	private int life1AddToAge;
	private int life2AddToAge;

	/* Add to rate */
	private double life1RateAdd;
	private double life2RateAdd;

	/* Multiply by rate */
	private double life1RateMultiply;
	private double life2RateMultiply;

	/* Monthly factor for PerMille */
	private double life1RateAddMonthlyEquivalent;
	private double life2RateAddMonthlyEquivalent;

	/* "Per mille" divisor */
	private double life1PerMilleDivisor;
	private double life2PerMilleDivisor;

	/* "Per mille" term in months */
	private int life1PerMilleTerm;
	private int life2PerMilleTerm;

	/**
	 * Copies the values from the supplied BenefitRating class to this one.
	 * 
	 * @param fromAnother
	 *            the class to copy the detail from.
	 */
	void copy(BenefitRatings fromAnother) {

		this.life1ChargeAdd = fromAnother.life1ChargeAdd;
		life1ChargeMultiply = fromAnother.life1ChargeMultiply;
		life1AddToAge = fromAnother.life1AddToAge;
		life1RateAdd = fromAnother.life1RateAdd;
		life1RateMultiply = fromAnother.life1RateMultiply;
	}

	/**
	 * Adds a rating of the specified type and value.
	 * 
	 * @param ratingType
	 *            The type of rating
	 * @param amount
	 *            The value of the rating.
	 */
	public void addLife1Rating(EnumBenefitRating ratingType, double amount) {

		switch (ratingType) {
		case ADDITIVE_TO_CHARGE:
			life1ChargeAdd = life1ChargeAdd + amount;

			break;
		case MULTIPLICATIVE_ON_CHARGE: {
			if (amount == QuoteConst.OneHundredPercent) {
				// No rating
				return;
			}

			if (amount > QuoteConst.OneHundredPercent) {
				amount = amount - QuoteConst.OneHundredPercent;
				life1ChargeMultiply = life1ChargeMultiply + (amount / QuoteConst.OneHundredPercent);
			} else {
				// E.g. 90 returns a 0.9 (reduction in cost)
				amount = amount - QuoteConst.OneHundredPercent;
				if (life1ChargeMultiply != 1) {
					life1ChargeMultiply = life1ChargeMultiply + amount / QuoteConst.OneHundredPercent;
				} else {
					life1ChargeMultiply = amount / QuoteConst.OneHundredPercent;
				}
			}
		}
			break;
		case ADDITIVE_TO_AGE:
			life1AddToAge = life1AddToAge + (int) Math.round(amount);

			break;
		case ADDITIVE_TO_RATE:
			life1RateAdd = life1RateAdd + amount;

			break;
		case MULTIPLICATIVE_ON_RATE: {
			if (amount == QuoteConst.OneHundredPercent) {
				// No rating
				return;
			}

			if (amount > QuoteConst.OneHundredPercent) {
				amount = amount - QuoteConst.OneHundredPercent;
				life1RateMultiply = life1RateMultiply + (amount / QuoteConst.OneHundredPercent);
			} else {
				// E.g. 90 returns a 0.9 (reduction in cost)
				amount = amount - QuoteConst.OneHundredPercent;
				if (life1RateMultiply != 1) {
					life1RateMultiply = life1RateMultiply + amount / QuoteConst.OneHundredPercent;
				} else {
					life1RateMultiply = amount / QuoteConst.OneHundredPercent;
				}
			}
		}
			break;
		default:
			throw new RuntimeException(EngineException.InvalidRating);
		}
	}
	
	public void addLife2Rating(EnumBenefitRating ratingType, double amount) {

		switch (ratingType) {
		case ADDITIVE_TO_CHARGE:
			life2ChargeAdd = life2ChargeAdd + amount;

			break;
		case MULTIPLICATIVE_ON_CHARGE: {
			if (amount == QuoteConst.OneHundredPercent) {
				// No rating
				return;
			}

			if (amount > QuoteConst.OneHundredPercent) {
				amount = amount - QuoteConst.OneHundredPercent;
				life2ChargeMultiply = life2ChargeMultiply + (amount / QuoteConst.OneHundredPercent);
			} else {
				// E.g. 90 returns a 0.9 (reduction in cost)
				amount = amount - QuoteConst.OneHundredPercent;
				if (life2ChargeMultiply != 1) {
					life2ChargeMultiply = life2ChargeMultiply + amount / QuoteConst.OneHundredPercent;
				} else {
					life2ChargeMultiply = amount / QuoteConst.OneHundredPercent;
				}
			}
		}
			break;
		case ADDITIVE_TO_AGE:
			life2AddToAge = life2AddToAge + (int) Math.round(amount);

			break;
		case ADDITIVE_TO_RATE:
			life2RateAdd = life2RateAdd + amount;

			break;
		case MULTIPLICATIVE_ON_RATE: {
			if (amount == QuoteConst.OneHundredPercent) {
				// No rating
				return;
			}

			if (amount > QuoteConst.OneHundredPercent) {
				amount = amount - QuoteConst.OneHundredPercent;
				life2RateMultiply = life2RateMultiply + (amount / QuoteConst.OneHundredPercent);
			} else {
				// E.g. 90 returns a 0.9 (reduction in cost)
				amount = amount - QuoteConst.OneHundredPercent;
				if (life2RateMultiply != 1) {
					life2RateMultiply = life2RateMultiply + amount / QuoteConst.OneHundredPercent;
				} else {
					life2RateMultiply = amount / QuoteConst.OneHundredPercent;
				}
			}
		}
			break;
		default:
			throw new RuntimeException(EngineException.InvalidRating);
		}
	}


	/**
	 * Gets the benefit rating amount for the specified rating type
	 * 
	 * @param ratingType
	 * @return
	 */
	public double getLife1Rating(EnumBenefitRating ratingType) {

		switch (ratingType) {
		case ADDITIVE_TO_CHARGE:
			return life1ChargeAdd;
		case MULTIPLICATIVE_ON_CHARGE:
			return getLife1ChargeMultiply();
		case ADDITIVE_TO_AGE:
			return life1AddToAge;
		case ADDITIVE_TO_RATE:
			return life1RateAdd;
		case MULTIPLICATIVE_ON_RATE:
			return getLife1RateMultiply();
		default:
			throw new RuntimeException(EngineException.InvalidRating);
		}

	}
	
	public double getLife2Rating(EnumBenefitRating ratingType) {

		switch (ratingType) {
		case ADDITIVE_TO_CHARGE:
			return life2ChargeAdd;
		case MULTIPLICATIVE_ON_CHARGE:
			return getLife2ChargeMultiply();
		case ADDITIVE_TO_AGE:
			return life2AddToAge;
		case ADDITIVE_TO_RATE:
			return life2RateAdd;
		case MULTIPLICATIVE_ON_RATE:
			return getLife2RateMultiply();
		default:
			throw new RuntimeException(EngineException.InvalidRating);
		}

	}

	/**
	 * Gets the Charge Multiply rating value
	 */
	public double getLife1ChargeMultiply() {

		if (life1ChargeMultiply == 0) {
			return 1;
		}

		// If the charge is less than zero this means that the overall rate will
		// be
		// reduced. The maximum reduction is zero.
		if (life1ChargeMultiply < 0) {
			double value = Math.abs(life1ChargeMultiply);
			if (value < 1) {
				return 1 - value;
			}
			return 0;
		}
		return 1 + life1ChargeMultiply;
	}
	
	public double getLife2ChargeMultiply() {

		if (life2ChargeMultiply == 0) {
			return 1;
		}

		// If the charge is less than zero this means that the overall rate will
		// be
		// reduced. The maximum reduction is zero.
		if (life2ChargeMultiply < 0) {
			double value = Math.abs(life2ChargeMultiply);
			if (value < 1) {
				return 1 - value;
			}
			return 0;
		}
		return 1 + life2ChargeMultiply;
	}

	/**
	 * Gets the "Rate Multiply" rating value.
	 */
	public double getLife1RateMultiply() {
		double value; // Rating value

		if (life1RateMultiply == 0) {
			return 1;
		}

		if (life1RateMultiply < 0) {
			value = Math.abs(life1RateMultiply);
			if (value < 1) {
				return 1 - value;
			}
			return 0;
		}
		return 1 + life1RateMultiply;

	}
	
	public double getLife2RateMultiply() {
		double value; // Rating value

		if (life2RateMultiply == 0) {
			return 1;
		}

		if (life2RateMultiply < 0) {
			value = Math.abs(life2RateMultiply);
			if (value < 1) {
				return 1 - value;
			}
			return 0;
		}
		return 1 + life2RateMultiply;

	}

	/**
	 * Gets the "Charge Add" rating value.
	 */
	public double getLife1ChargeAdd() {
		return life1ChargeAdd;
	}
	
	public double getLife2ChargeAdd() {
		return life2ChargeAdd;
	}

	/**
	 * Gets the "Rate Add" rating value.
	 */
	public double getLife1RateAdd() {
		return life1RateAdd / life1PerMilleDivisor * life1RateAddMonthlyEquivalent;
	}
	
	public double getLife2RateAdd() {
		return life2RateAdd / life2PerMilleDivisor * life2RateAddMonthlyEquivalent;
	}

	/**
	 * Gets the input "Rate Add" rating value.
	 */
	public double getLife1InputRateAdd() {
		return life1RateAdd;
	}
	
	public double getLife2InputRateAdd() {
		return life2RateAdd;
	}
	
	/**
	 * Gets the "Add to Age" rating value.
	 */
	public int getLife1AddToAge() {
		return life1AddToAge;
	}
	
	public int getLife2AddToAge() {
		return life1AddToAge;
	}

	/**
	 * Sets the per mille divisor.
	 * 
	 * @param perMilleDivisor
	 *            the new per mille divisor
	 */
	public void setLife1PerMilleDivisor(double newValue) {
		life1PerMilleDivisor = newValue;
		life2PerMilleDivisor = newValue;
	}
	
	public void setLife2PerMilleDivisor(double newValue) {
		life1PerMilleDivisor = newValue;
		life2PerMilleDivisor = newValue;
	}

	/**
	 * Sets the rate add monthly equivalent.
	 * 
	 * @param rate
	 *            the new rate add monthly equivalent
	 */
	public void setLife1RateAddMonthlyEquivalent(double newValue) {
		life1RateAddMonthlyEquivalent = newValue;
	}
	
	public void setLife2RateAddMonthlyEquivalent(double newValue) {
		life2RateAddMonthlyEquivalent = newValue;
	}


	/**
	 * Sets the per mille rating term in months. This is really only used by the output routines.
	 * @param perMilleTerm
	 */
	public void setLife1PerMilleTerm(int perMilleTerm) {
		this.life1PerMilleTerm = perMilleTerm; 
	}
	
	public void setLife2PerMilleTerm(int perMilleTerm) {
		this.life2PerMilleTerm = perMilleTerm; 
	}

	/**
	 * Returns the per mille term in months. 
	 */
	public int getLife1PerMilleTerm() {
		return life1PerMilleTerm; 
		
	}
	
	public int getLife2PerMilleTerm() {
		return life2PerMilleTerm; 
		
	}
	
}
