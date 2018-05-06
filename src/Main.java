import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {

	Session session;

	public static void main(String[] args) {
		Main main = new Main();
		main.addNewData();
		main.printSchools();
		main.executeQueries();
		main.close();
	}

	public Main() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	private void executeQueries() {
		// 
        String hql = "FROM School";
        Query query = session.createQuery(hql);
        List results = query.list();
        System.out.println("zad 0: " + results);
        
        // Zad. 3.1
		String hql1 = "FROM School where name= 'AE'";
        Query query1 = session.createQuery(hql1);
        List results1 = query1.list();
        System.out.println("zad 1: " + results1);
		
		// Zad 3.2
		String tbd = "delete FROM School where name= 'AE'";
		Query query2 = session.createQuery(tbd);
        query2.executeUpdate();
        String hql2 = "FROM School";
        Query query2_2 = session.createQuery(hql2);
        List results2 = query2_2.list();
        System.out.println("zad 2: " + results2);
		
		// Zad. 3.3
		String hql3 = "select count(*) from School";
	    Query query3 = session.createQuery(hql3);
	    List results3 = query3.list();
	    System.out.println("zad 3: " + results3);
				
		// Zad. 3.4
		String hql4 = "select count(*) from Student";
	    Query query4 = session.createQuery(hql4);
	    List results4 = query4.list();
	    System.out.println("zad 4: " + results4);
		
		// Zad. 3.5
		String hql5 = "SELECT COUNT(S) FROM School S WHERE size(S.classes)>=2";
	    Query query5 = session.createQuery(hql5);
	    List results5 = query5.list();
	    System.out.println("zad 5: " + results5);
		
		// Zad. 3.6
        String hql6 = "SELECT s FROM School s INNER JOIN s.classes classes WHERE classes.profile = 'mat-fiz' and classes.currentYear >= 2";
        Query query6 = session.createQuery(hql6);
        List results6 = query6.list();
        System.out.println("zad 6: " + results6);
		
		// Zad. 4 i 5
		Query query7 = session.createQuery("from School where id= :id");
		query7.setLong("id", 3);
		School school7 = (School) query7.uniqueResult();
		school7.setAddress("Warszawa");		
		Transaction transaction7 = session.beginTransaction();
		session.update(school7);
		transaction7.commit();
		
		String hql7 = "FROM School";
		Query query7_2 = session.createQuery(hql7);
		List results7 = query7_2.list();
		System.out.println("zad 7: " + results7);
	}
	
	public void addNewData() {
		Set<Student> newStudents = new HashSet<Student>();
		Student newStudent = new Student();
		newStudent.setName("Ania");
		newStudent.setSurname("Adamowska");
		newStudent.setPesel(84040613008L);
		newStudents.add(newStudent);		
		
		Set<SchoolClass> newClasses = new HashSet<SchoolClass>();
		SchoolClass newClass = new SchoolClass();
		newClass.setProfile("humanistyczna");
		newClass.setCurrentYear(2);
		newClass.setStartYear(2016);	
		newClass.setStudents(newStudents);
		newClasses.add(newClass);
		
		School newSchool = new School();
		newSchool.setName("UJ");
		newSchool.setAddress("Krakow");
		newSchool.setClasses(newClasses);
		
		Transaction transaction = session.beginTransaction();
		session.save(newSchool);
		transaction.commit();
		
//		System.out.println("nowa " + newSchool);
	}

	public void close() {
		session.close();
		HibernateUtil.shutdown();
	}

	private void printSchools() {
		Criteria crit = session.createCriteria(School.class);
		List<School> schools = crit.list();

		System.out.println("### Schools and classes");
		for (School s : schools) {
			System.out.println(s);
			for (SchoolClass c : s.getClasses()) {
				System.out.println(c);
				System.out.println(">STUDENTS: ");
				for(Student st : c.getStudents()) {
					System.out.println(st);
				}
			}
		}
	}

	private void jdbcTest() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("org.sqlite.JDBC");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:sqlite:school.db", "", "");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM schools";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				String name = rs.getString("name");
				String address = rs.getString("address");

				// Display values
				System.out.println("Name: " + name);
				System.out.println(" address: " + address);
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}// end jdbcTest

}
