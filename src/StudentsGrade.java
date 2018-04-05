
public class StudentsGrade{
	private Long ID;
	private String name;
	private int grade[] = new int[5];
	private float total;
	
	public StudentsGrade(String[] inputSource) {
		this.ID=Long.parseLong(inputSource[0]);
		this.name=inputSource[1];
		for(int i=2;i<7;i++)grade[i-2]=Integer.parseInt(inputSource[i]);
	}
	public int getTotal(int weight[]) {
		total = 0;
		for(int j =0;j<this.grade.length;j++) {
			total+=(float)(this.grade[j]*weight[j])/100;
		}
		return Math.round(total);
	}
	public Long getID() {
		return ID;
	}
	public int[] getGrade() {
		return this.grade;
	}
	public String getName() {
		return name;
	}

}
