package organiser;

import java.util.UUID;

public class DataItem<T> {
	private UUID id;
	private String label;
	private T value;
	public boolean isEdit;
	
	public DataItem(String label, T data){
		id = UUID.randomUUID();
		this.label = label;
		this.value = data;
		this.isEdit = true;
	}
	
	public UUID getID(){
		return id;
	}
	public String getLabel(){
		return label;
	}
	public T getValue(){
		return value;
	}
}
