package quickstart;

import com.mongodb.MongoClient;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.util.List;

/**
 * Created by DBQ on 2017/2/22.
 */
public class QuickStart {

    private Datastore datastore;

    @Before
    public void initMorphia() {
        System.out.println("Init MongoDB.");
        Morphia morphia = new Morphia();
        morphia.mapPackage("quickstart");
        // create the DataStore connecting to the default port on the local host
        datastore = morphia.createDatastore(new MongoClient("localhost", 27017), "morphia_example");
        datastore.ensureIndexes();
    }

    @Test
    public void insert() {
        final Employee elmer = new Employee("Elmer Fudd", 50000.0);
        datastore.save(elmer);
        final Employee daffy = new Employee("Daffy Duck", 40000.0);
        datastore.save(daffy);
        final Employee pepe = new Employee("Pepé Le Pew", 25000.0);
        datastore.save(pepe);
        elmer.getDirectReports().add(daffy);
        elmer.getDirectReports().add(pepe);
        datastore.save(elmer);
    }

    @Test
    public void query1() {
        final Query<Employee> query = datastore.createQuery(Employee.class);
        final List<Employee> employeeList1 = query.asList();
        System.out.println(employeeList1);
    }

    @Test
    public void query2() {
        List<Employee> employeeList2 = datastore.createQuery(Employee.class)
                .field("salary").lessThanOrEq(30000)
                .asList();
        System.out.println(employeeList2);
    }

    @Test
    public void query3() {
        List<Employee> underpaid = datastore.createQuery(Employee.class)
                .filter("salary <=", 30000)
                .asList();
        System.out.println(underpaid);
    }

    @Test
    public void update() {
        final Query<Employee> underPaidQuery = datastore.createQuery(Employee.class)
                .filter("salary <=", 30000);
        final UpdateOperations<Employee> updateOperations = datastore.createUpdateOperations(Employee.class)
                .inc("salary", 10000);
        final UpdateResults results = datastore.update(underPaidQuery, updateOperations);
        System.out.println(results);
    }

    @Test
    public void delete1() {
        final Query<Employee> overPaidQuery = datastore.createQuery(Employee.class)
                .filter("salary >", 40000);
        datastore.delete(overPaidQuery);
    }

    @Test
    public void delete2(){
        final Query<Employee> queryAll = datastore.createQuery(Employee.class);
        datastore.delete(queryAll);
    }
}
