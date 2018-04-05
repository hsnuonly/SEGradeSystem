
import java.awt.List;
import java.awt.RenderingHints.Key;
import java.io.*;
import java.lang.reflect.Array;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.w3c.dom.traversal.NodeIterator;

public class GradeSys {

	Hashtable<Long, StudentsGrade> studentTable = new Hashtable<>();
	int weight[] = {10,10,10,30,40};
	public Boolean add(StudentsGrade studentsGrade) {
		this.studentTable.put(studentsGrade.getID(), studentsGrade);
		return true;
	}
	public int[] getGrade(Long ID) {
		if(this.studentTable.containsKey(ID))return this.studentTable.get(ID).getGrade();
		return null;
	}
	public boolean inList(Long ID) {
		return this.studentTable.containsKey(ID);
	}
	public String getName(Long ID) {
		if(inList(ID))return studentTable.get(ID).getName();
		else return "";
	}
	public int getNumberOfMember() {
		return studentTable.size();
	}
	public int[] getWeight(Long ID) {
		//studentTable.get(ID).getWeight();
		return this.weight;
	}
	public void updateWeight(int newWeight,int position) {
		this.weight[position] = newWeight;
	}
	public int getTotal(Long ID) {
		if(inList(ID))return studentTable.get(ID).getTotal(weight);
		return -1;
	}
	public int getRank(Long ID) {
		ArrayList<StudentsGrade> temp = new ArrayList<>();
		for(Map.Entry<Long, StudentsGrade> e:studentTable.entrySet()) {
			temp.add(e.getValue());
		}
		temp.sort(new Comparator<StudentsGrade>() {
			@Override
			public int compare(StudentsGrade a,StudentsGrade b) {
				return b.getTotal(weight)-a.getTotal(weight);
			}
		});
		for(int i=0;i<temp.size();i++) {
			if(temp.get(i).getID().equals(ID))return i+1;
		}
		return -1;
	}
	public void showGrades(int[] grade,String[] test,Long ID,GradeSys gradeSys) {
		for(int i=0;i<grade.length;i++) {
			if(i<grade.length-2) {	
				if(grade[i]>=60)
					System.out.println(test[i]+":\t\t"+grade[i]);
				else
					System.out.println(test[i]+":\t\t"+grade[i]+"*");
			}
			else {
				if(grade[i]>=60)
					System.out.println(test[i]+":\t"+grade[i]);
				else
					System.out.println(test[i]+":\t"+grade[i]+"*");
			}
		}
		System.out.println("total grade:\t"+gradeSys.getTotal(ID));
	}
	public int caseUpdateWeight(Scanner scanner,int weight[],GradeSys gradeSys,String test[],Long ID) {
		System.out.println("舊配分");
		for(int i=0;i<weight.length;i++) {
			if(i!=weight.length-1)
				System.out.println(test[i]+"\t\t"+weight[i]);
			else
				System.out.println(test[i]+"\t"+weight[i]);
		}
		System.out.println("輸入新配分");
		for(int i=0;i<weight.length;i++) {
				System.out.print(test[i]+"\t\t");
				int inputWeight = scanner.nextInt();
				gradeSys.updateWeight(inputWeight,i);
				//System.out.print("\n");
		}
		System.out.println("請確認新配分");
		for(int i=0;i<weight.length;i++) {
			if(i!=weight.length-1)
				System.out.println(test[i]+"\t\t"+weight[i]+"%");
			else
				System.out.println(test[i]+"\t"+weight[i]+"%");
		}
		System.out.println("以上正確嗎? Y (Yes) 或 N (No)");
		String inputYN = scanner.next();
		if(inputYN.equals("N")) {
			gradeSys.caseUpdateWeight(scanner,weight,gradeSys,test,ID);
		}
	
		return 0;
		
		
	}
	public int commandExecute(Long ID,Scanner scanner,GradeSys gradeSys,String input ,int[] grade, int weight[],String[] test,int flag) {
		
		switch (input) {
		case "G":
			gradeSys.showGrades(grade,test,ID,gradeSys);
			break;
		case "R":
			System.out.println(gradeSys.getName(ID)+"排名第"+(gradeSys.getRank(ID))+"/"+gradeSys.studentTable.size());
			break;
		case "A":
			System.out.println(gradeSys.getName(ID)+"的平均為"+(grade[0]+grade[1]+grade[2]+grade[3]+grade[4])/5);
			break;
		case "W":
			flag=gradeSys.caseUpdateWeight(scanner,weight,gradeSys,test,ID);
			break;
		case "E"	:
			flag=1;
			break;				
		default:
			break;
		}
		return flag==1?1:0;
	}

	public static void menu(Long ID,Scanner scanner,GradeSys gradeSys) {
		int flag=0;
		while(flag==0) {
			System.out.println("輸入指令 1)G 顯示成績\n        2)R 顯示排名\n        3)A 顯示平均\n        4)W 更新配分\n        5)E 離開選單");
			String input = scanner.next();
			int grade[] = gradeSys.getGrade(ID);
			int weight[] = gradeSys.getWeight(ID);
			String test[]= {"lab1","lab2","lab3","mid-term","final exam"};
			flag=gradeSys.commandExecute(ID,scanner,gradeSys,input,grade,weight,test,flag);

		}
	}
	public static void main(String[] args) throws IOException {
		Long ID = null;
		GradeSys gradeSys = new GradeSys();
		Scanner scanner = new Scanner(new File("src/simple.txt"));
		while(scanner.hasNext()) {
			String studentData= scanner.nextLine();
			StudentsGrade studentsGrade = new StudentsGrade(studentData.split(" "));
			gradeSys.add(studentsGrade);
		}
		scanner.close();
		
		scanner = new Scanner(System.in);
		
		while(true) {
			System.out.print("輸入ID或Q(結束使用): ");
			String input = scanner.next();
			if(input.matches("^Q"))break;
			else{
				ID = Long.parseLong(input);
				System.out.println(ID);
				if(gradeSys.inList(ID)) {
					System.out.println("Welcome "+gradeSys.getName(ID));
					menu(ID, scanner, gradeSys);
					continue;
				}
			}
			
		}
	}

}
