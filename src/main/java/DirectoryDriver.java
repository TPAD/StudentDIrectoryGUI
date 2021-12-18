import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * DirectoryDriver - Swing GUI for the Directory class.
 * @author Tony Padilla (apadilla)
 */
public class DirectoryDriver extends JFrame {

    /**
     * Constant that represents the max number of columns per line in a valid
     * CSV file read by DirectoryDriver.
     */
    private static final int MAX_LEN = 4;

    /**
     * Constant that represents the GUI frame width.
     */
    private static final int FRAME_WIDTH = 720;

    /**
     * Constant that represents the GUI frame height.
     */
    private static final int FRAME_HEIGHT = 540;

    /**
     * Constant that represents the column size of the text fields in the
     * "Add a new student" section of the GUI.
     */
    private static final int COL_SMALL = 5;

    /**
     * Constant that represents the column size of the text fields in the
     * "Delete a student" and "Search student(s)" sections of the GUI.
     */
    private static final int COL_LARGE = 15;

    /**
     * Constant that represents the number of rows for the text area in the
     * "Results" section of the GUI.
     */
    private static final int TXT_AREA_R = 12;

    /**
     * Constant that represents the number of rows for the text area in the
     * "Results" section of the GUI.
     */
    private static final int TXT_AREA_C = 55;

    /**
     * Constant that represents the font size of the GUI's main labels.
     */
    private static final int FONT_SIZE = 18;

    /**
     * Reference to the text area.
     */
    private JTextArea textArea;

    /**
     * Reference to the student directory.
     */
    private Directory directory;

    /**
     * Reference to the search key text field.
     */
    private JTextField searchField;

    /**
     * Initializer for the DirectoryDriver. Lays out the GUI.
     * @param dir The student directory object.
     */
    public DirectoryDriver(Directory dir) {
        directory = dir;
        setTitle("Student Directory GUI");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JPanel addLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel deleteLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel searchLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel resultsLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel addLabel = new JLabel("   Add a new student");
        addLabel.setFont(new Font(Font.SERIF, Font.BOLD, FONT_SIZE));
        JLabel deleteLabel = new JLabel("   Delete a student");
        deleteLabel.setFont(new Font(Font.SERIF, Font.BOLD, FONT_SIZE));
        JLabel searchLabel = new JLabel("   Search student(s)");
        searchLabel.setFont(new Font(Font.SERIF, Font.BOLD, FONT_SIZE));
        JLabel resultsLabel = new JLabel("   Results");
        resultsLabel.setFont(new Font(Font.SERIF, Font.BOLD, FONT_SIZE));
        addLabelPanel.add(addLabel);
        deleteLabelPanel.add(deleteLabel);
        searchLabelPanel.add(searchLabel);
        resultsLabelPanel.add(resultsLabel);
        pane.add(addLabelPanel);
        pane.add(initAddPanel());
        pane.add(deleteLabelPanel);
        pane.add(initDeletePanel());
        pane.add(searchLabelPanel);
        pane.add(initSearchPanel());
        pane.add(resultsLabelPanel);
        pane.add(initResultsPanel());
        setContentPane(pane);
        setVisible(true);
        searchField.requestFocusInWindow();
    }

    /**
     * Initializes the JPanel for the "Add a new student" section of the GUI
     * including labels, text fields, buttons, and button actions.
     * @return a JPanel to be added to the main GUI pane.
     */
    private JPanel initAddPanel() {
        JPanel addLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField(COL_SMALL);
        addLine.add(firstNameLabel);
        addLine.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField(COL_SMALL);
        addLine.add(lastNameLabel);
        addLine.add(lastNameField);

        JLabel andrewLabel = new JLabel("Andrew ID:");
        JTextField andrewField = new JTextField(COL_SMALL);
        addLine.add(andrewLabel);
        addLine.add(andrewField);

        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField(COL_SMALL);
        addLine.add(phoneLabel);
        addLine.add(phoneField);

        JButton addButton = new JButton("Add");
        addLine.add(addButton);

        addButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String andrewId = andrewField.getText();
            String phoneNum = phoneField.getText();
            if (firstName.equals("")) {
                textArea.setText(null);
                textArea.append("First Name Missing (Add Student)\n");
            } else if (lastName.equals("")) {
                textArea.setText(null);
                textArea.append("Last Name Missing (Add Student)\n");
            } else if (andrewId.equals("")) {
                textArea.setText(null);
                textArea.append("Andrew ID Missing (Add Student)\n");
            } else {
                Student student = new Student(andrewId);
                if (directory.searchByAndrewId(andrewId) == null) {
                    student.setFirstName(firstName);
                    student.setLastName(lastName);
                    student.setPhoneNumber(phoneNum);
                    directory.addStudent(student);
                    textArea.append("New Entry Added: " + student + "\n");
                    firstNameField.setText(null);
                    lastNameField.setText(null);
                    andrewField.setText(null);
                    phoneField.setText(null);
                } else {
                    textArea.setText(null);
                    String s1  = "Data already contains an entry for ";
                    String s2 = s1 + "this Andrew ID\n";
                    textArea.setText(s2);
                }
            }
        });
        return addLine;
    }

    /**
     * Initializes the JPanel for the "Delete a student" section of the GUI
     * including labels, text fields, buttons, and button actions.
     * @return A JPanel to be added to the main GUI Pane.
     */
    private JPanel initDeletePanel() {
        JPanel deleteLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel andrewLabel = new JLabel("Andrew ID: ");
        JTextField andrewField = new JTextField(COL_LARGE);
        deleteLine.add(andrewLabel);
        deleteLine.add(andrewField);
        andrewField.setOpaque(false);

        JButton deleteButton = new JButton("Delete");
        deleteLine.add(deleteButton);
        deleteButton.addActionListener(e -> {
            String andrewId = andrewField.getText();
            if (andrewId.equals("")) {
                textArea.setText(null);
                textArea.append("Andrew ID missing (Delete Student)\n");
            } else {
                try {
                    Student student = directory.searchByAndrewId(andrewId);
                    directory.deleteStudent(andrewId);
                    textArea.append("Entry Deleted: " + student + "\n");
                    andrewField.setText(null);
                } catch (IllegalArgumentException exception) {
                    textArea.append(
                        "No match found for Andrew ID: " + andrewId + "\n");
                }
            }
        });

        return deleteLine;
    }

    /**
     * Initializes the JPanel for the "Search student(s)" section of the GUI
     * including labels, text fields, buttons, and button actions.
     * @return A JPanel to be added to the main GUI Pane.
     */
    private JPanel initSearchPanel() {
        JPanel searchLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Search Key: ");
        searchField = new JTextField(COL_LARGE);
        searchField.addActionListener(new SearchByAndrewActionListener());
        searchLine.add(searchLabel);
        searchLine.add(searchField);

        JButton andrewButton = new JButton("By Andrew ID");
        JButton fNameButton = new JButton("By First Name");
        JButton lNameButton = new JButton("By Last Name");

        searchLine.add(andrewButton);
        andrewButton.addActionListener(new SearchByAndrewActionListener());
        searchLine.add(fNameButton);
        fNameButton.addActionListener(e -> {
            String firstName = searchField.getText();
            if (firstName.equals("")) {
                textArea.setText(null);
                textArea.append("First Name Missing (Search by First Name)\n");
            } else {
                List<Student> students = directory.searchByFirstName(firstName);
                if (students.size() == 0) {
                    String s1 = "No Matches Found (Search by First Name): ";
                    String s2 = s1 + firstName + "\n";
                    textArea.append(s2);
                } else {
                    searchField.setText(null);
                    for (Student student : students) {
                        textArea.append(student.toString() + "\n");
                    }
                }
            }
        });
        searchLine.add(lNameButton);
        lNameButton.addActionListener(e -> {
            String lastName = searchField.getText();
            if (lastName.equals("")) {
                textArea.setText(null);
                textArea.append("Last Name Missing (Search by Last Name)\n");
            } else {
                List<Student> students = directory.searchByLastName(lastName);
                if (students.size() == 0) {
                    String s1 = "No Matches Found (Search by Last Name): ";
                    String s2 = s1 + lastName + "\n";
                    textArea.append(s2);
                } else {
                    searchField.setText(null);
                    for (Student student : students) {
                        textArea.append(student.toString() + "\n");
                    }
                }
            }
        });
        return searchLine;
    }

    /**
     * Initializes the JPanel for the "Results" section of the GUI.
     * @return A JPanel to be added to the main GUI Pane.
     */
    private JPanel initResultsPanel() {
        JPanel resultsPanel = new JPanel();
        textArea = new JTextArea(TXT_AREA_R, TXT_AREA_C);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JScrollPane scroller = new JScrollPane(textArea);
        resultsPanel.add(scroller);
        return resultsPanel;
    }

    /**
     * This method takes entries from the file, creates Student objects, and
     * loads them into the given Directory.
     * @param directory This is the student Directory object to load data into.
     * @param file This is the CSV file to load entries from
     * @throws FileNotFoundException if file is not found.
     * @throws IOException if data cannot be read.
     */
    private static void loadData(Directory directory, String file) throws
        FileNotFoundException, IOException {
        FileReader fr = new FileReader(file);
        CSVReader c = new CSVReader(fr);

        boolean eof = false;
        boolean first = true;
        while (!eof) {
            String[] values = c.readCSVLine();
            if (values == null) {
                eof = true;
            } else {
                if (!first) {
                    String firstName = values[0];
                    String lastName = values[1];
                    String andrewID = values[2];
                    Student student = new Student(andrewID);
                    student.setFirstName(firstName);
                    student.setLastName(lastName);
                    if (values.length == MAX_LEN) {
                        student.setPhoneNumber(values[MAX_LEN - 1]);
                    } else {
                        student.setPhoneNumber("");
                    }
                    directory.addStudent(student);
                }
                first = false;
            }
        }
        c.close();

    }

    /**
     * SearchByAndrewActionListener - Action Listener for "Search by Andrew ID".
     * This warrants its own inner class rather than the anonymous classes used
     * by other buttons because it is shared by two components, the search key
     * text field and the search by andrew id button.
     */
    private class SearchByAndrewActionListener implements ActionListener {

        /**
         * Performs the "Search By Andrew ID" functionality of the Directory.
         * @param event Action Event.
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            String andrewId = searchField.getText();
            if (andrewId.equals("")) {
                textArea.setText(null);
                textArea.append("Andrew ID Missing (Search By Andrew ID)\n");
            } else {
                Student student = directory.searchByAndrewId(andrewId);
                if (student == null) {
                    textArea.append(
                        "No Match found for Andrew Id: " + andrewId + "\n");
                } else {
                    textArea.append(student.toString() + "\n");
                    searchField.setText(null);
                }
            }
        }
    }

    /**
     * CSVReader - Simplified version of CSV Reader taken from lecture 8
     * examples.
     */
    private static class CSVReader extends BufferedReader {

        /**
         * Initializes the class.
         * @param in the reader from which to read the CSV lines
         */
        CSVReader(Reader in) {
            super(in);
        }

        /**
         * This method uses the readLine method from the superclass to get a
         * line and returns the comma separated values as an array of strings.
         * @return an array of String containing the values. At the end of file,
         * it will return null.
         * @throws IOException throws IOException.
         */
        public String[] readCSVLine() throws IOException {
            String line = super.readLine();
            if (line == null) {
                return null;
            }
            int commaCount = 0;
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == ',') {
                    commaCount++;
                }
            }
            String[] values = new String[commaCount + 1];
            int beginIndex = 0;
            for (int i = 0; i < commaCount; i++) {
                int endIndex = line.indexOf(',', beginIndex);
                if (line.charAt(beginIndex) == '"'
                    && line.charAt(endIndex - 1) == '"') {
                    values[i] = line.substring(beginIndex + 1, endIndex - 1);
                } else {
                    values[i] = line.substring(beginIndex, endIndex);
                }
                beginIndex = endIndex + 1;
            }
            if (line.charAt(beginIndex) == '"'
                && line.charAt(line.length() - 1) == '"') {
                values[commaCount] =
                    line.substring(beginIndex + 1, line.length() - 1);
            } else {
                values[commaCount] = line.substring(beginIndex, line.length());
            }

            return values;
        }
    }

    /**
     * Main method for the Directory GUI. Optionally loads the student directory
     * with data from a CSV file.
     * @param args This should be a single optional argument representing the
     * name of a CSV file.
     * @throws FileNotFoundException throws FileNotFoundException
     * @throws IOException throws IOException
     */
    public static void main(String[] args) throws FileNotFoundException,
        IOException {
        if (args.length > 1) {
            System.out.println("Usage: java DirectoryDriver <filename>");
            System.out.println("       java DirectoryDriver");
            System.exit(0);
        }
        Directory directory = new Directory();
        if (args.length == 1) {
            loadData(directory, args[0]);
        }
        new DirectoryDriver(directory);
    }

}
