/**
 * Copyright
 */
package foo.bar.model;

public class Case {

    private Customer customer;
    private int hourOfDay;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(final Customer customer) {
        this.customer = customer;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(final int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }
}
