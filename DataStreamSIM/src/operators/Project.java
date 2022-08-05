package operators;

/**
 * The implementation of a project operator.
 * 
 * @version 1.0
 * @author kostas
 */
public class Project extends Operator {
	
	private String projstream;
	
	private String projattribute;
	
	/**
	 * Constructor of class <class>Project</class>.
	 */
	public Project() {
		super();
		this.projstream = new String();
		this.projattribute = new String();
	}//end constructor Project()
	
	public void setProjstream(String str) {
		this.projstream = str;
	}
	
	public String getProjstream() {
		return this.projstream;
	}
	
	public void setProjattribute(String str) {
		this.projattribute = str;
	}
	
	public String getProjattribute() {
		return this.projattribute;
	}

	@Override
	public void parseOpInfodata() {
		String infodata = getOpInfodata();
		String[] fields = infodata.split(" ");
		
		setProjstream(fields[1]);
		setProjattribute(fields[2]);
	}
	
	@Override
	public void setPriority(float priority) {
		this.priority = priority;
	}

	@Override
	public float getPriority() {
		return this.priority;
	}

	/**
	 * Textual representation.
	 */
	@Override
	public String toString() {
		return "Project [projstream=" + projstream + ", projattribute="
				+ projattribute + "]";
	}

}//end class Project
