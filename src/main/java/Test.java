import java.io.File;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.jbdl.library.entity.CardEntity;
import com.jbdl.library.entity.StudentEntity;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Configuration conn = new Configuration();
		SessionFactory sf = conn.configure(new File("src/main/java/resources/hibernate.cfg.xml")).buildSessionFactory();
		Session session = sf.openSession();
		Transaction transaction = session.beginTransaction();
		
		StudentEntity se = new StudentEntity(
				23,
				"Dheeraj",
				"india",
				"dheeraj123@gmail.com",
				123123123,
				new Date(),
				null);
		CardEntity ce = new CardEntity(
				true,
				"dheeraj@gmail.com",
				new Date(),
				new Date(),
				null,
				se);
		se.setStudentCard(ce);
		session.save(se);
		session.save(ce);
		
		
		session.flush();
		transaction.commit();
		session.close();
	}

}
