package quickstart;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DBQ on 2017/2/22.
 * There are two ways that Morphia can handle your classes:
 * as top level entities or embedded in others.
 * Any class annotated with @Entity is treated as a top level document stored directly in a collection.
 * Any class with @Entity must have a field annotated with @Id to define which field to use as the _id value in the document written to MongoDB.
 * @Embedded indicates that the class will result in a subdocument inside another document.
 * @Embedded classes do not require the presence of an @Id field.
 */
@Entity("employees")
@Indexes(
        @Index(value = "salary", fields = @Field("salary"))
)
public class Employee {
    /*
    We’ve marked the id field to be used as our primary key (the _id field in the document).
    In this instance we’re using the Java driver type of ObjectId as the ID type.
    The ID can be any type you’d like but is generally something like ObjectId or Long.
    There are two other annotations to cover but it should be pointed out now that other than transient and static fields,
    Morphia will attempt to copy every field to a document bound for the database.
    */
    @Id
    private ObjectId id;
    private String name;

    /*
    @Reference.
    This annotation is telling Morphia that this field refers to other Morphia mapped entities.
    In this case Morphia will store what MongoDB calls a DBRef which is just a collection name and key value.
    These referenced entities must already be saved or at least have an ID assigned or Morphia will throw an exception.
    */
    @Reference
    private Employee manager;
    @Reference
    private List<Employee> directReports = new ArrayList<>();

    /*
     @Property
     This annotation is entirely optional.
     If you leave this annotation off, Morphia will use the Java field name as the document field name.
     Often times this is fine. However, some times you’ll want to change the document field name for any number of reasons.
     In those cases, you can use @Property and pass it the name to be used when this class is serialized out to a document to be handed off to MongoDB.
    */
    @Property("wage")
    private Double salary;

    public Employee() {
    }

    public Employee(String name, Double salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manager=" + manager +
                ", directReports=" + directReports +
                ", salary=" + salary +
                '}';
    }

    public List<Employee> getDirectReports() {
        return directReports;
    }

    public void setDirectReports(final List<Employee> directReports) {
        this.directReports = directReports;
    }

    public ObjectId getId() {
        return id;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(final Employee manager) {
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(final Double salary) {
        this.salary = salary;
    }
}