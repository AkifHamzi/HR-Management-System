package HRManagementApp.model;

import java.util.Objects;

/**
 * POJO for Designation.
 */
public class Designation {
    private long id;
    private String name;

    public Designation(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return name; // Used for display in JComboBox
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Designation that = (Designation) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toFileString(String delimiter) {
        return id + delimiter + name;
    }

    public static Designation fromString(String line, String delimiter) {
        String[] parts = line.split("\\" + delimiter);
        return new Designation(Long.parseLong(parts[0]), parts[1]);
    }
}