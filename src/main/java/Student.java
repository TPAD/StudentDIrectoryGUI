/**
 * Student - This is a class to model a single student including first name,
 * last name, andrew id, and phone number.
 * @author Tony Padilla (apadilla)
 */
public final class Student {

    /**
     * This is a prime number use din hashCode method.
     */
    private static final int HASH = 31;

    /**
     * This represents a Student's first name.
     */
    private String firstName;

    /**
     * This represents a Student's last name.
     */
    private String lastName;

    /**
     * This represents a Student's andrew Id.
     */
    private String id;

    /**
     * This represents a student's phone number.
     */
    private String phoneNum;

    /**
     * This is the one argument constructor for the Student class.
     * @param andrewId This represents the student's andrew id.
     */
    public Student(String andrewId) {
        id = andrewId;
    }

    /**
     * This is the getter for a Student instance's andrew id.
     * @return This gives the student's andrew id.
     */
    public String getAndrewId() {
        return id;
    }

    /**
     * This is the getter for a Student instance's first name.
     * @return This gives the student's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This is the getter for a Student instance's last name.
     * @return This gives the student's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * This is the getter for a Student instance's phone number.
     * @return This gives the student's phone number.
     */
    public String getPhoneNumber() {
        return phoneNum;
    }

    /**
     * This is the setter for a Student instance's first name.
     * @param s This represents the student's first name.
     */
    public void setFirstName(String s) {
        firstName = s;
    }

    /**
     * This is the setter for a Student instance's last name.
     * @param s This represents the student's last name.
     */
    public void setLastName(String s) {
        lastName = s;
    }

    /**
     * This is the setter for a Student instance's phone number.
     * @param s This represents the student's number formatted '###-###-####'
     */
    public void setPhoneNumber(String s) {
        phoneNum = s;
    }

    @Override
    public String toString() {
        return String.format("%s %s (Andrew ID: %s, Phone Number: %s)",
            firstName, lastName, id, phoneNum);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof  Student)) {
            return false;
        }
        Student s = (Student) o;
        return s.phoneNum.equals(phoneNum) && s.firstName.equals(firstName)
            && s.lastName.equals(lastName) && s.id.equals(id);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = HASH * result + firstName.hashCode();
        result = HASH * result + lastName.hashCode();
        result = HASH * result + phoneNum.hashCode();
        return  result;

    }

    /**
     * This method creates a copy of a Student object.
     * @param s This represents the Student object to copy.
     * @return This is a new Student object identical to the one given.
     */
    public static Student duplicate(Student s) {
        Student result = new Student(s.getAndrewId());
        result.setFirstName(s.getFirstName());
        result.setLastName(s.getLastName());
        result.setPhoneNumber(s.getPhoneNumber());
        return result;
    }

}
