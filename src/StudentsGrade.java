
public class StudentsGrade implements Comparable<StudentsGrade>{
	private Long ID;
	private String name;
	private int grade[] = new int[5];
	private int weight[] = {10,10,10,30,40};
	private float total;
	//private int lab1W=10,lab2W=10,lab3W=10,midtermW=30,finalexamW=30;
	
	public StudentsGrade(String[] inputSource) {
		this.ID=Long.parseLong(inputSource[0]);
		this.name=inputSource[1];
		for(int i=2;i<7;i++)grade[i-2]=Integer.parseInt(inputSource[i]);
	}
	public int getTotal() {
		total = 0;
		for(int j =0;j<this.grade.length;j++) {
			total+=(float)(this.grade[j]*this.weight[j])/100;
		}
		return Math.round(total);
	}
	public Long getID() {
		return ID;
	}
	public int[] getGrade() {
		return this.grade;
	}
	@Override
	public int compareTo(StudentsGrade other) {
		return other.getTotal()-this.getTotal();
	}
	public String getName() {
		return name;
	}
	public int[] getWeight() {

		return this.weight;
	}
	public void updateWeight(int newWeight,int position) {
		this.weight[position]=newWeight;
	}

}
