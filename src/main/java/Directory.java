import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
/**
 * Directory - A class to model a collection of students using Maps i.e. a
 * student directory.
 * @author Tony Padilla (apadilla)
 */
public final class Directory {

    /**
     * This has a student's Andrew ID as the key and maps from Andrew ID
     * to Student object.
     */
    private Map<String, Student> andrewIdMap;

    /**
     * This has a student's first name as key and maps from first name to
     * a list of Student objects.
     */
    private Map<String, List<Student>> fNameMap;

    /**
     * This has a student's last name as key and maps from last name to
     * a list of Student objects.
     */
    private Map<String, List<Student>> lNameMap;

    /**
     * This represents the number of students in the directory.
     */
    private int size;

    /**
     * This is the no arg constructor for the Directory class which initializes
     * the student directory Maps.
     */
    public Directory() {
        andrewIdMap = new HashMap<>();
        fNameMap = new HashMap<>();
        lNameMap = new HashMap<>();
        size = 0;
    }

    /**
     * Given a Student object, this will add the new student to the three maps
     * if the given student's Andrew ID is not present in the directory.
     * @param s This represents the student to add to the directory.
     * @throws IllegalArgumentException if the student's Andrew ID is already
     * present, or if student first name or last name are null.
     */
    public void addStudent(Student s) throws IllegalArgumentException {
        if (s == null) {
            throw new IllegalArgumentException("Student must be non-null");
        }
        String id = s.getAndrewId();
        String fname = s.getFirstName();
        String lname = s.getLastName();
        if (fname == null || lname == null || id == null) {
            throw new IllegalArgumentException("Missing student data");
        }
        if (andrewIdMap.containsKey(id)) {
            throw new IllegalArgumentException("Student already in directory");
        }
        Student copy = Student.duplicate(s);
        andrewIdMap.put(id, copy);
        if (!fNameMap.containsKey(fname)) {
            List<Student> students = new ArrayList<>();
            students.add(copy);
            fNameMap.put(fname, students);
        } else {
            List<Student> students = fNameMap.get(fname);
            students.add(copy);
            fNameMap.replace(fname, students);
        }
        if (!lNameMap.containsKey(lname)) {
            List<Student> students = new ArrayList<>();
            students.add(copy);
            lNameMap.put(lname, students);
        } else {
            List<Student> students = lNameMap.get(lname);
            students.add(copy);
            lNameMap.replace(lname, students);
        }
        size++;
    }

    /**
     * Given the Andrew Id string value, this method removes the corresponding
     * student from all three directory maps.
     * @param andrewId This represents the Andrew Id of the student to remove
     * @throws IllegalArgumentException if there is no Andrew Id match.
     */
    public void deleteStudent(String andrewId) throws IllegalArgumentException {
        if (andrewId == null) {
            throw new IllegalArgumentException("Andrew id must be non-null");
        }
        if (!andrewIdMap.containsKey(andrewId)) {
            throw new IllegalArgumentException("Andrew id specified not found");
        }
        Student s = andrewIdMap.get(andrewId);
        String fname = s.getFirstName();
        String lname = s.getLastName();
        andrewIdMap.remove(andrewId);
        List<Student> fnameList = fNameMap.get(fname);
        List<Student> lnameList = lNameMap.get(lname);
        if (fnameList.size() == 1) {
            fNameMap.remove(fname);
        } else {
            fnameList.remove(s);
        }
        if (lnameList.size() == 1) {
            lNameMap.remove(lname);
        } else {
            lnameList.remove(s);
        }
        size--;
    }

    /**
     * Given the Andrew Id string value, this method returns the Student with
     * the matching id in the directory.
     * @param andewId This represents a student's andrew id.
     * @return This is the student with the andrew id searched.
     * @throws IllegalArgumentException if the specified id is null.
     */
    public Student searchByAndrewId(String andewId) throws
        IllegalArgumentException {
        if (andewId == null) {
            throw new IllegalArgumentException("Andrew Id must be non-null");
        }
        Student student = andrewIdMap.get(andewId);
        if (student == null) {
            return null;
        } else {
            return Student.duplicate(student);
        }
    }

    /**
     * Given the first name string value, this method should return a list
     * containing all students that match the first name.
     * @param firstName This is the first name to match in the directory search.
     * @return A list containing all students with the given first name, or
     * an empty list if there is no match.
     * @throws IllegalArgumentException if the specified first name is null.
     */
    public List<Student> searchByFirstName(String firstName) throws
        IllegalArgumentException {
        if (firstName == null) {
            throw new IllegalArgumentException("First Name must be non-null");
        }
        if (fNameMap.containsKey(firstName)) {
            List<Student> result = new ArrayList<>();
            for (Student student : fNameMap.get(firstName)) {
                result.add(Student.duplicate(student));
            }
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Given the last name string value, this method should return a list
     * containing all students that match the last name.
     * @param lastName This is the last name to match in the directory search.
     * @return A list containing all students with the given last name, or
     * an empty list if there is no match.
     * @throws IllegalArgumentException if the specified last name is null.
     */
    public List<Student> searchByLastName(String lastName) throws
        IllegalArgumentException {
        if (lastName == null) {
            throw new IllegalArgumentException("Last name must be non-null");
        }
        if (lNameMap.containsKey(lastName)) {
            List<Student> result = new ArrayList<>();
            for (Student student : lNameMap.get(lastName)) {
                result.add(Student.duplicate(student));
            }
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * This represents the size of the directory.
     * @return The size of the directory.
     */
    public int size() {
        return size;
    }

}
