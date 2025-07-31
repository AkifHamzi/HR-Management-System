package HRManagementApp.model;

/**
 * POJO for Employee.
 */
public class Employee {
    private long id;
    private String name;
    private String address;
    private String contact;
    private long departmentId;
    private long designationId;

    public Employee(long id, String name, String address, String contact, long departmentId, long designationId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.departmentId = departmentId;
        this.designationId = designationId;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getContact() { return contact; }
    public long getDepartmentId() { return departmentId; }
    public long getDesignationId() { return designationId; }

    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setContact(String contact) { this.contact = contact; }
    public void setDepartmentId(long departmentId) { this.departmentId = departmentId; }
    public void setDesignationId(long designationId) { this.designationId = designationId; }


    public String toFileString(String delimiter) {
        return id + delimiter + name + delimiter + address + delimiter + contact + delimiter + departmentId + delimiter + designationId;
    }

    public static Employee fromString(String line, String delimiter) {
        String[] parts = line.split("\\" + delimiter);
        return new Employee(
                Long.parseLong(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                Long.parseLong(parts[4]),
                Long.parseLong(parts[5])
        );
    }
}