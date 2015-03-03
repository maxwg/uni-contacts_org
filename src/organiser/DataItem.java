package organiser;

import java.util.UUID;

public class DataItem<T extends DisplayableItem> {
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
	public void setValue(T value){
		this.value = value;
	}
}
