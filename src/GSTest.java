import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GSTest {

	public GradeSys gradeSys =null;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		gradeSys = new GradeSys();
		Scanner scanner = new Scanner(new File("src/simple.txt"));
		while(scanner.hasNext()) {
			String studentData= scanner.nextLine();
			StudentsGrade studentsGrade = new StudentsGrade(studentData.split(" "));
			gradeSys.add(studentsGrade);
		}
		scanner.close();
	}

	@AfterEach
	void tearDown() throws Exception {
		gradeSys = null;
	}
	
	@Test
	void testAdd() throws Exception{
		String newInput = "104062104 吳宗欣 100 100 100 100 100";
		StudentsGrade newCase = new StudentsGrade(newInput.split(" "));
		assertEquals(false, gradeSys.inList(newCase.getID()));
		gradeSys.add(newCase);
		assertEquals(true, gradeSys.inList(newCase.getID()));
	}
	
	@Test
	void testInList() throws Exception{
		Scanner scanner = new Scanner(new File("src/simple.txt"));
		while(scanner.hasNext()) {
			String studentData= scanner.nextLine();
			Long ID = Long.parseLong(studentData.split(" ")[0]);
			assertEquals(true, gradeSys.inList(ID));
		}
		scanner.close();
		assertEquals(false, gradeSys.inList(new Long(104062104)));
	}
	
	@Test
	void testGetWeight() throws Exception{
		int[] testweight=gradeSys.getWeight();
		assertEquals(testweight, gradeSys.weight);
	}
	@Test
	void testGetGrade() throws Exception{
		Scanner scanner = new Scanner(new File("src/simple.txt"));
		while(scanner.hasNext()) {
			String input= scanner.nextLine();
			String studentData[] = input.split(" ");
			Long ID = Long.parseLong(studentData[0]);
			int grade[] = new int[5];
			for(int i=0;i<5;i++) {
				grade[i] = Integer.parseInt(studentData[i+2]);
			}
			assertArrayEquals(grade, gradeSys.getGrade(ID));
		}
		scanner.close();
	}
	@Test
	void testCaseUpdateWeight() throws Exception{
		Scanner scanner = new Scanner(System.in);
		//gradeSys.caseUpdateWeight(scanner, weight, gradeSys, test);
	}
	@Test
	void testGetName() throws Exception{
		
	}

}
