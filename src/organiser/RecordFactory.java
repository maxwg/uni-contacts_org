package organiser;

public class RecordFactory {
	private static RecordFactory instance;
	public static RecordFactory instance(){
		if(instance == null){
			instance = new RecordFactory();
		}
		return instance;
	}
}
