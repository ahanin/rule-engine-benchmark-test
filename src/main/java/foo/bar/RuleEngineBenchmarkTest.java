/**
 * Copyright
 */
package foo.bar;

import foo.bar.drools.DroolsBenchmarkTest;
import foo.bar.model.Case;
import foo.bar.model.Country;
import foo.bar.model.Customer;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;

public class RuleEngineBenchmarkTest {

    private static final BenchmarkTest[] TESTS = new BenchmarkTest[]{
            new DroolsBenchmarkTest()
    };

    public static void main(String[] args) {
        final int numCases = 1000;

        final ArrayList<Case> cases = new ArrayList<Case>(numCases);
        for (int i = 0; i < numCases; i++) {
            final Case aCase = new Case();
            final Customer customer = new Customer();
            customer.setName("John Doe");
            customer.setCountry(Country.DE);
            customer.setAge(20 + i % 10);
            aCase.setCustomer(customer);
            cases.add(aCase);
        }

        System.out.println(new Date());
        for (BenchmarkTest test : TESTS) {
            final long start = System.nanoTime();

            test.process(cases);

            final long finish = System.nanoTime();

            System.out.println(MessageFormat.format(
                    "{0}: {1} case/s", test.getName(),
                    (double) cases.size() / ((double) (finish - start) / Math.pow(10d, 9) * 1000))
            );
        }
        System.out.println(new Date());
    }

}
