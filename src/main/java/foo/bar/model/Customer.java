/**
 * Copyright
 */
package foo.bar.model;

public class Customer {

    private String name;
    private MaritalStatus maritalStatus;
    private Gender gender;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(final MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(final Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer{" + "name='" + name + '\'' + ", maritalStatus=" + maritalStatus + ", gender=" + gender
                + ", age=" + age + '}';
    }
}
