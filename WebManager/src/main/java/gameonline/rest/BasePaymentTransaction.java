package gameonline.rest;

public abstract class BasePaymentTransaction extends BaseAuthorization {
	protected static final String CLIENT_ID = "Aem4MF00BIM2kzLVzLYTbptnw_1t37lXs_-hB7CR_xWJyjQ58uvDe_KpRygPrRSYPXhsiK6jmZXEYqTM";
	protected static final String CLIENT_SECRET = "EOEUB3X-gzyd1IVH541fscTNuqiOQIIbpWwH2oRhZxdF5jKUPd7r1rVaGqBMHSbft-wPqV4Tc4v0pLK7";
	protected static final String MODE = "sandbox";
	

	@Override
	protected MyRespone respone() {
		return executeTransaction();
	}

	protected abstract MyRespone executeTransaction();

}
