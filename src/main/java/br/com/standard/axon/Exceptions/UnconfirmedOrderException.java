package br.com.standard.axon.Exceptions;

public class UnconfirmedOrderException extends Exception {

    public UnconfirmedOrderException() {
        System.out.println("Order unconfirmed, Error at: " + super.getLocalizedMessage());
    }

}
