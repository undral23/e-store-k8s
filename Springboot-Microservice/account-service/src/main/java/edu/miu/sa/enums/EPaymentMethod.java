package edu.miu.sa.enums;

public enum EPaymentMethod {
	PayPal("PP"), CreditCard("CC"), BankAccount("BA");

	private final String method;

	EPaymentMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return method;
	}
}
